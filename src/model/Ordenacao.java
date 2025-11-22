package model;

import java.util.Comparator;
import enumerated.Naipe;
import enumerated.Valor;


public class Ordenacao implements Comparator<Carta> {

    // A carta "vira" da rodada é essencial para determinar a força das manilhas.
    private final Carta vira;

    public Ordenacao(Carta vira) {
        this.vira = vira;
    }

    private Valor getValorManilha(Valor viraValor) {
        // Assume que o método getProximoValor() existe na enum Valor.
        return viraValor.getProximoValor();
    }


    private int getCardTrucoPower(Carta c) {

        // 1. Força Padrão (Base: 1 a 10)
        // Usa o pesoTruco definido no enum Valor (QUATRO=1, ..., TRES=10).
        int power = c.getValor().getPesoTruco();

        // 2. Lógica para definir a MANILHA (Manilha é sempre mais forte que qualquer carta base)
        if (vira != null) {

            // Determina qual é o valor que se tornou manilha (ex: se Vira=7, Manilha=Q)
            Valor valorManilha = getValorManilha(this.vira.getValor());

            if (c.getValor() == valorManilha) {

                // Se for Manilha, ganha um bônus alto (1000) para garantir que seja a mais forte.
                power += 1000;


                if (c.getNaipe() == Naipe.ZAP) { // Manilha ZAP (correspondente ao Paus/Clubs no truco)
                    power += 4;
                } else if (c.getNaipe() == Naipe.ESPADILHA) { // Manilha ESPADILHA (correspondente ao Espadas/Spades)
                    power += 3;
                } else if (c.getNaipe() == Naipe.COPAS) { // Manilha COPAS
                    power += 2;
                } else if (c.getNaipe() == Naipe.PICAFUMO) { // Manilha PICAFUMO (correspondente ao Ouros/Diamonds)
                    power += 1;
                }
            }
        }

        return power;
    }

    @Override
    public int compare(Carta c1, Carta c2) {
        int power1 = getCardTrucoPower(c1);
        int power2 = getCardTrucoPower(c2);

        return power1 - power2;
    }
}