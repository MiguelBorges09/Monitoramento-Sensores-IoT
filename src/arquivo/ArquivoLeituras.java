package arquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArquivoLeituras {

    private final String caminho;

    public ArquivoLeituras(String caminho) {

        if (caminho == null || caminho.isBlank()) {
            throw new IllegalArgumentException(
                    "O caminho do arquivo é obrigatório."
            );
        }

        this.caminho = caminho;
    }

    public void salvar(String conteudo)
            throws IOException {

        Files.writeString(
                Paths.get(caminho),
                conteudo
        );
    }

    public String ler() throws IOException {
        return Files.readString(Paths.get(caminho));
    }
}