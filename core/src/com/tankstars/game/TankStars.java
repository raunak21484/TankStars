package com.tankstars.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tankstars.game.Actors.ButtonActor;

import java.util.ArrayList;

public class TankStars extends ApplicationAdapter implements InputProcessor {

	private final int MAIN_MENU = 1;
	private final int LOADING_SCREEN = 0;
	private int currStage;
	StageCreator stageCreator;
	private ArrayList<Stage> stages;
	private Boolean bool;
	private Actor tempActor;
	private Vector2 coord;
	InputMultiplexer mux;
	@Override
	public void create () {
		mux	= new InputMultiplexer();
		mux.addProcessor(this);
		stageCreator = new StageCreator();
		stages = new ArrayList<>();
		stages.add(stageCreator.initLoadingScreen(mux));
		currStage = LOADING_SCREEN;
		bool = false;
		Gdx.input.setInputProcessor(mux);
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
						stages.add(stageCreator.initMainMenu(mux));
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

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		coord = this.stages.get(currStage).screenToStageCoordinates(new Vector2((float) screenX, (float) screenY));
		Actor hitActor = this.stages.get(currStage).hit(coord.x, coord.y, false);
		if(hitActor instanceof ButtonActor){
			ButtonActor button1 = (ButtonActor) hitActor;
			button1.setAction(button1.getButtonAction());
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
