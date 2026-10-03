package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@FormatEbookQualifier(FormatEbookEnum.HTML)
public class GeneratorHTML implements GeneratorEbook{
    @Override
    public void generate(Ebook ebook) {
        Path outputDir = ebook.getOutputFile();
        try {
            if(!Files.exists(outputDir)){
                Path dirHTML = Files.createDirectory(outputDir);
            }

            int chapterNumber = 1;
            Map<Chapter, Path> htmlChapterFiles = new LinkedHashMap<>();
            for(Chapter chapter : ebook.getChapters()) {
                String nameFileHtml = getNameChapter(chapter, chapterNumber);
                Path arquivoHtml = outputDir.resolve(nameFileHtml);
                htmlChapterFiles.put(chapter, arquivoHtml);
                toWriteFileHtml(chapter, arquivoHtml);
                chapterNumber++;
            }

            toWriteSummaryFile(ebook, outputDir, htmlChapterFiles);

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao criar diretório: " + e.getMessage(), e);
        }
    }

    private void toWriteSummaryFile(Ebook ebook, Path outputDir, Map<Chapter, Path> htmlChapterFiles) {

        String itensSummaryHtml = ebook.getChapters().stream().map(chapter -> {
            return """
                    <li>
                        <a href="%s">%s</a>
                    </li>
                    """
                    .formatted(htmlChapterFiles.get(chapter).getFileName().toString(), chapter.getTitle());
        }).collect(Collectors.joining());

        String summaryHtml = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                </head>
                <body>
                    <h1>%s</h1>
                    <h2>Por: %s</h2>
                    <h3>Sumário</h3>
                    <ul>
                       %s
                    </ul>
                </body>
                </html>
                """.formatted(ebook.getTitle(), ebook.getTitle(), ebook.getAuthor(), itensSummaryHtml);

        Path index = outputDir.resolve("index.html");
        try {
            Files.writeString(index, summaryHtml, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void toWriteFileHtml(Chapter chapter, Path arquivoHtml) {
        String contentHTML = chapter.getContentHTML();
        String title = chapter.getTitle();

        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                </head>
                <body>
                %s
                </body>
                </html>
                """.formatted(title, contentHTML);

        try {
            Files.writeString(arquivoHtml, html, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao escrever ebook HTML: " + arquivoHtml.toAbsolutePath(), e);
        }
    }

    private String getNameChapter(Chapter chapter, int chapterNumber) {
        String titleClear = chapter.getTitle().toLowerCase().replaceAll("\\W", "");
        return "%02d-%s.html".formatted(chapterNumber, titleClear);
    }
}
