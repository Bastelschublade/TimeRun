package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import de.sarbot.timerun.Level;
import de.sarbot.timerun.Parallax;
import de.sarbot.timerun.Player;
import de.sarbot.timerun.TimeRun;

/**
 * Created by sarbot on 28.02.17.
 */

public class PlayScreen implements Screen{

    private TimeRun game;
    private Level level;
    private SpriteBatch hudBatch;
    private Parallax parallaxBackground;
    private Stage hudStage;
    private Table hudTable;


    public PlayScreen(TimeRun gam){
        game = gam;
    }

    @Override
    public void show() {

        hudStage = new Stage();
        hudTable = new Table();
        hudBatch = new SpriteBatch();

        int lvl = game.level;
        level = new Level(lvl); //create level, which creates the player
        hudBatch = new SpriteBatch();
        Array<Texture> textures = new Array<Texture>();
        for(int i = 1; i <=2;i++){
            //textures.add(new Texture(Gdx.files.internal("parallax/img"+i+".png")));
            textures.add(new Texture(Gdx.files.internal("background"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        parallaxBackground = new Parallax(textures);
        parallaxBackground.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        parallaxBackground.setSpeed(1);
        hudStage.addActor(parallaxBackground);

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
        hudStage.act(delta);
        hudStage.draw();
        level.render();

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
    }
}
