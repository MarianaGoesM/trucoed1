package core;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class PlayerListener implements LineListener {
	private MediaPlayer sujeito;

	public PlayerListener() {
	}

	@Override
	public void update(LineEvent arg0) {
		
		
		try {
			if (arg0.getType().equals(LineEvent.Type.STOP)
					&& (sujeito.getPl().getFramePosition() >= sujeito.getPl().getFrameLength())) {
				sujeito.passaMusica();
				sujeito.tocaMusica();
				System.out.println("Passou");
			} else if (arg0.getType().equals(LineEvent.Type.START)){
				System.out.println("Tocando");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setSujeito(MediaPlayer sujeito) {
		this.sujeito = sujeito;
	}
}
