package br.com.unipds;

public class Chapter {
    private String title;
    private String contentHTML;
    private Markdown markdown;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentHTML() {
        return contentHTML;
    }

    public void setContentHTML(String contentHTML) {
        this.contentHTML = contentHTML;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }
}
