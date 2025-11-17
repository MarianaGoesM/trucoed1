package model;

import java.util.ArrayList;
import java.util.List;

import enumerated.Naipe;
import enumerated.Valor;

public class Baralho {
	private List<Carta> cartas;
	private boolean tipo;
	
	public Baralho(){
		this.cartas = new ArrayList<Carta>();
	}
	
	public void addCarta(Carta c){
		this.cartas.add(c);
	}
	
	public Carta removeCarta(Naipe n, Valor v){
		
		for (Carta card : cartas){
			if (card.getNaipe().equals(n) && card.getValor().equals(v)){
				cartas.remove(card);
				return card;
			}
		}
		
		return null;
	}
	public List<Carta> getCartas() {
		return cartas;
	}
	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	
	
}
