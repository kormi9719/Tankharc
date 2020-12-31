package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import hu.elte.kormi.database.DatabaseHandler;
import hu.elte.kormi.database.User;
import hu.elte.kormi.huds.SinglePlayerMenuHUD;
import hu.elte.kormi.skins.SkinInfo;
import hu.elte.kormi.tankharc.GameMain;

import java.util.ArrayList;

public class SinglePlayerMenu implements Screen {

    private final GameMain gameMain;
    private final Texture background;
    private final ArrayList<Texture> availableSkins = new ArrayList<>();
    private final ArrayList<Texture> map = new ArrayList<>();
    private final SinglePlayerMenuHUD singlePlayerMenuHUD;
    public SinglePlayerMenu(GameMain gameMain, User user){

        this.gameMain = gameMain;
        for(int i = 0; i < 3; i++){
            map.add(new Texture("buttons/Map" + (i + 1) + ".png"));
        }

        ArrayList<SkinInfo> skinInfo = DatabaseHandler.getAvailableSkins(user);

        for (SkinInfo info : skinInfo) {
            String path = "collectibles/S" + info.getSeason() + info.getFlag() + info.getMap() + ".png";
            availableSkins.add(new Texture(path));
        }

        this.singlePlayerMenuHUD= new SinglePlayerMenuHUD(gameMain, user, availableSkins.size(), skinInfo);
        this.background = new Texture("backgrounds/SinglePlayerMenuBackground.png");
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
        gameMain.getSpriteBatch().draw(availableSkins.get(singlePlayerMenuHUD.getPage()), 450, 450);
        gameMain.getSpriteBatch().draw(map.get(singlePlayerMenuHUD.getMap()), 450, 150);
        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(singlePlayerMenuHUD.getStage().getCamera().combined);
        singlePlayerMenuHUD.getStage().draw();
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
        singlePlayerMenuHUD.getStage().dispose();
    }
}