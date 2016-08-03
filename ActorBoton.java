package com.apps.crashpenguin.actores;

import com.apps.crashpenguin.AdsController;
import com.apps.crashpenguin.Principal;
import com.apps.crashpenguin.pantallas.PantallaCompras;
import com.apps.crashpenguin.pantallas.PantallaConfig;
import com.apps.crashpenguin.pantallas.PantallaInicio;
import com.apps.crashpenguin.pantallas.PantallaItems;
import com.apps.crashpenguin.pantallas.PantallaJuego;
import com.apps.crashpenguin.utilidades.ResourceManager;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Disposable;

public class ActorBoton extends Actor implements Disposable {

	private TextureAtlas atlasBoton;
	private AtlasRegion botonUp;
	private String nombre;
	private String pantallaBack;
	private String selSombrero;
	private String selCuello;
	private String selLentes;

	public ActorBoton(String name, float sizeX, float sizeY, final Boolean setPantalla, final Principal game,
			final AdsController adsController) {

		nombre = name;
		atlasBoton = ResourceManager.manager.get(ResourceManager.ATLAS_ELEMENTOS);

		press(false);

		setSize(sizeX, sizeY);

		addListener(new InputListener() {

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				this.press(true);
			}

			private void press(boolean press) {
				if (press) {

					botonUp = atlasBoton.findRegion(nombre + "Down");
				} else {
					botonUp = atlasBoton.findRegion(nombre + "Up");
				}

			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				this.press(false);
				Sounds.sndBtn.play(Sounds.getMasterVol());

				if (setPantalla) {
					if (nombre == "btnConfig") {

						game.setScreen(new PantallaConfig(game, adsController));
					} else if (nombre == "btnCompras") {
						game.setScreen(new PantallaCompras(game, adsController));
					} else if (nombre == "btnCerrar" || nombre == "btnHome") {

						game.setScreen(new PantallaInicio(game, adsController));

					} else if (nombre == "btnItems" || nombre == "btnHome") {
						game.setScreen(new PantallaItems(game, pantallaBack, adsController));
					} else if (nombre == "btnVolver") {
						ResourceManager.preferences.putString("ojos", selLentes);
						ResourceManager.preferences.putString("cabeza", selSombrero);
						ResourceManager.preferences.putString("cuello", selCuello);
						ResourceManager.preferences.flush();
						if (pantallaBack == "pantallaConfig") {

							game.setScreen(new PantallaConfig(game, adsController));
						} else if (pantallaBack == "pantallaInicio") {

							game.setScreen(new PantallaInicio(game, adsController));
						} else if (pantallaBack == "pantallaJuego") {

							game.setScreen(new PantallaJuego(game, adsController));
						}

					}

				} else {

					game.setScreen(new PantallaJuego(game, adsController));

				}
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

				super.touchUp(event, x, y, pointer, button);
			}

		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.draw(botonUp, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());

	}

	@Override
	public void act(float delta) {

	}

	public void press(Boolean press) {
		if (press) {

			botonUp = atlasBoton.findRegion(nombre + "Down");
		} else {
			botonUp = atlasBoton.findRegion(nombre + "Up");
		}
	}

	public void setPantallaBack(String pantalla) {
		pantallaBack = pantalla;
	}

	public void setItems(String cabeza, String cuello, String ojos){
		selSombrero = cabeza;
		selCuello = cuello;
		selLentes = ojos;
	}
	@Override
	public void dispose() {

		atlasBoton.dispose();
		ResourceManager.dispose();

		System.gc();

	}
}