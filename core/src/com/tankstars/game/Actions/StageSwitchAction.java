package com.tankstars.game.Actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

public class StageSwitchAction extends RunnableAction {
    private int toSwitch;
    private Integer currStage;
    public StageSwitchAction(int toSwitch, Integer currstage){
        this.toSwitch = toSwitch;
        this.currStage= currstage;
        setRunnable(new Runnable() {
            @Override
            public void run() {
                StageSwitchAction.this.currStage = StageSwitchAction.this.toSwitch;
            }
        });
    }
}
