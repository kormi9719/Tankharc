package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.huds.GamblingHUD;
import hu.elte.kormi.skins.SkinHolder;
import hu.elte.kormi.tankharc.GameMain;

import java.util.ArrayList;

public class Gambling implements Screen {

    private final GameMain gameMain;
    private final Texture background;
    private final GamblingHUD gamblingHUD;
    private final ArrayList<ArrayList<Texture>> skins;
    private int counter = 0;
    private boolean isToDraw = false;
    private boolean isGambled = false;
    private Texture result = new Texture("gambling/empty.png");

    public Gambling(GameMain gameMain, User user){

        this.gameMain = gameMain;

        SkinHolder skinHolder = new SkinHolder();
        skins = skinHolder.getGamblingSkins();

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        orthographicCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        background = new Texture("backgrounds/GamblingBackground.png");
        gamblingHUD = new GamblingHUD(gameMain, user);
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

        if(isToDraw) {
            gameMain.getSpriteBatch().draw(result, 300, 390);
        }

        if (gamblingHUD.getSkinSequence().size() != 0 && counter < gamblingHUD.getSkinSequence().size()){
            if(!isGambled){
                result = skins.get(GameInfo.SEASON - 1).get(gamblingHUD.getSkinSequence().get(gamblingHUD.getSkinSequence().size() - 1));
                isGambled = true;
                isToDraw = false;
            }
            gameMain.getSpriteBatch().draw(skins.get(GameInfo.SEASON - 1).get(gamblingHUD.getSkinSequence().get(counter)), 300, 390);
            counter = counter + 1;
        }

        if(counter == gamblingHUD.getSkinSequence().size()){
            counter = 0;
            gamblingHUD.resetSkinSequence();
            isGambled = false;
            isToDraw = true;
        }
        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(gamblingHUD.getStage().getCamera().combined);
        gamblingHUD.getStage().draw();

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
        result.dispose();
        skins.get(GameInfo.SEASON - 1).get(counter).dispose();
        gamblingHUD.getStage().dispose();
    }
}
