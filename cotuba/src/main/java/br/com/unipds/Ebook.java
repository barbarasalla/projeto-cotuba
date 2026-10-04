package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

public record Ebook(String title, String author, FormatEbookEnum format, List<Chapter> chapters, Path outputFile) {
}