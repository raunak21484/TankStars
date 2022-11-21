package com.tankstars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class TankStars extends ApplicationAdapter {

	private final int MAIN_MENU = 1;
	private final int LOADING_SCREEN = 0;
	private int currStage;
	StageCreator stageCreator;
	private ArrayList<Stage> stages;
	private Boolean bool;
	private Actor tempActor;
	@Override
	public void create () {
		stageCreator = new StageCreator();
		stages = new ArrayList<>();
		stages.add(stageCreator.initLoadingScreen());
		currStage = LOADING_SCREEN;
		bool = false;
		Timer.schedule(new Timer.Task(){
			@Override
			public void run(){
				bool = true;
			}
		},3);

	}

	@Override
	public void render () {
		stages.get(currStage).act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stages.get(currStage).draw();
		switch(currStage){
			case LOADING_SCREEN:
				if(bool){
					tempActor =stages.get(currStage).getActors().first();
					tempActor.setColor(tempActor.getColor().r,tempActor.getColor().g,tempActor.getColor().b,tempActor.getColor().a-0.05f);
					if(tempActor.getColor().a<=0){
						stages.add(stageCreator.initMainMenu());
						currStage = MAIN_MENU;
					}
				}
				break;
			case MAIN_MENU:

				break;
		}
	}
	
	@Override
	public void dispose () {
		for(int i=0;i<stages.size();i++){
			stages.get(i).dispose();
		}
	}
}
