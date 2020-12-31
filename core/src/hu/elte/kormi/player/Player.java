package hu.elte.kormi.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import hu.elte.kormi.tank.Tank;
import hu.elte.kormi.tank.Weapon;

public class Player {

    private final Tank tank;
    private final Weapon weapon;

    public Player(World world, int xPos, int yPos, int rotation, Texture tankTexture, Texture weaponTexture){

        tank = new Tank(world, xPos, yPos, rotation, tankTexture);
        weapon = new Weapon(tank.getXPosition() - 37, tank.getYPosition() - 35, rotation - 90, weaponTexture);
    }

    public Tank getTank(){
        return this.tank;
    }

    public Weapon getWeapon(){
        return this.weapon;
    }
}
