package com.apps.crashpenguin.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ActorLogo extends Actor implements Disposable {

	private Texture logo;

	public ActorLogo() {

		logo = new Texture("logoCarga.png");
		setSize(516, 280);

	}

	@Override
	public void act(float delta) {

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(logo, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void dispose() {
		this.dispose();
		logo.dispose();

	}

}
