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
        this.historico = "idSensor;valor;unidade;alerta\n";
    }

    public void adicionarSensor(Sensor sensor) {

        if (sensor == null) {
            throw new IllegalArgumentException("O sensor não pode ser nulo.");
        }

        if (buscarSensor(sensor.getId()) != null) {
            throw new IllegalArgumentException("Já existe um sensor com esse ID.");
        }

        sensores.add(sensor);
    }

    public Sensor buscarSensor(String id) {

        for (Sensor sensor : sensores) {

            if (sensor.getId().equalsIgnoreCase(id)) {
                return sensor;
            }
        }

        return null;
    }

    public List<Sensor> getSensores() {
        return new ArrayList<Sensor>(sensores);
    }

    public String getHistorico() {
        return historico;
    }

    public void carregarHistorico(String conteudo) {

        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("O histórico carregado não pode ser vazio.");
        }

        historico = conteudo;

        if (!historico.endsWith("\n")) {
            historico += "\n";
        }
    }

    public void coletarLeituras() {

        if (sensores.isEmpty()) {
            System.out.println("Nenhum sensor cadastrado.");
            return;
        }

        for (Sensor sensor : sensores) {

            try {
                double valor = sensor.lerValor();
                boolean alerta = sensor.verificarAlerta(valor);

                historico += sensor.getId() + ";" + valor + ";" + sensor.getUnidade() + ";" + alerta + "\n";

                System.out.println();
                System.out.println("Sensor: " + sensor.getId() + " | Local: " + sensor.getLocal());

                System.out.println("Leitura: " + valor + " " + sensor.getUnidade());

                if (alerta) {
                    System.out.println("Alerta");
                } else {
                    System.out.println("Situação normal.");
                }

            } catch (SensorDesligado e) {
                System.out.println("Falha: " + e.getMessage());
            }
        }
    }
}