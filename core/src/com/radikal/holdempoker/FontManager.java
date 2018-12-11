package com.radikal.holdempoker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import java.util.ArrayList;
import java.util.List;

public class FontManager {

    public class Font {
        public String fontName;
        public List<Integer> fontSizes;

        public Font(String fontName) {
            this.fontName = fontName;
            fontSizes = new ArrayList<Integer>();
        }

        public Font addSize(int size) {
            fontSizes.add(size);
            return this;
        }

        public Font addSize(int... sizes) {
            for (int size : sizes)
                fontSizes.add(size);
            return this;
        }

    }

    private HoldemPokerGame game;
    private Font[] allFonts;

    public static final String ROOT_FOLDER = "fonts/";
    public static final String EXTENSION = ".ttf";

    public static final int TYPE_OSWALD_MEDIUM = 0;
    public static final int TYPE_OSWALD_regular = 1;

    public FontManager(HoldemPokerGame game) { this.game = game; }

    public void loadFonts() {
        float density = Gdx.graphics.getDensity();
        Gdx.app.log("loadFonts ", density + "");
        allFonts = new Font[2];
        allFonts[0] = new Font("Oswald-Medium").addSize(12, 14, 16, 18, 22, 24);
        allFonts[1] = new Font("Oswald-Regular").addSize(12, 14, 16, 18, 22, 24);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        this.game.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        this.game.assetManager.setLoader(BitmapFont.class, EXTENSION, new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter fontLoaderParameter;

        for(int i = 0; i < allFonts.length; i++) {
            for(int j = 0; j < allFonts[i].fontSizes.size(); j++) {
                fontLoaderParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
                fontLoaderParameter.fontFileName = ROOT_FOLDER + allFonts[i].fontName + EXTENSION;
                fontLoaderParameter.fontParameters.size = (int) (allFonts[i].fontSizes.get(j) * density);
                this.game.assetManager.load(allFonts[i].fontName + "_" + allFonts[i].fontSizes.get(j) + EXTENSION,
                        BitmapFont.class, fontLoaderParameter);
            }
        }
    }

    public BitmapFont getFont(int type, int size) {
        return this.getFont(type, size, Color.WHITE);
    }

    public BitmapFont getFont(int type, int size, Color color) {
        BitmapFont font = this.game.assetManager.get(allFonts[type].fontName + "_" + size + EXTENSION, BitmapFont.class);
        font.setColor(color);
        return font;
    }

    public void dispose() {

    }
}
