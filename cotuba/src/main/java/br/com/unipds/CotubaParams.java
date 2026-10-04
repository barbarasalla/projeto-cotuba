package br.com.unipds;

import java.nio.file.Path;

public record CotubaParams (
     Path diretorioDosMD,
     FormatEbookEnum formato,
     Path arquivoDeSaida,
     boolean modoVerboso){

    public CotubaParams (Path diretorioDosMD,
                         FormatEbookEnum formato,
                         Path arquivoDeSaida){
        if (diretorioDosMD == null) {
            throw new IllegalArgumentException("O diretório dos arquivos Markdown não pode ser nulo.");
        }
        if (formato == null) {
            throw new IllegalArgumentException("O formato do ebook não pode ser nulo.");
        }
        if (arquivoDeSaida == null) {
            throw new IllegalArgumentException("O arquivo de saída não pode ser nulo.");
        }
        this(diretorioDosMD, formato, arquivoDeSaida, false);
    }
}
