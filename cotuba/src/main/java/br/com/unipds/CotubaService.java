package br.com.unipds;

import java.util.List;

public class CotubaService {

    public void execute(CotubaParams cotubaParams) {
        var rendererMK = new RendererMK();
        List<Chapter> capitulos = rendererMK.render(cotubaParams.getDiretorioDosMD());

        Ebook ebook = new Ebook();

        LeitorPropriedadesEbook leitorPropriedadesEbook = new LeitorPropriedadesEbook();
        leitorPropriedadesEbook.ler(cotubaParams.getDiretorioDosMD(), ebook);

        ebook.setChapters(capitulos);
        ebook.setFormat(cotubaParams.getFormato());
        ebook.setOutputFile(cotubaParams.getArquivoDeSaida());

        if (FormatEbookEnum.PDF.equals(ebook.getFormat())) {
            var generatorPDF = new GeneratorPDF();
            generatorPDF.generate(ebook);
        } else if (FormatEbookEnum.EPUB.equals(ebook.getFormat())) {
            var generatorEPUB = new GenerateEPUB();
            generatorEPUB.generate(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + cotubaParams.getFormato());
        }
    }
}
