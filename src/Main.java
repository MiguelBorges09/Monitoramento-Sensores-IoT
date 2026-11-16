import arquivo.ArquivoLeituras;
import monitoramento.CentralMonitoramento;
import sensores.Sensor;
import sensores.SensorTemperatura;
import sensores.SensorUmidade;
import sensores.SensorPresenca;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        CentralMonitoramento central = new CentralMonitoramento();
        ArquivoLeituras arquivo = new ArquivoLeituras("leituras.csv");

        try {
            String conteudo = arquivo.ler();

            if (!conteudo.isBlank()) {
                central.carregarHistorico(conteudo);
            }

            System.out.println("Histórico anterior carregado");

        } catch (NoSuchFileException e) {
            System.out.println("Ainda não existe histórico salvo");

        } catch (IOException e) {
            System.out.println("Falha ao carregar: " + e.getMessage());
            System.out.println("Encerrando para proteger o arquivo anterior");
            return;
        }

        central.adicionarSensor(new SensorTemperatura("T01", "Sala de servidores"));

        central.adicionarSensor(new SensorUmidade("U01", "Laboratório", 70));

        central.adicionarSensor(new SensorPresenca("P01", "Entrada"));

        Scanner entrada = new Scanner(System.in);
        boolean executando = true;

        while (executando) {

            mostrarMenu();

            // Se a entrada terminar, também tenta salvar antes de encerrar.
            boolean fimEntrada = !entrada.hasNextLine();
            String opcao = "0";

            if (!fimEntrada) {
                opcao = entrada.nextLine().trim();
            }

            try {
                switch (opcao) {

                    case "1":
                        for (Sensor sensor : central.getSensores()) {
                            System.out.println(sensor.getId() + " | " + sensor.getLocal() + " | Ligado: " + sensor.isLigado());
                        }
                        break;

                    case "2":
                        central.coletarLeituras();
                        break;

                    case "3":
                        Sensor escolhido = selecionarSensor(entrada, central);

                        if (escolhido.isLigado()) {
                            escolhido.desligar();
                        } else {
                            escolhido.ligar();
                        }

                        System.out.println("Ligado: " + escolhido.isLigado());
                        break;

                    case "4":
                        Sensor escolhidoLocal =
                                selecionarSensor(entrada, central);

                        escolhidoLocal.setLocal(lerTexto(entrada, "Novo local: "));

                        System.out.println("Local atualizado.");
                        break;

                    case "5":
                        alterarLimite(entrada, central);
                        break;

                    case "6":
                        System.out.println(central.getHistorico());
                        break;

                    case "0":
                        arquivo.salvar(central.getHistorico());
                        System.out.println("Histórico salvo em leituras.csv.");
                        executando = false;
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido para o limite.");

            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida: " + e.getMessage());

            } catch (IOException e) {
                System.out.println("Não foi possível salvar: " + e.getMessage());

                if (fimEntrada) {
                    System.out.println("Entrada encerrada. As novas leituras não foram salvas.");
                    break;
                }

                System.out.println("Corrija o problema e tente a opção 0 novamente.");
            }
        }

        entrada.close();
        System.out.println("Programa encerrado.");
    }

    private static void mostrarMenu() {

        System.out.println("\n===== SENSORES IoT - SIMULAÇÃO =====");
        System.out.println("1 - Listar sensores");
        System.out.println("2 - Coletar leituras");
        System.out.println("3 - Ligar ou desligar sensor");
        System.out.println("4 - Alterar local");
        System.out.println("5 - Alterar limite de alerta");
        System.out.println("6 - Mostrar histórico");
        System.out.println("0 - Salvar e sair");
        System.out.print("Opção: ");
    }

    private static String lerTexto(Scanner entrada, String mensagem) {

        System.out.print(mensagem);

        if (!entrada.hasNextLine()) {
            throw new IllegalArgumentException("Entrada encerrada.");
        }

        return entrada.nextLine().trim();
    }

    private static Sensor selecionarSensor(
            Scanner entrada, CentralMonitoramento central) {

        Sensor sensor = central.buscarSensor(lerTexto(entrada, "ID do sensor: "));

        if (sensor == null) {
            throw new IllegalArgumentException("Sensor não encontrado.");
        }

        return sensor;
    }

    private static void alterarLimite(
            Scanner entrada, CentralMonitoramento central) {

        Sensor sensor = selecionarSensor(entrada, central);

        if (sensor instanceof SensorPresenca) {
            System.out.println("Presença tem regra fixa: 1 indica presença.");
            return;
        }

        double limite = Double.parseDouble(lerTexto(entrada, "Novo limite: ").replace(',', '.'));

        if (sensor instanceof SensorTemperatura) {
            SensorTemperatura temperatura = (SensorTemperatura) sensor;
            temperatura.setLimiteAlerta(limite);

        } else if (sensor instanceof SensorUmidade) {
            SensorUmidade umidade = (SensorUmidade) sensor;
            umidade.setLimiteAlerta(limite);
        }

        System.out.println("Limite atualizado.");
    }
}