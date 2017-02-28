package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import javax.xml.soap.Text;

/**
 * Created by sarbot on 28.02.17.
 */
public class Player {

    public Vector2 position;
    public Vector2 size;
    public Vector2 velocity;

    private Texture playerImg;

    public Player(){

        position = new Vector2(0,0);
        size = new Vector2(0,0);
        velocity = new Vector2(0,0);
        playerImg = new Texture(Gdx.files.internal("img/player.png"));
    }

    public void render(Batch batch){
        batch.draw(playerImg, position.x, position.y);
    }

}
