package controller;

import java.util.Set;
import model.Carta;
import model.CartaJogada;
import model.Jogador;
import model.Turno;

public class ControlTurno {
	private Turno turno;
	
	public void novaCartaJogada(Jogador j, Carta c){
		addCartaJogada(new CartaJogada(c, j));
	}
	
	public void jogaCarta(Carta c, Set<Jogador> j){
		System.out.println(j.getClass().toString());
	}
	
	public void addCartaJogada(CartaJogada cj){
		this.turno.addCarta(cj);
	}
	
	public void criaTurno(){
		setTurno(new Turno());
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	
}
