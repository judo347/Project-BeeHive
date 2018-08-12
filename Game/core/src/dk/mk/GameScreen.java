package dk.mk;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen{

    private GdxGame game;
    private World world;
    private GameMapNew map;

    private float stateTime;

    public GameScreen(GdxGame game){
        this.game = game;
        this.stateTime = 0f;
        initialize();
    }

    private void initialize(){
        //this.world = new World(new Vector2(0,-9.8f), true); //Creating a world with gravity, true allows sleep = Dont calculate when nothing happens to elements.
        this.map = new GameMapNew();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.stateTime += delta;

        if(stateTime >= 1f){  //TODO These two places to change pace of ticks/game
            this.map.tick();
            stateTime -= 1; //TODO These two places to change pace of ticks/game
        }

        game.batch.begin();
        this.map.render(game.batch);
        game.batch.end();
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
