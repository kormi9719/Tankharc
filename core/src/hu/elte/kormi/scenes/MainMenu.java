package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.huds.MainMenuHUD;
import hu.elte.kormi.tankharc.GameMain;

public class MainMenu implements Screen {

    private final GameMain gameMain;
    private final Texture background;
    private final MainMenuHUD mainMenuHUD;

    public MainMenu(GameMain gameMain, User user){

        this.gameMain = gameMain;

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        orthographicCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        background = new Texture("backgrounds/LoginBackground.png");
        mainMenuHUD = new MainMenuHUD(gameMain, user);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameMain.getSpriteBatch().begin();
        gameMain.getSpriteBatch().draw(background, 0, 0);
        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(mainMenuHUD.getStage().getCamera().combined);
        mainMenuHUD.getStage().draw();

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
        background.dispose();
        mainMenuHUD.getStage().dispose();
    }
}