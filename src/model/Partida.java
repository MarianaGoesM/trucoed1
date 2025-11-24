package model;

import java.util.ArrayList;
import java.util.List;

public class Partida {

	private Jogador<Carta> vencedor;
	private int pontosDisputados;
	private List<Turno> turnos;
	private Carta manilha;

	public Partida() {
		this.turnos = new ArrayList<>();
	}

	public Carta getManilha() {
		return manilha;
	}

	public void setManilha(Carta manilha) {
		this.manilha = manilha;
	}

	public void addTurno(Turno t) {
		this.turnos.add(t);
	}

	public List<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public Jogador<Carta> getVencedor() {
		return vencedor;
	}

	public void setVencedor(Jogador<Carta> vencedor) {
		this.vencedor = vencedor;
	}

	public int getPontosDisputados() {
		return pontosDisputados;
	}

	public void setPontosDisputados(int pontosDisputados) {
		this.pontosDisputados = pontosDisputados;
	}


	public void ordenarMaosDosJogadores(List<Jogador<Carta>> jogadores) {

		// 1. Verifica se a manilha foi definida para esta partida
		if (this.manilha == null) {
			System.err.println("Erro de lógica: A manilha da partida não foi definida.");
			return;
		}

		// 2. Itera sobre a lista de jogadores e chama a ordenação
		for (Jogador<Carta> jogador : jogadores) {

			// Requer a implementação do método ordenarMao(Carta) em Jogador.java
			jogador.ordenarMao(this.manilha);
		}
	}
}