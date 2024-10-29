import java.util.List;
import java.util.ArrayList;

// Base class for all AST nodes
abstract class ASTNode {
}

// Program Node representing the entire program
class ProgramNode extends ASTNode {
    List<ASTNode> declarations;

    ProgramNode(List<ASTNode> declarations) {
        this.declarations = declarations;
    }

}

// Declaration Nodes
abstract class SimpleDeclarationNode extends ASTNode {}

class VarDeclNode extends SimpleDeclarationNode {
    String varName;
    ASTNode varType;
    ASTNode expression;

    VarDeclNode(String varName, ASTNode varType, ASTNode expression) {
        this.varName = varName;
        this.varType = varType;
        this.expression = expression;
    }
}

class TypeDeclNode extends SimpleDeclarationNode {
    String typeName;
    ASTNode type;

    TypeDeclNode(String typeName, ASTNode type) {
        this.typeName = typeName;
        this.type = type;
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
    }
}

class RecordTypeNode extends TypeNode {
    List<ASTNode> fields;

    RecordTypeNode(List<ASTNode> fields) {
        this.fields = fields;
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
    }
}

class RoutineCallNode extends StatementNode {
    String routineName;
    List<ASTNode> parameters;

    RoutineCallNode(String routineName, List<ASTNode> parameters) {
        this.routineName = routineName;
        this.parameters = parameters;
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
    }
}

class WhileLoopNode extends StatementNode {
    ASTNode condition;
    List<ASTNode> body;

    WhileLoopNode(ASTNode condition, List<ASTNode> body) {
        this.condition = condition;
        this.body = body;
    }
}

class ForLoopNode extends StatementNode {
    String iterator;
    ASTNode range;
    List<ASTNode> body;

    ForLoopNode(String iterator, ASTNode range, List<ASTNode> body) {
        this.iterator = iterator;
        this.range = range;
        this.body = body;
    }
}

class PrintNode extends StatementNode {
    ASTNode expression;

    PrintNode(ASTNode expression) {
        this.expression = expression;
    }
}

class BreakNode extends StatementNode {}

class ReturnNode extends StatementNode {
    ASTNode expression;

    ReturnNode(ASTNode expression) {
        this.expression = expression;
    }
}

// Expression Nodes
abstract class ExpressionNode extends ASTNode {}

public class BinaryExpressionNode extends ExpressionNode {
    private  ExpressionNode left;
    private  String operator;
    private  ExpressionNode right;
    private BinaryExpressionNode parent; // Adding a reference to the parent node for replacement

    public BinaryExpressionNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    // Getters
    public ExpressionNode getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
       
        if (left instanceof BinaryExpressionNode) {
            ((BinaryExpressionNode) left).setParent(this);
        }
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
        // Update the parent reference if the right node is a BinaryExpressionNode
        if (right instanceof BinaryExpressionNode) {
            ((BinaryExpressionNode) right).setParent(this);
        }
    }

    public void replaceWith(ASTNode newNode) {
        if (newNode == null) {
            throw new IllegalArgumentException("Cannot replace with a null node.");
        }

        if (this.parent != null) {
            if (this.parent.getLeft() == this) {
                this.parent.setLeft((ExpressionNode) newNode); 
            } else if (this.parent.getRight() == this) {
                this.parent.setRight((ExpressionNode) newNode); 
            }
        }
    }

    // Setter for parent (if needed)
    public void setParent(BinaryExpressionNode parent) {
        this.parent = parent;
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

class BooleanLiteralNode extends ASTNode {
    boolean value;

    BooleanLiteralNode(boolean value) {
        this.value = value;
    }
}

class VarRefNode extends ASTNode {
    String varName;

    VarRefNode(String varName) {
        this.varName = varName;
    }
}

class ArrayAccessNode extends ASTNode {
    ASTNode array;
    ASTNode index;

    ArrayAccessNode(ASTNode array, ASTNode index) {
        this.array = array;
        this.index = index;
    }
}

class BinaryOpNode extends ASTNode {
    public String operator;
    public ASTNode left;    
    public ASTNode right;   

    public BinaryOpNode(String operator, ASTNode left, ASTNode right)
    {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}

class FieldAccessNode extends ASTNode {
    ASTNode record;
    String field;

    FieldAccessNode(ASTNode record, String field) {
        this.record = record;
        this.field = field;
    }
}

// Range Node
class RangeNode extends ASTNode {
    ASTNode lowerBound; 
    ASTNode upperBound;

    RangeNode(ASTNode lowerBound, ASTNode upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}

// Parameters Node
class ParametersNode extends ASTNode {
    List<ASTNode> parameters;

    ParametersNode(List<ASTNode> parameters) {
        this.parameters = parameters;
    }
}

class ParameterDeclNode extends ASTNode {
    String varName;
    ASTNode type;

    ParameterDeclNode(String varName, ASTNode type) {
        this.varName = varName;
        this.type = type;
    }
}

// Body Node
class BodyNode extends ASTNode {
    public List<SimpleDeclarationNode> simpleDeclarations;
    public List<StatementNode> statements;

    public BodyNode() {
        this.simpleDeclarations = new ArrayList<>();
        this.statements = new ArrayList<>();
    }
}

// Field Assignment Node
class FieldAssignmentNode extends ASTNode {
    String varName;
    ASTNode expression;

    FieldAssignmentNode(String variableName, ASTNode expression) {
        this.varName = variableName;
        this.expression = expression;
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
    }
}
