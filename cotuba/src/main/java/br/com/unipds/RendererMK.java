package br.com.unipds;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.nio.file.Path;
import java.util.List;

public class RendererMK {

    // Retorn a list of html strings, each representing a chapter converted from markdown to html
    public List<Chapter> render(Path arquivoMK) {

        MarkdownRepository markdownRepository = new MarkdownRepository();
        List<Chapter> chapters = markdownRepository.search(arquivoMK);

        return chapters.stream().map(chapter -> {
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
                return chapter;
            } catch (Exception ex) {
                throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + chapter.getArchivePath(), ex);
            }
        }).toList();
    }
}