import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CodeGenerator {
    private StringBuilder jasminCode;

    public CodeGenerator() {
        jasminCode = new StringBuilder();
    }

    public void generateProgram(ProgramNode programNode) {
        jasminCode.append(".class public MyProgram\n");
        jasminCode.append(".super java/lang/Object\n");

        jasminCode.append(".method public static main([Ljava/lang/String;)V\n");
        jasminCode.append(".limit stack 10\n");
        jasminCode.append(".limit locals 10\n");

        for (ASTNode node : programNode.getChildren()) {
            generateNode(node);
        }

        jasminCode.append("return\n");
        jasminCode.append(".end method\n");
    }

    public void generateNode(ASTNode node) {
        if (node instanceof VarDeclNode) {
            generateVarDecl((VarDeclNode) node);
        } else if (node instanceof RoutineDeclNode) {
            generateRoutineDecl((RoutineDeclNode) node);
        } else if (node instanceof RoutineCallNode) {
            generateRoutineCall((RoutineCallNode) node);
        } else if (node instanceof PrintNode) {
            generatePrint((PrintNode) node);
        } else if (node instanceof IfNode) {
            generateIfLoop((IfNode) node);
        } else if (node instanceof WhileLoopNode) {
            generateWhileLoop((WhileLoopNode) node);
        } else if (node instanceof ForLoopNode) {
            generateForLoop((ForLoopNode) node);
        } else if (node instanceof AssignmentNode) {
            generateAssignment((AssignmentNode) node);
        } else if (node instanceof ReturnNode) {
            generateReturn((ReturnNode) node);
        }
    }

    private void generateVarDecl(VarDeclNode node) {
        if (node.expression != null) {
            generateNode(node.expression);
            jasminCode.append("astore_").append(node.varName).append("\n");
        }
    }

    private void generateRoutineDecl(RoutineDeclNode node) {
        jasminCode.append(".method public static ").append(node.routineName).append("()V\n");
        jasminCode.append(".limit stack 10\n");
        jasminCode.append(".limit locals 10\n");

        for (ASTNode stmt : node.body) {
            generateNode(stmt);
        }

        jasminCode.append("return\n");
        jasminCode.append(".end method\n");
    }

    private void generateRoutineCall(RoutineCallNode node) {
        for (ASTNode param : node.parameters) {
            generateNode(param);
        }
        jasminCode.append("invokestatic MyProgram/").append(node.routineName).append("()V\n");
    }

    private void generatePrint(PrintNode node) {
        jasminCode.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
        generateNode(node.expression);
        jasminCode.append("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
    }

    private void generateIfLoop(IfNode node) {
        String elseLabel = generateLabel("else");
        String endLabel = generateLabel("end");

        generateNode(node.condition);
        jasminCode.append("ifeq ").append(elseLabel).append("\n");

        for (ASTNode stmt : node.thenBody) {
            generateNode(stmt);
        }

        jasminCode.append("goto ").append(endLabel).append("\n");
        jasminCode.append(elseLabel).append(":\n");

        for (ASTNode stmt : node.elseBody) {
            generateNode(stmt);
        }

        jasminCode.append(endLabel).append(":\n");
    }

    private void generateWhileLoop(WhileLoopNode node) {
        String startLabel = generateLabel("while_start");
        String endLabel = generateLabel("while_end");

        jasminCode.append(startLabel).append(":\n");
        generateNode(node.condition);
        jasminCode.append("ifeq ").append(endLabel).append("\n");

        for (ASTNode stmt : node.body) {
            generateNode(stmt);
        }

        jasminCode.append("goto ").append(startLabel).append("\n");
        jasminCode.append(endLabel).append(":\n");
    }

    private void generateForLoop(ForLoopNode node) {
        String startLabel = generateLabel("for_start");
        String endLabel = generateLabel("for_end");

        generateNode(node.range);
        jasminCode.append("dup\n");

        jasminCode.append("iconst_0\n");
        jasminCode.append("istore_1\n");
        
        jasminCode.append("iconst_1\n"); 
        jasminCode.append("istore_2\n");

        jasminCode.append(startLabel).append(":\n");

        
        jasminCode.append("iload_1\n");
        jasminCode.append("iload_2\n"); 
        jasminCode.append("if_icmpge ").append(endLabel).append("\n");

        for (ASTNode stmt : node.body) {
            generateNode(stmt);
        }

        jasminCode.append("goto ").append(startLabel).append("\n");
        jasminCode.append(endLabel).append(":\n");
    }

    private void generateAssignment(AssignmentNode node) {
        generateNode(node.right);
        jasminCode.append("astore_").append(((VarDeclNode) node.left).varName).append("\n");
    }

    private void generateReturn(ReturnNode node) {
        if (node.expression != null) {
            generateNode(node.expression);
        }
        jasminCode.append("return\n");
    }

    private String generateLabel(String prefix) {
        return prefix + "_" + System.nanoTime();
    }

    public void writeToFile(String fileName) throws IOException {
        Path path = Paths.get(fileName + ".j");
        Files.write(path, jasminCode.toString().getBytes());
    }
}
