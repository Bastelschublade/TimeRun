package de.sarbot.timerun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.sarbot.timerun.Screens.MenuScreen;

public class TimeRun extends Game {

    // class variables to read from everywhere
    public static final String TITLE = "Time Run";

    // variables for the game object
    public int level;
    public int maxLevel;
    public FillViewport viewport;
    public int width;
    public int height;
    private OrthographicCamera camera;


	@Override
	public void create () {

        //generate camera and viewport for on screen positioning like hud and menus
		width = 800;
		height = 480;
        camera = new OrthographicCamera();
        viewport = new FillViewport(800,480, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        camera.update();

	    level = 0;
	    maxLevel = 3;
	    //get levelcount here ?
	    setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
