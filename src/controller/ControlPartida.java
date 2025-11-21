package controller;

import java.util.List;
import java.util.Random;
import enumerated.Valor;
import model.Carta;
import model.CartaJogada;
import model.Jogador;
import model.Baralho;
import model.Pilha;
import model.Partida;

public class ControlPartida {

    private ControlTurno ct;
    private Partida partida;
    private ControlJogo cj;

    private int turnosGanhosTime1;
    private int turnosGanhosTime2;

    public ControlPartida(ControlTurno ct, ControlJogo cj) {
        this.ct = ct;
        this.cj = cj;
        this.turnosGanhosTime1 = 0;
        this.turnosGanhosTime2 = 0;
    }

    public void fimDeJogo(int vencedor) {
        // Esta função deve ser implementada na View (JogoPrincipal)
        // Como o JogoPrincipal implementa a lógica do pop-up, faremos um System.out.println
        // Aqui para sinalizar e evitar o erro "cannot resolve symbol".
        System.out.println("SINALIZADOR: FIM DE JOGO - Vencedor time " + (vencedor == 1 ? "1" : "2"));
    }


    public void novaPartida() {
        setPartida(new Partida());
        this.turnosGanhosTime1 = 0;
        this.turnosGanhosTime2 = 0;
    }

    public void resetTurnosGanhos() {
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

    private Valor getValorManilha(Carta manilhaVirada) {
        if (manilhaVirada == null) return null;
        return manilhaVirada.getValor().getProximoValor();
    }

    public int getForcaTruco(Carta manilhaVirada) {
        Valor valorManilha = getValorManilha(manilhaVirada);
        return valorManilha.getPesoTruco() * 10 + 4;
    }

    public int getForcaTruco(Carta carta, Carta manilhaVirada) {

        Valor valorManilha = getValorManilha(manilhaVirada);

        if (carta.getValor() == valorManilha) {
            int forcaBase = 100;
            return forcaBase + carta.getNaipe().getValor();
        }

        return carta.getValor().getPesoTruco() * 10 + carta.getNaipe().getValor();
    }


    public int compararCartasTruco(Carta c1, Carta c2, Carta manilha) {
        int forcaC1 = getForcaTruco(c1, manilha);
        int forcaC2 = getForcaTruco(c2, manilha);

        if (forcaC1 > forcaC2) {
            return 1;
        } else if (forcaC1 < forcaC2) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean IdentificarSeZap(Carta carta, Carta manilha) {
        if (carta.getValor().getValor() == manilha.getValor().getValor() + 1 && carta.getNaipe().getValor() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public boolean pcTemZap() {
        Jogador<Carta> pc1 = cj.getJogo().getJogadores().get(1);
        Carta manilhaVirada = partida.getManilha();

        for (Carta carta : pc1.getMao()) {
            if (IdentificarSeZap(carta, manilhaVirada)) {
                return true;
            }
        }
        return false;
    }

    public boolean pcTemCartaForte() {
        Jogador<Carta> pc1 = cj.getJogo().getJogadores().get(1);
        Carta manilhaVirada = partida.getManilha();

        for (Carta carta : pc1.getMao()) {
            int forca = getForcaTruco(carta, manilhaVirada);
            if (forca >= Valor.TRES.getPesoTruco() * 10) {
                return true;
            }
        }
        return false;
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