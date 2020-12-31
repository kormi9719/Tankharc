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
import hu.elte.kormi.database.Score;
import hu.elte.kormi.scenes.MainMenu;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.tankharc.GameMain;
import hu.elte.kormi.database.DatabaseHandler;

import java.util.ArrayList;

public class LeaderboardHUD {

    private final Stage stage;
    private int season;
    private int map;
    private ArrayList<Score> scores;

    public LeaderboardHUD(final GameMain gameMain, final User user){

        season = 1;
        map = 1;

        Viewport viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, gameMain.getSpriteBatch());
        scores = DatabaseHandler.getLeaderboardScores(season, map);

        ImageButton backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/Back.png"))));
        ImageButton seasonRightArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RightArrow.png"))));
        ImageButton seasonLeftArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/LeftArrow.png"))));
        ImageButton mapRightArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/RightArrow.png"))));
        ImageButton mapLeftArrow = new ImageButton(new SpriteDrawable(new Sprite(new Texture("buttons/LeftArrow.png"))));

        seasonRightArrow.setPosition(625, 1050);
        seasonLeftArrow.setPosition(150, 1050);
        mapLeftArrow.setPosition(150, 900);
        mapRightArrow.setPosition(625, 900);
        backBtn.setPosition(850, 1050);

        Gdx.input.setInputProcessor(stage);

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameMain.setScreen(new MainMenu(gameMain, user));
            }
        });

        seasonRightArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                season = season + 1;
                if (season > GameInfo.SEASON){
                    season = GameInfo.SEASON;
                } else {
                    scores = DatabaseHandler.getLeaderboardScores(season, map);
                }
            }
        });

        seasonLeftArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                season = season - 1;
                if (season < 1){
                    season = 1;
                } else {
                    scores = DatabaseHandler.getLeaderboardScores(season, map);
                }
            }
        });

        mapRightArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                map = map + 1;
                if (map > 3){
                    map = 3;
                } else {
                    scores = DatabaseHandler.getLeaderboardScores(season, map);
                }
            }
        });

        mapLeftArrow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                map = map - 1;
                if (map < 1){
                    map = 1;
                } else {
                    scores = DatabaseHandler.getLeaderboardScores(season, map);
                }
            }
        });

        stage.addActor(backBtn);
        stage.addActor(seasonRightArrow);
        stage.addActor(seasonLeftArrow);
        stage.addActor(mapLeftArrow);
        stage.addActor(mapRightArrow);
    }


    public Stage getStage() {
        return this.stage;
    }

    public int getSeason(){
        return this.season;
    }

    public int getMap(){
        return this.map;
    }

    public ArrayList<Score> getScores(){
        return this.scores;
    }
}
