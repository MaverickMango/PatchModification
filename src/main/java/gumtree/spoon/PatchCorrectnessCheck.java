package gumtree.spoon;

import patch.PatchSameComparator;

public class PatchCorrectnessCheck {

    public static void main(String[] args) {
        String baseDir = "/home/liumengjiao/Desktop/";
        String srcBase = baseDir + "vbaprinfo/d4j_bug_info/src_path/";
        String groundtruthBase = baseDir + "VBAPRResult/Defects4jProjs_fix/";
        String buggyBase = baseDir + "VBAPRResult/Defects4jProjs/";
        String repairBase = baseDir + "VBAPRResult/";
        PatchSameComparator comparator = new PatchSameComparator();
        comparator.cmpAstChange4VBAPR_astor(srcBase, groundtruthBase, buggyBase, repairBase, new String[]{"Chart", "Math"}, repairBase);
    }

}
