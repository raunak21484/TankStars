package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StageCreator {
    public Stage initLoadingScreen( float timerTime){
        Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Image img = new Image(new Sprite(new Texture(Gdx.files.internal("MainMenu/loadingscreen.png"))));
        img.setSize(1920,887);
        stage.addActor(img);
        return stage;
    }
    public Stage initMainMenu(){
        Stage stage = new Stage(new ScreenViewport());
        return stage;
    }
}
