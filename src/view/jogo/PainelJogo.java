package view.jogo;

import java.awt.Color; // Import adicionado
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Carta;
import model.Jogador;

public class PainelJogo extends JPanel {

    // --- CONSTANTES DE TAMANHO DAS CARTAS ---
    // PLAYER (Mantido o tamanho grande: 151x216)
    private static final int PLAYER_CARD_WIDTH = 120;
    private static final int PLAYER_CARD_HEIGHT = 170;

    // PCs (Aumentado um pouco: 100x140)
    private static final int PC_CARD_WIDTH = 100;
    private static final int PC_CARD_HEIGHT = 140;

    // MANILHA (Diminuído um pouco: 120x170)
    private static final int MANILHA_CARD_WIDTH = 100;
    private static final int MANILHA_CARD_HEIGHT = 140;
    // ----------------------------------------

    private JLabel card[];
    private JLabel cartaMesa;
    private JLabel manilha;
    private JLabel cartaPc;

    private JLabel cardParceiro;
    private JLabel cardPCLateral;
    private JLabel lblNomePCParceiro;
    private JLabel lblNomePCLateral;
    private JLabel placarSet;

    private ImageIcon iconCard1;
    private ImageIcon iconCard2;
    private ImageIcon iconCard3;
    private JLabel cardJogadoPc;
    private JLabel placar;
    private JLabel lblNomeJogador;
    private JLabel lblTipoBaralho;
    private JLabel lblPedirTruco;
    private ImageIcon iconPedirTruco;
    private JLabel lblModoRoubo;

    // Icones de costas, carregados e redimensionados na inicialização
    protected ImageIcon cardCostasManilha;
    protected ImageIcon cardCostasPC;

    private ImageIcon createResizedIcon(String path, int width, int height) {
        URL location = this.getClass().getResource(path);

        if (location == null) {
            System.err.println("ERRO: Recurso não encontrado no caminho: " + path);
            return null;
        }

        try {
            ImageIcon iconeOriginal = new ImageIcon(location);
            Image imagem = iconeOriginal.getImage();

            Image imagemRedimensionada = imagem.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH
            );
            return new ImageIcon(imagemRedimensionada);
        } catch (Exception e) {
            System.err.println("Erro ao redimensionar a imagem: " + path);
            return null;
        }
    }
    // ------------------------------------------------------------------------

    public PainelJogo() {
        // Carrega os ícones de costas para PC e Manilha nos novos tamanhos
        this.cardCostasPC = createResizedIcon("/resource/img/cenario/carta-costas.jpg", PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.cardCostasManilha = createResizedIcon("/resource/img/cenario/carta-costas.jpg", MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);

        // Fallback simples para evitar NPE se o recurso falhar
        if (this.cardCostasPC == null) {
            this.cardCostasPC = new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png"));
        }
        if (this.cardCostasManilha == null) {
            this.cardCostasManilha = new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png"));
        }

        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        this.card = new JLabel[3];
        for (int i = 0; i < this.card.length; i++) {
            this.card[i] = new JLabel();
        }

        this.manilha = new JLabel();
        this.manilha.setName("manilha");
        this.manilha.setBounds(390, 280, MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);
        this.manilha.setIcon(cardCostasManilha); // Usa o ícone redimensionado para manilha
        this.add(manilha);

        this.cartaMesa = new JLabel();
        this.cartaMesa.setBounds(550, 420, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
        this.cartaMesa.setVisible(false);

        this.cartaPc = new JLabel();
        cartaPc.setIcon(cardCostasPC);
        this.cartaPc.setBounds( 550, 100, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.cartaPc.setVisible(true);
        this.add(cartaMesa);
        this.add(cartaPc);

        this.cardParceiro = new JLabel();
        cardParceiro.setIcon(cardCostasPC);
        this.cardParceiro.setBounds(250, 200, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.add(cardParceiro);

        this.cardPCLateral = new JLabel();
        cardPCLateral.setIcon(cardCostasPC);
        this.cardPCLateral.setBounds(850, 200, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.add(cardPCLateral);


        // --- LABELS E PLACAR (POSIÇÕES MANTIDAS/AJUSTADAS PARA TELA GRANDE) ---
        lblNomeJogador = new JLabel();
        lblNomeJogador.setFont(new Font("Rosewood Std Regular", Font.BOLD, 20));
        lblNomeJogador.setBounds(500, 700, 200, 48);
        lblNomeJogador.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(lblNomeJogador);

        lblNomePCParceiro = new JLabel("Parceiro");
        lblNomePCParceiro.setFont(new Font("Rosewood Std Regular", Font.BOLD, 14));
        lblNomePCParceiro.setBounds(250, 150, 100, 20);
        lblNomePCParceiro.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(lblNomePCParceiro);

        lblNomePCLateral = new JLabel("Oponente");
        lblNomePCLateral.setFont(new Font("Rosewood Std Regular", Font.BOLD, 14));
        lblNomePCLateral.setBounds(850, 150, 100, 20);
        lblNomePCLateral.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(lblNomePCLateral);

        placar = new JLabel();
        placar.setBounds(30, 13, 300, 40);
        placar.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 42));
        placar.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(placar);

        placarSet = new JLabel("Set: 0 X 0");
        placarSet.setBounds(30, 50, 300, 40);
        placarSet.setFont(new Font("Rosewood Std Regular", Font.BOLD, 20));
        placarSet.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(placarSet);

        lblPedirTruco = new JLabel("TRUCO");
        lblPedirTruco.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblPedirTruco.setBounds(950, 300, 188, 104);
        lblPedirTruco.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(lblPedirTruco);

        lblModoRoubo = new JLabel("ROUBO");
        lblModoRoubo.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblModoRoubo.setBounds(950, 380, 188, 104);
        lblModoRoubo.setForeground(Color.WHITE); // Cor do texto alterada para Branco
        this.add(lblModoRoubo);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image background = new ImageIcon(getClass().getResource("/resource/img/cenario/cenario0.png"))
                .getImage();
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    public void criarTela(List<Jogador<Carta>> jogadores) {
        // ... (Corpo da função mantido, apenas o setIconePequeno mudou) ...
        if (jogadores == null || jogadores.size() < 4) {
            System.err.println("ERRO: Número de jogadores insuficiente para criar a tela 2x2.");
            return;
        }

        Jogador<Carta> jHumano = jogadores.get(0);
        lblNomeJogador.setText(jHumano.getNome());

        if (jHumano.getMao() == null || jHumano.getMao().isEmpty()) {
            System.err.println("ERRO: Jogador Humano não recebeu cartas.");
            return;
        }

        int x = 360;
        List<Carta> mao = jHumano.getMao();

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

            x += 160;
        }

        for (int i = mao.size(); i < 3; i++) {
            card[i].setVisible(false);
        }

        lblNomePCParceiro.setText(jogadores.get(2).getNome());
        lblNomePCLateral.setText(jogadores.get(3).getNome());
        cartaPc.setName(jogadores.get(2).getNome());
    }

    public void configLabel(JLabel card) {
        card.setVisible(true);
        this.add(card);
    }


    public void setIconePequeno(JLabel card, String naipe, String valor, int x) {
        System.out.println(naipe + " " + valor + " de " + naipe.toLowerCase() + ".jpg");

        String path = "/resource/img/baralho/" + naipe.toLowerCase() + "/" + valor + " de " + naipe.toLowerCase() + ".jpg";

        // Redimensiona para o tamanho do PLAYER (151x216)
        ImageIcon icone = createResizedIcon(path, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);

        if(icone == null){
            // Fallback usando o ícone de costas do PC como um placeholder
            icone = cardCostasPC;
        }

        card.setBounds(x, 600, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
        card.setIcon(icone);
    }

    /**
     * Usado para virar a carta do humano na mesa (tamanho 151x216).
     */
    public void setIconeGrande(JLabel card, String naipe, String valor) {
        String naipeLower = naipe.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valor + " de " + naipeLower + ".jpg";
        ImageIcon icone = createResizedIcon(path, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);

        if (icone != null) {
            card.setBounds(card.getX(), card.getY() - 50, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            card.setIcon(icone);
        }
    }

    /**
     * Usado para limpar a mesa ou retornar cartas à mão de costas.
     * Usa o ícone de costas do PLAYER (151x216) para a mão humana, ou o de PC (100x140) para a mesa.
     */
    public void setIconePequeno(JLabel card) {
        // Se for uma das cartas na mão, usa o tamanho do PLAYER
        if (card.getName() != null && card.getName().contains("carta")) {
            ImageIcon playerCostas = createResizedIcon("/resource/img/cenario/carta-costas.png", PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            card.setIcon(playerCostas != null ? playerCostas : cardCostasPC);

            // Reposicionamento da mão humana (apenas para fallback, a criaçãoTela faz o correto)
            if (card.getName().contains("carta0"))
                card.setBounds(360, 500, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            else if (card.getName().contains("carta1"))
                card.setBounds(520, 500, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            else if (card.getName().contains("carta2"))
                card.setBounds(680, 500, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
        } else {
            // Se for usado para cartas na mesa (com nomes card1/card2/card3 - DEVE USAR TAMANHO DO PC)
            card.setIcon(cardCostasPC);
            if (card.getName().equals("card1"))
                card.setBounds(290, 462, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            else if (card.getName().equals("card2"))
                card.setBounds(426, 462, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            else if (card.getName().equals("card3"))
                card.setBounds(576, 462, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        }
    }

    // --- Outros métodos com ajustes de posicionamento/tamanho ---

    public void limparMesa() {
        cartaMesa.setVisible(false);
        // Usa o ícone de costas do PC (100x140)
        cartaPc.setIcon(cardCostasPC);
        cardParceiro.setIcon(cardCostasPC);
        cardPCLateral.setIcon(cardCostasPC);
        this.repaint();
    }

    public void moverCardParaMesa(JLabel card) {
        this.setIconePequeno(card);
        // Move para a posição da carta do humano na mesa (usando o tamanho do PC como placeholder)
        card.setBounds(550, 420, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        if (card.getMouseListeners().length > 0) {
            card.removeMouseListener(card.getMouseListeners()[0]);
        }
    }

    // Vira a carta do humano na mesa (Tamanho 151x216)
    public void moverCardParaMesa(JLabel card, String naipe, String valor) {
        String naipeLower = naipe.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valor + " de " + naipeLower + ".jpg";
        ImageIcon icone = createResizedIcon(path, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);

        if (icone != null) {
            cartaMesa.setIcon(icone);
            cartaMesa.setBounds(550, 420, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            cartaMesa.setVisible(true);
        }
        card.setVisible(false);
        if (card.getMouseListeners().length > 0) {
            card.removeMouseListener(card.getMouseListeners()[0]);
        }
    }

    // Vira a carta do PC de cima (Tamanho 100x140)
    public void viraCartaPc(String naipe, String valor){
        String naipeLower = naipe.toLowerCase();
        String valorLower = valor.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PC_CARD_WIDTH, PC_CARD_HEIGHT);

        if (icone != null) {
            this.cartaPc.setIcon(icone);
            this.cartaPc.setBounds( 550, 100, PC_CARD_WIDTH, PC_CARD_HEIGHT); // Garante a posição
            this.cartaPc.setVisible(true);
        }
    }

    // Vira a carta do PC Parceiro (Tamanho 100x140)
    public void viraCartaPCSide1(String naipe, String valor){
        String naipeLower = naipe.toLowerCase();
        String valorLower = valor.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PC_CARD_WIDTH, PC_CARD_HEIGHT);

        if (icone != null) {
            this.cardParceiro.setIcon(icone);
            this.cardParceiro.setBounds(250, 200, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            this.cardParceiro.setVisible(true);
        }
    }

    // Vira a carta do PC Oponente (Tamanho 100x140)
    public void viraCartaPCSide2(String naipe, String valor){
        String naipeLower = naipe.toLowerCase();
        String valorLower = valor.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PC_CARD_WIDTH, PC_CARD_HEIGHT);

        if (icone != null) {
            this.cardPCLateral.setIcon(icone);
            this.cardPCLateral.setBounds(850, 200, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            this.cardPCLateral.setVisible(true);
        }
    }

    public void atualizarMaoHumano(List<Carta> mao) {
        // ... (Corpo da função mantido, chama setIconePequeno que usa 151x216) ...
        int x = 360;

        for (JLabel c : card) {
            c.setVisible(false);
        }

        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);
            JLabel currentCardLabel = card[i];

            currentCardLabel.setName("carta" + i + "-" + carta.getNaipe().toString().toLowerCase() + "-"
                    + carta.getValor().toString().toLowerCase());

            setIconePequeno(currentCardLabel, carta.getNaipe().toString().toLowerCase(),
                    carta.getValor().toString().toLowerCase(), x);

            currentCardLabel.setVisible(true);

            x += 160;
        }

        this.repaint();
    }


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


    /**
     * Define a manilha no tamanho menor (120x170).
     */
    public void setManilha(String naipe, String valor) {
        String naipeLower = naipe.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valor + " de " + naipeLower + ".jpg";
        ImageIcon icone = createResizedIcon(path, MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);

        if (icone != null) {
            this.manilha.setIcon(icone);
            this.manilha.setBounds(390, 280, MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);
        }
    }

    // ... (Demais Getters e Setters mantidos) ...

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

    public ImageIcon getIconPedirTruco() {
        return iconPedirTruco;
    }

    public void setIconPedirTruco(ImageIcon iconPedirTruco) {
        this.iconPedirTruco = iconPedirTruco;
    }

    public ImageIcon getCardCostasGrande() {
        return cardCostasManilha; // Usando Manilha como "Grande" de referência
    }

    public void setCardCostasGrande(ImageIcon cardCostasGrande) {
        this.cardCostasManilha = cardCostasGrande;
    }

    public ImageIcon getCardCostas() {
        return cardCostasPC; // Usando PC como "Costas" de referência
    }

    public void setCardCostas(ImageIcon cardCostas) {
        this.cardCostasPC = cardCostas;
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

    public JLabel getLblModoRoubo() {
        return lblModoRoubo;
    }

    public void setLblModoRoubo(JLabel lblModoRoubo) {
        this.lblModoRoubo = lblModoRoubo;
    }

    public void setCard(JLabel[] card) {
        this.card = card;
    }

    public void setNomeJogador(JLabel nomeJogador) {
        this.lblNomeJogador = nomeJogador;
    }

    public String getNomeJogador() {
        return lblNomeJogador.getText();
    }

    public void atualizaPlacar(int pontosTime1, int pontosTime2) {
        placar.setText("Mão: " + pontosTime1 + " X " + pontosTime2);
        this.placar.repaint();
    }

    public void atualizaPlacarSet(int pontosSetTime1, int pontosSetTime2) {
        placarSet.setText("Set: " + pontosSetTime1 + " X " + pontosSetTime2);
        placarSet.repaint();
    }
}