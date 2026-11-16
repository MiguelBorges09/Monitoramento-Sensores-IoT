package sensores;

import alertas.Alertavel;
import excecoes.SensorDesligado;

public abstract class Sensor implements Alertavel {


    private String id;
    private String local;
    private boolean ligado;

    protected Sensor(String id, String local) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID é obrigatório.");
        }

        if (id.contains(";") || id.contains("\n") || id.contains("\r")) {
            throw new IllegalArgumentException("O ID não pode conter ponto e vírgula ou quebra de linha.");
        }

        this.id = id.trim();
        setLocal(local);
        this.ligado = true;
    }

    public String getId() {
        return id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {

        if (local == null || local.isBlank()) {
            throw new IllegalArgumentException("O local é obrigatório.");
        }

        this.local = local.trim();
    }

    public boolean isLigado() {
        return ligado;
    }

    public void ligar() {
        ligado = true;
    }

    public void desligar() {
        ligado = false;
    }

    protected void verificarLigado() throws SensorDesligado {

        if (!ligado) {
            throw new SensorDesligado("O sensor " + id + " tá desligado.");
        }
    }

    public abstract double lerValor()
            throws SensorDesligado;

    public abstract String getUnidade();

}
