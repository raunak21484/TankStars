package com.tankstars.game.Actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tankstars.game.utils.Controller;

import java.util.ArrayList;

public class ImageAnimation extends Image implements Controller
{
    private Animation<TextureRegion> animation;
    private float time;
    public ArrayList<Float> BreakPoints;
    private boolean isRewinding,isFrameLimited;
    private float limitFrame;
    private float animationLength;

    protected float speed = 1f;
    protected boolean isPlayed =false;
    protected TextureRegionDrawable drawable = new TextureRegionDrawable();

    public ImageAnimation() {
        super();
        setDrawable(drawable);
        this.BreakPoints = new ArrayList<>();
        animationLength = animation.getKeyFrames().length/animation.getFrameDuration();
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public void setPose(TextureRegion textureRegion) {
        drawable.setRegion(textureRegion);
        setDrawable(drawable);
        invalidateHierarchy();
        setSize(getPrefWidth(), getPrefHeight());
        invalidate();
        this.animation = null;
    }

    @Override
    public void act(float delta)
    {
        time += delta * speed;
        if(animation != null && animation.getAnimationDuration() > 0&& isPlayed){
            TextureRegion frame = animation.getKeyFrame(time, true);
            drawable.setRegion(frame);
            setDrawable(drawable);
            invalidateHierarchy();
            invalidate();
        }else{
            // setDrawable(null);
        }
        super.act(delta);
    }
    public boolean checkFrame(float frame){

        if(this.isFrameLimited){
            if(this.isRewinding){
                if(frame%animationLength < this.limitFrame%animationLength){
                    return false;
                }else{return true;}
            }
            else{
                if(frame%animationLength > this.limitFrame%animationLength){
                    return false;
                }else{return true;}
            }
        }
        return true;
    }
    @Override
    public void pause() {
        this.isPlayed = false;
    }

    @Override
    public void play() {
        this.isPlayed = true;
        this.isFrameLimited = false;
    }

    @Override
    public void playTill(float frame) {
        limitFrame = frame;
    }

    @Override
    public void reverse(float frame) {
        limitFrame =frame;
        this.isRewinding = !this.isRewinding;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }
}