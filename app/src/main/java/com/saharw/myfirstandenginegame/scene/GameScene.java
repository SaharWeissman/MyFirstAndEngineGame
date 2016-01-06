package com.saharw.myfirstandenginegame.scene;

import android.util.Log;
import com.saharw.myfirstandenginegame.ResourceManager;
import com.saharw.myfirstandenginegame.SceneManager;
import com.saharw.myfirstandenginegame.scene.base.BaseScene;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by Sahar on 01/02/2016.
 */
public class GameScene extends BaseScene {

    private final String TAG = "GameScene";


    private float width = 200;
    private float height = 200;
    private ITextureRegion hillTextureRegion;
    private Sprite hillFar, hillMid, hillClose;
    private ParallaxBackground parallaxBckgd;

    @Override
    public void createScene() {
        initDimensions();
        createBackground();
        createControllers();
    }

    private void createControllers() {
        Sprite rightController = null;
        rightController = new Sprite(710, 250, resourcesManager.gameRightControllerTextureRegion, engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionDown()){
                    camera.setCenter(pSceneTouchEvent.getX(), camera.getCenterY());
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        attachChild(rightController);
        registerTouchArea(rightController);
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void initDimensions() {
        ITextureRegion gameSceneBckgdTextureRegion = ResourceManager.getInstance().gameSceneBckgdTextureRegion;
        if(gameSceneBckgdTextureRegion != null){
            width = gameSceneBckgdTextureRegion.getWidth();
            height = gameSceneBckgdTextureRegion.getHeight();
            hillTextureRegion = gameSceneBckgdTextureRegion;
        }
    }

    @Override
    public void onBackKeyPressed() {
        Log.d(TAG, "onBackKeyPressed");
        SceneManager.getInstance().createMenuScene();
        ((SmoothCamera)camera).setCenterDirect(400, 240);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {

    }


    private void createBackground(){
        hillFar = new Sprite(width * 0.5f, height * 0.5f + 50, hillTextureRegion, engine.getVertexBufferObjectManager());
        hillMid = new Sprite(width * 0.5f, height * 0.5f + 25, hillTextureRegion, engine.getVertexBufferObjectManager());
        hillClose = new Sprite(width * 0.5f, height * 0.5f, hillTextureRegion, engine.getVertexBufferObjectManager());

        parallaxBckgd = new ParallaxBackground(1f, 1f, 1f){

            float cameraPrevX = 0f;
            float parallaxValueOffset = 0f;

            @Override
            public void onUpdate(float pSecondsElapsed) {
                final float cameraCurrX = camera.getCenterX();

                // if camera position changed since last update
                if(cameraPrevX != cameraCurrX){

                    // calculate offset
                    parallaxValueOffset += cameraCurrX - cameraPrevX;
                    cameraPrevX = cameraCurrX; // update camera position
                    this.setParallaxValue(parallaxValueOffset);
                }
                super.onUpdate(pSecondsElapsed); // call super
            }
        };
        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(5, hillFar));
        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(10, hillMid));
        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(15, hillClose));
        setBackground(parallaxBckgd);
        setBackgroundEnabled(true);
    }



}
