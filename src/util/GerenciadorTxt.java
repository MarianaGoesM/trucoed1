package util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class GerenciadorTxt {

    private static final String NOME_ARQUIVO = "registro_jogadas.txt";

    public static void registrarJogada(String mensagem) {
        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(mensagem);

        } catch (IOException e) {
            System.err.println("ERRO ao escrever no log: " + e.getMessage());
        }
    }

    public static void iniciarNovoLog() {
        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("--- NOVO JOGO INICIADO: " + java.time.LocalDateTime.now() + " ---");

        } catch (IOException e) {
            System.err.println("ERRO ao iniciar novo log: " + e.getMessage());
        }
    }
}