package model;

public class Pilha <T> {
    private T[] elementos;
    private int topo;

    @SuppressWarnings("unchecked")
    public Pilha(int capacidade) {
        elementos = (T[]) new Object[capacidade];
        topo = -1;
    }

    public void push(T elemento) {
        if(isFull()){
            throw new RuntimeException("Pilha cheia");
        }
        elementos[++topo] = elemento;
    }

    // Pilha.java (pop)
    public T pop() {
        if (isEmpty()){
            throw new RuntimeException("Pilha vazia"); // Lançada na 2ª puxada
        }
        T elemento = elementos[topo];
        elementos[topo] = null; // Zera a posição
        topo = topo-1; // SÓ DEPOIS decrementa o índice
        return elemento;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        // Se topo (que é o índice) for igual a capacidade-1, a pilha está cheia.
        return topo == elementos.length - 1;
    }

    public int size() {
        return topo + 1;
    }

    public T peek() {
        return elementos[topo];
    }
}

