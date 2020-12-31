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
import hu.elte.kormi.database.DatabaseHandler;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.scenes.MainMenu;
import hu.elte.kormi.scenes.SinglePlayer;
import hu.elte.kormi.skins.SkinInfo;
import hu.elte.kormi.tankharc.GameMain;

import java.net.*;
import java.util.ArrayList;


public class SinglePlayerMenuHUD {

    private final Stage stage;
    private int page;
    private int map;

    public SinglePlayerMenuHUD(final GameMain gameMain, final User user, final int availableSkins, final ArrayList<SkinInfo> skinInfo){

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());

        this.page = 0;
        this.map = 0;

        ImageButton backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Back.png"))));
        ImageButton readyBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Ready.png"))));
        ImageButton tankRightArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RightArrow.png"))));
        ImageButton tankLeftArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/LeftArrow.png"))));
        ImageButton mapRightArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RightArrow.png"))));
        ImageButton mapLeftArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/LeftArrow.png"))));

        readyBtn.setPosition(450, 1050);
        backBtn.setPosition(825, 1050);
        tankRightArrow.setPosition(800, 537);
        tankLeftArrow.setPosition(275, 537);
        mapLeftArrow.setPosition(275, 150);
        mapRightArrow.setPosition(800, 150);

        Gdx.input.setInputProcessor(stage);

        mapLeftArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                map = map - 1;
                if(map < 0){
                    map = 0;
                }
            }
        });

        mapRightArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                map = map + 1;
                if(map > 2){
                    map = 2;
                }
            }
        });

        tankRightArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page = page + 1;
                if(page == availableSkins){
                    page = 0;
                }
            }
        });

        tankLeftArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page = page - 1;
                if(page == -1){
                    page = availableSkins - 1;
                }
            }
        });

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new MainMenu(gameMain, user));
            }
        });

        readyBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameMain.setScreen(new SinglePlayer(gameMain, user, map + 1, new Texture("tanks/S" + skinInfo.get(page).getSeason() + skinInfo.get(page).getFlag() + skinInfo.get(page).getMap() + ".png"), new Texture("weapons/S" + skinInfo.get(page).getSeason() + skinInfo.get(page).getFlag() + skinInfo.get(page).getMap() + ".png")));

            }});

        stage.addActor(readyBtn);
        stage.addActor(backBtn);
        stage.addActor(tankRightArrow);
        stage.addActor(tankLeftArrow);
        stage.addActor(mapLeftArrow);
        stage.addActor(mapRightArrow);
    }

    public Stage getStage() {
        return this.stage;
    }

    public int getPage() {
        return this.page;
    }

    public int getMap() {
        return this.map;
    }
}
