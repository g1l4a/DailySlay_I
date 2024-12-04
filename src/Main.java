import org.antlr.v4.runtime.CharStreams;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) throws Exception {
    
        CharStream input = CharStreams.fromFileName("input.txt");

        ImpGrammarLexer lexer = new ImpGrammarLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ImpGrammarParser parser = new ImpGrammarParser(tokens);

        ParseTree tree = parser.program();

        Visitor visitor = new Visitor();
        ASTNode astRoot = visitor.visit(tree);
        
        ASTPrinter printer = new ASTPrinter();
        System.out.println("AST before semantic analysis:");
        printer.printAST(astRoot, ""); 
        System.out.println();

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyze(astRoot);
        System.out.println("Semantic analysis completed successfully.");

        System.out.println("AST after semantic analysis:");
        printer.printAST(astRoot, "");

        CodeGenerator codeGenerator = new CodeGenerator();

        String bytecode = codeGenerator.generate(astRoot);

        System.out.println("Generated Bytecode:");
        System.out.println(bytecode);

        // Write bytecode to a .j file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.j"))) {
            writer.write(bytecode);
            System.out.println("Bytecode written to output.j");
        } catch (IOException e) {
            System.err.println("Error writing bytecode to file: " + e.getMessage());
        } 
    }
}

