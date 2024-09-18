# Simulador de Fila em Tandem

Este projeto implementa um simulador de uma rede de filas em tandem, onde a saída de uma fila é a entrada da próxima. O simulador suporta configurações com múltiplas filas e processa eventos de chegada e saída de clientes, considerando o tempo de atendimento em cada fila e a capacidade das mesmas.

## Estrutura das Filas

- **Fila 1**: Tipo G/G/2/3 (2 servidores, capacidade 3), tempo de chegada de clientes entre 1 e 4 minutos, e tempo de atendimento entre 3 e 4 minutos.
- **Fila 2**: Tipo G/G/1/5 (1 servidor, capacidade 5), sem chegadas externas, recebendo 100% dos clientes da Fila 1. O tempo de atendimento é entre 2 e 3 minutos.

## Como Usar

### Pré-requisitos

- Java Development Kit (JDK) instalado.

### Compilação

1. Clone ou baixe o repositório.
2. Navegue até o diretório do projeto e salve o código-fonte como `SimuladorFilaTandem.java`.
3. Compile o código utilizando o seguinte comando:
   ```bash
   javac SimuladorFilaTandem.

### Execução

Após compilar, execute o simulador com o seguinte comando:
    ```bash
    
     java SimuladorFilaTandem


### Saída do Simulador

O simulador processará até 100.000 eventos pseudoaleatórios e exibirá:

Clientes perdidos: O número de clientes que não conseguiram entrar nas filas devido à capacidade.
Tempo ocupado: O tempo total em que cada fila esteve ocupada.
Tempo total da simulação: O tempo final no qual a simulação foi encerrada.

### Exemplo de Saída

```bash
Simulação finalizada com 100000 eventos.
Tempo total da simulação: 35236.75 minutos.
Fila 1 - Clientes perdidos: 245
Fila 2 - Clientes perdidos: 0
Fila 1 - Tempo ocupado: 17025.63 minutos.
Fila 2 - Tempo ocupado: 14518.12 minutos.


