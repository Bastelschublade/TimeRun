package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sarbot on 27.02.17.
 */
public class Parallax extends Actor {

    private int scroll;
    private Texture layers;
    private final int LAYER_SPEED_DIFFERENCE = 2;

    float x,y,width,heigth,scaleX,scaleY;
    int originX, originY,rotation,srcX,srcY;
    boolean flipX,flipY;

    private int speed;

    public Parallax(Texture textures, int w, int h){
        width = w;
        heigth = h;
        layers = textures;
        layers.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);

        scroll = 0;
        speed = 0;

        x = y = originX = originY = rotation = srcY = 0;
        width =  800;
        heigth = 480;
        scaleX = scaleY = 1;
        flipX = flipY = false;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);


        scroll+=speed;
        srcX = scroll;
        batch.draw(layers, x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.getWidth(),layers.getHeight(),flipX,flipY);

    }
}