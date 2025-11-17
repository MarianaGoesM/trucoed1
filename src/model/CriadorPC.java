package model;

public class CriadorPC extends FactoryJogador{

	@Override
	public Jogador novo() {
		return new PC("Computador" + (PC.getId() + 1));
	}
	
}
