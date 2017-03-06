package de.sarbot.timerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import de.sarbot.timerun.Screens.PlayScreen;


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
    private float buttonY;
    private Skin skin;
    private Table table;
    private Texture btn;
    private Texture cupsImg;
    private TextureRegion[] cups;
    private TimeRun game;


    public Interface (Level lvl, TimeRun gam){
        game = gam;
        level = lvl;
        font = new BitmapFont(Gdx.files.internal("mabitmap.fnt"));
        popupImg = new Texture("img/popup.png");
        btn = new Texture("img/buttons.png");
        cupsImg = new Texture("img/cups.png");

        cups = TextureRegion.split(cupsImg, 83, 81)[0];
        timer = 0;
        deltaTime = 0;
        popupY = 500;
        buttonY = -200;
    }
    @Override
    public void act(float delta){

        deltaTime = delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(level.coin, 15, 420, (float) (level.coin.getWidth()), (float)(level.coin.getHeight()));
        font.draw(batch, level.score+ "", 50, 480-30);

        //draw popup:
        if(level.won || !level.player.alive){
            timer += deltaTime; //increase timer 1/sec
            movePopup();
            //moveButtons();
            batch.draw(btn, 400-btn.getWidth()/2, buttonY);
            batch.draw(popupImg, 400-popupImg.getWidth()/2, popupY);
            batch.draw(level.coin, 400-70, popupY + 70);
            font.draw(batch, level.score +" / " + level.coinsCount, 800/2 -30, popupY + 100);
        }
        if(level.won){
            int place = 2;
            if (level.score/level.coinsCount == 1) place = 0;
            else if(level.score/level.coinsCount > 0.75) place = 1;
            font.draw(batch, "Level Done", 800/2 -70, popupY + 160);
            batch.draw(cups[place], 800/2 + 100, popupY + 20, 61, 60);
            if(timer > 3){
                game.level++;
                game.setScreen(new PlayScreen(game));
            }

        }
        else if(!level.player.alive){
            font.draw(batch, "Game Over", 800/2 -70, popupY + 160);
            if(timer >3){
                game.setScreen(new PlayScreen(game));
            }
        }

    }

    public void dispose(){

        level.dispose();
        btn.dispose();
        game.dispose();
        cupsImg.dispose();

    }

    private void movePopup(){
        if(timer < 0.8){
            float m = (float) 400;
            popupY =  m * (float)Math.pow(timer-0.6, 2)+200;
        }
        if(timer< 1.5 && timer > 0.8){
            float m = (float) 40;
            buttonY = popupY - m*timer;
        }
    }

    private void moveButtons(){
        if(timer < 1){
            float m = (float) 400;
            buttonY = -350 + timer * m;
        }

    }
}
