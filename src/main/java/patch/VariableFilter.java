package patch;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.visitor.filter.AbstractFilter;

public class VariableFilter extends AbstractFilter<CtExpression> {
    private String _name;

    public void set_name(String name) {
        this._name = compactStr(name);
    }

    boolean compare(String str) {
        if (_name.equals(compactStr(str))) {
            return true;
        }
        return false;
    }

    String compactStr(String str) {
        if (str.startsWith("(") && str.endsWith(")"))
            str = str.substring(1, str.length() - 1);
        return str.trim().replace(" ", "")
                .replace("\n", "")
                .replace("\t", "");
    }

    @Override
    public boolean matches(CtExpression element) {
        if (element instanceof CtThisAccess || element instanceof CtSuperAccess
                || element instanceof CtTypeAccess)
            return false;
        String str = "";
        try {
            str = element.getOriginalSourceFragment().getSourceCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compare(str);
    }
}
