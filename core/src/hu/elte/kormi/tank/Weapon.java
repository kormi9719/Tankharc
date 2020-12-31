package hu.elte.kormi.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon extends Sprite {

    private float xPosition;
    private float yPosition;
    private float alpha;

    public Weapon(float xPosition, float yPosition, float rotation, Texture texture){
        super(texture);
        this.alpha = rotation;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void setXPosition(float xPosition){
        this.xPosition = xPosition;
    }

    public void setYPosition(float yPosition){
        this.yPosition = yPosition;
    }

    public float getXPosition(){
        return this.xPosition;
    }

    public float getYPosition(){
        return this.yPosition;
    }

    public float getAlpha(){
        return this.alpha;
    }

    public void setAlpha(float alpha){
        this.alpha = alpha;
    }
}