package com.tankstars.game.Actions;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.tankstars.game.StageCreator;
import com.tankstars.game.utils.MutableInt;

public class StageSwitchAction extends RunnableAction {
    private int toSwitch;
    private MutableInt currStage;
    private StageCreator stageCreator;
    private InputMultiplexer mux;
    private boolean createNew;
    public StageSwitchAction(StageCreator stageCreator, InputMultiplexer mux, int toSwitch, MutableInt currstage, boolean createNew){
        this.toSwitch = toSwitch;
        this.currStage= currstage;
        this.stageCreator = stageCreator;
        this.mux = mux;
        setRunnable(new Runnable() {
            @Override
            public void run() {

                StageSwitchAction.this.currStage.val = StageSwitchAction.this.toSwitch;
                if(StageSwitchAction.this.createNew){
                StageSwitchAction.this.stageCreator.IndexedInit(StageSwitchAction.this.currStage.val,StageSwitchAction.this.mux);}
            }
        });
    }
}
