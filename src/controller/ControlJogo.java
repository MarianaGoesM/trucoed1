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

public class ControlJogo {
    private CriadorCarta fabricaCarta;
    private Valor[] valores;
    private Naipe[] naipes;
    private Jogo jogo;
    private ControlPartida cp; // Usado para acessar a lógica de Truco (getForcaTruco)

    // Controle da ordem circular (quem joga primeiro)
    private int indiceJogadorMao;
    // Placar do SET
    private int pontosSetTime1;
    private int pontosSetTime2;
    private boolean modoRouboUsado;

    public ControlJogo(ControlPartida cp) {
        this.fabricaCarta = new CriadorCarta();
        this.valores = Valor.values();
        this.naipes = Naipe.values();
        this.cp = cp;

        this.pontosSetTime1 = 0;
        this.pontosSetTime2 = 0;
        this.indiceJogadorMao = 0;

        this.modoRouboUsado = false;
    }

    // --- Métodos de Setup (iniciaJogo, setarBaralho, etc.) ---

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

        // Time 1: Pessoa (Nome fornecido)
        Jogador<Carta> p1 = setarJogador(true, nomePessoa, 1);
        if (p1 != null) this.jogo.addJogador(p1);
        System.out.println("criei: " + p1.getNome());

        // Time 2: PC 1 (Oponente 1)
        Jogador<Carta> pc1 = setarJogador(false, "", 2);
        if (pc1 != null) this.jogo.addJogador(pc1);

        // Time 1: PC 2 (Parceiro - Joga na frente)
        Jogador<Carta> pc2 = setarJogador(false, "", 1);
        if (pc2 != null)this.jogo.addJogador(pc2);

        // Time 2: PC 3 (Oponente 2)
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
        this.indiceJogadorMao = 0; // Inicia com o Humano (índice 0)
    }

    // --- Lógica Principal do Modo Roubo ---

    /**
     * Implementa o Modo Roubo: Busca Linear pela carta mais forte no baralho
     * e troca pela mais fraca na mão do jogador humano.
     */
    public boolean aplicarModoRoubo() {
        if (this.modoRouboUsado) {
            System.out.println("Modo Roubo: Já foi usado nesta partida.");
            return false;
        }

        Jogador<Carta> jHumano = getJogadorHumano();
        Baralho baralho = this.jogo.getB();
        Carta manilhaVirada = cp.getPartida().getManilha();

        if (jHumano == null || baralho == null || manilhaVirada == null) {
            System.err.println("Modo Roubo: Componente essencial não encontrado.");
            return false;
        }

        // 1. Extrai e inspeciona as cartas do baralho (Busca Linear Setup)
        List<Carta> cartasNoBaralho = new ArrayList<>();
        Carta cartaPilha;

        // Esvazia a pilha do baralho e move as cartas para uma lista
        while ((cartaPilha = baralho.ComprarCarta()) != null) {
            cartasNoBaralho.add(cartaPilha);
        }

        if (cartasNoBaralho.isEmpty()) {
            System.out.println("Modo Roubo: Baralho vazio.");
            return false;
        }

        // 2. BUSCA LINEAR: Encontra as cartas para a troca

        // Inicializa a busca com a primeira carta do baralho e a primeira carta da mão
        Carta cartaMaisForteBaralho = cartasNoBaralho.get(0);

        // CHAMA O MÉTODO CORRETO EM CONTROL_PARTIDA (cp)
        int forcaMax = cp.getForcaTruco(cartaMaisForteBaralho, manilhaVirada);

        Carta cartaMaisFracaHumano = jHumano.getMao().get(0);

        // CHAMA O MÉTODO CORRETO EM CONTROL_PARTIDA (cp)
        int forcaMinHumano = cp.getForcaTruco(cartaMaisFracaHumano, manilhaVirada);

        // Busca a carta mais forte no Baralho
        for (int i = 1; i < cartasNoBaralho.size(); i++) {
            Carta cartaAtual = cartasNoBaralho.get(i);
            int forcaAtual = cp.getForcaTruco(cartaAtual, manilhaVirada);

            if (forcaAtual > forcaMax) {
                forcaMax = forcaAtual;
                cartaMaisForteBaralho = cartaAtual;
            }
        }

        // Busca a carta mais fraca na Mão do Humano
        for (Carta carta : jHumano.getMao()) {
            int forcaAtual = cp.getForcaTruco(carta, manilhaVirada);
            if (forcaAtual < forcaMinHumano) {
                forcaMinHumano = forcaAtual;
                cartaMaisFracaHumano = carta;
            }
        }

        // 3. TROCA DE CARTAS
        cartasNoBaralho.remove(cartaMaisForteBaralho);
        jHumano.getMao().remove(cartaMaisFracaHumano);

        jHumano.getMao().add(cartaMaisForteBaralho); // Humano ganha a carta forte
        baralho.addCarta(cartaMaisFracaHumano);      // Baralho recebe a carta fraca

        // 4. Reconstroi o Baralho (coloca as cartas inspecionadas de volta na Pilha)
        for (Carta c : cartasNoBaralho) {
            baralho.addCarta(c);
        }

        // 5. Finaliza
        this.modoRouboUsado = true;
        System.out.println("Modo Roubo executado. Ganhou: " + cartaMaisForteBaralho.getValor() +
                " de " + cartaMaisForteBaralho.getNaipe() +
                ". Perdeu: " + cartaMaisFracaHumano.getValor() +
                " de " + cartaMaisFracaHumano.getNaipe());

        return true;
    }

    // --- Métodos de Placar e Getters/Setters ---

    public void atualizarPlacarSet(int pontosGanhos, int vencedorMao) {
        if (vencedorMao == 1) { // Time 1 venceu a Mão
            this.pontosSetTime1 += pontosGanhos;
        } else if (vencedorMao == -1) { // Time 2 venceu a Mão
            this.pontosSetTime2 += pontosGanhos;
        }

        // Zera o placar da Mão no Jogo (turnos ganhos)
        this.jogo.setPontosA(0);
        this.jogo.setPontosB(0);

        // ** ADICIONE LÓGICA DE VERIFICAÇÃO DE FIM DE SET/PARTIDA AQUI **
    }

    // REMOVIDO: Método getValorManilha(Carta manilhaVirada) movido para ControlPartida.

    // REMOVIDO: Método getForcaTruco(Carta carta, Carta manilhaVirada) movido para ControlPartida.

    // REMOVIDO: Método getValorManilha(Valor valorVirado) removido por ser duplicado e desnecessário.

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