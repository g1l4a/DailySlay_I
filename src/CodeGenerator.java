import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    private StringBuilder code;
    private int labelCounter;
    private int nextVarIndex;
    private String currentBreakLabel;
    private Map<String, VarDeclNode> symbolTable;
    private Map<String, ASTNode> returnTypeMap;

    public CodeGenerator() {
        this.code = new StringBuilder();
        this.labelCounter = 0;
        this.nextVarIndex = 0;
        this.symbolTable = new HashMap<>();
        this.returnTypeMap = new HashMap<>();
    }

    private String generateLabel(String base) {
        return base + "_" + (labelCounter++);
    }

    private int allocateVarIndex() {
        return nextVarIndex++;
    }


    public String generate(ASTNode root) {
        if (root instanceof ProgramNode) {
            visitProgram((ProgramNode) root);
        } else {
            throw new IllegalArgumentException("Root node is not a ProgramNode.");
        }
        return code.toString();
    }

    private void visitProgram(ProgramNode node) {
            code.append(".class public MyProgram\n");
            code.append(".super java/lang/Object\n");


            for (ASTNode decl : node.declarations) {
                if (decl instanceof RoutineDeclNode) {
                    visit(decl); 
                }
            }

            
            code.append(".method public static main([Ljava/lang/String;)V\n");
            code.append("   .limit stack 10\n");
            code.append("   .limit locals 10\n");

            for (ASTNode decl : node.declarations) {
            if (!(decl instanceof RoutineDeclNode) && !(decl instanceof TypeDeclNode)) {
                visit(decl);
                }
            }

            code.append("   return\n");
            code.append(".end method\n");

            for (ASTNode decl : node.declarations) {
                if (decl instanceof TypeDeclNode) {
                    visit(decl); 
                }
            }

    }

    private void visit(ASTNode node) {
        if (node instanceof VarDeclNode) {
            visitVarDecl((VarDeclNode) node);
        } else if (node instanceof AssignmentNode) {
            visitAssignment((AssignmentNode) node);
        } else if (node instanceof IfNode) {
            visitIf((IfNode) node);
        } else if (node instanceof WhileLoopNode) {
            visitWhileLoop((WhileLoopNode) node);
        } else if (node instanceof PrintNode) {
            visitPrint((PrintNode) node);
        } else if (node instanceof RoutineDeclNode) {
            visitRoutineDecl((RoutineDeclNode) node);
        } else if (node instanceof TypeDeclNode) {
            visitTypeDecl((TypeDeclNode) node);
        } else if (node instanceof PrimitiveTypeNode) {
            visitPrimitiveType((PrimitiveTypeNode) node);
        } else if (node instanceof UserDefinedTypeNode) {
            visitUserDefinedType((UserDefinedTypeNode) node);
        } else if (node instanceof ArrayTypeNode) {
            visitArrayType((ArrayTypeNode) node);
        } else if (node instanceof RecordTypeNode) {
            visitRecordType((RecordTypeNode) node);
        } else if (node instanceof RoutineCallNode) {
            visitRoutineCall((RoutineCallNode) node);
        } else if (node instanceof ForLoopNode) {
            visitForLoop((ForLoopNode) node);
        } else if (node instanceof BreakNode) {
            visitBreak((BreakNode) node);
        } else if (node instanceof ReturnNode) {
            visitReturn((ReturnNode) node);
        } else if (node instanceof ArrayAccessNode) {
            visitArrayAccess((ArrayAccessNode) node, true);
        } else if (node instanceof BinaryOpNode) {
            visitBinaryOp((BinaryOpNode) node);
        } else if (node instanceof FieldAccessNode) {
            visitFieldAccess((FieldAccessNode) node);
        } else if (node instanceof RangeNode) {
            visitRange((RangeNode) node);
        } else if (node instanceof ParametersNode) {
            visitParameters((ParametersNode) node);
        } else if (node instanceof ParameterDeclNode) {
            visitParameterDecl((ParameterDeclNode) node);
        } else if (node instanceof BodyNode) {
            visitBody((BodyNode) node);
        } else if (node instanceof FieldAssignmentNode) {
            visitFieldAssignment((FieldAssignmentNode) node);
        } else if (node instanceof RecordInitNode) {
            visitRecordInit((RecordInitNode) node);
        } else if (node instanceof ModifiablePrimaryNode) {
            visitModifiablePrimary((ModifiablePrimaryNode) node);

        } else if (node instanceof ExpressionNode) {
            node = (ExpressionNode) node;
            if (node instanceof BinaryExpressionNode) {
                visitBinaryExpression((BinaryExpressionNode) node);
            } else if (node instanceof IntLiteralNode) {
                visitIntLiteral((IntLiteralNode) node);
            } else if (node instanceof RealLiteralNode) {
                visitRealLiteral((RealLiteralNode) node);
            } else if (node instanceof BooleanLiteralNode) {
                visitBooleanLiteral((BooleanLiteralNode) node);
            } else if (node instanceof CharLiteralNode) {
                visitCharLiteral((CharLiteralNode) node);
            } else if (node instanceof VarRefNode) {
                visitVarRef((VarRefNode) node);
            }
        } else {
            code.append("вызов того, чего нет\n");
            throw new IllegalArgumentException("Unsupported AST node: " + node.getClass().getSimpleName());
        }
    }

    private void visitVarDecl(VarDeclNode node) {
        int index = allocateVarIndex();
        node.varIndex = index;
        symbolTable.put(node.varName, node);
        if (node.expression != null) {
            visit(node.expression);
            code.append("   ").append("istore_").append(index).append("\n");
        } else {
            if (node.varType instanceof ArrayTypeNode) {
                ArrayTypeNode arr = (ArrayTypeNode) node.varType;
                code.append("   ldc ").append(((IntLiteralNode) arr.sizeExpression).value).append("\n");
                ExpressionNode type = castPrimitiveToExpression((PrimitiveTypeNode) arr.elementType);
                if (type instanceof IntLiteralNode) {
                    code.append("   newarray ").append("int").append("\n");
                } else if (type instanceof RealLiteralNode) {
                    code.append("   newarray ").append("float").append("\n");
                } else if (type instanceof CharLiteralNode) {
                    code.append("   newarray ").append("char").append("\n");
                } else if (type instanceof BooleanLiteralNode) {
                    code.append("   newarray ").append("boolean").append("\n");
                }
                code.append("   astore_").append(node.varIndex).append("\n");

            }
        }
    }

    private void visitTypeDecl(TypeDeclNode node) {
        if (node.type != null) {
            visit(node.type);
        }
    }

    private void visitPrimitiveType(PrimitiveTypeNode node) {
        code.append("; Primitive type: ").append(node.type).append("\n");
    }

    private void visitUserDefinedType(UserDefinedTypeNode node) {
        code.append("; User-defined type: ").append(node.typeName).append("\n");
    }

    private void visitArrayType(ArrayTypeNode node) {
    code.append("; Array type\n");
    visit(node.sizeExpression);

    if (node.elementType != null) {
        visit(node.elementType);
        }
    }


    private void visitRecordType(RecordTypeNode node) {
        if (node.getParent() instanceof TypeDeclNode) {
            String recordName = ((TypeDeclNode) node.getParent()).typeName; // имя записи
            if (recordName == null || recordName.isEmpty()) {
                throw new IllegalArgumentException("Record name is null or empty");
            }
            code.append(".class public ").append(recordName).append("\n");
            code.append(".super java/lang/Object\n");

            // Генерация полей
            for (ASTNode field : node.fields) {
                if (field instanceof VarDeclNode) {
                    VarDeclNode varDeclField = (VarDeclNode) field;
                    String fieldName = varDeclField.varName;
                    String fieldType = getTypeDescriptor(varDeclField.varType); // Получаем дескриптор типа
                    code.append(".field public ").append(fieldName).append(" ").append(fieldType).append("\n");
                } else {
                    throw new IllegalArgumentException("Expected VarDeclNode, got " + field.getClass().getSimpleName());
                }
            }

            // Генерация конструктора
            code.append(".method public <init>(");
            for (ASTNode field : node.fields) {
                if (field instanceof VarDeclNode) {
                    VarDeclNode varDeclField = (VarDeclNode) field;
                    String fieldType = getTypeDescriptor(varDeclField.varType);
                    code.append(fieldType); // Параметры конструктора
                } else {
                    throw new IllegalArgumentException("Expected VarDeclNode, got " + field.getClass().getSimpleName());
                }
            }
            code.append(")V\n");
            code.append("   aload_0\n");
            code.append("   invokespecial java/lang/Object/<init>()V\n");

            int paramIndex = 1; // Индексы параметров конструктора
            for (ASTNode field : node.fields) {
                if (field instanceof VarDeclNode) {
                    VarDeclNode varDeclField = (VarDeclNode) field;
                    String fieldName = varDeclField.varName;
                    String fieldType = getTypeDescriptor(varDeclField.varType);
                    code.append("   aload_0\n");
                    code.append("   ").append(loadInstructionForType(varDeclField.varType)).append(" ")
                            .append(paramIndex).append("\n");
                    code.append("   putfield ").append(recordName).append("/").append(fieldName).append(" ")
                            .append(fieldType).append("\n");
                    paramIndex++;
                } else {
                    throw new IllegalArgumentException("Expected VarDeclNode, got " + field.getClass().getSimpleName());
                }
            }
            code.append("   return\n");
            code.append(".end method\n");
        } else {
            throw new IllegalArgumentException(
                    "Parent class is not TypeDeclNode for " + node.getClass().getSimpleName());
        }
    }

    private String loadInstructionForType(ASTNode type) {
        if (type instanceof PrimitiveTypeNode) {
            String primitiveType = ((PrimitiveTypeNode) type).type;
            switch (primitiveType) {
                case "int":
                case "boolean":
                    return "iload";
                case "real":
                    return "fload";
                case "char":
                    return "aload";
                default:
                    throw new IllegalArgumentException("Unknown primitive type: " + primitiveType);
            }
        }
        return "aload"; // для объектов
    }

    private String getTypeDescriptor(ASTNode type) {
        if (type instanceof PrimitiveTypeNode) {
            String primitiveType = ((PrimitiveTypeNode) type).type;
            switch (primitiveType) {
                case "integer":
                    return "I";
                case "real":
                    return "F";
                case "boolean":
                    return "Z";
                case "char":
                    return "C";
                default:
                    throw new IllegalArgumentException("Unknown primitive type: " + primitiveType);
            }
        } else if (type instanceof UserDefinedTypeNode) {
            return "L" + ((UserDefinedTypeNode) type).typeName + ";";
        } else if (type instanceof ArrayTypeNode) {
            ArrayTypeNode arrayTypeNode = (ArrayTypeNode) type;
            return "[" + getTypeDescriptor(arrayTypeNode.elementType);
        }
        else {
            throw new IllegalArgumentException("Unsupported type node: " + type.getClass().getSimpleName());
        }
    }

    private void visitAssignment(AssignmentNode node) {
        if (node.left instanceof VarRefNode) {
            visit(node.right);
            VarDeclNode varDecl = symbolTable.get(((VarRefNode) node.left).varName);
            int inx = varDecl.varIndex;
            ASTNode type = varDecl.varType;

            if (type instanceof PrimitiveTypeNode) {
                ExpressionNode typeExp = castPrimitiveToExpression((PrimitiveTypeNode) type);
                if (typeExp instanceof IntLiteralNode || typeExp instanceof BooleanLiteralNode) {
                    code.append("   istore_").append(inx).append("\n");
                } else if (typeExp instanceof RealLiteralNode) {
                    code.append("   fstore_").append(inx).append("\n");
                } else if (typeExp instanceof CharLiteralNode) {
                    code.append("   astore_").append(inx).append("\n");
                }
            }
        } else if (node.left instanceof ArrayAccessNode) {
            ArrayAccessNode arrayAccess = (ArrayAccessNode) node.left;

            visitArrayAccess(arrayAccess, false);
    
            visit(arrayAccess.index);

            visit(node.right);

            code.append("   iastore\n");
        }

    }

    private void visitVarRef(VarRefNode node) {
        if (!symbolTable.containsKey(node.varName)) {
            int inx = allocateVarIndex();
            symbolTable.put(node.varName, new VarDeclNode(node.varName, node, null));
            symbolTable.get(node.varName).varIndex = inx;
        }
        VarDeclNode varDecl = symbolTable.get(node.varName);
        int inx = varDecl.varIndex;
        ASTNode type = varDecl.varType;

        if (type instanceof PrimitiveTypeNode) {
            ExpressionNode typeExp = castPrimitiveToExpression((PrimitiveTypeNode) type);
            if (typeExp instanceof IntLiteralNode || typeExp instanceof BooleanLiteralNode) {
                code.append("   iload_").append(inx).append("\n");
            } else if (typeExp instanceof RealLiteralNode) {
                code.append("   fload_").append(inx).append("\n");
            } else if (typeExp instanceof CharLiteralNode) {
                code.append("   aload_").append(inx).append("\n");
            }
        } else if (type instanceof VarRefNode) {
            code.append("   iload_").append(inx).append("\n");
            
        }
    }

    private void visitRoutineDecl(RoutineDeclNode node) {
        String returnType = "V";
        code.append(".method public static ");
        code.append(node.routineName).append("(");

        for (ASTNode param : node.parameters) {
            visitParameterDecl((ParameterDeclNode) param);
        }

        returnTypeMap.put(node.routineName, node.returnType);
        if (node.returnType != null)
        {
            if (node.returnType instanceof PrimitiveTypeNode) {
                returnType = getTypeDescriptor((PrimitiveTypeNode) node.returnType);
            }
        }

        code.append(")").append(returnType).append("\n");
        code.append("   .limit stack 10\n");
        code.append("   .limit locals ").append(nextVarIndex + node.parameters.size() + 1).append("\n");

        for (ASTNode statement : node.body) {
            visit(statement);
        }

        if (returnType.equals("I")) {
            code.append("   ireturn\n");
        } else if (returnType.equals("F")) {
            code.append("   freturn\n");
        } else if (returnType.equals("C")) {
            code.append("   ireturn\n");
        } else if (returnType.equals("Z")) {
            code.append("   ireturn\n");
        } else if (returnType.startsWith("L")) {
            code.append("   areturn\n");
        } else if (returnType.startsWith("[")) {
            code.append("   areturn\n");
        }
        else {
            code.append("   return\n");
        }
        code.append(".end method\n");
    }

    private void visitRoutineCall(RoutineCallNode node) {
        for (ASTNode param : node.parameters) {
            visit(param);
        }

        String returnType = "V";
        if (!returnTypeMap.containsKey(node.routineName)) {
            throw new RuntimeException("Routine '" + node.routineName + "' was not declared before use.");
        }
        ASTNode returnTypeNode = returnTypeMap.get(node.routineName);
        
        if (returnTypeNode != null)
        {
            if (returnTypeNode instanceof PrimitiveTypeNode) {
                returnType = getTypeDescriptor((PrimitiveTypeNode) returnTypeNode);
            }
        }

        code.append("   invokestatic MyProgram/").append(node.routineName).append("(");
        for (ASTNode param : node.parameters) {
            if (param instanceof VarRefNode) {
                VarDeclNode varDecl = symbolTable.get(((VarRefNode) param).varName);
                int inx = varDecl.varIndex;
                ASTNode type = varDecl.varType;

                if (type instanceof PrimitiveTypeNode) {
                    ExpressionNode typeExp = castPrimitiveToExpression((PrimitiveTypeNode) type);
                    if (typeExp instanceof IntLiteralNode) {
                        code.append("I");
                    } else if (typeExp instanceof RealLiteralNode) {
                        code.append("F");
                    } else if (typeExp instanceof CharLiteralNode) {
                        code.append("C");
                    } else if (typeExp instanceof BooleanLiteralNode) {
                        code.append("Z");
                    }
                }
            }
        }
        if (returnType != null) {
            code.append(")").append(returnType).append("\n");
        } else {
            code.append(")").append("V").append("\n");
        }
        
    }

    private void visitForLoop(ForLoopNode node) {
        //resetLocalVarCounter();
        String startLabel = generateLabel("L" + (labelCounter++));
        String endLabel = generateLabel("L" + (labelCounter++));
        currentBreakLabel = endLabel;


        if (!(node.range instanceof RangeNode)) {
            throw new RuntimeException("Expected RangeNode for for-loop range");
        }
        RangeNode range = (RangeNode) node.range;

        // Определяем, нужно ли делать обратный порядок
        boolean reverse = node.isReversed; // reverse указывает на флаг

        // Выставляем нижнюю и верхнюю границу диапазона
        visit(range.lowerBound);
        VarRefNode iterator = (VarRefNode)node.iterator;
        int newInx = 0;
        if (!symbolTable.containsKey(iterator.varName)) {
            int inx = allocateVarIndex();
            symbolTable.put(iterator.varName, new VarDeclNode(iterator.varName, node.iterator, null));
            symbolTable.get(iterator.varName).varIndex = inx;
            newInx = inx;
        }
        
        code.append("   istore_").append(newInx).append("\n");

        // Метки для начала и конца цикла
        code.append(startLabel).append(":\n");

        // load loop variable
        visit(node.iterator);

        // push upper bound to stack
        visit(range.upperBound);


        if (reverse) {
            
            code.append("   if_icmplt ").append(endLabel).append("\n"); // Если индекс < нижней границы, выходим из цикла
        } else {
            code.append("   if_icmpgt ").append(endLabel).append("\n");
        }

        // Генерируем тело цикла
        for (ASTNode stmt : node.body) {
            visit(stmt);
        }

        
        code.append("   iinc ").append(newInx).append(node.isReversed ? " -1" : " 1").append("\n");
        // Переход к началу цикла
        code.append("   goto ").append(startLabel).append("\n");
        code.append(endLabel).append(":\n");
    }

    private void visitBreak(BreakNode node) {
        code.append("goto ").append(currentBreakLabel).append("\n");
    }

    private void visitReturn(ReturnNode node) {
        if (node.expression != null) {
            visit(node.expression);
        }
        code.append("return\n");
    }

    private void visitRealLiteral(RealLiteralNode node) {
        code.append("   ldc ").append(node.value).append("\n");
    }

    private void visitCharLiteral(CharLiteralNode node) {
        code.append("   ldc ").append((int) node.value).append("\n");
    }

    private void visitBooleanLiteral(BooleanLiteralNode node) {
        code.append("   ldc ").append(node.value ? 1 : 0).append("\n");
    }

    private void visitArrayAccess(ArrayAccessNode node, boolean onlyAccess) {
        VarDeclNode varDecl = symbolTable.get(node.array);
        int inx = varDecl.varIndex;
        code.append("   aload_").append(inx).append("\n");
        if (onlyAccess) {
            visit(node.index);
            code.append("   iaload\n");
        }
        
    }

    private void visitBinaryOp(BinaryOpNode node) {
        visit(node.left);
        visit(node.right);

        switch (node.operator) {
            case "+":
                code.append("iadd\n");
                break;
            case "-":
                code.append("isub\n");
                break;
            case "*":
                code.append("imul\n");
                break;
            case "/":
                code.append("idiv\n");
                break;
            case ">":
                code.append("if_icmpgt LABEL_TRUE\n");
                code.append("iconst_0\n");
                code.append("goto LABEL_END\n");
                code.append("LABEL_TRUE:\n");
                code.append("iconst_1\n");
                code.append("LABEL_END:\n");
                break;
            case ">=":
                code.append("if_icmpge LABEL_TRUE\n");
                code.append("iconst_0\n");
                code.append("goto LABEL_END\n");
                code.append("LABEL_TRUE:\n");
                code.append("iconst_1\n");
                code.append("LABEL_END:\n");
                break;
            case "<":
                code.append("if_icmplt LABEL_TRUE\n");
                code.append("iconst_0\n");
                code.append("goto LABEL_END\n");
                code.append("LABEL_TRUE:\n");
                code.append("iconst_1\n");
                code.append("LABEL_END:\n");
                break;
            case "<=":
                code.append("if_icmple LABEL_TRUE\n");
                code.append("iconst_0\n");
                code.append("goto LABEL_END\n");
                code.append("LABEL_TRUE:\n");
                code.append("iconst_1\n");
                code.append("LABEL_END:\n");
                break;
            case "=":
                code.append("if_icmpeq LABEL_TRUE\n");
                code.append("iconst_0\n");
                code.append("goto LABEL_END\n");
                code.append("LABEL_TRUE:\n");
                code.append("iconst_1\n");
                code.append("LABEL_END:\n");
                break;
            case "/=":
                code.append("if_icmpeq LABEL_TRUE\n");
                code.append("iconst_0\n");
                code.append("goto LABEL_END\n");
                code.append("LABEL_TRUE:\n");
                code.append("iconst_1\n");
                code.append("LABEL_END:\n");
                break;
            default:
                throw new RuntimeException("Unsupported binary operator: " + node.operator);
        }
    }

    private void visitFieldAccess(FieldAccessNode node) {
        visit(node.record);
        code.append("getfield ").append(node.field).append("\n");
    }

    private void visitRange(RangeNode node) {
        visit(node.lowerBound);
        visit(node.upperBound);
    }

    private void visitParameters(ParametersNode node) {
        for (ASTNode param : node.parameters) {
            visit(param);
        }
    }

    private void visitParameterDecl(ParameterDeclNode node) {
        String name = node.varName;
        ASTNode type = node.type;

        if (type instanceof PrimitiveTypeNode) {
            VarDeclNode nodeForTable = new VarDeclNode(name, type, null);
            symbolTable.put(name, nodeForTable);
            ExpressionNode typeLiteral = castPrimitiveToExpression((PrimitiveTypeNode) type);
            if (typeLiteral instanceof IntLiteralNode) {
                code.append("I");
            } else if (typeLiteral instanceof RealLiteralNode) {
                code.append("F");
            } else if (typeLiteral instanceof CharLiteralNode) {
                code.append("C");
            } else if (typeLiteral instanceof BooleanLiteralNode) {
                code.append("Z");
            }
        }
    }

    private void visitBody(BodyNode node) {
        for (SimpleDeclarationNode decl : node.simpleDeclarations) {
            visit(decl);
        }
        for (StatementNode stmt : node.statements) {
            visit(stmt);
        }
    }

    private void visitFieldAssignment(FieldAssignmentNode node) {
        visit(node.expression);
        code.append("   putfield ").append(node.varName).append("\n");
    }

    private void visitRecordInit(RecordInitNode node) {
        code.append("   new ").append(((UserDefinedTypeNode) node.type).typeName).append("\n");
        code.append("   dup\n");
        
        for (FieldAssignmentNode field : node.fieldAssignments) {
            visit(field.expression); // Evaluate the field's value
        }

        code.append("   invokespecial ").append(((UserDefinedTypeNode) node.type).typeName).append("/<init>(");
        for (FieldAssignmentNode field : node.fieldAssignments) {
            if (field.expression instanceof IntLiteralNode) {
                code.append("I");
            } else if (field.expression instanceof IntLiteralNode) {
                code.append("F");
            }
        }
        code.append(")V\n");
        int inx = allocateVarIndex();
        code.append("   astore_").append(inx).append("\n");

    }

    private void visitModifiablePrimary(ModifiablePrimaryNode node) {
        if (node.expression != null) {
            visit(node.expression);
        } else if (node.accessVarName != null) {
            code.append("getfield ").append(node.varName).append(".").append(node.accessVarName).append("\n");
        } else {
            code.append("load ").append(node.varName).append("\n");
        }
    }

    private void visitIf(IfNode node) {
        String elseLabel = generateLabel("else");
        String endLabel = generateLabel("endif");

        visit(node.condition);
        code.append("ifeq ").append(elseLabel).append("\n");
        for (ASTNode stmt : node.thenBody) {
            visit(stmt);
        }
        code.append("goto ").append(endLabel).append("\n");
        code.append(elseLabel).append(":\n");
        for (ASTNode stmt : node.elseBody) {
            visit(stmt);
        }
        code.append(endLabel).append(":\n");
    }

    private void visitWhileLoop(WhileLoopNode node) {
        String startLabel = generateLabel("while_start");
        String endLabel = generateLabel("while_end");

        code.append(startLabel).append(":\n");
        visit(node.condition);
        code.append("ifeq ").append(endLabel).append("\n");
        for (ASTNode stmt : node.body) {
            visit(stmt);
        }
        code.append("goto ").append(startLabel).append("\n");
        code.append(endLabel).append(":\n");
    }

    private void visitPrint(PrintNode node) {
        code.append("   getstatic java/lang/System/out Ljava/io/PrintStream;\n");
        visit(node.expression);
        ExpressionNode expression = (ExpressionNode) node.expression;
        if (expression instanceof IntLiteralNode) {
            code.append("   invokevirtual java/io/PrintStream/println(I)V\n");
        } else if (expression instanceof RealLiteralNode) {
            code.append("   invokevirtual java/io/PrintStream/println(F)V\n");
        } else if (expression instanceof CharLiteralNode) {
            code.append("   invokevirtual java/io/PrintStream/println(C)V\n");
        } else if (expression instanceof BooleanLiteralNode) {
            code.append("   invokevirtual java/io/PrintStream/println(Z)V\n");
        } else if (expression instanceof VarRefNode) {
            VarDeclNode var = symbolTable.get(((VarRefNode) expression).varName);

            if (var.varType instanceof VarRefNode) {
                // iterator in for loop case
                code.append("   invokevirtual java/io/PrintStream/println(I)V\n");
            } else {
                ExpressionNode type = castPrimitiveToExpression((PrimitiveTypeNode) var.varType);
                if (type instanceof IntLiteralNode) {
                    code.append("   invokevirtual java/io/PrintStream/println(I)V\n");
                } else if (type instanceof RealLiteralNode) {
                    code.append("   invokevirtual java/io/PrintStream/println(F)V\n");
                } else if (type instanceof CharLiteralNode) {
                    code.append("   invokevirtual java/io/PrintStream/println(C)V\n");
                } else if (type instanceof BooleanLiteralNode) {
                    code.append("   invokevirtual java/io/PrintStream/println(Z)V\n");
                } else {
                    // System.out.println(type.getClass());
                    code.append("   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
                }
            }

        } else if (expression instanceof ArrayAccessNode) {

            code.append("   invokevirtual java/io/PrintStream/println(I)V\n");
        } else {

            code.append("   invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n");
        }

    }

    private ExpressionNode castPrimitiveToExpression(PrimitiveTypeNode type) {
        String typeStr = type.type;
        if (typeStr.equals("integer")) {
            return new IntLiteralNode(0);
        } else if (typeStr.equals("real")) {
            return new RealLiteralNode(0.0);
        } else if (typeStr.equals("char")) {
            return new CharLiteralNode(' ');
        } else if (typeStr.equals("boolean")) {
            return new BooleanLiteralNode(false);
        }
        return null;
    }

    private void visitBinaryExpression(BinaryExpressionNode node) {
        visit(node.getLeft());
        visit(node.getRight());
        switch (node.getOperator()) {
            case "+":
                code.append("   iadd\n");
                break;
            case "-":
                code.append("   isub\n");
                break;
            case "*":
                code.append("   imul\n");
                break;
            case "/":
                code.append("   idiv\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + node.getOperator());
        }
    }

    private void visitIntLiteral(IntLiteralNode node) {
        int value = node.value;

        code.append("   ldc ").append(value).append("\n");

    }
}
