package controller;

import java.util.concurrent.Semaphore;

public class ThreadJogada extends Thread{

	private Semaphore semaforo;
	
	public ThreadJogada(Semaphore semaforo) {
		this.semaforo = semaforo;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			semaforo.acquire();
			jogada();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
	}

	private void jogada() {
		
	}
	
}