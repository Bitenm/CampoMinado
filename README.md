# Campo Minado Swing

Um jogo de Campo Minado para desktop desenvolvido em Java com Swing. O projeto foi construído como prática de orientação a objetos, interfaces funcionais, expressões lambda e do padrão Observer.

## Funcionalidades

- Grade de 16 linhas, 30 colunas e 50 minas.
- Abertura em cascata para campos com vizinhança segura.
- Marcação e desmarcação de possíveis minas.
- Detecção de vitória e derrota, com revelação das minas em caso de explosão.
- Cronômetro que inicia na primeira abertura, para ao fim da partida e zera no reinício.
- Contador visual de marcações em relação ao total de minas.
- Botão para reiniciar a partida.

## Conceitos praticados

- Java e orientação a objetos.
- Java Swing para a interface gráfica.
- Padrão Observer para comunicar mudanças entre `Campo`, `Tabuleiro` e componentes visuais.
- Interfaces funcionais, `Consumer` e expressões lambda.

## Como executar

1. Abra o projeto em uma IDE com suporte a Java, como IntelliJ IDEA.
2. Execute a classe `br.com.biten.cm.visao.TelaPrincipal`.

## Estrutura principal

- `modelo`: regras do jogo, campos, tabuleiro e eventos.
- `visao`: tela Swing, botões dos campos, cabeçalho, cronômetro e contador de marcações.
