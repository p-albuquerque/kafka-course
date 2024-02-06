# Mensageria e Kafka (Aula 1.1)
## Motivação geral do paradigma
### Exemplo: Sistema de Ecommerce (momento de realizar a compra online)
- Método monolítico/sequencial:
    - Requisição HTTP
    -> (thread 1) Email: compra em análise... -> verificação de pagamento.
- Método modular/paralela
    - Requisição HTTP
    -> (thread 1) SERVICE: Email de compra em análise...
    -> (thread 2) SERVICE: Verificação de pagamento

Se for inserido nesse sistema a implementação da feature de auditoria/log de cada etapa desse processo, se torna
ainda mais plausível o uso da modularidade, uma vez que cada etapa (email, pagamento, ...) vai chamar o processo
de criação de uma instância no log/registro de atividades.

## Como funciona
### Tópico
Cada serviço permanece "atento" ao tópico que diz respeito a sua execução, exemplo:
- (thread 1) SERVICE: Verificação de pagamento -> TRUE -> (send new message in TOPIC: pagamento_confirmado)
- (thread nova) SERVICE TRIGGERED "pag_confirmado": Reservar estoque

Ou seja, assim que a verificação de pagamento é finalizada, ele disponibiliza os dados dessa compra(mensagem) no tópico (pag_confirmado). Assim que um novo conteúdo é disponibilizado neste tópico, os serviços que o escuta são iniciados
se alimentando desses dados contidos no tópico.

### Broker
- Recebe as mensagens enviadas por qualquer serviço e os torna visíveis nos tópicos correspondentes
deste modo, iniciando cada serviço que está ouvindo mensagens deste tópico;

# Instalando kafka (Aula 1.2)
- Em kafka.apache.org, baixe o tar.gz da versão binária (código já buildado)
- Extraia o diretório do kafka e abra o terminal nele

## Configurando e iniciando o kafka
Dentro do root do diretório do kafka:
- Rodar o zookeeper com as configurações padrão 
    
    ```
    bin/zookeeper-server-start.sh config/zookeeper.properties
    ```
    Resultado: zookeeper em pé na porta 2181...

- Em outra aba, também no root do diretório kafka, agora rodar o kafka com configuração padrão
    
    ```
    bin/kafka-server-start.sh config/server.properties
    ```
    Resultado: kafka iniciado com sucesso, na porta 9092 (server.properties define)

## Usando o kafka
Agora com o zookeeper e o kafka rodando, criaremos um tópico para transitar mensagens:
- Criar tópico: 
    
    ```
    bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
    ```
    Resultado: tópico chamado LOJA_NOVO_PEDIDO criado, agregado ao kafka que está rodando na porta 9092 (replicant-factor e partitions iremos ver depois)

- Verificar tópico criado:
    
    ```
    bin/kafka-topics.sh --list --bootstrap-server localhost:9092
    ```
    Resultado: Exibirá o nome do tópico criado

- Criar um produtor para este tópico:
    
    ```
    bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
    ```
    Resultado: o bash está preparado para receber as mensagens, conteúdos produzidos para este tópico

- Produzir algumas mensagens para este tópico:
    ```
    pedido0,550
    pedido1,330
    pedido2,67213
    ```

- Criar um consumidor para este tópico:
    ```
    bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
    ```
    Resultado: Exibirá todas as mensagens criadas pelo produtor (a tag --from-beginning indica que enxergará todas as mensagens, desde o primeiro envio)

# Aplicando kafka em código

- Cria novo projeto maven
- Pesquisa dependência do kafka para maven 
```
    mvn kafka-clients
```
- Aplica dependência no ```pom.xml```
- Pesquisar dependência sf4j e aplica também

