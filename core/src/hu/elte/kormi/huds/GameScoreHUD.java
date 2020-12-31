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

import hu.elte.kormi.database.User;
import hu.elte.kormi.scenes.MainMenu;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.tankharc.GameMain;

public class GameScoreHUD {

    private final Stage stage;

    public GameScoreHUD(final GameMain gameMain, final User user){

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());

        ImageButton mainMenuBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/MainMenu.png"))));
        mainMenuBtn.setPosition(450, 150);

        Gdx.input.setInputProcessor(stage);

        mainMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new MainMenu(gameMain, user));
            }
        });
        stage.addActor(mainMenuBtn);
    }

    public Stage getStage() {
        return this.stage;
    }
}
