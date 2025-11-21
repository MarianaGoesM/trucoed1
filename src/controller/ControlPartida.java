package controller;

import java.util.List;
import java.util.Random;
import java.util.Set;

import model.*;
import enumerated.Valor; // Importe necessário para usar o enum Valor
import model.Carta;

public class ControlPartida {

    private ControlTurno ct;
    private Partida partida;

    private int turnosGanhosTime1;
    private int turnosGanhosTime2;

    public ControlPartida(ControlTurno ct) {
        this.ct = ct;
        this.turnosGanhosTime1 = 0;
        this.turnosGanhosTime2 = 0;
    }

    public void novaPartida() {
        setPartida(new Partida());
        this.turnosGanhosTime1 = 0;
        this.turnosGanhosTime2 = 0;
    }

    public void iniciarTurno() {
        ct.criaTurno();
        partida.addTurno(ct.getTurno());
    }


    public void distribuiCartas(List<Jogador<Carta>> j, Baralho b) {
        embaralhar(b);
        if (b.getCartas().isEmpty()){
            System.out.println("baralho empty");
            return;
        }

        int totalCartasPuxadas = 0;

        for (int i = 0; i < 3; i++) {
            System.out.println("entrou for");
            for (Jogador<Carta> jo : j) {
                System.out.println("teste");
                try {
                    System.out.println("quantidade antes de puxar: " + b.getCartas().size());
                    jo.addCarta(b.ComprarCarta());
                    System.out.println("quantidade dps e puxar: " + b.getCartas().size());
                    totalCartasPuxadas++;
                } catch (RuntimeException e) {
                    System.err.println("Erro ao distribuir carta (Mão): " + e.getMessage() + ". Cartas puxadas: " + totalCartasPuxadas);
                    return;
                }
            }
        }

        try {
            partida.setManilha(b.ComprarCarta());
            totalCartasPuxadas++;
            System.out.println("foi a manilha: ");
        } catch (RuntimeException e) {
            System.err.println("Erro ao puxar a manilha: " + e.getMessage());
        }

        System.out.println("Distribuição concluída. Total de cartas puxadas: 13");
    }

    public void embaralhar(Baralho b) {
        Pilha<Carta> pilhaAntiga = b.getCartas();
        List<Carta> listaParaEmbaralhar = new java.util.ArrayList<>();


        while (!pilhaAntiga.isEmpty()) {
            listaParaEmbaralhar.add(pilhaAntiga.pop());
        }


        java.util.Collections.shuffle(listaParaEmbaralhar);


        Pilha<Carta> novaPilha = new Pilha<Carta>(40);
        b.setCartas(novaPilha);


        for (Carta c : listaParaEmbaralhar) {
            novaPilha.push(c);
        }
        System.out.println("qnt cartas lista: " + listaParaEmbaralhar.size());
        System.out.println("Cartas pilha: " + novaPilha.size());
    }

    // MÉTODO AUXILIAR PARA CALCULAR A MANILHA DE VIRA
    private Valor getValorManilha(Carta manilhaVirada) {
        if (manilhaVirada == null) return null;
        return manilhaVirada.getValor().getProximoValor();
    }

    /**
     * Retorna a força/peso numérico de uma carta na ordem do Truco.
     * Necessário para a lógica de comparação e do Modo Roubo.
     * @param carta A carta a ser avaliada.
     * @param manilhaVirada A carta virada que define as manilhas do turno.
     * @return Um valor inteiro que representa a força da carta (maior é mais forte).
     */
    public int getForcaTruco(Carta carta, Carta manilhaVirada) {

        Valor valorManilha = getValorManilha(manilhaVirada);

        // --- 1. Cartas Manilha (Força 11 a 14) ---
        if (carta.getValor() == valorManilha) {
            int forcaBase = 10;
            // Usa o valor do naipe (assumindo ZAP=4, PICAFUMO=1)
            return forcaBase + carta.getNaipe().getValor();
        }

        // --- 2. Cartas Comuns (Força 1 a 10) ---
        // Usa o getPesoTruco() do enum Valor
        return carta.getValor().getPesoTruco();
    }


    /**
     * Compara duas cartas usando a força numérica do Truco.
     * @return 1 se c1 vence, -1 se c2 vence, 0 se empatar.
     */
    public int compararCartasTruco(Carta c1, Carta c2, Carta manilha) {
        int forcaC1 = getForcaTruco(c1, manilha);
        int forcaC2 = getForcaTruco(c2, manilha);

        if (forcaC1 > forcaC2) {
            return 1; // c1 vence
        } else if (forcaC1 < forcaC2) {
            return -1; // c2 vence
        } else {
            return 0; // Empate (Cores / Melado)
        }
    }

    public boolean IdentificarSeZap(Carta carta, Carta manilha) {
        if (carta.getValor().getValor() == manilha.getValor().getValor() + 1 && carta.getNaipe().getValor() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public int verificarVencedorTurno(List<CartaJogada> cartasJogadas, Carta manilha) {

        if (cartasJogadas == null || cartasJogadas.size() != 4) {
            return 0;
        }

        CartaJogada cartaVencedora = cartasJogadas.get(0);
        boolean empate = false;

        for (CartaJogada cj : cartasJogadas) {
            if (IdentificarSeZap(cj.getCarta(), manilha)) {
                int vencedor = cj.getJogador().getTime() == 1 ? 1 : -1;
                return pontuarMao(vencedor);
            }
        }

        for (int i = 1; i < cartasJogadas.size(); i++) {
            CartaJogada cartaAtual = cartasJogadas.get(i);
            int resultado = compararCartasTruco(cartaAtual.getCarta(), cartaVencedora.getCarta(), manilha);

            if (resultado == 1) {
                cartaVencedora = cartaAtual;
                empate = false;
            } else if (resultado == 0) {
                if (cartaAtual.getJogador().getTime() != cartaVencedora.getJogador().getTime()) {
                    empate = true;
                }
            }
        }

        if (empate) {
            ct.getTurno().setMelado(true);
            return 0;
        }

        int vencedorTurno = cartaVencedora.getJogador().getTime() == 1 ? 1 : -1;

        return pontuarMao(vencedorTurno);
    }

    private int pontuarMao(int vencedor) {
        if (vencedor == 1) {
            this.turnosGanhosTime1++;
        } else if (vencedor == -1) {
            this.turnosGanhosTime2++;
        }

        if (this.turnosGanhosTime1 >= 2 || this.turnosGanhosTime2 >= 2) {
            return (this.turnosGanhosTime1 >= 2) ? 10 : -10;
        }

        return vencedor;
    }

    public void aplicarDelay(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void verificarVendorPartida() {

    }

    public void atualizarPlacar() {

    }

    public void jogadaPC() {
        Random a = new Random();
        int tempo;
        tempo = a.nextInt(6);
        try {
            Thread.sleep(1000 + (tempo * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void jogadaJogador() {

    }

    public ControlTurno getCt() {
        return ct;
    }

    public void setCt(ControlTurno ct) {
        this.ct = ct;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getTurnosGanhosTime1() {
        return turnosGanhosTime1;
    }

    public int getTurnosGanhosTime2() {
        return turnosGanhosTime2;
    }
}