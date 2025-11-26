package model;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import enumerated.Naipe;
import enumerated.Valor;


public class Ordenacao implements Comparator<Carta> {
    private final Carta vira;

    public Ordenacao(Carta vira) {
        this.vira = vira;
    }

    private Valor getValorManilha(Valor viraValor) {
        return viraValor.getProximoValor();
    }


    public static void bubbleSort(List<Carta> cartas, Ordenacao instanciaOrdenacao) {
        int n = cartas.size();
        boolean trocou;

        for (int i = 0; i < n - 1; i++) {
            trocou = false;

            for (int j = 0; j < n - 1 - i; j++) {

                int power1 = instanciaOrdenacao.getCardTrucoPower(cartas.get(j));
                int power2 = instanciaOrdenacao.getCardTrucoPower(cartas.get(j + 1));

                if (power1 > power2) {

                    Collections.swap(cartas, j, j + 1);
                    trocou = true;
                }
            }

            if (!trocou) {
                break;
            }
        }
    }

    private int getCardTrucoPower(Carta c) {

        int power = c.getValor().getPesoTruco();

        if (vira != null) {

            Valor valorManilha = getValorManilha(this.vira.getValor());

            if (c.getValor() == valorManilha) {
                power += 1000;
                if (c.getNaipe() == Naipe.ZAP) {
                    power += 4;
                } else if (c.getNaipe() == Naipe.ESPADILHA) {
                    power += 3;
                } else if (c.getNaipe() == Naipe.COPAS) {
                    power += 2;
                } else if (c.getNaipe() == Naipe.OURO) {
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