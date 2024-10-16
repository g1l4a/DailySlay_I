// Generated from grammar/ImpGrammar.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ImpGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ImpGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ImpGrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleDeclaration(ImpGrammarParser.SimpleDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(ImpGrammarParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#typeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeDeclaration(ImpGrammarParser.TypeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(ImpGrammarParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(ImpGrammarParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#recordType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordType(ImpGrammarParser.RecordTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(ImpGrammarParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#routineDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutineDeclaration(ImpGrammarParser.RoutineDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(ImpGrammarParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(ImpGrammarParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ImpGrammarParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ImpGrammarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(ImpGrammarParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#routineCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoutineCall(ImpGrammarParser.RoutineCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(ImpGrammarParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(ImpGrammarParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(ImpGrammarParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(ImpGrammarParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(ImpGrammarParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#range}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange(ImpGrammarParser.RangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(ImpGrammarParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ImpGrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation(ImpGrammarParser.RelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#simple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple(ImpGrammarParser.SimpleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(ImpGrammarParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#summand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSummand(ImpGrammarParser.SummandContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(ImpGrammarParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#modifiablePrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifiablePrimary(ImpGrammarParser.ModifiablePrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link ImpGrammarParser#fieldAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAssignment(ImpGrammarParser.FieldAssignmentContext ctx);
}