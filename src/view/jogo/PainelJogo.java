package view.jogo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Carta;
import model.Jogador;

public class PainelJogo extends JPanel {

    private JLabel card[];
    private JLabel cartaMesa;
    private JLabel manilha;
    private JLabel cartaPc; // Oponente sentado à frente

    private JLabel cardParceiro;
    private JLabel cardPCLateral;
    private JLabel lblNomePCParceiro;
    private JLabel lblNomePCLateral;
    private JLabel placarSet; // Placar do Set (Melhor de 2)

    private ImageIcon iconCard1;
    private ImageIcon iconCard2;
    private ImageIcon iconCard3;
    private JLabel cardJogadoPc;
    private JLabel placar; // Placar da Mão (Melhor de 3)
    private JLabel lblNomeJogador;
    private JLabel lblTipoBaralho;
    private JLabel lblPedirTruco;
    private JLabel lblVirarCarta;
    private JLabel lblDesce;
    private JLabel lblCorre;
    private ImageIcon iconPedirTruco;
    protected ImageIcon cardCostasGrande = new ImageIcon(
            this.getClass().getResource("/resource/img/cenario/carta-costas grande.png"));
    protected ImageIcon cardCostas = new ImageIcon(
            this.getClass().getResource("/resource/img/cenario/carta-costas.png"));

    public PainelJogo() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        this.card = new JLabel[3];
        for (int i = 0; i < this.card.length; i++) {
            this.card[i] = new JLabel();
        }

        this.manilha = new JLabel();
        this.manilha.setName("manilha");
        this.manilha.setBounds(399, 247, 73, 100);
        this.manilha.setIcon(new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png")));
        this.add(manilha);

        this.cartaMesa = new JLabel();
        this.cartaMesa.setBounds(424, 356, 73, 100);
        this.cartaMesa.setVisible(false);

        this.cartaPc = new JLabel();
        cartaPc.setIcon(cardCostas);
        this.cartaPc.setBounds( 424, 138, 73, 100);
        this.cartaPc.setVisible(true);
        this.add(cartaMesa);
        this.add(cartaPc);

        this.cardParceiro = new JLabel();
        cardParceiro.setIcon(cardCostas);
        this.cardParceiro.setBounds(200, 247, 73, 100);
        this.add(cardParceiro);

        this.cardPCLateral = new JLabel();
        cardPCLateral.setIcon(cardCostas);
        this.cardPCLateral.setBounds(650, 247, 73, 100);
        this.add(cardPCLateral);


        lblNomeJogador = new JLabel();
        lblNomeJogador.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        lblNomeJogador.setBounds(33, 492, 812, 48);
        this.add(lblNomeJogador);

        lblNomePCParceiro = new JLabel("Parceiro");
        lblNomePCParceiro.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
        lblNomePCParceiro.setBounds(150, 200, 100, 20);
        this.add(lblNomePCParceiro);

        lblNomePCLateral = new JLabel("Oponente");
        lblNomePCLateral.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
        lblNomePCLateral.setBounds(700, 200, 100, 20);
        this.add(lblNomePCLateral);


        placar = new JLabel();
        placar.setBounds(196, 13, 649, 40);
        placar.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 42));
        this.add(placar);

        placarSet = new JLabel("Set: 0 X 0");
        placarSet.setBounds(196, 50, 649, 40);
        placarSet.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        this.add(placarSet);


        lblPedirTruco = new JLabel("TRUCO");
        lblPedirTruco.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblPedirTruco.setBounds(706, 200, 188, 104);
        this.add(lblPedirTruco);

        lblVirarCarta = new JLabel("VIRAR");
        lblVirarCarta.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblVirarCarta.setBounds(12, 200, 188, 104);
        this.add(lblVirarCarta);

        lblDesce = new JLabel("DESCE");
        lblDesce.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblDesce.setBounds(706, 277, 171, 104);
        this.add(lblDesce);

        lblCorre = new JLabel("CORRE");
        lblCorre.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblCorre.setBounds(12, 277, 188, 104);
        this.add(lblCorre);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image background = new ImageIcon(getClass().getResource("/resource/img/cenario/fundo-mesa-menor.jpg"))
                .getImage();
        g.drawImage(background, 0, 0, this);

    }

    public void criarTela(List<Jogador<Carta>> jogadores) {

        if (jogadores == null || jogadores.size() < 4) {
            System.err.println("ERRO: Número de jogadores insuficiente para criar a tela 2x2.");
            return;
        }

        // 1. Configura o Jogador Humano (Posição 0)
        Jogador<Carta> jHumano = jogadores.get(0);
        lblNomeJogador.setText(jHumano.getNome());

        if (jHumano.getMao() == null || jHumano.getMao().isEmpty()) {
            System.err.println("ERRO: Jogador Humano não recebeu cartas.");
            return;
        }


        // 2. Configura a mão do Jogador Humano
        int x = 256;
        List<Carta> mao = jHumano.getMao();

        // Remove listeners antigos antes de configurar novos
        for (JLabel c : card) {
            if (c.getMouseListeners().length > 0) {
                c.removeMouseListener(c.getMouseListeners()[0]);
            }
        }

        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);

            card[i].setName("carta" + i + "-" + carta.getNaipe().toString().toLowerCase() + "-"
                    + carta.getValor().toString().toLowerCase());

            setIconePequeno(card[i], carta.getNaipe().toString().toLowerCase(),
                    carta.getValor().toString().toLowerCase(), x);

            configLabel(card[i]);

            x += 175;
        }

        // Esconde cartas que sobraram se a mão for menor que 3 (última rodada)
        for (int i = mao.size(); i < 3; i++) {
            card[i].setVisible(false);
        }

        // 3. Configura nomes dos PCs (Assumindo que o Parceiro (2) fica na frente)
        lblNomePCParceiro.setText(jogadores.get(2).getNome());
        lblNomePCLateral.setText(jogadores.get(3).getNome());
        cartaPc.setName(jogadores.get(2).getNome());

    }


    public void configLabel(JLabel card) {
        card.setVisible(true);
        this.add(card);
    }

    // hover para valores
    public void setIconePequeno(JLabel card, String naipe, String valor, int x) {
        ImageIcon icone = new ImageIcon(
                this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        card.setBounds(x, 400, 150, 200);
        card.setIcon(icone);
    }

    // hover para carta costas
    public void setIconeGrande(JLabel card, String naipe, String valor) {

        ImageIcon icone = new ImageIcon(this.getClass()
                .getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + "-grande.jpg"));
        card.setBounds(card.getX(), card.getY() - 50, card.getWidth(), card.getHeight());
        card.setIcon(icone);
    }

    // para carta costas
    public void setIconePequeno(JLabel card) {
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png"));
        if (card.getName().equals("card1"))
            card.setBounds(290, 462, 73, 100);
        else if (card.getName().equals("card2"))
            card.setBounds(426, 462, 73, 100);
        else if (card.getName().equals("card3"))
            card.setBounds(576, 462, 73, 100);
        card.setIcon(cardCostas);
    }

    public void atualizaPlacar(int pontosTime1, int pontosTime2) {
        // Placar da Mão (Turnos Ganhos)
        placar.setText("Mão: " + pontosTime1 + " X " + pontosTime2);
        this.placar.repaint();
    }

    public void atualizaPlacarSet(int pontosSetTime1, int pontosSetTime2) {
        placarSet.setText("Set: " + pontosSetTime1 + " X " + pontosSetTime2);
        placarSet.repaint();
    }

    public void limparMesa() {
        cartaMesa.setVisible(false);
        cartaPc.setIcon(cardCostas);
        cardParceiro.setIcon(cardCostas);
        cardPCLateral.setIcon(cardCostas);



        this.repaint();
    }

    public void moverCardParaMesa(JLabel card) {
        this.setIconePequeno(card);
        card.setBounds(424, 356, 73, 100);
        if (card.getMouseListeners().length > 0) {
            card.removeMouseListener(card.getMouseListeners()[0]);
        }
    }

    public void moverCardParaMesa(JLabel card, String naipe, String valor) {
        cartaMesa.setIcon(new ImageIcon(
                this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg")));
        cartaMesa.setVisible(true);
        card.setVisible(false);
        if (card.getMouseListeners().length > 0) {
            card.removeMouseListener(card.getMouseListeners()[0]);
        }
    }

    public void viraCartaPc(String naipe, String valor){
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.cartaPc.setIcon(icone);
        this.setVisible(true);
    }

    public void viraCartaPCSide1(String naipe, String valor){
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.cardParceiro.setIcon(icone);
        this.cardParceiro.setVisible(true);
    }

    public void viraCartaPCSide2(String naipe, String valor){
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.cardPCLateral.setIcon(icone);
        this.cardPCLateral.setVisible(true);
    }

    public void atualizarMaoHumano(List<Carta> mao) {
        int x = 256;

        // 1. Esconde todos os cards para começar a re-renderização
        for (JLabel c : card) {
            c.setVisible(false);
        }

        // 2. Renderiza apenas as cartas restantes na mão
        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);
            JLabel currentCardLabel = card[i]; // Usa o JLabel de posição i

            // Renomeia o label com a carta correta
            currentCardLabel.setName("carta" + i + "-" + carta.getNaipe().toString().toLowerCase() + "-"
                    + carta.getValor().toString().toLowerCase());

            // Redefine o ícone e a posição
            setIconePequeno(currentCardLabel, carta.getNaipe().toString().toLowerCase(),
                    carta.getValor().toString().toLowerCase(), x);

            currentCardLabel.setVisible(true); // Torna a carta visível

            x += 175;
        }

        this.repaint();
    }


    // =========================================================
    // GETTERS AND SETTERS COMPLETOS
    // =========================================================

    public JLabel getCartaMesa() {
        return cartaMesa;
    }

    public void setCartaMesa(JLabel cartaMesa) {
        this.cartaMesa = cartaMesa;
    }

    public JLabel getManilha() {
        return manilha;
    }

    public void setManilha(JLabel manilha) {
        this.manilha = manilha;
    }


    public void setManilha(String naipe, String valor) {
        ImageIcon icone = new ImageIcon(
                this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.manilha.setIcon(icone);
    }

    public JLabel getCartaPc() {
        return cartaPc;
    }

    public void setCartaPc(JLabel cartaPc) {
        this.cartaPc = cartaPc;
    }

    public JLabel getCardParceiro() {
        return cardParceiro;
    }

    public void setCardParceiro(JLabel cardParceiro) {
        this.cardParceiro = cardParceiro;
    }

    public JLabel getCardPCLateral() {
        return cardPCLateral;
    }

    public void setCardPCLateral(JLabel cardPCLateral) {
        this.cardPCLateral = cardPCLateral;
    }

    public JLabel getLblNomePCParceiro() {
        return lblNomePCParceiro;
    }

    public void setLblNomePCParceiro(JLabel lblNomePCParceiro) {
        this.lblNomePCParceiro = lblNomePCParceiro;
    }

    public JLabel getLblNomePCLateral() {
        return lblNomePCLateral;
    }

    public void setLblNomePCLateral(JLabel lblNomePCLateral) {
        this.lblNomePCLateral = lblNomePCLateral;
    }

    public JLabel getPlacarSet() {
        return placarSet;
    }

    public void setPlacarSet(JLabel placarSet) {
        this.placarSet = placarSet;
    }

    public ImageIcon getIconCard1() {
        return iconCard1;
    }

    public void setIconCard1(ImageIcon iconCard1) {
        this.iconCard1 = iconCard1;
    }

    public ImageIcon getIconCard2() {
        return iconCard2;
    }

    public void setIconCard2(ImageIcon iconCard2) {
        this.iconCard2 = iconCard2;
    }

    public ImageIcon getIconCard3() {
        return iconCard3;
    }

    public void setIconCard3(ImageIcon iconCard3) {
        this.iconCard3 = iconCard3;
    }

    public JLabel getCardJogadoPc() {
        return cardJogadoPc;
    }

    public void setCardJogadoPc(JLabel cardJogadoPc) {
        this.cardJogadoPc = cardJogadoPc;
    }

    public JLabel getPlacar() {
        return placar;
    }

    public void setPlacar(JLabel placar) {
        this.placar = placar;
    }

    public JLabel getLblNomeJogador() {
        return lblNomeJogador;
    }

    public void setLblNomeJogador(JLabel lblNomeJogador) {
        this.lblNomeJogador = lblNomeJogador;
    }

    public JLabel getLblTipoBaralho() {
        return lblTipoBaralho;
    }

    public void setLblTipoBaralho(JLabel lblTipoBaralho) {
        this.lblTipoBaralho = lblTipoBaralho;
    }

    public JLabel getLblPedirTruco() {
        return lblPedirTruco;
    }

    public void setLblPedirTruco(JLabel lblPedirTruco) {
        this.lblPedirTruco = lblPedirTruco;
    }

    public JLabel getLblVirarCarta() {
        return lblVirarCarta;
    }

    public void setLblVirarCarta(JLabel lblVirarCarta) {
        this.lblVirarCarta = lblVirarCarta;
    }

    public JLabel getLblDesce() {
        return lblDesce;
    }

    public void setLblDesce(JLabel lblDesce) {
        this.lblDesce = lblDesce;
    }

    public JLabel getLblCorre() {
        return lblCorre;
    }

    public void setLblCorre(JLabel lblCorre) {
        this.lblCorre = lblCorre;
    }

    public ImageIcon getIconPedirTruco() {
        return iconPedirTruco;
    }

    public void setIconPedirTruco(ImageIcon iconPedirTruco) {
        this.iconPedirTruco = iconPedirTruco;
    }

    public ImageIcon getCardCostasGrande() {
        return cardCostasGrande;
    }

    public void setCardCostasGrande(ImageIcon cardCostasGrande) {
        this.cardCostasGrande = cardCostasGrande;
    }

    public ImageIcon getCardCostas() {
        return cardCostas;
    }

    public void setCardCostas(ImageIcon cardCostas) {
        this.cardCostas = cardCostas;
    }

    public JLabel getCard1() {
        return card[0];
    }

    public JLabel getCard2() {
        return card[1];
    }

    public JLabel getCard3() {
        return card[2];
    }

    public JLabel[] getCard() {
        return card;
    }

    public void setCard(JLabel[] card) {
        this.card = card;
    }

    // Método setter para lblNomeJogador (usando o nome da variável local)
    public void setNomeJogador(JLabel nomeJogador) {
        this.lblNomeJogador = nomeJogador;
    }

    public String getNomeJogador() {
        return lblNomeJogador.getText();
    }
}