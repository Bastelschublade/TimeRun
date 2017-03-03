package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;


/**
 * Created by sarbot on 03.03.17.
 */
public class Interface extends Actor implements Disposable{
    private Level level;
    private BitmapFont font;
    private Texture popupImg;
    private float timer;
    private float deltaTime;
    private float popupY;


    public Interface (Level lvl){
        level = lvl;
        font = new BitmapFont(Gdx.files.internal("mabitmap.fnt"));
        popupImg = new Texture("img/popup.png");
        timer = 0;
        deltaTime = 0;
        popupY = 500;
    }
    @Override
    public void act(float delta){
        deltaTime = delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(level.coin, 20, Gdx.graphics.getHeight()-60, (float) (level.coin.getWidth()), (float)(level.coin.getHeight()));
        font.draw(batch, level.score+ "", 50, Gdx.graphics.getHeight()-30);

        //draw popup:
        if(level.won || !level.player.alive){
            timer += deltaTime; //increase timer 1/sec
            moveDown();
            batch.draw(popupImg, Gdx.graphics.getWidth()/2-popupImg.getHeight()/2, popupY);
            System.out.print(popupY);
        }
    }

    public void dispose(){
        level.dispose();
    }

    private void moveDown(){
        if(popupY > 200){
            popupY = 500 - timer*200;
        }
    }
}
