package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
public class CotubaService {

    private final RendererMK rendererMK;
    private final LeitorPropriedadesEbook leitorPropriedadesEbook;
    private final MarkdownRepository markdownRepository;
    private final GeneratorEbook generatorPDF;
    private final GeneratorEbook generatorEPUB;

    @Inject
    public CotubaService(RendererMK rendererMK, LeitorPropriedadesEbook leitorPropriedadesEbook, MarkdownRepository markdownRepository, @Named("GeradorPDF") GeneratorEbook generatorPDF, @Named("GeradorEPUB") GeneratorEbook generatorEPUB) {
        this.rendererMK = rendererMK;
        this.leitorPropriedadesEbook = leitorPropriedadesEbook;
        this.markdownRepository = markdownRepository;
        this.generatorPDF = generatorPDF;
        this.generatorEPUB = generatorEPUB;
    }

    public void execute(CotubaParams cotubaParams) {

        Path diretorioDosMD = cotubaParams.getDiretorioDosMD();
        List<Chapter> capitulos = markdownRepository.search(diretorioDosMD);
        rendererMK.render(capitulos);

        Ebook ebook = new Ebook();

        leitorPropriedadesEbook.ler(cotubaParams.getDiretorioDosMD(), ebook);

        ebook.setChapters(capitulos);
        ebook.setFormat(cotubaParams.getFormato());
        ebook.setOutputFile(cotubaParams.getArquivoDeSaida());

        GeneratorEbook generatorEbook;
        if (FormatEbookEnum.PDF.equals(ebook.getFormat())) {
            generatorEbook = generatorPDF;
        } else if (FormatEbookEnum.EPUB.equals(ebook.getFormat())) {
            generatorEbook = generatorEPUB;
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + cotubaParams.getFormato());
        }
        generatorEbook.generate(ebook);
    }
}
