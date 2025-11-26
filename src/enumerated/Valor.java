package enumerated;

public enum Valor {
    // Definindo a força base da carta na ordem do Truco: 1 (mais fraca) a 10 (mais forte, não-manilha).

    QUATRO(1),
    CINCO(2),
    SEIS(3),
    SETE(4),
    Q(5),      // Dama
    J(6),      // Valete
    K(7),      // Rei
    A(8),      // Ás
    DOIS(9),
    TRES(10);  // 3 é a carta comum mais forte

    private final int pesoTruco;

    Valor(int pesoTruco) {
        this.pesoTruco = pesoTruco;
    }


    public int getPesoTruco() {
        return pesoTruco;
    }


    public int getValor() {
        return pesoTruco;
    }


    public Valor getProximoValor() {
        Valor[] valores = Valor.values();

        if (this.ordinal() == valores.length - 1) {

            return valores[0];
        }

        return valores[this.ordinal() + 1];
    }
}