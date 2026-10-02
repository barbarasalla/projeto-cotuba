package br.com.unipds;

import java.nio.file.Path;
import java.util.List;

public class Ebook {
    private String title;
    private String author;
    private FormatEbookEnum format;
    private List<Chapter> chapters;
    private Path outputFile;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public FormatEbookEnum getFormat() {
        return format;
    }

    public void setFormat(FormatEbookEnum format) {
        this.format = format;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }
}