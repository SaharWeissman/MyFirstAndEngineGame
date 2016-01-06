package com.saharw.myfirstandenginegame.scene.base;

import android.app.Activity;

import com.saharw.myfirstandenginegame.ResourceManager;
import com.saharw.myfirstandenginegame.SceneManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Sahar on 11/14/2015.
 */
public abstract class BaseScene extends Scene
{
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------

    protected Engine engine;
    protected Activity activity;
    protected ResourceManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;

    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

    public BaseScene()
    {
        this.resourcesManager = ResourceManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        createScene();
    }

    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------

    public abstract void createScene();

    public abstract void onBackKeyPressed();

    public abstract SceneManager.SceneType getSceneType();

    public abstract void disposeScene();
}