
<img src="./assets/beholder.png" 
        alt="Picture" 
        width="200" 
        height="200" 
        style="display: block; margin: 0 auto" />
<h1 style="text-align:center;">Beholder</h1>
<h3 style="text-align:center;">Sistema de monitoramento de serviços</h3>


## Objetivo

A disponibilidade e o desempenho de serviços são fatores críticos para qualquer aplicação. Pequenas instabilidades podem impactar a experiência do usuário e até mesmo gerar perdas financeiras. Este sistema foi desenvolvido para monitorar serviços periodicamente, registrando e analisando métricas essenciais para garantir sua estabilidade e eficiência.

A cada intervalo de tempo configurado, o sistema realiza requisições aos serviços monitorados, registrando informações como tempo de resposta, status HTTP e mensagem retornada. Esses dados são armazenados e apresentados em gráficos, permitindo a análise histórica e a identificação de padrões de comportamento, facilitando a detecção de falhas e gargalos de desempenho.

Atualmente, o sistema fornece uma visão clara do status dos serviços monitorados, ajudando na manutenção preventiva e na rápida identificação de problemas. Como melhoria futura, será implementado um sistema de notificações, permitindo alertas automáticos sempre que uma anomalia for detectada, garantindo ainda mais agilidade na resposta a incidentes.

## Alguns dashboards criados 

### Tempo de resposta medio por serviço
![Tempo de resposta medio por serviço](/assets/tempo-de-resposta-medio-por-servico.png)

### Taxa de erro por serviço

![Taxa de erro por serviço](/assets/taxa-de-erro-por-servico.png)


### Variação de status Http por serviço
![Variação de status Http por serviço](/assets/mudanca-de-status-http-por-servico.png)