import java.util.List;
import java.util.ArrayList;

abstract class ASTNode {}

class ProgramNode extends ASTNode {
    List<ASTNode> declarations;

    ProgramNode(List<ASTNode> declarations) {
        this.declarations = declarations;
    }
}

abstract class SimpleDeclarationNode extends ASTNode {
}

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

class PrimitiveTypeNode extends ASTNode {
    String type;

    PrimitiveTypeNode(String type) {
        this.type = type;
    }
}

class ArrayTypeNode extends ASTNode {
    ASTNode sizeExpression;
    ASTNode elementType;

    ArrayTypeNode(ASTNode sizeExpression, ASTNode elementType) {
        this.sizeExpression = sizeExpression;
        this.elementType = elementType;
    }
}

class RecordTypeNode extends ASTNode {
    List<ASTNode> fields;

    RecordTypeNode(List<ASTNode> fields) {
        this.fields = fields;
    }
}

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

    RoutineCallNode(String routineName, List<ASTNode> parameters)
    {
        this.routineName = routineName;
        this.parameters = parameters;
    }
}

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

class BinaryOpNode extends ASTNode {
    String operator;
    ASTNode left;
    ASTNode right;

    BinaryOpNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
}

abstract class ExpressionNode extends ASTNode {
    public ExpressionNode() {
        super();
    }
}

class BinaryExpressionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final String operator;
    private final ExpressionNode right;

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

}


class IntLiteralNode extends ASTNode {
    int value;

    IntLiteralNode(int value) {
        this.value = value;
    }
}

class RealLiteralNode extends ASTNode {
    float value;

    RealLiteralNode(float value) {
        this.value = value;
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

class FieldAccessNode extends ASTNode {
    ASTNode record;
    String field;

    FieldAccessNode(ASTNode record, String field) {
        this.record = record;
        this.field = field;
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

class ParametersNode extends ASTNode {
    List<ASTNode> parameters;

    public ParametersNode(List<ASTNode> parameters) {
        this.parameters = parameters;
    }

}

class ParameterDeclNode extends ASTNode {
    String varName;
    ASTNode type;

    public ParameterDeclNode(String varName, ASTNode type) {
        this.varName = varName;
        this.type = type;
    }

}

class BodyNode extends ASTNode {
    public List<SimpleDeclarationNode> simpleDeclarations;
    public List<StatementNode> statements;

    public BodyNode() {
        this.simpleDeclarations = new ArrayList<>();
        this.statements = new ArrayList<>();
    }
}

abstract class StatementNode extends ASTNode {
}


class RangeNode extends ASTNode {
    public ASTNode lowerBound; 
    public ASTNode upperBound;

    public RangeNode(ASTNode lowerBound, ASTNode upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}

class FieldAssignmentNode extends ASTNode {
    public String varName;
    public ASTNode expression;

    public FieldAssignmentNode(String variableName, ASTNode expression) {
        this.varName = variableName;
        this.expression = expression;
    }
}