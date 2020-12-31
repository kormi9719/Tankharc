package hu.elte.kormi.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Actor;

import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.scenes.*;
import hu.elte.kormi.tankharc.GameMain;


public class MainMenuHUD {

    private final Stage stage;

    public MainMenuHUD(final GameMain gameMain, final User user){

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());

        Gdx.input.setInputProcessor(stage);

        ImageButton singlePlayerBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/SinglePlayer.png"))));
        ImageButton collectiblesBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Collectibles.png"))));
        final ImageButton gamblingBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Gambling.png"))));
        ImageButton leaderboardBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Leaderboard.png"))));
        ImageButton quitBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Quit.png"))));


        singlePlayerBtn.setPosition(500, 900);
        collectiblesBtn.setPosition(500, 700);
        gamblingBtn.setPosition(500, 500);
        leaderboardBtn.setPosition(500, 300);
        quitBtn.setPosition(500, 100);

        stage.addActor(singlePlayerBtn);
        stage.addActor(collectiblesBtn);
        stage.addActor(gamblingBtn);
        stage.addActor(leaderboardBtn);
        stage.addActor(quitBtn);


        singlePlayerBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new SinglePlayerMenu(gameMain, user));
                stage.dispose();
            }
        });


        collectiblesBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new Collectibles(gameMain, user));
                stage.dispose();
            }
        });


        gamblingBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new Gambling(gameMain, user));
                stage.dispose();
            }
        });


        leaderboardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameMain.setScreen(new Leaderboard(gameMain, user));
                stage.dispose();
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                stage.dispose();
                Gdx.app.exit();
                System.exit(0);
            }
        });
    }

    public Stage getStage() {
        return this.stage;
    }
}
