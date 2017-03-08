package de.sarbot.timerun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.sarbot.timerun.PagedScrollPane;
import de.sarbot.timerun.TimeRun;

/**
 * Created by sarbot on 28.02.17.
 */
public class LevelScreen implements Screen{

    private TimeRun game;
    private StretchViewport myVp;

    private Skin skin;
    private Stage stage;
    private Table container;
    private Skin levelSkin;
    private TextureAtlas atlas;
    private BitmapFont font;

    public LevelScreen (TimeRun gam){
        game = gam;
    }
    @Override
    public void show() {

        myVp = new StretchViewport(800,480);
        stage = new Stage(myVp);
        font = new BitmapFont(Gdx.files.internal("ui/default.fnt"));

        atlas = new TextureAtlas("ui/levelmenu.atlas");
        levelSkin = new Skin(atlas);
        levelSkin.add("top", levelSkin.newDrawable("boxEmpty"), Drawable.class);
        /*
        skin = new Skin(Gdx.files.internal("testui/uiskin.json"));
        skin.add("top", skin.newDrawable("default-round", Color.RED), Drawable.class);
        skin.add("star-filled", skin.newDrawable("white", Color.YELLOW), Drawable.class);
        skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY), Drawable.class);
        */
        skin = new Skin(atlas);
        skin.add("top", skin.newDrawable("boxEmpty"), Drawable.class);
        skin.add("star-filled", skin.newDrawable("star"), Drawable.class);
        skin.add("star-unfilled", skin.newDrawable("star", Color.GRAY), Drawable.class);

        skin.add("default", font, BitmapFont.class);


        Gdx.input.setInputProcessor(stage);

        container = new Table();
        stage.addActor(container);
        container.setFillParent(true);

        PagedScrollPane scroll = new PagedScrollPane();
        scroll.setFlingTime(0.1f);
        scroll.setPageSpacing(25);
        int c = 1;
        int lvlperpage = 12;
        int pages = (int) Math.floor(game.maxLevel/lvlperpage); //calc number of pages
        for (int l = 0; l <= pages; l++) {
            Table levels = new Table().pad(50);
            levels.defaults().pad(20, 40, 20, 40);
            for (int y = 0; y < 3; y++) {
                levels.row();
                for (int x = 0; x < 4; x++) {
                    System.out.print(game.maxLevel + " and c: " + c + "\n");
                    if(c <= game.maxLevel){
                        //(c++) fkt wird für c ausgef und dann ++ genommen
                        levels.add(getLevelButton(c++)).expand().fill();
                    }
                    else{
                        levels.add(getEmptyButton()).expand().fill();
                    }
                }
            }
            scroll.addPage(levels);
        }
        container.add(scroll).expand().fill();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //Table.drawDebug(stage);

    }

    @Override
    public void resize(int width, int height) {
        myVp.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.setViewport(myVp);
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
        skin.dispose();
    }

    public Button getEmptyButton() {
        Button.ButtonStyle btnStyle = new Button.ButtonStyle();
        Button button = new Button(btnStyle);
        Button.ButtonStyle style = button.getStyle();
        style.up = style.down = null;

        return button;

    }


    public Button getLevelButton(int level) {
        Button.ButtonStyle btnStyle = new Button.ButtonStyle();

        Button button = new Button(btnStyle);
        //Button.ButtonStyle style = button.getStyle();
        //style.up = 	style.down = null;

        // Create the label to show the level number
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        Label label = new Label(Integer.toString(level), labelStyle);
        label.setFontScale(2f);
        label.setAlignment(Align.center);

        // Stack the image and the label at the top of our button
        button.stack(new Image(skin.getDrawable("top")), label).expand().fill();

        // Randomize the number of stars earned for demonstration purposes
        int stars = MathUtils.random(-1, +3);
        Table starTable = new Table();
        starTable.defaults().pad(0);
        if (stars >= 0) {
            for (int star = 0; star < 3; star++) {
                if (stars > star) {
                    starTable.add(new Image(skin.getDrawable("star-filled"))).width(40).height(40);
                } else {
                    starTable.add(new Image(skin.getDrawable("star-unfilled"))).width(40).height(40);
                }
            }
        }

        button.row();
        button.add(starTable).height(40);

        button.setName("Level " + Integer.toString(level));
        button.setOriginX(level);
        button.addListener(levelClickListener);
        return button;
    }

    /**
     * Handle the click - in real life, we'd go to the level
     */
    public ClickListener levelClickListener = new ClickListener() {
        @Override
        public void clicked (InputEvent event, float x, float y) {
            System.out.println("Click: " + event.getListenerActor().getName());
            if(event.getListenerActor().getOriginX() <= game.maxLevel){
                game.level = (int) event.getListenerActor().getOriginX();
                game.setScreen(new PlayScreen(game));
            }
        }
    };
}
