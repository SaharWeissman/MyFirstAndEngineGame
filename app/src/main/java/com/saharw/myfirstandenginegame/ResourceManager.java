package com.saharw.myfirstandenginegame;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by Sahar on 12/05/2015.
 */
public class ResourceManager {

    private static final String GFX_BASE_DIR = "gfx/";
    private static final int BITMAP_TEXTURE_ATLAS_WIDTH = 256;
    private static final int BITMAP_TEXTURE_ATLAS_HEIGHT = 256;

    private static ResourceManager sInstance = null;

    public Engine engine;
    public BaseGameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    // ==========  Bitmap Texture Atlases & Texture Regions =======
    // splash scene
    public BitmapTextureAtlas splashSceneTextureAtlas;
    public ITextureRegion splashTextureRegion;

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

    public void loadSplashSceneRes() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(GFX_BASE_DIR);
        splashSceneTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), BITMAP_TEXTURE_ATLAS_WIDTH,
                BITMAP_TEXTURE_ATLAS_HEIGHT, TextureOptions.BILINEAR);
        splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashSceneTextureAtlas, activity, "splash.png", 0, 0);
        splashSceneTextureAtlas.load();
    }

    public void unloadSplashSceneRes(){
        splashSceneTextureAtlas.unload();
        splashTextureRegion = null;
    }

//=====================/ load & unload scenes resources ====================
}
