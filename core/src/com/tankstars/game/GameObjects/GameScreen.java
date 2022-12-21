package com.tankstars.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tankstars.game.TankStars;
import com.tankstars.game.utils.InputController;

public class GameScreen implements Screen {
    private Stage GameStage;
    private SpriteBatch batch;
    private Sprite sprite;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Camera camera;
    private AssetManager assetManager;
    private TankStars tankStars;
    private Tank tank;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    public GameScreen(Stage GameStage, TankStars tankStars){
        this.GameStage = GameStage;
        this.assetManager = tankStars.getAssetManager();
        this.tankStars = tankStars;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        sprite = new Sprite(assetManager.get("SelectionMenu/Choose.png",Texture.class));
        world = new World(new Vector2(0,-9.81f),true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/10f, Gdx.graphics.getHeight()/10f);

        BodyDef balldef = new BodyDef();
        balldef.type = BodyDef.BodyType.DynamicBody;
        balldef.position.set(0,1);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.75f;
        fixtureDef.restitution = 0.25f;
        world.createBody(balldef).createFixture(fixtureDef);

        BodyDef grounddef = new BodyDef();
        grounddef.type = BodyDef.BodyType.StaticBody;
        grounddef.position.set(0,-1);
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(new float[]{-camera.viewportWidth/2,-1,0,-1,40,10,500,1});
        fixtureDef.shape = chainShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        world.createBody(grounddef).createFixture(fixtureDef);
        FixtureDef chassisDef = new FixtureDef(), wheelDef = new FixtureDef();
        chassisDef.restitution = 0.5f;
        chassisDef.friction = 0.5f;
        chassisDef.density = 4;
        wheelDef.restitution = 0.2f;
        wheelDef.friction = 20;
        wheelDef.density = 10;
        tank = new Tank(world,chassisDef,wheelDef,2,10,5,2);
        tankStars.getMux().addProcessor(tank);
        tankStars.getMux().addProcessor(new InputController(){
        });

        chainShape.dispose();
        circleShape.dispose();
    }
    Vector2 tDelta=null;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world,camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        if(tDelta != null){
            tDelta.sub(tank.getLeftWheel().getLinearVelocity());
            System.out.println("Net Force on Left Wheel = "+new Vector2(tDelta.x*tank.getLeftWheel().getMass(),tDelta.y*tank.getLeftWheel().getMass()));
        }
        tDelta = new Vector2(tank.getLeftWheel().getLinearVelocity().x,tank.getLeftWheel().getLinearVelocity().y);
        camera.position.x = tank.getChassis().getPosition().x;
        camera.position.y = tank.getChassis().getPosition().y;
        camera.update();
        tank.cancelGravity();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/25;
        camera.viewportHeight = height/25;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        tankStars.getMux().removeProcessor(tankStars.getMux().size()-1);
        tankStars.getMux().removeProcessor(tankStars.getMux().size()-1);
    }

    @Override
    public void dispose() {

    }
    public void cancelGravity(Body body){
        //Vector2 NetForce = new Vector2(0,0);
         //Fixture fixture = body.getFixtureList().get(0);

    }
}
