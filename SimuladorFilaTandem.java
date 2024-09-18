/**
 *
 * @author Marcio Menezes, Maria Eduarda Contu e 
 */
import java.util.PriorityQueue;
import java.util.Random;

class Evento implements Comparable<Evento> {
    static final int CHEGADA = 0;
    static final int SAIDA = 1;

    int tipo;
    double tempo;
    int numeroFila;

    public Evento(int tipo, double tempo, int numeroFila) {
        this.tipo = tipo;
        this.tempo = tempo;
        this.numeroFila = numeroFila;
    }

    @Override
    public int compareTo(Evento outro) {
        return Double.compare(this.tempo, outro.tempo);
    }
}

class SistemaFila {
    private final int servidores;
    private final int capacidade;
    private final double atendimentoMin;
    private final double atendimentoMax;
    private int clientesNaFila = 0;
    private int perdas = 0;
    private double tempoOcupado = 0.0;
    private double ultimoTempoEvento = 0.0;

    public SistemaFila(int servidores, int capacidade, double atendimentoMin, double atendimentoMax) {
        this.servidores = servidores;
        this.capacidade = capacidade;
        this.atendimentoMin = atendimentoMin;
        this.atendimentoMax = atendimentoMax;
    }

    public boolean estaCheia() {
        return clientesNaFila >= capacidade;
    }

    public void processarChegada(double tempoAtual) {
        if (clientesNaFila < servidores) {
            tempoOcupado += tempoAtual - ultimoTempoEvento;
        }
        if (!estaCheia()) {
            clientesNaFila++;
        } else {
            perdas++;
        }
        ultimoTempoEvento = tempoAtual;
    }

    public void processarSaida(double tempoAtual) {
        if (clientesNaFila > 0) {
            clientesNaFila--;
            if (clientesNaFila < servidores) {
                tempoOcupado += tempoAtual - ultimoTempoEvento;
            }
            ultimoTempoEvento = tempoAtual;
        }
    }

    public double gerarTempoAtendimento(Random aleatorio) {
        return atendimentoMin + (atendimentoMax - atendimentoMin) * aleatorio.nextDouble();
    }

    public int getPerdas() {
        return perdas;
    }

    public double getTempoOcupado() {
        return tempoOcupado;
    }
}

public class SimuladorFilaTandem {
    private static final int MAX_EVENTOS = 100000;

    public static void main(String[] args) {
        // Parâmetros de simulação
        double chegadaMin = 1.0, chegadaMax = 4.0;
        double atendimento1Min = 3.0, atendimento1Max = 4.0; // Fila 1: Atendimento
        double atendimento2Min = 2.0, atendimento2Max = 3.0; // Fila 2: Atendimento

        // Inicializar sistema de filas
        SistemaFila fila1 = new SistemaFila(2, 3, atendimento1Min, atendimento1Max); // Fila 1
        SistemaFila fila2 = new SistemaFila(1, 5, atendimento2Min, atendimento2Max); // Fila 2

        // Inicializar eventos
        PriorityQueue<Evento> listaEventos = new PriorityQueue<>();
        Random aleatorio = new Random();

        // Primeiro cliente chega ao tempo 1.5
        listaEventos.add(new Evento(Evento.CHEGADA, 1.5, 1));

        double tempoAtual = 0.0;
        int totalEventos = 0;

        // Processar eventos
        while (totalEventos < MAX_EVENTOS && !listaEventos.isEmpty()) {
            Evento evento = listaEventos.poll();
            tempoAtual = evento.tempo;
            totalEventos++;

            if (evento.tipo == Evento.CHEGADA) {
                if (evento.numeroFila == 1) {
                    fila1.processarChegada(tempoAtual);
                    double proximaChegada = tempoAtual + chegadaMin + (chegadaMax - chegadaMin) * aleatorio.nextDouble();
                    listaEventos.add(new Evento(Evento.CHEGADA, proximaChegada, 1));

                    double tempoAtendimento1 = fila1.gerarTempoAtendimento(aleatorio);
                    listaEventos.add(new Evento(Evento.SAIDA, tempoAtual + tempoAtendimento1, 1));
                } else if (evento.numeroFila == 2) {
                    fila2.processarChegada(tempoAtual);

                    double tempoAtendimento2 = fila2.gerarTempoAtendimento(aleatorio);
                    listaEventos.add(new Evento(Evento.SAIDA, tempoAtual + tempoAtendimento2, 2));
                }
            } else if (evento.tipo == Evento.SAIDA) {
                if (evento.numeroFila == 1) {
                    fila1.processarSaida(tempoAtual);
                    // Cliente vai para a fila 2
                    listaEventos.add(new Evento(Evento.CHEGADA, tempoAtual, 2));
                } else if (evento.numeroFila == 2) {
                    fila2.processarSaida(tempoAtual);
                }
            }
        }

        System.out.println("Simulação finalizada com " + totalEventos + " eventos.");
        System.out.println("Tempo total da simulação: " + tempoAtual + " minutos.");
        System.out.println("Fila 1 - Clientes perdidos: " + fila1.getPerdas());
        System.out.println("Fila 2 - Clientes perdidos: " + fila2.getPerdas());
        System.out.println("Fila 1 - Tempo ocupado: " + fila1.getTempoOcupado() + " minutos.");
        System.out.println("Fila 2 - Tempo ocupado: " + fila2.getTempoOcupado() + " minutos.");
    }
}
