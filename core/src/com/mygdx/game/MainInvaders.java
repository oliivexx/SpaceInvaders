package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainInvaders extends Game {

  	SpriteBatch batch;
	public BitmapFont font;

          @Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new PantallaInicio(this));
	}

          @Override
	public void render() {
		super.render(); // important!
	}

          @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
