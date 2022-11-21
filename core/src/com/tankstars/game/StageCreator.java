package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StageCreator {
    public Stage initLoadingScreen(Boolean bool, float timerTime){
        Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        return stage;
    }
}
