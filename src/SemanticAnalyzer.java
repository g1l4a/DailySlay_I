import java.util.*;

public class SemanticAnalyzer {
    private final SymbolTable symbolTable;
    private boolean insideFunction = false; 
    private boolean insideLoop = false; 
    private Set<String> declaredVariables = new HashSet<>();
    private Set<String> usedVariables = new HashSet<>();
    public ASTNode astRoot;
    private ProgramNode programNode;

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
    }

    public void analyze(ASTNode node) {
        if (astRoot == null)
        {
            astRoot = node;
        }

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
        } else if (node instanceof RecordInitNode) {
            visitRecordInit((RecordInitNode) node);
        } else if (node instanceof BodyNode) {
            visitBody((BodyNode) node);
        } else if (node instanceof BreakNode) {
            visitBreak((BreakNode) node);
        } else if (node instanceof ArrayAccessNode) {
            visitArrayAccessNode((ArrayAccessNode) node);
        } else if (node instanceof ExpressionNode) {
            visitExpression(node);
        } else if (node instanceof ParameterDeclNode) {
            visitParameterDeclaration((ParameterDeclNode) node);
        }

    }

    private void visitProgram(ProgramNode program) {
        programNode = program;
        for (ASTNode declaration : program.declarations) {
            analyze(declaration);
        }

        removeUnusedVariableDeclarations(program);
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
        declaredVariables.add(varName);
        analyze(varDecl.varType);
        analyze(varDecl.expression);

        // check the compatibility of types of expression and declared type
        if (varDecl.expression != null) {
            ASTNode typeOfExpression = getTypeOfExpression(varDecl.expression);
            if (!typeMatch(varDecl.varType, typeOfExpression))
            {   
                if (varDecl.varType instanceof PrimitiveTypeNode) {
                    String type = ((PrimitiveTypeNode)varDecl.varType).type;
                    throw new SemanticException("Type mismatch in variable declaration " + type + " " + typeOfExpression.getClass());
                } else {
                    throw new SemanticException("Type mismatch in variable declaration " + varDecl.varType.getClass() + " " + typeOfExpression.getClass());
                }
            }
        }
    }

    public void visitTypeDecl(TypeDeclNode typeDecl) {
        String typeName = typeDecl.typeName;
        symbolTable.put(typeName, typeDecl);
    }

    public void visitRoutineDecl(RoutineDeclNode routineDecl) {
        insideFunction = true;
        String routineName = routineDecl.routineName;
        symbolTable.putRoutine(routineName, routineDecl);
        symbolTable.enterScope();

        for (ASTNode param : routineDecl.parameters) {
            
            analyze(param);
        }

        Iterator<ASTNode> iterator = routineDecl.body.iterator();
        while (iterator.hasNext()) {
            ASTNode bodyNode = iterator.next();
            analyze(bodyNode);
    
        }

        symbolTable.exitScope();
        insideFunction = false;
    }

    private void visitParameterDeclaration(ParameterDeclNode parameterDeclNode) {
        String paramString = parameterDeclNode.varName;
        symbolTable.put(paramString, parameterDeclNode);
    }

    public void visitAssignment(AssignmentNode assignment) {
        ASTNode left = assignment.left;
        ASTNode right = assignment.right;
    
        String varName;
        if (left instanceof VarRefNode) {
            varName = ((VarRefNode) left).varName;
        } else if (left instanceof ArrayAccessNode) {
            varName = ((ArrayAccessNode) left).array;
        } else {
            throw new SemanticException("Invalid left-hand side in assignment: " + left.getClass().getSimpleName());
        }
    
        ASTNode leftType = symbolTable.get(varName);
        usedVariables.add(varName);
        analyze(right);
    
        ASTNode rightType = getTypeOfExpression(right);
        if (leftType instanceof VarDeclNode) {
            if (!typeMatch(((VarDeclNode) leftType).varType, rightType)) {
                throw new SemanticException("Type mismatch: cannot assign " + rightType + " to " + ((VarDeclNode) leftType).varType);
            }
            
        } else if (leftType instanceof ParameterDeclNode)
        {
            
        }
         else {
            throw new SemanticException("Cannot assign to non-variable type: " + leftType);
        }
    }
    

    public void visitArrayAccessNode(ArrayAccessNode arr) {
        VarDeclNode varArray = (VarDeclNode) symbolTable.get(arr.array);
        if (varArray == null) {
            throw new SemanticException("Array with name " + arr.array + " does not exist.");
        } else {
            int index = ((IntLiteralNode) arr.index).value;
            int length = ((IntLiteralNode)(((ArrayTypeNode)(varArray.varType)).sizeExpression)).value;
            if ((index >=  length) || (index < 0)) {
                throw new SemanticException("Index " + index + " is out of bounds of array " + arr.array);
            }
        }
   }

    public void visitIfNode(IfNode ifNode) {
        insideLoop = true;
        analyze(ifNode.condition);
        symbolTable.enterScope();
        for (ASTNode stmt : ifNode.thenBody) {
            analyze(stmt);
        }
        for (ASTNode stmt : ifNode.elseBody) {
            analyze(stmt);
        }

        symbolTable.exitScope();
        insideLoop = false;
    }

    public void visitWhileLoop(WhileLoopNode whileLoop) {
        insideLoop = true;
        analyze(whileLoop.condition);
        symbolTable.enterScope();
        for (ASTNode stmt : whileLoop.body) {
            analyze(stmt);
        }
        symbolTable.exitScope();
        insideLoop = false;
    }

    public void visitForLoop(ForLoopNode forLoop) {
        insideLoop = true;
        if (symbolTable.contains(forLoop.iterator)) {
            throw new SemanticException("Iterator " + forLoop.iterator + " is already declared.");
        }
        symbolTable.put(forLoop.iterator, new VarDeclNode(forLoop.iterator, new PrimitiveTypeNode("int"), null));
        declaredVariables.add(forLoop.iterator);
        analyze(forLoop.range);
        symbolTable.enterScope(); 
        for (ASTNode stmt : forLoop.body) {
            analyze(stmt);
        }
        symbolTable.exitScope();
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
        RoutineDeclNode routineInfo = symbolTable.getRoutine(routineCall.routineName);
        
        List<ASTNode> expectedParameters = routineInfo.parameters;
        List<ASTNode> actualParameters = routineCall.parameters;

        if (canInline(routineInfo)) {
            inlineRoutine(routineCall, routineInfo);
        } else {
            if (expectedParameters.size() != actualParameters.size()) {
                throw new SemanticException("Parameter count mismatch for routine '" + routineCall.routineName + "'. Expected " 
                    + expectedParameters.size() + " but found " + actualParameters.size() + ".");
            }
    
            for (int i = 0; i < expectedParameters.size(); i++) {
                ASTNode actualParam = actualParameters.get(i);
                analyze(actualParam);
    
            }
        }

    }
    
    
    public void visitRecordInit(RecordInitNode recordInit) {
        symbolTable.put(recordInit.variableName, recordInit);
        declaredVariables.add(recordInit.variableName);
        if (!symbolTable.contains(recordInit.type.typeName)) {
            throw new SemanticException("Type " + recordInit.type.typeName + " is not declared.");
        }
    
        for (FieldAssignmentNode fieldAssignment : recordInit.fieldAssignments) {
            analyze(fieldAssignment);
            analyze(fieldAssignment.expression); 
        }
    }
    
    private void visitExpression(ASTNode expression) {
        
        if (expression instanceof BinaryExpressionNode) {
            visitBinaryExpression((BinaryExpressionNode) expression);
        } else if (expression instanceof IntLiteralNode) {
            
        } else if (expression instanceof RealLiteralNode) {
            
        } else if (expression instanceof BooleanLiteralNode) {
            
        } else if (expression instanceof CharLiteralNode) {

        } else if (expression instanceof VarRefNode) {
    
            VarRefNode varRef = (VarRefNode) expression;
            symbolTable.get(varRef.varName); 
            usedVariables.add(varRef.varName); 
        } else {
            throw new SemanticException("Unsupported expression type: " + expression);
        }
    }

    private void removeUnusedVariableDeclarations(ProgramNode program) {
        Iterator<ASTNode> iterator = program.declarations.iterator();
        while (iterator.hasNext()) {
            ASTNode declaration = iterator.next();
            if (declaration instanceof VarDeclNode) {
                VarDeclNode varDecl = (VarDeclNode) declaration;
                String varName = varDecl.varName;
                if (!usedVariables.contains(varName)) {
                    System.out.println("Variable " + varName + " is declared but never used.");
                    iterator.remove();
                }
            }
        }
    }
    
    private void visitBinaryExpression(BinaryExpressionNode binaryExpression) {
        if (isConstantExpression(binaryExpression)) {
            simplifyConstantExpression(binaryExpression);
        } else {
            analyze(binaryExpression.getLeft());
            analyze(binaryExpression.getRight());
        }
    }

    private boolean isConstantExpression(BinaryExpressionNode expression) {
        return (expression.getLeft() instanceof IntLiteralNode || expression.getLeft() instanceof RealLiteralNode) &&
               (expression.getRight() instanceof IntLiteralNode || expression.getRight() instanceof RealLiteralNode);
    }

    private void simplifyConstantExpression(BinaryExpressionNode expression) {
        int leftValue = ((IntLiteralNode) expression.getLeft()).value;
        int rightValue = ((IntLiteralNode) expression.getRight()).value;
        int result = 0; 
        boolean resultBool = false;
        boolean isBool = false;

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
            case "%":
                if (rightValue == 0) {
                    throw new SemanticException("Division by zero.");
                }
                result = leftValue % rightValue;
            case ">":
                resultBool = leftValue > rightValue;
                isBool =true;
                break;
            case "<":
                resultBool = leftValue < rightValue;
                isBool = true;
                break;
            case ">=":
                resultBool = leftValue >= rightValue;

                isBool =true;
                break; 
            case "<=":
                resultBool = leftValue <= rightValue;
               
                break;
            default:
                throw new SemanticException("Unsupported operator: " + expression.getOperator());
        }

        if (isBool) {
            expression.replaceWith(new BooleanLiteralNode(resultBool));
        } else {
            expression.replaceWith(new IntLiteralNode(result));
        }
    }

    private ASTNode getTypeOfExpression(ASTNode expression) {
        
        if (expression instanceof BinaryExpressionNode) {
            return getTypeOfExpression(((BinaryExpressionNode) expression).getLeft());
        } else if (expression instanceof IntLiteralNode) {
            return (IntLiteralNode)expression;
        } else if (expression instanceof RealLiteralNode) {
            return (RealLiteralNode) expression;
        } else if (expression instanceof CharLiteralNode) {
            return (CharLiteralNode) expression;
        } else if (expression instanceof BooleanLiteralNode) {
            return (BooleanLiteralNode) expression;
        } else if (expression instanceof VarRefNode) {
            return (VarRefNode) expression;
        } else if (expression instanceof BinaryOpNode) {
            return (BinaryOpNode) expression;
        } else if (expression instanceof ArrayAccessNode) {
            String arrName = ((ArrayAccessNode) expression).array;
            ASTNode node = symbolTable.get(arrName);
            ASTNode type = ((VarDeclNode) node).varType;
            return ((ArrayTypeNode)type).elementType;
        }
        return null;
    }

    private boolean canInline(RoutineDeclNode routineInfo) {
        if (routineInfo.body.size() <= 3) { 
            for (ASTNode stmt : routineInfo.body) {
                if (stmt instanceof WhileLoopNode || stmt instanceof ForLoopNode || stmt instanceof RoutineCallNode) {
                    return false; 
                }
            }
            return true;
        }
        return false;
    }

    private void inlineRoutine(RoutineCallNode routineCall, RoutineDeclNode routineInfo) {
        Map<String, ASTNode> paramMapping = new HashMap<>();
        List<ASTNode> expectedParameters = routineInfo.parameters;
        List<ASTNode> actualParameters = routineCall.parameters;

        for (int i = 0; i < expectedParameters.size(); i++) {
            String paramName = ((ParameterDeclNode) expectedParameters.get(i)).varName;
            paramMapping.put(paramName, actualParameters.get(i));
        }

        List<ASTNode> inlinedBody = new ArrayList<>();
        for (ASTNode stmt : routineInfo.body) {
            ASTNode copiedStmt = copyNode(stmt);
            System.out.println(stmt);
            ASTNode inlinedStmt = replaceParameters(copiedStmt, paramMapping);
            inlinedBody.add(inlinedStmt);
        }

        replaceWithInline(routineCall, inlinedBody);
    }

    public void replaceWithInline(RoutineCallNode routineCall, List<ASTNode> inlinedBody) {
        ASTNode parent = routineCall.getParent();
    
        if (parent == null) {
            int callIndex = programNode.declarations.indexOf(routineCall);
    
            programNode.declarations.remove(callIndex);
            programNode.declarations.addAll(callIndex, inlinedBody);
    
            for (ASTNode stmt : inlinedBody) {
                stmt.setParent(programNode);
            }
        } else if (parent instanceof RoutineDeclNode) {
            RoutineDeclNode routineParent = (RoutineDeclNode) parent;
            int callIndex = routineParent.body.indexOf(routineCall);
    
            routineParent.body.remove(callIndex);
            routineParent.body.addAll(callIndex, inlinedBody);
    
            for (ASTNode stmt : inlinedBody) {
                stmt.setParent(routineParent);
            }
        } else {
            throw new IllegalArgumentException("Unsupported parent type for inlining.");
        }
    }
    
    private ASTNode replaceParameters(ASTNode stmt, Map<String, ASTNode> paramMapping) {
        if (stmt instanceof VarRefNode) {
            String varName = ((VarRefNode) stmt).varName;
            if (paramMapping.containsKey(varName)) {
                return paramMapping.get(varName); 
            }
        }
        List<ASTNode> replacedChildren = new ArrayList<>();
        for (ASTNode child : stmt.getChildren()) {
            replacedChildren.add(replaceParameters(child, paramMapping));
        }

        stmt.setChildren(replacedChildren);
        return stmt;
    }

    private ASTNode copyNode(ASTNode node) {
        if (node == null) return null;
    
        ASTNode copiedNode = node.copy();

        return copiedNode;
    }
    
    

    private boolean typeMatch(ASTNode node1, ASTNode node2) {

        if (node1.getClass().equals(node2.getClass())) {

            if (node1 instanceof ArrayTypeNode) {
                ArrayTypeNode arrayNode1 = (ArrayTypeNode) node1;
                ArrayTypeNode arrayNode2 = (ArrayTypeNode) node2;
                return typeMatch(arrayNode1.elementType, arrayNode2.elementType);
            }
            return true;
        } else {
            if (node1 instanceof PrimitiveTypeNode) {
                if (node2 instanceof IntLiteralNode && ((PrimitiveTypeNode) node1).type.equals("integer")) {
                    return true;
                } else if (node2 instanceof RealLiteralNode && ((PrimitiveTypeNode) node1).type.equals("real")) {
                    return true;
                } else if (node2 instanceof CharLiteralNode && ((PrimitiveTypeNode) node1).type.equals("char")) {
                    return true;
                } else if (node2 instanceof BooleanLiteralNode && ((PrimitiveTypeNode) node1).type.equals("boolean")) {
                    return true;
                } else if (node2 instanceof ArrayTypeNode) {
                    return typeMatch(node1, ((ArrayTypeNode)node2).elementType);
                }
            } else if (node2 instanceof PrimitiveTypeNode) {
                if (node1 instanceof IntLiteralNode && ((PrimitiveTypeNode) node2).type.equals("integer")) {
                    return true;
                } else if (node1 instanceof RealLiteralNode && ((PrimitiveTypeNode) node2).type.equals("real")) {
                    return true;
                } else if (node1 instanceof CharLiteralNode && ((PrimitiveTypeNode) node2).type.equals("char")) {
                    return true;
                } else if (node1 instanceof BooleanLiteralNode && ((PrimitiveTypeNode) node2).type.equals("boolean")) {
                    return true;
                } else if (node1 instanceof ArrayTypeNode) {
                    return typeMatch(((ArrayTypeNode)node1).elementType, node2);
                }
            } else if (node1 instanceof ArrayTypeNode) {
                return typeMatch(((ArrayTypeNode)node1).elementType, node2);
            } else if (node2 instanceof ArrayTypeNode) {
                return typeMatch(node1, ((ArrayTypeNode)node2).elementType);
            }
        }
        return false;
    }
}
