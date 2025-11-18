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

    // NOVOS COMPONENTES PARA 4 JOGADORES (2x2)
    private JLabel cardParceiro; // PC Parceiro (Lateral esquerda/direita)
    private JLabel cardPCLateral; // PC Oponente (Lateral oposta)
    private JLabel lblNomePCParceiro;
    private JLabel lblNomePCLateral;

    private ImageIcon iconCard1;
    private ImageIcon iconCard2;
    private ImageIcon iconCard3;
    private JLabel cardJogadoPc;
    private JLabel placar;
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

        // 1. Inicializa o array de JLabels para a MÃO do Jogador Humano
        this.card = new JLabel[3];
        for (int i = 0; i < this.card.length; i++) {
            this.card[i] = new JLabel();
        }

        // 2. INICIALIZAÇÃO E ADIÇÃO DOS COMPONENTES FIXOS

        this.manilha = new JLabel();
        this.manilha.setName("manilha");
        this.manilha.setBounds(399, 247, 73, 100);
        this.manilha.setIcon(new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png")));
        this.add(manilha);

        this.cartaMesa = new JLabel();
        this.cartaMesa.setBounds(424, 356, 73, 100);
        this.cartaMesa.setVisible(false);

        // PC 1 (Oponente - Frente)
        this.cartaPc = new JLabel();
        cartaPc.setIcon(cardCostas);
        this.cartaPc.setBounds( 424, 138, 73, 100);
        this.cartaPc.setVisible(true);
        this.add(cartaMesa);
        this.add(cartaPc);

        // NOVO: PC 2 (Parceiro - Lateral Esquerda)
        this.cardParceiro = new JLabel();
        cardParceiro.setIcon(cardCostas);
        this.cardParceiro.setBounds(200, 247, 73, 100);
        this.add(cardParceiro);

        // NOVO: PC 3 (Oponente - Lateral Direita)
        this.cardPCLateral = new JLabel();
        cardPCLateral.setIcon(cardCostas);
        this.cardPCLateral.setBounds(650, 247, 73, 100);
        this.add(cardPCLateral);


        // LABELS DE TEXTO

        // Nome Jogador Humano
        lblNomeJogador = new JLabel();
        lblNomeJogador.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        lblNomeJogador.setBounds(33, 492, 812, 48); // Posição inferior
        this.add(lblNomeJogador);

        // NOVO: Nome PC Parceiro (Lateral Esquerda)
        lblNomePCParceiro = new JLabel("Parceiro");
        lblNomePCParceiro.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
        lblNomePCParceiro.setBounds(150, 200, 100, 20);
        this.add(lblNomePCParceiro);

        // NOVO: Nome PC Lateral (Lateral Direita)
        lblNomePCLateral = new JLabel("Oponente");
        lblNomePCLateral.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
        lblNomePCLateral.setBounds(700, 200, 100, 20);
        this.add(lblNomePCLateral);


        placar = new JLabel();
        placar.setBounds(196, 13, 649, 86);
        placar.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 42));
        this.add(placar);

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

    // Método ajustado para receber a lista de 4 jogadores
    public void criarTela(List<Jogador<Carta>> jogadores) {

        if (jogadores == null || jogadores.size() < 4) {
            System.err.println("ERRO: Número de jogadores insuficiente para criar a tela 2x2.");
            return;
        }

        // Jogadores:
        // Posição 0: Jogador Humano (Baixo)
        // Posição 1: PC Oponente (Frente - cartaPc)
        // Posição 2: PC Parceiro (Lateral Esquerda - cardParceiro)
        // Posição 3: PC Oponente Lateral (Lateral Direita - cardPCLateral)
        // NOTA: A ordem acima é uma suposição baseada no ControlJogo.setarJogadoresJogo
        // onde a ordem era: Humano(1), PC1(2), PC2(Parceiro-1), PC3(2). A ordem real
        // na lista é a que importa. Usarei a ordem do ControlJogo.java:
        // 0: Humano (Time 1)
        // 1: PC 1 (Time 2 - Oponente da Frente) -> cartaPc
        // 2: PC 2 (Time 1 - Parceiro) -> cardParceiro
        // 3: PC 3 (Time 2 - Oponente Lateral) -> cardPCLateral


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

        for (int i = 0; i < card.length; i++) {
            // A instância card[i] já existe
            Carta carta = mao.get(i);

            card[i].setName("carta" + i + "-" + carta.getNaipe().toString().toLowerCase() + "-"
                    + carta.getValor().toString().toLowerCase());

            // setIconePequeno configura o ícone e a posição da carta do humano
            setIconePequeno(card[i], carta.getNaipe().toString().toLowerCase(),
                    carta.getValor().toString().toLowerCase(), x);

            configLabel(card[i]); // Adiciona e torna visível

            x += 175;
        }

        // 3. Configura nomes dos PCs
        lblNomePCParceiro.setText(jogadores.get(2).getNome()); // PC Parceiro (Time 1)
        lblNomePCLateral.setText(jogadores.get(3).getNome());  // PC Oponente Lateral (Time 2)
        cartaPc.setName(jogadores.get(1).getNome()); // PC Oponente (Frente - Time 2)

        // Nota: As cartas dos PCs laterais (cardParceiro, cardPCLateral) e da frente (cartaPc)
        // já estão configuradas para exibir "carta-costas.png" no construtor.
    }


    public void configLabel(JLabel card) {
        card.setVisible(true);
        this.add(card);
    }

    // hover para valores
    public void setIconePequeno(JLabel card, String naipe, String valor, int x) {
        System.out.println(naipe);
        System.out.println(valor);

        ImageIcon icone = new ImageIcon(
                this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        System.out.println(
                this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        System.out.println(this.getClass()
                .getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg").getFile());

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

    // PainelJogo.java (Verifique se o seu método é parecido com este)

    public void atualizaPlacar(int pontosA, int pontosB) {
        // 1. Atualize o texto do seu JLabel de placar
        this.placar.setText("Time A: " + pontosA + " x Time B: " + pontosB);

        // 2. Garanta que o Painel seja repintado para mostrar a mudança
        this.placar.repaint();
        this.revalidate();
        this.repaint();
    }

    // aqui move a carta costas
    public void moverCardParaMesa(JLabel card) {
        this.setIconePequeno(card);
        card.setBounds(424, 356, 73, 100);
        card.removeMouseListener(card.getMouseListeners()[0]);
    }

    // move a carta com valor
    public void moverCardParaMesa(JLabel card, String naipe, String valor) {
        System.out.println(card.getName());
        System.out.println(naipe);
        System.out.println(valor);
        cartaMesa.setIcon(new ImageIcon(
                this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg")));
        cartaMesa.setVisible(true);
        card.setVisible(false);
        card.removeMouseListener(card.getMouseListeners()[0]);
    }

    public JLabel getManilha() {
        return manilha;
    }

    public void setManilha(JLabel manilha) {
        this.manilha = manilha;
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

    public JLabel getPlacar() {
        return placar;
    }

    public void setPlacar(JLabel placar) {
        this.placar = placar;
    }

    public JLabel getNomeJogador() {
        return lblNomeJogador;
    }

    public void setNomeJogador(JLabel nomeJogador) {
        this.lblNomeJogador = nomeJogador;
    }

    public ImageIcon getIconPedirTruco() {
        return iconPedirTruco;
    }

    public void setIconPedirTruco(ImageIcon iconPedirTruco) {
        this.iconPedirTruco = iconPedirTruco;
    }

    public JLabel getCardJogadoPc() {
        return cardJogadoPc;
    }

    public void setCardJogadoPc(JLabel cardJogadoPc) {
        this.cardJogadoPc = cardJogadoPc;
    }

    public JLabel getTipoBaralho() {
        return lblTipoBaralho;
    }

    public void setTipoBaralho(JLabel tipoBaralho) {
        this.lblTipoBaralho = tipoBaralho;
    }

    public JLabel getPedirTruco() {
        return lblPedirTruco;
    }

    public void setPedirTruco(JLabel pedirTruco) {
        this.lblPedirTruco = pedirTruco;
    }

    public JLabel getVirarCard() {
        return lblVirarCarta;
    }

    public void setVirarCard(JLabel virarCard) {
        this.lblVirarCarta = virarCard;
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

    public void viraCartaPc(String naipe, String valor){
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.cartaPc.setIcon(icone);
        this.setVisible(true);
    }

    // NOVOS GETTERS E SETTERS
    public JLabel getCardParceiro() {
        return cardParceiro;
    }

    public JLabel getCardPCLateral() {
        return cardPCLateral;
    }

    public JLabel getLblNomePCParceiro() {
        return lblNomePCParceiro;
    }

    public JLabel getLblNomePCLateral() {
        return lblNomePCLateral;
    }

    // PainelJogo.java

// ... (Outros métodos e getters/setters)

    /**
     * Método para virar a carta do PC Oponente 1 (Índice 1 - Posição Lateral 1).
     * Assume que 'cardParceiro' é o JLabel para esta posição.
     */
    public void viraCartaPCSide1(String naipe, String valor){
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.cardParceiro.setIcon(icone);
        this.cardParceiro.setVisible(true);
        // Nota: Se precisar mover o card para a mesa, adicione lógica de setBounds aqui.
    }

    /**
     * Método para virar a carta do PC Oponente 2 (Índice 3 - Posição Lateral 2).
     * Assume que 'cardPCLateral' é o JLabel para esta posição.
     */
    public void viraCartaPCSide2(String naipe, String valor){
        ImageIcon icone = new ImageIcon(this.getClass().getResource("/resource/img/baralho/" + naipe + "/" + valor + "-de-" + naipe + ".jpg"));
        this.cardPCLateral.setIcon(icone);
        this.cardPCLateral.setVisible(true);
        // Nota: Se precisar mover o card para a mesa, adicione lógica de setBounds aqui.
    }
}