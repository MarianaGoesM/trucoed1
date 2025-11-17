package model;

import java.util.ArrayList;
import java.util.List;


public class Turno {
	private int numero;
	private List<CartaJogada> cartasJogadas;
	private Jogador vencedor;
	private boolean isMelado;
	
	public Turno() {
		this.cartasJogadas = new ArrayList<CartaJogada>();
	}

	public void addCarta(CartaJogada c) {
		this.cartasJogadas.add(c);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<CartaJogada> getCartasJogadas() {
		return cartasJogadas;
	}

	public void setCartasJogadas(List<CartaJogada> cartasJogadas) {
		this.cartasJogadas = cartasJogadas;
	}

	public Jogador getVencedor() {
		return vencedor;
	}

	public void setVencedor(Jogador vencedor) {
		this.vencedor = vencedor;
	}

	public boolean isMelado() {
		return isMelado;
	}

	public void setMelado(boolean isMelado) {
		this.isMelado = isMelado;
	}

}
