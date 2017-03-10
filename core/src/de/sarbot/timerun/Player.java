package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import javax.xml.soap.Text;

/**
 * Created by sarbot on 28.02.17.
 */
public class Player implements Disposable{

    public static float JUMPSPEED = 23f;
    public static float RUNSPEED = 10f;
    public static float SLIDEHEIGHT = 0.8f;
    public static float HEIGHT = 1.3f;
    public static float WIDTH = 1f;
    public Vector2 position;
    public Vector2 size;
    public Vector2 velocity;
    public boolean grounded;
    public boolean alive;
    enum State { Standing, Walking, Jumping, Sliding}
    public State state = State.Walking;
    float stateTime;

    private Texture playerWalkTexture;
    private Texture playerStaticsTexture;
    private Animation<TextureRegion> stand;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> slide;
    private Animation<TextureRegion> dead;


    public Player(){

        //init data
        position = new Vector2(0,0);
        size = new Vector2(WIDTH, HEIGHT);
        velocity = new Vector2(RUNSPEED,0);
        grounded = false;
        alive = true;

        //animations and textures
        //playerImg = new Texture(Gdx.files.internal("img/player.png")); //not longer needed
        playerWalkTexture = new Texture("img/player_walk.png"); //all animation frames
        playerStaticsTexture = new Texture("img/player_statics.png"); //all other static images in one file
        TextureRegion[] regions = TextureRegion.split(playerWalkTexture, 32, 42)[0];
        TextureRegion[] staticRegions = TextureRegion.split(playerStaticsTexture, 32, 42)[0];
        //first parameter sets framduration we use 0 to dont animate it
        stand = new Animation(0,staticRegions[0]);
        jump = new Animation(0, staticRegions[1]);
        slide = new Animation( 0, staticRegions[3]);
        dead = new Animation(0, staticRegions[2]);
        walk = new Animation(0.05f, regions); //the hole vector
        walk.setPlayMode(Animation.PlayMode.LOOP); //loop-pingpong if u have a more simple animation

        //if u want to get the player size from the image size to keep ratio do it here i did it manually in deklaration
        WIDTH = 1/32f * regions[0].getRegionWidth();
        HEIGHT = 1/32f * regions[0].getRegionHeight();
    }

    public void jump(){
        //TODO: switch playerstate to JUMPING
        if(grounded && alive) {
            velocity.y = JUMPSPEED;
            state = State.Jumping;
            grounded = false;
        }
    }

    public void slide(){
        if(grounded && alive){
            state = State.Sliding;
        }
    }

    public void render(Batch batch){

        TextureRegion frame = null; //create empty frame
        if(!alive){
            frame = dead.getKeyFrame(stateTime);
        }
        else{
            switch (state) {
                case Standing:
                    frame = stand.getKeyFrame(stateTime);
                    break;
                case Walking:
                    frame = walk.getKeyFrame(stateTime);
                    break;
                case Jumping:
                    frame = jump.getKeyFrame(stateTime);
                    break;
                case Sliding:
                    frame = slide.getKeyFrame(stateTime);
                    break;
            }
        }
        batch.begin();
        //batch.draw(playerImg, position.x, position.y, size.x, size.y);
        batch.draw(frame, position.x, position.y, WIDTH, HEIGHT); //we render always in 32x42px just hitbox is smaller on slide
        batch.end();
    }

    public void resetState(){
        if(grounded && alive && state != State.Standing){
            // TODO: check for collision up (dont stand up if tile above)
            state = State.Walking;
        }
        else if (alive && !grounded && state != State.Standing){
            state = State.Jumping;
        }
    }

    @Override
    public void dispose() {

        playerStaticsTexture.dispose();
        playerWalkTexture.dispose();
    }
}
