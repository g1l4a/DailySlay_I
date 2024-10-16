import java.util.List;

abstract class ASTNode {}

class ProgramNode extends ASTNode {
    List<ASTNode> declarations;

    ProgramNode(List<ASTNode> declarations) {
        this.declarations = declarations;
    }
}


class VarDeclNode extends ASTNode {
    String varName;
    ASTNode varType;
    ASTNode expression;

    VarDeclNode(String varName, ASTNode varType, ASTNode expression) {
        this.varName = varName;
        this.varType = varType;
        this.expression = expression;
    }
}

class TypeDeclNode extends ASTNode {
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

class AssignmentNode extends ASTNode {
    ASTNode left;
    ASTNode right;

    AssignmentNode(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }
}

class IfNode extends ASTNode {
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
