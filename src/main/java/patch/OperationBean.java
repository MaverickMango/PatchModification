package patch;

import gumtree.spoon.builder.SpoonGumTreeBuilder;
import gumtree.spoon.diff.operations.*;
import spoon.reflect.cu.position.NoSourcePosition;
import spoon.reflect.declaration.CtElement;

public class OperationBean {
    String type;
    String origin;
    String destination;

    int position = -1;


    public OperationBean() {
    }

    public OperationBean(Operation op) {
        type = op.getAction().getClass().getSimpleName();
        CtElement child = (CtElement) op.getAction().getNode().getMetadata(SpoonGumTreeBuilder.SPOON_OBJECT);
        if (op instanceof UpdateOperation) {
            destination = op.getDstNode().getClass().getSimpleName();
            if (child.getParent() != null) {
                destination += "|" + child.getParent().getClass().getSimpleName();
            }
        } else {
            destination = "";
        }
        if (op instanceof InsertOperation) {
            origin = "";
            destination = op.getSrcNode().getClass().getSimpleName();
            if (child.getParent() != null) {
                destination += "|" + child.getParent().getClass().getSimpleName();
            }
        } else {
            origin = op.getSrcNode().getClass().getSimpleName();
            if (child.getParent() != null) {
                origin += "|" + child.getParent().getClass().getSimpleName();
            }
        }
        if (op instanceof DeleteOperation || op instanceof InsertOperation) {
            CtElement element = op.getSrcNode();
            if (element.getPosition() != null && !(element.getPosition() instanceof NoSourcePosition)) {
                position = element.getPosition().getLine();
            }
        }
        if (op instanceof MoveOperation) {
            CtElement element = (CtElement) op.getAction().getNode().getMetadata(SpoonGumTreeBuilder.SPOON_OBJECT_DEST);
            if (element.getPosition() != null && !(element.getPosition() instanceof NoSourcePosition)) {
                position = element.getPosition().getLine();
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
