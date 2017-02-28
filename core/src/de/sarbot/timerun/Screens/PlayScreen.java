package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.sarbot.timerun.Level;
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
        level.update();

        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        defaultBatch.begin();
        level.render(defaultBatch);
        defaultBatch.end();

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
