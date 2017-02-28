package de.sarbot.timerun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.sarbot.timerun.Screens.MenuScreen;

public class TimeRun extends Game {

    // class variables to read from everywhere
    public static final String TITLE = "Time Run";

    // variables for the game object
    public int level;

	@Override
	public void create () {

	    level = 0;
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
