package sensores;

import alertas.Alertavel;
import excecoes.SensorDesligado;

public abstract class Sensor implements Alertavel {


    private String id;
    private String local;
    private boolean ligado;

    protected Sensor(String id, String local) {
        this.id = id;
        this.local = local;
        this.ligado = true;
    }

    public String getId() {
        return id;
    }

    public String getLocal() {
        return local;
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
            throw new SensorDesligado("O sensor " + id + " está desligado.");
        }
    }

    public abstract double lerValor()
            throws SensorDesligado;

    public abstract String getUnidade();

}
