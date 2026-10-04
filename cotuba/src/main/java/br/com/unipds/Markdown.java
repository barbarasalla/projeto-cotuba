package br.com.unipds;

import java.nio.file.Path;

public record Markdown (Path archivePath, String contentMarkdown){
}
