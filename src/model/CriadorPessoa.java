package model;

public class CriadorPessoa extends FactoryJogador<Pessoa>{
	
	@Override
    public Pessoa novo(String nome, int time){
        return new Pessoa(nome, time);
    }
}
