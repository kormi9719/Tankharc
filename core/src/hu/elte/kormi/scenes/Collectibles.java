package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import hu.elte.kormi.database.DatabaseHandler;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.huds.CollectiblesHUD;
import hu.elte.kormi.skins.SkinHolder;
import hu.elte.kormi.tankharc.GameMain;

import java.util.ArrayList;

public class Collectibles implements Screen {

    private final GameMain gameMain;
    private final Texture background;
    private final ArrayList<Texture> gamblingSkins = new ArrayList<>();
    private final ArrayList<Texture> mappingSkins = new ArrayList<>();
    private final Texture m1Label;
    private final Texture m2Label;
    private final Texture m3Label;
    private final Texture g1Label;
    private final Texture g2Label;
    private final Texture g3Label;
    private final Texture season;
    private final CollectiblesHUD collectiblesHUD;
    private final User user;
    private ArrayList<Integer> gambledSkins;
    private ArrayList<Integer> mappedSkins;

    public Collectibles(GameMain gameMain, User user){

        SkinHolder skinHolder = new SkinHolder();
        ArrayList<ArrayList<Texture>> gambleSkins = skinHolder.getCollectiblesGamblingSkins();
        ArrayList<ArrayList<Texture>> mapSkins = skinHolder.getCollectiblesMapSkins();

        this.gameMain = gameMain;
        this.user = user;

        background = new Texture("collectibles/Background.png");
        m1Label = new Texture("buttons/Map1.png");
        m2Label = new Texture("buttons/Map2.png");
        m3Label = new Texture("buttons/Map3.png");
        g1Label = new Texture("buttons/Common.png");
        g2Label = new Texture("buttons/Rare.png");
        g3Label = new Texture("buttons/VeryRare.png");

        season = new Texture("buttons/Season1.png");

        Texture locked = new Texture("collectibles/Locked.png");

        int seasonNumber = GameInfo.SEASON;
        setGambledSkins(seasonNumber);
        setMappedSkins(seasonNumber);

        for(int i = 0; i < 3; i ++){
            if(gambledSkins.contains(i + 1)){
                gamblingSkins.add(gambleSkins.get(GameInfo.SEASON - 1).get(i));
            } else {
                gamblingSkins.add(locked);
            }
            if(mappedSkins.contains(i + 1)){
                mappingSkins.add(mapSkins.get(GameInfo.SEASON - 1).get(i));
            } else {
                mappingSkins.add(locked);
            }
        }
        collectiblesHUD = new CollectiblesHUD(gameMain, user);
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
        gameMain.getSpriteBatch().draw(season, 300, 1050);

        gameMain.getSpriteBatch().draw(g1Label, 75, 25);
        gameMain.getSpriteBatch().draw(g2Label, 450, 25);
        gameMain.getSpriteBatch().draw(g3Label, 825, 25);

        gameMain.getSpriteBatch().draw(gamblingSkins.get(0), 75, 175);
        gameMain.getSpriteBatch().draw(gamblingSkins.get(1), 450, 175);
        gameMain.getSpriteBatch().draw(gamblingSkins.get(2), 825, 175);

        gameMain.getSpriteBatch().draw(mappingSkins.get(0), 75,575);
        gameMain.getSpriteBatch().draw(mappingSkins.get(1), 450,575);
        gameMain.getSpriteBatch().draw(mappingSkins.get(2), 825,575);

        gameMain.getSpriteBatch().draw(m1Label, 75, 900);
        gameMain.getSpriteBatch().draw(m2Label, 450, 900);
        gameMain.getSpriteBatch().draw(m3Label, 825, 900);

        gameMain.getSpriteBatch().end();

        gameMain.getSpriteBatch().setProjectionMatrix(collectiblesHUD.getStage().getCamera().combined);
        collectiblesHUD.getStage().draw();
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
        season.dispose();
        m1Label.dispose();
        m2Label.dispose();
        m3Label.dispose();
        g1Label.dispose();
        g2Label.dispose();
        g3Label.dispose();
        collectiblesHUD.getStage().dispose();

    }

    public void setGambledSkins(int seasonNumber){
        this.gambledSkins = DatabaseHandler.getGambledSkins(user.getEmail(), seasonNumber);
    }

    public void setMappedSkins(int seasonNumber){
        this.mappedSkins = DatabaseHandler.getMappedSkins(user.getEmail(), seasonNumber);
    }
}