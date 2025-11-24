package controller;

import java.util.List;
import model.Carta;
import model.CartaJogada;
import model.Jogador;
import model.Turno;
import util.GerenciadorTxt;

public class ControlTurno {
    private Turno turno;

    public void novaCartaJogada(Jogador j, Carta c){

        // 🚨 CORREÇÃO PRINCIPAL: Verificação para garantir que o turno foi iniciado.
        // Se o turno for null, isso significa que criaTurno() não foi chamado no início da rodada.
        if (this.turno == null) {
            System.err.println("ERRO LÓGICO: O Turno não foi inicializado! Garanta que ControlPartida.iniciarTurno() foi chamado.");
            return; // Sai para evitar a NullPointerException
        }

        // 1. REGISTRAR NO LOG
        // Mantive o try-catch apenas para proteger contra erros de dados (getters null),
        // mas é melhor ter uma classe de log que lide com exceções I/O.
        try {
            String linhaLog = String.format("JOGADA: Jogador %s (Time %d) jogou: %s de %s",
                    j.getNome(),
                    j.getTime(),
                    c.getValor().toString(),
                    c.getNaipe().toString()
            );
            GerenciadorTxt.registrarJogada(linhaLog);
        } catch (Exception e) {
            System.err.println("Erro ao registrar jogada no log: " + e.getMessage());
        }

        // 2. Adicionar ao Turno (Chama addCartaJogada, que agora está seguro)
        addCartaJogada(new CartaJogada(c, j));
    }


    public void jogaCarta(Carta c, List<Jogador<Carta>> j){
        System.out.println(j.getClass().toString());

    }

    public void addCartaJogada(CartaJogada cj){
        // Esta linha é segura porque verificamos 'this.turno' em novaCartaJogada.
        this.turno.addCarta(cj);
    }

    public void criaTurno(){
        setTurno(new Turno());
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}