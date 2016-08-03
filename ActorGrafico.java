package com.apps.crashpenguin.actores;

import com.apps.crashpenguin.utilidades.ResourceManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class ActorGrafico extends Actor implements Disposable {
	private TextureAtlas atlas;
	private TextureRegion region;
	private String nombre; 

	public ActorGrafico(String resAtlas, String name, float sizeX, float sizeY) {

		atlas = ResourceManager.manager.get(resAtlas);
		region = atlas.findRegion(name);
		nombre = name;
		setSize(sizeX, sizeY);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	public void setImagen(String nameTexture) {

		region = atlas.findRegion(nameTexture);
		nombre = nameTexture;
	}
	
	public String getImagenName(){
		return nombre;
		
	}

	@Override
	public void dispose() {
		atlas.dispose();
		ResourceManager.dispose();
		System.gc();

	}

}
