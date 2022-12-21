package com.tankstars.game.Actions;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.tankstars.game.TankStars;

public class ScreenSwitchAction extends RunnableAction {
    private TankStars tankStars;
    private Screen screen;
    public ScreenSwitchAction(TankStars tankStars, Screen screen){
        this.tankStars=tankStars;
        this.screen = screen;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                ScreenSwitchAction.this.tankStars.setScreen(ScreenSwitchAction.this.screen);
            }
        });
    }

}
