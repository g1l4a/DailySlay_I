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
            System.out.println(indent + "  BreakStatement");
        } else if (node instanceof ReturnNode) {
            printReturnNode((ReturnNode) node, indent);
        } else if (node instanceof BinaryOpNode) {
            printBinaryOpNode((BinaryOpNode) node, indent);
        } else if (node instanceof IntLiteralNode) {
            printIntLiteralNode((IntLiteralNode) node, indent);
        } else if (node instanceof RealLiteralNode) {
            printRealLiteralNode((RealLiteralNode) node, indent);
        } else if (node instanceof BooleanLiteralNode) {
            printBooleanLiteralNode((BooleanLiteralNode) node, indent);
        } else if (node instanceof VarRefNode) {
            printVarRefNode((VarRefNode) node, indent);
        } else if (node instanceof ArrayAccessNode) {
            printArrayAccessNode((ArrayAccessNode) node, indent);
        } else if (node instanceof FieldAccessNode) {
            printFieldAccessNode((FieldAccessNode) node, indent);
        } else if (node instanceof ParametersNode) {
            printParametersNode((ParametersNode) node, indent);
        } else if (node instanceof ParameterDeclNode) {
            printParameterDeclNode((ParameterDeclNode) node, indent);
        } else if (node instanceof BodyNode) {
            printBodyNode((BodyNode) node, indent);
        } else if (node instanceof FieldAssignmentNode) {
            printFieldAssignmentNode((FieldAssignmentNode) node, indent);
        } else if (node instanceof RecordInitNode) {
            printRecordInitNode((RecordInitNode) node, indent);
        }
    }

    private void printProgramNode(ProgramNode node, String indent) {
        System.out.println(indent + "  Declarations: ");
        for (ASTNode decl : node.declarations) {
            printAST(decl, indent + "    ");
        }
    }

    private void printVarDeclNode(VarDeclNode node, String indent) {
        System.out.println(indent + "  VarName: " + node.varName);
        if (node.varType != null) {
            System.out.println(indent + "  VarType: ");
            printAST(node.varType, indent + "    ");
        }
        if (node.expression != null) {
            System.out.println(indent + "  Expression: ");
            printAST(node.expression, indent + "    ");
        }
    }

    private void printTypeDeclNode(TypeDeclNode node, String indent) {
        System.out.println(indent + "  TypeName: " + node.typeName);
        System.out.println(indent + "  Type: ");
        printAST(node.type, indent + "    ");
    }

    private void printPrimitiveTypeNode(PrimitiveTypeNode node, String indent) {
        System.out.println(indent + "  Type: " + node.type);
    }

    private void printArrayTypeNode(ArrayTypeNode node, String indent) {
        System.out.println(indent + "  SizeExpression: ");
        printAST(node.sizeExpression, indent + "    ");
        System.out.println(indent + "  ElementType: ");
        printAST(node.elementType, indent + "    ");
    }

    private void printRecordTypeNode(RecordTypeNode node, String indent) {
        System.out.println(indent + "  Fields: ");
        for (ASTNode field : node.fields) {
            printAST(field, indent + "    ");
        }
    }

    private void printRoutineDeclNode(RoutineDeclNode node, String indent) {
        System.out.println(indent + "  RoutineName: " + node.routineName);
        if (node.parameters.isEmpty()) {
            System.out.println(indent + "  No Parameters: ");
        } else {
            System.out.println(indent + "  Parameters: ");
            for (ASTNode param : node.parameters) {
                printAST(param, indent + "    ");
            }
        }
        if (node.returnType != null) {
            System.out.println(indent + "  ReturnType: ");
            printAST(node.returnType, indent + "    ");
        } else {
            System.out.println(indent + "  ReturnType: void");
        }
        System.out.println(indent + "  Body: ");
        for (ASTNode bodyElement : node.body) {
            printAST(bodyElement, indent + "    ");
        }
    }

    private void printRoutineCallNode(RoutineCallNode node, String indent) {
        System.out.println(indent + "  RoutineName: " + node.routineName);
        System.out.println(indent + "  Parameters: ");
        for (ASTNode param : node.parameters) {
            printAST(param, indent + "    ");
        }
    }

    private void printAssignmentNode(AssignmentNode node, String indent) {
        System.out.println(indent + "  Left: ");
        printAST(node.left, indent + "    ");
        System.out.println(indent + "  Right: ");
        if (node.right instanceof BinaryOpNode) {
            BinaryOpNode binaryOp = (BinaryOpNode) node.right;
            System.out.println(indent + "  Binary Operation: ");
            System.out.println(indent + "    Operator: " + binaryOp.operator);
            System.out.println(indent + "    Left Operand: ");
            printAST(binaryOp.left, indent + "      ");
            System.out.println(indent + "    Right Operand: ");
            printAST(binaryOp.right, indent + "      ");
        } else {
            printAST(node.right, indent + "    ");
        }
    }

    private void printIfNode(IfNode node, String indent) {
        System.out.println(indent + "  Condition: ");
        printAST(node.condition, indent + "    ");
        System.out.println(indent + "  ThenBody: ");
        for (ASTNode thenBodyElement : node.thenBody) {
            printAST(thenBodyElement, indent + "    ");
        }
        System.out.println(indent + "  ElseBody: ");
        for (ASTNode elseBodyElement : node.elseBody) {
            printAST(elseBodyElement, indent + "    ");
        }
    }

    private void printWhileLoopNode(WhileLoopNode node, String indent) {
        System.out.println(indent + "  Condition: ");
        printAST(node.condition, indent + "    ");
        System.out.println(indent + "  Body: ");
        for (ASTNode bodyElement : node.body) {
            printAST(bodyElement, indent + "    ");
        }
    }

    private void printForLoopNode(ForLoopNode node, String indent) {
        System.out.println(indent + "  Iterator: " + node.iterator);
        System.out.println(indent + "  Range: ");
        printAST(node.range, indent + "    ");
        System.out.println(indent + "  Body: ");
        for (ASTNode bodyElement : node.body) {
            printAST(bodyElement, indent + "    ");
        }
    }

    private void printPrintNode(PrintNode node, String indent) {
        System.out.println(indent + "  Expression: ");
        printAST(node.expression, indent + "    ");
    }

    private void printReturnNode(ReturnNode node, String indent) {
        System.out.println(indent + "  ReturnStatement");
        if (node.expression != null) {
            System.out.println(indent + "  Expression: ");
            printAST(node.expression, indent + "    ");
        }
    }

    private void printBinaryOpNode(BinaryOpNode node, String indent) {
        System.out.println(indent + "  Operator: " + node.operator);
        System.out.println(indent + "  Left: ");
        printAST(node.left, indent + "    ");
        System.out.println(indent + "  Right: ");
        printAST(node.right, indent + "    ");
    }

    private void printIntLiteralNode(IntLiteralNode node, String indent) {
        System.out.println(indent + "  IntValue: " + node.value);
    }

    private void printRealLiteralNode(RealLiteralNode node, String indent) {
        System.out.println(indent + "  RealValue: " + node.value);
    }

    private void printBooleanLiteralNode(BooleanLiteralNode node, String indent) {
        System.out.println(indent + "  BooleanValue: " + node.value);
    }

    private void printVarRefNode(VarRefNode node, String indent) {
        System.out.println(indent + "  VarName: " + node.varName);
    }

    private void printArrayAccessNode(ArrayAccessNode node, String indent) {
        System.out.println(indent + "  Array: ");
        printAST(node.array, indent + "    ");
        System.out.println(indent + "  Index: ");
        printAST(node.index, indent + "    ");
    }

    private void printFieldAccessNode(FieldAccessNode node, String indent) {
        System.out.println(indent + "  Record: ");
        printAST(node.record, indent + "    ");
        System.out.println(indent + "  Field: " + node.field);
    }

    private void printParametersNode(ParametersNode node, String indent) {
        System.out.println(indent + "  Parameters: ");
        for (ASTNode param : node.parameters) {
            printAST(param, indent + "    ");
        }
    }

    private void printParameterDeclNode(ParameterDeclNode node, String indent) {
        System.out.println(indent + "  VarName: " + node.varName);
        System.out.println(indent + "  Type: ");
        printAST(node.type, indent + "    ");
    }

    private void printBodyNode(BodyNode node, String indent) {
        System.out.println(indent + "  SimpleDeclarations: ");
        for (SimpleDeclarationNode decl : node.simpleDeclarations) {
            printAST(decl, indent + "    ");
        }
        System.out.println(indent + "  Statements: ");
        for (StatementNode stmt : node.statements) {
            printAST(stmt, indent + "    ");
        }
    }

    private void printFieldAssignmentNode(FieldAssignmentNode node, String indent) {
        System.out.println(indent + "  Field: " + node.varName);
        System.out.println(indent + "  Expression: ");
        printAST(node.expression, indent + "    ");
    }

    private void printRecordInitNode(RecordInitNode node, String indent) {
        System.out.println(indent + "RecordInit:");
        System.out.println(indent + "  Variable Name: " + node.variableName);
        System.out.println(indent + "  Type: " + node.type.typeName);
    
        System.out.println(indent + "  Field Assignments:");
        for (FieldAssignmentNode fieldAssignment : node.fieldAssignments) {
            System.out.println(indent + "    Field: " + fieldAssignment.varName);
            printAST(fieldAssignment.expression, indent + "      ");
        }
    }
    
}
