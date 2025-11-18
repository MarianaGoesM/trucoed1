package model;

public class Pessoa extends Jogador<Carta> {
	
	
	public Pessoa(String nome, int time) {
		super(nome, time);
	}

	@Override
	public Carta jogarCarta() {
		return null;
	}
}
