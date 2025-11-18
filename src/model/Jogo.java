package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.LinkedList; // Importar LinkedList

public class Jogo {

    private LinkedList<Jogador<Carta>> jogadores;
    private int numeroJogadores;
    private Date data;

    private int pontosA;
    private int pontosB;
    private List<Partida> partidas;
    private Baralho b;

    public Jogo() {

        this.jogadores = new LinkedList<Jogador<Carta>>();
        this.partidas = new ArrayList<Partida>();
    }


    public void addJogador(Jogador<Carta> j){
        this.jogadores.addLast(j);
    }

    public void addPartida(Partida p){
        this.partidas.add(p);
    }


    public List<Jogador<Carta>> getJogadores() {
        return jogadores;
    }


    public void setJogadores(LinkedList<Jogador<Carta>> jogadores) {
        this.jogadores = jogadores;
    }

    public int getNumeroJogadores() {
        return numeroJogadores;
    }



    public void setNumeroJogadores(int numeroJogadores) {
        this.numeroJogadores = numeroJogadores;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getPontosA() {
        return pontosA;
    }

    public void setPontosA(int pontosA) {
        this.pontosA = pontosA;
    }

    public int getPontosB() {
        return pontosB;
    }

    public void setPontosB(int pontosB) {
        this.pontosB = pontosB;
    }

    public Baralho getB() {
        return b;
    }

    public void setB(Baralho b) {
        this.b = b;
    }
}