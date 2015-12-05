package com.saharw.myfirstandenginegame;

import com.saharw.myfirstandenginegame.scene.BaseScene;
import com.saharw.myfirstandenginegame.scene.SplashScene;

import org.andengine.ui.IGameInterface;

/**
 * Created by Sahar on 12/05/2015.
 */
public class SceneManager {

    private static SceneManager sInstance = null;

    //============== SCENES ====================
    private BaseScene currScene;

    private BaseScene splashScene;

    private SceneManager(){}

    public enum SceneType {
        SCENE_SPLASH
    }

    public static SceneManager getInstance(){
        if(sInstance == null){
            sInstance = new SceneManager();
        }
        return sInstance;
    }

    public BaseScene getCurrentScene(){
        return this.currScene;
    }

    public SceneType getCurrentSceneType(){
        return getCurrentScene().getSceneType();
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourceManager.getInstance().loadSplashSceneRes();
        splashScene = new SplashScene();
        currScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
}
