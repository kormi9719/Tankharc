package hu.elte.kormi.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import hu.elte.kormi.helpers.GameInfo;

public class Bullet extends Sprite {

    private final float xPosition;
    private final float yPosition;
    private final World world;
    private Body body;

    public Bullet(World world, float xPosition, float yPosition){
        super(new Texture("weapons/Bullet.png"));
        this.world = world;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        createBody();
    }

    public void createBody(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPosition / GameInfo.PPM , yPosition / GameInfo.PPM);

        this.body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((getWidth() / 2 ) / GameInfo.PPM, (getHeight() / 2 ) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Bullet");
    }

    public void updateBullet(){
        this.setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }

    public Body getBody(){
        return this.body;
    }
}
