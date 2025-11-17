package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Jogo {
	private Set<Jogador> jogadores;
	private int numeroJogadores;
	private Date data;
	private int pontosA;
	private int pontosB;
	private List<Partida> partidas;
	private Baralho b;

	public Jogo() {
		this.jogadores = new HashSet<Jogador>();
		this.partidas = new ArrayList<Partida>();
	}
	
	public void addJogador(Jogador j){
		this.jogadores.add(j);
	}

	public void addPartida(Partida p){
		this.partidas.add(p);
	}
	
	public Set<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(Set<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public int getNumeroJogadores() {
		return numeroJogadores;
	}

	public void setNumeroJogadores(int numeroJogadores) {
		this.numeroJogadores = numeroJogadores;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getPontosJogador() {
		return pontosA;
	}

	public void setPontosJogador(int pontosA) {
		this.pontosA = pontosA;
	}

	public int getPontosPc() {
		return pontosB;
	}

	public void setPontosPc(int pontosB) {
		this.pontosB = pontosB;
	}

	public Baralho getB() {
		return b;
	}

	public void setB(Baralho b) {
		this.b = b;
	}

}
