package br.com.unipds;

import java.nio.file.Path;

public class Chapter {
    private String title;
    private String contentMarkdown;
    private String contentHTML;
    private Path archivePath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentMarkdown() {
        return contentMarkdown;
    }

    public void setContentMarkdown(String contentMarkdown) {
        this.contentMarkdown = contentMarkdown;
    }

    public String getContentHTML() {
        return contentHTML;
    }

    public void setContentHTML(String contentHTML) {
        this.contentHTML = contentHTML;
    }

    public Path getArchivePath() {
        return archivePath;
    }

    public void setArchivePath(Path archivePath) {
        this.archivePath = archivePath;
    }
}
