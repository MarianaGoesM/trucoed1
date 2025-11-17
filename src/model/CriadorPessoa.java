package model;

public class CriadorPessoa extends FactoryJogador{
	
	@Override
	public Jogador novo(){
		return new Pessoa("");
	}
}
