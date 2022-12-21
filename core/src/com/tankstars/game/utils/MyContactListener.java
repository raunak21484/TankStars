package com.tankstars.game.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {
        // Called when two fixtures cease to overlap
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Called before collision resolution
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Called after collision resolution
    }
}