package hu.elte.kormi.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.scenes.MainMenu;
import hu.elte.kormi.tankharc.GameMain;


public class CollectiblesHUD {

    private final Stage stage;
    private int page = GameInfo.SEASON - 1;

    public CollectiblesHUD(final GameMain gameMain, final User user){

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());

        ImageButton backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Back.png"))));
        ImageButton rightArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RightArrow.png"))));
        ImageButton leftArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/LeftArrow.png"))));

        backBtn.setPosition(825, 1050);
        rightArrow.setPosition(625, 1050);
        leftArrow.setPosition(150, 1050);

        Gdx.input.setInputProcessor(stage);

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new MainMenu(gameMain, user));
            }
        });

        rightArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page = page + 1;
                if(page > GameInfo.SEASON - 1){
                    page = GameInfo.SEASON - 1;
                }
            }
        });

        leftArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page = page - 1;
                if(page < 0){
                    page = 0;
                }
            }
        });



        stage.addActor(backBtn);
        stage.addActor(rightArrow);
        stage.addActor(leftArrow);
    }

    public Stage getStage() {
        return this.stage;
    }

    public int getPage(){ return this.page;}
}
