package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

import nl.siegmann.epublib.domain.*;

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
            leitorDeOpcoes.read(args);


            Path diretorioDosMD = leitorDeOpcoes.getDiretorioDosMD();
            String formato = leitorDeOpcoes.getFormato();
            Path arquivoDeSaida = leitorDeOpcoes.getArquivoDeSaida();
            modoVerboso = leitorDeOpcoes.isModoVerboso();

            var rendererMK = new RendererMK();
            List<String> capitulosEmHTML = rendererMK.render(diretorioDosMD);

            if ("pdf".equals(formato)) {
                var generatorPDF = new GeneratorPDF();
                generatorPDF.generate(capitulosEmHTML, arquivoDeSaida);
            } else if ("epub".equals(formato)) {
                var generatorEPUB = new GenerateEPUB();
                generatorEPUB.generate(capitulosEmHTML, arquivoDeSaida);
            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
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