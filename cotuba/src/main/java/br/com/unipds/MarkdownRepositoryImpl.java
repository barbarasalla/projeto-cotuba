package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class MarkdownRepositoryImpl implements MarkdownRepository {

    public List<Markdown> search(Path inputDirMk) {
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
                            String markdownContent = Files.readString(arquivoMD);
                            return new Markdown(arquivoMD, markdownContent);
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
