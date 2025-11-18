package model;

public abstract class FactoryJogador<T extends Jogador<Carta>> {
    public abstract T novo(String nome, int time);
}
