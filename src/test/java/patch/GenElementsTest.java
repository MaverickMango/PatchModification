package patch;

import gumtree.spoon.AstComparator;
import junit.framework.TestCase;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetInfoFromPatchTest {

    @Test
    public void testgetStmts() throws Exception {
        String buggyFileDir = "/home/liu/Desktop/groundtruth/buggyfiles/" +
                "chart/chart_1_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<CtStatement> stmts = GetInfoFromPatch.getStmts(buggyFilePath.get(0), 1797);
        System.out.println(stmts);
    }

    @Test
    public void testgetVars() throws Exception {
        String buggyFileDir = "/home/liu/Desktop/groundtruth/buggyfiles/" +
                "chart/chart_1_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<CtStatement> stmts = GetInfoFromPatch.getStmts(buggyFilePath.get(0), 1797);
        Set<CtExpression> list = GetInfoFromPatch.getNodes(stmts, "(dataset!=null)");
        System.out.println(list);
    }

    @Test
    public void testExampleMath5() throws Exception {
        GetInfoFromPatch.setGTElements("math", 5);
    }

}