package dk.mk;

import com.badlogic.gdx.Screen;

import static dk.mk.GameInfo.TICK_DURATION;

public class GameScreen implements Screen{

    private GdxGame game;
    private GameClass map;

    private float stateTime;

    public GameScreen(GdxGame game){
        this.game = game;
        this.stateTime = 0f;
        initialize();
    }

    private void initialize(){
        this.map = new GameClass(SpawnMethods.SPAWN_TYPE.TESTSPAWN);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.stateTime += delta;

        //Calls the method map.tick() when/every TICK_DURATION has passed.
        if(stateTime >= TICK_DURATION){
            this.map.tick(delta);
            stateTime -= TICK_DURATION;
        }

        //Render map
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
