package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tankstars.game.Actions.SoundAction;
import com.tankstars.game.Actors.ButtonActor;

public class StageCreator {
    public Stage initLoadingScreen(InputMultiplexer mux){
        Stage stage = new Stage(new ScreenViewport());
        mux.removeProcessor(mux.size()-1);
        mux.addProcessor(stage);
        Image img = new Image(new Sprite(new Texture(Gdx.files.internal("MainMenu/loadingscreen.png"))));
        img.setSize(1920,887);
        stage.addActor(img);
        return stage;
    }
    public Stage initMainMenu(InputMultiplexer mux){
        mux.removeProcessor(mux.size()-1);
        Stage stage = new Stage(new ScreenViewport());
        mux.addProcessor(stage);
        //Image logo = new Image(new Sprite(new Texture(Gdx.files.internal("MainMenu/logo.png"))));
        ButtonActor logo = new ButtonActor("MainMenu/logo.png",150,100,100,50);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("tempsound1.mp3"));
        logo.setAction(new SoundAction(sound));
        //logo.setScaling(new Scaling() {

        Image background = new Image(new Sprite(new Texture(Gdx.files.internal("badlogic.jpg"))));
        stage.addActor(background);
        stage.addActor(logo);

        return stage;
    }
}
