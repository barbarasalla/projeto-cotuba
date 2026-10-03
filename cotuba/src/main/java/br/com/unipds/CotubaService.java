package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
public class CotubaService {

    private final RendererMK rendererMK;
    private final LeitorPropriedadesEbook leitorPropriedadesEbook;
    private final MarkdownRepository markdownRepository;
    private final Instance<GeneratorEbook> generatorEbooks; // Injeção de dependência para todos os beans que implementam GeneratorEbook

    @Inject
    public CotubaService(RendererMK rendererMK, LeitorPropriedadesEbook leitorPropriedadesEbook, MarkdownRepository markdownRepository, @Any Instance<GeneratorEbook> generatorEbooks) {
        this.rendererMK = rendererMK;
        this.leitorPropriedadesEbook = leitorPropriedadesEbook;
        this.markdownRepository = markdownRepository;
        this.generatorEbooks = generatorEbooks;
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

        GeneratorEbook generatorEbook = generatorEbooks.select(FormatEbookFilter.of(ebook.getFormat())).get(); // Seleciona o bean correto com base no formato do ebook
        generatorEbook.generate(ebook);
    }
}
