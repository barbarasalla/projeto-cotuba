package br.com.unipds;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {

    void main(String[] args) {
        int exitCode = executar(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    int executar(String[] args) {
        boolean modoVerboso = true;

        try(SeContainer container = SeContainerInitializer.newInstance().initialize()) { // Inicializa o CDI
            var leitorDeOpcoes = new ReadOptionsCLI();
            CotubaParams cotubaParams = leitorDeOpcoes.read(args);

            modoVerboso = cotubaParams.modoVerboso();

            CotubaService cotubaService = container.select(CotubaService.class).get(); // Obtém a instância do CotubaService gerenciada pelo CDI
            cotubaService.execute(cotubaParams);

            System.out.println("Arquivo gerado com sucesso: " + cotubaParams.arquivoDeSaida());
            return 0;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                System.err.println();
                ex.printStackTrace();
            }
            return 1;
        }
    }

}