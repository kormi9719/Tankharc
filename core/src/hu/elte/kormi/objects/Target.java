package hu.elte.kormi.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import hu.elte.kormi.helpers.GameInfo;

public class Target extends Sprite {

    private final float xPosition;
    private final float yPosition;

    private boolean isDestroyed = false;

    private final World world;

    public Target(World world, float xPosition, float yPosition, int id){
        super(new Texture("target/target.png"));
        this.world = world;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        createBody(id);
    }

    public void createBody(int id){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(xPosition / GameInfo.PPM , yPosition / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((getWidth() / 2 ) / GameInfo.PPM, (getHeight() / 2 ) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(id);

        polygonShape.dispose();
    }

    public float getXPosition(){
        return this.xPosition;
    }

    public float getYPosition(){
        return this.yPosition;
    }

    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}
