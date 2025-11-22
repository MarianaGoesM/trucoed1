package view.jogo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
        cj = new ControlJogo(null);
        cp = new ControlPartida(ct, cj);

        cj.setCp(cp);

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
        this.painel.getLblModoRoubo().addMouseListener(this);
        this.painel.getLblPedirTruco().addMouseListener(this);
    }

    public void inicioPartida() {
        this.i = 0;


        this.painel.limparMesa();
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

        painel.atualizaPlacar(cp.getTurnosGanhosTime1(), cp.getTurnosGanhosTime2());
        painel.atualizaPlacarSet(cj.getPontosSetTime1(), cj.getPontosSetTime2());

        iniciarTurnoComVerificacao();
    }

    public void fimDeJogo(int vencedor) {
        String mensagem;
        String titulo;

        if (vencedor == 1) {
            titulo = "Parabéns, Você Ganhou!";
            mensagem = "Parabéns! Você alcançou 12 pontos. Deseja começar um Novo Jogo ou Sair?";
        } else {
            titulo = "Fim de Jogo";
            mensagem = "Tente Novamente! O time adversário alcançou 12 pontos. Deseja começar um Novo Jogo ou Sair?";
        }

        Object[] options = {"Novo Jogo", "Sair"};
        int n = JOptionPane.showOptionDialog(this,
                mensagem,
                titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (n == JOptionPane.YES_OPTION) {
            cj.setPontosSetTime1(0);
            cj.setPontosSetTime2(0);

            // Reseta a mão
            cj.setIndiceJogadorMao((cj.getIndiceJogadorMao() + 1) % 4);

            SwingUtilities.invokeLater(() -> {
                inicioPartida();
            });

        } else {
            System.exit(0);
        }
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

        if (cj.isTrucoPendente()) {
            if (e.getComponent() == this.painel.getLblPedirTruco()) {
                executarRespostaTruco();
            }
            return;
        }

        if (e.getComponent() == this.painel.getLblModoRoubo()) {
            executarModoRoubo();
            return;
        }

        if (e.getComponent() == this.painel.getLblPedirTruco()) {
            executarPedirTruco();
            return;
        }


        int cartasJogadasNoTurno = ct.getTurno().getCartasJogadas().size();
        int indiceInicial = cj.getIndiceJogadorMao();

        //fila encadeada circular
        int proximoAJogarIndex = indiceInicial;
        for (int i = 0; i < cartasJogadasNoTurno; i++) {
            proximoAJogarIndex = (proximoAJogarIndex - 1 + 4) % 4;
        }

        if (proximoAJogarIndex != 0) return;


        String name = e.getComponent().getName();

        Jogador<Carta> jogadorHumano = cj.getJogadorHumano();
        if (jogadorHumano == null) return;

        Carta cartaJogadaJogador = null;
        List<Carta> cartasHumano = jogadorHumano.getMao();
        JLabel card = null;

        if (name != null && name.equalsIgnoreCase(this.painel.getCard1().getName())) {
            card = painel.getCard1();
        } else if (name != null && name.equalsIgnoreCase(this.painel.getCard2().getName())) {
            card = painel.getCard2();
        } else if (name != null && name.equalsIgnoreCase(this.painel.getCard3().getName())) {
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

    private void executarRespostaTruco() {
        int valorAtual = cj.getValorAtualMao();
        String mensagem = "Seu oponente pediu " + (valorAtual == 3 ? "TRUCO" : valorAtual) + ". Deseja aceitar?";

        Object[] options = {"Aceitar", "Correr"};
        int n = JOptionPane.showOptionDialog(this,
                mensagem,
                "Aposta Pendente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (n == JOptionPane.YES_OPTION) {
            cj.aceitarTruco();
            iniciarFluxoPC();
        } else {
            // O jogador (Time 1) correu. Time 2 ganha a pontuação anterior.
            cj.correrTruco(1);
            SwingUtilities.invokeLater(() -> {
                painel.limparMesa();
                cj.setIndiceJogadorMao((cj.getIndiceJogadorMao() + 1) % 4);
                inicioPartida();
            });
        }

        removerCardMouseListener();
        addCardMouseListener();
    }


    private void executarModoRoubo() {

        removerCardMouseListener();

        boolean sucesso = cj.aplicarModoRoubo();

        if (sucesso) {
            SwingUtilities.invokeLater(() -> {
                Jogador<Carta> jHumano = cj.getJogadorHumano();
                if (jHumano != null) {
                    painel.atualizarMaoHumano(jHumano.getMao());
                }
                addCardMouseListener();
            });
        } else {
            addCardMouseListener();
        }
    }

    private void executarPedirTruco() {
        removerCardMouseListener();

        cj.pedirTruco();

        if (cj.isTrucoPendente()) {
            // Se Truco/Seis for pendente (PC pediu aumento ou Truco), mostra o pop-up
            executarRespostaTruco();
        } else {
            // Se o PC aceitou (valorAtualMao=3) ou correu (valorAtualMao=1)
            if (cj.getValorAtualMao() == 1) {
                SwingUtilities.invokeLater(() -> {
                    painel.limparMesa();
                    cj.setIndiceJogadorMao((cj.getIndiceJogadorMao() + 1) % 4);
                    inicioPartida();
                });
            } else {
                // PC aceitou o Truco (valorAtualMao=3). O jogo continua normalmente.
                SwingUtilities.invokeLater(() -> addCardMouseListener());
            }
        }
    }


    private void iniciarFluxoPC() {
        new Thread(() -> {
            try {
                if (cj.isTrucoPendente()) return;

                List<Jogador<Carta>> jogadores = cj.getJogo().getJogadores();

                int cartasJogadas = ct.getTurno().getCartasJogadas().size();
                int indiceInicial = cj.getIndiceJogadorMao();

                int proximoAJogarIndex = indiceInicial;

                for (int i = 0; i < cartasJogadas; i++) {
                    proximoAJogarIndex = (proximoAJogarIndex - 1 + 4) % 4;
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

                    if (cj.isTrucoPendente()) {
                        SwingUtilities.invokeLater(() -> executarRespostaTruco());
                        return;
                    }

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
        System.out.println("PC JOGANDO: " + jogadorPC.getNome() + " - Carta: " + cartaPCJogada.getValor());
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
                if (resultado >= 10 || resultado <= -10) {
                    int vencedorMao = (resultado >= 10) ? 1 : -1;

                    cj.atualizarPlacarSet(cj.getValorAtualMao(), vencedorMao);

                    SwingUtilities.invokeLater(() -> {
                        painel.atualizaPlacar(cp.getTurnosGanhosTime1(), cp.getTurnosGanhosTime2());
                        painel.atualizaPlacarSet(cj.getPontosSetTime1(), cj.getPontosSetTime2());

                        if (cj.getPontosSetTime1() >= 12) {
                            fimDeJogo(1);
                        } else if (cj.getPontosSetTime2() >= 12) {
                            fimDeJogo(-1);
                        } else {
                            // Se não for Fim de Jogo, reinicia a rodada
                            cp.aplicarDelay(3);
                            painel.limparMesa();
                            cj.setIndiceJogadorMao((cj.getIndiceJogadorMao() + 1) % 4);
                            inicioPartida();
                        }
                    });

                }
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
        this.painel.getLblModoRoubo().removeMouseListener(this);
        this.painel.getLblPedirTruco().removeMouseListener(this);
    }

    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    public PainelJogo getPainel() { return painel; }
    public ControlJogo getControlJogo() { return cj; }
    public void setPainel(PainelJogo painel) { this.painel = painel; }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
}