package sensores;

import excecoes.SensorDesligado;

public class SensorUmidade extends Sensor {

    private double limiteAlerta;

    public SensorUmidade(String id, String local, double limiteAlerta) {super(id, local);
        setLimiteAlerta(limiteAlerta);
    }

    public double getLimiteAlerta() {
        return limiteAlerta;
    }

    public void setLimiteAlerta(double limite) {
        if (!Double.isFinite(limite) || limite < 0 || limite > 100) {
            throw new IllegalArgumentException("O limite deve estar entre 0 e 100%.");
        }

        this.limiteAlerta = limite;
    }

    @Override
    public double lerValor() throws SensorDesligado {
        verificarLigado();

        return Math.random() * 100;
    }

    @Override
    public boolean verificarAlerta(double valor) {
        return valor > limiteAlerta;
    }

    @Override
    public String getUnidade() {
        return "%";
    }
}
