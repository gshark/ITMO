package ru.ifmo.ctddev.shovkoplias.parsers.lab3;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import ru.ifmo.ctddev.shovkoplias.parsers.lab3.gen.ru.ifmo.ctddev.shovkoplias.parsers.lab3.PascalLexer;
import ru.ifmo.ctddev.shovkoplias.parsers.lab3.gen.ru.ifmo.ctddev.shovkoplias.parsers.lab3.PascalParser;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static String EXAMPLES_PREFIX = "src/examples/";
    private static int examplesCount = 5;

    public static void main(String[] args) {
        for (int i = 1; i <= examplesCount; i++) {
            try {
                ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(new File(EXAMPLES_PREFIX + "example" + i + ".pas")));
                PascalLexer lexer = new PascalLexer(in);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                PascalParser parser = new PascalParser(tokens);
                PascalParser.EvalContext result = parser.eval();
                String code = result.code;
                try (PrintWriter out = new PrintWriter(EXAMPLES_PREFIX + "example" + i + ".dpr")) {
                    out.println(code);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}