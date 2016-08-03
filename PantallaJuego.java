package com.apps.crashpenguin.pantallas;

import com.apps.crashpenguin.AdsController;
import com.apps.crashpenguin.Pantalla;
import com.apps.crashpenguin.Principal;
import com.apps.crashpenguin.actores.ActorAnimado;
import com.apps.crashpenguin.actores.ActorBoton;
import com.apps.crashpenguin.actores.ActorGrafico;
import com.apps.crashpenguin.actores.ActorNinePatch;
import com.apps.crashpenguin.actores.ActorTexture;
import com.apps.crashpenguin.utilidades.ResourceManager;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego extends Pantalla {

	private Stage escenario;
	private ActorTexture piso;
	private ActorTexture hielo;
	private ActorTexture hielo2;
	private ActorTexture nubes;
	private ActorTexture nubes2;
	private ActorTexture nieve;
	private ActorTexture nieve2;
	private ActorNinePatch cielo;
	private ActorNinePatch filtroJuego;
	private ActorGrafico caraPinguino;
	private ActorGrafico pieL;
	private ActorGrafico pieR;

	private Boolean crashTime = false;
	private Group grpPinguino;

	private ActorAnimado derrape;
	private ActorAnimado derrape2;
	private ActorAnimado pinguino;
	private Slider slider;

	private float grados;
	private boolean gameOver = false;
	private float anguloCaida = 24;
	private float score = 0;
	private int finalScore = 0;
	private int saveScore;
	private Preferences prefs;

	// elementos Game Over

	private Group grpGameOver;
	private ActorNinePatch filtroGameOver;
	private ActorBoton btnCerrar;

	private Label lblScore;
	private Label lblFinalScore;
	private Label lblTextoFinalScore;
	private Label lblHighScore;
	private Label lblTextoHighScore;
	private Label lblGameOver;

	private TextButton btnRetry;
	private ActorBoton btnItems;

	private Viewport vp;

	private Skin skin;
	private TextureAtlas atlas;

	// elementos Tutorial

	private ActorAnimado dedo;
	private ActorAnimado stars;
	private ActorAnimado stars2;
	private ActorAnimado stars3;
	private Label lblTutorial;
	private Label lblTutorial2;
	private Label lblCrashTime;
	private ActorGrafico flechaTutorial;
	private ActorGrafico lentePinguino;
	private ActorGrafico sombreroPinguino;

	private ActorGrafico cuelloPinguino;
	private ActorNinePatch filtroTutorial;
	private ActorNinePatch filtroBoton;
	private ActorNinePatch filtroGanador;

	private Group grpTutorial;
	private Group grpLabel;
	// Elementos barra

	private ActorNinePatch barra;
	private ActorGrafico[] barraPunto = new ActorGrafico[12];
	private ActorGrafico barraPuntero;
	private Group groupBarra;

	// Styles

	private SliderStyle sliderstyle;
	private LabelStyle styleWhite;
	private LabelStyle styleBlue;
	private TextButtonStyle btnStyle;

	private I18NBundle bundle;
	private String strTutorial = "";
	private String strTutorial2 = "";
	private String strRecord = "";
	private String strScore = "";
	private Boolean desbloqueo = false;

	private Boolean btnEnabled = false;

	private Boolean bannerVisible = false;

	private float dificultad = 0.05f;
	private float dif = 0.5f;

	public PantallaJuego(Principal game, final AdsController adsController) {
		super(game, adsController);
		this.adsController = adsController;

		adsController.hideBannerAd();
		Sounds.sndMusic.stop();

		skin = new Skin();
		atlas = ResourceManager.manager.get(ResourceManager.ATLAS_SKIN);
		skin.addRegions(atlas);

		sliderstyle = new SliderStyle();

		sliderstyle.background = skin.getDrawable("sliderBack");
		sliderstyle.knob = skin.getDrawable("sliderKnob");

		// Multi Lenguaje

		bundle = ResourceManager.getSelectedBundle();

		strTutorial = bundle.get("lblTutorial");
		strTutorial2 = bundle.get("lblTutorial2");
		strRecord = bundle.get("lblRecord");
		strScore = bundle.get("lblScore");

		vp = new FitViewport(720, 1280);

		styleWhite = new LabelStyle(ResourceManager.getMediumFont(), Color.WHITE);
		styleBlue = new LabelStyle(ResourceManager.getMediumFont(), Color.valueOf("d6fff7"));

		escenario = new Stage(vp);

		Gdx.input.setInputProcessor(escenario);

		// Sonidos

		Sounds.sndMusic.setVolume(Sounds.masterVol / 2);
		Sounds.sndMusic.play();

		filtroJuego = new ActorNinePatch("filtroPaisaje", 10, 10, 10, 10);
		filtroJuego.setSize(720, 1280);
		filtroJuego.setPosition(0, 0);

		filtroBoton = new ActorNinePatch("filtroBoton", 5, 5, 0, 0);
		filtroBoton.setSize(720, 640);
		filtroBoton.setPosition(0, 0);

		filtroGanador = new ActorNinePatch("filtroGanador", 10, 10, 10, 10);
		filtroGanador.setSize(720, 1280);
		filtroGanador.setPosition(0, 0);
		filtroGanador.setVisible(false);

		pinguino = new ActorAnimado("cuerpo", 350, 249);
		pinguino.setPosition(0, 30);
		pinguino.setOrigin(pinguino.getWidth() / 2, 100);

		caraPinguino = new ActorGrafico(ResourceManager.ATLAS_PARTES, "cara01", 128, 101);
		caraPinguino.setPosition(180, 160);

		pieL = new ActorGrafico(ResourceManager.ATLAS_PARTES, "pata", 72, 53);
		pieL.setOrigin(36, 20);
		pieL.setPosition(120, 0);

		pieR = new ActorGrafico(ResourceManager.ATLAS_PARTES, "pata", 72, 53);
		pieR.setOrigin(36, 20);
		pieR.setPosition(230, 5);

		lentePinguino = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, ResourceManager.preferences.getString("ojos"),
				178, 68);
		lentePinguino.setPosition(110, 205);

		sombreroPinguino = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS,
				ResourceManager.preferences.getString("cabeza"), 209, 146);
		sombreroPinguino.setPosition(85, 230);

		cuelloPinguino = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS,
				ResourceManager.preferences.getString("cuello"), 118, 127);
		cuelloPinguino.setPosition(135, 83);

		// Desbloqueo

		stars = new ActorAnimado("stars", 183, 156);
		stars.setPosition(70, 160);
		stars.playMode(PlayMode.NORMAL);
		stars.setFrameRate(13);
		stars.setVisible(false);

		stars2 = new ActorAnimado("stars", 183, 156);
		stars2.setPosition(100, 220);
		stars2.playMode(PlayMode.NORMAL);
		stars2.setFrameRate(10);
		stars2.setVisible(false);

		stars3 = new ActorAnimado("stars", 183, 156);
		stars3.setPosition(120, 120);
		stars3.playMode(PlayMode.NORMAL);
		stars3.setFrameRate(14);
		stars3.setVisible(false);

		grpPinguino = new Group();
		grpPinguino.setOriginY(10);
		grpPinguino.setRotation(0);
		grpPinguino.addActor(pieL);
		grpPinguino.addActor(pieR);
		grpPinguino.addActor(pinguino);
		grpPinguino.addActor(sombreroPinguino);
		grpPinguino.addActor(cuelloPinguino);
		grpPinguino.addActor(caraPinguino);
		grpPinguino.addActor(lentePinguino);
		grpPinguino.addActor(stars);
		grpPinguino.addActor(stars2);
		grpPinguino.addActor(stars3);

		grpPinguino.setPosition(160, 550);
		grpPinguino.setRotation(20);

		cielo = new ActorNinePatch("cielo", 10, 10, 0, 0);
		cielo.setSize(720, 538);
		cielo.setPosition(0, 742);

		piso = new ActorTexture("piso01.png", 1040, 830);
		piso.setPosition(0, 0);

		hielo = new ActorTexture("hielo01.png", 1623, 594);
		hielo.setPosition(0, 565);

		hielo2 = new ActorTexture("hielo02.png", 1623, 594);
		hielo2.setPosition(1623, 565);

		nubes = new ActorTexture("nubes01.png", 1575, 285);
		nubes.setPosition(0, 900);

		nubes2 = new ActorTexture("nubes02.png", 1575, 285);
		nubes2.setPosition(1575, 900);

		nieve = new ActorTexture("nieve01.png", 1479, 144);
		nieve.setPosition(0, 820);

		nieve2 = new ActorTexture("nieve02.png", 1479, 144);
		nieve2.setPosition(1479, 820);

		derrape = new ActorAnimado("derrape", 196, 98);
		derrape.setPosition(110, 530);

		derrape2 = new ActorAnimado("derrape", 196, 98);
		derrape2.setPosition(240, 540);

		lblScore = new Label("0 m", styleWhite);
		lblScore.setPosition(720 - lblScore.getWidth() - 32, 1280 - lblScore.getHeight() - 32);
		lblScore.setAlignment(Align.topRight, Align.right);

		lblCrashTime = new Label("CRASHTIME!", styleWhite);

		grpLabel = new Group();

		grpLabel.setPosition(92, 1280 - lblCrashTime.getHeight() - 32);
		grpLabel.addActor(lblCrashTime);
		grpLabel.setOriginX(120);
		grpLabel.setOriginY(40);
		grpLabel.setScale(0, 0);

		// Barra avance

		barra = new ActorNinePatch("barra", 40, 40, 0, 0);
		barra.setSize(450, 51);
		barra.setPosition(0, 15);

		for (int i = 0; i < 12; i++) {
			barraPunto[i] = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "barraPuntoDisabled", 19, 20);
			float position = (((barra.getWidth() / 13) * (i + 1)) - (barraPunto[i].getWidth() / 2));
			barraPunto[i].setPosition(position, 30);
		}

		int numDesbloqueo = ResourceManager.preferences.getInteger("Un");

		if (numDesbloqueo != 0) {

			for (int i = 0; i < numDesbloqueo; i++) {
				barraPunto[i].setImagen("barraPuntoEnabled");
			}
		}

		barraPuntero = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "barraPoint", 22, 68);

		groupBarra = new Group();

		groupBarra.addActor(barra);
		for (int i = 0; i < 12; i++) {
			groupBarra.addActor(barraPunto[i]);
		}

		groupBarra.addActor(barraPuntero);

		groupBarra.setPosition(32, 1180);

		// ELementos Game Over

		filtroGameOver = new ActorNinePatch("filtroGameOver", 10, 10, 10, 10);
		filtroGameOver.setSize(720, 1280);
		filtroGameOver.setPosition(0, 0);

		btnCerrar = new ActorBoton("btnHome", 116, 156, true, game, adsController);
		btnCerrar.setPosition(32, 1248 - btnCerrar.getHeight());

		btnItems = new ActorBoton("btnItems", 116, 156, true, game, adsController);
		btnItems.setPosition(688 - btnItems.getWidth(), 1248 - btnItems.getHeight());
		btnItems.setPantallaBack("pantallaJuego");

		lblFinalScore = new Label("final", styleWhite);
		lblFinalScore.setAlignment(Align.center, Align.right);
		lblFinalScore.setPosition(410, 600);

		lblTextoFinalScore = new Label(strScore, styleBlue);
		lblTextoFinalScore.setPosition(100, 600);

		lblHighScore = new Label("high", styleWhite);
		lblHighScore.setAlignment(Align.center, Align.right);
		lblHighScore.setPosition(410, 660);

		lblTextoHighScore = new Label(strRecord, styleBlue);
		lblTextoHighScore.setPosition(100, 660);

		styleWhite.font = ResourceManager.getBiggerFont();
		lblGameOver = new Label("Game Over", styleWhite);
		lblGameOver.setPosition((escenario.getWidth() / 2) - (lblGameOver.getWidth() / 2), 780);

		// Asignar estilo al botón (poner imágenes)
		btnStyle = new TextButtonStyle();
		btnStyle.up = skin.getDrawable("btnDefUp");
		btnStyle.down = skin.getDrawable("btnDefDown");

		btnStyle.font = ResourceManager.getMediumFont();
		btnRetry = new TextButton(bundle.get("lblRetry"), btnStyle);
		btnRetry.setSize(400, 100);
		btnRetry.setPosition((escenario.getWidth() / 2) - (btnRetry.getWidth() / 2), 400);

		// Elementos Tutorial

		dedo = new ActorAnimado("dedo", 322, 326);
		dedo.setPosition(360 - (dedo.getHeight() / 2), 0);
		dedo.setFrameRate(3.98f);
		dedo.setTouchable(Touchable.disabled);
		styleWhite.font = ResourceManager.getSmallFont();
		lblTutorial = new Label(strTutorial, styleWhite);
		lblTutorial.setPosition(360 - (lblTutorial.getWidth() / 2), 470);

		lblTutorial2 = new Label(strTutorial2, styleWhite);
		lblTutorial2.setPosition(360 - (lblTutorial2.getWidth() / 2), 410);

		flechaTutorial = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "flechaDown", 61, 28);
		flechaTutorial.setPosition(360 - (flechaTutorial.getWidth() / 2), 370);

		filtroTutorial = new ActorNinePatch("filtroTutorial", 0, 0, 0, 0);
		filtroTutorial.setSize(720, 1280);
		filtroTutorial.setPosition(0, 0);
		filtroTutorial.setTouchable(Touchable.disabled);
		grpTutorial = new Group();
		grpTutorial.addActor(filtroTutorial);
		grpTutorial.addActor(lblTutorial);
		grpTutorial.addActor(lblTutorial2);
		grpTutorial.addActor(flechaTutorial);
		grpTutorial.addActor(dedo);

		lblTutorial.addAction(Actions.forever(
				Actions.sequence(Actions.delay(0.5f), Actions.alpha(0), Actions.delay(0.5f), Actions.alpha(1))));
		lblTutorial2.addAction(Actions.forever(
				Actions.sequence(Actions.delay(0.5f), Actions.alpha(0), Actions.delay(0.5f), Actions.alpha(1))));

		flechaTutorial.addAction(Actions.forever(Actions.sequence(Actions.delay(0.5f), Actions.visible(false),
				Actions.delay(0.5f), Actions.visible(true))));

		// SLider"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11

		slider = new Slider(0, 10, 0.1f, false, sliderstyle);

		slider.setValue(5f);
		slider.setSize(226, 227);

		slider.setPosition((escenario.getWidth() / 2) - (slider.getWidth() / 2), 120);

		grpGameOver = new Group();
		grpGameOver.setVisible(false);
		grpGameOver.addActor(filtroGameOver);
		grpGameOver.addActor(btnCerrar);
		grpGameOver.addActor(btnItems);
		grpGameOver.addActor(lblGameOver);
		grpGameOver.addActor(lblFinalScore);
		grpGameOver.addActor(lblHighScore);
		grpGameOver.addActor(lblTextoFinalScore);
		grpGameOver.addActor(lblTextoHighScore);
		grpGameOver.addActor(btnRetry);

		if (grpTutorial.isVisible()) {
			grpPinguino.addAction(
					Actions.forever(Actions.sequence(Actions.rotateBy(-40, 0.5f), Actions.rotateBy(40, 0.5f))));
		}
		escenario.addActor(cielo);
		escenario.addActor(nubes2);
		escenario.addActor(nubes);

		escenario.addActor(nieve2);
		escenario.addActor(nieve);

		escenario.addActor(hielo2);
		escenario.addActor(hielo);

		escenario.addActor(piso);
		escenario.addActor(filtroJuego);
		escenario.addActor(filtroGanador);
		escenario.addActor(derrape2);
		escenario.addActor(derrape);

		escenario.addActor(grpPinguino);
		escenario.addActor(lblScore);
		escenario.addActor(grpLabel);
		escenario.addActor(groupBarra);
		escenario.addActor(filtroBoton);
		escenario.addActor(slider);
		escenario.addActor(grpTutorial);
		escenario.addActor(grpGameOver);

		prefs = Gdx.app.getPreferences("Preferences");
		prefs.flush();

		slider.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (grpTutorial.isVisible()) {
					grpPinguino.clearActions();
					grpPinguino.setRotation(0);
					grpTutorial.setVisible(false);
					btnEnabled = true;
				}
				return super.touchDown(event, x, y, pointer, button);
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (btnEnabled) {
					verificarScore();
					gameOver = true;
					Sounds.sndLose.stop();
					Sounds.sndLose.play(Sounds.getMasterVol() / 2);
					Sounds.sndVozCae.stop();
					Sounds.sndVozCae.play(Sounds.getMasterVol());
					Sounds.sndMusic.stop();
					Sounds.sndCrashTime.stop();

					btnEnabled = false;
				}
				super.exit(event, x, y, pointer, toActor);
			}
		});

		btnRetry.addListener(new InputListener() {

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				restartGame();
				super.exit(event, x, y, pointer, toActor);

			}
		});

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		loop(hielo, hielo2, 10, 1623);
		loop(hielo2, hielo, 10, 1623);

		loop(nubes, nubes2, 0.5f, 1575);
		loop(nubes2, nubes, 0.5f, 1575);

		loop(nieve, nieve2, 2, 1479);
		loop(nieve2, nieve, 2, 1479);

		if (piso.getX() > -330) {
			piso.setX(piso.getX() - 15);
		} else {
			piso.setX(0);

		}

		if (gameOver) {
			if (bannerVisible == false) {
				if (this.adsController.isWifiConnected() || this.adsController.isMobileConnected() ) {
					this.adsController.showBannerAd();
					bannerVisible = true;
				}
			}
			if (grpPinguino.getRotation() > 0) {
				dedo.setTouchable(Touchable.disabled);
				grpPinguino.setOriginY(70);

				grpPinguino.addAction(Actions.sequence(Actions.rotateTo(90, 0.1f), Actions.moveTo(180, 520, 0.1f)));
				derrape.setX(10);
				caraPinguino.setImagen("cara06");

				pinguino.setFrameRate(20);

			} else if (grpPinguino.getRotation() < 0) {
				dedo.setTouchable(Touchable.disabled);
				grpPinguino.setOriginY(70);

				grpPinguino.addAction(Actions.sequence(Actions.rotateTo(-90, 0.1f), Actions.moveTo(180, 490, 0.1f)));

				caraPinguino.setImagen("cara06");
				pinguino.setFrameRate(20);
			}

			grpGameOver.setVisible(true);

			lblScore.setVisible(false);
			pieL.setRotation(0);
			pieR.setRotation(0);

		} else {

			verificarDesbloqueo(prefs.getInteger("Un"));
			subirDificultad();

			derrape.setVisible(true);
			derrape2.setVisible(true);

			if (Gdx.input.isKeyPressed(Keys.BACK)) {
				game.setScreen(new PantallaInicio(game, adsController));
			}

			if (grpPinguino.getRotation() > 0) {
				grpPinguino.setOriginX(156);
				derrape.setVisible(true);
				derrape2.setVisible(false);
				pieR.setRotation(0);

			} else if (grpPinguino.getRotation() < 0) {
				grpPinguino.setOriginX(266);
				derrape2.setVisible(true);
				derrape.setVisible(false);
				pieL.setRotation(0);
			} else {
				derrape2.setVisible(true);
				derrape.setVisible(true);
			}

			if (slider.isDragging()) {
				score = score + 0.1f;
				finalScore = Math.round(score);
				lblScore.setText(String.valueOf(finalScore) + " m");

				float barraPosition = (float) (finalScore / 1.45f);
				barraPuntero.setPosition(barraPosition, 0);

				if (slider.getValue() >= 5f) {
					grpPinguino.rotateBy(dificultad);

					grados = -(dif);

					grpPinguino.rotateBy(grados);

					if (grpPinguino.getRotation() < 0) {
						pieR.rotateBy(-grados);

					} else {

						pieL.rotateBy(-grados);
					}

					if (grpPinguino.getRotation() < -18 && grpPinguino.getRotation() > -24
							|| grpPinguino.getRotation() > 18 && grpPinguino.getRotation() < 24) {

						caraPinguino.setImagen("cara04");
					} else if (grpPinguino.getRotation() < -12 && grpPinguino.getRotation() > -18
							|| grpPinguino.getRotation() > 12 && grpPinguino.getRotation() < 18) {
						Sounds.sndVozAsustado.stop();
						Sounds.sndVozAsustado.play(Sounds.getMasterVol() / 1.47f);
						caraPinguino.setImagen("cara03");
					} else if (grpPinguino.getRotation() < -6 && grpPinguino.getRotation() > -12
							|| grpPinguino.getRotation() > 6 && grpPinguino.getRotation() < 12) {
						caraPinguino.setImagen("cara02");
					} else if (grpPinguino.getRotation() < 0 && grpPinguino.getRotation() > -6
							|| grpPinguino.getRotation() > 0 && grpPinguino.getRotation() < 6) {
						caraPinguino.setImagen("cara01");
					}

					if (grados < -5) {
						pinguino.setFrameRate(20);
					} else {
						pinguino.setFrameRate(10);
					}

					if (grpPinguino.getRotation() > anguloCaida || grpPinguino.getRotation() < -anguloCaida) {
						verificarScore();
						gameOver = true;
						Sounds.sndVozAsustado.stop();

					}

				} else if (slider.getValue() < 5f) {

					grpPinguino.rotateBy(-dificultad);
					grados = dif;

					grpPinguino.rotateBy(grados);

					if (grpPinguino.getRotation() > 0) {
						pieL.rotateBy(-grados);
					} else {
						pieR.rotateBy(-grados);

					}

					if (grpPinguino.getRotation() < -18 && grpPinguino.getRotation() > -24
							|| grpPinguino.getRotation() > 18 && grpPinguino.getRotation() < 24) {

						caraPinguino.setImagen("cara04");
					} else if (grpPinguino.getRotation() < -12 && grpPinguino.getRotation() > -18
							|| grpPinguino.getRotation() > 12 && grpPinguino.getRotation() < 18) {
						Sounds.sndVozAsustado.stop();
						Sounds.sndVozAsustado.play(Sounds.getMasterVol() / 1.47f);
						caraPinguino.setImagen("cara03");
					} else if (grpPinguino.getRotation() < -6 && grpPinguino.getRotation() > -12
							|| grpPinguino.getRotation() > 6 && grpPinguino.getRotation() < 12) {
						caraPinguino.setImagen("cara02");
					} else if (grpPinguino.getRotation() < 0 && grpPinguino.getRotation() > -6
							|| grpPinguino.getRotation() > 0 && grpPinguino.getRotation() < 6) {
						caraPinguino.setImagen("cara01");
					}

					if (grpPinguino.getRotation() > anguloCaida || grpPinguino.getRotation() < -anguloCaida) {
						verificarScore();
						gameOver = true;
						Sounds.sndVozAsustado.stop();

					}

				}
			}

		}

		escenario.draw();
		escenario.act();

		System.out.println(dif);

	}

	@Override
	public void resize(int width, int height) {
		escenario.getViewport().update(width, height);
	}

	void loop(Actor actor1, Actor actor2, float x, int ancho) {
		if (actor1.getX() > -ancho) {
			actor1.setX(actor1.getX() - x);
		} else {
			actor1.setX(actor2.getX() + ancho);
		}

	}

	void verificarScore() {
		saveScore = prefs.getInteger("highScore");
		// Verificar high score
		if (finalScore > saveScore) {
			prefs.putInteger("highScore", finalScore);
			prefs.flush();
			saveScore = prefs.getInteger("highScore");
		}
		lblFinalScore.setText(String.valueOf(finalScore) + " m");
		lblHighScore.setText(String.valueOf(saveScore) + " m");
		if (desbloqueo) {
			prefs.putString("ojos", lentePinguino.getImagenName());
			prefs.putString("cabeza", sombreroPinguino.getImagenName());
			prefs.putString("cuello", cuelloPinguino.getImagenName());
			prefs.flush();
			desbloqueo = false;
		}

	}

	void verificarDesbloqueo(int item) {

		if (item == 0 & finalScore == 50) {
			desbloquear(lentePinguino, "itemLentes01");
			desbloqueo = true;
			barraPunto[0].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 1 & finalScore == 100) {
			desbloquear(sombreroPinguino, "itemSombrero01");
			desbloqueo = true;
			barraPunto[1].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 2 & finalScore == 150) {
			desbloquear(cuelloPinguino, "itemCuello01");
			desbloqueo = true;
			barraPunto[2].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 3 & finalScore == 200) {
			desbloquear(lentePinguino, "itemLentes02");
			desbloqueo = true;
			barraPunto[3].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 4 & finalScore == 250) {
			desbloquear(sombreroPinguino, "itemSombrero02");
			desbloqueo = true;
			barraPunto[4].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 5 & finalScore == 300) {
			desbloquear(cuelloPinguino, "itemCuello02");
			desbloqueo = true;
			barraPunto[5].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 6 & finalScore == 350) {
			desbloquear(lentePinguino, "itemLentes03");
			desbloqueo = true;
			barraPunto[6].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 7 & finalScore == 400) {
			desbloquear(sombreroPinguino, "itemSombrero03");
			desbloqueo = true;
			barraPunto[7].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 8 & finalScore == 450) {
			desbloquear(cuelloPinguino, "itemCuello03");
			desbloqueo = true;
			barraPunto[8].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 9 & finalScore == 500) {
			desbloquear(lentePinguino, "itemLentes04");
			desbloqueo = true;
			barraPunto[9].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 10 & finalScore == 550) {
			desbloquear(sombreroPinguino, "itemSombrero04");
			desbloqueo = true;
			barraPunto[10].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		} else if (item == 11 & finalScore == 600) {
			desbloquear(cuelloPinguino, "itemCuello04");
			desbloqueo = true;
			barraPunto[11].setImagen("barraPuntoEnabled");
			Sounds.sndItem.play(Sounds.getMasterVol());

		}

	}

	void subirDificultad() {
		if (finalScore == 50) {
			dif = 0.67f;

		}
		if (finalScore == 100) {
			dif = 0.83f;

		}
		if (finalScore == 150) {
			dif = 1f;
		}
		if (finalScore == 200) {
			dif = 1.11f;
		}
		if (finalScore == 250) {
			dif = 1.22f;
		}
		if (finalScore == 300) {
			dif = 1.33f;
		}

		if (finalScore == 600) {
			if (!crashTime) {
				crashTime();

			}
		}

	}

	void crashTime() {
		// Sounds

		Sounds.sndMusic.stop();

		Sounds.sndCrashTime.setLooping(true);
		Sounds.sndCrashTime.play();

		filtroGanador.setVisible(true);
		groupBarra.setVisible(false);
		grpLabel.addAction(Actions.sequence(Actions.scaleBy(1.5f, 1.5f, 0.2f), Actions.scaleBy(-0.5f, -0.5f, 0.1f),
				Actions.forever(
						Actions.sequence(Actions.scaleBy(0.1f, 0.1f, 0.1f), Actions.scaleBy(-0.1f, -0.1f, 0.1f)))));

		crashTime = true;

	}

	void desbloquear(ActorGrafico actor, String item) {
		stars.setVisible(true);
		stars.restart();
		stars2.setVisible(true);
		stars2.restart();
		stars3.setVisible(true);
		stars3.restart();

		actor.setImagen(item);
		int bl = prefs.getInteger("Un") + 1;
		prefs.putInteger("Un", bl);
		prefs.flush();
	}

	void restartGame() {
		grpGameOver.setVisible(false);
		if (adsController.isWifiConnected() || this.adsController.isMobileConnected()) {
			adsController.hideBannerAd();

		}
		bannerVisible = false;
		grpPinguino.setRotation(20);
		grpPinguino.setOriginY(50);
		grpPinguino.setPosition(166, 550);
		grpPinguino.clearActions();
		derrape.setX(110);
		pinguino.setFrameRate(10);
		gameOver = false;
		score = 0;
		finalScore = 0;
		lblScore.setText(String.valueOf(finalScore) + " m");
		lblScore.setVisible(true);
		grados = 0;
		caraPinguino.setImagen("cara01");
		slider.setValue(5f);

		grpTutorial.setVisible(true);

		Sounds.sndBtn.play(Sounds.getMasterVol());
		Sounds.sndMusic.play();
		dif = 0.5f;
		filtroGanador.setVisible(false);
		if (grpTutorial.isVisible()) {
			dedo.restart();
			grpPinguino.addAction(
					Actions.forever(Actions.sequence(Actions.rotateBy(-40, 0.5f), Actions.rotateBy(40, 0.5f))));
		}
		grpLabel.setScale(0, 0);
		grpLabel.clearActions();
		groupBarra.setVisible(true);
		crashTime = false;
		barraPuntero.setPosition(0, 0);
	}

	@Override
	public void dispose() {

		super.dispose();
		this.dispose();
		escenario.dispose();
		pinguino.dispose();
		piso.dispose();
		hielo.dispose();
		hielo2.dispose();
		nubes.dispose();
		nubes2.dispose();
		nieve.dispose();
		nieve2.dispose();
		cielo.dispose();
		ResourceManager.dispose();
		caraPinguino.dispose();
		pieL.dispose();
		pieR.dispose();
		filtroJuego.dispose();
		derrape.dispose();
		derrape2.dispose();
		filtroGameOver.dispose();
		btnCerrar.dispose();
		skin.dispose();
		atlas.dispose();
		dedo.dispose();
		flechaTutorial.dispose();
		filtroTutorial.dispose();
		System.gc();
		pinguino.dispose();
		btnItems.dispose();
		stars.dispose();
		stars2.dispose();
		stars3.dispose();
		lentePinguino.dispose();
		sombreroPinguino.dispose();
		cuelloPinguino.dispose();
		filtroBoton.dispose();
		filtroGanador.dispose();
		barra.dispose();
		barraPuntero.dispose();
		
		for (int i = 0; i < 12; i++) {
			barraPunto[i].dispose();
		}

		

	}
}
