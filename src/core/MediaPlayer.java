package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MediaPlayer extends Thread {
	private List<String> caminhos = new ArrayList<String>();
	private int atual;
	private Clip pl;
	private String path;
	private AudioInputStream ad;

	public MediaPlayer(String path) throws LineUnavailableException,
			ZipException, IOException {
		this.path = path;
		this.pl = AudioSystem.getClip();
		this.atual = 0;

		File f = new File(this.getClass().getResource(path).getPath());

		if (f.getPath().contains(".jar")) {
			caminhos = carregaMusicaJar(path);
		} else {
			this.caminhos = Arrays.asList(f.list());

			for (int i = 0; i < caminhos.size(); i++) {
				caminhos.set(i, path + "/" + caminhos.get(i));
			}
		}

		this.geraPlaylist();

	}

	@Override
	public void run() {
		try {
			tocaMusica();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void tocaMusica() throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		ad = AudioSystem.getAudioInputStream(this.getClass().getResource(
				caminhos.get(atual)));
		pl.open(ad);
		pl.start();
	}
	
	public void continuaMusica(){
		pl.start();
	}

	public void passaMusica() {
		if (atual >= caminhos.size() - 1) {
			atual = 0;
		} else {
			atual++;
		}

		flushFecha();
	}

	public void voltaMusica() {
		if (atual <= 0) {
			atual = caminhos.size() - 1;
		} else {
			atual--;
		}

		flushFecha();
	}

	public void flushFecha() {
		pl.flush();
		pl.close();
	}

	public void geraPlaylist() {
		Random gerador = new Random();
		String aux;
		int novoind;

		for (int i = 0; i < caminhos.size(); i++) {
			novoind = gerador.nextInt(caminhos.size() - 1);
			aux = caminhos.get(novoind);
			caminhos.set(novoind, caminhos.get(i));
			caminhos.set(i, aux);
		}
	}

	public List<String> carregaMusicaJar(String path) throws ZipException,
			IOException {
		ZipFile zf = new ZipFile(new File(this.getClass().getProtectionDomain()
				.getCodeSource().getLocation().getPath()));

		Enumeration<? extends ZipEntry> entries = zf.entries();

		ZipEntry ze = null;

		while (entries.hasMoreElements()) {
			ze = entries.nextElement();

			if (ze.getName().endsWith(".wav")) {
				caminhos.add("/" + ze.getName());
			}
		}

		return caminhos;

	}
	
	public void pausa(){
		pl.stop();
	}

	public int getAtual() {
		return atual;
	}

	public void setAtual(int atual) {
		this.atual = atual;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Clip getPl() {
		return pl;
	}

	public void setPl(Clip pl) {
		this.pl = pl;
	}

	public AudioInputStream getAd() {
		return ad;
	}

	public void setAd(AudioInputStream ad) {
		this.ad = ad;
	}

	public void setListener(PlayerListener teste) {
		pl.addLineListener(teste);
	}

}