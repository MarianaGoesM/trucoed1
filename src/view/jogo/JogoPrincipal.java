package view.jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import controller.ControlJogo;
import controller.ControlPartida;
import controller.ControlTurno;
import model.Carta;
import model.Jogador;
import model.CartaJogada;

public class JogoPrincipal extends JFrame implements MouseListener {

    private int i = 0;
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
        this.i = 0;

        cp.novaPartida();
        cj.getJogo().addPartida(cp.getPartida());
        boolean tipoBaralho = cj.getJogo().getB() != null ? cj.getJogo().getB().isTipo() : false;
        cj.setarBaralho(tipoBaralho);

        for (Jogador<Carta> j : cj.getJogo().getJogadores()) {
            j.getMao().clear();
        }

        cp.distribuiCartas(cj.getJogo().getJogadores(), cj.getJogo().getB());

        painel.criarTela(cj.getJogo().getJogadores());

        if (cp.getPartida() != null && cp.getPartida().getManilha() != null) {
            this.painel.setManilha(cp.getPartida().getManilha().getNaipe().toString().toLowerCase(),
                    cp.getPartida().getManilha().getValor().toString().toLowerCase());
        }

        iniciarTurnoComVerificacao();
    }

    private void iniciarTurnoComVerificacao() {
        cp.iniciarTurno();

        int indiceJogadorMao = cj.getIndiceJogadorMao();

        if (indiceJogadorMao == 0) {
            addCardMouseListener();
        } else {
            iniciarFluxoPC();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int cartasJogadasNoTurno = ct.getTurno().getCartasJogadas().size();
        int indiceInicial = cj.getIndiceJogadorMao();

        int jogadorDaVezIndex = (indiceInicial - cartasJogadasNoTurno + 4) % 4;

        if (jogadorDaVezIndex != 0) return;

        String name = e.getComponent().getName();

        Jogador<Carta> jogadorHumano = cj.getJogadorHumano();
        if (jogadorHumano == null) return;

        Carta cartaJogadaJogador = null;
        List<Carta> cartasHumano = jogadorHumano.getMao();
        JLabel card = null;

        if (name.equalsIgnoreCase(this.painel.getCard1().getName())) {
            card = painel.getCard1();
        } else if (name.equalsIgnoreCase(this.painel.getCard2().getName())) {
            card = painel.getCard2();
        } else if (name.equalsIgnoreCase(this.painel.getCard3().getName())) {
            card = painel.getCard3();
        }

        if (card != null) {
            Carta cartaParaRemover = null;
            for (Carta carta : cartasHumano) {
                if (card.getName().contains(carta.getNaipe().toString().toLowerCase())
                        && card.getName().contains(carta.getValor().toString().toLowerCase())) {
                    cartaJogadaJogador = carta;
                    cartaParaRemover = carta;
                    break;
                }
            }
            if (cartaParaRemover != null) {
                cartasHumano.remove(cartaParaRemover);
            }
        }

        if (cartaJogadaJogador != null) {
            removerCardMouseListener();

            this.painel.moverCardParaMesa(card, cartaJogadaJogador.getNaipe().toString().toLowerCase(),
                    cartaJogadaJogador.getValor().toString().toLowerCase());

            ct.novaCartaJogada(jogadorHumano, cartaJogadaJogador);

            iniciarFluxoPC();
        }
    }


    private void iniciarFluxoPC() {
        new Thread(() -> {
            try {
                List<Jogador<Carta>> jogadores = cj.getJogo().getJogadores();

                int cartasJogadas = ct.getTurno().getCartasJogadas().size();

                int indiceInicial = cj.getIndiceJogadorMao();

                int proximoAJogarIndex;
                if (cartasJogadas == 0) {
                    proximoAJogarIndex = indiceInicial;
                } else {
                    int ultimoAJogarIndex = (indiceInicial - cartasJogadas + 1 + 4) % 4;
                    proximoAJogarIndex = (ultimoAJogarIndex - 1 + 4) % 4;
                }

                int jogadasRestantes = 4 - cartasJogadas;

                for (int count = 0; count < jogadasRestantes; count++) {

                    if (proximoAJogarIndex == 0) {
                        SwingUtilities.invokeLater(() -> addCardMouseListener());
                        break;
                    }

                    Jogador<Carta> jogadorPC = jogadores.get(proximoAJogarIndex);

                    cp.aplicarDelay(1);

                    simularJogadaPC(jogadorPC);

                    proximoAJogarIndex = (proximoAJogarIndex - 1 + 4) % 4;
                }

                if (ct.getTurno().getCartasJogadas().size() == 4) {
                    SwingUtilities.invokeLater(() -> verificarFimTurno());
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void simularJogadaPC(Jogador<Carta> jogadorPC) {
        List<Carta> maoPC = jogadorPC.getMao();
        if (maoPC.isEmpty()) return;

        Carta cartaPCJogada = maoPC.get(0);
        System.out.println("DEBUG: PC JOGANDO: " + jogadorPC.getNome() + " - Carta: " + cartaPCJogada.getValor());
        ct.novaCartaJogada(jogadorPC, cartaPCJogada);

        SwingUtilities.invokeLater(() -> {
            int pcIndex = cj.getJogo().getJogadores().indexOf(jogadorPC);
            String naipe = cartaPCJogada.getNaipe().toString();
            String valor = cartaPCJogada.getValor().toString();

            if (pcIndex == 2) {
                painel.viraCartaPc(naipe, valor);
            } else if (pcIndex == 1) {
                painel.viraCartaPCSide1(naipe, valor);
            } else if (pcIndex == 3) {
                painel.viraCartaPCSide2(naipe, valor);
            }
        });

        maoPC.remove(0);
    }

    private void verificarFimTurno() {
        List<CartaJogada> cartasDoTurno = ct.getTurno().getCartasJogadas();

        if (cartasDoTurno.size() != 4) return;

        int resultado = cp.verificarVencedorTurno(cartasDoTurno, cp.getPartida().getManilha());

        new Thread(() -> {
            try {
                // FIM DA MÃO (MELHOR DE 3 RODADAS)
                if (resultado == 10 || resultado == -10) {
                    int vencedorMao = (resultado == 10) ? 1 : -1;
                    cj.atualizarPlacarSet(1, vencedorMao);

                    SwingUtilities.invokeLater(() -> {
                        painel.atualizaPlacar(cj.getJogo().getPontosA(), cj.getJogo().getPontosB());
                        painel.atualizaPlacarSet(cj.getPontosSetTime1(), cj.getPontosSetTime2());
                    });

                    cp.aplicarDelay(3);

                    SwingUtilities.invokeLater(() -> {
                        painel.limparMesa(); // LIMPA A MESA FINALMENTE
                        inicioPartida();
                    });
                }
                // TURNO GANHO OU EMPATE (A MÃO CONTINUA)
                else {

                    cj.getJogo().setPontosA(cp.getTurnosGanhosTime1());
                    cj.getJogo().setPontosB(cp.getTurnosGanhosTime2());

                    if (resultado != 0) {
                        CartaJogada cartaVencedora = encontrarCartaVencedora(cartasDoTurno, cp.getPartida().getManilha());
                        int vencedorIndex = cj.getJogo().getJogadores().indexOf(cartaVencedora.getJogador());
                        cj.setIndiceJogadorMao(vencedorIndex);
                    }

                    SwingUtilities.invokeLater(() -> {
                        painel.atualizaPlacar(cj.getJogo().getPontosA(), cj.getJogo().getPontosB());
                    });

                    cp.aplicarDelay(2);

                    SwingUtilities.invokeLater(() -> {
                        painel.limparMesa();

                        Jogador<Carta> jHumano = cj.getJogadorHumano();
                        if (jHumano != null) {
                            painel.atualizarMaoHumano(jHumano.getMao());
                        }

                        iniciarTurnoComVerificacao();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private CartaJogada encontrarCartaVencedora(List<CartaJogada> cartasDoTurno, Carta manilha) {
        if (cartasDoTurno.isEmpty()) return null;

        CartaJogada cartaVencedora = cartasDoTurno.get(0);

        for (int i = 1; i < cartasDoTurno.size(); i++) {
            CartaJogada cartaAtual = cartasDoTurno.get(i);
            int resultado = cp.compararCartasTruco(cartaAtual.getCarta(), cartaVencedora.getCarta(), manilha);

            if (resultado == 1) {
                cartaVencedora = cartaAtual;
            } else if (resultado == 0) {
            }
        }
        return cartaVencedora;
    }

    public void removerCardMouseListener() {
        this.painel.getCard1().removeMouseListener(this);
        this.painel.getCard2().removeMouseListener(this);
        this.painel.getCard3().removeMouseListener(this);
    }

    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    public PainelJogo getPainel() { return painel; }
    public ControlJogo getControlJogo() { return cj; }
    public void setPainel(PainelJogo painel) { this.painel = painel; }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
}