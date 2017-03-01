package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.sarbot.timerun.Level;
import de.sarbot.timerun.Player;
import de.sarbot.timerun.TimeRun;

/**
 * Created by sarbot on 28.02.17.
 */
public class PlayScreen implements Screen{

    private TimeRun game;
    private Level level;
    private SpriteBatch defaultBatch;

    public PlayScreen(TimeRun gam){
        game = gam;
    }

    @Override
    public void show() {

        int lvl = game.level;
        level = new Level(lvl); //create level, which creates the player
        defaultBatch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP) || isTouched(0.5f, 1) ) {
            level.player.jump();
        }
        level.player.resetState();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || isTouched(0, 0.5f) ) {
            level.player.slide();
        }

        level.update(delta);

        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level.render(defaultBatch);

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
    }
}
