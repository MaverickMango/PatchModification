package patch;


import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtSuperAccess;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtVariable;

public class GTFilter extends ExpressionFilter {

    String compactStr(String str) {
        return str.trim().replace(" ", "")
                .replace("\n", "")
                .replace("\t", "");
    }

    @Override
    public boolean matches(CtElement element) {
        if (element instanceof CtExpression || element instanceof CtVariable) {
            if (element instanceof CtThisAccess || element instanceof CtSuperAccess
                    || element instanceof CtTypeAccess)
                return false;
            String str = element.toString();
            return compare(str);
        }
        return false;
    }
}
