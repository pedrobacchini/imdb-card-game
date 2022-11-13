# imdb-card-game

## Descrição
API REST para uma aplicação ao estilo card game, onde serão informados dois filmes e o jogador deve acertar aquele que possui melhor avaliação no IMDB.

## Requisitos
1. Ojogadordevefazerloginparainiciarumanovapartida.Portanto,cadapartidasempre será identificada pela autenticação do usuário.
a. Nãohárestriçõesondearmazenarosusuários:emmemória,ouembanco,etc.
2. Cadarodadadojogoconsisteeminformarumpardefilmes,observandoparanãorepetir
o mesmo par nem formar um par com um único filme.
a. São sequências não-válidas: [A-A] o mesmo filme repetido; [A-B, A-B] pares
repetidos – considere iguais os pares do tipo A-B e B-A.
b. Osseguintesparessãoválidos:[A-B,B-C]ofilmeBéusadoemparesdiferentes.
3. O jogador deve tentar acertar qual filme possui maior pontuação, composta pela nota (0.0-10.0) multiplicado pelo total de votos.
4. Se escolher o vencedor correto, conta 1 ponto. São permitidos até três erros. Após responder, terá acesso a novo par de filmes quando acessar o endpoint do quiz.
5. Forneça endpoints específicos para iniciar e encerrar a partida a qualquer momento. Valide o momento em que cada funcionalidade pode ser acionada.
6. Nãodeveserpossívelavançarparaopróximoparsemresponderoatual.
7. Deve existir uma funcionalidade de ranking, exibindo os melhores jogadores e suas
pontuações.
8. A pontuação é obtida multiplicando a quantidade de quizzes respondidos pela
porcentagem de acerto.

## Não-Funcionais
1. ArmazeneosdadosemH2epreenchatodasastabelasnecessárias.
2. Inicie os dados de sua aplicação usando webscraping ou a partir da API pública
“http://www.omdbapi.com/” – levamos a sério que os dados sejam fidedignos.
3. ExploreosframeworksSpring:Web,Boot,Data,SecurityeCloud.
4. Linguagem:Java11ou17
5. Escrevatestesunitários(paravalidarasregrasdenegócio)edeintegração(para
validar a API). Cobertura de testes mínima: 80% dos métodos.
6. NãodeixedeadicionaradocumentaçãodaAPIcombasenoOpenAPI3.0.
7. Escolhaasoluçãodeautenticaçãoqueacharmaisinteressante.Criepelomenosdois
usuários/jogadores.
