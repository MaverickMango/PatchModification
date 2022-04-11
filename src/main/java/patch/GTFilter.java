package patch;


import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;

import java.util.ArrayList;
import java.util.List;

public class GTFilter extends ExpressionFilter {
    private CtElement root = null;
    private List<String> _names = new ArrayList<>();

    public GTFilter(CtElement root) {
        this.root = root;
    }

    public void set_name(List<CtElement> nodes) {
        for (CtElement e :nodes) {
            _names.add(e.toString());
        }
    }

    boolean compare(String str) {
        boolean flag = super.compare(str);
        for (String name :_names) {
            flag = flag || (name.equals(str));
        }
        return flag;
    }

    @Override
    public boolean matches(CtElement element) {
        if (element instanceof CtExpression || element instanceof CtVariableReference || element instanceof CtTypeReference) {
            if (element instanceof CtThisAccess || element instanceof CtSuperAccess
                    )//
                return false;
            String str = element.toString();
            boolean flag = compare(str) && !isParentMoved(element);
            if (flag)
                return flag;
            CtElement lhs = findAssignmentLHS(element);
            if (lhs instanceof CtVariableReference) {
                boolean a = compare(((CtVariableReference<?>) lhs).getSimpleName()) && !isParentMoved(lhs);//only a true
                return a;
            } else if (lhs instanceof CtVariableWrite) {
                boolean a = compare(lhs.toString()) && !isParentMoved(lhs);
                return a;
            }
        }
        return false;
    }

    boolean isParentMoved(CtElement e) {
        while (e != null && e != root) {
            if (e.getMetadata("isMoved") != null) {
                return true;
            }
            e = e.getParent();
        }
        return false;
    }

    CtElement findAssignmentLHS(CtElement e) {
        while (e != null && !(e instanceof CtBlock)) {
            if (e instanceof CtAssignment) {
                CtAssignment asg = (CtAssignment) e;
                return asg.getAssigned();
            }
            if (e instanceof CtLocalVariable) {
                CtLocalVariable lv = (CtLocalVariable) e;
                return lv.getReference();
            }
            e = e.getParent();
        }
        return null;
    }

}
