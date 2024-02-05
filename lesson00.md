# Mensageria e Kafka
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
