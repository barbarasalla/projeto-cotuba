package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;

@ApplicationScoped
public class RendererMarkedownImpl implements RendererMK {

    // Retorn a list of html strings, each representing a chapter converted from markdown to html
    @Override
    public void render(List<Chapter> chapterList) {

        chapterList.forEach(chapter -> {
            Parser parser = Parser.builder().build();
            Node document = null;
            try {
                String markdown = chapter.getContentMarkdown(); // Lê o conteúdo do arquivo .md
                document = parser.parse(markdown); // Faz o parse do conteúdo Markdown para um documento Node
                document.accept(new AbstractVisitor() {
                    @Override
                    public void visit(Heading heading) {
                        if (heading.getLevel() == 1) {
                            // capítulo
                            String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
                            chapter.setTitle(tituloDoCapitulo);
                            // TODO: usar título do capítulo
                        } else if (heading.getLevel() == 2) {
                            // seção
                        } else if (heading.getLevel() == 3) {
                            // título
                        }
                    }

                });
            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao fazer parse do arquivo " + chapter.getArchivePath(), ex);
            }

            try {
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                chapter.setContentHTML(renderer.render(document));
            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + chapter.getArchivePath(), ex);
            }
        });
    }
}