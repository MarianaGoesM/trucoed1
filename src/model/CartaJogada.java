package model;

public class CartaJogada {
	private Carta carta;
	private Jogador jogador;
	
	public CartaJogada(Carta carta, Jogador jogador){
		this.carta = carta;
		this.jogador = jogador;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}
	
	
}
