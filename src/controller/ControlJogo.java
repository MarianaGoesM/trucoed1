package controller;

import java.util.ArrayList;
import java.util.List;
import enumerated.Naipe;
import enumerated.Valor;
import model.Baralho;
import model.Carta;
import model.CriadorCarta;
import model.CriadorPC;
import model.CriadorPessoa;
import model.Jogador;
import model.Jogo;
import model.Pessoa;
import util.GerenciadorTxt;

public class ControlJogo {
    private CriadorCarta fabricaCarta;
    private Valor[] valores;
    private Naipe[] naipes;
    private Jogo jogo;
    private ControlPartida cp;

    private int indiceJogadorMao;
    private int pontosSetTime1;
    private int pontosSetTime2;
    private boolean modoRouboUsado;

    private int valorAtualMao;
    private static final int PLACAR_VITORIA = 12;
    private boolean trucoPendente;

    public ControlJogo(ControlPartida cp) {
        this.fabricaCarta = new CriadorCarta();
        this.valores = Valor.values();
        this.naipes = Naipe.values();
        this.cp = cp;

        this.pontosSetTime1 = 0;
        this.pontosSetTime2 = 0;
        this.indiceJogadorMao = 0;

        this.modoRouboUsado = false;
        this.valorAtualMao = 1;
        this.trucoPendente = false;
    }

    public void setCp(ControlPartida cp) {
        this.cp = cp;
        this.indiceJogadorMao = 0;
    }

    public void setPontosSetTime1(int pontosSetTime1) {
        this.pontosSetTime1 = pontosSetTime1;
    }

    public void setPontosSetTime2(int pontosSetTime2) {
        this.pontosSetTime2 = pontosSetTime2;
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

    public Jogador<Carta> setarJogador(boolean cond, String nome, int time) {

        if (cond) {
            return new CriadorPessoa().novo(nome, time);
        } else {
            return new CriadorPC().novo(nome, time);
        }

    }

    public void setarJogadoresJogo(String nomePessoa, int numerojogadores){
        if (numerojogadores != 4) return;

        Jogador<Carta> p1 = setarJogador(true, nomePessoa, 1);
        Jogador<Carta> pc1 = setarJogador(false, "PC1", 2);
        Jogador<Carta> pc2 = setarJogador(false, "PC2", 1);
        Jogador<Carta> pc3 = setarJogador(false, "PC3", 2);

        if (p1 != null) this.jogo.addJogador(p1);
        if (pc1 != null) this.jogo.addJogador(pc1);
        if (pc2 != null) this.jogo.addJogador(pc2);
        if (pc3 != null) this.jogo.addJogador(pc3);


        // P1 (Jogador Humano) aponta para PC3
        p1.setProximoJogador(pc3);

        // PC3 aponta para PC2
        pc3.setProximoJogador(pc2);

        // PC2 aponta para PC1
        pc2.setProximoJogador(pc1);

        // PC1 (último) aponta para P1
        pc1.setProximoJogador(p1);

        System.out.println("Jogadores jogados com sucesso!" + this.jogo.getJogadores().size());
    }

    public void iniciaJogo(String nome, boolean tipo, int numerojogadores) {
        this.jogo = new Jogo();
        this.jogo.setNumeroJogadores(numerojogadores);
        setarJogadoresJogo(nome, numerojogadores);
        setarBaralho(tipo);
        cp.novaPartida();
        this.jogo.addPartida(cp.getPartida());
        this.indiceJogadorMao = 0;
        this.valorAtualMao = 1;
        this.trucoPendente = false;


        this.pontosSetTime1 = 10;
        this.pontosSetTime2 = 0;
        this.indiceJogadorMao = 0;

        GerenciadorTxt.iniciarNovoLog();
    }

    //METODO DE BUSCA
    public boolean aplicarModoRoubo() {
        if (this.modoRouboUsado) {
            System.out.println("Modo Roubo: Já foi usado nesta partida.");
            return false;
        }

        Jogador<Carta> jHumano = getJogadorHumano();
        Baralho baralho = this.jogo.getB();
        Carta manilhaVirada = cp.getPartida().getManilha();

        List<Carta> cartasNoBaralho = new ArrayList<>();
        Carta cartaPilha;

        while ((cartaPilha = baralho.ComprarCarta()) != null) {
            cartasNoBaralho.add(cartaPilha);
        }

        if (cartasNoBaralho.isEmpty()) {
            System.out.println("Modo Roubo: Baralho vazio.");
            return false;
        }

        Carta cartaMaisForteBaralho = cartasNoBaralho.get(0);

        int forcaMax = cp.getForcaTruco(cartaMaisForteBaralho, manilhaVirada);

        Carta cartaMaisFracaHumano = jHumano.getMao().get(0);

        int forcaMinHumano = cp.getForcaTruco(cartaMaisFracaHumano, manilhaVirada);

        //BUSCA LINEAR
        for (int i = 1; i < cartasNoBaralho.size(); i++) {
            Carta cartaAtual = cartasNoBaralho.get(i);
            int forcaAtual = cp.getForcaTruco(cartaAtual, manilhaVirada);

            if (forcaAtual > forcaMax) {
                forcaMax = forcaAtual;
                cartaMaisForteBaralho = cartaAtual;
            }
        }

        for (Carta carta : jHumano.getMao()) {
            int forcaAtual = cp.getForcaTruco(carta, manilhaVirada);
            if (forcaAtual < forcaMinHumano) {
                forcaMinHumano = forcaAtual;
                cartaMaisFracaHumano = carta;
            }
        }

        cartasNoBaralho.remove(cartaMaisForteBaralho);
        jHumano.getMao().remove(cartaMaisFracaHumano);

        jHumano.getMao().add(cartaMaisForteBaralho);
        baralho.addCarta(cartaMaisFracaHumano);

        for (Carta c : cartasNoBaralho) {
            baralho.addCarta(c);
        }

        this.modoRouboUsado = true;
        System.out.println("Modo Roubo executado. Ganhou: " + cartaMaisForteBaralho.getValor() +
                " de " + cartaMaisForteBaralho.getNaipe() +
                ". Perdeu: " + cartaMaisFracaHumano.getValor() +
                " de " + cartaMaisFracaHumano.getNaipe());

        return true;
    }


    public String pedirTruco() {

        if (this.valorAtualMao == 3) {
            int novoValor = 6;
            this.valorAtualMao = novoValor;
            this.trucoPendente = true;
            System.out.println("Jogador: PEÇO SEIS! PC deve responder.");
            return "PENDENTE: Oponente pediu Seis.";
        }

        if (this.valorAtualMao == 1) {
            this.valorAtualMao = 3;

            boolean pcChamouTruco = (this.indiceJogadorMao == 1 || this.indiceJogadorMao == 3);

            if (pcChamouTruco) {
                this.trucoPendente = true;
                System.out.println("PC: TRUCO! Jogador deve responder.");
                return "PENDENTE: Oponente pediu Truco.";
            } else {
                if (cp.pcTemZap()) {
                    this.valorAtualMao = 6;
                    this.trucoPendente = true;
                    System.out.println("PC: ACEITO (3) e peço SEIS! Jogador deve responder.");
                    return "PC Aceitou e Aumentou para SEIS!";
                }
                else if (cp.pcTemCartaForte()) {
                    this.trucoPendente = false;
                    System.out.println("PC: ACEITO (3).");
                    return "PC Aceitou o Truco (Mão vale 3).";
                }
                else {
                    this.trucoPendente = false;
                    System.out.println("PC: CORRO! Time do jogador ganha 1 ponto.");
                    atualizarPlacarSet(1, 1);
                    return "PC Correu (Time 1 ganhou 1 ponto).";
                }
            }
        }
        return "Nenhuma ação.";
    }

    public int getValorAtualMao() {
        return valorAtualMao;
    }

    public boolean isTrucoPendente() {
        return trucoPendente;
    }

    public void setTrucoPendente(boolean trucoPendente) {
        this.trucoPendente = trucoPendente;
    }

    public void aceitarTruco() {
        this.trucoPendente = false;
        System.out.println("Jogador aceitou. Mão vale " + this.valorAtualMao + ".");
    }

    public void correrTruco(int timePerdeu) {
        this.trucoPendente = false;

        int pontosVencedor;
        if (this.valorAtualMao == 3) {
            pontosVencedor = 1;
        } else {
            pontosVencedor = this.valorAtualMao - 3;
        }

        int vencedorTime = (timePerdeu == 1) ? -1 : 1;

        atualizarPlacarSet(pontosVencedor, vencedorTime);
        System.out.println((timePerdeu == 1 ? "Jogador" : "PC") + " correu. Time " + (vencedorTime == 1 ? "1" : "2") + " ganha " + pontosVencedor + " ponto(s). NOVA RODADA.");
    }

    public void atualizarPlacarSet(int pontosGanhos, int vencedorTime) {
        if (vencedorTime == 1) {
            this.pontosSetTime1 += pontosGanhos;
        } else if (vencedorTime == -1) {
            this.pontosSetTime2 += pontosGanhos;
        }

        this.jogo.setPontosA(0);
        this.jogo.setPontosB(0);

        if (this.cp != null) {
            this.cp.resetTurnosGanhos();
        }

        this.valorAtualMao = 1;

        verificarFimSet();
    }

    private void verificarFimSet() {
        if (this.pontosSetTime1 >= PLACAR_VITORIA) {
            cp.fimDeJogo(1);
        } else if (this.pontosSetTime2 >= PLACAR_VITORIA) {
            cp.fimDeJogo(-1);
        }
    }

    public boolean ordenarMaoDoJogadorAtual(Carta manilha) {
        Jogador<Carta> jogadorAtual = getJogadorHumano();

        if (jogadorAtual != null && manilha != null) {
            jogadorAtual.ordenarMao(manilha);
            return true;
        }
        return false;
    }

    public int getIndiceJogadorMao() { return indiceJogadorMao; }

    public Jogador<Carta> getJogadorHumano(){
        List<Jogador<Carta>> jogadores = jogo.getJogadores();

        for (Jogador<Carta> j : jogadores){
            if (j instanceof Pessoa){
                return j;
            }
        }
        return null;}

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

    public int getPontosSetTime1() { return pontosSetTime1; }
    public int getPontosSetTime2() { return pontosSetTime2; }

    public Jogador<Carta> getJogadorMao() {
        if (this.jogo == null || this.jogo.getJogadores() == null || this.jogo.getJogadores().isEmpty()) {
            return null;
        }
        List<Jogador<Carta>> jogadores = this.jogo.getJogadores();

        if (this.indiceJogadorMao >= 0 && this.indiceJogadorMao < jogadores.size()) {
            return jogadores.get(this.indiceJogadorMao);
        }
        return null;
    }

    public boolean isModoRouboUsado() {
        return modoRouboUsado;
    }

    public void setModoRouboUsado(boolean modoRouboUsado) {
        this.modoRouboUsado = modoRouboUsado;
    }
    public void setIndiceJogadorMao(int proximoStarterIndex) {
        this.indiceJogadorMao = proximoStarterIndex;
    }
}