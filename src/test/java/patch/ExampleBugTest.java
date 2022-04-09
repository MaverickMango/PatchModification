package patch;

import gumtree.spoon.FilterWithGT;
import org.junit.Assert;
import org.junit.Test;

public class ExampleBugTest {

    @Test
    public void testExampleMath5() throws Exception {
        GenElements.setGTElements("math", 5);
    }

    @Test
    public void testExampleMath75() throws Exception { //error
        GenElements.setGTElements("math", 75);
    }

    @Test
    public void testExamplechart20() throws Exception { //error
        GenElements.setGTElements("chart", 20);
    }

    @Test
    public void testExamplechart22() throws Exception { //error
        GenElements.setGTElements("chart", 20);
    }

    @Test
    public void testExampleMath98() throws Exception {
        GenElements.setGTElements("math", 98);
    }

    @Test
    public void testExampleMath59() throws Exception {
        GenElements.setGTElements("math", 59);
    }

    @Test
    public void testExampleChart18() throws Exception {
        GenElements.setGTElements("chart", 18);
    }

    @Test
    public void testExampleClosure68() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "closure";
        int version = 115;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, version);
        FilterWithGT filter = new FilterWithGT();
        boolean g = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
    }

    @Test
    public void testExamplelang18() throws Exception {
        GenElements.setGTElements("lang", 45);
    }

    @Test
    public void testExamplelang58() throws Exception {
        GenElements.setGTElements("lang", 58);
    }

    @Test
    public void testExampleChart8() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/TBar/FixedBugs/";
        String proj = "chart";
        int version = 8;
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repair = "/home/liu/IdeaProjects/gumtree-spoon-ast-diff/gumtree-spoon-ast-diff/src/test/resources/TBar/FixedBugs/Chart_8/";
        GenElements.setGTElements(proj, version);
        FilterWithGT filter = new FilterWithGT();
        boolean g = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
        Assert.assertFalse(g);//error filter cause by the operation type! this is actually update but delete&insert in this code
    }

    @Test
    public void testExampleTime11() throws Exception {
        GenElements.setGTElements("time", 11);//not consider field
    }

    @Test
    public void testExamplemath89() throws Exception {
        GenElements.setGTElements("math", 89);
    }

    @Test
    public void testExamplemockito29() throws Exception {
        GenElements.setGTElements("mockito", 29);
    }
}
