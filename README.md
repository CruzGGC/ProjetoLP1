# Trabalho Prático nº 1 – Ficheiros

Em Portugal, a eleição da Assembleia da República é feita com base num sistema de representação proporcional, usando círculos eleitorais. Atualmente existem 22 círculos eleitorais, sendo 18 círculos correspondentes aos distritos de Portugal Continental e 2 círculos correspondentes às regiões autónomas dos Açores e da Madeira, respetivamente. Os restantes 2 círculos eleitorais são para a emigração portuguesa, um para a Europa e outro para o resto do mundo.

Para ajudar a Comissão Nacional de Eleições a apurar todos os votos e verificar quem é o partido vencedor foi pedido à ESTGA que os alunos da Licenciatura em Tecnologias da Informação desenvolvessem um programa que permita fazer esse apuramento automaticamente.

O programa a desenvolver deve ler os dados de cada círculo eleitoral, calcular os resultados e guardar num ficheiro de texto (um por cada círculo) com o nome do círculo e a extensão .txt. Para os resultados nacionais, para alem de aparecer no ecrã, deve ser criado um ficheiro com o nome TotalNacional.txt.

![Tabela Exemplo](https://i.imgur.com/YzE6Clm.png)

# Explicação do Projeto

Este projeto é um programa Java que lê dados de votação de vários círculos eleitorais em Portugal, calcula os resultados e os grava em arquivos de texto. Cada círculo eleitoral tem seu próprio arquivo e há também um arquivo para os resultados nacionais.

O programa lê os dados de votação de um diretório chamado "Distritos", onde cada arquivo representa um círculo eleitoral. Os dados de votação são armazenados em objetos VotosCirculoEleitoral e VotosConcelho, que são serializados e armazenados nos arquivos.

O programa lê cada arquivo no diretório "Distritos", deserializa o objeto VotosCirculoEleitoral armazenado nele e, em seguida, processa os dados de votação. Ele imprime o nome do círculo eleitoral e, para cada conselho no círculo eleitoral, imprime o nome do conselho e os votos de cada partido nesse conselho.

O programa também mantém um total de votos para cada partido em todos os círculos eleitorais em um HashMap chamado totalVotos. Para cada conselho no círculo eleitoral, ele adiciona os votos de cada partido ao total de votos.

Finalmente, o programa imprime o total de votos de cada partido em todos os círculos eleitorais e grava esses resultados no arquivo "Resultado/TotalNacional.txt". Ele calcula a porcentagem de votos que cada partido recebeu e imprime essa informação tanto na saída padrão quanto no arquivo "Resultado/TotalNacional.txt".

# Explicação das classes

A classe java.util.Map é usada para armazenar e manipular conjuntos de dados na forma de pares chave-valor.

> `put(K key, V value)`: Este método é usado para associar o valor especificado com a chave especificada no mapa. É usado nas classes `VotosCirculoEleitoral` e `VotosConcelho` para adicionar votos a um concelho ou partido, e na classe `TP1` para adicionar votos ao total de votos.

> `get(Object key)`: Este método é usado para retornar o valor ao qual a chave especificada está mapeada, ou null se este mapa não contém mapeamento para a chave. É usado na classe `TP1` para obter os votos de um concelho ou partido.

> `keySet()`: Usado para iterar sobre todas as chaves em um mapa. Cada chave representa um conselho em um círculo eleitoral. `leitura.getVotosPorConcelho().keySet()` retorna um conjunto de todas as chaves (ou seja, nomes dos conselhos) no mapa retornado por `getVotosPorConcelho()`.

> `getOrDefault(Object key, V defaultValue)`: Este método é usado para retornar o valor ao qual a chave especificada está mapeada, ou o valor padrão se este mapa não contém mapeamento para a chave. É usado na classe `TP1` para obter o total de votos de um partido, ou 0 se o partido não tiver votos.

> `values()`: Usado para iterar sobre todos os valores em um mapa. Cada valor representa o total de votos de um partido. `totalVotos.values()` retorna uma coleção de todos os valores (ou seja, totais de votos) no mapa `totalVotos`

> `entrySet()`: Usado para iterar sobre todos os mapeamentos em um mapa. Cada mapeamento representa um partido e o total de votos que recebeu. `votos.entrySet()` retorna um conjunto de todos os mapeamentos (ou seja, pares partido-votos) no mapa votos.
