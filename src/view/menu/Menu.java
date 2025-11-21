package view.menu;

import java.awt.Dialog;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

import core.MediaPlayer;
import core.PlayerListener;

public class Menu extends JFrame implements MouseListener, KeyListener {

	private PainelMenu painel;
	private MenuNovo novoJogo;
	private MediaPlayer mp;
	private boolean pausado = false;

	public Menu() {
		painel = new PainelMenu();
		this.setContentPane(painel);
		this.painel.getNovo().addMouseListener(this);
		this.painel.getPlacar().addMouseListener(this);
		this.painel.addKeyListener(this);
		this.addKeyListener(this);

//		try {
//			mp = new MediaPlayer("/resource/music");
//			PlayerListener teste = new PlayerListener();
//			teste.setSujeito(mp);
//			mp.setListener(teste);
//			mp.run();
//		} catch (LineUnavailableException | IOException e) {
//
//			e.printStackTrace();
//		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String name = e.getComponent().getName();

		if (name.equalsIgnoreCase(this.painel.getNovo().getName())) {
			this.novoJogo = new MenuNovo(this);
			this.novoJogo.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
			this.novoJogo.setVisible(true);
			this.novoJogo.setLocationRelativeTo(this);
			this.novoJogo.setResizable(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		String name = e.getComponent().getName();

		if (name.equalsIgnoreCase(this.painel.getNovo().getName())) {
			this.painel.getNovo().setIcon(this.painel.getNovoHoverIcon());
		} else if (name.equalsIgnoreCase(this.painel.getPlacar().getName())) {
			this.painel.getPlacar().setIcon(this.painel.getPlacarHoverIcon());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		String name = e.getComponent().getName();

		if (name.equalsIgnoreCase(this.painel.getNovo().getName())) {
			this.painel.getNovo().setIcon(this.painel.getNovoIcon());
		} else if (name.equalsIgnoreCase(this.painel.getPlacar().getName())) {
			this.painel.getPlacar().setIcon(this.painel.getPlacarIcon());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		try {

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				mp.voltaMusica();
				mp.tocaMusica();
				System.out.println("cu");

			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				mp.passaMusica();
				mp.tocaMusica();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_P) {
			if (pausado) {
				mp.continuaMusica();
				pausado = false;
			} else {
				mp.pausa();
				pausado = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
