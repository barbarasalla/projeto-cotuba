package br.com.unipds;

import java.nio.file.Path;

public class CotubaParams {

    private Path diretorioDosMD;
    private FormatEbookEnum formato;
    private Path arquivoDeSaida;
    private boolean modoVerboso = false;

    public Path getDiretorioDosMD() {
        return diretorioDosMD;
    }

    public void setDiretorioDosMD(Path diretorioDosMD) {
        this.diretorioDosMD = diretorioDosMD;
    }

    public FormatEbookEnum getFormato() {
        return formato;
    }

    public void setFormato(FormatEbookEnum formato) {
        this.formato = formato;
    }

    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    public void setArquivoDeSaida(Path arquivoDeSaida) {
        this.arquivoDeSaida = arquivoDeSaida;
    }

    public boolean isModoVerboso() {
        return modoVerboso;
    }

    public void setModoVerboso(boolean modoVerboso) {
        this.modoVerboso = modoVerboso;
    }
}
