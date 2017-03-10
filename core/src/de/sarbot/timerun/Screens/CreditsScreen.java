package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.sarbot.timerun.TimeRun;

import java.sql.Time;

/**
 * Created by sarbot on 06.03.17.
 */
public class CreditsScreen implements Screen {

    private TimeRun game;
    private float timer;
    private float y;
    private float speed;

    public CreditsScreen(TimeRun gam){
        game = gam;
        timer = 0;
        y = 0;
        speed = 20; // px-units/sec
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timer += delta;
        y = timer*speed;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    }
}
