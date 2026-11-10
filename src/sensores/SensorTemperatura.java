package sensores;

import excecoes.SensorDesligado;

public class SensorTemperatura extends Sensor{

    private double limiteAlerta;

    public SensorTemperatura(String id, String local) {
        this(id, local, 30);
    }

    public SensorTemperatura(String id, String local,
                             double limiteAlerta) {
        super(id, local);
        setLimiteAlerta(limiteAlerta);
    }

    public double getLimiteAlerta() {
        return limiteAlerta;
    }

    public void setLimiteAlerta(double limite) {
        if (!Double.isFinite(limite) || limite < -40 || limite > 125) {
            throw new IllegalArgumentException("O limite deve estar entre -40 e 125 °C.");
        }

        this.limiteAlerta = limite;
    }

    @Override
    public double lerValor() throws SensorDesligado {
        verificarLigado();

        return 15 + Math.random() * 25;
    }

    @Override
    public boolean verificarAlerta(double valor) {
        return valor > limiteAlerta;
    }

    @Override
    public String getUnidade() {
        return "°C";
    }
}
