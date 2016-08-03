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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaCompras extends Pantalla {

	private Table tabla;
	private ActorTexture fondo;
	private ActorNinePatch panel;
	private ActorGrafico icono;
	private ActorBoton btnCerrar;
	private TextButton btnComprar;

	private Label lblStore;
	private Label lblRemove;

	private Skin skin;
	private Viewport vp;
	private Stage escenario;
	private TextureAtlas atlas;
	
	private I18NBundle bundle;

	// Styles
	private LabelStyle styleWhite;
	private LabelStyle styleGray;
	private LabelStyle styleGrayer;
	private TextButtonStyle btnStyle;
	
	
	public PantallaCompras(Principal game, AdsController adsController) {
		super(game,adsController);
		this.game = game;
		this.adsController = adsController;
		
		adsController.showBannerAd();
	}

	@Override
	public void show() {

		
		vp = new FitViewport(720, 1280);

		escenario = new Stage(vp);
		tabla = new Table();

		Gdx.input.setInputProcessor(escenario);
		
		bundle = ResourceManager.getSelectedBundle();
		
	
		skin = new Skin();
		atlas = ResourceManager.manager.get(ResourceManager.ATLAS_SKIN);
		skin.addRegions(atlas);

		fondo = new ActorTexture("fondoConfig.jpg", 720, 1280);
		panel = new ActorNinePatch("panel", 60, 60, 70, 70);
		panel.setSize(656, 410);
		panel.setOriginX(panel.getWidth() / 2);
		panel.setPosition(escenario.getWidth() / 2 - panel.getOriginX(), 675);

		styleWhite = new LabelStyle(ResourceManager.getBigFont(), Color.WHITE);
		styleGray = new LabelStyle(ResourceManager.getSmallFont(), Color.valueOf("a0a0a0"));
		styleGrayer = new LabelStyle(ResourceManager.getMediumFont(), Color.valueOf("888888"));

		lblStore = new Label(bundle.get("titleShop"), styleWhite);
		lblStore.setPosition(208, 1140);
		lblStore.setAlignment(Align.left);

		lblRemove = new Label(bundle.get("textStore"), styleGray);
	
		icono = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "icon", 105, 105);

		btnCerrar = new ActorBoton("btnCerrar", 116, 156, true, game,adsController);
		btnCerrar.setPosition(32, 1248 - btnCerrar.getHeight());

		// Asignar estilo al botón (poner imágenes)
		btnStyle = new TextButtonStyle();
		btnStyle.up = skin.getDrawable("btnDefUp");
		btnStyle.down = skin.getDrawable("btnDefDown");

		btnStyle.font = ResourceManager.getMediumFont();

		btnComprar = new TextButton("$0,99", btnStyle);
		btnComprar.setSize(300, 104);

		tabla.setFillParent(true);
		tabla.pad(16, 40, 510, 40);
	

		tabla.add(lblRemove).align(Align.center).padBottom(25);
		tabla.row();
		tabla.add(icono).expandX().padBottom(25);
		tabla.row();
		//tabla.add(lblPrice).expandX();
		tabla.add(btnComprar).size(300, 104).expandX();

		escenario.addActor(fondo);
		escenario.addActor(panel);
		escenario.addActor(btnCerrar);
		escenario.addActor(lblStore);
		escenario.addActor(tabla);
		
		btnComprar.addListener(new InputListener(){
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
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

	@Override
	public void dispose() {
		super.dispose();
		this.dispose();
		fondo.dispose();
		panel.dispose();
		icono.dispose();
		btnCerrar.dispose();
		skin.dispose();
		escenario.dispose();
		atlas.dispose();
			
		ResourceManager.dispose();
		System.gc();
	}
}
