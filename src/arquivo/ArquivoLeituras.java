package arquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArquivoLeituras {

    private final String caminho;

    public ArquivoLeituras(String caminho) {

        if (caminho == null || caminho.isBlank()) {
            throw new IllegalArgumentException("O caminho do arquivo é obrigatório.");
        }

        this.caminho = caminho;
    }

    public void salvar(String conteudo) throws IOException {

        if (conteudo == null) {
            throw new IllegalArgumentException("O conteúdo não pode ser nulo.");
        }

        Path arquivo = Paths.get(caminho);
        Files.writeString(arquivo, conteudo);
    }

    public String ler() throws IOException {

        Path arquivo = Paths.get(caminho);
        return Files.readString(arquivo);
    }
}