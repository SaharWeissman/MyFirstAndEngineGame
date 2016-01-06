package com.saharw.myfirstandenginegame.scene;

import android.util.Log;

import com.saharw.myfirstandenginegame.SceneManager;
import com.saharw.myfirstandenginegame.scene.base.BaseScene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by Sahar on 12/12/2015.
 */
public class MainMenuScene extends BaseScene implements MenuScene.IOnMenuItemClickListener {

    private MenuScene menuChildScene;
    private final String TAG = "MainMenuScene";
    private final int MENU_NEW_GAME = 0;
    private final int MENU_CONTINUE_GAME = 1;
    private final int MENU_OPTIONS = 2;
    private float MENU_BTN_PADDING_Y_AXIS = 75.0f;

    public MainMenuScene(){
        this.setTouchAreaBindingOnActionDownEnabled(true);
    }

    @Override
    public void createScene() {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        Log.d(TAG, "onBackKeyPressed - exiting..");
        System.exit(0);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {

    }

    private void createBackground() {
        attachChild(new Sprite(400, 240, resourcesManager.menuBckgdRegion, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }


    private void createMenuChildScene() {
        menuChildScene = new MenuScene(camera);
        menuChildScene.setPosition(0, 0);

        final IMenuItem newGameMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_NEW_GAME, resourcesManager.btnNewGameRegion, vbom), 1.2f, 1);
        final IMenuItem continueGameMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_CONTINUE_GAME, resourcesManager.btnContinueGameRegion, vbom), 1.2f, 1);
        final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.btnOptionsRegion, vbom), 1.2f, 1);

        menuChildScene.addMenuItem(newGameMenuItem);
        menuChildScene.addMenuItem(continueGameMenuItem);
        menuChildScene.addMenuItem(optionsMenuItem);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        // custom positioning of menu buttons
        newGameMenuItem.setPosition(newGameMenuItem.getX(), newGameMenuItem.getY() - (newGameMenuItem.getHeight()*0.25f));
        continueGameMenuItem.setPosition(newGameMenuItem.getX(), newGameMenuItem.getY() - newGameMenuItem.getHeight() - MENU_BTN_PADDING_Y_AXIS);
        optionsMenuItem.setPosition(continueGameMenuItem.getX(), continueGameMenuItem.getY() - continueGameMenuItem.getHeight() - MENU_BTN_PADDING_Y_AXIS);

        menuChildScene.setOnMenuItemClickListener(this);

        setChildScene(menuChildScene);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()){
            case MENU_NEW_GAME:{
                Log.d(TAG, "onMenuItemClicked: case MENU_NEW_GAME");
                SceneManager.getInstance().createGameScene();
                break;
            }
            case MENU_CONTINUE_GAME:{
                Log.d(TAG, "onMenuItemClicked: case MENU_CONTINUE_GAME");
                break;
            }
            case MENU_OPTIONS:{
                Log.d(TAG, "onMenuItemClicked: case MENU_OPTIONS");
                break;
            }
            default:{
                return false;
            }
        }
        return true;
    }
}
