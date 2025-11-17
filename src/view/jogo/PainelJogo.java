package view.jogo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

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
	private JLabel cartaPc;
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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Image background = new ImageIcon(getClass().getResource("/resource/img/cenario/fundo-mesa-menor.jpg"))
				.getImage();
		g.drawImage(background, 0, 0, this);

	}

	public void criarTela(Jogador j) {
		// cria as cartas e adiciona no jogo, dps � s� fazer a l�gica e ir
		// mudando o icone e retirando as cartas
		// seria melhor jogar isso dentro de uma metodo que adiciona a carta do
		// jogo e um que remove
		this.manilha = new JLabel();
		this.manilha.setName("manilha");
		this.manilha.setBounds(399, 247, 73, 100);
		this.manilha.setIcon(new ImageIcon(this.getClass().getResource("/resource/img/cenario/carta-costas.png")));
		this.add(manilha);

		this.cartaMesa = new JLabel();
		this.cartaMesa.setBounds(424, 356, 73, 100);
		
		this.cartaPc = new JLabel();
		cartaPc.setIcon(cardCostas);
		this.cartaPc.setBounds( 424, 138, 73, 100);
		
		this.cartaPc.setVisible(true);
		this.cartaMesa.setVisible(false);
		this.add(cartaMesa);
		this.add(cartaPc);
		
		this.card = new JLabel[3];
		int x = 256;

		for (int i = 0; i < card.length; i++) {
			card[i] = new JLabel();
			Carta carta = (Carta) j.getMao().get(i);

			card[i].setName("carta" + i + "-" + carta.getNaipe().toString().toLowerCase() + "-"
					+ carta.getValor().toString().toLowerCase());

			setIconePequeno(card[i], carta.getNaipe().toString().toLowerCase(),
					carta.getValor().toString().toLowerCase(), x);

			configLabel(card[i]);

			x += 175;

		}

		lblNomeJogador = new JLabel();
		lblNomeJogador.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
		lblNomeJogador.setBounds(33, 492, 812, 48);
		this.add(lblNomeJogador);

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
		/*
		 * if (card.getName().equals("card1")) card.setBounds(229, 362, 150,
		 * 200); else if (card.getName().equals("card2")) card.setBounds(389,
		 * 362, 150, 200); else if (card.getName().equals("card3"))
		 * card.setBounds(523, 362, 150, 200); card.setIcon(cardCostasGrande);
		 */
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

	public void atualizaPlacar(int pontosJogador, int pontosPC) {
		placar.setText("Placar: " + lblNomeJogador.getText() + " " + pontosJogador + " X " + pontosPC + " PC");
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

}
