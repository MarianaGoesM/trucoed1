package view.menu;

import java.awt.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PainelMenu extends JPanel{

    private JLabel novo;
    private JLabel placar;
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

    public PainelMenu() {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);

        this.fontePrincipal = loadCustomFont("Milkyway DEMO.ttf", Font.BOLD, 12f);

        this.criarMenu();


        this.setName("Truco");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image background = new ImageIcon(this.getClass().getResource("/resource/img/cenario/telaInicial.jpg")).getImage();
        g.drawImage(background, 0, 0, this);

    }

    public void criarMenu() {

        this.novo = new JLabel("Novo Jogo");
        this.novo.setName("novo");
        this.novo.setFont(fontePrincipal.deriveFont(Font.PLAIN, 30f));
        this.novo.setForeground(Color.PINK);
        this.add(novo);
        this.novo.setBounds(440, 435, 140, 46);

    }

    public JLabel getNovo() {
        return novo;
    }

    public void setNovo(JLabel novo) {
        this.novo = novo;
    }

    public JLabel getPlacar() {
        return placar;
    }

    public void setPlacar(JLabel placar) {
        this.placar = placar;
    }
}