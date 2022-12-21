package com.tankstars.game.Actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tankstars.game.GameObjects.Tank;

public class ImageAndTankAdjustmentAction extends RunnableAction {
    private Image image;
    private float delY,delX, radius;
    private Tank tank;
    public ImageAndTankAdjustmentAction(Tank tank, Image sector, float delX, float delY, float radius){
        this.image = sector;
        this.delX = delX;
        this.delY = delY;
        this.radius = radius;
        this.tank = tank;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                Image image = ImageAndTankAdjustmentAction.this.image;
                float delY = ImageAndTankAdjustmentAction.this.delY;
                float delX = ImageAndTankAdjustmentAction.this.delX;
                float radius = ImageAndTankAdjustmentAction.this.radius;
                Tank tank  = ImageAndTankAdjustmentAction.this.tank;

            }
        });
    }
}
