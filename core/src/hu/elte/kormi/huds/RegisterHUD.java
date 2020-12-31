package hu.elte.kormi.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import hu.elte.kormi.scenes.Login;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.tankharc.GameMain;
import hu.elte.kormi.database.DatabaseHandler;

import java.util.ArrayList;

public class RegisterHUD {

    private final Stage stage;
    private final TextField username;
    private final TextField password;
    private final TextField email;
    private final TextField passwordAgain;

    private boolean isEmailInUse = false;
    private boolean isUsernameTooLong = false;
    private boolean isPasswordTooShort = false;
    private boolean isUsernameInfoClicked = false;
    private boolean isPasswordInfoClicked = false;
    private boolean arePasswordNotMatching = false;
    private boolean isUsernameContainsSpecialCharacter = false;

    public RegisterHUD(final GameMain gameMain){

        Skin skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());

        username = new TextField("", skin);
        email = new TextField("", skin);
        password = new TextField("", skin);
        passwordAgain = new TextField("", skin);
        Image usernameTag = new Image(new Texture("tags/Username.png"));
        Image passwordTag = new Image(new Texture("tags/Password.png"));
        Image passwordAgainTag = new Image(new Texture("tags/PasswordAgain.png"));
        Image emailTag = new Image(new Texture("tags/Email.png"));
        ImageButton registerBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RegisterButton.png"))));
        ImageButton backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Back.png"))));
        ImageButton usernameInfoBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("menu/Info.png"))));
        ImageButton passwordInfoBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("menu/Info.png"))));

        password.setPasswordMode(true);
        password.setPasswordCharacter('*');

        passwordAgain.setPasswordMode(true);
        passwordAgain.setPasswordCharacter('*');

        username.setSize(200, 40);
        email.setSize(200, 40);
        password.setSize(200,40);
        passwordAgain.setSize(200, 40);

        username.setPosition(900, 775);
        email.setPosition(900, 695);
        password.setPosition(900, 615);
        passwordAgain.setPosition(900, 535);

        usernameTag.setPosition(900, 815);
        emailTag.setPosition(900, 735);
        passwordTag.setPosition(900, 655);
        passwordAgainTag.setPosition(900, 575);

        usernameInfoBtn.setPosition(1110, 775);
        passwordInfoBtn.setPosition(1110, 615);

        registerBtn.setPosition(850, 875);
        backBtn.setPosition(50, 875);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(registerBtn);
        stage.addActor(backBtn);
        stage.addActor(username);
        stage.addActor(email);
        stage.addActor(password);
        stage.addActor(passwordAgain);
        stage.addActor(usernameTag);
        stage.addActor(passwordTag);
        stage.addActor(passwordAgainTag);
        stage.addActor(emailTag);
        stage.addActor(usernameInfoBtn);
        stage.addActor(passwordInfoBtn);

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new Login(gameMain));
            }
        });

        registerBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){

                ArrayList<String> emails = DatabaseHandler.getEmails();

                isUsernameContainsSpecialCharacter = !username.getText().matches("[a-zA-Z0-9]+");

                assert emails != null;
                isEmailInUse = emails.contains(email.getText());
                arePasswordNotMatching = !password.getText().equals(passwordAgain.getText());
                isUsernameTooLong = username.getText().length() > 10;
                isPasswordTooShort = password.getText().length() < 8;

                if(!isPasswordTooShort && !isEmailInUse && !isUsernameTooLong && !arePasswordNotMatching && !isUsernameContainsSpecialCharacter){
                    DatabaseHandler.registerUser(username.getText(), password.getText(), email.getText());
                    DatabaseHandler.giveFirstSkin(email.getText());
                    gameMain.setScreen(new Login(gameMain));
                }
            }
        });

        passwordInfoBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                isPasswordInfoClicked = !isPasswordInfoClicked;
                isUsernameInfoClicked = false;
            }
        });

        usernameInfoBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                isUsernameInfoClicked = !isUsernameInfoClicked;
                isPasswordInfoClicked = false;
            }
        });

    }

    public Stage getStage() {
        return this.stage;
    }

    public void setUsernameInfoClicked(boolean isUsernameInfoClicked){
        this.isUsernameInfoClicked = isUsernameInfoClicked;
    }

    public void setPasswordInfoClicked(boolean isPasswordInfoClicked){
        this.isPasswordInfoClicked = isPasswordInfoClicked;
    }

    public boolean getIsUsernameInfoClicked(){
        return this.isUsernameInfoClicked;
    }

    public boolean getIsPasswordInfoClicked(){
        return this.isPasswordInfoClicked;
    }

    public boolean getIsEmailInUse(){
        return this.isEmailInUse;
    }

    public boolean getIsPasswordTooShort(){
        return this.isPasswordTooShort;
    }

    public boolean getIsUsernameContainsSpecialCharacter(){
        return this.isUsernameContainsSpecialCharacter;
    }

    public boolean getIsUsernameTooLong(){
        return this.isUsernameTooLong;
    }

    public boolean getArePasswordNotMatching(){
        return this.arePasswordNotMatching;
    }

    public void setIsEmailInUse(boolean isEmailInUse){
        this.isEmailInUse = isEmailInUse;
    }

    public void setIsPasswordTooShort(boolean isPasswordTooShort){
        this.isPasswordTooShort = isPasswordTooShort;
    }

    public void setIsUsernameContainsSpecialCharacter(boolean isUsernameContainsSpecialCharacter){
        this.isUsernameContainsSpecialCharacter = isUsernameContainsSpecialCharacter;
    }

    public void setIsUsernameTooLong(boolean isUsernameTooLong){
        this.isUsernameTooLong = isUsernameTooLong;
    }

    public void setArePasswordNotMatching(boolean arePasswordNotMatching){
        this.arePasswordNotMatching = arePasswordNotMatching;
    }
}
