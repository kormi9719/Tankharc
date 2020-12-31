package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;

import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.huds.RegisterHUD;
import hu.elte.kormi.tankharc.GameMain;

public class Register implements Screen {

    private int timer = 0;

    private final GameMain gameMain;
    private final Texture background;
    private final RegisterHUD registerHUD;
    private final Texture passwordInfo;
    private final Texture usernameInfo;
    private final Texture emailInUse;
    private final Texture passwordShort;
    private final Texture usernameLong;
    private final Texture passwordNotMatch;
    private final Texture usernameCharacter;

    public Register(GameMain gameMain){

        this.gameMain = gameMain;

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        orthographicCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        passwordInfo = new Texture("info/PasswordInfo.png");
        usernameInfo = new Texture("info/UsernameInfo.png");
        passwordShort = new Texture("info/PasswordShort.png");
        emailInUse = new Texture("info/EmailAlreadyInUse.png");
        usernameLong = new Texture("info/UsernameTooLong.png");
        background = new Texture("backgrounds/LoginBackground.png");
        passwordNotMatch = new Texture("info/PasswordDoesNotMatch.png");
        usernameCharacter = new Texture("info/NotAllowedCharacter.png");
        registerHUD = new RegisterHUD(gameMain);
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

        if(registerHUD.getArePasswordNotMatching()){
            registerHUD.setPasswordInfoClicked(false);
            registerHUD.setUsernameInfoClicked(false);
            gameMain.getSpriteBatch().draw(passwordNotMatch, 450, 470);
            timer = timer + 1;
            if(timer > 240){
                timer = 0;
                registerHUD.setArePasswordNotMatching(false);
            }
        }

        if(registerHUD.getIsEmailInUse()) {
            registerHUD.setPasswordInfoClicked(false);
            registerHUD.setUsernameInfoClicked(false);
            gameMain.getSpriteBatch().draw(emailInUse, 450, 470);
            timer = timer + 1;
            if (timer > 240) {
                timer = 0;
                registerHUD.setIsEmailInUse(false);
            }
        }

        if(registerHUD.getIsPasswordTooShort()) {
            registerHUD.setPasswordInfoClicked(false);
            registerHUD.setUsernameInfoClicked(false);
            gameMain.getSpriteBatch().draw(passwordShort, 450, 470);
            timer = timer + 1;
            if (timer > 240) {
                timer = 0;
                registerHUD.setIsPasswordTooShort(false);
            }
        }

        if(registerHUD.getIsUsernameContainsSpecialCharacter()) {
            registerHUD.setPasswordInfoClicked(false);
            registerHUD.setUsernameInfoClicked(false);
            gameMain.getSpriteBatch().draw(usernameCharacter, 450, 470);
            timer = timer + 1;
            if (timer > 240) {
                timer = 0;
                registerHUD.setIsUsernameContainsSpecialCharacter(false);
            }
        }

        if(registerHUD.getIsUsernameTooLong()) {
            registerHUD.setPasswordInfoClicked(false);
            registerHUD.setUsernameInfoClicked(false);
            gameMain.getSpriteBatch().draw(usernameLong, 450, 470);
            timer = timer + 1;
            if (timer > 240) {
                timer = 0;
                registerHUD.setIsUsernameTooLong(false);
            }
        }

        if(registerHUD.getIsPasswordInfoClicked()){
            gameMain.getSpriteBatch().draw(passwordInfo, 450, 470);
        }

        if(registerHUD.getIsUsernameInfoClicked()){
            gameMain.getSpriteBatch().draw(usernameInfo, 450, 470);
        }
        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(registerHUD.getStage().getCamera().combined);
        registerHUD.getStage().draw();


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
        passwordInfo.dispose();
        usernameInfo.dispose();
        passwordShort.dispose();
        emailInUse.dispose();
        usernameLong.dispose();
        background.dispose();
        passwordNotMatch.dispose();
        usernameCharacter.dispose();
        background.dispose();
        registerHUD.getStage().dispose();
    }
}
