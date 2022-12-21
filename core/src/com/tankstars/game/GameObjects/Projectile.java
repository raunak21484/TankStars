package com.tankstars.game.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.tankstars.game.TankStars;
import com.tankstars.game.utils.DataPacket;
import com.tankstars.game.utils.MutableInt;

import java.util.ArrayList;

public class Projectile{
    private Body projectile;
    private World world;
    private Sprite sprite;
    private MutableInt id;
    private TankStars tankStars;
    private Fixture fix;
    private DataPacket dataPacket;
    public static ArrayList<String> ProjectileTextures = new ArrayList<String>(){{add("PlayableGame/ProjectileAssets/b1.png");add("PlayableGame/ProjectileAssets/b2.png");add("PlayableGame/ProjectileAssets/b3.png");}};

    public Projectile(TankStars tankStars, World world, FixtureDef fixtureDef, MutableInt id,float posX, float posY, float velocityX, float velocityY){
        this.sprite = new Sprite(tankStars.getAssetManager().get(ProjectileTextures.get(id.val), Texture.class));
        this.tankStars = tankStars;
        this.world = world;
        this.id = id;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX,posY);
        CircleShape projectileShape = new CircleShape();
        projectileShape.setRadius(0.5f);
        sprite.setSize(2,0.5f);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        fixtureDef.shape = projectileShape;

        projectile = world.createBody(bodyDef);
        dataPacket = new DataPacket("Projectile");
        Fixture fix = projectile.createFixture(fixtureDef);
        fix.setUserData(dataPacket);
        projectile.setTransform(posX,posY,(float)Math.atan((velocityY/velocityX)));
        projectile.setLinearVelocity(velocityX,velocityY);



        projectileShape.dispose();
    }
    public Body getProjectile(){
        return this.projectile;
    }
    public void render(SpriteBatch batch){
        if(this.sprite==null){
            System.out.println("Projectile:: NULL");
            return;
        }
        this.sprite.setPosition(projectile.getPosition().x-sprite.getWidth()/2,projectile.getPosition().y-sprite.getHeight()/2);
        this.sprite.setRotation(projectile.getAngle()* MathUtils.radiansToDegrees);
        this.sprite.draw(batch);
    }
}
