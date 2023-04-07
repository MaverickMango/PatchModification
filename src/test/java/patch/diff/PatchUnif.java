package patch.diff;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;
import org.junit.Test;
import patch.FileTools;
import patch.PatchSameComparator;
import spoon.reflect.declaration.CtType;

import java.io.File;
import java.util.*;

public class PatchUnif {

    String[] projsConsidered = {"Chart", "Math"};//, "Math", "Time", "Lang"

    public void extractFixFiles() {
        String baseDir = "/home/liumengjiao/Desktop/";
        String srcBase = baseDir + "vbaprinfo/d4j_bug_info/src_path/";
        String buggyBase = baseDir + "vbaprinfo/d4j_bug_info/buggyfiles/";
        String groundtruthBase = baseDir + "VBAPRResult/Defects4jProjs_fix/";
        String repairBase = baseDir + "VBAPRResult/";
        List<String> projs = FileTools.getDirNames(groundtruthBase);
        for (String proj :projs) {
            List<String> buggyDirs = FileTools.getDirNames(buggyBase + proj.toLowerCase() + "/");
            for (String buggyDir : buggyDirs) {
                List<String> buggyfilesPath = FileTools.getFilePaths(buggyBase + proj.toLowerCase() + "/" + buggyDir, ".java");
                for (String buggyfilePath :buggyfilesPath) {
                    if (buggyfilePath.contains(".fix"))
                        continue;
                    String infixPath = buggyfilePath.split(buggyBase)[1];
                    infixPath = infixPath.substring(0, infixPath.lastIndexOf("/")).replace("_buggy", "");
                    String fixfilesDir = srcBase + "../fixfiles/" + infixPath;
                    String fixFilePath = groundtruthBase + proj + "/" + proj + "_" + buggyDir.split("_")[1] + "/" + buggyfilePath.split(buggyBase)[1].split("buggy")[1];
                    if (FileTools.isFileExist(fixFilePath))
                        continue;
                    StringBuilder stringBuilder = new StringBuilder("mkdir -p " + fixfilesDir + " && cp ");
                    stringBuilder.append(fixFilePath).append(" ").append(fixfilesDir);
                    String[] cmd = new String[]{"/bin/bash", "-c", stringBuilder.toString().replace("/", File.separator)};
                    List<String> message = FileTools.execute(cmd);
                    System.out.println(message);
                }
            }
        }
    }

    public void extractFixFilesTest4spoon() throws Exception {
        String baseDir = "/home/liumengjiao/Desktop/";
        String srcBase = baseDir + "vbaprinfo/d4j_bug_info/src_path/";
        String fixBase = baseDir + "vbaprinfo/d4j_bug_info/fixfiles/";
        List<String> projs = FileTools.getDirNames(fixBase);
        for (String proj :projs) {
            List<String> buggyDirs = FileTools.getDirNames(fixBase + proj.toLowerCase() + "/");
            for (String buggyDir : buggyDirs) {
                List<String> srcDirPath = FileTools.readEachLine(srcBase + proj.toLowerCase() + "/" + buggyDir.split("_")[1] + ".txt");
                String subPrefix4bug = srcDirPath.get(0);
                List<String> buggyfilesPath = FileTools.getFilePaths(fixBase + proj.toLowerCase() + "/" + buggyDir, ".java");
                for (String buggyfilePath :buggyfilesPath) {
                    String outputfile = fixBase + "/" + proj.toLowerCase() + "/" + buggyDir + "/" + subPrefix4bug;
//                    if (!FileTools.getFilePaths(outputfile, ".java").isEmpty())
//                        continue;
                    AstComparator astComparator = new AstComparator();
                    CtType ctType = astComparator.getCtType(new File(buggyfilePath));
                    PatchSameComparator comparator = new PatchSameComparator();
                    comparator.saveSourceCodeOnDisk(astComparator.getFactory(), ctType, outputfile.replace("fixfiles", "fixfiles4spoon"));
                }
            }
        }
    }
    @Test
    public void test1() throws Exception {
    }

    @Test
    public void test() throws Exception {
        String baseDir = "/home/liumengjiao/Desktop/";
        String srcBase = baseDir + "vbaprinfo/d4j_bug_info/src_path/";
        String groundtruthBase = baseDir + "VBAPRResult/Defects4jProjs_fix/";
        String buggyBase = baseDir + "VBAPRResult/Defects4jProjs/";
        String repairBase = baseDir + "VBAPRResult/";
        List<String> projs = FileTools.getDirNames(groundtruthBase);
        Map<String, String> sameFixList = new HashMap<>();
        Map<String, Integer> proj_version = new HashMap<>();
        try {
            for (String proj : projs) {
                if (!Arrays.asList(projsConsidered).contains(proj))
                    continue;
                System.out.println("---------- Check for Project " + proj);
                List<String> repairDirs = FileTools.getDirNames(repairBase + proj + "/");
                for (String repairDir : repairDirs) {
                    if (repairDir.split(proj).length < 2)
                        continue;
                    String version = repairDir.split(proj)[1].split("_")[1];
                    if (!proj_version.containsKey(proj + "_" + version)) {
                        proj_version.put(proj + "_" + version, 0);
                    }
                    System.out.println("     ----- Check for version " + version);
                    List<String> fixFilesPath = FileTools.getFilePaths(srcBase + "../fixfiles4spoon/" + proj.toLowerCase() + "/" + proj.toLowerCase() + "_" + version, ".java");
                    if (fixFilesPath.isEmpty()) {
                        System.err.println("Fix version file does not exist.");
                        continue;
                    }
                    boolean hasSameFix = false;
                    List<String> patchesDir = FileTools.getDirNames(repairBase + proj + "/" + repairDir + "/src");
                    patchesDir.sort(new Comparator<String>() {
                        public int getID(String patchDir) {
                            if (!patchDir.startsWith("variant"))
                                return -1;
                            return Integer.parseInt(patchDir.split("-")[1].split("_")[0]);
                        }
                        @Override
                        public int compare(String o1, String o2) {
                            return Integer.compare(getID(o1), getID(o2));
                        }
                    });
                    for (String patchDir : patchesDir) {
                        if (hasSameFix)
                            break;
                        if (!patchDir.startsWith("variant") || !patchDir.endsWith("_f"))
                            continue;
                        System.out.println("           " + patchDir);
                        proj_version.put(proj + "_" + version, proj_version.get(proj + "_" + version) + 1);
                        String prefix4patch = repairBase + proj + "/" + repairDir + "/src/" + patchDir;
                        List<String> patchFiles = FileTools.getFilePaths(prefix4patch, ".java");
                        for (String patchFilePath : patchFiles) {
                            if (hasSameFix)
                                break;
                            String buggyFilePostfix = patchFilePath.substring(patchFilePath.lastIndexOf("/"));
                            for (String fixFilePath : fixFilesPath) {
                                String fixFlePathPostfix = fixFilePath.substring(fixFilePath.lastIndexOf("/"));
                                if (!buggyFilePostfix.equals(fixFlePathPostfix))
                                    continue;
                                Diff result = new AstComparator().compare(new File(fixFilePath), new File(patchFilePath));
                                if (result.getRootOperations().isEmpty()) {
                                    hasSameFix = true;
                                    System.out.println("           " + patchDir + ": Solution Found!");
                                    sameFixList.put(proj + "_" + version, patchDir);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------- Finished.");
        StringBuilder stringBuilder = new StringBuilder("Total bugs: ");
        stringBuilder.append(proj_version.size()).append("\n")
                        .append("Average pataches: ").append(proj_version.values().stream().mapToInt(o -> o).average()).append("\n");
        if (!sameFixList.isEmpty()) {
            stringBuilder.append("Solutions Found: ").append(sameFixList.size()).append("\n")
                            .append(FileTools.getMap2String(sameFixList));
//            FileTools.writeToFile(stringBuilder.toString(), outputFile, true);
        } else {
            stringBuilder.append("No Solution Found.");
        }
        System.out.println(stringBuilder);
    }
}
