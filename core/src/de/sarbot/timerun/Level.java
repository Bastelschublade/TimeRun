package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.util.Iterator;

/**
 * Created by sarbot on 28.02.17.
 */
public class Level implements Disposable {

    public Player player;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Array<Vector2> coinPositions;
    private int coinsCount;
    private float finishX;
    public Texture coin;
    private float gravity;
    private Vector2 newPosition;
    private Array<Rectangle> tiles;
    private Rectangle playerRect;
    private Animation<TextureRegion> stand;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> slide;
    int score;
    public boolean won;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    public Level(int lvl){

        score = 0;
        won = false;
        newPosition = new Vector2(0,0);
        tiles = new Array<Rectangle>();
        gravity =  40;
        coin = new Texture(Gdx.files.internal("img/coin.png"));
        map = new TmxMapLoader().load("level/level"+lvl+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f); //1/32 da 1tile = 32px
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 20, 12);
        camera.position.x = Gdx.graphics.getWidth()/64;
        camera.update();
        player = new Player();

        playerRect = new Rectangle();
        parseObjects();

    }

    public void update(float delta){
        playerUpdate(delta);
        if(player.alive && player.position.x*32 > Gdx.graphics.getWidth()/2)
            camera.position.x = player.position.x; //todo: use boolean for this to use same for paralax and stop for dead
        camera.update(); //otherwise it moves out of the screen

    }



    public void playerUpdate(float delta){
        player.stateTime += delta;
        if(!player.alive || won) {
            player.velocity.y -= gravity*delta;
            newPosition.y = player.position.y + player.velocity.y * delta;
            collideBottom();
            player.position.y = newPosition.y;
            return; // player will not be updated after he dies (exept the gravitation
        }
        if(player.position.x > finishX){
            won = true;
            player.state = Player.State.Standing;
            return;
        }



        player.size.y = Player.HEIGHT; //reset height from sliding

        //shrink player hitbox to sliding
        if(player.state == Player.State.Sliding){
            player.size.y = Player.SLIDEHEIGHT;
        }

        player.velocity.y -= gravity * delta;
        newPosition.x = player.position.x +player.velocity.x * delta;
        newPosition.y = player.position.y + player.velocity.y * delta;
        collideBottom();
        collideRight();
        collideCoins();
        player.position.x = newPosition.x;
        player.position.y = newPosition.y;

    }



    public void render(){

        //render tiledmap
        renderer.setView(camera);
        renderer.render();

        //render coins on tiledmap batch
        renderCoins();

        player.render(renderer.getBatch()); //actually rendered on defaultBatch in PlayScreen (independent from camera)
    }



    private void parseObjects(){
        //read the map file and set startPos and finish for player and create list of coins
        MapLayer objLayer = map.getLayers().get("objectsLayer");

        coinPositions = new Array<Vector2>();
        coinsCount = 0;

        for(MapObject obj :  objLayer.getObjects()){


            if(obj instanceof TextureMapObject) {
                if(obj.getName().contains("coin")) {
                    float x, y;
                    x = 10;
                    y = 10;
                    TextureMapObject mapObject = (TextureMapObject) obj;
                    x = mapObject.getX();
                    y = mapObject.getY();
                    Vector2 pos = new Vector2(x / 32, y / 32);
                    coinPositions.add(pos);
                    coinsCount++;
                }
                else if(obj.getName().contains("exit")){
                    finishX = ((TextureMapObject) obj).getX() / 32;
                }
                else if(obj.getName().contains("start")){
                    player.position.x = ((TextureMapObject) obj).getX() / 32;
                    player.position.y = ((TextureMapObject) obj).getY() / 32;
                }
            }


        }
    }

    public void renderCoins(){
        Batch batch = renderer.getBatch();
        batch.begin();

        for(Vector2 pos : coinPositions){
            batch.draw(coin, pos.x, pos.y, 1, 1);
        }
        batch.end();


    }

    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("blockedLayer");
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                //TiledMapTileLayer.Cell cell = layer.getCell(150, 0);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
    }

    private void collideBottom(){
        player.grounded = false; //false if not groundet...
        int startX, startY, endX, endY;
        startX = (int) (player.position.x);
        endX = (int) (player.position.x + player.size.x);
        //startY = (int) player.position.y;
        startY = endY = (int) (newPosition.y); //deltaY is so small on good fps its lost on casting to int anyway
        getTiles(startX, startY, endX, endY, tiles);
        playerRect.x = newPosition.x;
        playerRect.y = newPosition.y;
        playerRect.width = player.size.x;
        playerRect.height = player.size.y; //matters if we later change the hitbox for sliding
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                // collide only in bottom direction
                if (player.velocity.y < 0) {
                    //set player ontop of the tile
                    newPosition.y = tile.y + tile.height;
                    // if we hit the ground, mark us as grounded so we can jump
                    player.grounded = true;
                    //we will add player states later
                    //if(player.state != player.State.Dead) player.state = player.State.Walking;
                }
                //reset falling speed
                player.velocity.y = 0;
                break;
            }
        }
    }

    private void collideRight(){
        int startX, startY, endX, endY;
        startX = endX = (int) (newPosition.x + player.size.x); //we round to a specific tile thats not good... can we pass float instead?
        startY = (int) (newPosition.y ); //this is bad rounded too
        endY = (int) (newPosition.y + player.size.y); //deltaY is so small on good fps its lost on casting to int anyway
        getTiles(startX, startY, endX, endY, tiles);
        playerRect.x = newPosition.x;
        playerRect.y = newPosition.y;
        playerRect.width = player.size.x;
        playerRect.height = player.size.y; //matters if we later change the hitbox for sliding
        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                //reset falling speed
                player.velocity.x = 0;
                player.alive = false;
                break;
            }
        }
    }

    private void collideCoins() {
        Iterator<Vector2> i = coinPositions.iterator();
        while (i.hasNext()) {
            Vector2 pos = i.next();

            if ((player.position.x < pos.x + 1) && (player.position.x + 1 > pos.x) && (player.position.y < pos.y + 1) && (player.position.y + 1 > pos.y)) {
                i.remove(); //remove coin from position list
                score++;

            }
        }
    }


    @Override
    public void dispose() {
        map.dispose();
        player.dispose();
    }
}
