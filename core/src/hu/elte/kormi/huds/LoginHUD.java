package hu.elte.kormi.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import hu.elte.kormi.database.DatabaseHandler;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.tankharc.GameMain;
import hu.elte.kormi.scenes.Register;
import hu.elte.kormi.scenes.MainMenu;


public class LoginHUD {

    private final Stage stage;
    private final TextField email;
    private final TextField password;
    private boolean areCredentialsValid = true;
    private boolean isEmailVerified = true;

    public LoginHUD(final GameMain gameMain){

        Skin skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());
        password = new TextField("", skin);

        ImageButton registerBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RegisterButton.png"))));
        ImageButton loginBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Login.png"))));
        Image usernameTag = new Image(new Texture("tags/Email.png"));
        Image passwordTag = new Image(new Texture("tags/Password.png"));

        Gdx.input.setInputProcessor(stage);

        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        password.setSize(200, 40);
        password.setPosition(900, 695);

        email = new TextField("", skin);
        email.setSize(200, 40);
        email.setPosition(900, 775);

        passwordTag.setPosition(900, 735);
        usernameTag.setPosition(900, 815);

        registerBtn.setPosition(44, 142);
        loginBtn.setPosition(850, 875);

        stage.addActor(loginBtn);
        stage.addActor(registerBtn);
        stage.addActor(email);
        stage.addActor(password);
        stage.addActor(usernameTag);
        stage.addActor(passwordTag);

        registerBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new Register(gameMain));
            }
        });

        loginBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){

                String givenPassword = DatabaseHandler.getPasswordByEmail(email.getText());
                isEmailVerified = DatabaseHandler.getIsEmailVerified(email.getText());
                assert givenPassword != null;
                areCredentialsValid = givenPassword.equals(password.getText());

                if(isEmailVerified && areCredentialsValid) {
                    User user = new User(email.getText());
                    gameMain.setScreen(new MainMenu(gameMain, user));
                }
            }
        });
    }

    public Stage getStage() {
        return this.stage;
    }

    public boolean getAreCredentialsValid(){
        return this.areCredentialsValid;
    }

    public boolean getIsEmailVerified(){
        return this.isEmailVerified;
    }

    public void setIsEmailVerified(boolean isEmailVerified){
        this.isEmailVerified = isEmailVerified;
    }

    public void setAreCredentialsValid(boolean areCredentialsValid){
        this.areCredentialsValid = areCredentialsValid;
    }
}
