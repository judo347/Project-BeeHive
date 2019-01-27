package dk.mk;

import com.badlogic.gdx.Screen;

import static dk.mk.GameInfo.TICK_DURATION;

public class GameScreen implements Screen{

    private GdxGame game;
    private GameClass gameClass;

    private float stateTime;

    public GameScreen(GdxGame game){
        this.game = game;
        this.stateTime = 0f;
        initialize();
    }

    private void initialize(){
        this.gameClass = new GameClass(SpawnMethods.SPAWN_TYPE.TESTSPAWN);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.stateTime += delta;

        //Calls the method gameClass.tick() when/every TICK_DURATION has passed.
        if(stateTime >= TICK_DURATION){
            this.gameClass.tick(delta);
            stateTime -= TICK_DURATION;
        }

        //Render gameClass
        game.batch.begin();
        this.gameClass.render(game.batch);
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
