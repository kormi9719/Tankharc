package hu.elte.kormi.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import hu.elte.kormi.helpers.GameInfo;

public class Tank extends Sprite{

    private final float xPosition;
    private final float yPosition;
    private float rotation;
    private final World world;
    private Body body;

    public Tank(World world, int xPosition, int yPosition, int rotation, Texture texture){

        super(texture);
        this.world = world;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotation = rotation;

        createBody();
    }

    public void createBody(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPosition / GameInfo.PPM , yPosition / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Tank");

        body.setFixedRotation(true);

        polygonShape.dispose();
    }

    public void updateTank(){
        this.setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }

    public Body getBody(){
        return this.body;
    }

    public float getRotation(){
        return this.rotation;
    }

    public void setRotation(float rotation){

        this.rotation = rotation;

        if(this.rotation == 360){
            this.rotation = 0;
        }

        if(this.rotation == -1){
            this.rotation = 359;
        }
    }

    public float getXPosition(){
        return this.xPosition;
    }

    public float getYPosition(){
        return this.yPosition;
    }
}