package com.saharw.myfirstandenginegame;

import com.saharw.myfirstandenginegame.scene.GameScene;
import com.saharw.myfirstandenginegame.scene.base.BaseScene;
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
    private BaseScene mGameScene;

    private SceneType mCurrSceneType;
    private Engine mEngine;


    private SceneManager(){
        mEngine = ResourceManager.getInstance().engine;
        mCurrSceneType = SceneType.SCENE_SPLASH;
    }

    public enum SceneType {
        SCENE_MENU,
        SCENE_SPLASH,
        SCENE_GAME
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
        if(mSplashScene != null) {
            mSplashScene.dispose(); // dispose scene entity itself
            mSplashScene = null;
        }
    }

    // menu scene
    public void createMenuScene(){
        ResourceManager.getInstance().loadMenuResources();
        mMenuScene = new MainMenuScene();
        setScene(mMenuScene);
        disposeSplashScene();
    }

    public void disposeMenuScene(){
        ResourceManager.getInstance().unloadMenuResources();
        mMenuScene.dispose();
        mMenuScene = null;
    }

    // game scene
    public void createGameScene(){
        //load resources
        ResourceManager.getInstance().loadGameSceneRes();
        mGameScene = new GameScene();
        setScene(mGameScene);
        disposeMenuScene();
    }

    public void disposeGameScene(){
        ResourceManager.getInstance().unloadGameSceneRes();
        mGameScene.dispose();
        mGameScene = null;
    }
}
