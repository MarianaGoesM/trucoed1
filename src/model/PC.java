package model;

public class PC extends Jogador{
	
	private static int id;
	
	public PC(String nome) {
		super(nome);
	}

	@Override
	public Carta jogarCarta() {
		return null;
	}
	
	public void decidirAcao(){
		
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		PC.id = id;
	}
}