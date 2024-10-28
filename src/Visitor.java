import java.util.List;
import java.util.ArrayList;

public class Visitor extends ImpGrammarBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(ImpGrammarParser.ProgramContext ctx) {
        List<ASTNode> declarations = new ArrayList<>();
        
        // Collect variable declarations
        for (ImpGrammarParser.SimpleDeclarationContext varDecl : ctx.simpleDeclaration()) {
            declarations.add(visit(varDecl));
        }
        
        // Collect routine declarations
        for (ImpGrammarParser.RoutineDeclarationContext routineDecl : ctx.routineDeclaration()) {
            declarations.add(visit(routineDecl));
        }
        
        // Collect statements
        for (ImpGrammarParser.StatementContext stmt : ctx.statement()) {
            declarations.add(visit(stmt));
        }
        
        return new ProgramNode(declarations);
    }

    @Override
    public ASTNode visitVariableDeclaration(ImpGrammarParser.VariableDeclarationContext ctx) {
        String varName = ctx.TK_VARNAME().getText();
        ASTNode varType = visit(ctx.type());
        ASTNode expression = ctx.expression() != null ? visit(ctx.expression()) : null;
        return new VarDeclNode(varName, varType, expression);
    }

    @Override
    public ASTNode visitTypeDeclaration(ImpGrammarParser.TypeDeclarationContext ctx) {
        String typeName = ctx.TK_VARNAME().getText();
        ASTNode type = visit(ctx.type());
        return new TypeDeclNode(typeName, type);
    }

    @Override
    public ASTNode visitType(ImpGrammarParser.TypeContext ctx) {
        // Check for primitive types
        if (ctx.primitiveType() != null) {
            return visit(ctx.primitiveType());
        }

        // Check for array types
        if (ctx.arrayType() != null) {
            return visit(ctx.arrayType());
        }

        // Check for record types
        if (ctx.recordType() != null) {
            return visit(ctx.recordType());
        }

        // Check for variable name
        if (ctx.TK_VARNAME() != null) {
            String varName = ctx.TK_VARNAME().getText();
            return new UserDefinedTypeNode(varName); // Assuming you have a class to represent variable types
        }

        // If none of the cases matched, handle it accordingly (throw an error or return null)
        throw new IllegalArgumentException("Invalid type found in context");
    }


    @Override
    public ASTNode visitPrimitiveType(ImpGrammarParser.PrimitiveTypeContext ctx) {
        return new PrimitiveTypeNode(ctx.getText());
    }

    @Override
    public ASTNode visitArrayType(ImpGrammarParser.ArrayTypeContext ctx) {
        ASTNode sizeExpr = ctx.expression() != null ? visit(ctx.expression()) : null;
        ASTNode elementType = visit(ctx.type());
        return new ArrayTypeNode(sizeExpr, elementType);
    }

    @Override
    public ASTNode visitRecordType(ImpGrammarParser.RecordTypeContext ctx) {
        List<ASTNode> fields = new ArrayList<>();
        for (ImpGrammarParser.VariableDeclarationContext varDecl : ctx.variableDeclaration()) {
            fields.add(visit(varDecl));
        }
        return new RecordTypeNode(fields);
    }

    @Override
    public ASTNode visitRoutineDeclaration(ImpGrammarParser.RoutineDeclarationContext ctx) {
        String routineName = ctx.TK_VARNAME().getText();
        List<ASTNode> parameters = new ArrayList<>();
        
        if (ctx.parameters() != null) {
            for (ImpGrammarParser.ParameterDeclarationContext paramDecl : ctx.parameters().parameterDeclaration()) {
                parameters.add(visit(paramDecl));
            }
        }
        
        ASTNode returnType = ctx.type() != null ? visit(ctx.type()) : null;
        List<ASTNode> body = new ArrayList<>();
        
        for (ImpGrammarParser.SimpleDeclarationContext decl : ctx.body().simpleDeclaration()) {
            body.add(visit(decl));
        }
        
        for (ImpGrammarParser.StatementContext stmt : ctx.body().statement()) {
            body.add(visit(stmt));
        }
        
        return new RoutineDeclNode(routineName, parameters, returnType, body);
    }

    @Override
    public ASTNode visitRoutineCall(ImpGrammarParser.RoutineCallContext ctx) {
        String routineName = ctx.TK_VARNAME().getText();
        List<ASTNode> arguments = new ArrayList<>();
        
        for (ImpGrammarParser.ExpressionContext exprCtx : ctx.expression()) {
            arguments.add(visit(exprCtx));
        }

        return new RoutineCallNode(routineName, arguments);
    }

    @Override
    public ASTNode visitAssignment(ImpGrammarParser.AssignmentContext ctx) {
        ASTNode left = visit(ctx.modifiablePrimary());
        ASTNode right = visit(ctx.expression());
        return new AssignmentNode(left, right);
    }

    @Override
    public ASTNode visitIfStatement(ImpGrammarParser.IfStatementContext ctx) {
        ASTNode condition = visit(ctx.expression());
        List<ASTNode> thenBody = new ArrayList<>();
        
        for (ImpGrammarParser.SimpleDeclarationContext decl : ctx.body(0).simpleDeclaration()) {
            thenBody.add(visit(decl));
        }
        
        for (ImpGrammarParser.StatementContext stmt : ctx.body(0).statement()) {
            thenBody.add(visit(stmt));
        }
        
        List<ASTNode> elseBody = new ArrayList<>();
        if (ctx.body(1) != null) {
            for (ImpGrammarParser.SimpleDeclarationContext decl : ctx.body(1).simpleDeclaration()) {
                elseBody.add(visit(decl));
            }
            for (ImpGrammarParser.StatementContext stmt : ctx.body(1).statement()) {
                elseBody.add(visit(stmt));
            }
        }
        
        return new IfNode(condition, thenBody, elseBody);
    }

    @Override
    public ASTNode visitWhileLoop(ImpGrammarParser.WhileLoopContext ctx) {
        ASTNode condition = visit(ctx.expression());
        List<ASTNode> body = new ArrayList<>();
        
        for (ImpGrammarParser.SimpleDeclarationContext decl : ctx.body().simpleDeclaration()) {
            body.add(visit(decl));
        }
        
        for (ImpGrammarParser.StatementContext stmt : ctx.body().statement()) {
            body.add(visit(stmt));
        }
        
        return new WhileLoopNode(condition, body);
    }

    @Override
    public ASTNode visitForLoop(ImpGrammarParser.ForLoopContext ctx) {
        String iterator = ctx.TK_VARNAME().getText();
        ASTNode range = visit(ctx.range());
        List<ASTNode> body = new ArrayList<>();
        
        for (ImpGrammarParser.SimpleDeclarationContext decl : ctx.body().simpleDeclaration()) {
            body.add(visit(decl));
        }
        
        for (ImpGrammarParser.StatementContext stmt : ctx.body().statement()) {
            body.add(visit(stmt));
        }
        
        return new ForLoopNode(iterator, range, body);
    }

    @Override
    public ASTNode visitPrintStatement(ImpGrammarParser.PrintStatementContext ctx) {
        ASTNode expression = visit(ctx.expression());
        return new PrintNode(expression);
    }

    @Override
    public ASTNode visitBreakStatement(ImpGrammarParser.BreakStatementContext ctx) {
        return new BreakNode();
    }

    @Override
    public ASTNode visitReturnStatement(ImpGrammarParser.ReturnStatementContext ctx) {
        ASTNode expression = ctx.expression() != null ? visit(ctx.expression()) : null;
        return new ReturnNode(expression);
    }

    @Override
    public ASTNode visitExpression(ImpGrammarParser.ExpressionContext ctx) {
        // Print the number of relations and their contents
        for (int i = 0; i < ctx.relation().size(); i++) {
            // Visit each relation and print its textual representation
            ASTNode relationNode = visit(ctx.relation(i));
        }

        List<ASTNode> relations = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        // Iterate over relations and populate lists
        for (int i = 0; i < ctx.relation().size(); i++) {
            relations.add(visit(ctx.relation(i)));
        }

        // Collect operators (assuming they are at odd indices)
        for (int i = 1; i < ctx.getChildCount(); i += 2) {
            operators.add(ctx.getChild(i).getText());
        }

        // Handle the binary expression creation
        if (relations.size() == 1) {
            return relations.get(0);
        }

        ASTNode expressionNode = relations.get(0);
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            ASTNode right = relations.get(i + 1);
            expressionNode = new BinaryExpressionNode((ExpressionNode) expressionNode, operator, (ExpressionNode) right);
        }

        return expressionNode;
    }

    @Override
    public ASTNode visitRelation(ImpGrammarParser.RelationContext ctx) {
        ASTNode left = visit(ctx.simple(0));
        if (ctx.simple().size() > 1) {
            ASTNode right = visit(ctx.simple(1));
            String operator = ctx.getChild(1).getText();
            return new BinaryOpNode(operator, left, right);
        }
        return left;
    }

    @Override
    public ASTNode visitPrimary(ImpGrammarParser.PrimaryContext ctx) {
        if (ctx.IntegerLiteral() != null) {
            // Handle IntegerLiteral
            int value = Integer.parseInt(ctx.IntegerLiteral().getText());
            return new IntLiteralNode(value);
        } else if (ctx.RealLiteral() != null) {
            // Handle RealLiteral
            double value = Double.parseDouble(ctx.RealLiteral().getText());
            return new RealLiteralNode(value);
        } else if (ctx.getText().equals("true")) {
            // Handle boolean literal true
            return new BooleanLiteralNode(true);
        } else if (ctx.getText().equals("false")) {
            // Handle boolean literal false
            return new BooleanLiteralNode(false);
        } else if (ctx.modifiablePrimary() != null) {
            // Handle modifiablePrimary (like variables)
            return visit(ctx.modifiablePrimary());
        } else if (ctx.routineCall() != null) {
            // Handle routineCall
            return visit(ctx.routineCall());
        } else if (ctx.fieldAssignment() != null && ctx.fieldAssignment().size() == 1) {
            // Handle field assignment in parentheses, ensuring it's a single assignment
            return visitFieldAssignment(ctx.fieldAssignment(0)); // Ensure you handle only the first context
        } 
    
        // If no valid primary found, consider throwing an error or handling accordingly
        throw new IllegalArgumentException("Invalid primary found in context: " + ctx.getText());
    }
    
    @Override
    public ASTNode visitSummand(ImpGrammarParser.SummandContext ctx) {
        // Check if the summand is a primary or a type
        if (ctx.primary() != null) {
            // Handle the primary case
            return visit(ctx.primary());
        } else if (ctx.type() != null) {
            // Handle the type case (if relevant to your AST)
            return visit(ctx.type());
        } else if (ctx.expression() != null) {
            // Handle the expression case in parentheses
            // Assuming you have a suitable class for expression nodes
            return visit(ctx.expression());
        }

        // If none of the above cases match, return null or handle as needed
        return null;
    }

    @Override
    public ASTNode visitFactor(ImpGrammarParser.FactorContext ctx) {
        // Create a list to hold the summands and their corresponding operators
        List<ASTNode> summands = new ArrayList<>();
        List<String> operators = new ArrayList<>();

        // Visit the first summand
        summands.add(visit(ctx.summand(0)));

        // Process subsequent summands and operators
        for (int i = 1; i < ctx.summand().size(); i++) {
            // Determine the operator
            String operator = ctx.getChild(i * 2 - 1).getText(); // Assuming operators are at odd indices
            operators.add(operator);

            // Visit the summand
            summands.add(visit(ctx.summand(i)));
        }

        // If there's only one summand, return it
        if (summands.size() == 1) {
            return summands.get(0);
        }

        // If there are multiple summands, create a new BinaryExpressionNode
        ASTNode expressionNode = summands.get(0);
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            ASTNode right = summands.get(i + 1);
            expressionNode = new BinaryExpressionNode((ExpressionNode) expressionNode, operator, (ExpressionNode) right);
        }

        return expressionNode;
    }



    @Override
    public ASTNode visitModifiablePrimary(ImpGrammarParser.ModifiablePrimaryContext ctx) {
        String varName = ctx.TK_VARNAME(0).getText();
        return new VarRefNode(varName);
    }

    @Override
    public ASTNode visitParameters(ImpGrammarParser.ParametersContext ctx) {
        List<ASTNode> parameterNodes = new ArrayList<>();
        
        for (ImpGrammarParser.ParameterDeclarationContext paramDecl : ctx.parameterDeclaration()) {
            parameterNodes.add(visit(paramDecl));
        }

        return new ParametersNode(parameterNodes); 
    }

    @Override
    public ASTNode visitParameterDeclaration(ImpGrammarParser.ParameterDeclarationContext ctx) {
        String varName = ctx.TK_VARNAME().getText();
        ASTNode typeNode = visit(ctx.type());
        return new ParameterDeclNode(varName, typeNode);
    }

    @Override
    public ASTNode visitRecordInit(ImpGrammarParser.RecordInitContext ctx) {
        // Extract the variable name (the name of the variable being initialized as a record)
        String variableName = ctx.TK_VARNAME(0).getText();
        
        // Extract the record type name
        String typeName = ctx.TK_VARNAME(1).getText();
        UserDefinedTypeNode recordType = new UserDefinedTypeNode(typeName);
        
        // Collect the field assignments for the record initialization
        List<FieldAssignmentNode> fieldAssignments = new ArrayList<>();
        for (ImpGrammarParser.FieldAssignmentContext fieldCtx : ctx.fieldAssignment()) {
            String fieldName = fieldCtx.TK_VARNAME().getText();
            ASTNode fieldValue = visit(fieldCtx.expression()); // Visit to get the expression value
            fieldAssignments.add(new FieldAssignmentNode(fieldName, fieldValue));
        }

        // Create and return a new RecordInitNode with the parsed details
        return new RecordInitNode(variableName, recordType, fieldAssignments);
    }

}

