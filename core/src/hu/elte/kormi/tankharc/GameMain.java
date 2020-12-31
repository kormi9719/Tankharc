package hu.elte.kormi.tankharc;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hu.elte.kormi.gambling.GamblingController;
import hu.elte.kormi.scenes.Login;

public class GameMain extends Game {

    private SpriteBatch spriteBatch;

    @Override
    public void create () {
        GamblingController.gamble();
        spriteBatch = new SpriteBatch();
        setScreen(new Login(this));
    }

    @Override
    public void render () {
        super.render();
    }

    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }
}
