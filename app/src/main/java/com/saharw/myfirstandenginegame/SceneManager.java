package com.saharw.myfirstandenginegame;

import com.saharw.myfirstandenginegame.scene.BaseScene;
import com.saharw.myfirstandenginegame.scene.MainMenuScene;
import com.saharw.myfirstandenginegame.scene.SplashScene;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface;

/**
 * Created by Sahar on 12/05/2015.
 */
public class SceneManager {

    private static SceneManager sInstance = null;

    //============== SCENES ====================
    private BaseScene mCurrScene;

    private BaseScene mSplashScene;
    private BaseScene mMenuScene;

    private SceneType mCurrSceneType;
    private Engine mEngine;


    private SceneManager(){
        mEngine = ResourceManager.getInstance().engine;
        mCurrSceneType = SceneType.SCENE_SPLASH;
    }

    public enum SceneType {
        SCENE_MENU, SCENE_SPLASH
    }

    public static SceneManager getInstance(){
        if(sInstance == null){
            sInstance = new SceneManager();
        }
        return sInstance;
    }

    public void setScene(BaseScene scene)
    {
        mEngine.setScene(scene);
        mCurrScene = scene;
        mCurrSceneType = scene.getSceneType();
    }

    public BaseScene getCurrentScene(){
        return this.mCurrScene;
    }

    public SceneType getCurrentSceneType(){
        return getCurrentScene().getSceneType();
    }

//========== Create & Dispose Scenes =======================
    // splash scene
    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourceManager.getInstance().loadSplashSceneRes();
        mSplashScene = new SplashScene();
        mCurrScene = mSplashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
    }

    public void disposeSplashScene(){
        ResourceManager.getInstance().unloadSplashSceneRes();
        mSplashScene.dispose(); // dispose scene entity itself
        mSplashScene = null;
    }

    // menu scene
    public void createMenuScene(){
        ResourceManager.getInstance().loadMenuResources();
        mMenuScene = new MainMenuScene();
        //TODO: add loading scene
        setScene(mMenuScene);
        disposeSplashScene();
    }
}
