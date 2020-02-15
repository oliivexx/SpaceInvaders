/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PantallaJuego implements Screen {
  	final MainInvaders game;
        //enemigos
	Texture imagenbicho;
        Texture imagenbicho2;
        Texture imagenbicho3;
        Texture imagenbicho4;
        Texture imagenBala;
        
        Texture fondo;
	Texture imagenNave;
        
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
        
	Rectangle naveEspacial;
        
	Array<Rectangle> arrayBichos;
        Array<Rectangle> arrayBichos2;
        Array<Rectangle> arrayBichos3;
        Array<Rectangle> arrayBichos4;
        Array<Rectangle> arrayBalas;
        
	long ultimoSpawnBicho1;
        long ultimoSpawnBicho2;
        long ultimoSpawnBicho3;
        long ultimoSpawnBicho4;
	int contador;
        float xinicial2;
        float ultimabala;
        float tiempoInicial = 0;

        //Constructor
	public PantallaJuego(final MainInvaders gam) {
		this.game = gam;

		// Cargamos las imagenes
		imagenbicho = new Texture(Gdx.files.internal("bicho.png"));
                imagenbicho2 = new Texture(Gdx.files.internal("bicho2.png"));
                imagenbicho3 = new Texture(Gdx.files.internal("bicho3.png"));
                imagenbicho4 = new Texture(Gdx.files.internal("bicho4.png"));
		imagenNave = new Texture(Gdx.files.internal("nave.png"));
                fondo = new Texture(Gdx.files.internal("fondo.jpg"));
                imagenBala = new Texture(Gdx.files.internal("bala.png"));

		// cargamos sonidos
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// creamos camara y SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// representamlos la nave
		naveEspacial = new Rectangle();
		naveEspacial.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		naveEspacial.y = 20; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		naveEspacial.width = 64;
		naveEspacial.height = 64;

		// create the raindrops array and spawn the first raindrop
		arrayBichos = new Array<>();
                arrayBichos2 = new Array<>();
                arrayBichos3 = new Array<>();
                arrayBichos4 = new Array<>();
                arrayBalas = new Array<>();
                

	}


	@Override
	public void render(float delta) {
                //limpiamos pantalla
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
                
                noSalirPantalla();
                leerTeclado();
                pintarObjetos();
                tiempoInicial = System.currentTimeMillis();

		// comprobamos metodo para crear enemigos
		if (System.currentTimeMillis() - ultimoSpawnBicho1 > 1000)
			spawnEnemigo1();
                if (System.currentTimeMillis() - ultimoSpawnBicho2 > 2000)
			spawnEnemigo2();  
                if (System.currentTimeMillis() - ultimoSpawnBicho3 > 2000){
			spawnEnemigo3();
                }
                
                if (System.currentTimeMillis() - ultimoSpawnBicho4 > 6000){
			spawnEnemigo4();
                }
                
                
                
                
                                      
                movimientoEnemigo1();
                movimientoEnemigo2();
                movimientoEnemigo3();
                movimientoEnemigo4();
                movimientoBala();
	}
        
        private void spawnEnemigo1() {
		Rectangle bicho1 = new Rectangle();
		bicho1.x = MathUtils.random(0, 800 - 64);
		bicho1.y = 480;
		bicho1.width = 64;
		bicho1.height = 64;
		arrayBichos.add(bicho1);
		ultimoSpawnBicho1 = System.currentTimeMillis();
	}
        
        public void spawnEnemigo2(){
            Rectangle bicho2 = new Rectangle();
		bicho2.x = MathUtils.random(0, 800 - 64);
		bicho2.y = 480;
		bicho2.width = 64;
		bicho2.height = 64;
		arrayBichos2.add(bicho2);
		ultimoSpawnBicho2 = System.currentTimeMillis();
        }
        
        public void spawnEnemigo3(){
            Rectangle bicho3 = new Rectangle();
		bicho3.x = MathUtils.random(0, 800 - 64);
		bicho3.y = 480;
		bicho3.width = 64;
		bicho3.height = 64;
		arrayBichos3.add(bicho3);
		ultimoSpawnBicho3 = System.currentTimeMillis();
        }
        
        public void spawnEnemigo4(){
            Rectangle bicho4 = new Rectangle();
		bicho4.x = MathUtils.random(0, 800 - 64);
		bicho4.y = 480;
		bicho4.width = 64;
		bicho4.height = 64;
		arrayBichos4.add(bicho4);
		ultimoSpawnBicho4 = System.currentTimeMillis();
        }
        
        
        
        public void movimientoEnemigo1(){
            Iterator<Rectangle> iter = arrayBichos.iterator();
            while (iter.hasNext()) {
			Rectangle enemigo = iter.next();
			enemigo.y -= 200 * Gdx.graphics.getDeltaTime();
			if (enemigo.y + 64 < 0)
				iter.remove();
                        
			if (enemigo.overlaps(naveEspacial)) {
				contador++;
				iter.remove();
                                game.setScreen(new pantallaFinal(game));
                                dispose();
			}
                        
                        for (Rectangle bala : arrayBalas){
                            if(bala.x > enemigo.x && bala.x < enemigo.x + 64 && bala.y >= enemigo.y){
                                    iter.remove();
                            }
                        }
                        
		}
        }
        
       public void movimientoEnemigo2(){
           Iterator<Rectangle> iter2 = arrayBichos2.iterator();
                while (iter2.hasNext()) {
			Rectangle enemigo2 = iter2.next();
			enemigo2.y -= 145* Gdx.graphics.getDeltaTime();
                        xinicial2 = (float) Math.random()*10;

                        if(xinicial2 < 5){
                            enemigo2.x += 100 * Gdx.graphics.getDeltaTime();
                        }else{
                            enemigo2.x -= 100 * Gdx.graphics.getDeltaTime();
                        }
                        
			if (enemigo2.y + 64 < 0)
				iter2.remove();
			if (enemigo2.overlaps(naveEspacial)) {
				contador++;
				iter2.remove();
                                game.setScreen(new pantallaFinal(game));
                                dispose();
			}
                        
                        //matar enemigo 2
                        for (Rectangle bala : arrayBalas){
                            if(bala.x >= enemigo2.x && bala.x <= enemigo2.x + 64){
                                if(bala.y >= enemigo2.y)
                                    iter2.remove();
                            }
                        }
		}
       }
       
       public void movimientoEnemigo3(){
           Iterator<Rectangle> iter3 = arrayBichos3.iterator();
                while (iter3.hasNext()) {
			Rectangle enemigo3 = iter3.next();
			enemigo3.y -= 550 * Gdx.graphics.getDeltaTime();


			if (enemigo3.y + 64 < 0)
				iter3.remove();
			if (enemigo3.overlaps(naveEspacial)) {
				contador++;
				iter3.remove();
                                game.setScreen(new pantallaFinal(game));
                                dispose();
			}
                        
                        //matar enemigo 3
                        for (Rectangle bala : arrayBalas){
                            if(bala.x >= enemigo3.x && bala.x <= enemigo3.x + 64){
                                if(bala.y >= enemigo3.y)
                                    iter3.remove();
                            }
                        }
		}
       }
       
       public void movimientoEnemigo4(){
           Iterator<Rectangle> iter4 = arrayBichos4.iterator();
                while (iter4.hasNext()) {
			Rectangle enemigo4 = iter4.next();
			enemigo4.y -= 500 * Gdx.graphics.getDeltaTime();
                        enemigo4.x -= 50 * Gdx.graphics.getDeltaTime();
                        enemigo4.x += 150 * Gdx.graphics.getDeltaTime();
  
                        
			if (enemigo4.y + 64 < 0)
				iter4.remove();
			if (enemigo4.overlaps(naveEspacial)) {
				contador++;
				iter4.remove();
                                game.setScreen(new pantallaFinal(game));
                                dispose();
			}
                        
                        //matar enemigo 4
                        for (Rectangle bala : arrayBalas){
                            if(bala.x >= enemigo4.x && bala.x <= enemigo4.x + 64){
                                if(bala.y >= enemigo4.y)
                                    iter4.remove();
                            }
                        }
		}
       }

        public void spawnBala(){
            Rectangle bala = new Rectangle();
		bala.x = naveEspacial.x + 32;
		bala.y = 84;
		bala.width = 14;
		bala.height = 35;
		arrayBalas.add(bala);
                ultimabala = System.currentTimeMillis();
        }
        
        public void movimientoBala(){
        Iterator<Rectangle> iter = arrayBalas.iterator();
		while (iter.hasNext()) {
			Rectangle bala = iter.next();
			bala.y += 350 * Gdx.graphics.getDeltaTime();
                        
			if (bala.y + 35 > 480)
                            iter.remove();
                        
                        for (Rectangle enemigo : arrayBichos) {
                        /*
                            if (bala.overlaps(enemigo)) {
                                    contador++;
                                    dropSound.play();
                                    iter.remove();
                                    dispose();
                            }
                            */
                        }

		}
        }
        public void pintarObjetos(){
        game.batch.begin();
		game.batch.setProjectionMatrix(camera.combined);

		// dibujar();
                game.batch.draw(fondo, 0, 0);
		game.batch.draw(imagenNave, naveEspacial.x, naveEspacial.y);
                
                //pintar enemigo 1
		for (Rectangle bichoPintar : arrayBichos) {
			game.batch.draw(imagenbicho, bichoPintar.x, bichoPintar.y);
		}
                
                
                for (Rectangle bichoPintar2 : arrayBichos2) {
			game.batch.draw(imagenbicho2, bichoPintar2.x, bichoPintar2.y);
		}
                
                for (Rectangle bichoPintar3 : arrayBichos3) {
			game.batch.draw(imagenbicho3, bichoPintar3.x, bichoPintar3.y);
		}
                
              for (Rectangle bichoPintar4 : arrayBichos4) {
			game.batch.draw(imagenbicho4, bichoPintar4.x, bichoPintar4.y);
		}
                
                
                
                
                for (Rectangle bala : arrayBalas) {
			game.batch.draw(imagenBala, bala.x, bala.y);
		}
                
                

                
		game.batch.end();
        }
        

        public void noSalirPantalla(){
        	// no se sale de la pantalla
		if (naveEspacial.x < 0)
			naveEspacial.x = 0;
		if (naveEspacial.x > 800 - 64)
			naveEspacial.x = 800 - 64;   
                if (naveEspacial.y < 0 )
			naveEspacial.y = 0;
                if (naveEspacial.y > 480 - 64)
			naveEspacial.y = 480 - 64;
      
        }
        
        public void leerTeclado(){
            if (Gdx.input.isKeyPressed(Keys.LEFT))
		naveEspacial.x -= 450 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
		naveEspacial.x += 450 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.UP))
		naveEspacial.y += 250 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.DOWN))
		naveEspacial.y -= 250 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.SPACE)){
                spawnBala();
            }
                
        }
        


	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		imagenbicho.dispose();
		imagenNave.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
        
        

}
