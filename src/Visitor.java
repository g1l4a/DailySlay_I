import java.util.List;
import java.util.ArrayList;

public class Visitor extends ImpGrammarBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(ImpGrammarParser.ProgramContext ctx) {
        List<ASTNode> declarations = new ArrayList<>();
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
            return new IntLiteralNode(Integer.parseInt(ctx.IntegerLiteral().getText()));
        } else if (ctx.RealLiteral() != null) {
            return new RealLiteralNode(Float.parseFloat(ctx.RealLiteral().getText()));
        }
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitModifiablePrimary(ImpGrammarParser.ModifiablePrimaryContext ctx) {
        String varName = ctx.TK_VARNAME(0).getText();
        return new VarRefNode(varName);
    }
}
