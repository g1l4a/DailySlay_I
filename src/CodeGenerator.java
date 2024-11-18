public class CodeGenerator {
    private StringBuilder code;
    private int labelCounter; 
    private int nextVarIndex;
    private String currentBreakLabel;

    public CodeGenerator() {
        this.code = new StringBuilder();
        this.labelCounter = 0;
        this.nextVarIndex = 0;
    }

    private String generateLabel(String base) {
        return base + "_" + (labelCounter++);
    }

    private int allocateVarIndex() {
        int index = nextVarIndex++;
        return index;
    }

    public String generate(ASTNode root) {
        if (root instanceof ProgramNode) {
            visitProgram((ProgramNode) root);
        } else {
            throw new IllegalArgumentException("Root node is not a ProgramNode.");
        }
        return code.toString();
    }

    private void visitProgram(ProgramNode node) {
        code.append(".class public MyProgram\n");
        code.append(".super java/lang/Object\n");

        for (ASTNode decl : node.declarations) {
            visit(decl);
        }

        code.append(".method public static main([Ljava/lang/String;)V\n");
        code.append(".limit stack 10\n");
        code.append(".limit locals 10\n");

        code.append("return\n");
        code.append(".end method\n");
    }

    private void visit(ASTNode node) {
        if (node instanceof VarDeclNode) {
            // code.append("вызов visitVarDecl\n");
            visitVarDecl((VarDeclNode) node);
        } else if (node instanceof AssignmentNode) {
            // code.append("вызов visitAssignment\n");
            visitAssignment((AssignmentNode) node);
        } else if (node instanceof IfNode) {
            // code.append("вызов visitIf\n");
            visitIf((IfNode) node);
        } else if (node instanceof WhileLoopNode) {
            // code.append("вызов visitWhileLoop\n");
            visitWhileLoop((WhileLoopNode) node);
        } else if (node instanceof PrintNode) {
            // code.append("вызов visitPrint\n");
            visitPrint((PrintNode) node);
        } else if (node instanceof RoutineDeclNode) {
            // code.append("вызов visitRoutineDecl\n");
            visitRoutineDecl((RoutineDeclNode) node);
        } else if (node instanceof TypeDeclNode) {
            // code.append("вызов visitTypeDecl\n");
            visitTypeDecl((TypeDeclNode) node);
        } else if (node instanceof PrimitiveTypeNode) {
            // code.append("вызов visitPrimitiveType\n");
            visitPrimitiveType((PrimitiveTypeNode) node);
        } else if (node instanceof UserDefinedTypeNode) {
            // code.append("вызов visitUserDefinedType\n");
            visitUserDefinedType((UserDefinedTypeNode) node);
        } else if (node instanceof ArrayTypeNode) {
            // code.append("вызов visitArrayType\n");
            visitArrayType((ArrayTypeNode) node);
        } else if (node instanceof RecordTypeNode) {
            // code.append("вызов visitRecordType\n");
            visitRecordType((RecordTypeNode) node);
        } else if (node instanceof RoutineCallNode) {
            // code.append("вызов visitRoutineCall\n");
            visitRoutineCall((RoutineCallNode) node);
        } else if (node instanceof ForLoopNode) {
            // code.append("вызов visitForLoop\n");
            visitForLoop((ForLoopNode) node);
        } else if (node instanceof BreakNode) {
            // code.append("вызов visitBreak\n");
            visitBreak((BreakNode) node);
        } else if (node instanceof ReturnNode) {
            // code.append("вызов visitReturn\n");
            visitReturn((ReturnNode) node);
        }
         else if (node instanceof ArrayAccessNode) {
            // code.append("вызов visitArrayAccess\n");
            visitArrayAccess((ArrayAccessNode) node);
        } else if (node instanceof BinaryOpNode) {
            // code.append("вызов visitBinaryOp\n");
            visitBinaryOp((BinaryOpNode) node);
        } else if (node instanceof FieldAccessNode) {
            // code.append("вызов visitFieldAccess\n");
            visitFieldAccess((FieldAccessNode) node);
        } else if (node instanceof RangeNode) {
            // code.append("вызов visitRange\n");
            visitRange((RangeNode) node);
        } else if (node instanceof ParametersNode) {
            // code.append("вызов visitParameters\n");
            visitParameters((ParametersNode) node);
        } else if (node instanceof ParameterDeclNode) {
            // code.append("вызов visitParameterDecl\n");
            visitParameterDecl((ParameterDeclNode) node);
        } else if (node instanceof BodyNode) {
            // code.append("вызов visitBody\n");
            visitBody((BodyNode) node);
        } else if (node instanceof FieldAssignmentNode) {
            // code.append("вызов visitFieldAssignment\n");
            visitFieldAssignment((FieldAssignmentNode) node);
        } else if (node instanceof RecordInitNode) {
            // code.append("вызов visitRecordInit\n");
            visitRecordInit((RecordInitNode) node);
        } else if (node instanceof ModifiablePrimaryNode) {
            // code.append("вызов visitModifiablePrimary\n");
            visitModifiablePrimary((ModifiablePrimaryNode) node);

        } else if (node instanceof ExpressionNode) {
            node = (ExpressionNode) node;
            if (node instanceof BinaryExpressionNode) {
                // code.append("вызов visitBinaryExpression\n");
                visitBinaryExpression((BinaryExpressionNode) node);
            } else if (node instanceof IntLiteralNode) {
                // code.append("вызов visitIntLiteral\n");
                visitIntLiteral((IntLiteralNode) node);
            } else if (node instanceof RealLiteralNode) {
                // code.append("вызов visitRealLiteral\n");
                visitRealLiteral((RealLiteralNode) node);
            } else if (node instanceof BooleanLiteralNode) {
                // code.append("вызов visitBooleanLiteral\n");
                visitBooleanLiteral((BooleanLiteralNode) node);
            } else if (node instanceof CharLiteralNode) {
                // code.append("вызов visitCharLiteral\n");
                visitCharLiteral((CharLiteralNode) node);
            } else if (node instanceof VarRefNode) {
                // code.append("вызов visitVarRef\n");
                visitVarRef((VarRefNode) node);
            }
        } else {
            code.append("вызов того, чего нет\n");
            throw new IllegalArgumentException("Unsupported AST node: " + node.getClass().getSimpleName());
        }
    }

    private void visitVarDecl(VarDeclNode node) {
        int index = allocateVarIndex(); 
        node.varIndex = index; 

        if (node.expression != null) {
            visit(node.expression);
            code.append("istore ");
            code.append(index).append("\n");
        } else {
            code.append("iconst_0\n");
            code.append("istore ");
            code.append(index).append("\n");
        }
    }

    private void visitTypeDecl(TypeDeclNode node) {
        if (node.type != null) {
            visit(node.type);
        }
    }

    private void visitPrimitiveType(PrimitiveTypeNode node) {
        code.append("; Primitive type: ").append(node.type).append("\n");
    }

    private void visitUserDefinedType(UserDefinedTypeNode node) {
        code.append("; User-defined type: ").append(node.typeName).append("\n");
    }

    private void visitArrayType(ArrayTypeNode node) {
        code.append("; Array type\n");
        visit(node.sizeExpression);

        if (node.elementType != null) {
            visit(node.elementType);
        }
    }

    private void visitRecordType(RecordTypeNode node) {
        code.append("; Record type\n");

        for (ASTNode field : node.fields) {
            visit(field);
        }
    }

    private void visitAssignment(AssignmentNode node) {
        visit(node.right);
        int index = ((VarRefNode) node.left).varIndex;
        // code.append("\n").append("; Parameter ").append(node.left).append(" has index ").append(index).append(" in AssignmentNode ").append("\n");
        code.append("istore ").append(index).append("\n");
    }

    private void visitVarRef(VarRefNode node) {
        int index = node.varIndex;
        // code.append("\n").append("; Parameter ").append(node).append(" with name ").append(node.varName).append(" has index ").append(index).append(" in VarRefNode").append("\n");
        code.append("iconst_0\nistore ").append(index).append("\n");
        code.append("iload ").append(index).append("\n");
    }

    private void visitRoutineDecl(RoutineDeclNode node) {
        code.append(".method public static ").append(node.routineName).append("()V\n");
        code.append(".limit stack 10\n");
        code.append(".limit locals ").append(nextVarIndex + node.parameters.size() + 1).append("\n");

        for (ASTNode param : node.parameters) {
            visitParameterDecl((ParameterDeclNode) param);
        }
    
        for (ASTNode statement : node.body) {
            visit(statement);
        }

        code.append("return\n");
        code.append(".end method\n");
    }

    private void visitRoutineCall(RoutineCallNode node) {
        for (ASTNode param : node.parameters) {
            visit(param);
        }
        code.append("invoke static MyProgram/").append(node.routineName).append("(I)V\n");
    }

    private void visitForLoop(ForLoopNode node) {
        String startLabel = generateLabel("L" + (labelCounter++));
        String endLabel = generateLabel("L" + (labelCounter++));
        currentBreakLabel = endLabel;

        visit(node.range);
        code.append(startLabel).append(":\n");

        for (ASTNode stmt : node.body) {
            visit(stmt);
        }

        code.append("goto ").append(startLabel).append("\n");
        code.append(endLabel).append(":\n");
    }

    private void visitBreak(BreakNode node) {
        code.append("goto ").append(currentBreakLabel).append("\n");
    }

    private void visitReturn(ReturnNode node) {
        if (node.expression != null) {
            visit(node.expression);
        }
        code.append("return\n");
    }

    private void visitRealLiteral(RealLiteralNode node) {
        code.append("ldc ").append(node.value).append("\n");
    }

    private void visitCharLiteral(CharLiteralNode node) {
        code.append("ldc ").append((int) node.value).append("\n");
    }

    private void visitBooleanLiteral(BooleanLiteralNode node) {
        code.append("ldc ").append(node.value ? 1 : 0).append("\n");
    }

    private void visitArrayAccess(ArrayAccessNode node) {
        visit(node.index);

        code.append("aload ").append(node.array).append("\n");
        code.append("iaload\n");
    }

    private void visitBinaryOp(BinaryOpNode node) {
        visit(node.left);
        visit(node.right);

        switch (node.operator) {
            case "+":
                code.append("iadd\n");
                break;
            case "-":
                code.append("isub\n");
                break;
            case "*":
                code.append("imul\n");
                break;
            case "/":
                code.append("idiv\n");
                break;
            default:
                throw new RuntimeException("Unsupported binary operator: " + node.operator);
        }
    }

    private void visitFieldAccess(FieldAccessNode node) {
        visit(node.record);
        code.append("getfield ").append(node.field).append("\n");
    }

    private void visitRange(RangeNode node) {
        visit(node.lowerBound);
        visit(node.upperBound);
    }

    private void visitParameters(ParametersNode node) {
        for (ASTNode param : node.parameters) {
            visit(param);
        }
    }

    private void visitParameterDecl(ParameterDeclNode node) {
        // visit(node.type);
        int index = allocateVarIndex();
        node.varIndex = index;

        // code.append("iconst_0\n"); // Загружаем константу 0
        // code.append("istore ").append(index).append("\n");
        // code.append("\n").append("; Parameter ").append(node.varName).append(" has index ").append(index).append(" in ParameterDeclNode ").append("\n");
    }

    private void visitBody(BodyNode node) {
        for (SimpleDeclarationNode decl : node.simpleDeclarations) {
            visit(decl);
        }
        for (StatementNode stmt : node.statements) {
            visit(stmt);
        }
    }

    private void visitFieldAssignment(FieldAssignmentNode node) {
        visit(node.expression);
        code.append("putfield ").append(node.varName).append("\n");
    }

    private void visitRecordInit(RecordInitNode node) {
        visit(node.type);
        for (FieldAssignmentNode field : node.fieldAssignments) {
            visit(field);
        }
    }

    private void visitModifiablePrimary(ModifiablePrimaryNode node) {
        if (node.expression != null) {
            visit(node.expression);
        } else if (node.accessVarName != null) {
            code.append("getfield ").append(node.varName).append(".").append(node.accessVarName).append("\n");
        } else {
            code.append("load ").append(node.varName).append("\n");
        }
    }

    private void visitIf(IfNode node) {
        String elseLabel = generateLabel("else");
        String endLabel = generateLabel("endif");

        visit(node.condition);
        code.append("ifeq ").append(elseLabel).append("\n");
        for (ASTNode stmt : node.thenBody) {
            visit(stmt);
        }
        code.append("goto ").append(endLabel).append("\n");
        code.append(elseLabel).append(":\n");
        for (ASTNode stmt : node.elseBody) {
            visit(stmt);
        }
        code.append(endLabel).append(":\n");
    }

    private void visitWhileLoop(WhileLoopNode node) {
        String startLabel = generateLabel("while_start");
        String endLabel = generateLabel("while_end");

        code.append(startLabel).append(":\n");
        visit(node.condition);
        code.append("ifeq ").append(endLabel).append("\n");
        for (ASTNode stmt : node.body) {
            visit(stmt);
        }
        code.append("goto ").append(startLabel).append("\n");
        code.append(endLabel).append(":\n");
    }

    private void visitPrint(PrintNode node) {
        visit(node.expression);
        code.append("getstatic java/lang/System/out Ljava/io/PrintStream;\n");
        // code.append("dup\n");
        // code.append("swap\n");
        code.append("invokevirtual java/io/PrintStream/println(I)V\n");
    }

    private void visitBinaryExpression(BinaryExpressionNode node) {
        visit(node.getLeft());
        visit(node.getRight());
        switch (node.getOperator()) {
            case "+":
                code.append("iadd\n");
                break;
            case "-":
                code.append("isub\n");
                break;
            case "*":
                code.append("imul\n");
                break;
            case "/":
                code.append("idiv\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + node.getOperator());
        }
    }

    private void visitIntLiteral(IntLiteralNode node) {
        int value = node.value;
        if (value >= -128 && value <= 127) {
            code.append("bipush ").append(value).append("\n");
        } else {
            code.append("ldc ").append(value).append("\n");
        }
    }
}
