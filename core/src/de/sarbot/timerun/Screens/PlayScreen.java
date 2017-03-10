package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.timerun.*;

/**
 * Created by sarbot on 28.02.17.
 */

public class PlayScreen implements Screen{

    private TimeRun game;
    private Level level;
    private ParallaxLayer parallaxBackground;
    private Stage hudStage;
    private Table hudTable;
    private Stage backStage;
    private Parallax paraBackground;
    private Texture bgImage;
    private Interface hud;
    private OrthographicCamera myCam;
    private StretchViewport myViewport;

    public PlayScreen(TimeRun gam){
        game = gam;
    }

    @Override
    public void show() {

        myCam = new OrthographicCamera(game.width, game.height);
        myViewport = new StretchViewport(game.width,game.height, myCam);
        backStage = new Stage(myViewport);
        hudStage = new Stage();
        hudTable = new Table();
        bgImage = new Texture("img/background.png");

        int lvl = game.data.lvl;
        if(!Gdx.files.internal("level/level"+lvl+".tmx").exists()) {game.setScreen(new CreditsScreen(game));}
        level = new Level(lvl); //create level, which creates the player

        paraBackground = new Parallax(bgImage, game.width, game.height);
        hud = new Interface(level, game);
        hudStage.addActor(hud);
        hudStage.setViewport(game.viewport);
        backStage.addActor(paraBackground);

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) ||
                Gdx.input.isKeyPressed(Input.Keys.UP) ||
                isTouched(0.5f, 1) ) {
            level.player.jump();
        }
        level.player.resetState();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || isTouched(0, 0.5f) ) {
            level.player.slide();
        }

        level.update(delta);

        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        paraBackground.setSpeed(0);
        if(level.player.alive && level.player.position.x > Gdx.graphics.getWidth()/64 && !level.won){
            paraBackground.setSpeed(1);
        }
        //hudStage.act(delta);
        //hudStage.draw();
        backStage.act(delta);
        hudStage.act(delta);
        backStage.draw();
        level.render();
        hudStage.draw();

    }


    private boolean isTouched (float startX, float endX) {
        // Check for touch inputs between startX and endX
        // startX/endX are given between 0 (left edge of the screen) and 1 (right edge of the screen)
        for (int i = 0; i < 2; i++) {
            float x = Gdx.input.getX(i) / (float)Gdx.graphics.getWidth();
            if (Gdx.input.isTouched(i) && (x >= startX && x <= endX)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.dispose();
        level.dispose();
        bgImage.dispose();
    }
}
