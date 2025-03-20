<p align="center">
  <img src="./assets/beholder.png" alt="logo" width="200" height="200" />
</p>
<h1 align="center">Beholder</h1>
<h3 align="center">Sistema de monitoramento de serviços</h3>


## Objetivo

A disponibilidade e o desempenho de serviços são fatores críticos para qualquer aplicação. Pequenas instabilidades podem impactar a experiência do usuário e até mesmo gerar perdas financeiras. Este sistema foi desenvolvido para monitorar serviços periodicamente, registrando e analisando métricas essenciais para garantir sua estabilidade e eficiência.

A cada intervalo de tempo configurado, o sistema realiza requisições aos serviços monitorados, registrando informações como tempo de resposta, status HTTP e mensagem retornada. Esses dados são armazenados e apresentados em gráficos, permitindo a análise histórica e a identificação de padrões de comportamento, facilitando a detecção de falhas e gargalos de desempenho.

Atualmente, o sistema fornece uma visão clara do status dos serviços monitorados, ajudando na manutenção preventiva e na rápida identificação de problemas. Como melhoria futura, será implementado um sistema de notificações, permitindo alertas automáticos sempre que uma anomalia for detectada, garantindo ainda mais agilidade na resposta a incidentes.

Acessando o Grafana

Para visualizar os dashboards, acesse o Grafana no endereço padrão, localhost:3000.

As credenciais padrão são:

* Usuário: admin

* Senha: admin

Já existe um dashboard configurado, exibindo métricas essenciais sobre os serviços monitorados. Ele pode ser acessado no menu "Dashboards" dentro do Grafana.

## Algmas views criadas no dashboard

### Tempo de resposta medio por serviço
![Tempo de resposta medio por serviço](/assets/tempo-de-resposta-medio-por-servico.png)

### Taxa de erro por serviço

![Taxa de erro por serviço](/assets/taxa-de-erro-por-servico.png)


### Variação de status Http por serviço
![Variação de status Http por serviço](/assets/mudanca-de-status-http-por-servico.png)

## Detalhes Técnicos  

O sistema foi desenvolvido em **Java 8**, seguindo a **arquitetura de Adapters** para garantir um alto nível de desacoplamento. Com essa abordagem, os serviços implementados podem ser facilmente substituídos ou estendidos sem impactar o restante da aplicação, tornando o sistema mais flexível e adaptável a diferentes necessidades.  

A aplicação usa **Spring Boot** para gerenciar a lógica de monitoramento e agendar as requisições. Os dados coletados são armazenados em um **PostgreSQL**, enquanto o **Prometheus** faz a coleta e o armazenamento otimizado das métricas. Para análise e visualização, o **Grafana** permite a criação de dashboards interativos, facilitando o acompanhamento do desempenho e da disponibilidade dos serviços monitorados.  

Com essa estrutura, o sistema pode evoluir com facilidade, permitindo mudanças e integrações sem grandes impactos na base de código.

# Setup

## Dependências 

- Java 8 ou 11
- Gradle 6.4

## Envs

   - Crie um arquivo `.env` na raiz do projeto seguindo o modelo disponibilizado em `.env.sample`.  

   - Preencha as variáveis conforme necessário, incluindo as credenciais do banco de dados e configurações dos serviços.  

   - O Spring não recupera os dados do arquivo .env por padrão. Por isso é preciso adcionar ad envs diretamente no Sistema Operacional

## Containers

Os serviços que o sistema necessita são rodados em containers docker que são orquestrados via docker-compose, no arquivo docker-compose.local.yml

Execute o seguinte comando para iniciar os contêineres necessários:  
     
```sh
   gradle dockerComposeUp
```  

Esse comando orquestra os seguintes serviços:

   **Database**: Armazena as informações de serviços observados, logs e notificações

   **Prometheus**: Coleta e armazena os dados do sistema.  

   **Grafana**: Exibe as métricas em dashboards interativos.  

   **Adminer**: Interface para gerenciar o banco de dados.  

Com os serviços em execução, o sistema estará pronto para coletar e visualizar métricas em tempo real.

Se for necessario derrubar os containers, execute o comando 

```sh
   gradle dockerComposeDown
```  

## Migrations 

Este repositório contém um script Bash para executar migrações no banco de dados PostgreSQL, garantindo que o cliente psql esteja instalado antes da execução.

Os dados migrados são apenas para testes locais, não devendo ser usados em ambientes de produção.

Para executar a migração, execute a task migrateAndRun do gradle

```bash
gradle migrateAndRun
```

ou

```bash
./gradlew migrateAndRun
```

Também é possível executar a migration seguindo os passos abaixo.


- 1: Conceda permissão de execução ao script:

```bash
chmod +x ./scripts/migrations.sh

```

- 2 Execute a migração


```bash
./scripts/migrations.sh
```
