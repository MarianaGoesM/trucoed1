package enumerated;

public enum Valor {
	QUATRO(1), 
	CINCO(2),
	SEIS(3), 
	SETE(4),
	Q(5),
	J(6),
	K(7),
	A(8),
	DOIS(9),
	TRES(10);

	private int valor;

	Valor(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
