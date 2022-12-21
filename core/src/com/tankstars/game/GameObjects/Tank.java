package com.tankstars.game.GameObjects;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
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

import java.util.ArrayList;

public class Tank implements InputProcessor {

    private Body chassis, leftWheel, rightWheel, muzzle;
    private WheelJoint leftAxis, rightAxis, muzzleJoint;
    private float motorSpeed = 100;
    private float MotorPower = 1;
    private float fuel = 1,fuelPerc = 100;
    private float health = 1,healthPerc = 100;
    private static ArrayList<Float> MotorPowers = new ArrayList<Float>(){{add(0.6f);add(0.8f);add((1.2f));add(0.7f);}};
    private static ArrayList<Float> Fuels = new ArrayList<Float>(){{add(0.5f);add(0.8f);add(1.2f);add(0.9f);}};
    private static ArrayList<Float> Healths  = new ArrayList<Float>(){{add(1f);add(0.8f);add(0.5f);add(0.8f);}};
    private static ArrayList<String> TankTextures = new ArrayList<String>(){{add("PlayableGame/TankAssets/tank1_body.png");add("PlayableGame/TankAssets/tank2_body.png");add("PlayableGame/TankAssets/tank3_body.png");add("PlayableGame/TankAssets/tank4_body.png");}};
    private static ArrayList<String> MuzzleTextures = new ArrayList<String>(){{add("PlayableGame/TankAssets/tank1_nuzzle.png");add("PlayableGame/TankAssets/tank2_nuzzle.png");add("PlayableGame/TankAssets/tank3_nuzzle.png");add("PlayableGame/TankAssets/tank4_nuzzle.png");}};
    private boolean Controlling;
    private World world;

    public Tank(World world, FixtureDef chassisFixtureDef, FixtureDef wheelFixtureDef, float x, float y, float width, float height) {
        this.world = world;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angularDamping = 1f;
        // chassis
        PolygonShape chassisShape = new PolygonShape();
        chassisShape.set(new float[] {-width / 2, -height / 2, width / 2, -height / 2, width / 2 * .4f, height / 2, -width / 2 * .8f, height / 2 * .8f}); // counterclockwise order


        chassisFixtureDef.shape = chassisShape;

        chassis = world.createBody(bodyDef);
        chassis.createFixture(chassisFixtureDef);

        //Muzzle
        PolygonShape muzzleShape = new PolygonShape();
        muzzleShape.setAsBox(width/8,height/8);
        BodyDef muzzleBodyDef = new BodyDef();
        muzzleBodyDef.type = BodyType.KinematicBody;
        muzzleBodyDef.position.set(x+width/4,y+width/4);
        muzzle = world.createBody(muzzleBodyDef);
        chassisFixtureDef.shape = muzzleShape;
        muzzle.createFixture(chassisFixtureDef);
        // left wheel
        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(height / 3f);

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
        axisDef.localAnchorA.set(-width / 2 * .75f + wheelShape.getRadius(), -height / 2 * 1.25f);
        axisDef.frequencyHz = chassisFixtureDef.density;
        axisDef.localAxisA.set(Vector2.Y);
        axisDef.maxMotorTorque = chassisFixtureDef.density * 30;
        leftAxis = (WheelJoint) world.createJoint(axisDef);


        // right axis
        axisDef.bodyB = rightWheel;
        axisDef.localAnchorA.x *= -1;

        rightAxis = (WheelJoint) world.createJoint(axisDef);
        //muzzle joint
        WheelJointDef muzzleDef = new WheelJointDef();
        muzzleDef.bodyA = chassis;
        muzzleDef.bodyB = muzzle;
        muzzleDef.localAnchorA.set(width/2- muzzleShape.getRadius(),height/4);
        muzzleDef.frequencyHz = chassisFixtureDef.density;
        muzzleDef.localAxisA.set(Vector2.Y);
        muzzleDef.maxMotorTorque = 0;
        muzzleJoint = (WheelJoint) world.createJoint(muzzleDef);
        wheelShape.dispose();
        chassisShape.dispose();
        muzzleShape.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("tank:: keydown");
        switch(keycode) {
            case Keys.W:
                this.setControlling(true);
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(-motorSpeed);
                break;
            case Keys.S:
                this.setControlling(true);
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(motorSpeed);
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
            case Keys.S:
                this.setControlling(false);
                leftAxis.enableMotor(false);
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
    public void cancelGravity(){
        Vector2 gForce = new Vector2((float)(-Math.sin(this.getChassis().getAngle())*Math.cos(this.getChassis().getAngle())*world.getGravity().y),(float)(-Math.sin(this.getChassis().getAngle())*Math.sin(this.getChassis().getAngle())*world.getGravity().y));
        this.getChassis().applyForceToCenter(new Vector2(gForce.x*getChassis().getMass(),gForce.y*getChassis().getMass()),true);

//        System.out.println("Force applying to chassis: "+new Vector2(gForce.x*getChassis().getMass(),gForce.y*getChassis().getMass()));
        float LRadius = getLeftWheel().getFixtureList().get(0).getShape().getRadius();
        float RRadius = getRightWheel().getFixtureList().get(0).getShape().getRadius();
        float cAngle = getChassis().getAngle();
        this.getLeftWheel().applyForce(new Vector2(gForce.x*1.02f*getLeftWheel().getMass()/2,gForce.y*1.02f*getLeftWheel().getMass()/2),new Vector2((float)(getLeftWheel().getPosition().x+LRadius*Math.sin(cAngle)),(float)(getLeftWheel().getPosition().y+ LRadius*Math.cos(cAngle))),true);
        this.getRightWheel().applyForce(new Vector2(gForce.x*1.02f*getRightWheel().getMass()/2,gForce.y*1.02f*getRightWheel().getMass()/2),new Vector2((float)(getRightWheel().getPosition().x+RRadius*Math.sin(cAngle)),(float)(getRightWheel().getPosition().y+RRadius*Math.cos(cAngle))),true);
        if(!this.isControlling()){
            getLeftWheel().setAngularVelocity(-cAngle*getLeftWheel().getMass()*0.01f);
            getRightWheel().setAngularVelocity(-cAngle*getRightWheel().getMass()*0.01f);
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
}