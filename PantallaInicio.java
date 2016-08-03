package com.apps.crashpenguin.pantallas;

import com.apps.crashpenguin.AdsController;
import com.apps.crashpenguin.Pantalla;
import com.apps.crashpenguin.Principal;
import com.apps.crashpenguin.actores.ActorBoton;
import com.apps.crashpenguin.actores.ActorGrafico;
import com.apps.crashpenguin.actores.ActorNinePatch;
import com.apps.crashpenguin.actores.ActorTexture;
import com.apps.crashpenguin.utilidades.ResourceManager;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaInicio extends Pantalla {

	private Stage escenario;
	private ActorTexture piso;
	private ActorTexture piso2;
	private ActorTexture hielo;
	private ActorTexture hielo2;
	private ActorTexture nubes;
	private ActorTexture nubes2;
	private ActorTexture nieve;
	private ActorTexture nieve2;
	private ActorGrafico titulo;
	private ActorNinePatch filtro;
	private ActorNinePatch cielo;

	private ActorBoton boton;
	private ActorBoton botonShop;
	private ActorBoton botonConfig;
	private ActorBoton botonItem;
	private PantallaJuego pantallaJuego;

	private Music music;

	private Table tabla;

	private Viewport vp;

	public PantallaInicio(final Principal game, AdsController adsController) {
		super(game, adsController);

		adsController.hideBannerAd();

		vp = new FitViewport(720, 1280);

		escenario = new Stage(vp);
		tabla = new Table();

		Gdx.input.setInputProcessor(escenario);
		Gdx.input.setCatchBackKey(true);

		if (ResourceManager.preferences.getInteger("Music") == 1) {

			Sounds.sndMusic.setVolume(1);

		} else {

			Sounds.sndMusic.setVolume(0);

		}

		if (ResourceManager.preferences.getInteger("Sound") == 1) {
			Sounds.setMasterVol(1);

		} else {
			Sounds.setMasterVol(0);

		}

		Sounds.sndMusic.setLooping(true);
		Sounds.sndMusic.play();

		boton = new ActorBoton("btnPlay", 228, 292, false, game, adsController);
		boton.setOrigin(boton.getWidth() / 2, boton.getHeight() / 2);
		boton.setPosition((escenario.getWidth() / 2) - boton.getOriginX(), 100);

		botonShop = new ActorBoton("btnCompras", 116, 156, true, game, adsController);

		botonItem = new ActorBoton("btnItems", 116, 156, true, game, adsController);
		botonItem.setPantallaBack("pantallaInicio");

		botonConfig = new ActorBoton("btnConfig", 116, 156, true, game, adsController);

		titulo = new ActorGrafico("atlas/elementos.atlas", "logo", 608, 328);

		filtro = new ActorNinePatch("filtroInicio", 10, 10, 10, 10);
		filtro.setSize(720, 1280);
		filtro.setPosition(0, 0);

		cielo = new ActorNinePatch("cielo", 10, 10, 0, 0);
		cielo.setSize(720, 1280);
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

		tabla.setFillParent(true);

		tabla.pad(32, 32, 32, 32);

		// tabla.add(botonShop).fillX().align(Align.topLeft).fillY();
		tabla.add(botonConfig).fillX().fillY().align(Align.topLeft);
		tabla.add(botonItem).fillX().fillY().align(Align.topRight);
		tabla.row();
		tabla.add(titulo).expandX().expandY().align(Align.center).colspan(2).padBottom(400);

		escenario.addActor(cielo);
		escenario.addActor(nubes2);
		escenario.addActor(nubes);

		escenario.addActor(nieve2);
		escenario.addActor(nieve);

		escenario.addActor(hielo2);
		escenario.addActor(hielo);

		escenario.addActor(piso);
		escenario.addActor(filtro);
		escenario.addActor(tabla);
		escenario.addActor(boton);

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

		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			Gdx.app.exit();
		}

		escenario.draw();

		escenario.act();

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

	@Override
	public void dispose() {
		super.dispose();
		this.dispose();
		escenario.dispose();
		piso.dispose();
		piso2.dispose();
		hielo.dispose();
		hielo2.dispose();
		nubes.dispose();
		nubes2.dispose();
		nieve.dispose();
		nieve2.dispose();
		titulo.dispose();
		filtro.dispose();
		cielo.dispose();
		boton.dispose();
		botonShop.dispose();
		botonConfig.dispose();
		pantallaJuego.dispose();
		music.dispose();
		ResourceManager.dispose();
		System.gc();

	}
}
