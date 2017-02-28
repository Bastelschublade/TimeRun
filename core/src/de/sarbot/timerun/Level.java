package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by sarbot on 28.02.17.
 */
public class Level {

    private Player player;
    private TiledMap map;
    private Texture backgroundImg;

    public Level(int lvl){
        backgroundImg = new Texture(Gdx.files.internal("img/background.png"));
        map = new TmxMapLoader().load("level/level"+lvl+".tmx");
        player = new Player();
        parseObjects();

    }

    public void update(){

    }

    public void render(Batch batch){
        batch.draw(backgroundImg, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
    }

    private void parseObjects(){
        //read the map file and set startPos and finish for player and create list of coins
        player.position.x = 10;
        player.position.y = 10;
    }

}
