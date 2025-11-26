package model;

import java.util.ArrayList;
import java.util.List;
import enumerated.Naipe;
import enumerated.Valor;


public abstract class Jogador<T> {
    private List<T> mao;
    private String nome;
    private int pontuacao;
    private T cartaJogada;
    private int time;

    private Jogador<T> proximoJogador;

    public Jogador(String nome, int time) {
        this.nome = nome;
        this.mao = new ArrayList<T>();
        this.time = time;
    }


    public abstract T jogarCarta();

    public void addCarta(T c) {
        this.mao.add(c);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public List<T> getMao() {
        return mao;
    }

    public void setMao(List<T> mao) {
        this.mao = mao;
    }

    public Jogador<T> getProximoJogador() {
        return this.proximoJogador;
    }

    public void setProximoJogador(Jogador<T> proximoJogador) {
        this.proximoJogador = proximoJogador;
    }


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void ordenarMao(Carta vira) {
        if (!this.mao.isEmpty() && this.mao.get(0) instanceof Carta) {

            Ordenacao comparator = new Ordenacao(vira);

            List<Carta> cartas = (List<Carta>) this.mao;

            Ordenacao.bubbleSort(cartas, comparator);
        } else {
            System.err.println("Erro: A mão do jogador não pode ser ordenada.");
        }
    }

}