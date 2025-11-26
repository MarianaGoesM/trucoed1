package view.jogo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Carta;
import model.Jogador;

public class PainelJogo extends JPanel {

    private static final int PLAYER_CARD_WIDTH = 120;
    private static final int PLAYER_CARD_HEIGHT = 170;

    private static final int PC_CARD_WIDTH = 100;
    private static final int PC_CARD_HEIGHT = 140;

    private static final int MANILHA_CARD_WIDTH = 100;
    private static final int MANILHA_CARD_HEIGHT = 140;

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

    protected ImageIcon cardCostasManilha;
    protected ImageIcon cardCostasPC;
    private JLabel lblOrdenarCartas;

    private Font fontePrincipal;

    private Font loadCustomFont(String fontFileName, int style, float size) {
        try {
            String path = "/resource/fonts/" + fontFileName;
            InputStream is = this.getClass().getResourceAsStream(path);

            if (is == null) {
                System.err.println("ERRO: Arquivo de fonte não encontrado: " + path);
                return new Font("Arial", style, (int) size);
            }

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return customFont.deriveFont(style, size);

        } catch (IOException | FontFormatException e) {
            System.err.println("Erro ao carregar ou formatar a fonte " + fontFileName);
            e.printStackTrace();
            return new Font("Arial", style, (int) size);
        }
    }

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

    public PainelJogo() {
        this.cardCostasPC = createResizedIcon("/resource/img/cenario/carta-costas.jpg", PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.cardCostasManilha = createResizedIcon("/resource/img/cenario/carta-costas.jpg", MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);

        if (this.cardCostasPC == null) {
            this.cardCostasPC = new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png"));
        }
        if (this.cardCostasManilha == null) {
            this.cardCostasManilha = new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png"));
        }

        // Carrega a fonte personalizada
        this.fontePrincipal = loadCustomFont("Milkyway DEMO.ttf", Font.BOLD, 12f);

        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        this.card = new JLabel[3];
        for (int i = 0; i < this.card.length; i++) {
            this.card[i] = new JLabel();
        }

        this.manilha = new JLabel();
        this.manilha.setName("manilha");
        this.manilha.setBounds(550, 300, MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);
        this.manilha.setIcon(cardCostasManilha);
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
        this.cardParceiro.setBounds(250, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.add(cardParceiro);

        this.cardPCLateral = new JLabel();
        cardPCLateral.setIcon(cardCostasPC);
        this.cardPCLateral.setBounds(850, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        this.add(cardPCLateral);


        lblNomeJogador = new JLabel();
        lblNomeJogador.setFont(fontePrincipal.deriveFont(Font.BOLD, 20f));
        lblNomeJogador.setBounds(500, 700, 200, 48);
        lblNomeJogador.setForeground(Color.WHITE);
        this.add(lblNomeJogador);

        lblNomePCParceiro = new JLabel("Parceiro");
        lblNomePCParceiro.setFont(fontePrincipal.deriveFont(Font.BOLD, 14f));
        lblNomePCParceiro.setBounds(250, 150, 100, 20);
        lblNomePCParceiro.setForeground(Color.WHITE);
        this.add(lblNomePCParceiro);

        lblNomePCLateral = new JLabel("Oponente");
        lblNomePCLateral.setFont(fontePrincipal.deriveFont(Font.BOLD, 14f));
        lblNomePCLateral.setBounds(850, 150, 100, 20);
        lblNomePCLateral.setForeground(Color.WHITE);
        this.add(lblNomePCLateral);

        placar = new JLabel();
        placar.setBounds(30, 50, 300, 50);
        placar.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 42));
        placar.setForeground(Color.WHITE);
        this.add(placar);

        placarSet = new JLabel("Set: 0 X 0");
        placarSet.setBounds(30, 95, 300, 40);
        placarSet.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 20));
        placarSet.setForeground(Color.WHITE);
        this.add(placarSet);

        lblPedirTruco = new JLabel("TRUCO");
        lblPedirTruco.setFont(fontePrincipal.deriveFont(Font.PLAIN, 50f));
        lblPedirTruco.setBounds(990, 555, 188, 104);
        lblPedirTruco.setForeground(Color.WHITE);
        this.add(lblPedirTruco);

        lblModoRoubo = new JLabel("ROUBO");
        lblModoRoubo.setFont(fontePrincipal.deriveFont(Font.PLAIN, 50f));
        lblModoRoubo.setBounds(990, 655, 188, 104);
        lblModoRoubo.setForeground(Color.WHITE);
        this.add(lblModoRoubo);

        lblOrdenarCartas = new JLabel("↑");
        lblOrdenarCartas.setFont(new Font("Rosewood Std Regular", Font.PLAIN, 50));
        lblOrdenarCartas.setBounds(257, 647, 70, 104);
        lblOrdenarCartas.setForeground(Color.WHITE);
        this.add(lblOrdenarCartas);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image background = new ImageIcon(getClass().getResource("/resource/img/cenario/fundo-mesa.jpg"))
                .getImage();
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    public void criarTela(List<Jogador<Carta>> jogadores) {
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
        String valorLower = valor.toLowerCase();
        String naipeLower = naipe.toLowerCase();
        System.out.println(naipeLower + " " + valorLower + " de " + naipeLower + ".jpg");

        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);

        if(icone == null){
            icone = cardCostasPC;
        }

        card.setBounds(x, 600, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
        card.setIcon(icone);
    }


    public void setIconeGrande(JLabel card, String naipe, String valor) {
        String naipeLower = naipe.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valor + " de " + naipeLower + ".jpg";
        ImageIcon icone = createResizedIcon(path, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);

        if (icone != null) {
            card.setBounds(card.getX(), card.getY() - 50, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            card.setIcon(icone);
        }
    }

    public void setIconePequeno(JLabel card) {
        if (card.getName() != null && card.getName().contains("carta")) {
            ImageIcon playerCostas = createResizedIcon("/resource/img/cenario/carta-costas.png", PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            card.setIcon(playerCostas != null ? playerCostas : cardCostasPC);

            if (card.getName().contains("carta0"))
                card.setBounds(360, 300, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            else if (card.getName().contains("carta1"))
                card.setBounds(520, 300, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
            else if (card.getName().contains("carta2"))
                card.setBounds(680, 300, PLAYER_CARD_WIDTH, PLAYER_CARD_HEIGHT);
        } else {
            card.setIcon(cardCostasPC);
            if (card.getName().equals("card1"))
                card.setBounds(290, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            else if (card.getName().equals("card2"))
                card.setBounds(426, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            else if (card.getName().equals("card3"))
                card.setBounds(576, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        }
    }

    public void limparMesa() {
        cartaMesa.setVisible(false);
        cartaPc.setIcon(cardCostasPC);
        cardParceiro.setIcon(cardCostasPC);
        cardPCLateral.setIcon(cardCostasPC);
        this.repaint();
    }

    public void moverCardParaMesa(JLabel card) {
        this.setIconePequeno(card);
        card.setBounds(550, 420, PC_CARD_WIDTH, PC_CARD_HEIGHT);
        if (card.getMouseListeners().length > 0) {
            card.removeMouseListener(card.getMouseListeners()[0]);
        }
    }

    public void moverCardParaMesa(JLabel card, String naipe, String valor) {
        String valorLower = valor.toLowerCase();
        String naipeLower = naipe.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";
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

    public void viraCartaPc(String naipe, String valor){
        String naipeLower = naipe.toLowerCase();
        String valorLower = valor.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PC_CARD_WIDTH, PC_CARD_HEIGHT);

        if (icone != null) {
            this.cartaPc.setIcon(icone);
            this.cartaPc.setBounds( 550, 100, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            this.cartaPc.setVisible(true);
        }
    }

    public void viraCartaPCSide1(String naipe, String valor){
        String naipeLower = naipe.toLowerCase();
        String valorLower = valor.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PC_CARD_WIDTH, PC_CARD_HEIGHT);

        if (icone != null) {
            this.cardParceiro.setIcon(icone);
            this.cardParceiro.setBounds(250, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            this.cardParceiro.setVisible(true);
        }
    }

    public void viraCartaPCSide2(String naipe, String valor){
        String naipeLower = naipe.toLowerCase();
        String valorLower = valor.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";

        ImageIcon icone = createResizedIcon(path, PC_CARD_WIDTH, PC_CARD_HEIGHT);

        if (icone != null) {
            this.cardPCLateral.setIcon(icone);
            this.cardPCLateral.setBounds(850, 300, PC_CARD_WIDTH, PC_CARD_HEIGHT);
            this.cardPCLateral.setVisible(true);
        }
    }

    public void atualizarMaoHumano(List<Carta> mao) {
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


    public void setManilha(String naipe, String valor) {
        String valorLower = valor.toLowerCase();
        String naipeLower = naipe.toLowerCase();
        String path = "/resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg";
        System.out.println("manilha = /resource/img/baralho/" + naipeLower + "/" + valorLower + " de " + naipeLower + ".jpg");
        ImageIcon icone = createResizedIcon(path, MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);

        if (icone != null) {
            this.manilha.setIcon(icone);
            this.manilha.setBounds(440, 300, MANILHA_CARD_WIDTH, MANILHA_CARD_HEIGHT);
        }
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

    public ImageIcon getIconPedirTruco() {
        return iconPedirTruco;
    }

    public void setIconPedirTruco(ImageIcon iconPedirTruco) {
        this.iconPedirTruco = iconPedirTruco;
    }

    public ImageIcon getCardCostasGrande() {
        return cardCostasManilha;
    }

    public void setCardCostasGrande(ImageIcon cardCostasGrande) {
        this.cardCostasManilha = cardCostasGrande;
    }

    public ImageIcon getCardCostas() {
        return cardCostasPC;
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

    public JLabel getLblOrdenarCartas() {
        return lblOrdenarCartas;
    }
}