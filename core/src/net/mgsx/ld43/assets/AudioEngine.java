package net.mgsx.ld43.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;

public class AudioEngine {

	public static AudioEngine i;
	
	private ObjectMap<Integer, Music> musics = new ObjectMap<Integer, Music>();
	private ObjectMap<Integer, Sound> sounds = new ObjectMap<Integer, Sound>();
	
	private Music currentMusic;
	
	private static float SFXVolume = .5f;
	
	public static final boolean enabled = true;
	
	public AudioEngine() 
	{
		if(!enabled) return;
		
		songs(0, "PO arrivee sur ile.mp3");
		songs(1, "PO credits.mp3");
		songs(2, "PO intro.mp3");
		songs(3, "PO main theme.mp3");
		songs(4, "PO main theme tenu.mp3");
		songs(5, "PO taverne.mp3");
		
		sfx(0, "baril explose.wav");
		sfx(1, "bateau coule.wav");
		sfx(2, "bateau coule avec pirate.wav");
		sfx(3, "bateau en dange.wav");
		sfx(4, "bulle eau.wav");
		sfx(5, "canon 1.wav");
		sfx(6, "canon 2.wav");
		sfx(7, "mat 1.wav");
		sfx(8, "mat 2.wav");
		sfx(9, "mat 3.wav");
		sfx(10, "pirate lance 1.wav");
		sfx(11, "pirate lance 2.wav");
		sfx(12, "requin mache humain.wav");
		
		sfx(13, "bip.wav");
		sfx(14, "canon 5.wav");
		sfx(15, "game over 2.wav");
		sfx(16, "game over.wav");
		sfx(17, "impact requin.wav");
		sfx(18, "jet objet.wav");
		sfx(19, "loose.wav");
		sfx(20, "mat court.wav");
		sfx(21, "perroquet.wav");
		sfx(22, "reparation2.wav");
		sfx(23, "reparation.wav");
		sfx(24, "requin mange bateau.wav");
		sfx(25, "requin mange bateau 2.wav");
		sfx(26, "requin mange bateau 3.wav");
		sfx(27, "requin mange humain 2.wav");
		sfx(28, "requin touche.wav");
	}

	private void songs(int i, String name) {
		musics.put(i, Gdx.audio.newMusic(Gdx.files.internal("audio/songs/" + name)));
	}
	private void sfx(int i, String name) {
		sounds.put(i, Gdx.audio.newSound(Gdx.files.internal("audio/sfx/" + name)));
	}
	
	public void playSFX(int index){
		System.out.println("sfx " + index);
		if(!enabled) return;
		
		Sound sound = sounds.get(index);
		if(sound != null){
			sound.play(SFXVolume);
		}
	}
	
	public void playMusic(int index)
	{
		if(!enabled) return;
		
		if(currentMusic != null){
			currentMusic.stop();
			currentMusic = null;
		}
		
		currentMusic = musics.get(index);
		if(currentMusic != null){
			currentMusic.setLooping(true);
			currentMusic.play();
		}
	}

	public void playSFXRandom(int ... snds) {
		int i = MathUtils.random(snds.length-1);
		playSFX(snds[i]);
	}
	
}
