package hu.elte.kormi.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import hu.elte.kormi.objects.Bullet;

import java.util.ArrayList;

public class PlayerController {

    public static Bullet playerInput(World world, ArrayList<Boolean> keys, Player player, boolean shotFired, ArrayList<ArrayList<Integer>> terrain, Sound sound){

        for(int i = 0; i < keys.size(); i++){
            if(keys.get(0)){
                Vector2 v = calculateHeadingVector(player.getTank().getRotation(), true);
                player.getTank().getBody().setLinearVelocity(v.x / getTerrainEffect(player, terrain), v.y / getTerrainEffect(player, terrain));
            } else if (keys.get(1)){
                Vector2 v = calculateHeadingVector(player.getTank().getRotation(), false);
                player.getTank().getBody().setLinearVelocity(v.x / getTerrainEffect(player, terrain), v.y / getTerrainEffect(player, terrain));
            } else {
                player.getTank().getBody().setLinearVelocity(new Vector2(0, 0));
            }
            if(keys.get(2)){
                player.getTank().getBody().setAngularVelocity(1f);
                player.getTank().setRotation(player.getTank().getRotation() + 0.1363f);
                player.getWeapon().setAlpha(player.getWeapon().getAlpha() + 0.1363f);
            } else if (keys.get(3)){
                player.getTank().getBody().setAngularVelocity(-1f);
                player.getTank().setRotation(player.getTank().getRotation() - 0.1363f);
                player.getWeapon().setAlpha(player.getWeapon().getAlpha() - 0.1363f);
            } else {
                player.getTank().getBody().setAngularVelocity(0);
            }

            if(keys.get(4)){
                player.getWeapon().setAlpha(player.getWeapon().getAlpha() + 0.1363f);
            } else if (keys.get(5)){
                player.getWeapon().setAlpha(player.getWeapon().getAlpha() - 0.1363f);
            }

            if(keys.get(6)){
                if(!shotFired) {
                    sound.play(0.6f);
                    return shoot(world, player);
                }
            }
        }
        return null;
    }

    private static Bullet shoot(World world, Player player){

        Vector2 bhv = calculateBulletHeadingVector(player.getWeapon().getAlpha() + 90);
        Bullet bullet = new Bullet(world, player.getTank().getX() + (30 * bhv.x), player.getTank().getY() + (30 * bhv.y));
        bullet.getBody().setLinearVelocity(new Vector2(bhv.x * 8, bhv.y * 8));
        return bullet;
    }

    private static Vector2 calculateHeadingVector(float angle, boolean isForward){

        double radian = Math.toRadians(angle);
        if(isForward) {
            return new Vector2((float) Math.cos(radian), (float) Math.sin(radian));
        } else {
            return new Vector2((float) Math.cos(radian) * -1, (float) Math.sin(radian) * -1);
        }
    }

    private static Vector2 calculateBulletHeadingVector(float angle){
        double radian = Math.toRadians(angle);
        return new Vector2((float) Math.cos(radian) * 3, (float) Math.sin(radian) * 3);
    }

    public static int getTerrainEffect(Player player, ArrayList<ArrayList<Integer>> terrain){

        double x = Math.floor(player.getTank().getXPosition() / 50);
        int i = (int)x;

        double y = Math.floor(player.getTank().getYPosition() / 50);
        int j = (int)y;

        if(terrain.get(i).get(j) == 1){
            return 1;
        } else if(terrain.get(i).get(j) == 2){
            return 2;
        } else {
            return 3;
        }
    }
}
