package br.com.unipds;

import java.util.List;

public interface RendererMK {
    // Retorn a list of html strings, each representing a chapter converted from markdown to html
    List<Chapter> render(List<Markdown> markdownsList);
}
