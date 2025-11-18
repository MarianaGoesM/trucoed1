package controller;

import java.util.List;
import java.util.Random;
import java.util.Set;

import model.*;

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
                jo.addCarta(b.getCartas().pop());
            }
        }

        partida.setManilha(b.getCartas().pop());
    }

    public void embaralhar(Baralho b) {
        Pilha<Carta> pilhaCartas = b.getCartas();
        List<Carta> listaParaEmbaralhar = new java.util.ArrayList<>();

        while (!pilhaCartas.isEmpty()) {
            listaParaEmbaralhar.add(pilhaCartas.pop());
        }

        java.util.Collections.shuffle(listaParaEmbaralhar);

        for (Carta c : listaParaEmbaralhar) {
            pilhaCartas.push(c);
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
