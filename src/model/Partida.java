package model;

import java.util.ArrayList;
import java.util.List;

public class Partida {
	private Jogador vencedor;
	private int pontosDisputados;
	private List<Turno> turnos;
	private Carta manilha;

	public Partida() {
		this.turnos = new ArrayList<Turno>();
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

	public Jogador getVencedor() {
		return vencedor;
	}

	public void setVencedor(Jogador vencedor) {
		this.vencedor = vencedor;
	}

	public int getPontosDisputados() {
		return pontosDisputados;
	}

	public void setPontosDisputados(int pontosDisputados) {
		this.pontosDisputados = pontosDisputados;
	}
}
