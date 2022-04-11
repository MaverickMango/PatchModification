package gumtree.spoon;

import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.operations.DeleteOperation;
import gumtree.spoon.diff.operations.InsertOperation;
import gumtree.spoon.diff.operations.Operation;
import gumtree.spoon.diff.operations.UpdateOperation;
import patch.*;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtVariableWrite;
import spoon.reflect.cu.position.NoSourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtVariableReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterWithGT {
    public List<Operation> getActions(String buggyFileDir, String repairFileDir) throws Exception {
        List<Operation> actions = new ArrayList<>();
        AstComparator diff = new AstComparator();
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<String> repairFilePath = FileTools.getFilePaths(repairFileDir, ".java");
        assert repairFilePath.size() != 0;
        for (String re :repairFilePath) {
            for (String bu :buggyFilePath) {
                Diff result = diff.compare(new File(bu),new File(re));
                actions.addAll(result.getRootOperations());
            }
        }
        return actions;
    }

    public List<Operation> getActionsWithFile(File buggyFilePath, File repairFilePath) throws Exception {
        AstComparator diff = new AstComparator();
        Diff result = diff.compare(buggyFilePath, repairFilePath);
        return result.getRootOperations();
    }

    public boolean filterWithGT(List<Operation> actions) {
        if (ReadGT.GTs.size() == 0) {
            return false;
        }
        boolean flag = true;
        for (Operation op :actions) {
            if (op instanceof InsertOperation) {
                flag = flag && insertFilter((InsertOperation) op);
            }
            if (op instanceof UpdateOperation) {
                flag = flag && updateFilter((UpdateOperation) op);
            }
            if (op instanceof DeleteOperation) {
                flag = flag && deleteFilter((DeleteOperation) op);
            }
            if (!flag)
                break;
        }
        return flag;
    }

    boolean insertFilter(InsertOperation op) {
        CtElement newNode = op.getSrcNode();
        List<CtElement> list = new ArrayList<>();
        for (GroundTruth gt : ReadGT.GTs) {
            List<CtElement> nodes = gt.getNodes();
//            GTFilter filter = new GTFilter(newNode);
//            filter.set_name(nodes);
//            list.addAll(newNode.getElements(filter));
            for (CtElement exp :nodes) {
                GTFilter filter = new GTFilter(newNode);
                filter.set_name(exp.toString());
                list.addAll(newNode.getElements(filter));
            }
        }
        return list.size() == 0;
    }

    boolean updateFilter(UpdateOperation op) {
        CtElement srcode = op.getSrcNode();
        CtElement newNode = op.getDstNode();
        int pos = 0;
        if (srcode.getPosition() != null && !(srcode.getPosition() instanceof NoSourcePosition)) {
            pos = srcode.getPosition().getLine();
        }
        if (!fitLineScope(pos)) {
            return true;
        }
        boolean flag = false;
        for (GroundTruth gt : ReadGT.GTs) {
            List<CtElement> nodes = gt.getNodes();
            if (!gt.isExp()) {
                for (CtElement exp : nodes) {
                    GTFilter filter = new GTFilter(srcode);
                    GTFilter filter2 = new GTFilter(newNode);
                    filter.set_name(exp.toString());
                    filter2.set_name(exp.toString());
                    flag = flag || isVarUpdateWithGT(srcode.getElements(filter), newNode.getElements(filter2), exp);
                }
            } else {
                for (CtElement exp : nodes) {
                    flag = flag || isExpChanged(srcode, newNode, exp.toString());
                }
            }
            if (flag)
                break;
        }
        return !flag;
    }

    boolean deleteFilter(DeleteOperation op) {
        CtElement srcode = op.getSrcNode();
        int pos = 0;
        if (srcode.getPosition() != null && !(srcode.getPosition() instanceof NoSourcePosition)) {
            pos = srcode.getPosition().getLine();
        }
        if (!fitLineScope(pos)) {
            return true;
        }
        List<CtElement> list = new ArrayList<>();
        boolean flag = false;
        for (GroundTruth gt : ReadGT.GTs) {
            List<CtElement> nodes = gt.getNodes();
            if (gt.isExp()) {
                for (CtElement exp : nodes) {
                    flag = flag || isExpChanged(srcode, exp.toString());
                }
//                continue;
            }
            for (CtElement exp :nodes) {
                GTFilter filter = new GTFilter(srcode);
                filter.set_name(exp.toString());
                list.addAll(srcode.getElements(filter));
            }
        }
        if (flag)
            return false;
        else return list.size() == 0;
    }

    public boolean isVarUpdateWithGT(List<CtElement> oldNodes, List<CtElement> newNodes, CtElement gtElement) {
        if (oldNodes.isEmpty() && newNodes.isEmpty())
            return false;
        if (newNodes.size() != oldNodes.size())
            return true;
        else {
            if (gtElement instanceof CtVariableReference) {
                boolean b = isTypeChanged(((CtVariableReference<?>) gtElement).getType().toString(),
                        (newNodes.get(0)).toString());// type change
                return b;
            }
            if (gtElement instanceof CtVariableWrite) {
                boolean c = isAsgChanged(gtElement.toString(), newNodes.get(0));
                return c;
            }
        }
        return false;
    }

    private boolean isAsgChanged(String gt, CtElement e) {
        while (e != null && !(e instanceof CtBlock)) {
            if (e instanceof CtAssignment) {
                CtAssignment asg = (CtAssignment) e;
                return asg.getAssigned().toString().equals(gt);
            }
            e = e.getParent();
        }
        return false;
    }

    boolean isTypeChanged(String ori, String modi) {
        return !ori.equals(modi);
    }

    public boolean isExpChanged(CtElement oldNode, CtElement newNode, String gt) {
        return oldNode.toString().equals(gt) && !oldNode.toString().equals(newNode.toString());
    }

    public boolean isExpChanged(CtElement oldNode, String gt) {
        return oldNode.toString().equals(gt);
    }

    public boolean fitLineScope(int line) {
        boolean flag = false;
        for (GroundTruth gt :ReadGT.GTs) {
            if (gt.isOnlyOneLine()) {
                int one = gt.getLinenumber();
                flag = flag || one == line;
            } else {
                int start = gt.getStartLineNumber();
                int end = gt.getEndLineNumber();
                flag = flag || (start <= line && end >= line);
            }
        }
        return flag;
    }

    public boolean filterWithLine(List<Operation> actions, String proj_version, String location) {
        List<LineGroundTruth> lgts = ReadGT.LGTMap.get(proj_version);
        boolean flag = false;
        for (LineGroundTruth lgt :lgts) {
            if (!lgt.getLocation().equals(location))
                continue;
            for (Operation op :actions) {
                if (op instanceof UpdateOperation || op instanceof DeleteOperation) {
                    flag = flag || isChangeContainPos(op.getSrcNode(), lgt);
                }
                if (op instanceof InsertOperation) {
                    flag = true;
                }
                if (!flag)
                    break;
            }
        }
        return !flag;
    }

    boolean isChangeContainPos(CtElement srcode, LineGroundTruth lgt) {
        int start = 0, end = 0;
        if (srcode.getPosition() != null && !(srcode.getPosition() instanceof NoSourcePosition)) {
            start = srcode.getPosition().getLine();
            end = srcode.getPosition().getEndLine();
        }
        List<String> poses = new ArrayList<>();
        while (start <= end) {
            poses.add(String.valueOf(start));
            start ++;
        }
        List<String> lines = lgt.getLinenumbers();
        for (String pos :poses) {
            if (lines.contains(pos))
                return true;
        }
        return false;
    }

}
