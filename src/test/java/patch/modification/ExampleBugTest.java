package patch.modification;

import gumtree.spoon.FilterWithGT;
import org.junit.Assert;
import org.junit.Test;
import patch.FileTools;
import patch.GenElements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExampleBugTest {

    @Test
    public void testExampleMath5() throws Exception {
        GenElements.setGTElements("math", 5);
    }

    @Test
    public void testExampleMath75() throws Exception {
        GenElements.setGTElements("math", 75);
    }

    @Test
    public void testExamplechart20() throws Exception {
        GenElements.setGTElements("chart", 20);
    }

    @Test
    public void testExamplechart22() throws Exception {
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
    public void testExamplemath79() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "math";
        String version = "71";
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repairFileDir = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, Integer.parseInt(version));
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<String> repairFilePath = FileTools.getFilePaths(repairFileDir, ".java");
        boolean flag = false;
        int total = buggyFilePath.size();
        for (String bu :buggyFilePath) {
            for (String repair : repairFilePath) {
                File lf = new File(bu);
                File rf = new File(repair);
                String rfn = rf.getName().split("_").length > 0 ? rf.getName().split("_")[1] : rf.getName();
                if (!lf.getName().equals(rfn)) {
                    continue;
                }
                total --;
                FilterWithGT filter = new FilterWithGT();
                flag = flag || filter.filterWithGT(filter.getActionsWithFile(lf, rf));
            }
        }
        if (total > 0) {
            flag = true;
        }
        Assert.assertFalse(flag);
    }

    @Test
    public void testExamplemath73() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "math";
        String version = "73";
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repairFileDir = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, Integer.parseInt(version));
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<String> repairFilePath = FileTools.getFilePaths(repairFileDir, ".java");
        boolean flag = false;
        int total = buggyFilePath.size();
        for (String bu :buggyFilePath) {
            for (String repair : repairFilePath) {
                File lf = new File(bu);
                File rf = new File(repair);
                String rfn = rf.getName().split("_").length > 0 ? rf.getName().split("_")[1] : rf.getName();
                if (!lf.getName().equals(rfn)) {
                    continue;
                }
                total --;
                FilterWithGT filter = new FilterWithGT();
                flag = flag || filter.filterWithGT(filter.getActionsWithFile(lf, rf));
            }
        }
        if (total > 0) {
            flag = true;
        }
        Assert.assertFalse(flag);
    }

    @Test
    public void testExamplelang18() throws Exception {
        GenElements.setGTElements("lang", 45);
    }

    @Test
    public void testExamplelang58() throws Exception { //ast position is different with gt
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "lang";
        String version = "58";
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repairFileDir = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, Integer.parseInt(version));
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<String> repairFilePath = FileTools.getFilePaths(repairFileDir, ".java");
        boolean flag = false;
        int total = buggyFilePath.size();
        for (String bu :buggyFilePath) {
            for (String repair : repairFilePath) {
                File lf = new File(bu);
                File rf = new File(repair);
                String rfn = rf.getName().split("_").length > 0 ? rf.getName().split("_")[1] : rf.getName();
                if (!lf.getName().equals(rfn)) {
                    continue;
                }
                total --;
                FilterWithGT filter = new FilterWithGT();
                flag = flag || filter.filterWithGT(filter.getActionsWithFile(lf, rf));
            }
        }
        if (total > 0) {
            flag = true;
        }
        Assert.assertFalse(flag);
    }

    @Test
    public void testExamplelang27() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String proj = "lang";
        String version = "27";
        String buggyFileDir = buggyBase + proj +
                "/" + proj + "_" + version + "_buggy";
        String repairFileDir = repairBase + proj + "/" + version;
        GenElements.setGTElements(proj, Integer.parseInt(version));
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<String> repairFilePath = FileTools.getFilePaths(repairFileDir, ".java");
        boolean flag = false;
        int total = buggyFilePath.size();
        for (String bu :buggyFilePath) {
            for (String repair : repairFilePath) {
                File lf = new File(bu);
                File rf = new File(repair);
                String rfn = rf.getName().split("_").length > 0 ? rf.getName().split("_")[1] : rf.getName();
                if (!lf.getName().equals(rfn)) {
                    continue;
                }
                total --;
                FilterWithGT filter = new FilterWithGT();
                flag = flag || filter.filterWithGT(filter.getActionsWithFile(lf, rf));
            }
        }
        if (total > 0) {
            flag = true;
        }
        Assert.assertFalse(flag);
    }

    @Test
    public void testExamplemath65() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/TBar/FixedBugs_P/";
            String proj = "Chart";
        String version = "14";
        String repair = repairBase + proj + "_" + version + "";
        String buggyFileDir = buggyBase + proj.toLowerCase() +
                "/" + proj.toLowerCase() + "_" + version + "_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        int total = buggyFilePath.size();
        List<String> repairFilePath = FileTools.getFilePaths(repair, ".java");
        GenElements.setGTElements(proj, Integer.parseInt(version));
        boolean res = false;
        for (String bu :buggyFilePath) {
            for (String path :repairFilePath) {
                File lf = new File(bu);
                File rf = new File(path);
                String rfn = rf.getName();
                if (!lf.getName().equals(rfn)) {
                    continue;
                }
                total --;
                FilterWithGT filter = new FilterWithGT();
                boolean flag = filter.filterWithGT(filter.getActionsWithFile(lf, rf));
                res = res || flag;
            }
        }
        boolean flag = false;
        if (res || total > 0) {
            flag = true;
        }
        Assert.assertFalse(flag);
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

    @Test
    public void testExampleClosure14() throws Exception {
        GenElements.setGTElements("closure", 14);
    }
}
