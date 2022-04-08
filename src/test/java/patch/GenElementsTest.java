package patch;

import gumtree.spoon.AstComparator;
import gumtree.spoon.FilterWithGT;
import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.operations.Operation;
import org.junit.Assert;
import org.junit.Test;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;

import java.io.File;
import java.util.List;
import java.util.Set;

public class GenElementsTest {

    @Test
    public void testgetStmts() throws Exception {
        String buggyFileDir = "/home/liu/Desktop/groundtruth/buggyfiles/" +
                "chart/chart_1_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<CtStatement> stmts = GenElements.getStmts(buggyFilePath.get(0), 1797);
        System.out.println(stmts);
    }

    @Test
    public void testgetVars() throws Exception {
        String buggyFileDir = "/home/liu/Desktop/groundtruth/buggyfiles/" +
                "chart/chart_1_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<CtStatement> stmts = GenElements.getStmts(buggyFilePath.get(0), 1797);
        List<CtElement> list = GenElements.getNodes(stmts, "(dataset!=null)");
        System.out.println(list);
    }

    @Test
    public void testExampleMath5() throws Exception {
        GenElements.setGTElements("math", 5);
    }

    @Test
    public void testExampleMath75() throws Exception { //error
        GenElements.setGTElements("math", 75);
    }

    @Test
    public void testExampleMath98() throws Exception {
        GenElements.setGTElements("math", 98);
    }


    @Test
    public void testGetActions() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "chart";
        int version = 1;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
        FilterWithGT filter = new FilterWithGT();
        System.out.println(filter.getActions(buggyFileDir, repair));
    }

    @Test
    public void testUpdateVarCompare() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "math";
        int version = 5;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, version);
        FilterWithGT filter = new FilterWithGT();
        boolean g = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
        Assert.assertFalse(g);
    }

    @Test
    public void testUpdateExpCompare() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "chart";
        int version = 1;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, version);
        FilterWithGT filter = new FilterWithGT();
        boolean g = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
        Assert.assertFalse(g);
    }

    @Test
    public void testInsertCompare() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "chart";
        int version = 3;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, version);
        FilterWithGT filter = new FilterWithGT();
        boolean g = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
        Assert.assertTrue(g);//chart-3, a correct patch, incorrect filter!
    }

    @Test
    public void testInsertCompare1() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "chart";
        int version = 12;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, version);
        FilterWithGT filter = new FilterWithGT();
        boolean g = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
        Assert.assertFalse(g);//chart-12, a incorrect patch,cannot filter! cause of No GT!
    }

    @Test
    public void testGetDir() {
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        System.out.println(FileTools.getDirNames(repairBase));
    }

    @Test
    public void testSimFix() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        List<String> projs = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        int i = 0;
        for (String proj :projs) {
            stringBuilder.append(proj).append(":");
            List<String> versions = FileTools.getDirNames(repairBase + proj);
            for (String version :versions) {
                i ++;
                String buggyFileDir = buggyBase + proj +
                        "/" + proj + "_" + version + "_buggy";
                String repair = repairBase + proj + "/" + version;
                GenElements.setGTElements(proj, Integer.parseInt(version));
                FilterWithGT filter = new FilterWithGT();
                boolean flag = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
                if (flag)
                    stringBuilder.append(" ").append(version);
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
        System.out.println("total bugs number: " + i);
    }

}