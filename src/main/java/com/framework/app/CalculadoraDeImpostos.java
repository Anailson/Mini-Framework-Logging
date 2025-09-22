package com.framework.app;

import com.framework.logging.ConsoleLoggerCreator;
import com.framework.logging.PatternConsoleLoggerCreator;


import com.framework.logging.Level;
import com.framework.logging.Logger;

import com.framework.logging.RollingTxtLoggerCreator;
import com.framework.logging.TxtFileLoggerCreator;
import com.framework.logging.XmlFileLoggerCreator;

public class CalculadoraDeImpostos {

    private final Logger logger;

    public CalculadoraDeImpostos(Logger logger) {
        this.logger = logger;
    }

    public double calcular(double valor) {
        double imposto = valor * 0.17;
        logger.info("Imposto calculado para o valor R$" + valor + " = R$" + String.format("%.2f", imposto));
        return imposto;
    }

    public static void main(String[] args) throws Exception {
        // 1) Console padrão (texto + timestamp + nível)
        Logger console = (Logger) new ConsoleLoggerCreator().createLogger();
        new CalculadoraDeImpostos(console).calcular(1000.0);

        // 2) Console com Pattern custom e minLevel=INFO
        Logger patternConsole = (Logger) new PatternConsoleLoggerCreator(
                "[%d{dd/MM/yyyy HH:mm:ss}] [%level] %msg", Level.INFO
        ).createLogger();
        new CalculadoraDeImpostos(patternConsole).calcular(2500.0);

        // 3) TXT com pattern e minLevel=WARN (INFO será filtrado)
        Logger txt = new TxtFileLoggerCreator("logs/app.txt", Level.WARN).createLogger();
        txt.warn("Aviso que vai para o arquivo");
        new CalculadoraDeImpostos(txt).calcular(500.0); // INFO (filtrado, não grava)

        // 4) XML em arquivo
        Logger xml = (Logger) new XmlFileLoggerCreator("logs/app.xml").createLogger();
        xml.error("Erro crítico em processo X");

        // 5) Rolling por tamanho (ex.: 5 KB), minLevel=DEBUG
        Logger rolling = new RollingTxtLoggerCreator("logs/rolling.txt", 5 * 1024, Level.DEBUG).createLogger();
        for (int i = 0; i < 500; i++) {
            rolling.debug("Linha " + i + " para forçar rotação");
        }

        // Fechamento de recursos (se necessário)
        if (console instanceof AutoCloseable c) c.close();
        if (patternConsole instanceof AutoCloseable c2) c2.close();
        if (txt instanceof AutoCloseable c3) c3.close();
        if (xml instanceof AutoCloseable c4) c4.close();
        if (rolling instanceof AutoCloseable c5) c5.close();
    }
}
