package sensores;

import excecoes.SensorDesligado;

public class SensorPresenca extends Sensor {

    public SensorPresenca(String id, String local) {
        super(id, local);
    }

    @Override
    public double lerValor() throws SensorDesligado {
        verificarLigado();

        if (Math.random() < 0.5) {
            return 0;
        }

        return 1;
    }

    @Override
    public boolean verificarAlerta(double valor) {
        return valor == 1;
    }

    @Override
    public String getUnidade() {
        return "presença (0/1)";
    }
}