package gumtree.spoon;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.operations.DeleteOperation;
import gumtree.spoon.diff.operations.InsertOperation;
import gumtree.spoon.diff.operations.Operation;
import gumtree.spoon.diff.operations.UpdateOperation;
import patch.*;
import spoon.Launcher;
import spoon.reflect.code.CtExpression;
import spoon.reflect.cu.position.NoSourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.Factory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilterWithGT {
    public static void main(String[] args) throws Exception {
        //args-0 buggyBase /args-1 repairBase
//        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
//        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String buggyBase = args[0];
        String repairBase = args[1];
        String proj = "chart";
        int version = 1;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
    }

    public List<Operation> getActions(String buggyFileDir, String repair) throws Exception {
        List<Operation> actions = new ArrayList<>();
        AstComparator diff = new AstComparator();
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<String> repairFilePath = FileTools.getFilePaths(repair, ".java");
        assert repairFilePath.size() != 0;
        for (String re :repairFilePath) {
            for (String bu :buggyFilePath) {
                Diff result = diff.compare(new File(bu),new File(re));
                actions.addAll(result.getRootOperations());
            }
        }
        return actions;
    }

    public boolean filterWithGT(List<Operation> actions) {
        if (ReadGT.GTs.size() == 0) {
            return false;
        }
        boolean flag = false;
        for (Operation op :actions) {
            if (op instanceof InsertOperation) {
                flag = insertFilter((InsertOperation) op);
            }
            if (op instanceof UpdateOperation) {
                flag = updateFilter((UpdateOperation) op);
            }
            if (op instanceof DeleteOperation) {
                flag = deleteFilter((DeleteOperation) op);
            }
            if (flag)
                break;
        }
        return flag;
    }

    boolean insertFilter(InsertOperation op) {
        CtElement newNode = op.getSrcNode();
        List<CtElement> list = new ArrayList<>();
        for (GroundTruth gt : ReadGT.GTs) {
            List<CtElement> nodes = gt.getNodes();
            for (CtElement exp :nodes) {
                GTFilter filter = new GTFilter();
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
                    GTFilter filter = new GTFilter();
                    filter.set_name(exp.toString());
                    flag = isVarUpdateWithGT(srcode.getElements(filter), newNode.getElements(filter));
                    if (flag)
                        break;
                }
            } else {
                for (CtElement exp : nodes) {
                    flag = isExpChanged(srcode, newNode, exp.toString());
                    if (flag)
                        break;
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
        for (GroundTruth gt : ReadGT.GTs) {
            if (gt.isExp())
                continue;
            List<CtElement> nodes = gt.getNodes();
            for (CtElement exp :nodes) {
                GTFilter filter = new GTFilter();
                filter.set_name(exp.toString());
                list.addAll(srcode.getElements(filter));
            }
        }
        return list.size() == 0;
    }

    public boolean isVarUpdateWithGT(List<CtElement> oldNodes, List<CtElement> newNodes) {
        if ((!newNodes.isEmpty()) && oldNodes.containsAll(newNodes))
            return false;
        if (oldNodes.isEmpty() && newNodes.isEmpty())
            return false;
        return true;
    }

    public boolean isExpChanged(CtElement oldNode, CtElement newNode, String gt) {
        return oldNode.toString().equals(gt) && !oldNode.toString().equals(newNode.toString());
    }

    public boolean fitLineScope(int line) {
        for (GroundTruth gt :ReadGT.GTs) {
            if (gt.isOnlyOneLine()) {
                int one = gt.getLinenumber();
                return one == line;
            } else {
                int start = gt.getStartLineNumber();
                int end = gt.getEndLineNumber();
                return start <= line && end >= line;
            }
        }
        return true;
    }

}
