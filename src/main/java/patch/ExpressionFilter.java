package patch;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.filter.AbstractFilter;

public class ExpressionFilter extends AbstractFilter<CtElement> {
    private String _name;

    public void set_name(String name) {
        this._name = compactStr(name);
    }

    boolean compare(String str) {
        String name = _name;
        if (name.startsWith("(") && name.endsWith(")"))
            name = name.substring(1, name.length() - 1);
        if (name.equals(compactStr(str))){
            return true;
        }
        return _name.equals(compactStr(str));
    }

    String compactStr(String str) {
        return str.trim().replace(" ", "")
                .replace("\n", "")
                .replace("\t", "");
    }

    @Override
    public boolean matches(CtElement element) {
        if (element instanceof CtExpression) {
            if (element instanceof CtThisAccess || element instanceof CtSuperAccess
                    || element instanceof CtTypeAccess)
                return false;
            String str = "";
            try {
                str = element.getOriginalSourceFragment().getSourceCode();
            } catch (Exception e) {
                str = element.toString();
//                System.err.println(str);
            }
            return compare(str);
        }
        return false;
    }
}
