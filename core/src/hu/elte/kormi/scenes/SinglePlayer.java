package hu.elte.kormi.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import hu.elte.kormi.database.DatabaseHandler;
import hu.elte.kormi.database.User;
import hu.elte.kormi.helpers.CustomInputProcessor;
import hu.elte.kormi.helpers.GameInfo;
import hu.elte.kormi.maps.GameMap;
import hu.elte.kormi.objects.Bullet;
import hu.elte.kormi.objects.SmallRock;
import hu.elte.kormi.objects.Target;
import hu.elte.kormi.player.Player;
import hu.elte.kormi.player.PlayerController;
import hu.elte.kormi.tankharc.GameMain;

import java.util.ArrayList;

public class SinglePlayer implements Screen, ContactListener {

    private final GameMain gameMain;
    private final Texture grass;
    private final Texture dirt;
    private final Texture water;
    private final Player player;
    private final World world;
    private final User user;
    private final Sound shootSound;
    private final Music engineNoise;
    private Bullet bullet;
    private boolean shotFired = false;
    private final int map;
    private int score = 99999;
    private int targetsRemaining;
    private ArrayList<Boolean> keys;
    private final CustomInputProcessor customInputProcessor;
    private final ArrayList<ArrayList<Integer>> terrain;
    private final ArrayList<SmallRock> smallRocks;
    private final ArrayList<Target> targets;

    private ArrayList<Fixture> fixturesToDestroy = new ArrayList<>();

    public SinglePlayer(GameMain gameMain, User user, int map, Texture tankTexture, Texture weaponTexture){

        this.gameMain = gameMain;
        GameMap gameMap = new GameMap(GameInfo.SEASON, map);
        this.terrain = gameMap.getTerrain();
        this.keys = new ArrayList<>();
        this.user = user;
        this.map = map;

        this.engineNoise = Gdx.audio.newMusic(Gdx.files.internal("sound/TankEngine.mp3"));
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sound/Shooting.mp3"));

        this.smallRocks = new ArrayList<>();
        this.targets = new ArrayList<>();

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(this);

        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        orthographicCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        grass = new Texture("terrain/grass.png");
        dirt = new Texture("terrain/dirt.png");
        water = new Texture("terrain/water.png");

        player = new Player(world, 70, 70 , 90, tankTexture, weaponTexture);

        for(int i = 0; i < gameMap.getRocks().size(); i++){
            for(int j = 0; j < gameMap.getRocks().get(i).size(); j++){
                if(gameMap.getRocks().get(i).get(j).equals(1)){
                    smallRocks.add(new SmallRock(world, 1200 - ((j + 1) * 50) + 25, (i * 50) + 25, smallRocks.size()));
                }
            }
        }

        for(int i = 0; i < gameMap.getTargets().size(); i++){
            for(int j = 0; j < gameMap.getTargets().get(i).size(); j++){
                if(gameMap.getTargets().get(i).get(j).equals(1)){
                    targets.add(new Target(world, 1200 - ((j + 1) * 50) + 25, (i * 50) + 25, targets.size()));

                }
            }
        }

        this.targetsRemaining = targets.size();

        engineNoise.setVolume(0.4f);
        engineNoise.setLooping(true);
        engineNoise.play();

        customInputProcessor = new CustomInputProcessor();
        Gdx.input.setInputProcessor(customInputProcessor);


    }

    @Override
    public void show() {

    }


    public void update(){

        keys = customInputProcessor.getKeysPressed();
        bullet = PlayerController.playerInput(world, keys, player, shotFired, terrain, shootSound);
        if(bullet != null){
            shotFired = true;
        }

        player.getWeapon().setXPosition(player.getTank().getX() - 37);
        player.getWeapon().setYPosition(player.getTank().getY() - 35);
    }



    @Override
    public void render(float delta) {

        update();

        if(targetsRemaining == 0){
            this.engineNoise.stop();
            dispose();
            DatabaseHandler.uploadPlayerScore(user, GameInfo.SEASON, map, score);
            gameMain.setScreen(new GameScore(gameMain, user, score));
        }

        score = score - 19;

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.getTank().updateTank();
        if(bullet != null){
            bullet.updateBullet();
        }

        gameMain.getSpriteBatch().begin();
        for(int i = 0; i < terrain.size(); i++){
            for(int j = 0; j < terrain.get(i).size(); j++){
                if(terrain.get(i).get(j).equals(1)){
                    gameMain.getSpriteBatch().draw(grass, j * 50, i * 50);
                }
                if(terrain.get(i).get(j).equals(2)){
                    gameMain.getSpriteBatch().draw(dirt, j * 50, i * 50);
                }
                if(terrain.get(i).get(j).equals(3)){
                    gameMain.getSpriteBatch().draw(water, j * 50, i * 50);
                }
            }
        }

        for (SmallRock smallRock : smallRocks) {
            gameMain.getSpriteBatch().draw(smallRock, smallRock.getXPosition() - (smallRock.getWidth() / 2), smallRock.getYPosition() - (smallRock.getHeight() / 2));
        }

        for (Target target : targets) {
            if(!target.isDestroyed()) {
                gameMain.getSpriteBatch().draw(target, target.getXPosition() - (target.getWidth() / 2), target.getYPosition() - (target.getHeight() / 2));
            }
        }

        gameMain.getSpriteBatch().draw(player.getTank(), player.getTank().getX() - (player.getTank().getWidth() / 2), player.getTank().getY() - (player.getTank().getHeight() / 2), player.getTank().getWidth() / 2, player.getTank().getHeight() / 2, player.getTank().getWidth(), player.getTank().getHeight(), 1, 1, player.getTank().getRotation() - 90);
        gameMain.getSpriteBatch().draw(player.getWeapon(), player.getWeapon().getXPosition(), player.getWeapon().getYPosition(), player.getWeapon().getWidth() / 2, player.getWeapon().getHeight() / 4, player.getWeapon().getWidth(), player.getWeapon().getHeight(), 1, 1, player.getWeapon().getAlpha());

        if(shotFired){
            if(bullet != null) {
                gameMain.getSpriteBatch().draw(bullet, bullet.getX() - (bullet.getWidth() / 2), bullet.getY() - (bullet.getHeight() / 2), bullet.getWidth() / 2, bullet.getHeight() / 2, bullet.getWidth(), bullet.getHeight(), 1, 1, player.getWeapon().getAlpha());
            }
        }

        gameMain.getSpriteBatch().end();
        world.step(Gdx.graphics.getDeltaTime(), 1, 1);

        for (Fixture fixture : fixturesToDestroy) {
            fixture.getBody().destroyFixture(fixture);
            bullet = null;
        }
        fixturesToDestroy = new ArrayList<>();
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
        engineNoise.dispose();
        dirt.dispose();
        water.dispose();
        grass.dispose();
    }

    @Override
    public void beginContact(Contact contact) {

        for(int i = 0; i < targets.size(); i++) {
            if ((contact.getFixtureB().getUserData() == "Bullet" && contact.getFixtureA().getUserData().equals(i)) || (contact.getFixtureA().getUserData() == "Bullet" && contact.getFixtureB().getUserData().equals(i))) {
                fixturesToDestroy.add(contact.getFixtureB());
                fixturesToDestroy.add(contact.getFixtureA());
                targets.get(i).setDestroyed(true);
                targetsRemaining = targetsRemaining - 1;
                shotFired = false;
            }
        }

        if(shotFired) {
            for (int i = 0; i < smallRocks.size(); i++) {
                if ((contact.getFixtureA().getUserData().equals((i * 100) + 100) && contact.getFixtureB().getUserData() == "Bullet") || (contact.getFixtureB().getUserData().equals((i * 100) + 100) && contact.getFixtureA().getUserData() == "Bullet")) {
                    fixturesToDestroy.add(contact.getFixtureB());
                    shotFired = false;
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
