package de.sarbot.timerun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import de.sarbot.timerun.Screens.GameSave;
import de.sarbot.timerun.Screens.MenuScreen;

import java.util.ArrayList;
import java.util.Iterator;


public class TimeRun extends Game {

    // class variables to read from everywhere
    public static final String TITLE = "Time Run";

    // variables for the game object
    public int level;
    public int maxLevel;
    public int unlockLevel;
    public FillViewport viewport;
    public int width;
    public int height;
    public int[] levelSolves;
    private OrthographicCamera camera;
    private GameSave gameSave;



    public class Data{
        public int lvl;
        public int[] solves;

        public Data(){
            lvl = 0;
            solves = new int[100];
        }
    }

    public Data data;


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




        //level = 0;
        unlockLevel = 3;
        maxLevel = 3;
        //levelSolves = new int[maxLevel+1];


        data = loadData();
        //levelSolves = data.solves;
        //level = data.lvl;

        int sum = 0;
        for(int elem : data.solves){
            sum += elem;
        }
        unlockLevel = (int) Math.ceil(sum/2 + 3 ); //you get 3 levels for free and than a new for each 2 stars u get

	    //get levelcount here ?
	    setScreen(new MenuScreen(this));
	    saveData();


	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	public void saveData () {
        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        json.setUsePrototypes(false);
        //json.toJson(data, Gdx.files.local("data2.json"));
        //Data dat = new Data();
        json.toJson(data, Object.class, Gdx.files.local("data.json"));
        System.out.println(json.toJson(data));

    }

    public Data loadData () {
        Json json = new Json();
        Data dat = new Data();
        //Data dat = new Data();
	    if(Gdx.files.local("data.json").exists()){
            //read data
            System.out.print("loading from file:");
            JsonValue root = new JsonReader().parse(Gdx.files.local("data.json"));
            int l = root.get("lvl").asInt();
            int[] s = root.get("solves").asIntArray();

            //modify data manually TODO: remove
            dat.solves = s;
            dat.lvl = l;
            /*
            Iterator iterator = root.get("solves").iterator();
            int[] s = new int[20];
            System.out.print("s: " + s);

            while (iterator.hasNext()){
                System.out.print(iterator.next());
            }
            */
        }
        else{
            //set default
            dat = new Data();
            dat.solves = new int[maxLevel+1];
        }
        return dat;
    }

    public void removeData(){
	    //remove data.json file TODO:
    }


}
