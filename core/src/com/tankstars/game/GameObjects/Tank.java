package com.tankstars.game.GameObjects;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.tankstars.game.TankStars;
import com.tankstars.game.utils.MutableInt;
import com.tankstars.game.utils.ObjectOutputStreeam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Tank implements InputProcessor, Serializable {

    private Body chassis, leftWheel, rightWheel, muzzle;
    private WheelJoint leftAxis, rightAxis, muzzleJoint;
    private float motorSpeed = 100;
    private float MotorPower = 1;
    private float fuel = 1,fuelPerc = 100;
    private float health = 1,healthPerc = 100;
    public static ArrayList<Float> MotorPowers = new ArrayList<Float>(){{add(0.6f);add(0.8f);add((1.2f));add(0.7f);}};
    public static ArrayList<Float> Fuels = new ArrayList<Float>(){{add(0.5f);add(0.8f);add(1.2f);add(0.9f);}};
    public static ArrayList<Float> Healths  = new ArrayList<Float>(){{add(1f);add(0.8f);add(0.5f);add(0.8f);}};
    public static ArrayList<String> TankTextures = new ArrayList<String>(){{add("PlayableGame/TankAssets/tank1_body.png");add("PlayableGame/TankAssets/tank2_body.png");add("PlayableGame/TankAssets/tank3_body.png");add("PlayableGame/TankAssets/tank4_body.png");}};
    public static ArrayList<String> MuzzleTextures = new ArrayList<String>(){{add("PlayableGame/TankAssets/tank1_nuzzle.png");add("PlayableGame/TankAssets/tank2_nuzzle.png");add("PlayableGame/TankAssets/tank3_nuzzle.png");add("PlayableGame/TankAssets/tank4_nuzzle.png");}};
    private boolean Controlling;
    private Sprite tankSprite, muzzleSprite;
    private World world;
    private MutableInt tankCode;
    private Projectile projectile;
    private TankStars tankStars;
    private FixtureDef chassisFixtureDef;
    private float ShotPower = 1;
    public Tank(TankStars tankStars , World world, FixtureDef chassisFixtureDef, FixtureDef wheelFixtureDef, float x, float y, float width, float height, MutableInt tankCode) {
        this.tankStars = tankStars;
        this.chassisFixtureDef = chassisFixtureDef;
        this.world = world;
        this.tankCode = tankCode;
        this.tankSprite = new Sprite(tankStars.getAssetManager().get(TankTextures.get(tankCode.val), Texture.class));
        this.muzzleSprite = new Sprite(tankStars.getAssetManager().get(MuzzleTextures.get(tankCode.val), Texture.class));
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angularDamping = 1f;
        // chassis
        PolygonShape chassisShape = new PolygonShape();
        chassisShape.set(new float[] {-width / 2, -height / 2, width / 2, -height / 2, width / 2 * .4f, height / 2, -width / 2 * .8f, height / 2 * .8f}); // counterclockwise order
        tankSprite.setSize(width*2,height*2);
        tankSprite.setOrigin(tankSprite.getWidth()/2,tankSprite.getHeight()/2);

        chassisFixtureDef.shape = chassisShape;

        chassis = world.createBody(bodyDef);
        chassis.createFixture(chassisFixtureDef);
        chassis.setUserData(tankSprite);
        //Muzzle
        PolygonShape muzzleShape = new PolygonShape();
        muzzleShape.setAsBox(width/5,height/6);
        BodyDef muzzleBodyDef = new BodyDef();
        muzzleBodyDef.type = BodyType.DynamicBody;
        muzzleBodyDef.position.set(x,y+width/6);
        muzzle = world.createBody(muzzleBodyDef);
        chassisFixtureDef.shape = muzzleShape;
        muzzle.createFixture(chassisFixtureDef);


        muzzleSprite.setSize(width,height*3);
        muzzleSprite.setOrigin(muzzleSprite.getWidth()/2,muzzleSprite.getHeight()/2);
        muzzle.setUserData(muzzleSprite);
        // left wheel
        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(height / 2.8f);

        wheelFixtureDef.shape = wheelShape;

        leftWheel = world.createBody(bodyDef);
        leftWheel.createFixture(wheelFixtureDef);

        // right wheel
        rightWheel = world.createBody(bodyDef);
        rightWheel.createFixture(wheelFixtureDef);

        // left axis
        WheelJointDef axisDef = new WheelJointDef();
        axisDef.bodyA = chassis;
        axisDef.bodyB = leftWheel;
        axisDef.localAnchorA.set(-width / 2 * .87f + wheelShape.getRadius(), -height / 2 * 1.25f);
        axisDef.frequencyHz = chassisFixtureDef.density;
        axisDef.localAxisA.set(Vector2.Y);
        axisDef.maxMotorTorque = chassisFixtureDef.density * 40;
        System.out.println("MaxMotorTorque = "+axisDef.maxMotorTorque);
        leftAxis = (WheelJoint) world.createJoint(axisDef);


        // right axis
        axisDef.bodyB = rightWheel;
        axisDef.localAnchorA.x *= -1;

        rightAxis = (WheelJoint) world.createJoint(axisDef);
        //muzzle joint
        WheelJointDef muzzleDef = new WheelJointDef();
        muzzleDef.bodyA = chassis;
        muzzleDef.bodyB = muzzle;
        muzzleDef.localAnchorA.set(0,height/5);
        muzzleDef.frequencyHz = chassisFixtureDef.density;
        muzzleDef.localAxisA.set(Vector2.X);
        muzzleDef.maxMotorTorque = 0;
        muzzleDef.collideConnected= false;

        muzzleJoint = (WheelJoint) world.createJoint(muzzleDef);
        wheelShape.dispose();
        chassisShape.dispose();
        muzzleShape.dispose();

    }
    public void fire(int id){
        System.out.println("Shooting with Shoit power = "+ShotPower);
        this.projectile = new Projectile(tankStars,world,chassisFixtureDef,new MutableInt(id),muzzle.getPosition().x+(float)Math.cos(muzzle.getAngle())*muzzleSprite.getWidth(),muzzle.getPosition().y+(float)Math.sin(muzzle.getAngle())*muzzleSprite.getHeight(),100*ShotPower*(float)Math.cos(muzzle.getAngle()),100*ShotPower*(float)Math.sin(muzzle.getAngle()));

    }
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("tank:: keydown");
        switch(keycode) {
            case Keys.W:
                this.setControlling(true);
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(-motorSpeed);
                rightAxis.enableMotor(true);
                rightAxis.setMotorSpeed(-motorSpeed);
                break;
            case Keys.S:
                this.setControlling(true);
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(motorSpeed*1.1f);
                rightAxis.enableMotor(true);
                rightAxis.setMotorSpeed(motorSpeed*1.1f);
                break;
            case Keys.A:
                chassis.applyAngularImpulse(100,true);
                break;
            case Keys.D:
                chassis.applyAngularImpulse(-100,true);
               // muzzle.setTransform(muzzle.getPosition(),muzzle.getAngle()-0.1f);
                break;

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("tank :: keyup");
        switch(keycode) {
            case Keys.W:
                this.setControlling(false);
                leftAxis.enableMotor(false);
                rightAxis.enableMotor(false);
            case Keys.S:
                this.setControlling(false);
                leftAxis.enableMotor(false);
                rightAxis.enableMotor(false);
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Body getChassis() {
        return chassis;
    }
    public Body getLeftWheel(){
        return this.leftWheel;
    }
    public Body getRightWheel(){
        return this.rightWheel;
    }
    int counter = 0;
    public void cancelGravity(){
        Vector2 gForce = new Vector2((float)(-Math.sin(this.getChassis().getAngle())*Math.cos(this.getChassis().getAngle())*world.getGravity().y)+(float)(-Math.sin(this.getChassis().getAngle())*Math.cos(this.getChassis().getAngle())*world.getGravity().x),(float)(-Math.sin(this.getChassis().getAngle())*Math.sin(this.getChassis().getAngle())*world.getGravity().y)+(float)(-Math.cos(this.getChassis().getAngle())*Math.cos(this.getChassis().getAngle())*world.getGravity().x));
        this.getChassis().applyForceToCenter(new Vector2(gForce.x*getChassis().getMass(),gForce.y*getChassis().getMass()),true);
        muzzle.setAngularVelocity(0f);
//        System.out.println("Force applying to chassis: "+new Vector2(gForce.x*getChassis().getMass(),gForce.y*getChassis().getMass()));
        float LRadius = getLeftWheel().getFixtureList().get(0).getShape().getRadius();
        float RRadius = getRightWheel().getFixtureList().get(0).getShape().getRadius();
        float cAngle = getChassis().getAngle();
        this.getLeftWheel().applyForce(new Vector2(gForce.x*1.02f*getLeftWheel().getMass()/2,gForce.y*1.02f*getLeftWheel().getMass()/2),new Vector2((float)(getLeftWheel().getPosition().x+LRadius*Math.sin(cAngle)),(float)(getLeftWheel().getPosition().y+ LRadius*Math.cos(cAngle))),true);
        this.getRightWheel().applyForce(new Vector2(gForce.x*1.02f*getRightWheel().getMass()/2,gForce.y*1.02f*getRightWheel().getMass()/2),new Vector2((float)(getRightWheel().getPosition().x+RRadius*Math.sin(cAngle)),(float)(getRightWheel().getPosition().y+RRadius*Math.cos(cAngle))),true);
        if(!this.isControlling()){
            getLeftWheel().setAngularVelocity(-cAngle*getLeftWheel().getMass()*0.01f);
            getRightWheel().setAngularVelocity(-cAngle*getRightWheel().getMass()*0.01f);
            //getChassis().applyAngularImpulse(-cAngle*chassis.getMass(),true);
        }
        float angle = (float)(((chassis.getAngle()+2*Math.PI)%(2*Math.PI))*MathUtils.radiansToDegrees);
        if(!((angle>=0 && angle <=120)||(angle>=250&&angle<=360))){
            counter++;
        }else{
            counter =0;
        }
        if(counter>250){
            counter = 0;
            this.healthPerc -= 20;
            chassis.setTransform(chassis.getPosition().x,chassis.getPosition().y+25,0);
            muzzle.setTransform(muzzle.getPosition().x,muzzle.getPosition().y+25,0);
            chassis.setAngularVelocity(0);
            muzzle.setAngularVelocity(0);
            getRightWheel().setAngularVelocity(0);
            getLeftWheel().setAngularVelocity(0);
            chassis.setLinearVelocity(Vector2.Zero);
            muzzle.setLinearVelocity(Vector2.Zero);
            System.out.println("TOPPLED! health: "+healthPerc);
        }


        //System.out.println("Angle "+cAngle);
        //System.out.println("Left wheel center: "+ new Vector2((float)(getLeftWheel().getPosition().x),(float)(getLeftWheel().getPosition().y)) +"Left wheel Point of contact coordinates: "+new Vector2((float)(getLeftWheel().getPosition().x+LRadius*Math.sin(cAngle)),(float)(getLeftWheel().getPosition().y- LRadius*Math.cos(cAngle))) + "Force applied: "+new Vector2(gForce.x*getLeftWheel().getMass(),gForce.y*getLeftWheel().getMass()));
    }
    public boolean isControlling() {
        return Controlling;
    }

    public void setControlling(boolean controlling) {
        Controlling = controlling;
    }
    public Tank setMotorPower(float motorPower){
        this.MotorPower = motorPower;
        return this;
    }
    public Tank setFuel(float fuel){
        this.fuel = fuel;
        return this;
    }
    public void render(SpriteBatch batch){
        if(this.tankSprite==null || this.muzzleSprite == null){
            System.out.println("TANK: null");return;}

        this.tankSprite.setPosition(chassis.getPosition().x - tankSprite.getWidth()/2,chassis.getPosition().y - tankSprite.getHeight()/2);
        //System.out.println("Chassis position: "+chassis.getPosition()+ "TankSprite: "+ new Vector2(tankSprite.getWidth(),tankSprite.getHeight()));
        this.tankSprite.setRotation(chassis.getAngle()* MathUtils.radiansToDegrees);
        this.muzzleSprite.setPosition(muzzle.getPosition().x- muzzleSprite.getWidth()/2,muzzle.getPosition().y - muzzleSprite.getHeight()/2);
        this.muzzleSprite.setRotation(muzzle.getAngle()* MathUtils.radiansToDegrees);

        this.tankSprite.draw(batch);
        this.muzzleSprite.draw(batch);

    }
    public Body getMuzzle(){
        return this.muzzle;
    }

    public float getShotPower() {
        return ShotPower;
    }

    public void setShotPower(float shotPower) {
        ShotPower = shotPower;
    }
    public void Serialize() throws IOException{
        ObjectOutputStreeam out = null;
        try{
            out = new ObjectOutputStreeam(new FileOutputStream("out.txt"));
            out.writeObject(this.motorSpeed);
            out.writeObject(this.chassis.getPosition());
            out.writeObject(this.chassis.getAngle());
            out.writeObject(this.muzzle.getPosition());
            out.writeObject(this.muzzle.getAngle());
            out.writeObject(this.MotorPower);
            out.writeObject(this.fuel);
            out.writeObject(this.health);
            out.writeObject(this.healthPerc);
        }catch(Exception e){

        }
    }
}