package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.huds.LoginHUD;
import hu.elte.kormi.tankharc.GameMain;

public class Login implements Screen {

    private int timer = 0;
    private final GameMain gameMain;
    private final Texture background;
    private final Texture invalidCredentials;
    private final Texture notVerified;
    private final LoginHUD loginHUD;

    public Login(GameMain gameMain){

        this.gameMain = gameMain;

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        orthographicCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        background = new Texture("backgrounds/LoginBackground.png");
        invalidCredentials = new Texture("info/InvalidCredentials.png");
        notVerified = new Texture("info/EmailNotVerified.png");
        loginHUD = new LoginHUD(gameMain);
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

        if(!loginHUD.getAreCredentialsValid()){
            gameMain.getSpriteBatch().draw(invalidCredentials, 450, 470);
            timer = timer + 1;
            if(timer > 240){
                timer = 0;
                loginHUD.setAreCredentialsValid(true);
            }
        }

        if(!loginHUD.getIsEmailVerified()){
            gameMain.getSpriteBatch().draw(notVerified, 450, 470);
            timer = timer + 1;
            if(timer > 240){
                timer = 0;
                loginHUD.setIsEmailVerified(true);
            }
        }

        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(loginHUD.getStage().getCamera().combined);
        loginHUD.getStage().draw();

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
        notVerified.dispose();
        invalidCredentials.dispose();
        background.dispose();
        loginHUD.getStage().dispose();
    }
}
