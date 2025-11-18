package model;

	public class CriadorPC extends FactoryJogador<PC>{

        @Override
        public PC novo(String nome, int time) {
            return new PC("Computador" + (PC.getId()), time);
        }
    }
	

