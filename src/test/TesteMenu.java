package test;

import javax.swing.JFrame;

import view.menu.Menu;

public class TesteMenu{

    public static void main(String[] args) {
        Menu menuPrincipal = new Menu();
        menuPrincipal.setSize(1002,662);
        menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipal.setVisible(true);
        menuPrincipal.setResizable(false);
    }
}
