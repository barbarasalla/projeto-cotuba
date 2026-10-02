package br.com.unipds;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@ApplicationScoped
public class LeitorPropriedadesEbookImpl implements LeitorPropriedadesEbook {

    @Override
    public void ler(Path dirMD, Ebook ebook){

        Path resolve = dirMD.resolve("ebook.properties"); // Caminho completo para o arquivo ebook.properties

        if(!Files.exists(resolve)){
            throw new IllegalStateException("Arquivo ebook.properties não encontrado no diretório: " + dirMD);
        }

        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(resolve)){ // Abre o arquivo ebook.properties para leitura
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8); // Cria um InputStreamReader para ler o arquivo com codificação UTF-8
            properties.load(reader); // Carrega as propriedades do arquivo
            } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String titulo = properties.getProperty("cotuba.ebook.titulo");
        String author = properties.getProperty("cotuba.ebook.autor");

        if(titulo == null || titulo.isBlank()){
            throw new IllegalStateException("Propriedade cotuba.ebook.titulo não encontrada no arquivo ebook.properties");
        }

        if (author == null || author.isBlank()){
            throw new IllegalStateException("Propriedade cotuba.ebook.autor não encontrada no arquivo ebook.properties");
        }

        ebook.setTitle(titulo);
        ebook.setAuthor(author);

    }
}
