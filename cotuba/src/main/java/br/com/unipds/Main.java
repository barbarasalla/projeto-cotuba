package br.com.unipds;

public class Main {

    void main(String[] args) {
        int exitCode = executar(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    int executar(String[] args) {
        boolean modoVerboso = true;
        try {
            var leitorDeOpcoes = new ReadOptionsCLI();
            CotubaParams cotubaParams = leitorDeOpcoes.read(args);

            modoVerboso = cotubaParams.isModoVerboso();

            CotubaService cotubaService = new CotubaService();
            cotubaService.execute(cotubaParams);

            System.out.println("Arquivo gerado com sucesso: " + cotubaParams.getArquivoDeSaida());
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