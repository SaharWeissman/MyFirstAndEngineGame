package com.saharw.myfirstandenginegame;

import android.graphics.Color;
import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

/**
 * Created by Sahar on 12/05/2015.
 */
public class ResourceManager {

    private static final String GFX_BASE_DIR = "gfx/";
    private static final String GFX_MENU_DIR = "menu/";
    private static final String GFX_GAME_DIR = "game/";
    private String FONT_BASE_DIR = "font/";

    private static final int SPLASH_BITMAP_TEXTURE_ATLAS_SIDE_SIZE = 256;
    private static final int MENU_BITMAP_TEXTURE_ATLAS_SIDE_SIZE = 1024;

    private static ResourceManager sInstance = null;

    private final String TAG = "ResourceManager";

    public Engine engine;
    public BaseGameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    // ==========  Bitmap Texture Atlases & Texture Regions =======
    // splash scene
    public BitmapTextureAtlas splashSceneTextureAtlas;
    public ITextureRegion splashTextureRegion;

    // menu scene
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    public ITextureRegion menuBckgdRegion;
    public Font menuFont;
    public ITextureRegion btnNewGameRegion;
    public ITextureRegion btnContinueGameRegion;
    public ITextureRegion btnOptionsRegion;

    // game scene
    private BuildableBitmapTextureAtlas gameSceneBackgdTextureAtlas;
    public ITextureRegion gameSceneBckgdTextureRegion;
    public ITextureRegion gameRightControllerTextureRegion;
    public ITextureRegion gameSceneFloorTextureRegion;
    public ITiledTextureRegion gameScenePlayerTextureRegion;

    private ResourceManager(){}

    public static ResourceManager getInstance(){
        if(sInstance == null){
            sInstance = new ResourceManager();
        }
        return sInstance;
    }

    public static void init(Engine engine, BaseGameActivity activity, Camera camera, VertexBufferObjectManager vbom){
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

//===================== load & unload scenes resources ====================
    // splash scene
    public void loadSplashSceneRes() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GFX_BASE_DIR);
        splashSceneTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), SPLASH_BITMAP_TEXTURE_ATLAS_SIDE_SIZE,
                SPLASH_BITMAP_TEXTURE_ATLAS_SIDE_SIZE, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashSceneTextureAtlas, activity, "splash.png", 0, 0);
        splashSceneTextureAtlas.load();
    }

    public void unloadSplashSceneRes(){
        splashSceneTextureAtlas.unload();
        splashTextureRegion = null;
    }

    // menu scene
    public void loadMenuResources(){
        loadMenuGfx();
        loadMenuAudio();
        loadMenuFonts();
    }

    public void unloadMenuResources(){
        unloadMenuGfx();
        unloadMenuAudio();
        unloadMenuFonts();
    }

    // load game scene
    public void loadGameSceneRes() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GFX_BASE_DIR + GFX_GAME_DIR);

        // background
        gameSceneBackgdTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
        gameSceneBckgdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneBackgdTextureAtlas, activity, "hill.png");
        gameRightControllerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneBackgdTextureAtlas, activity, "right_arrow.png");
        gameSceneFloorTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameSceneBackgdTextureAtlas, activity, "floor.png");


        // game character
        gameScenePlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameSceneBackgdTextureAtlas, activity, "player_walking_animate.png", 5, 1);

        try {
            gameSceneBackgdTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            gameSceneBackgdTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }

    public void unloadGameSceneRes() {
        gameSceneBackgdTextureAtlas.unload();
        gameSceneBckgdTextureRegion = null;
        gameRightControllerTextureRegion = null;
    }
//=====================/ load & unload scenes resources ====================

//==================== private / helper methods =============================
    private void loadMenuGfx(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GFX_BASE_DIR + GFX_MENU_DIR);
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), MENU_BITMAP_TEXTURE_ATLAS_SIDE_SIZE,
                MENU_BITMAP_TEXTURE_ATLAS_SIDE_SIZE, TextureOptions.BILINEAR);
        menuBckgdRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_bckgd.png");
        btnNewGameRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "btn_new_game.png");
        btnContinueGameRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "btn_continue_game.png");
        btnOptionsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "btn_options.png");
        try {
            menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            menuTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e(TAG, "loadMenuGfx: error creating buildable texture atlas!", e);
        }
    }

    private void loadMenuAudio(){
        // TODO:
    }

    private void loadMenuFonts(){
        FontFactory.setAssetBasePath(FONT_BASE_DIR);
        final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        menuFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font3.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
        menuFont.load();
    }


    private void unloadMenuGfx() {
        menuTextureAtlas.unload();
        menuBckgdRegion = null;
        btnContinueGameRegion = null;
        btnNewGameRegion = null;
        btnOptionsRegion = null;
    }


    private void unloadMenuAudio() {
        // TODO:
    }


    private void unloadMenuFonts() {
        menuFont.unload();
        menuFont = null;
    }

//==================== /private / helper methods =============================
}
