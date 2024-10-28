public class ASTPrinter {

    public void printAST(ASTNode node, String indent) {
        if (node == null) return;

        System.out.println(indent + node.getClass().getSimpleName());

        if (node instanceof ProgramNode) {
            ProgramNode programNode = (ProgramNode) node;
            System.out.println(indent + "  Declarations: ");
            for (ASTNode decl : programNode.declarations) {
                printAST(decl, indent + "    ");
            }
        } else if (node instanceof VarDeclNode) {
            VarDeclNode varDecl = (VarDeclNode) node;
            System.out.println(indent + "  VarName: " + varDecl.varName);
            if (varDecl.varType != null) {
                System.out.println(indent + "  VarType: ");
                printAST(varDecl.varType, indent + "    ");
            }
            if (varDecl.expression != null) {
                System.out.println(indent + "  Expression: ");
                printAST(varDecl.expression, indent + "    ");
            }
        } else if (node instanceof TypeDeclNode) {
            TypeDeclNode typeDecl = (TypeDeclNode) node;
            System.out.println(indent + "  TypeName: " + typeDecl.typeName);
            System.out.println(indent + "  Type: ");
            printAST(typeDecl.type, indent + "    ");
        } else if (node instanceof PrimitiveTypeNode) {
            PrimitiveTypeNode primitiveType = (PrimitiveTypeNode) node;
            System.out.println(indent + "  Type: " + primitiveType.type);
        } else if (node instanceof ArrayTypeNode) {
            ArrayTypeNode arrayType = (ArrayTypeNode) node;
            System.out.println(indent + "  SizeExpression: ");
            printAST(arrayType.sizeExpression, indent + "    ");
            System.out.println(indent + "  ElementType: ");
            printAST(arrayType.elementType, indent + "    ");
        } else if (node instanceof RecordTypeNode) {
            RecordTypeNode recordType = (RecordTypeNode) node;
            System.out.println(indent + "  Fields: ");
            for (ASTNode field : recordType.fields) {
                printAST(field, indent + "    ");
            }
        } else if (node instanceof RoutineDeclNode) {
            RoutineDeclNode routineDecl = (RoutineDeclNode) node;
            System.out.println(indent + "  RoutineName: " + routineDecl.routineName);
            System.out.println(indent + "  Parameters: ");
            if (routineDecl.parameters.isEmpty()) {
                System.out.println(indent + "  No Parameters: ");
            } else {
                for (ASTNode param : routineDecl.parameters) {
                    printAST(param, indent + "    ");
                }
            }
            if (routineDecl.returnType != null) {
                System.out.println(indent + "  ReturnType: ");
                printAST(routineDecl.returnType, indent + "    ");
            } else {
                System.out.println(indent + "  ReturnType: void");
            }
            System.out.println(indent + "  Body: ");
            for (ASTNode bodyElement : routineDecl.body) {
                printAST(bodyElement, indent + "    ");
            }
        } else if (node instanceof RoutineCallNode) {
            RoutineCallNode routineCall = (RoutineCallNode) node;
            System.out.println(indent + "  RoutineName: " + routineCall.routineName);
            System.out.println(indent + "  Parameters: ");
            for (ASTNode param : routineCall.parameters) {
                printAST(param, indent + "    ");
            }
        } else if (node instanceof AssignmentNode) {
            AssignmentNode assignment = (AssignmentNode) node;
            System.out.println(indent + "  Left: ");
            printAST(assignment.left, indent + "    ");
            System.out.println(indent + "  Right: ");
            if (assignment.right instanceof BinaryOpNode) {
                BinaryOpNode binaryOp = (BinaryOpNode) assignment.right;
                System.out.println(indent + "  Binary Operation: ");
                System.out.println(indent + "    Operator: " + binaryOp.operator);
                System.out.println(indent + "    Left Operand: ");
                printAST(binaryOp.left, indent + "      ");
                System.out.println(indent + "    Right Operand: ");
                printAST(binaryOp.right, indent + "      ");
            } else {
                printAST(assignment.right, indent + "    ");
            }
        } else if (node instanceof IfNode) {
            IfNode ifNode = (IfNode) node;
            System.out.println(indent + "  Condition: ");
            printAST(ifNode.condition, indent + "    ");
            System.out.println(indent + "  ThenBody: ");
            for (ASTNode thenBodyElement : ifNode.thenBody) {
                printAST(thenBodyElement, indent + "    ");
            }
            System.out.println(indent + "  ElseBody: ");
            for (ASTNode elseBodyElement : ifNode.elseBody) {
                printAST(elseBodyElement, indent + "    ");
            }
        } else if (node instanceof WhileLoopNode) {
            WhileLoopNode whileLoop = (WhileLoopNode) node;
            System.out.println(indent + "  Condition: ");
            printAST(whileLoop.condition, indent + "    ");
            System.out.println(indent + "  Body: ");
            for (ASTNode bodyElement : whileLoop.body) {
                printAST(bodyElement, indent + "    ");
            }
        } else if (node instanceof ForLoopNode) {
            ForLoopNode forLoop = (ForLoopNode) node;
            System.out.println(indent + "  Iterator: " + forLoop.iterator);
            System.out.println(indent + "  Range: ");
            printAST(forLoop.range, indent + "    ");
            System.out.println(indent + "  Body: ");
            for (ASTNode bodyElement : forLoop.body) {
                printAST(bodyElement, indent + "    ");
            }
        } else if (node instanceof PrintNode) {
            PrintNode printNode = (PrintNode) node;
            System.out.println(indent + "  Expression: ");
            printAST(printNode.expression, indent + "    ");
        } else if (node instanceof BreakNode) {
            System.out.println(indent + "  BreakStatement");
        } else if (node instanceof ReturnNode) {
            ReturnNode returnNode = (ReturnNode) node;
            System.out.println(indent + "  ReturnStatement");
            if (returnNode.expression != null) {
                System.out.println(indent + "  Expression: ");
                printAST(returnNode.expression, indent + "    ");
            }
        } else if (node instanceof BinaryOpNode) {
            BinaryOpNode binaryOp = (BinaryOpNode) node;
            System.out.println(indent + "  Operator: " + binaryOp.operator);
            System.out.println(indent + "  Left: ");
            printAST(binaryOp.left, indent + "    ");
            System.out.println(indent + "  Right: ");
            printAST(binaryOp.right, indent + "    ");
        } else if (node instanceof IntLiteralNode) {
            IntLiteralNode intLiteral = (IntLiteralNode) node;
            System.out.println(indent + "  IntValue: " + intLiteral.value);
        } else if (node instanceof RealLiteralNode) {
            RealLiteralNode realLiteral = (RealLiteralNode) node;
            System.out.println(indent + "  RealValue: " + realLiteral.value);
        } else if (node instanceof BooleanLiteralNode) {
            BooleanLiteralNode booleanLiteral = (BooleanLiteralNode) node;
            System.out.println(indent + "  BooleanValue: " + booleanLiteral.value);
        } else if (node instanceof VarRefNode) {
            VarRefNode varRef = (VarRefNode) node;
            System.out.println(indent + "  VarName: " + varRef.varName);
        } else if (node instanceof ArrayAccessNode) {
            ArrayAccessNode arrayAccess = (ArrayAccessNode) node;
            System.out.println(indent + "  Array: ");
            printAST(arrayAccess.array, indent + "    ");
            System.out.println(indent + "  Index: ");
            printAST(arrayAccess.index, indent + "    ");
        } else if (node instanceof FieldAccessNode) {
            FieldAccessNode fieldAccess = (FieldAccessNode) node;
            System.out.println(indent + "  Record: ");
            printAST(fieldAccess.record, indent + "    ");
            System.out.println(indent + "  Field: " + fieldAccess.field);
        } else if (node instanceof ParametersNode) {  // Handling ParametersNode
            ParametersNode parametersNode = (ParametersNode) node;
            System.out.println(indent + "  Parameters: ");
            for (ASTNode param : parametersNode.parameters) {
                printAST(param, indent + "    ");
            }
        } else if (node instanceof ParameterDeclNode) {  // Handling ParameterDeclNode
            ParameterDeclNode parameterDecl = (ParameterDeclNode) node;
            System.out.println(indent + "  VarName: " + parameterDecl.varName);
            System.out.println(indent + "  Type: ");
            printAST(parameterDecl.type, indent + "    ");
        }
        else if (node instanceof BodyNode) {
            BodyNode bodyNode = (BodyNode) node;
            System.out.println(indent + "  SimpleDeclarations: ");
            for (SimpleDeclarationNode decl : bodyNode.simpleDeclarations) {
                printAST(decl, indent + "    ");
            }
            System.out.println(indent + "  Statements: ");
            for (StatementNode stmt : bodyNode.statements) {
                printAST(stmt, indent + "    ");
            }
        } else if (node instanceof FieldAssignmentNode) {
            FieldAssignmentNode fieldAssignmentNode = (FieldAssignmentNode) node;
            System.out.println(indent + "  Field: " + fieldAssignmentNode.varName);
            System.out.println(indent + "  Expression: ");
            printAST(fieldAssignmentNode.expression, indent + "    ");
        }
    }
}
