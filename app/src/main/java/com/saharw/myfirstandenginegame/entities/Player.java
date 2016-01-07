package com.saharw.myfirstandenginegame.entities;

import com.saharw.myfirstandenginegame.ResourceManager;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Sahar on 01/07/2016.
 */
public class Player extends AnimatedSprite {
    private final long[] mPlayerAnimate = new long[]{100, 100, 100, 100 ,100};

    public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld) {
        super(pX, pY, ResourceManager.getInstance().gameScenePlayerTextureRegion, vbo);
//        camera.setChaseEntity(this);

        // DEBUG !!!
        setWalking();
    }

    public void setWalking(){
        animate(mPlayerAnimate, 0, 4, true);
    }
}
