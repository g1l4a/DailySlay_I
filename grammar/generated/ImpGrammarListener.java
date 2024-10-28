// Generated from ImpGrammar.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ImpGrammarParser}.
 */
public interface ImpGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ImpGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ImpGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSimpleDeclaration(ImpGrammarParser.SimpleDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSimpleDeclaration(ImpGrammarParser.SimpleDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(ImpGrammarParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(ImpGrammarParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(ImpGrammarParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(ImpGrammarParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(ImpGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(ImpGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(ImpGrammarParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(ImpGrammarParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#recordType}.
	 * @param ctx the parse tree
	 */
	void enterRecordType(ImpGrammarParser.RecordTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#recordType}.
	 * @param ctx the parse tree
	 */
	void exitRecordType(ImpGrammarParser.RecordTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(ImpGrammarParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(ImpGrammarParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#routineDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterRoutineDeclaration(ImpGrammarParser.RoutineDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#routineDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitRoutineDeclaration(ImpGrammarParser.RoutineDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(ImpGrammarParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(ImpGrammarParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(ImpGrammarParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(ImpGrammarParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(ImpGrammarParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(ImpGrammarParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ImpGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ImpGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ImpGrammarParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ImpGrammarParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#routineCall}.
	 * @param ctx the parse tree
	 */
	void enterRoutineCall(ImpGrammarParser.RoutineCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#routineCall}.
	 * @param ctx the parse tree
	 */
	void exitRoutineCall(ImpGrammarParser.RoutineCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(ImpGrammarParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(ImpGrammarParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(ImpGrammarParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(ImpGrammarParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(ImpGrammarParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(ImpGrammarParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(ImpGrammarParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(ImpGrammarParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void enterForLoop(ImpGrammarParser.ForLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void exitForLoop(ImpGrammarParser.ForLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#range}.
	 * @param ctx the parse tree
	 */
	void enterRange(ImpGrammarParser.RangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#range}.
	 * @param ctx the parse tree
	 */
	void exitRange(ImpGrammarParser.RangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(ImpGrammarParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(ImpGrammarParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ImpGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ImpGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(ImpGrammarParser.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(ImpGrammarParser.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#simple}.
	 * @param ctx the parse tree
	 */
	void enterSimple(ImpGrammarParser.SimpleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#simple}.
	 * @param ctx the parse tree
	 */
	void exitSimple(ImpGrammarParser.SimpleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(ImpGrammarParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(ImpGrammarParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#summand}.
	 * @param ctx the parse tree
	 */
	void enterSummand(ImpGrammarParser.SummandContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#summand}.
	 * @param ctx the parse tree
	 */
	void exitSummand(ImpGrammarParser.SummandContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ImpGrammarParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ImpGrammarParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#modifiablePrimary}.
	 * @param ctx the parse tree
	 */
	void enterModifiablePrimary(ImpGrammarParser.ModifiablePrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#modifiablePrimary}.
	 * @param ctx the parse tree
	 */
	void exitModifiablePrimary(ImpGrammarParser.ModifiablePrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ImpGrammarParser#fieldAssignment}.
	 * @param ctx the parse tree
	 */
	void enterFieldAssignment(ImpGrammarParser.FieldAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ImpGrammarParser#fieldAssignment}.
	 * @param ctx the parse tree
	 */
	void exitFieldAssignment(ImpGrammarParser.FieldAssignmentContext ctx);
}