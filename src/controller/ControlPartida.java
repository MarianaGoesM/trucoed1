package controller;

import java.util.List;
import java.util.Random;
import java.util.Set;

import model.Baralho;
import model.Carta;
import model.Jogador;
import model.Partida;

public class ControlPartida {

	private ControlTurno ct;
	private Partida partida;

	public ControlPartida(ControlTurno ct) {
		this.ct = ct;
	}

	public void novaPartida() {
		setPartida(new Partida());
	}

	public void iniciarTurno() {
		ct.criaTurno();
		partida.addTurno(ct.getTurno());
	}

	public void distribuiCartas(Set<Jogador> j, Baralho b) {
		embaralhar(b);

		for (int i = 0; i < 3; i++) {
			for (Jogador jo : j) {
				jo.addCarta(b.getCartas().remove(0));
			}
			partida.setManilha(b.getCartas().remove(0));

		}

		/*
		 * for (Jogador jo : j){ System.out.println(jo.getNome());
		 * 
		 * for (Carta c : jo.getMao()){ System.out.println(c.getValor() + " de " +
		 * c.getNaipe()); } }
		 */
	}

	public void embaralhar(Baralho b) {
		List<Carta> cartas = b.getCartas();
		Random gerador = new Random();
		Carta aux;
		int indice;

		for (int j = 0; j < 50; j++) {
			for (int i = 0; i < cartas.size(); i++) {
				indice = gerador.nextInt(cartas.size());
				aux = cartas.get(indice);
				cartas.set(indice, cartas.get(i));
				cartas.set(i, aux);
			}
		}
	}

	public int verificarVencedorTurno(Carta cartaJogador, Carta cartaPc, Carta manilha) {
		int valorManilha = 0;
		if (manilha.getValor().getValor() + 1 > 10) {
			valorManilha = 1;
		} else {
			valorManilha = manilha.getValor().getValor() + 1;
		}
		if (cartaJogador.getValor().getValor() > cartaPc.getValor().getValor()) {
			if (cartaPc.getValor().getValor() == valorManilha) {
				return -1;
			}
			return 1;
		} else if (cartaJogador.getValor().getValor() < cartaPc.getValor().getValor()) {
			if (cartaJogador.getValor().getValor() == valorManilha) {
				return 1;
			}
			return -1;
		} else {
			if (cartaJogador.getValor().getValor() == valorManilha && cartaPc.getValor().getValor() == valorManilha) {
				if(cartaJogador.getNaipe().getValor()>cartaPc.getNaipe().getValor()) {
					return 1;
				}else {
					return -1;
				}
			}
			ct.getTurno().setMelado(true);
			return 0;
		}
	}

	public void verificarVendorPartida() {

	}

	public void atualizarPlacar() {

	}

	public void jogadaPC() {
		Random a = new Random();
		int tempo;
		tempo = a.nextInt(6);
		try {
			Thread.sleep(1000 + (tempo * 2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void jogadaJogador() {

	}

	public ControlTurno getCt() {
		return ct;
	}

	public void setCt(ControlTurno ct) {
		this.ct = ct;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
}
