/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Antonio
 */
public class pantallaFinal implements Screen {
        final MainInvaders game;
	OrthographicCamera camera;
        Texture pantallaFinal;

    public pantallaFinal(MainInvaders game) {
        this.game = game;
        camera = new OrthographicCamera();
	camera.setToOrtho(false, 800, 480);
        pantallaFinal = new Texture(Gdx.files.internal("pantallaFinal.jpg"));
    }
        
        

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
                Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
                game.batch.draw(pantallaFinal, delta, delta);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new PantallaJuego(game));
			dispose();
		}
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
