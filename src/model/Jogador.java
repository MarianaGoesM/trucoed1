package model;

import java.util.ArrayList;
import java.util.List;
import enumerated.Naipe;
import enumerated.Valor;

public abstract class Jogador {
	private List<Carta> mao;
	private String nome;
	private int pontuacao;
	private Carta cartaJogada;

	public Jogador(String nome) {
		this.nome = nome;
		this.mao = new ArrayList<Carta>();
	}

	public abstract Carta jogarCarta();

	public void addCarta(Carta c) {
		this.mao.add(c);
	}

	public Carta removeCarta(Naipe n, Valor v) {
		for (Carta c : mao) {
			if (c.getNaipe().equals(n) && c.getValor().equals(v)) {
				mao.remove(c);
				return c;
			}
		}
		
		return null;
	}

	public List<Carta> getMao() {
		return mao;
	}

	public void setMao(List<Carta> mao) {
		this.mao = mao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Carta getCartaJogada() {
		return cartaJogada;
	}

	public void setCartaJogada(Carta cartaJogada) {
		this.cartaJogada = cartaJogada;
	}
	
	
}