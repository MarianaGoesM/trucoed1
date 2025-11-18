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

    public T pop() {
        if (isEmpty()){
            throw new RuntimeException("Pilha vazia");
        }
        T elemento = elementos[topo];
        elementos[topo--] = null;
        return elemento;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == elementos.length;
    }

    public int size() {
        return topo + 1;
    }

}

