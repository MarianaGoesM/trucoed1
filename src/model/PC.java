package model;

public class PC extends Jogador<Carta>{
	
	private static int id;

    public PC(String nome, int time) {
        super(nome, time);
        PC.id++;
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