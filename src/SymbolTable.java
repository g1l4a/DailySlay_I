import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private final Stack<Map<String, ASTNode>> scopes;

    public SymbolTable() {
        this.scopes = new Stack<>();
        enterScope(); // Start with a global scope
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
    }

    public void put(String name, ASTNode type) {
        if (contains(name)) {
            throw new SemanticException("Variable '" + name + "' is already declared in this scope.");
        }
        scopes.peek().put(name, type);
    }

    public ASTNode get(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, ASTNode> scope = scopes.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        throw new SemanticException("Variable '" + name + "' is not declared.");
    }

    public boolean contains(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    // Methods for handling routines
    private final Map<String, RoutineInfo> routines = new HashMap<>();

    public void putRoutine(String name, ASTNode returnType, List<ASTNode> parameters) {
        if (containsRoutine(name)) {
            throw new SemanticException("Routine '" + name + "' is already declared.");
        }
        routines.put(name, new RoutineInfo(returnType, parameters));
    }

    public RoutineInfo getRoutine(String name) {
        RoutineInfo routineInfo = routines.get(name);
        if (routineInfo == null) {
            throw new SemanticException("Routine '" + name + "' is not declared.");
        }
        return routineInfo;
    }

    public boolean containsRoutine(String name) {
        return routines.containsKey(name);
    }

    // Inner class to hold routine information
    public static class RoutineInfo {
        private final ASTNode returnType;
        private final List<ASTNode> parameters;

        public RoutineInfo(ASTNode returnType, List<ASTNode> parameters) {
            this.returnType = returnType;
            this.parameters = parameters;
        }

        public ASTNode getReturnType() {
            return returnType;
        }

        public List<ASTNode> getParameters() {
            return parameters;
        }
    }
}

class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, Throwable cause) {
        super(message, cause);
    }
}

