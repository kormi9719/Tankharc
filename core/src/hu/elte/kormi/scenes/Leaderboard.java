package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import hu.elte.kormi.database.Score;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.huds.LeaderboardHUD;
import hu.elte.kormi.skins.CharacterHolder;
import hu.elte.kormi.tankharc.GameMain;

import java.util.ArrayList;

public class Leaderboard implements Screen {

    private final GameMain gameMain;
    private final LeaderboardHUD leaderboardHUD;
    private final Texture background;
    private final Texture board = new Texture("leaderboards/Board.png");
    private final ArrayList<Texture> season;
    private final ArrayList<Texture> map;

    public Leaderboard(GameMain gameMain, User user){

        this.gameMain = gameMain;
        this.leaderboardHUD = new LeaderboardHUD(gameMain, user);
        background = new Texture("leaderboards/Background.png");
        season = new ArrayList<>();
        map = new ArrayList<>();

        for (int i = 0; i < GameInfo.SEASON; i++){
            season.add(new Texture("buttons/Season" + (i + 1) + ".png"));
        }

        for(int i = 0; i < 3; i++){
            map.add(new Texture("buttons/Map" + (i + 1) + ".png"));
        }
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
        gameMain.getSpriteBatch().draw(board, 0, 0);

        gameMain.getSpriteBatch().draw(season.get(leaderboardHUD.getSeason() - 1), 300, 1050);
        gameMain.getSpriteBatch().draw(map.get(leaderboardHUD.getMap() - 1), 300, 900);
        ArrayList<Score> scores = leaderboardHUD.getScores();


        for(int i = 0; i < scores.size(); i++){
            for(int j = 0; j < scores.get(i).getRank().length(); j++){
                gameMain.getSpriteBatch().draw(CharacterHolder.getCharacter(scores.get(i).getRank().charAt(j)), (j * 43) + 33, 848 - ((i + 1) * 82));
            }

            for(int j = 0; j < scores.get(i).getUsername().length(); j++){
                gameMain.getSpriteBatch().draw(CharacterHolder.getCharacter(scores.get(i).getUsername().charAt(j)), (j * 42) + 155,848 - ((i + 1) * 82));
            }

            for(int j = 0; j < scores.get(i).getScore().length(); j++){
                gameMain.getSpriteBatch().draw(CharacterHolder.getCharacter(scores.get(i).getScore().charAt(j)), (j * 43) + 609,848 - ((i + 1) * 82));
            }
        }

        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(leaderboardHUD.getStage().getCamera().combined);
        leaderboardHUD.getStage().draw();
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
        background.dispose();
        leaderboardHUD.getStage().dispose();
    }
}