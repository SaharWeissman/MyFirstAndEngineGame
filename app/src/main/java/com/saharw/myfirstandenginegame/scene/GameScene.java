package com.saharw.myfirstandenginegame.scene;

import android.util.Log;
import com.saharw.myfirstandenginegame.ResourceManager;
import com.saharw.myfirstandenginegame.SceneManager;
import com.saharw.myfirstandenginegame.entities.Player;
import com.saharw.myfirstandenginegame.scene.base.BaseScene;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by Sahar on 01/02/2016.
 */
public class GameScene extends BaseScene {

    private final String TAG = "GameScene";
    private final float BACKGROUND_Y_AXIS_OFFSET = 50f;

    private ITextureRegion hillTextureRegion, floorTextureRegion;
    private Sprite hillFar, hillMid, hillClose, floor;
    private ParallaxBackground parallaxBckgd;
    private Entity mForegroundLayer, mBackgroundLayer;
    private Sprite mRightController;
    private Player mPlayer;

    @Override
    public void createScene() {
        initTextures();
        initLayers();
        createBackground();
        createControllers();
        createPlayer();
        setBackground();
        attachLayers();
    }

    private void createPlayer() {
        mPlayer = new Player(50, 100, vbom, camera, null); //TODO: implement physics
        attachChild(mPlayer);
    }

    private void setBackground() {
        setBackground(parallaxBckgd);
        setBackgroundEnabled(true);
    }

    private void attachLayers() {
        attachChild(mBackgroundLayer);
        attachChild(mForegroundLayer);
    }

    private void initLayers() {
        mBackgroundLayer = new Entity();
        mForegroundLayer = new Entity();
    }

    private void createControllers() {
        mRightController = null;
        mRightController = new Sprite(710, 250, resourcesManager.gameRightControllerTextureRegion, engine.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionDown()){
                    camera.setCenter(pSceneTouchEvent.getX(), camera.getCenterY());
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0, mRightController));
        registerTouchArea(mRightController);
        setTouchAreaBindingOnActionDownEnabled(true);
    }

    private void initTextures() {
        ITextureRegion gameSceneBckgdTextureRegion = ResourceManager.getInstance().gameSceneBckgdTextureRegion;
        ITextureRegion gameSceneFloorTextureRegion = ResourceManager.getInstance().gameSceneFloorTextureRegion;

        // hills parallax background
        if(gameSceneBckgdTextureRegion != null){
            hillTextureRegion = gameSceneBckgdTextureRegion;
        }

        // floorTextureRegion
        if(gameSceneFloorTextureRegion != null){
            floorTextureRegion = gameSceneFloorTextureRegion;
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

        // get floor dimensions
        float floorHeight = floorTextureRegion.getHeight();
        float floorWidth = floorTextureRegion.getWidth();
        floor = new Sprite(floorWidth / 2, floorHeight / 2 , floorTextureRegion, engine.getVertexBufferObjectManager());

        // get hills dimensions
        float hillWidth = hillTextureRegion.getWidth();
        float hillHeight = hillTextureRegion.getHeight();

        hillFar = new Sprite(hillWidth * 0.5f, BACKGROUND_Y_AXIS_OFFSET + (hillHeight * 0.5f + 50), hillTextureRegion, engine.getVertexBufferObjectManager());
        hillMid = new Sprite(hillWidth * 0.5f, BACKGROUND_Y_AXIS_OFFSET + (hillHeight * 0.5f + 25), hillTextureRegion, engine.getVertexBufferObjectManager());
        hillClose = new Sprite(hillWidth * 0.5f, BACKGROUND_Y_AXIS_OFFSET + (hillHeight * 0.5f), hillTextureRegion, engine.getVertexBufferObjectManager());

        parallaxBckgd = new ParallaxBackground(1f, 1f, 1f){

            float cameraPrevX = 0f;
            float parallaxValueOffset = 0f;

            @Override
            public void onUpdate(float pSecondsElapsed) {
                final float cameraCurrX = camera.getCenterX();

                // if camera position changed since last update
                if(cameraPrevX != cameraCurrX){

                    // calculate offset
                    parallaxValueOffset -= cameraCurrX - cameraPrevX;
                    cameraPrevX = cameraCurrX; // update camera position
                    this.setParallaxValue(parallaxValueOffset); // tODO: need to attach floor to background in a different way, since attaching to parallax background cause player to go up y axis
                }
                super.onUpdate(pSecondsElapsed); // call super
            }
        };

        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(5, hillFar));
        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(10, hillMid));
        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(15, hillClose));
        parallaxBckgd.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0, floor));
    }
}
