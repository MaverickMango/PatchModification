package patch;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MethodVisitor extends ASTVisitor {

    private CompilationUnit _unit;
    private List<MethodDeclaration> methods = new ArrayList<>();
    private List<Integer> _positions;

    public MethodVisitor(List<Integer> _positions) {
        this._positions = _positions;
    }

    public MethodVisitor() {
    }

    public List<MethodDeclaration> getMethod(CompilationUnit unit, List<Integer> positions) {
        _unit = unit;
        _positions = positions;
        unit.accept(this);
        return methods;
    }

    public List<MethodDeclaration> getMethod(CompilationUnit unit, int position) {
        _unit = unit;
        _positions = new ArrayList<>();
        _positions.add(position);
        unit.accept(this);
        return methods;
    }

    public boolean visit(MethodDeclaration methodDeclaration) {
        int startLine = _unit.getLineNumber(methodDeclaration.getStartPosition());
        int endLine = _unit.getLineNumber(methodDeclaration.getStartPosition()+methodDeclaration.getLength());
        for (int position : _positions) {
            if (startLine < position && endLine > position) {
                if (!methods.contains(methodDeclaration))
                    methods.add(methodDeclaration);
            }
        }
        return true;
    }
}
