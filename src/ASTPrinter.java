public class ASTPrinter {

    public void printAST(ASTNode node, String indent) {
        if (node == null) return;

        // Print the class name of the current AST node
        System.out.println(indent + node.getClass().getSimpleName());

        // Dispatch based on the specific node type
        if (node instanceof ProgramNode) {
            printProgramNode((ProgramNode) node, indent);
        } else if (node instanceof VarDeclNode) {
            printVarDeclNode((VarDeclNode) node, indent);
        } else if (node instanceof TypeDeclNode) {
            printTypeDeclNode((TypeDeclNode) node, indent);
        } else if (node instanceof PrimitiveTypeNode) {
            printPrimitiveTypeNode((PrimitiveTypeNode) node, indent);
        } else if (node instanceof ArrayTypeNode) {
            printArrayTypeNode((ArrayTypeNode) node, indent);
        } else if (node instanceof RecordTypeNode) {
            printRecordTypeNode((RecordTypeNode) node, indent);
        } else if (node instanceof RoutineDeclNode) {
            printRoutineDeclNode((RoutineDeclNode) node, indent);
        } else if (node instanceof RoutineCallNode) {
            printRoutineCallNode((RoutineCallNode) node, indent);
        } else if (node instanceof AssignmentNode) {
            printAssignmentNode((AssignmentNode) node, indent);
        } else if (node instanceof IfNode) {
            printIfNode((IfNode) node, indent);
        } else if (node instanceof WhileLoopNode) {
            printWhileLoopNode((WhileLoopNode) node, indent);
        } else if (node instanceof ForLoopNode) {
            printForLoopNode((ForLoopNode) node, indent);
        } else if (node instanceof PrintNode) {
            printPrintNode((PrintNode) node, indent);
        } else if (node instanceof BreakNode) {
            printBreakNode((BreakNode) node, indent);
        } else if (node instanceof ReturnNode) {
            printReturnNode((ReturnNode) node, indent);
        } else if (node instanceof BinaryExpressionNode) {
            printBinaryExpressionNode((BinaryExpressionNode) node, indent);
        } else if (node instanceof IntLiteralNode) {
            printIntLiteralNode((IntLiteralNode) node, indent);
        } else if (node instanceof RealLiteralNode) {
            printRealLiteralNode((RealLiteralNode) node, indent);
        } else if (node instanceof CharLiteralNode) {
            printCharLiteralNode((CharLiteralNode) node, indent);
        } else if (node instanceof BooleanLiteralNode) {
            printBooleanLiteralNode((BooleanLiteralNode) node, indent);
        } else if (node instanceof VarRefNode) {
            printVarRefNode((VarRefNode) node, indent);
        } else if (node instanceof ArrayAccessNode) {
            printArrayAccessNode((ArrayAccessNode) node, indent);
        } else if (node instanceof FieldAccessNode) {
            printFieldAccessNode((FieldAccessNode) node, indent);
        } else if (node instanceof RangeNode) {
            printRangeNode((RangeNode) node, indent);
        } else if (node instanceof ParametersNode) {
            printParametersNode((ParametersNode) node, indent);
        } else if (node instanceof BodyNode) {
            printBodyNode((BodyNode) node, indent);
        } else if (node instanceof FieldAssignmentNode) {
            printFieldAssignmentNode((FieldAssignmentNode) node, indent);
        } else if (node instanceof RecordInitNode) {
            printRecordInitNode((RecordInitNode) node, indent);
        } else if (node instanceof ParameterDeclNode) {
            printParameterDeclNode((ParameterDeclNode) node, indent);
        }

    }

    private void printProgramNode(ProgramNode node, String indent) {
        for (ASTNode declaration : node.declarations) {
            printAST(declaration, indent + "  ");
        }
    }

    private void printVarDeclNode(VarDeclNode node, String indent) {
        System.out.println(indent + "varName: " + node.varName);
        System.out.println(indent + "varType:");
        printAST(node.varType, indent + "  ");
        System.out.println(indent + "expression:");
        printAST(node.expression, indent + "  ");
    }

    private void printTypeDeclNode(TypeDeclNode node, String indent) {
        System.out.println(indent + "typeName: " + node.typeName);
        System.out.println(indent + "type:");
        printAST(node.type, indent + "  ");
    }

    private void printPrimitiveTypeNode(PrimitiveTypeNode node, String indent) {
        System.out.println(indent + "type: " + node.type);
    }

    private void printArrayTypeNode(ArrayTypeNode node, String indent) {
        System.out.println(indent + "sizeExpression:");
        printAST(node.sizeExpression, indent + "  ");
        System.out.println(indent + "elementType:");
        printAST(node.elementType, indent + "  ");
    }

    private void printRecordTypeNode(RecordTypeNode node, String indent) {
        for (ASTNode field : node.fields) {
            printAST(field, indent + "  ");
        }
    }

    private void printRoutineDeclNode(RoutineDeclNode node, String indent) {
        System.out.println(indent + "routineName: " + node.routineName);
        System.out.println(indent + "parameters:");
        for (ASTNode param : node.parameters) {
            printAST(param, indent + "  ");
        }
        System.out.println(indent + "returnType:");
        printAST(node.returnType, indent + "  ");
        System.out.println(indent + "body:");
        for (ASTNode statement : node.body) {
            printAST(statement, indent + "  ");
        }
    }

    private void printRoutineCallNode(RoutineCallNode node, String indent) {
        System.out.println(indent + "routineName: " + node.routineName);
        for (ASTNode param : node.parameters) {
            printAST(param, indent + "  ");
        }
    }

    private void printAssignmentNode(AssignmentNode node, String indent) {
        System.out.println(indent + "left:");
        printAST(node.left, indent + "  ");
        System.out.println(indent + "right:");
        printAST(node.right, indent + "  ");
    }

    private void printIfNode(IfNode node, String indent) {
        System.out.println(indent + "condition:");
        printAST(node.condition, indent + "  ");
        System.out.println(indent + "thenBody:");
        for (ASTNode statement : node.thenBody) {
            printAST(statement, indent + "  ");
        }
        System.out.println(indent + "elseBody:");
        for (ASTNode statement : node.elseBody) {
            printAST(statement, indent + "  ");
        }
    }

    private void printWhileLoopNode(WhileLoopNode node, String indent) {
        System.out.println(indent + "condition:");
        printAST(node.condition, indent + "  ");
        System.out.println(indent + "body:");
        for (ASTNode statement : node.body) {
            printAST(statement, indent + "  ");
        }
    }

    private void printForLoopNode(ForLoopNode node, String indent) {
        System.out.println(indent + "iterator: ");
        printAST(node.iterator, indent + " ");
        if (node.isReversed)
        {
            System.out.println(indent + "reversed");
        }
        System.out.println(indent + "range:");
        printAST(node.range, indent + "  ");
        System.out.println(indent + "body:");
        for (ASTNode statement : node.body) {
            printAST(statement, indent + "  ");
        }
    }

    private void printPrintNode(PrintNode node, String indent) {
        System.out.println(indent + "expression:");
        printAST(node.expression, indent + "  ");
    }

    private void printBreakNode(BreakNode node, String indent) {
        System.out.println(indent + "Break");
    }

    private void printReturnNode(ReturnNode node, String indent) {
        System.out.println(indent + "expression:");
        printAST(node.expression, indent + "  ");
    }

    private void printBinaryExpressionNode(BinaryExpressionNode node, String indent) {
        System.out.println(indent + "left:");
        printAST(node.getLeft(), indent + "  ");
        System.out.println(indent + "operator: " + node.getOperator());
        System.out.println(indent + "right:");
        printAST(node.getRight(), indent + "  ");
    }

    private void printIntLiteralNode(IntLiteralNode node, String indent) {
        System.out.println(indent + "value: " + node.value);
    }

    private void printRealLiteralNode(RealLiteralNode node, String indent) {
        System.out.println(indent + "value: " + node.value);
    }

    private void printCharLiteralNode(CharLiteralNode node, String indent) {
        System.out.println(indent + "value: " + node.value);
    }

    private void printBooleanLiteralNode(BooleanLiteralNode node, String indent) {
        System.out.println(indent + "value: " + node.value);
    }

    private void printVarRefNode(VarRefNode node, String indent) {
        System.out.println(indent + "varName: " + node.varName);
    }

    private void printArrayAccessNode(ArrayAccessNode node, String indent) {
        System.out.println(indent + "array:");
        //printAST(node.array, indent + "  ");
        System.out.println(indent + "index:");
        printAST(node.index, indent + "  ");
    }

    private void printFieldAccessNode(FieldAccessNode node, String indent) {
        System.out.println(indent + "record:");
        printAST(node.record, indent + "  ");
        System.out.println(indent + "field: " + node.field);
    }

    private void printRangeNode(RangeNode node, String indent) {
        System.out.println(indent + "lowerBound:");
        printAST(node.lowerBound, indent + "  ");
        System.out.println(indent + "upperBound:");
        printAST(node.upperBound, indent + "  ");
    }

    private void printParametersNode(ParametersNode node, String indent) {
        for (ASTNode param : node.parameters) {
            printAST(param, indent + "  ");
        }
    }

    private void printParameterDeclNode(ParameterDeclNode node, String indent) {
        System.out.println("ParameterDeclarationNode");
        System.out.println(indent + "varName: " + node.varName);
        System.out.println(indent + "varType:");
        printAST(node.type, indent + "  ");
    }

    private void printBodyNode(BodyNode node, String indent) {
        for (SimpleDeclarationNode decl : node.simpleDeclarations) {
            printAST(decl, indent + "  ");
        }
        for (StatementNode statement : node.statements) {
            printAST(statement, indent + "  ");
        }
    }

    private void printFieldAssignmentNode(FieldAssignmentNode node, String indent) {
        System.out.println(indent + "varName: " + node.varName);
        System.out.println(indent + "expression:");
        printAST(node.expression, indent + "  ");
    }

    private void printRecordInitNode(RecordInitNode node, String indent) {
        System.out.println(indent + "variableName: " + node.variableName);
        System.out.println(indent + "type: " + node.type.typeName);
        for (FieldAssignmentNode fieldAssignment : node.fieldAssignments) {
            printFieldAssignmentNode(fieldAssignment, indent + "  ");
        }
    }
}
