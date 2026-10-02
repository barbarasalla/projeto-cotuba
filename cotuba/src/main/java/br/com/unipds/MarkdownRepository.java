package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

public interface MarkdownRepository {
    List<Chapter> search(Path inputDirMk);
}
