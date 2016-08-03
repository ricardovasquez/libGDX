package com.apps.crashpenguin.pantallas;

import com.apps.crashpenguin.AdsController;
import com.apps.crashpenguin.Pantalla;
import com.apps.crashpenguin.Principal;
import com.apps.crashpenguin.actores.ActorLogo;
import com.apps.crashpenguin.utilidades.ResourceManager;
import com.apps.crashpenguin.utilidades.Sounds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaSplash extends Pantalla {

	private Stage escenario;
	private Viewport vp;
	private ActorLogo logo;
	private float showTime = 3.0f;
	
	public PantallaSplash(Principal game, AdsController adsController) {
		super(game, adsController);
	
		adsController.loadBannerAd();
	}

	@Override
	public void show() {
		vp = new FitViewport(720, 1280);

		escenario = new Stage(vp);

		logo = new ActorLogo();
		logo.setPosition((escenario.getWidth() / 2) - (logo.getWidth() / 2),
				(escenario.getHeight() / 2) - (logo.getHeight() / 2));

		escenario.addActor(logo);
		
	

	}

	
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0.667f, 0.231F, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		escenario.draw();
		escenario.act();
		if (ResourceManager.manager.update() && showTime <= 1f) {
			logo.setColor(1, 1, 1, 1f - 0.1f * delta);
			Sounds.load();
			ResourceManager.load();
			

			if (showTime <= 0) {

				game.setScreen(new PantallaInicio(game, adsController));
			}
		}

		showTime -= delta;
	}

	@Override
	public void dispose() {
		super.dispose();
		this.dispose();
		logo.dispose();
		escenario.dispose();
		ResourceManager.dispose();
		Sounds.dispose();
		System.gc();

	}

	@Override
	public void resize(int width, int height) {
		escenario.getViewport().update(width, height);
	}

	@Override
	public void resume() {

		super.resume();
		ResourceManager.manager.finishLoading();
	}
}
