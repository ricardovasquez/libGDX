
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.I18NBundle;

public class ResourceManager {
	public static AssetManager manager = new AssetManager();
	public static String ATLAS_ELEMENTOS;
	public static String FONDO_CONFIG;
	public static String TEXTURE_HIELO;
	public static BitmapFont FONTSMALL;
	public static String MUSIC_INTRO;
	public static FileHandle ENGLISH_FH;
	public static FileHandle ESPANOL_FH;
	public static FileHandle ITALIANO_FH;
	public static FileHandle FRANCES_FH;
	public static FileHandle ALEMAN_FH;
	public static FileHandle PORTUGUES_FH;

  //Archivos con los lenguajes
	private static Locale locale_en;
	private static Locale locale_es;
	private static Locale locale_it;
	private static Locale locale_fr;
	private static Locale locale_gr;
	private static Locale locale_pt;

  //Lenguajes
  public static I18NBundle myBundleEnglish;
	public static I18NBundle myBundleEspañol;
	public static I18NBundle myBundleItaliano;
	public static I18NBundle myBundleFrances;
	public static I18NBundle myBundleAleman;
	public static I18NBundle myBundlePortugues;
	public static I18NBundle selectedBundle;

	public static Preferences preferences;

  //Para cargar la fuente
	private static FreeTypeFontGenerator generator;
	private static Boolean fontsLoaded = false;
	private static Boolean bundlesLoaded = false;
	public static Boolean prefsLoaded = false;

	public static void load() {
		ATLAS_ELEMENTOS = "atlas/elementos.atlas";
		FONDO_CONFIG = "fondoConfig.jpg";
		TEXTURE_HIELO = "hielo01.png";
		MUSIC_INTRO = "sounds/sndMusic.ogg";
		
		manager.load(ATLAS_ELEMENTOS, TextureAtlas.class);
	  manager.load(FONDO_CONFIG, Texture.class);
		manager.load(TEXTURE_HIELO, Texture.class);
		manager.load(MUSIC_INTRO, Music.class);
		
	
		if (fontsLoaded == false) {
			loadFonts();
		}

		if (bundlesLoaded == false) {
			loadBundles();
		}

		if (prefsLoaded == false) {
			loadPreferences();
		}

		while (!manager.update()) {
			System.out.println("Loaded: " + manager.getProgress() * 100 + "%");
		}

	}

	private static void loadFonts() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font/averia.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();

		parameter.size = 50;
		FONTSMALL = generator.generateFont(parameter); // font size 12 pixels
		parameter.size = 60;
		FONTMEDIUM = generator.generateFont(parameter); // font size 12 pixels
		parameter.size = 80;
		FONTBIG = generator.generateFont(parameter); // font size 12 pixels
		parameter.size = 140;
		FONTBIGGER = generator.generateFont(parameter); // font size 12 pixels
		fontsLoaded = true;
	}

	private static void loadBundles() {
		ENGLISH_FH = Gdx.files.internal("MyBundle_en");
		ESPANOL_FH = Gdx.files.internal("MyBundle_es");
		ITALIANO_FH = Gdx.files.internal("MyBundle_it");
		FRANCES_FH = Gdx.files.internal("MyBundle_fr");
		ALEMAN_FH = Gdx.files.internal("MyBundle_gr");
		PORTUGUES_FH = Gdx.files.internal("MyBundle_pt");

		locale_en = new Locale("en");
		locale_es = new Locale("es");
		locale_it = new Locale("it");
		locale_fr = new Locale("fr");
		locale_gr = new Locale("gr");
		locale_pt = new Locale("pt");

		myBundleEnglish = I18NBundle.createBundle(ENGLISH_FH, locale_en);
		myBundleEspañol = I18NBundle.createBundle(ESPANOL_FH, locale_es);
		myBundleItaliano = I18NBundle.createBundle(ITALIANO_FH, locale_it);
		myBundleFrances = I18NBundle.createBundle(FRANCES_FH, locale_fr);
		myBundleAleman = I18NBundle.createBundle(ALEMAN_FH, locale_gr);
		myBundlePortugues = I18NBundle.createBundle(PORTUGUES_FH, locale_pt);

	
		bundlesLoaded = true;
	}

	public static BitmapFont getSmallFont() {
		return FONTSMALL;
	}

	public static BitmapFont getMediumFont() {

		return FONTMEDIUM;

	}

	public static BitmapFont getBigFont() {

		return FONTBIG;

	}

	public static BitmapFont getBiggerFont() {

		return FONTBIGGER;

	}

	public static void setBundle(String myBundle) {
		if (myBundle.equals("English")) {
			selectedBundle = myBundleEnglish;
			preferences.putString("language", "English");
			preferences.putFloat("Y", 770);
			preferences.flush();
		} else if (myBundle.equals("Espanol")) {
			selectedBundle = myBundleEspañol;
			preferences.putString("language", "Espanol");
			preferences.putFloat("Y", 635);
			preferences.flush();
		} else if (myBundle.equals("Italiano")) {
			selectedBundle = myBundleItaliano;
			preferences.putString("language", "Italiano");
			preferences.putFloat("Y", 500);
			preferences.flush();
		} else if (myBundle.equals("Frances")) {
			selectedBundle = myBundleFrances;
			preferences.putString("language", "Frances");
			preferences.putFloat("Y", 360);
			preferences.flush();
		} else if (myBundle.equals("Aleman")) {
			selectedBundle = myBundleAleman;
			preferences.putString("language", "Aleman");
			preferences.putFloat("Y", 215);
			preferences.flush();
		} else if (myBundle.equals("Portugues")) {
			selectedBundle = myBundlePortugues;
			preferences.putString("language", "Portugues");
			preferences.putFloat("Y", 75);
			preferences.flush();
		}

	}

	public static I18NBundle getSelectedBundle() {
		return selectedBundle;
	}

	public static void loadPreferences() {
		preferences = Gdx.app.getPreferences("Preferences");
		if (!preferences.contains("language")) {
			preferences.putString("language", "English");
			preferences.putFloat("Y", 775);
			preferences.putString("ojos", "itemLentes00");
			preferences.putString("cabeza", "itemSombrero00");
			preferences.putString("cuello", "itemCuello00");
			preferences.putInteger("Un", 0);
			preferences.putInteger("Sound", 1);
			preferences.putInteger("Music", 1);
			preferences.flush();
			setBundle(preferences.getString("language"));
		} else {
			setBundle(preferences.getString("language"));

		}
		prefsLoaded = true;
	}

	public static Preferences getPreference() {
		return preferences;

	}

	public static Boolean isLoaded() {
		if (manager.getProgress() >= 1)
			return true;
		return false;
	}

	public static void dispose() {
		FONTSMALL.dispose();
		FONTMEDIUM.dispose();
		FONTBIG.dispose();
		FONTBIGGER.dispose();
		manager.dispose();
		manager = null;
	}
}
