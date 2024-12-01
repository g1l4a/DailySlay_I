import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

abstract class ASTNode {
    private ASTNode parent;

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

     public List<ASTNode> getChildren() {
        return Collections.emptyList();
    }

    public void setChildren(List<ASTNode> newChildren) {
        if (newChildren == null) return;
        
        for (ASTNode child : newChildren) {
            if (child != null) {
                child.setParent(this); 
            }
        }        
    }
    

    public ASTNode copy() {
        return null;
    }
}

class ProgramNode extends ASTNode {
    List<ASTNode> declarations;

    ProgramNode(List<ASTNode> declarations) {
        this.declarations = declarations;
    }

    @Override
    public List<ASTNode> getChildren() {
        return declarations;
    }

    public void replaceNode(RoutineCallNode routineCallNode, List<ASTNode> inlinedBody) {
        for (int i = 0; i < declarations.size(); i++) {
            ASTNode node = declarations.get(i);
            if (node == routineCallNode) {
                declarations.remove(i);
                declarations.addAll(i, inlinedBody);
                break;
            }
        }
    }
}

abstract class SimpleDeclarationNode extends ASTNode {}

class VarDeclNode extends SimpleDeclarationNode {
    String varName;
    ASTNode varType;
    ASTNode expression;
    public int varIndex;

    VarDeclNode(String varName, ASTNode varType, ASTNode expression) {
        this.varName = varName;
        this.varType = varType;
        this.expression = expression;

        if (expression != null) expression.setParent(this);
    }
}

class TypeDeclNode extends SimpleDeclarationNode {
    String typeName;
    ASTNode type;

    TypeDeclNode(String typeName, ASTNode type) {
        this.typeName = typeName;
        this.type = type;

        if (type != null) type.setParent(this);
    }
}

// Type Nodes
abstract class TypeNode extends ASTNode {}

class PrimitiveTypeNode extends TypeNode {
    String type;

    PrimitiveTypeNode(String type) {
        this.type = type;
    }
}

class UserDefinedTypeNode extends TypeNode {
    String typeName;

    UserDefinedTypeNode(String typeName) {
        this.typeName = typeName;
    }
}

class ArrayTypeNode extends TypeNode {
    ASTNode sizeExpression;
    ASTNode elementType;

    ArrayTypeNode(ASTNode sizeExpression, ASTNode elementType) {
        this.sizeExpression = sizeExpression;
        this.elementType = elementType;

        if (sizeExpression != null) sizeExpression.setParent(this);
        if (elementType != null) elementType.setParent(this);
    }
}

class RecordTypeNode extends TypeNode {
    List<ASTNode> fields;

    RecordTypeNode(List<ASTNode> fields) {
        this.fields = fields;

        for (ASTNode field : fields) {
            if (field != null) field.setParent(this);
        }
    }
}

// Routine Nodes
class RoutineDeclNode extends ASTNode {
    String routineName;
    List<ASTNode> parameters;
    ASTNode returnType;
    List<ASTNode> body;

    RoutineDeclNode(String routineName, List<ASTNode> parameters, ASTNode returnType, List<ASTNode> body) {
        this.routineName = routineName;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;

        if (returnType != null) returnType.setParent(this);
        for (ASTNode param : parameters) {
            if (param != null) param.setParent(this);
        }
        for (ASTNode stmt : body) {
            if (stmt != null) stmt.setParent(this);
        }
    }
}

class RoutineCallNode extends StatementNode {
    String routineName;
    List<ASTNode> parameters;

    RoutineCallNode(String routineName, List<ASTNode> parameters) {
        this.routineName = routineName;
        this.parameters = parameters;

        for (ASTNode param : parameters) {
            if (param != null) param.setParent(this);
        }
    }

    @Override
    public List<ASTNode> getChildren() {
        return parameters;
    }
}

// Statement Nodes
abstract class StatementNode extends ASTNode {}

class AssignmentNode extends StatementNode {
    ASTNode left;
    ASTNode right;

    AssignmentNode(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;

        if (left != null) left.setParent(this);
        if (right != null) right.setParent(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return Arrays.asList(left, right);
    }

    @Override
    public ASTNode copy()
    {
        return new AssignmentNode(left, right);
    }
}

class IfNode extends StatementNode {
    ASTNode condition;
    List<ASTNode> thenBody;
    List<ASTNode> elseBody;

    IfNode(ASTNode condition, List<ASTNode> thenBody, List<ASTNode> elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;

        if (condition != null) condition.setParent(this);

        for (ASTNode node : thenBody) {
            if (node != null) node.setParent(this);
        }

        for (ASTNode node : elseBody) {
            if (node != null) node.setParent(this);
        }

    }
}

class WhileLoopNode extends StatementNode {
    ASTNode condition;
    List<ASTNode> body;

    WhileLoopNode(ASTNode condition, List<ASTNode> body) {
        this.condition = condition;
        this.body = body;

        if (condition != null) condition.setParent(this);
        
        for (ASTNode node : body) {
            if (node != null) node.setParent(this);
        }
    }
}

class ForLoopNode extends StatementNode {
    ASTNode iterator;
    ASTNode range;
    List<ASTNode> body;
    boolean isReversed;

    ForLoopNode(ASTNode iterator, ASTNode range, List<ASTNode> body, boolean isReversed) {
        this.iterator = iterator;
        this.range = range;
        this.body = body;
        this.isReversed = isReversed;

        if (range != null) range.setParent(this);
        
        for (ASTNode node : body) {
            if (node != null) node.setParent(this);
        }
    }

}

class PrintNode extends StatementNode {
    ASTNode expression;

    PrintNode(ASTNode expression) {
        this.expression = expression;

        if (expression != null) expression.setParent(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return expression != null ? Collections.singletonList(expression) : Collections.emptyList();
    }

    @Override
    public void setChildren(List<ASTNode> newChildren) {
        ASTNode newExpression = newChildren.get(0);
        this.expression = newExpression;
        if (newExpression != null) {
            newExpression.setParent(this);
        }
    }

    @Override
    public PrintNode copy() {
        PrintNode copied = new PrintNode(this.expression != null ? this.expression.copy() : null);
        return copied;
    }

}

class BreakNode extends StatementNode {}

class ReturnNode extends StatementNode {
    ASTNode expression;

    ReturnNode(ASTNode expression) {
        this.expression = expression;

        if (expression != null) expression.setParent(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        return expression != null ? Collections.singletonList(expression) : Collections.emptyList();
    }
}

// Expression Nodes
abstract class ExpressionNode extends ASTNode {}

class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode left;
    private String operator;
    private ExpressionNode right;

    public BinaryExpressionNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;

        if (left != null) left.setParent(this);
        if (right != null) right.setParent(this);
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
        if (left != null) left.setParent(this);
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
        if (right != null) right.setParent(this);
    }

    public void replaceWith(ExpressionNode newNode) {
        ASTNode parent = this.getParent();
        if (parent == null) {
            throw new IllegalStateException("Cannot replace node without a parent.");
        }

        if (parent instanceof BinaryExpressionNode) {
            BinaryExpressionNode parentNode = (BinaryExpressionNode) parent;
            if (parentNode.getLeft() == this) {
                parentNode.setLeft(newNode);
            } else if (parentNode.getRight() == this) {
                parentNode.setRight(newNode);
            }
        } else if (parent instanceof AssignmentNode) {
            AssignmentNode parentNode = (AssignmentNode) parent;
            if (parentNode.left == this) {
                parentNode.left = newNode;
            } else if (parentNode.right == this) {
                parentNode.right = newNode;
            }
        } else if (parent instanceof VarDeclNode) {
            ((VarDeclNode) parent).expression = newNode;
            newNode.setParent(parent);
        }
        newNode.setParent(parent);
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        if (left != null) children.add(left);
        if (right != null) children.add(right);
        return children;
    }

    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}


class IntLiteralNode extends ExpressionNode {
    int value;

    IntLiteralNode(int value) {
        this.value = value;
    }
}

class RealLiteralNode extends ExpressionNode {
    double value;

    RealLiteralNode(double value2) {
        this.value = value2;
    }
}

class CharLiteralNode extends ExpressionNode {
    char value;

    CharLiteralNode(char value) {
        this.value = value;
    }
}
class BooleanLiteralNode extends ExpressionNode {
    boolean value;

    BooleanLiteralNode(boolean value) {
        this.value = value;
    }
}

class VarRefNode extends ExpressionNode {
    String varName;
    public int varIndex;

    VarRefNode(String varName) {
        this.varName = varName;
    }

    @Override
    public VarRefNode copy() {
        return new VarRefNode(varName);
    }
}

class ArrayAccessNode extends ExpressionNode {
    String array;
    ASTNode index;

    ArrayAccessNode(String array, ASTNode index) {
        this.array = array;
        this.index = index;

    }
}

class BinaryOpNode extends ExpressionNode {
    public String operator;
    public ASTNode left;    
    public ASTNode right;   

    public BinaryOpNode(String operator, ASTNode left, ASTNode right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;

        if (left != null) left.setParent(this);
        if (right != null) right.setParent(this);
    }
}

class FieldAccessNode extends ASTNode {
    ASTNode record;
    String field;

    FieldAccessNode(ASTNode record, String field) {
        this.record = record;
        this.field = field;

        if (record != null) record.setParent(this);
    }
}

class RangeNode extends ASTNode {
    ASTNode lowerBound; 
    ASTNode upperBound;

    RangeNode(ASTNode lowerBound, ASTNode upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        if (lowerBound != null) lowerBound.setParent(this);
        if (upperBound != null) upperBound.setParent(this);
    }
}


class ParametersNode extends ASTNode {
    List<ASTNode> parameters;

    ParametersNode(List<ASTNode> parameters) {
        this.parameters = parameters;
        for (ASTNode param : parameters) {
            if (param != null) param.setParent(this);
        }
    }
}

class ParameterDeclNode extends ASTNode {
    String varName;
    ASTNode type;
    public int varIndex;

    ParameterDeclNode(String varName, ASTNode type) {
        this.varName = varName;
        this.type = type;
        this.varIndex = 0;

        if (type != null) type.setParent(this);
    }

    @Override
    public List<ASTNode> getChildren() {
        List<ASTNode> children = new ArrayList<>();
        children.add(type);
        return children;
    }
    
}

class BodyNode extends ASTNode {
    public List<SimpleDeclarationNode> simpleDeclarations;
    public List<StatementNode> statements;

    public BodyNode() {
        this.simpleDeclarations = new ArrayList<>();
        this.statements = new ArrayList<>();
    }

    public void addSimpleDeclaration(SimpleDeclarationNode decl) {
        this.simpleDeclarations.add(decl);
        decl.setParent(this);
    }

    public void addStatement(StatementNode stmt) {
        this.statements.add(stmt);
        stmt.setParent(this);
    }

    public void replaceWithInline(RoutineCallNode routineCall, List<ASTNode> inlinedBody) {
        List<StatementNode> newStatements = new ArrayList<>();
        
        for (StatementNode stmt : statements) {
            if (stmt.equals(routineCall)) {
                for (ASTNode inlinedStmt : inlinedBody) {
                    if (inlinedStmt instanceof StatementNode) {
                        newStatements.add((StatementNode) inlinedStmt); 
                    }
                }
            } else {
                newStatements.add(stmt);
            }
        }

        this.statements = newStatements;
    }
}

class FieldAssignmentNode extends ASTNode {
    String varName;
    ASTNode expression;

    FieldAssignmentNode(String variableName, ASTNode expression) {
        this.varName = variableName;
        this.expression = expression;

        if (expression != null) expression.setParent(this);
    }


}


class RecordInitNode extends ASTNode {
    String variableName;                 
    UserDefinedTypeNode type;            
    List<FieldAssignmentNode> fieldAssignments; 

    
    RecordInitNode(String variableName, UserDefinedTypeNode type, List<FieldAssignmentNode> fieldAssignments) {
        this.variableName = variableName;
        this.type = type;
        this.fieldAssignments = fieldAssignments;

        if (type != null) type.setParent(this);
        for (FieldAssignmentNode field : fieldAssignments) {
            if (field != null) field.setParent(this);
        }
    }
}

class ModifiablePrimaryNode extends ASTNode {
    String varName;
    String accessVarName;
    ASTNode expression;

    ModifiablePrimaryNode(String varName, String accessVarName) {
        this.varName = varName;
        this.accessVarName = accessVarName;
    }

    ModifiablePrimaryNode(String varName, ASTNode expression) {
        this.varName = varName;
        this.expression = expression;

        if (expression != null) expression.setParent(this);
    }
}
