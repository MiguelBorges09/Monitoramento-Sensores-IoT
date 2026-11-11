package monitoramento;

import excecoes.SensorDesligado;
import sensores.Sensor;

import java.util.ArrayList;
import java.util.List;

public class CentralMonitoramento {

    private List<Sensor> sensores;
    private String historico;

    public CentralMonitoramento() {
        this.sensores = new ArrayList<Sensor>();
        this.historico = "idSensor;valor;unidade\n";
    }

    public void adicionarSensor(Sensor sensor) {

        if (sensor == null) {
            throw new IllegalArgumentException("O sensor não pode ser nulo.");
        }

        sensores.add(sensor);
    }

    public List<Sensor> getSensores() {
        return new ArrayList<Sensor>(sensores);
    }

    public String getHistorico() {
        return historico;
    }

    public void coletarLeituras() {

        for (Sensor sensor : sensores) {

            try {
                double valor = sensor.lerValor();

                historico += sensor.getId() + ";" + valor + ";" + sensor.getUnidade() + "\n";

                System.out.println();
                System.out.println("Sensor: " + sensor.getId());

                System.out.println("Leitura: " + valor + " " + sensor.getUnidade());

                if (sensor.verificarAlerta(valor)) {
                    System.out.println("ALERTA detectado!");
                } else {
                    System.out.println("Situação normal.");
                }

            } catch (SensorDesligado e) {
                System.out.println("Falha: " + e.getMessage());
            }
        }
    }
}