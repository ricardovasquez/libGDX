package com.apps.crashpenguin.pantallas;

import com.apps.crashpenguin.AdsController;
import com.apps.crashpenguin.Pantalla;
import com.apps.crashpenguin.Principal;
import com.apps.crashpenguin.actores.ActorBoton;
import com.apps.crashpenguin.actores.ActorGrafico;
import com.apps.crashpenguin.actores.ActorNinePatch;
import com.apps.crashpenguin.actores.ActorSwitch;
import com.apps.crashpenguin.actores.ActorTexture;
import com.apps.crashpenguin.utilidades.ResourceManager;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaConfig extends Pantalla {

	private Stage escenario;
	private Skin skin;
	private Viewport vp;
	private Table tabla;

	private ActorTexture fondo;
	private ActorNinePatch panel;
	private ActorBoton btnCerrar;
	private TextureAtlas atlas;
	private Label lblSettings;
	private Label lblSounds;
	private Label lblLanguage;
	private Label lblFinalLanguage;

	private ActorGrafico actorLogo;

	private ActorSwitch switchSounds;
	private TextButton btnItems;

	// Styles
	private LabelStyle styleWhite;
	private LabelStyle styleGray;
	private LabelStyle styleGrayer;
	private LabelStyle styleOrange;
	private TextButtonStyle btnStyle;

	// Multilenguaje
	private I18NBundle bundle;

	// Popup

	private ActorNinePatch filtroPopUp;
	private ActorNinePatch panelPopUp;
	private Label lblIngles;
	private Label lblEspañol;
	private Label lblItaliano;
	private Label lblFrances;
	private Label lblAleman;
	private Label lblPortugues;
	private ActorGrafico selector;
	private Table tablaPopUp;

	private Group grpPopUp;

	
	private Integer stateSound;

	public PantallaConfig(final Principal game, final AdsController adsController) {
		super(game, adsController);
		this.game = game;
		this.adsController = adsController;

		adsController.hideBannerAd();
		vp = new FitViewport(720, 1280);

		escenario = new Stage(vp);
		tabla = new Table();

		Gdx.input.setInputProcessor(escenario);

		bundle = ResourceManager.getSelectedBundle();

		fondo = new ActorTexture("fondoConfig.jpg", 720, 1280);
		panel = new ActorNinePatch("panel", 60, 60, 70, 70);
		panel.setSize(656, 1036);
		panel.setPosition((escenario.getWidth() / 2) - (panel.getWidth() / 2), 50);

		btnCerrar = new ActorBoton("btnCerrar", 116, 156, true, game, adsController);
		btnCerrar.setPosition(32, 1248 - btnCerrar.getHeight());

		actorLogo = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "logoSmall", 207, 112);
		actorLogo.setPosition((escenario.getWidth() / 2) - (actorLogo.getWidth() / 2), 70);

		styleWhite = new LabelStyle(ResourceManager.getBigFont(), Color.WHITE);
		styleGray = new LabelStyle(ResourceManager.getSmallFont(), Color.valueOf("a0a0a0"));
		styleGrayer = new LabelStyle(ResourceManager.getSmallFont(), Color.valueOf("888888"));
		styleOrange = new LabelStyle(ResourceManager.getSmallFont(), Color.valueOf("ffd283"));

		skin = new Skin();
		atlas = ResourceManager.manager.get(ResourceManager.ATLAS_SKIN);
		skin.addRegions(atlas);

		btnStyle = new TextButtonStyle();
		btnStyle.up = skin.getDrawable("btnDefUp");
		btnStyle.down = skin.getDrawable("btnDefDown");
		btnStyle.font = ResourceManager.getMediumFont();

		btnItems = new TextButton("Items", btnStyle);
		btnItems.setSize(550, 104);
		btnItems.setPosition((escenario.getWidth() / 2) - (btnItems.getWidth() / 2), 400);

		lblSettings = new Label(bundle.get("titleConfig"), styleWhite);
		lblSettings.setPosition(208, 1140);
		lblSettings.setAlignment(Align.left);

		// lblMusic = new Label(bundle.get("lblMusic"), styleGray);
		lblSounds = new Label(bundle.get("lblSound"), styleGray);
		lblLanguage = new Label(bundle.get("lblLanguage"), styleGray);
		lblFinalLanguage = new Label(bundle.get("lblSelectedLang"), styleOrange);

		stateSound = ResourceManager.preferences.getInteger("Sound");

		switchSounds = new ActorSwitch();

		if (stateSound == 1) {
			switchSounds.switchOn();
		} else {
			switchSounds.switchOff();
		}

		// Pop Up
		styleOrange.font = ResourceManager.getMediumFont();
		filtroPopUp = new ActorNinePatch("filtroTutorial", 10, 10, 10, 10);
		filtroPopUp.setSize(720, 1280);
		filtroPopUp.setVisible(false);
		filtroPopUp.setPosition(0, 0);
		panelPopUp = new ActorNinePatch("panel", 60, 60, 70, 70);
		panelPopUp.setSize(656, 900);

		lblIngles = new Label("English", styleOrange);
		lblEspañol = new Label("Español", styleOrange);
		lblItaliano = new Label("Italiano", styleOrange);
		lblFrances = new Label("Français", styleOrange);
		lblAleman = new Label("Deutsche", styleOrange);
		lblPortugues = new Label("Português", styleOrange);

		selector = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "selector", 41, 45);
		selector.setPosition(130, ResourceManager.preferences.getFloat("Y"));

		tablaPopUp = new Table();
		tablaPopUp.setFillParent(true);
		tablaPopUp.pad(100, 50, 100, 100);
		tablaPopUp.setPosition(370, 450);
		tablaPopUp.add(lblIngles).fillY().padBottom(70).align(Align.center);
		tablaPopUp.row();
		tablaPopUp.add(lblEspañol).fillY().padBottom(70).align(Align.center);
		tablaPopUp.row();
		tablaPopUp.add(lblItaliano).fillY().padBottom(70).align(Align.center);
		tablaPopUp.row();
		tablaPopUp.add(lblFrances).fillY().padBottom(70).align(Align.center);
		tablaPopUp.row();
		tablaPopUp.add(lblAleman).fillY().padBottom(70).align(Align.center);
		tablaPopUp.row();
		tablaPopUp.add(lblPortugues).fillY().align(Align.center);
		tablaPopUp.row();

		grpPopUp = new Group();

		grpPopUp.addActor(panelPopUp);
		grpPopUp.addActor(tablaPopUp);
		grpPopUp.addActor(selector);
		grpPopUp.setVisible(false);
		grpPopUp.setPosition(32, 180);

		tabla.setFillParent(true);
		tabla.pad(150, 100, 0, 100);

		
		tabla.add(lblSounds).fillX().fillY().align(Align.left).padBottom(90);
		tabla.add(switchSounds).expandX().align(Align.right).padBottom(90).padRight(50);
		tabla.row();
		tabla.add(lblLanguage).fillY().padBottom(300).align(Align.left);
		tabla.add(lblFinalLanguage).fillY().padBottom(300).align(Align.right);
		tabla.row();
		tabla.row();
		tabla.add(actorLogo).align(Align.center).expandX().colspan(2).padTop(200);

		escenario.addActor(fondo);
		escenario.addActor(panel);
		escenario.addActor(btnCerrar);

		escenario.addActor(lblSettings);
		escenario.addActor(tabla);
		escenario.addActor(filtroPopUp);
		escenario.addActor(grpPopUp);

		btnItems.addListener(new InputListener() {

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				game.setScreen(new PantallaItems(game, "pantallaConfig", adsController));
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}

		});
		lblFinalLanguage.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				filtroPopUp.setVisible(true);
				grpPopUp.setVisible(true);
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});

		lblIngles.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.setBundle("English");
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				selector.setY(775);
				lblFinalLanguage.setText("English");
				reLanguage();
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lblEspañol.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.setBundle("Espanol");
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				lblFinalLanguage.setText("Español");
				selector.setY(635);
				// lblSettings.setAlignment(Align.left);
				reLanguage();
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lblItaliano.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.setBundle("Italiano");
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				lblFinalLanguage.setText("Italian");
				selector.setY(500);
				reLanguage();
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lblFrances.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.setBundle("Frances");
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				lblFinalLanguage.setText("French");
				selector.setY(360);
				reLanguage();
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lblAleman.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.setBundle("Aleman");
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				lblFinalLanguage.setText("Germany");
				selector.setY(215);
				reLanguage();
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lblPortugues.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.setBundle("Portugues");
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				lblFinalLanguage.setText("Português");
				selector.setY(75);
				reLanguage();
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		});

		filtroPopUp.addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				grpPopUp.setVisible(false);
				filtroPopUp.setVisible(false);
				super.exit(event, x, y, pointer, toActor);
			}

		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		escenario.draw();
		escenario.act();
	}

	@Override
	public void resize(int width, int height) {
		escenario.getViewport().update(width, height);
	}

	private void reLanguage() {

		bundle = ResourceManager.getSelectedBundle();

		lblSettings.setText(bundle.get("titleConfig"));
		lblSounds.setText(bundle.get("lblSound"));
		lblLanguage.setText(bundle.get("lblLanguage"));
		lblFinalLanguage.setText(bundle.get("lblSelectedLang"));
	}

	@Override
	public void dispose() {

		super.dispose();
		this.dispose();
		escenario.dispose();
		skin.dispose();
		fondo.dispose();
		panel.dispose();
		btnCerrar.dispose();
		switchSounds.dispose();
		atlas.dispose();		
		actorLogo.dispose();		
		filtroPopUp.dispose();		
		panelPopUp.dispose();		
		selector.dispose();		

		ResourceManager.dispose();

		System.gc();

	}
}
