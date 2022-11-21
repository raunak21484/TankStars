package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class StageCreator {
    public Stage initLoadingScreen(){
        Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Image img = new Image(new Sprite(new Texture(Gdx.files.internal("MainMenu/loadingscreen.png"))));
        img.setSize(1920,887);
        stage.addActor(img);
        return stage;
    }
    public Stage initMainMenu(){
        Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Image logo = new Image(new Sprite(new Texture(Gdx.files.internal("MainMenu/logo.png"))));
        //logo.setScaling(new Scaling() {

        Image background = new Image(new Sprite(new Texture(Gdx.files.internal("badlogic.jpg"))));
        stage.addActor(background);
        stage.addActor(logo);

        return stage;
    }
}
