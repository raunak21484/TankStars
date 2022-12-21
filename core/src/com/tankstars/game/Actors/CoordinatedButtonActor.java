package com.tankstars.game.Actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tankstars.game.Actions.ImageAndTankAdjustmentAction;
import com.tankstars.game.GameObjects.Tank;

public class CoordinatedButtonActor extends ButtonActor{
    private float hitLocationX=250,hitlocationY=450;
    private Image image;
    private Tank tank;
    private float radius;
    public CoordinatedButtonActor(Tank tank, Image sector, String path, int width, int height, int x, int y) {
        super(path, width, height, x, y);
        this.radius = 250;
        this.image = sector;
        System.out.println("tank null? "+tank==null);
        this.tank = tank;

    }
    public void setTank(Tank tank){
        this.tank = tank;
    }
    @Override
    public void performAction(){
        float delY = hitlocationY-250;
        float delX = hitLocationX-250;
        tank.setShotPower((float)Math.sqrt(delX*delX + delY*delY)/radius);
        if(delX<0){
            //image.scaleBy((float)Math.sqrt(delX*delX + delY*delY)/radius);
            //image.setRotation((float) (Math.PI-Math.atan(delY/delX)* MathUtils.radiansToDegrees));
            tank.getMuzzle().setTransform(tank.getMuzzle().getPosition(),(float) (-Math.PI+Math.atan(delY/delX)));
        }else{
            //image.scaleBy((float)Math.sqrt(delX*delX + delY*delY)/radius);
            //image.setRotation((float)Math.atan(delY/delX)*MathUtils.radiansToDegrees);
            tank.getMuzzle().setTransform(tank.getMuzzle().getPosition(),(float)Math.atan(delY/delX));
        }
    }
    public void setHitLocation(float x, float y){
        this.hitLocationX = x;
        this.hitlocationY = y;
    }

}
