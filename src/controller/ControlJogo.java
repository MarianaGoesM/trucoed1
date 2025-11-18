package controller;

import java.util.List;
import enumerated.Naipe;
import enumerated.Valor;
import model.Baralho;
import model.Carta;
import model.CriadorCarta;
import model.CriadorPC;
import model.CriadorPessoa;
import model.FactoryJogador;
import model.Jogador;
import model.Jogo;
import model.Pessoa; // Importar Pessoa para o instanceof

public class ControlJogo {
    private CriadorCarta fabricaCarta;
    private Valor[] valores;
    private Naipe[] naipes;
    private Jogo jogo;
    private ControlPartida cp;

    public ControlJogo(ControlPartida cp) {
        this.fabricaCarta = new CriadorCarta();
        this.valores = Valor.values();
        this.naipes = Naipe.values();
        this.cp = cp;
    }

    public void setarBaralho(boolean bool) {
        Baralho b = new Baralho();
        b.setTipo(bool);
        if (b.isTipo()) {
            geraBaralho(b, 0);
        } else {
            geraBaralho(b, 0);
        }

        this.jogo.setB(b);
    }

    public void geraBaralho(Baralho b, int k) {
        for (int i = k; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                addCarta(b, valores[i], naipes[j]);
                System.out.println(j + " " + valores[i] + " " + naipes[j]);
            }
        }
        System.out.println("tamanho baralho gerado: " + b.getCartas().size());
    }

    public void addCarta(Baralho b, Valor v, Naipe n) {
        Carta c = fabricaCarta.novo();
        c.setValor(v);
        c.setNaipe(n);
        b.addCarta(c);
    }

    // Método setarJogador corrigido para a Factory e Generics
    public Jogador<Carta> setarJogador(boolean cond, String nome, int time) {

        if (cond) {
            return new CriadorPessoa().novo(nome, time);
        } else {
            return new CriadorPC().novo(nome, time);
        }

    }

    // Lógica 2x2 para configurar 4 jogadores
    public void setarJogadoresJogo(String nomePessoa, int numerojogadores){
        if (numerojogadores != 4) return;

        // Time 1: Pessoa (Nome fornecido)
        Jogador<Carta> p1 = setarJogador(true, nomePessoa, 1);
        if (p1 != null) this.jogo.addJogador(p1);
        System.out.println("criei: " + p1.getNome());

        // Time 2: PC 1
        Jogador<Carta> pc1 = setarJogador(false, "", 2);
        if (pc1 != null) this.jogo.addJogador(pc1);

        // Time 1: PC 2 (Parceiro)
        Jogador<Carta> pc2 = setarJogador(false, "", 1);
        if (pc2 != null)this.jogo.addJogador(pc2);

        // Time 2: PC 3 (Oponente)
        Jogador<Carta> pc3 = setarJogador(false, "", 2);
        if (pc3 != null)this.jogo.addJogador(pc3);

        System.out.println("Jogadores jogados com sucesso!" + this.jogo.getJogadores().size());
    }

    public void iniciaJogo(String nome, boolean tipo, int numerojogadores) {
        this.jogo = new Jogo();
        this.jogo.setNumeroJogadores(numerojogadores);
        setarJogadoresJogo(nome, numerojogadores);
        setarBaralho(tipo);
        cp.novaPartida();
        this.jogo.addPartida(cp.getPartida());
    }

    // CORREÇÃO do método getJogadorHumano: Usa List<Jogador<Carta>> e instanceof Pessoa
    public Jogador<Carta> getJogadorHumano(){
        List<Jogador<Carta>> jogadores = jogo.getJogadores();

        for (Jogador<Carta> j : jogadores){
            if (j instanceof Pessoa){
                return j;
            }
        }
        return null;}

    // Método getJogadorpC corrigido para o tipo genérico
    public Jogador<Carta> getJogadorpC(){
        List<Jogador<Carta>> jogadores = jogo.getJogadores();

        for (Jogador<Carta> j : jogadores){
            if (j.getNome().contains("Computador")){
                return j;
            }
        }

        return null;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
}