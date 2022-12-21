package com.tankstars.game.GameObjects;

import com.badlogic.gdx.Gdx;
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
import com.tankstars.game.utils.MutableInt;
import com.tankstars.game.utils.ObjectOutputStreeam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class GameScreen implements Screen, Serializable {
    private Sprite Background,Terrain;
    private Stage GameStage;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Camera camera;
    private AssetManager assetManager;
    private TankStars tankStars;
    private Tank tank1,tank2;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    private MutableInt move = new MutableInt(1);
    public Tank getTank(){
        if(move.val == 1){
            return this.getTank1();
        }else{
            return this.getTank2();
        }
    }
    public GameScreen(Stage GameStage, TankStars tankStars){
        this.GameStage = GameStage;
        this.assetManager = tankStars.getAssetManager();
        this.tankStars = tankStars;
    }
    public void shoot(){
        Tank tank = getTank();

    }
    @Override
    public void show() {
        Background = new Sprite(tankStars.getAssetManager().get("GameScreen/theme1.png", Texture.class));

        Terrain = new Sprite(tankStars.getAssetManager().get("GameScreen/terrain.png", Texture.class));
        batch = new SpriteBatch();
        world = new World(new Vector2(0,-9.81f),true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/25f, Gdx.graphics.getHeight()/25f);
        Terrain.setSize(camera.viewportWidth,camera.viewportHeight*0.67f);
        Terrain.setPosition(camera.position.x -Terrain.getWidth()/2,camera.position.y - Terrain.getHeight());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2.5f;

        BodyDef grounddef = new BodyDef();
        grounddef.type = BodyDef.BodyType.StaticBody;
        grounddef.position.set(0,-1);
        ChainShape chainShape = new ChainShape();

        chainShape.createChain(new float[]{-camera.viewportWidth/2,camera.position.y+10f, -camera.viewportWidth/2,camera.position.y-2.5f, -camera.viewportWidth/2+6.5f,camera.position.y-2.5f, -camera.viewportWidth/2+6.70f,camera.position.y-2.53f, -camera.viewportWidth/2+6.85f,camera.position.y-2.6f, -camera.viewportWidth/2+7f,camera.position.y-2.7f, -camera.viewportWidth/2+10.2f,camera.position.y-10f, -camera.viewportWidth/2+11f,camera.position.y-10.4f, -camera.viewportWidth/2+11.5f,camera.position.y-10.5f, -camera.viewportWidth/2+20.6f,camera.position.y-10.6f, -camera.viewportWidth/2+20.8f,camera.position.y-10.5f, -camera.viewportWidth/2+23.6f,camera.position.y-7.3f, -camera.viewportWidth/2+30.2f,camera.position.y-6.8f, -camera.viewportWidth/2+39.5f,camera.position.y-7.3f, -camera.viewportWidth/2+40.2f,camera.position.y-8.33f, -camera.viewportWidth/2+42.6f,camera.position.y-14f, -camera.viewportWidth/2+44f,camera.position.y-14.9f, -camera.viewportWidth/2+53.8f,camera.position.y-14.9f, -camera.viewportWidth/2+54.8f,camera.position.y-13.5f, -camera.viewportWidth/2+56.8f,camera.position.y-8.4f, -camera.viewportWidth/2+71.5f,camera.position.y-7f, -camera.viewportWidth/2+73.7f,camera.position.y-8.05f, -camera.viewportWidth/2+74.7f,camera.position.y-8.05f, -camera.viewportWidth/2+75f,camera.position.y-7.8f, -camera.viewportWidth/2+76.3f,camera.position.y-4f, -camera.viewportWidth/2+76.3f,camera.position.y+10f,});
        fixtureDef.shape = chainShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        world.createBody(grounddef).createFixture(fixtureDef);
        FixtureDef chassisDef = new FixtureDef(), wheelDef = new FixtureDef();
        chassisDef.restitution = 0.5f;
        chassisDef.friction = 0.5f;
        chassisDef.density = 8;
        wheelDef.restitution = 0.01f;
        wheelDef.friction = 20;
        wheelDef.density = 18;
        tank1 = new Tank(tankStars, world,chassisDef,wheelDef,-15,6,5,2f, new MutableInt(1));
        tank2 = new Tank(tankStars, world,chassisDef,wheelDef,15,6,5,2f, new MutableInt(3));
        tankStars.getStageCreator().getShootCircle().setTank(this.getTank());
        tankStars.getMux().addProcessor(tank1);
        tankStars.getMux().addProcessor(new InputController(){
        });
        chainShape.dispose();

    }
    Vector2 tDelta=null;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        if(tDelta != null){
            tDelta.sub(tank1.getLeftWheel().getLinearVelocity());
            //System.out.println("Net Force on Left Wheel = "+new Vector2(tDelta.x*tank.getLeftWheel().getMass(),tDelta.y*tank.getLeftWheel().getMass()));
        }
        tDelta = new Vector2(tank1.getLeftWheel().getLinearVelocity().x, tank1.getLeftWheel().getLinearVelocity().y);
//        camera.position.x = (tank1.getChassis().getPosition().x+tank2.getChassis().getPosition().x)/2;
//        camera.position.y = (tank1.getChassis().getPosition().y+tank2.getChassis().getPosition().y)/2;
        camera.update();
        tank1.cancelGravity();
        tank2.cancelGravity();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //System.out.println("Camera viewportWidth: "+camera.viewportWidth);
        Background.setSize(camera.viewportWidth,camera.viewportHeight);
        Background.setPosition(camera.position.x-Background.getWidth()/2,camera.position.y-Background.getHeight()/2);
        Background.draw(batch);

        Terrain.draw(batch);
        tank1.render(batch);
        tank2.render(batch);
        batch.end();
        debugRenderer.render(world,camera.combined);
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
    public void fire(int id){
        this.getTank().fire(id);
        if(move.val == 1){
            move.val =2;
        }else{
            move.val = 1;
        }
        tankStars.getStageCreator().getShootCircle().setTank(getTank());
        tankStars.getMux().removeProcessor(tankStars.getMux().size()-1);
        tankStars.getMux().removeProcessor(tankStars.getMux().size()-1);
        tankStars.getMux().addProcessor(getTank());
        tankStars.getMux().addProcessor(new InputController());
    }
    @Override
    public void dispose() {

    }
    public void cancelGravity(Body body){
        //Vector2 NetForce = new Vector2(0,0);
         //Fixture fixture = body.getFixtureList().get(0);

    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Tank getTank1() {
        return tank1;
    }
    public Tank getTank2(){
        return tank2;
    }
    public void setTank1(Tank tank1) {
        this.tank1 = tank1;
    }

    public Stage getGameStage() {
        return GameStage;
    }

    public void setGameStage(Stage gameStage) {
        GameStage = gameStage;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setTank2(Tank tank2) {
        this.tank2 = tank2;
    }

    public MutableInt getMove() {
        return move;
    }

    public void setMove(MutableInt move) {
        this.move = move;
    }
    public void Serialize() throws IOException {
        ObjectOutputStreeam out = null;
        try{
            out = new ObjectOutputStreeam(new FileOutputStream("out.txt"));
            out.writeObject(this.tank1);
            out.writeObject(this.tank2);
        }catch(Exception e){

        }
    }
}
