package util; // Use o pacote onde você quer guardar utilidades

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class GerenciadorTxt {

    // Nome do arquivo onde as jogadas serão salvas.
    private static final String NOME_ARQUIVO = "registro_jogadas.txt";

    /**
     * Adiciona uma linha de texto ao arquivo de log (no final).
     * @param mensagem A string contendo a jogada e quem jogou.
     */
    public static void registrarJogada(String mensagem) {
        // O 'true' no FileWriter(NOME_ARQUIVO, true) garante que o texto será ADICIONADO
        // ao final do arquivo, em vez de sobrescrever o conteúdo.
        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(mensagem);

        } catch (IOException e) {
            // Em caso de erro (ex: permissão negada), apenas exibe no console.
            System.err.println("ERRO ao escrever no log: " + e.getMessage());
        }
    }

    /**
     * Inicia um novo log, limpando o arquivo antigo e adicionando um cabeçalho.
     */
    public static void iniciarNovoLog() {
        // O 'false' no construtor de FileWriter sobrescreve o arquivo.
        try (FileWriter fileWriter = new FileWriter(NOME_ARQUIVO, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("--- NOVO JOGO INICIADO: " + java.time.LocalDateTime.now() + " ---");

        } catch (IOException e) {
            System.err.println("ERRO ao iniciar novo log: " + e.getMessage());
        }
    }
}