package view.jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import controller.ControlJogo;
import controller.ControlPartida;
import controller.ControlTurno;
import model.Carta;
import model.Jogador;
import model.Jogo;
import model.CartaJogada; // Importar CartaJogada

public class JogoPrincipal extends JFrame implements MouseListener {

    private int i = 0; // Índice do turno atual (0, 1, 2)
    private PainelJogo painel;
    private ControlPartida cp;
    private ControlJogo cj;
    private ControlTurno ct;

    public JogoPrincipal(String nome, boolean tipo, int n) {
        ct = new ControlTurno();
        cp = new ControlPartida(ct);
        cj = new ControlJogo(cp);

        cj.iniciaJogo(nome, tipo, n);

        this.setSize(900, 600);
        this.setResizable(false);

        painel = new PainelJogo();
        this.setContentPane(painel);

        inicioPartida();
    }

    public void addCardMouseListener() {
        this.painel.getCard1().addMouseListener(this);
        this.painel.getCard2().addMouseListener(this);
        this.painel.getCard3().addMouseListener(this);
    }

    public void inicioPartida() {
        // Zera o contador de turnos para a nova mão
        this.i = 0;

        // Garante que uma nova partida/mão está pronta
        cp.novaPartida();
        cj.getJogo().addPartida(cp.getPartida());

        // Distribui cartas
        cp.distribuiCartas(cj.getJogo().getJogadores(), cj.getJogo().getB());

        // Cria a tela, passando a lista COMPLETA de jogadores
        painel.criarTela(cj.getJogo().getJogadores());

        // Configura a manilha
        if (cp.getPartida() != null && cp.getPartida().getManilha() != null) {
            this.painel.setManilha(cp.getPartida().getManilha().getNaipe().toString().toLowerCase(),
                    cp.getPartida().getManilha().getValor().toString().toLowerCase());
        }

        addCardMouseListener();

        // Inicia um novo Turno (Rodada 1)
        cp.iniciarTurno();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String name = e.getComponent().getName();

        Jogador<Carta> jogadorHumano = cj.getJogadorHumano();
        if (jogadorHumano == null) return;

        Carta cartaJogadaJogador = null;
        Jogo jogo = cj.getJogo();

        List<Carta> cartasHumano = jogadorHumano.getMao();
        List<Jogador<Carta>> jogadores = cj.getJogo().getJogadores();

        JLabel card = null;

        // 1. Identifica e processa a jogada do Humano
        if (name.equalsIgnoreCase(this.painel.getCard1().getName())) {
            card = painel.getCard1();
        } else if (name.equalsIgnoreCase(this.painel.getCard2().getName())) {
            card = painel.getCard2();
        } else if (name.equalsIgnoreCase(this.painel.getCard3().getName())) {
            card = painel.getCard3();
        }

        if (card != null) {
            for (Carta carta : cartasHumano) {
                if (card.getName().contains(carta.getNaipe().toString().toLowerCase())
                        && card.getName().contains(carta.getValor().toString().toLowerCase())) {
                    cartaJogadaJogador = carta;
                    // Remove a carta da mão do Humano
                    cartasHumano.remove(carta);
                    break;
                }
            }
        }

        // Se o Humano jogou uma carta válida
        if (cartaJogadaJogador != null) {
            // Move a carta do Humano para a mesa
            this.painel.moverCardParaMesa(card, cartaJogadaJogador.getNaipe().toString().toLowerCase(),
                    cartaJogadaJogador.getValor().toString().toLowerCase());

            // 1. REGISTRA A JOGADA DO HUMANO
            ct.novaCartaJogada(jogadorHumano, cartaJogadaJogador);

            // 2. Simula e REGISTRA as jogadas dos 3 PCs
            for (int pcIndex = 1; pcIndex < jogadores.size(); pcIndex++) {
                Jogador<Carta> jogadorPC = jogadores.get(pcIndex);
                List<Carta> maoPC = jogadorPC.getMao();

                // CORREÇÃO ESSENCIAL: PC SEMPRE JOGA O ÍNDICE 0 E REMOVE O ÍNDICE 0
                // O índice 'i' é apenas para contar o número de turnos.
                if (!maoPC.isEmpty()) {
                    Carta cartaPCJogada = maoPC.get(0); // Pega a primeira carta restante

                    // Registra a jogada do PC
                    ct.novaCartaJogada(jogadorPC, cartaPCJogada);

                    // Movimentação visual da carta do PC (Layout 2x2)
                    String naipe = cartaPCJogada.getNaipe().toString();
                    String valor = cartaPCJogada.getValor().toString();

                    if (pcIndex == 2) { // PC 2 (Parceiro) -> Posição da Frente
                        painel.viraCartaPc(naipe, valor);
                    } else if (pcIndex == 1) { // PC 1 (Oponente) -> Posição Lateral 1
                        painel.viraCartaPCSide1(naipe, valor);
                    } else if (pcIndex == 3) { // PC 3 (Oponente) -> Posição Lateral 2
                        painel.viraCartaPCSide2(naipe, valor);
                    }

                    // Remove a carta da mão do PC
                    maoPC.remove(0); // Remove o index 0
                }
            }

            // 3. Incrementa 'i' (contador de turnos jogados: 0 -> 1 -> 2)
            i++;

            // 4. Verifica Vencedor do Turno (apenas se 4 cartas foram jogadas)
            List<CartaJogada> cartasDoTurno = ct.getTurno().getCartasJogadas();

            if (cartasDoTurno.size() == 4) {

                int vencedorTurno = cp.verificarVencedorTurno(cartasDoTurno, cp.getPartida().getManilha());

                // Lógica de pontuação por turno (1 ponto)
                if (vencedorTurno == 1) {
                    jogo.setPontosA(jogo.getPontosA() + 1); // Time 1 venceu o Turno
                } else if (vencedorTurno == -1) {
                    jogo.setPontosB(jogo.getPontosB() + 1); // Time 2 venceu o Turno
                }
                // Se for empate (0), o ControlPartida já deve ter marcado 'isMelado' no Turno.

                // ATUALIZAÇÃO DO PLACAR VISUAL
                painel.atualizaPlacar(jogo.getPontosA(), jogo.getPontosB());

                // Se o placar NÃO estiver atualizando, verifique o PainelJogo (veja a recomendação abaixo)

                // FLUXO DE JOGO: Mão/Partida

                if (i >= 3) {
                    // A MÃO (3 turnos) ACABOU - Inicia a próxima Partida/Mão
                    // ** ADICIONE A LÓGICA DE VERIFICAR VENCEDOR DA MÃO AQUI **

                    // placeholder: Inicia uma nova mão
                    System.out.println("Mão encerrada. Nova mão iniciada.");
                    // Chame um método de limpeza visual da mesa aqui
                    inicioPartida(); // Reinicia o processo (distribui cartas, zera 'i')
                } else {
                    // O Turno acabou, mas a Mão continua. Inicia o próximo Turno.
                    // ** ADICIONE UM DELAY (Thread.sleep) E LIMPEZA VISUAL AQUI **
                    cp.iniciarTurno();
                }
            }
        }
    }

    // ... (mouseEntered, mouseExited e Getters/Setters permanecem iguais)

    @Override
    public void mouseEntered(MouseEvent e) {
        String name = e.getComponent().getName();
        Carta c = null;
        JLabel card = null;
        List<Carta> cartas = cj.getJogadorHumano().getMao();

        if (name.equalsIgnoreCase(this.painel.getCard1().getName())) {
            card = painel.getCard1();
        } else if (name.equalsIgnoreCase(this.painel.getCard2().getName())) {
            card = painel.getCard2();
        } else if (name.equalsIgnoreCase(this.painel.getCard3().getName())) {
            card = painel.getCard3();
        }

        if (card != null) {
            for (Carta carta : cartas) {
                if (card.getName().contains(carta.getNaipe().toString().toLowerCase())
                        && card.getName().contains(carta.getValor().toString().toLowerCase())) {
                    c = carta;
                    break;
                }
            }
            if (c != null) {
                painel.setIconeGrande(card, c.getNaipe().toString().toLowerCase(), c.getValor().toString().toLowerCase());
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        String name = e.getComponent().getName();
        Carta c = null;
        JLabel card = null;
        List<Carta> cartas = cj.getJogadorHumano().getMao();

        if (name.equalsIgnoreCase(this.painel.getCard1().getName())) {
            card = painel.getCard1();
        } else if (name.equalsIgnoreCase(this.painel.getCard2().getName())) {
            card = painel.getCard2();
        } else if (name.equalsIgnoreCase(this.painel.getCard3().getName())) {
            card = painel.getCard3();
        }

        if (card != null) {
            for (Carta carta : cartas) {
                if (card.getName().contains(carta.getNaipe().toString().toLowerCase())
                        && card.getName().contains(carta.getValor().toString().toLowerCase())) {
                    c = carta;
                    break;
                }
            }
            if (c != null) {
                painel.setIconePequeno(card, c.getNaipe().toString().toLowerCase(), c.getValor().toString().toLowerCase(),
                        card.getX());
            }
        }
    }

    public PainelJogo getPainel() {
        return painel;
    }

    public ControlJogo getControlJogo() {
        return cj;
    }

    public void setPainel(PainelJogo painel) {
        this.painel = painel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}