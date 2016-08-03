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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PantallaItems extends Pantalla {

	private FitViewport vp;
	private Stage escenario;
	private Skin skin;
	private TextureAtlas atlas;
	private ActorBoton btnVolver;
	private ActorTexture fondo;
	private ActorAnimado pinguino;
	private ActorGrafico caraPinguino;
	private ActorGrafico pieL;
	private ActorGrafico pieR;
	private ActorGrafico sombra;
	private ActorNinePatch panel;
	private ActorNinePatch sombraPanel;
	private Group grpPinguino;

	private ActorGrafico[] lentes = new ActorGrafico[5];
	private ActorGrafico[] sombrero = new ActorGrafico[5];
	private ActorGrafico[] cuello = new ActorGrafico[5];

	private ActorGrafico lentePinguino;
	private ActorGrafico sombreroPinguino;
	private ActorGrafico cuelloPinguino;
	private Button tab1;
	private Button tab2;
	private Button tab3;
	private Table tblLentes;
	private Table tblSombrero;
	private Table tblCuello;
	private Table main;

	private String selLentes;
	private String selSombrero;
	private String selCuello;
	private Integer numDesbloqueo;

	private I18NBundle bundle;
	private Label lblItems;
	private TextButton btnApply;
	// Styles
	private TextButtonStyle btnStyle;
	private LabelStyle styleWhite;

	private String pantallaPrev;

	private ButtonGroup tabs;

	public PantallaItems(final Principal game, String pantalla, final AdsController adsController) {
		super(game, adsController);
		this.game = game;
		this.adsController = adsController;
		adsController.hideBannerAd();
		pantallaPrev = pantalla;

		vp = new FitViewport(720, 1280);

		escenario = new Stage(vp);

		Gdx.input.setInputProcessor(escenario);

		bundle = ResourceManager.getSelectedBundle();

		skin = new Skin();
		atlas = ResourceManager.manager.get(ResourceManager.ATLAS_SKIN);
		skin.addRegions(atlas);

		panel = new ActorNinePatch("panel", 60, 60, 70, 70);
		panel.setSize(656, 666);
		panel.setPosition((escenario.getWidth() / 2) - (panel.getWidth() / 2), 420);

		sombraPanel = new ActorNinePatch("sombraPanel", 0, 0, 10, 10);
		sombraPanel.setSize(656, 35);
		sombraPanel.setPosition((escenario.getWidth() / 2) - (sombraPanel.getWidth() / 2), 925);

		// styles
		styleWhite = new LabelStyle(ResourceManager.getBigFont(), Color.WHITE);

		btnStyle = new TextButtonStyle();
		btnStyle.up = skin.getDrawable("btnDefUp");
		btnStyle.down = skin.getDrawable("btnDefDown");
		btnStyle.font = ResourceManager.getMediumFont();

		btnApply = new TextButton(bundle.get("lblApply"), btnStyle);
		btnApply.setSize(550, 104);
		btnApply.setPosition((escenario.getWidth() / 2) - (btnApply.getWidth() / 2), 32);

		fondo = new ActorTexture("fondoConfig.jpg", 720, 1280);
		btnVolver = new ActorBoton("btnVolver", 116, 156, true, game, adsController);
		btnVolver.setPosition(32, 1248 - btnVolver.getHeight());
		btnVolver.setPantallaBack(pantallaPrev);
		

		lblItems = new Label("Items", styleWhite);
		lblItems.setPosition(208, 1140);
		lblItems.setAlignment(Align.left);

		selLentes = ResourceManager.preferences.getString("ojos");
		selSombrero = ResourceManager.preferences.getString("cabeza");
		selCuello = ResourceManager.preferences.getString("cuello");

		btnVolver.setItems(selSombrero, selCuello, selLentes);
		// TabbedPane
		numDesbloqueo = ResourceManager.preferences.getInteger("Un");

		main = new Table();
		main.setFillParent(true);
		main.pad(230, 64, 300, 64);

		HorizontalGroup group = new HorizontalGroup();
		tab1 = new ImageButton(skin.getDrawable("tabOjosDis"), skin.getDrawable("tabOjosEn"),
				skin.getDrawable("tabOjosEn"));
		tab2 = new ImageButton(skin.getDrawable("tabCabezaDis"), skin.getDrawable("tabCabezaEn"),
				skin.getDrawable("tabCabezaEn"));
		tab3 = new ImageButton(skin.getDrawable("tabCuelloDis"), skin.getDrawable("tabCuelloEn"),
				skin.getDrawable("tabCuelloEn"));

		group.addActor(tab1);
		group.addActor(tab2);
		group.addActor(tab3);
		main.add(tab1).padLeft(20).padRight(10);
		main.add(tab2).padLeft(10).padRight(5);
		main.add(tab3).padLeft(10).padRight(20);
		main.row();

		Stack content = new Stack();
		for (int i = 0; i < 5; i++) {
			String name = "imgLentes0" + String.valueOf(i);
			lentes[i] = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, name, 153, 153);

		}

		if (numDesbloqueo < 10) {
			lentes[4].setImagen("imgItemBlock");
			lentes[4].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 7) {
			lentes[3].setImagen("imgItemBlock");
			lentes[3].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 4) {
			lentes[2].setImagen("imgItemBlock");
			lentes[2].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 1) {
			lentes[1].setImagen("imgItemBlock");
			lentes[1].setTouchable(Touchable.disabled);
		}

		for (int i = 0; i < 5; i++) {
			String name = "imgCabeza0" + String.valueOf(i);
			sombrero[i] = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, name, 153, 153);
		}
		if (numDesbloqueo < 11) {
			sombrero[4].setImagen("imgItemBlock");
			sombrero[4].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 8) {
			sombrero[3].setImagen("imgItemBlock");
			sombrero[3].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 5) {
			sombrero[2].setImagen("imgItemBlock");
			sombrero[2].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 2) {
			sombrero[1].setImagen("imgItemBlock");
			sombrero[1].setTouchable(Touchable.disabled);
		}

		for (int i = 0; i < 5; i++) {
			String name = "imgCuello0" + String.valueOf(i);
			cuello[i] = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, name, 153, 153);

		}

		if (numDesbloqueo < 12) {
			cuello[4].setImagen("imgItemBlock");
			cuello[4].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 9) {
			cuello[3].setImagen("imgItemBlock");
			cuello[3].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 6) {
			cuello[2].setImagen("imgItemBlock");
			cuello[2].setTouchable(Touchable.disabled);
		}
		if (numDesbloqueo < 3) {
			cuello[1].setImagen("imgItemBlock");
			cuello[1].setTouchable(Touchable.disabled);
		}

		tblLentes = new Table();
		tblLentes.setFillParent(true);
		tblLentes.padBottom(200);
		tblLentes.padTop(70);
		tblLentes.add(lentes[0]).expandX();
		tblLentes.add(lentes[1]).expandX();
		tblLentes.add(lentes[2]).expandX();
		tblLentes.row();
		Table lent2 = new Table();
		lent2.add(lentes[3]).expandX().pad(20);
		lent2.add(lentes[4]).expandX().pad(20);
		tblLentes.add(lent2).colspan(3).expandX().padTop(10);

		tblSombrero = new Table();
		tblSombrero.setFillParent(true);
		tblSombrero.padBottom(200);
		tblSombrero.padTop(70);
		tblSombrero.add(sombrero[0]).expandX();
		tblSombrero.add(sombrero[1]).expandX();
		tblSombrero.add(sombrero[2]).expandX();
		tblSombrero.row();
		Table som2 = new Table();
		som2.add(sombrero[3]).expandX().pad(20);
		som2.add(sombrero[4]).expandX().pad(20);
		tblSombrero.add(som2).colspan(3).expandX().padTop(10);

		tblCuello = new Table();
		tblCuello.setFillParent(true);
		tblCuello.padBottom(200);
		tblCuello.padTop(70);
		tblCuello.add(cuello[0]).expandX();
		tblCuello.add(cuello[1]).expandX();
		tblCuello.add(cuello[2]).expandX();
		tblCuello.row();
		Table cue2 = new Table();
		cue2.add(cuello[3]).expandX().pad(20);
		cue2.add(cuello[4]).expandX().pad(20);
		tblCuello.add(cue2).colspan(3).expandX().padTop(10);

		content.addActor(tblLentes);
		content.addActor(tblSombrero);
		content.addActor(tblCuello);

		main.add(content).colspan(3).expand().fill();

		ChangeListener tab_listener = new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tblLentes.setVisible(tab1.isChecked());
				tblSombrero.setVisible(tab2.isChecked());
				tblCuello.setVisible(tab3.isChecked());
			}

		};

		InputListener listener = new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				super.exit(event, x, y, pointer, toActor);
			}
		};
		tab1.addListener(tab_listener);
		tab1.addListener(listener);
		tab2.addListener(tab_listener);
		tab2.addListener(listener);
		tab3.addListener(tab_listener);
		tab3.addListener(listener);

		tabs = new ButtonGroup();
		tabs.setMinCheckCount(1);
		tabs.setMaxCheckCount(1);
		tabs.add(tab1);
		tabs.add(tab2);
		tabs.add(tab3);
		// main.debug();

		// Pinguino

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

		sombra = new ActorGrafico(ResourceManager.ATLAS_ELEMENTOS, "sombra", 220, 68);
		sombra.setPosition(250, 150);

		grpPinguino = new Group();
		grpPinguino.addActor(pieL);
		grpPinguino.addActor(pieR);
		grpPinguino.addActor(pinguino);
		grpPinguino.addActor(sombreroPinguino);
		grpPinguino.addActor(cuelloPinguino);
		grpPinguino.addActor(caraPinguino);
		grpPinguino.addActor(lentePinguino);

		grpPinguino.setPosition(155, 170);

		escenario.addActor(fondo);
		escenario.addActor(btnVolver);
		escenario.addActor(lblItems);
		escenario.addActor(panel);
		escenario.addActor(sombraPanel);
		escenario.addActor(main);
		escenario.addActor(sombra);
		escenario.addActor(grpPinguino);
		escenario.addActor(btnApply);

		lentes[0].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				lentePinguino.setImagen("itemLentes00");
				selLentes = "itemLentes00";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lentes[1].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				lentePinguino.setImagen("itemLentes01");
				selLentes = "itemLentes01";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lentes[2].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				lentePinguino.setImagen("itemLentes02");
				selLentes = "itemLentes02";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lentes[3].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				lentePinguino.setImagen("itemLentes03");
				selLentes = "itemLentes03";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		lentes[4].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				lentePinguino.setImagen("itemLentes04");
				selLentes = "itemLentes04";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});

		sombrero[0].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				sombreroPinguino.setImagen("itemSombrero00");
				selSombrero = "itemSombrero00";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		sombrero[1].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				sombreroPinguino.setImagen("itemSombrero01");
				selSombrero = "itemSombrero01";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		sombrero[2].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				sombreroPinguino.setImagen("itemSombrero02");
				selSombrero = "itemSombrero02";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		sombrero[3].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				sombreroPinguino.setImagen("itemSombrero03");
				selSombrero = "itemSombrero03";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		sombrero[4].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				sombreroPinguino.setImagen("itemSombrero04");
				selSombrero = "itemSombrero04";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});

		cuello[0].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				cuelloPinguino.setImagen("itemCuello00");
				selCuello = "itemCuello00";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		cuello[1].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				cuelloPinguino.setImagen("itemCuello01");
				selCuello = "itemCuello01";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		cuello[2].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				cuelloPinguino.setImagen("itemCuello02");
				selCuello = "itemCuello02";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		cuello[3].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				cuelloPinguino.setImagen("itemCuello03");
				selCuello = "itemCuello03";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		cuello[4].addListener(new InputListener() {
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				Sounds.sndBtn.play(Sounds.getMasterVol());
				cuelloPinguino.setImagen("itemCuello04");
				selCuello = "itemCuello04";
				btnVolver.setItems(selSombrero, selCuello, selLentes);
				super.exit(event, x, y, pointer, toActor);
			}
		});
		btnApply.addListener(new InputListener() {

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				ResourceManager.preferences.putString("ojos", selLentes);
				ResourceManager.preferences.putString("cabeza", selSombrero);
				ResourceManager.preferences.putString("cuello", selCuello);
				ResourceManager.preferences.flush();

				if (pantallaPrev == "pantallaConfig") {
					game.setScreen(new PantallaConfig(game, adsController));
				} else {
					game.setScreen(new PantallaJuego(game, adsController));
				}
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
		escenario.dispose();
		skin.dispose();
		atlas.dispose();
		btnVolver.dispose();
		fondo.dispose();
		pinguino.dispose();
		caraPinguino.dispose();
		pieL.dispose();
		pieR.dispose();
		sombra.dispose();
		panel.dispose();
		sombraPanel.dispose();
		lentes[0].dispose();
		lentes[1].dispose();
		lentes[2].dispose();
		lentes[3].dispose();
		lentes[4].dispose();
		lentes[5].dispose();
		sombrero[0].dispose();
		sombrero[1].dispose();
		sombrero[2].dispose();
		sombrero[3].dispose();
		sombrero[4].dispose();
		sombrero[5].dispose();
		cuello[0].dispose();
		cuello[1].dispose();
		cuello[2].dispose();
		cuello[3].dispose();
		cuello[4].dispose();
		cuello[5].dispose();

		lentePinguino.dispose();
		sombreroPinguino.dispose();
		cuelloPinguino.dispose();

		ResourceManager.dispose();

		System.gc();
	}

}
