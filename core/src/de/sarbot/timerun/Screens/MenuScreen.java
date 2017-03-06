package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.sarbot.timerun.TimeRun;

/**
 * Created by sarbot on 28.02.17.
 */
public class MenuScreen implements Screen {

    private TimeRun game;
    private Texture button;
    private Texture background;
    private Batch menuBatch;
    private Stage stage;
    private TextureAtlas atlas;
    private Table table;
    private TextButton buttonExit, buttonPlay, buttonLevel;
    private BitmapFont menuFont;
    private Label heading;
    private Skin skin;
    private SpriteBatch batch;

    public MenuScreen(TimeRun gam){
        game = gam;
    }



    @Override
    public void show() {
        background = new Texture("background.png");
        atlas = new TextureAtlas("ui/skin.pack");
        menuFont = new BitmapFont(Gdx.files.internal("mabitmap.fnt"), false);


        stage = new Stage();

        Gdx.input.setInputProcessor(stage); //stage is handling user input
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down = skin.getDrawable("button");
        textButtonStyle.font = menuFont;
        textButtonStyle.pressedOffsetX = 2;
        textButtonStyle.pressedOffsetY = -2;

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(0,10,0,10);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });
        buttonLevel = new TextButton("LEVEL", textButtonStyle);
        buttonLevel.pad(0,10,0,10);
        buttonLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.pad(0, 10, 0, 10);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(menuFont, Color.WHITE);
        heading = new Label(game.TITLE, headingStyle);
        heading.setFontScale(2);

        table.add(heading);
        table.getCell(heading).padBottom(40);
        table.row();
        table.add(buttonPlay);
        table.row();
        table.add(buttonLevel);
        table.row();
        table.add(buttonExit);
        //table.debug();
        stage.addActor(table);
        batch = new SpriteBatch();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();


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

        stage.dispose();
        atlas.dispose();
        skin.dispose();
        menuFont.dispose();
    }
}
