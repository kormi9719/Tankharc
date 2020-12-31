package hu.elte.kormi.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


import hu.elte.kormi.database.DatabaseHandler;
import hu.elte.kormi.database.User;
import hu.elte.kormi.scenes.MainMenu;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.tankharc.GameMain;
import hu.elte.kormi.gambling.GamblingController;

import java.util.ArrayList;

public class GamblingHUD {

    private final Stage stage;
    private ArrayList<Integer> skinSequence = new ArrayList<>();

    public GamblingHUD(final GameMain gameMain, final User user){


        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());

        ImageButton backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Back.png"))));
        ImageButton gambleBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Gamble.png"))));

        gambleBtn.setPosition(300, 100);
        backBtn.setPosition(850, 1050);

        Gdx.input.setInputProcessor(stage);

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new MainMenu(gameMain, user));
            }
        });


        gambleBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                skinSequence = GamblingController.gamble();
                DatabaseHandler.insertGamblingResult(user.getEmail(), GameInfo.SEASON, skinSequence.get(skinSequence.size() - 1) + 1);
            }
        });

        stage.addActor(backBtn);
        stage.addActor(gambleBtn);

    }

    public ArrayList<Integer> getSkinSequence(){
        return this.skinSequence;
    }

    public void resetSkinSequence(){
        this.skinSequence = new ArrayList<>();
    }

    public Stage getStage() {
        return this.stage;
    }
}
