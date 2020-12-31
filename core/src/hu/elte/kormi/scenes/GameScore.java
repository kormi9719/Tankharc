package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import hu.elte.kormi.database.User;
import hu.elte.kormi.huds.GameScoreHUD;
import hu.elte.kormi.skins.CharacterHolder;
import hu.elte.kormi.tankharc.GameMain;


public class GameScore implements Screen {

    private final GameMain gameMain;
    private final GameScoreHUD gameScoreHUD;
    private final Texture background;
    private final String scoreString;

    public GameScore(GameMain gameMain, User user, int score){

        this.gameMain = gameMain;
        this.gameScoreHUD = new GameScoreHUD(gameMain, user);
        this.scoreString = Integer.toString(score);
        background = new Texture("backgrounds/ScoreBackground.png");
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

        for(int i = 0; i < scoreString.length(); i++) {
            gameMain.getSpriteBatch().draw(CharacterHolder.getCharacter(scoreString.charAt(i)), 475 + (i * 50), 650);
        }

        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(this.gameScoreHUD.getStage().getCamera().combined);
        this.gameScoreHUD.getStage().draw();

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
        gameScoreHUD.getStage().dispose();
    }
}