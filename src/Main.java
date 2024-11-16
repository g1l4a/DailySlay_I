import org.antlr.v4.runtime.CharStreams;
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
    }
}

