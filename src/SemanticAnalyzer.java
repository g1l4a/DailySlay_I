import java.util.*;

public class SemanticAnalyzer {
    private final SymbolTable symbolTable;
    private boolean insideFunction = false; 
    private boolean insideLoop = false; 
    private Set<String> declaredVariables = new HashSet<>(); // Track declared variables
    private Set<String> usedVariables = new HashSet<>(); // Track used variables

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
    }

    public void analyze(ASTNode node) {
        if (node instanceof ProgramNode) {
            visitProgram((ProgramNode) node);
        } else if (node instanceof VarDeclNode) {
            visitVarDecl((VarDeclNode) node);
        } else if (node instanceof TypeDeclNode) {
            visitTypeDecl((TypeDeclNode) node);
        } else if (node instanceof RoutineDeclNode) {
            visitRoutineDecl((RoutineDeclNode) node);
        } else if (node instanceof AssignmentNode) {
            visitAssignment((AssignmentNode) node);
        } else if (node instanceof IfNode) {
            visitIfNode((IfNode) node);
        } else if (node instanceof WhileLoopNode) {
            visitWhileLoop((WhileLoopNode) node);
        } else if (node instanceof ForLoopNode) {
            visitForLoop((ForLoopNode) node);
        } else if (node instanceof PrintNode) {
            visitPrint((PrintNode) node);
        } else if (node instanceof ReturnNode) {
            visitReturn((ReturnNode) node);
        } else if (node instanceof RoutineCallNode) {
            visitRoutineCall((RoutineCallNode) node);
        } else if (node instanceof FieldAccessNode) {
            visitFieldAccess((FieldAccessNode) node);
        } else if (node instanceof RecordInitNode) {
            visitRecordInit((RecordInitNode) node);
        } else if (node instanceof BodyNode) {
            visitBody((BodyNode) node);
        }
    }

    private void visitProgram(ProgramNode program) {
        for (ASTNode declaration : program.declarations) {
            analyze(declaration);
        }

        // After analyzing all declarations, remove unused variables
        removeUnusedVariables();
    }

    public void visitBody(BodyNode body) {
        symbolTable.enterScope();
    
        for (SimpleDeclarationNode decl : body.simpleDeclarations) {
            analyze(decl); 
        }
    
        for (StatementNode statement : body.statements) {
            analyze(statement); 
        }
    
        symbolTable.exitScope();
    }

    public void visitVarDecl(VarDeclNode varDecl) {
        String varName = varDecl.varName;
        symbolTable.put(varName, varDecl);
        declaredVariables.add(varName); // Track declared variables
        analyze(varDecl.varType);
        analyze(varDecl.expression);
    }

    public void visitTypeDecl(TypeDeclNode typeDecl) {
        String typeName = typeDecl.typeName;
        symbolTable.put(typeName, typeDecl);
    }

    public void visitRoutineDecl(RoutineDeclNode routineDecl) {
        insideFunction = true;
        String routineName = routineDecl.routineName;
        symbolTable.putRoutine(routineName, routineDecl.returnType, routineDecl.parameters);
        symbolTable.enterScope();

        for (ASTNode param : routineDecl.parameters) {
            analyze(param);
        }

        for (ASTNode bodyNode : routineDecl.body) {
            analyze(bodyNode);
        }

        symbolTable.exitScope();
        insideFunction = false;
    }

    // Visit methods for various nodes
    public void visitAssignment(AssignmentNode assignment) {
        VarRefNode left = (VarRefNode) assignment.left;
        ASTNode right = assignment.right;

        symbolTable.get(left.varName);
        usedVariables.add(left.varName); // Track used variable
        analyze(right); 

        // Type checking
        ASTNode leftType = symbolTable.get(left.varName);
        ASTNode rightType = getTypeOfExpression(right);

        if (!leftType.equals(rightType)) {
            throw new SemanticException("Type mismatch: cannot assign " + rightType + " to " + leftType);
        }
    }

    public void visitIfNode(IfNode ifNode) {
        insideLoop = true;
        analyze(ifNode.condition);
        for (ASTNode stmt : ifNode.thenBody) {
            analyze(stmt);
        }
        for (ASTNode stmt : ifNode.elseBody) {
            analyze(stmt);
        }

        insideLoop = false;
    }

    public void visitWhileLoop(WhileLoopNode whileLoop) {
        insideLoop = true;
        analyze(whileLoop.condition);
        symbolTable.enterScope(); // Enter a new scope for the loop body
        for (ASTNode stmt : whileLoop.body) {
            analyze(stmt);
        }
        symbolTable.exitScope(); // Exit the loop scope
        insideLoop = false;
    }

    public void visitForLoop(ForLoopNode forLoop) {
        insideLoop = true;
        if (symbolTable.contains(forLoop.iterator)) {
            throw new SemanticException("Iterator " + forLoop.iterator + " is already declared.");
        }
        symbolTable.put(forLoop.iterator, new VarDeclNode(forLoop.iterator, new PrimitiveTypeNode("int"), null));
        declaredVariables.add(forLoop.iterator); // Track iterator variable
        analyze(forLoop.range);
        symbolTable.enterScope(); // Enter a new scope for the loop body
        for (ASTNode stmt : forLoop.body) {
            analyze(stmt);
        }
        symbolTable.exitScope(); // Exit the loop scope
        insideLoop = false;
    }

    public void visitPrint(PrintNode printNode) {
        analyze(printNode.expression);
    }

    public void visitReturn(ReturnNode returnNode) {
        if (!insideFunction) {
            throw new SemanticException("Return statement can only be used inside a function.");
        }
        if (returnNode.expression != null) {
            analyze(returnNode.expression);
        }
    }

    public void visitBreak(BreakNode breakNode) {
        if (!insideLoop) {
            throw new SemanticException("Break statement can only be used inside a loop.");
        }
    }

    public void visitRoutineCall(RoutineCallNode routineCall) {
        SymbolTable.RoutineInfo routineInfo = symbolTable.getRoutine(routineCall.routineName);
        
        List<ASTNode> expectedParameters = routineInfo.getParameters();
        List<ASTNode> actualParameters = routineCall.parameters;

        if (expectedParameters.size() != actualParameters.size()) {
            throw new SemanticException("Parameter count mismatch for routine '" + routineCall.routineName + "'. Expected " 
                + expectedParameters.size() + " but found " + actualParameters.size() + ".");
        }

        for (int i = 0; i < expectedParameters.size(); i++) {
            ASTNode actualParam = actualParameters.get(i);
            analyze(actualParam);

        }
    }
    
    public void visitFieldAccess(FieldAccessNode fieldAccess) {
        analyze(fieldAccess.record);
    
        if (fieldAccess.record instanceof VarRefNode) {
            String recordVarName = ((VarRefNode) fieldAccess.record).varName;
            ASTNode recordType = symbolTable.get(recordVarName);
    
            if (recordType instanceof UserDefinedTypeNode) {
                RecordTypeNode record = (RecordTypeNode) symbolTable.get(((UserDefinedTypeNode) recordType).typeName);
    
                // Check if the field exists in the record type
                boolean fieldExists = record.fields.stream().anyMatch(field -> field instanceof FieldAssignmentNode 
                    && ((FieldAssignmentNode) field).varName.equals(fieldAccess.field));
    
                if (!fieldExists) {
                    throw new SemanticException("Field '" + fieldAccess.field + "' does not exist in record type '" + ((UserDefinedTypeNode) recordType).typeName + "'.");
                }
            } else {
                throw new SemanticException("Field access on non-record type: " + recordType);
            }
        } else {
            throw new SemanticException("Invalid record reference in field access.");
        }
    }
    
    public void visitRecordInit(RecordInitNode recordInit) {
        symbolTable.put(recordInit.variableName, recordInit);
        declaredVariables.add(recordInit.variableName); // Track declared variable
        if (!symbolTable.contains(recordInit.type.typeName)) {
            throw new SemanticException("Type " + recordInit.type.typeName + " is not declared.");
        }
    
        for (FieldAssignmentNode fieldAssignment : recordInit.fieldAssignments) {
            analyze(fieldAssignment);
            analyze(fieldAssignment.expression); 
        }
    }
    
    public void visitExpression(ASTNode expression) {
        if (expression instanceof BinaryExpressionNode) {
            visitBinaryExpression((BinaryExpressionNode) expression);
        } else if (expression instanceof IntLiteralNode) {
            // No action needed for literals
        } else if (expression instanceof RealLiteralNode) {
            // Handle real literal
        } else if (expression instanceof BooleanLiteralNode) {
            // Handle boolean literal
        } else if (expression instanceof VarRefNode) {
            // Handle variable reference
            VarRefNode varRef = (VarRefNode) expression;
            symbolTable.get(varRef.varName); 
            usedVariables.add(varRef.varName); // Track used variable
        } else {
            throw new SemanticException("Unsupported expression type: " + expression);
        }
    }

    private void removeUnusedVariables() {
        for (String varName : declaredVariables) {
            if (!usedVariables.contains(varName)) {
                System.out.println("Warning: Variable '" + varName + "' declared but not used.");
            }
        }
    }

    private boolean typesMatch(ASTNode expectedType, ASTNode actualType) {
        // Implement type matching logic here
        return expectedType.equals(actualType);
    }

    private ASTNode getType(ASTNode expression) {
        // Implement logic to get the type of the expression
        return null; // Replace with actual type retrieval
    }

    private ASTNode getTypeOfExpression(ASTNode expression) {
        // Implement logic to determine the type of an expression
        return null; // Replace with actual type determination
    }
    
    private void visitBinaryExpression(BinaryExpressionNode binaryExpression) {
        // Simplification of constant expressions
        if (isConstantExpression(binaryExpression)) {
            // Simplify expression and possibly replace it
            simplifyConstantExpression(binaryExpression);
        } else {
            // Analyze both sides
            analyze(binaryExpression.getLeft());
            analyze(binaryExpression.getRight());
        }
    }

    private boolean isConstantExpression(BinaryExpressionNode expression) {
        return (expression.getLeft() instanceof IntLiteralNode || expression.getLeft() instanceof RealLiteralNode) &&
               (expression.getRight() instanceof IntLiteralNode || expression.getRight() instanceof RealLiteralNode);
    }

    private void simplifyConstantExpression(BinaryExpressionNode expression) {
        double leftValue = ((IntLiteralNode) expression.getLeft()).value;
        double rightValue = ((IntLiteralNode) expression.getRight()).value;
        double result = 0; // Default value

        switch (expression.getOperator()) {
            case "+":
                result = leftValue + rightValue;
                break;
            case "-":
                result = leftValue - rightValue;
                break;
            case "*":
                result = leftValue * rightValue;
                break;
            case "/":
                if (rightValue == 0) {
                    throw new SemanticException("Division by zero.");
                }
                result = leftValue / rightValue;
                break;
            default:
                throw new SemanticException("Unsupported operator: " + expression.getOperator());
        }

    
        expression.replaceWith(new RealLiteralNode(result));
    }
}
