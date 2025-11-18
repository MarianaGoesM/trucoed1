package model;

import java.util.ArrayList;
import java.util.List;

import enumerated.Naipe;
import enumerated.Valor;

public class Baralho {
	private Pilha<Carta> cartas;
	private boolean tipo;
	
	public Baralho(){
		this.cartas = new Pilha<Carta>(40); //baralho sem 8,9 e 10
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
