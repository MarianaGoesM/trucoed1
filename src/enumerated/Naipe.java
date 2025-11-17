package enumerated;

public enum Naipe {
	PICAFUMO(1),
	ESPADILHA(2),
	COPAS(3),
	ZAP(4);

	private int valor;

	Naipe(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

}
