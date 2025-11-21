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

    /**
     * Retorna a força base da carta na ordem do Truco (1 a 10).
     * Este é o método que o ControlPartida deve chamar.
     */
    public int getPesoTruco() {
        return pesoTruco;
    }

    // Você pode manter este método se outras partes do seu código o usam para obter o valor.
    // Ele retorna o mesmo valor de getPesoTruco().
    public int getValor() {
        return pesoTruco;
    }


    /**
     * Retorna o Valor (do Enum) que se torna a manilha de vira,
     * ou seja, o próximo valor na ordem de força do Truco.
     */
    public Valor getProximoValor() {
        Valor[] valores = Valor.values();

        // Verifica se o valor atual é o último da lista de enums (o TRES)
        if (this.ordinal() == valores.length - 1) {
            // Se for o TRES, o próximo é o primeiro valor (o QUATRO).
            return valores[0];
        }

        // Senão, retorna o próximo valor na ordem de declaração (que segue a ordem de força)
        return valores[this.ordinal() + 1];
    }
}