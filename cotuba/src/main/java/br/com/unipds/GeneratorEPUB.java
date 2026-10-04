package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.GuideReference;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
@FormatEbookQualifier(FormatEbookEnum.EPUB)
public class GeneratorEPUB implements GeneratorEbook{

    @Override
    public void generate(Ebook ebook) {
        List<Chapter> chapters = ebook.chapters();
        Path outputFile = ebook.outputFile();

        try {
            var epub = new Book();

            epub.getMetadata().addTitle(ebook.title());
            epub.getMetadata().addAuthor(new Author(ebook.author()));

            boolean[] ehPrimeiroCapitulo = {true};

            chapters.forEach(chapter -> {
                String contentHTML = chapter.getContentHTML();
                String title = chapter.getTitle();


                String epubHtml = """
                        <html xmlns="http://www.w3.org/1999/xhtml">
                        <head>
                            <title>%s</title>
                        </head>
                        <body>
                        %s
                        </body>
                        </html>
                        """.formatted(title, contentHTML);

                var c = new Resource(epubHtml.getBytes(), MediatypeService.XHTML);
                epub.addSection(title, c);

                if (ehPrimeiroCapitulo[0]) {
                    epub.getGuide().addReference(new GuideReference(c, "text", "Start Reading"));
                    ehPrimeiroCapitulo[0] = false;
                }
            });

            var epubWriter = new EpubWriter();

            try {
                epubWriter.write(epub, Files.newOutputStream(outputFile));
            } catch (IOException ex) {
                throw new IllegalStateException("Erro ao criar arquivo EPUB: " + outputFile.toAbsolutePath(), ex);
            }

        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao gerar EPUB: " + outputFile.toAbsolutePath(), ex);
        }
    }
}
