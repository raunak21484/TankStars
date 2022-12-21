package com.tankstars.game.GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class TankAuto {
    // Tank body and fixture
    private Body body;
    private Fixture fixture;

    // Muzzle body and fixture
    private Body muzzle;
    private Fixture muzzleFixture;

    // Left wheel body and fixture
    private Body leftWheel;
    private Fixture leftWheelFixture;

    // Right wheel body and fixture
    private Body rightWheel;
    private Fixture rightWheelFixture;

    // Tank dimensions
    private float width;
    private float height;

    // Muzzle dimensions
    private float muzzleWidth;
    private float muzzleHeight;

    // Wheel dimensions
    private float wheelRadius;

    // Distance between the center of the tank and the center of the muzzle
    private float muzzleOffset;

    // Placeholder for the chassis sprite
    private Sprite chassisSprite;

    // Placeholder for the muzzle sprite
    private Sprite muzzleSprite;

    // Placeholder for the left wheel sprite
    private Sprite leftWheelSprite;

    // Placeholder for the right wheel sprite
    private Sprite rightWheelSprite;

    // Constructor
    public TankAuto(World world, FixtureDef fixtureDef) {
        // Set tank dimensions
        width = 2.0f;
        height = 3.0f;

        // Set muzzle dimensions
        muzzleWidth = 0.5f;
        muzzleHeight = 0.5f;

        // Set wheel dimensions
        wheelRadius = 0.5f;

        // Set muzzle offset
        muzzleOffset = 1.0f;

        // Create tank body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;


        // Create tank fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        // Create muzzle body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;


        // Create muzzle fixture
        shape = new PolygonShape();
        shape.setAsBox(muzzleWidth / 2, muzzleHeight / 2);
        fixtureDef.shape = shape;
        muzzle = world.createBody(bodyDef);
        muzzle.createFixture(fixtureDef);
        shape.dispose();

        // Set muzzle position relative to tank
        muzzle.setTransform(body.getPosition().x + muzzleOffset, body.getPosition().y, 0);

        // Create a revolute joint between the tank and the muzzle
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(body, muzzle, body.getWorldCenter());
        world.createJoint(jointDef);

        // Create left wheel body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Create left wheel fixture
        shape = new PolygonShape();
        shape.setAsBox(wheelRadius, wheelRadius);
        fixtureDef.shape = shape;
        leftWheel = world.createBody(bodyDef);
        leftWheel.createFixture(fixtureDef);
        shape.dispose();

// Set left wheel position relative to tank
        leftWheel.setTransform(body.getPosition().x - width / 2, body.getPosition().y - height / 2, 0);

// Create right wheel body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;


// Create right wheel fixture
        shape = new PolygonShape();
        shape.setAsBox(wheelRadius, wheelRadius);
        fixtureDef.shape = shape;
        rightWheel = world.createBody(bodyDef);
        rightWheel.createFixture(fixtureDef);
        shape.dispose();

// Set right wheel position relative to tank
        rightWheel.setTransform(body.getPosition().x + width / 2, body.getPosition().y - height / 2, 0);

// Create a revolute joint between the tank and the left wheel
        jointDef = new RevoluteJointDef();
        jointDef.initialize(body, leftWheel, leftWheel.getWorldCenter());
        world.createJoint(jointDef);

// Create a revolute joint between the tank and the right wheel
        jointDef = new RevoluteJointDef();
        jointDef.initialize(body, rightWheel, rightWheel.getWorldCenter());
        world.createJoint(jointDef);

// Create placeholders for the sprites
        chassisSprite = new Sprite();
        muzzleSprite = new Sprite();
        leftWheelSprite = new Sprite();
        rightWheelSprite = new Sprite();
    }
    public void rotateMuzzle(float angle) {
        // Set the new angle of the revolute joint
        ((RevoluteJoint)muzzle.getJointList().first().joint).setLimits(angle, angle);
    }

    // Set the position of the tank
    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }

    // Update the sprites to match the positions of the bodies
    public void updateSprites() {
        // Set the position and rotation of the chassis sprite
        chassisSprite.setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
        chassisSprite.setRotation((float)Math.toDegrees(body.getAngle()));

        // Set the position and rotation of the muzzle sprite
        muzzleSprite.setPosition(muzzle.getPosition().x - muzzleWidth / 2, muzzle.getPosition().y - muzzleHeight / 2);
        muzzleSprite.setRotation((float)Math.toDegrees(muzzle.getAngle()));

        // Set the position and rotation of the left wheel sprite
        leftWheelSprite.setPosition(leftWheel.getPosition().x - wheelRadius, leftWheel.getPosition().y - wheelRadius);
        leftWheelSprite.setRotation((float)Math.toDegrees(leftWheel.getAngle()));

        // Set the position and rotation of the right wheel sprite
        rightWheelSprite.setPosition(rightWheel.getPosition().x - wheelRadius, rightWheel.getPosition().y - wheelRadius);
        rightWheelSprite.setRotation((float)Math.toDegrees(rightWheel.getAngle()));
    }

    // Getters for the sprites
    public Sprite getChassisSprite() { return chassisSprite; }
    public Sprite getMuzzleSprite() { return muzzleSprite; }
    public Sprite getLeftWheelSprite() { return leftWheelSprite; }
    public Sprite getRightWheelSprite() { return rightWheelSprite; }
}
