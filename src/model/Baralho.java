package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enumerated.Naipe;
import enumerated.Valor;

public class Baralho {
	private Pilha<Carta> cartas;
	private boolean tipo;

	public Baralho(){
		this.cartas = new Pilha<>(40); // baralho sem 8,9 e 10
	}


	public void criarBaralho() {


		for (Naipe n : Naipe.values()) {
			for (Valor v : Valor.values()) {

				Carta novaCarta = new Carta();
				novaCarta.setNaipe(n);
				novaCarta.setValor(v);
				this.addCarta(novaCarta); // Adiciona na Pilha
			}
		}
	}


	public void embaralhar() {
		List<Carta> listaCartas = new ArrayList<>();

		// Move da Pilha para a Lista
		while (!this.cartas.isEmpty()) {
			listaCartas.add(this.cartas.pop());
		}

		// Embaralha a lista (API nativa do Java)
		Collections.shuffle(listaCartas);

		// Devolve as cartas embaralhadas para a Pilha
		for (Carta c : listaCartas) {
			this.cartas.push(c);
		}
	}

	public void distribuirCartas(List<Jogador<Carta>> jogadores) {

		// Distribui 3 cartas para cada jogador (passada verticalmente para garantir a ordem de compra)
		for (int i = 0; i < 3; i++) {
			for (Jogador<Carta> jogador : jogadores) {
				if (!this.cartas.isEmpty()) {
					jogador.addCarta(this.cartas.pop());
				} else {
					System.err.println("Baralho vazio durante a distribuição!");
					return;
				}
			}
		}
	}

	public Carta retirarVira() {
		// Retira e retorna a próxima carta do topo (a Vira/Manilha)
		if (!this.cartas.isEmpty()) {
			return this.cartas.pop();
		}
		return null;
	}


	public void addCarta(Carta c){
		this.cartas.push(c);
	}

	public Carta ComprarCarta(){
		if(this.cartas.isEmpty()){
			return null;
		}
		else{
			return this.cartas.pop();
		}
	}
	public Pilha<Carta> getCartas() {
		return cartas;
	}
	public void setCartas(Pilha<Carta> cartas) {
		this.cartas = cartas;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
}