package com.saharw.myfirstandenginegame.scene;

import com.saharw.myfirstandenginegame.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by Sahar on 12/05/2015.
 */
public class SplashScene extends BaseScene {

    private Sprite mSplash;

    @Override
    public void createScene() {
        mSplash = new Sprite(0, 0, resourcesManager.splashTextureRegion, vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither(); // increase the number of displayable colors
            }
        };
        mSplash.setScale(1.5f);
        mSplash.setPosition((camera.getWidth()) / 2, (camera.getHeight()) / 2);
        attachChild(mSplash);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene() {
        mSplash.detachSelf();
        mSplash.dispose();
        this.detachSelf();
        this.dispose();
    }
}
