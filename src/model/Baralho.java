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
		this.cartas = new Pilha<Carta>(40); //baralho sem 8,9 e 10
	}

	// --- NOVO MÉTODO: INICIALIZAR O BARALHO COM 40 CARTAS ---
	public void criarBaralho() {
		// Limpar o baralho existente, se houver
		// Assumindo que sua Pilha tem um método clear() ou que é nova.

		// Vamos iterar sobre todos os Naipes e Valores válidos
		for (Naipe n : Naipe.values()) {
			for (Valor v : Valor.values()) {
				// EXCLUSÃO: Assumindo que os enums Valor incluem APENAS os valores válicos (A, 2, 3, 4, 5, 6, 7, Dama, Valete, Rei)
				// Se seus enums incluem 8, 9 e 10, você precisa pular eles aqui.

				// Exemplo de exclusão (se o enum Valor for completo):
				// if (v != Valor.OITO && v != Valor.NOVE && v != Valor.DEZ) {
				Carta novaCarta = new Carta();
				novaCarta.setNaipe(n);
				novaCarta.setValor(v);
				this.addCarta(novaCarta); // Adiciona na Pilha
				// }
			}
		}
	}

	// --- NOVO MÉTODO: EMBARALHAR O BARALHO ---
	public void embaralhar() {
		// Para embaralhar uma Pilha, a maneira mais fácil é:
		// 1. Mover os elementos da Pilha para uma List.
		// 2. Usar Collections.shuffle na List.
		// 3. Mover os elementos de volta para a Pilha.

		List<Carta> listaCartas = new ArrayList<>();
		while (!this.cartas.isEmpty()) {
			listaCartas.add(this.cartas.pop());
		}

		// Embaralha a lista
		Collections.shuffle(listaCartas);

		// Devolve as cartas embaralhadas para a Pilha
		for (Carta c : listaCartas) {
			this.cartas.push(c);
		}
	}

	// --- NOVO MÉTODO: DISTRIBUIR CARTAS E DEFINIR A VIRA (MANILHA) ---
	public void distribuirCartas(List<Jogador<Carta>> jogadores) {

		// Distribui 3 cartas para cada jogador
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

	// --- NOVO MÉTODO: RETIRAR A CARTA VIRA ---
	public Carta retirarVira() {
		if (!this.cartas.isEmpty()) {
			return this.cartas.pop(); // A próxima carta no topo é a Vira
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
