package br.com.unipds;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

public class MarkdownRepository {

    public List<Chapter> search(Path inputDirMk) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
        try (Stream<Path> streamMDs = Files.list(inputDirMk)) {
            List<Path> arquivosMD = streamMDs
                    .filter(matcher::matches)
                    .sorted()
                    .toList();

            if (arquivosMD.isEmpty()) {
                throw new IllegalStateException("Não foram encontrados capítulos (arquivos .md) no diretório: " + inputDirMk.toAbsolutePath());
            }

            return arquivosMD.stream().map(
                    arquivoMD -> {
                        try {
                            Chapter chapter = new Chapter();
                            chapter.setArchivePath(arquivoMD);

                            String markdown = null;

                            markdown = Files.readString(arquivoMD);
                            chapter.setContentMarkdown(markdown);
                            return chapter;
                        } catch (IOException e) {
                            throw new IllegalStateException("Erro ao ler o arquivo .md: " + arquivoMD, e);
                        }
                    }
            ).toList();

        } catch (IOException ex) {
            throw new IllegalStateException("Erro tentando encontrar arquivos .md em " + inputDirMk.toAbsolutePath(), ex);
        }
    }
}
