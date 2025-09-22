# Mini Framework de Logging (Factory Method + Strategy)

## Visão geral
Framework didático de logging com `Logger`, `Formatter` e `Appender`, usando Factory Method para criar combinações prontas (console, txt, xml).

## Estrutura
- `com.framework.logging`: núcleo (interfaces, formatadores, appenders, creators)
- `com.framework.app`: exemplo de uso (`CalculadoraDeImpostos`)

## Objetivo

Criamos um mini-framework de **logging** desacoplado e extensível. Ele permite:

- Incluir **timestamp** antes de cada mensagem.
- Registrar em **múltiplos destinos** (console, arquivo TXT, XML, “rolling” por tamanho).
- Usar **formatos diferentes** (texto padrão, XML, pattern configurável).
- Filtrar por **nível de log** (`DEBUG`, `INFO`, `WARN`, `ERROR`).

---

## Padrões de Projeto usados

- **Strategy**: dois pontos de variação independentes
    - `Formatter` decide **como** a mensagem é formatada.
    - `Appender` decide **onde** a mensagem é gravada.

- **Factory Method**: `LoggerCreator` cria combinações prontas de `Formatter` + `Appender(s)` para facilitar o uso.

- **Composition**: `DefaultLogger` recebe 1 `Formatter` e uma lista de `Appender`s (permite várias saídas ao mesmo tempo).

---

## Principais componentes

- **Logger (interface)**  
  Contrato para logar mensagens. Mantém `log(String)` (compatibilidade) e `log(Level, String)`.  
  Inclui helpers: `info(...)`, `warn(...)`, `error(...)`, `debug(...)`.

- **Level (enum)**  
  Define os níveis de log e permite filtragem mínima no `DefaultLogger`.

- **Formatters (Strategy – “como”)**
    - `TimestampTextFormatter`: `[yyyy-MM-dd HH:mm:ss] [LEVEL] mensagem`
    - `TimestampXmlFormatter`: `<log timestamp="..." level="..."><message>...</message></log>`
    - `PatternFormatter`: estilo Logback/Log4j com tokens:
        - `%d{pattern-java-time}` (ex.: `%d{dd/MM/yyyy HH:mm:ss}`)
        - `%level`
        - `%msg`

- **Appenders (Strategy – “onde”)**
    - `ConsoleAppender`: escreve em `System.out`.
    - `FileAppender`: escreve/append em arquivo (cria diretórios e arquivo se não existirem).
    - `RollingFileAppender` (opcional): faz rotação por tamanho (renomeia e recria o arquivo).

- **DefaultLogger**  
  Orquestra `Formatter` + vários `Appender`s e aplica filtro de nível mínimo.

- **Factories (Factory Method)**
    - `ConsoleLoggerCreator`
    - `TxtFileLoggerCreator` (aceita `minLevel`)
    - `XmlFileLoggerCreator`
    - `PatternConsoleLoggerCreator`
    - `RollingTxtLoggerCreator` (usa `RollingFileAppender`)

---

## Fluxo em tempo de execução

1. O cliente (ex.: `CalculadoraDeImpostos`) recebe um `Logger` no construtor.
2. Ao chamar `logger.info("...")`, o `DefaultLogger`:
    - Verifica o **nível mínimo** (ex.: ignora `INFO` se `minLevel` for `WARN`).
    - Formata a mensagem via `Formatter`.
    - Envia a string formatada para **cada** `Appender` (console/arquivo/etc.).  
