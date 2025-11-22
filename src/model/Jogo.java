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
    private Partida partidaAtual;

    public Jogo() {

        this.jogadores = new LinkedList<Jogador<Carta>>();
        this.partidas = new ArrayList<Partida>();
    }


    public void iniciarNovaPartida() {

        this.partidaAtual = new Partida();
        this.addPartida(this.partidaAtual);

        if (this.b != null) {

            // 1. Inicializa, Embaralha e Distribui
            this.b.criarBaralho();
            this.b.embaralhar();
            this.b.distribuirCartas(this.jogadores);

            // 2. Define a Vira e a Manilha na Partida
            Carta vira = this.b.retirarVira();
            this.partidaAtual.setManilha(vira);

            // 3. CHAMA A ORDENAÇÃO
            if (vira != null) {
                this.partidaAtual.ordenarMaosDosJogadores(this.jogadores);
                System.out.println("Mãos ordenadas para a nova partida (Vira: " + vira.getValor() + " de " + vira.getNaipe() + ").");
            } else {
                System.err.println("Erro: A manilha (vira) não foi definida, a ordenação não pode ser feita.");
            }
        } else {
            System.err.println("Erro: O baralho (b) não foi inicializado.");
        }
    }

    public Partida getPartidaAtual() {
        return partidaAtual;
    }

    public void setPartidaAtual(Partida partidaAtual) {
        this.partidaAtual = partidaAtual;
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