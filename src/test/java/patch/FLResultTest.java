package patch;

import gumtree.spoon.FilterWithGT;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FLResultTest {

    @Test
    public void testMatches() {
        String regex = "\\([\\w\\W]+\\)-";
        Pattern pattern1 = Pattern.compile(regex);
        Matcher matcher1 = pattern1.matcher("(str.length() - 1)-147/12/1 0.1");
        if (matcher1.find()) {
            System.out.println(matcher1.group().substring(0, matcher1.group().length() - 1));
            System.out.println("(str.length() - 1)-147/12/1 0.1".substring(matcher1.end()));
        }
    }

    @Test
    public void testReaadGT() {
        ReadTopN.readTopN("math", 85, 10);
    }

    @Test
    public void testSimFix() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/SimFix/result/patch/";
        String baseDir = "/home/liu/Desktop/FLtopN/";
        List<String> projs = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        int i = 0, j = 0, k = 0, topN = 10;
        for (String proj :projs) {
            if (proj.equals("closure"))
                continue;
//            stringBuilder.append(proj).append(":");
            if (!filtered.containsKey(proj)) {
                filtered.put(proj, new ArrayList<>());
            }
            if (!unfiltered.containsKey(proj)) {
                unfiltered.put(proj, new ArrayList<>());
            }
            List<String> versions = FileTools.getDirNames(baseDir + "vars/" + proj + "/");
            for (String p_v :versions) {
                String version = p_v.split("_")[1];
                List<String> patches = FileTools.getDirNames(repairBase + proj);
                if (!patches.contains(version)){
                    continue;
                }
                i ++;
//                System.out.println(proj + version);
                String buggyFileDir = buggyBase + proj +
                        "/" + proj + "_" + version + "_buggy";
                String repairFileDir = repairBase + proj + "/" + version;
                boolean succ = ReadTopN.setGTElements(proj, Integer.parseInt(version), topN);
                if (!succ) {
                    List<String> fd = filtered.get(proj);
                    fd.add(version);
                    j ++;
                    continue;
                }
                List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
                List<File> buggyfiles = new ArrayList<>();
                for (String bu :buggyFilePath) {
                    buggyfiles.add(new File(bu));
                }
                List<String> repairFilePath = FileTools.getFilePaths(repairFileDir, ".java");
                boolean flag = false;
                int total = buggyFilePath.size();
                for (File lf :buggyfiles) {
                    for (String repair : repairFilePath) {
//                        File lf = new File(bu);
                        File rf = new File(repair);
                        String rfn = rf.getName().split("_").length > 1 ? rf.getName().split("_")[1] : rf.getName();
                        if (ReadGT.hasExtraRepair(buggyfiles, rfn)) {
                            flag = true;
                        }
                        if (!lf.getName().equals(rfn)) {
                            continue;
                        }
                        total --;
                        FilterWithGT filter = new FilterWithGT();
                        flag = flag || filter.filterWithGT(filter.getActionsWithFile(lf, rf));
                    }
                }
                if (flag || total > 0) {
//                    stringBuilder.append(" ").append(version);
                    List<String> fd = filtered.get(proj);
                    fd.add(version);
                    j ++;
                } else {
                    List<String> u = unfiltered.get(proj);
                    u.add(version);
                    k ++;
                }
            }
//            stringBuilder.append("\n");
        }
        stringBuilder.append("total bugs number: " + i).append("\n");
        stringBuilder.append("filter number: " + j).append("\n");
        for (String key :filtered.keySet()) {
            List<String> temp =  filtered.get(key);
            stringBuilder.append(key + ": ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("unfiltered number: " + k).append("\n");
        for (String key :unfiltered.keySet()) {
            List<String> temp =  unfiltered.get(key);
            stringBuilder.append(key + ": ");
            stringBuilder.append(temp).append("\n");
        }
        System.out.println(stringBuilder);
    }

    @Test
    public void testTbar() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/TBar/FixedBugs/";
        String baseDir = "/home/liu/Desktop/FLtopN/";
        List<String> proj_versions = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
//        Map<String, PriorityQueue<String>> filterd = new HashMap<>();
        int i = 0, j = 0, k = 0, topN = 10;
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        for (String p_v :proj_versions) {
            String[] temp = p_v.split("_");
            assert temp.length >= 2;
            String proj = temp[0].toLowerCase(Locale.ROOT), version = temp[1];
            if (proj.equals("closure"))
                continue;
            List<String> versions = FileTools.getDirNames(baseDir + "vars/" + proj + "/");
            if (!versions.contains(proj+"_"+version)){
                continue;
            }
            String buggyFileDir = buggyBase + proj +
                    "/" + proj + "_" + version + "_buggy";
            String repair = repairBase + p_v + "/";
            List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
            int total = buggyFilePath.size();
            List<String> repairFilePath = FileTools.getFilePaths(repair, ".java");
            if (repairFilePath.size() == 0) {
                System.err.println("no patch at " + proj + version);
                continue;
            }
            if (!filtered.containsKey(proj)) {
                filtered.put(proj, new ArrayList<>());
            }
            if (!unfiltered.containsKey(proj)) {
                unfiltered.put(proj, new ArrayList<>());
            }
            i ++;
            boolean succ = ReadTopN.setGTElements(proj, Integer.parseInt(version), topN);
            if (!succ) {
                List<String> fd = filtered.get(proj);
                fd.add(version);
                j ++;
                continue;
            }
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
            if (res || total > 0) {
//                stringBuilder.append(" ").append(p_v);
                List<String> fd = filtered.get(proj);
                fd.add(version);
                j ++;
            } else {
                List<String> u = unfiltered.get(proj);
                u.add(version);
                k ++;
            }
        }
//        System.out.println(stringBuilder);
//        System.out.println("total bugs number: " + i);
//        System.out.println("filter number: " + j + "\n");
//
//        for (String key :filterd.keySet()) {
//            PriorityQueue<String> temp =  filterd.get(key);
//            System.out.println(key + ": ");
//            System.out.println(temp);
//        }
        stringBuilder.append("total bugs number: " + i).append("\n");
        stringBuilder.append("filter number: " + j).append("\n");
        for (String key :filtered.keySet()) {
            List<String> temp =  filtered.get(key);
            stringBuilder.append(key + ": ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("unfiltered number: " + k).append("\n");
        for (String key :unfiltered.keySet()) {
            List<String> temp =  unfiltered.get(key);
            stringBuilder.append(key + ": ");
            stringBuilder.append(temp).append("\n");
        }
        System.out.println(stringBuilder);
    }
    public List<String[]> readinfos(String filepath){
        List<String> lines = FileTools.readEachLine(filepath);
        List<String[]> list = new ArrayList<>();
        for (String line :lines) {
            String[] temp = line.split(",");
            assert temp.length == 3;
            String[] infos = new String[5];
            String[] p_v = temp[2].split("_");
            infos[0] = p_v[0] + p_v[1] + "b_" + temp[0];//dirname
            infos[1] = p_v[0];infos[2] = p_v[1];//proj;version
            infos[3] = temp[1];//correctness
            infos[4] = temp[0];//patch id
            list.add(infos);
        }
        return list;
    }
    @Test
    public void testPatchsim() throws Exception {
        String buggyBase = "/home/liu/Desktop/patchsim/";
        String repairBase = buggyBase;
        String baseDir = "/home/liu/Desktop/FLtopN/";
        List<String[]> infos = readinfos(repairBase + "PatchCorrectness.csv");
        String psf = "1, 2, 4, 8, 9, 13, 15, 16, 17, 19, 22, 23, 32, 33, 34, 36, 38, 48, 53, 55, 58, 65, 66, 67, 68, 69, 72, 74, 77, 79, 80, 81, 84, 88, 92, 93, 150, 151, 154, 157, 158, 159, 160, 163, 167, 169, 170, 174, 176, 181, 183, 184, 185, 186, 187, 193, 198, 208, HDRepair5, HDRepair6, HDRepair8, HDRepair9";
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        int i = 0, j = 0, k = 0, topN = 10, c = 0;
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        for (String[] info :infos) {
            String proj = info[1].toLowerCase(Locale.ROOT), version = info[2];
            if (proj.equals("closure"))
                continue;
            List<String> versions = FileTools.getDirNames(baseDir + "vars/" + proj + "/");
            if (!versions.contains(proj+"_"+version) || info[0].equals("Chart14b_Patch188")){
                continue;
            }
            String buggyFileDir = buggyBase + info[0] + "/ori";
            String repair = repairBase + info[0] + "/";
            List<String> buggyFilePath = FileTools.getFilePath(buggyFileDir, ".java");
            int total = buggyFilePath.size();
            List<String> repairFilePath = FileTools.getFilePath(repair, ".java");
            if (!filtered.containsKey(proj)) {
                filtered.put(proj, new ArrayList<>());
            }
            if (!unfiltered.containsKey(proj)) {
                unfiltered.put(proj, new ArrayList<>());
            }
            i ++;
            if (info[3].equals("Correct")) {
                info[4] = info[4] + "*";
                c ++;
            }
            boolean succ = ReadTopN.setGTElements(buggyFilePath, proj, Integer.parseInt(version), topN);
            if (!succ) {
                List<String> fd = filtered.get(proj);
                fd.add(info[4]);
                j ++;
                continue;
            }
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
            if (res || total > 0) {
//                stringBuilder.append(" ").append(p_v);
                List<String> fd = filtered.get(proj);
                fd.add(info[4]);
                j ++;
            } else {
                List<String> u = unfiltered.get(proj);
                u.add(info[4]);
                k ++;
            }
        }
        stringBuilder.append("total bugs number: " + i).append("\n");
        stringBuilder.append("total correct number: " + c).append("\n");
        stringBuilder.append("filter number: " + j).append("\n");
        for (String key :filtered.keySet()) {
            List<String> temp =  filtered.get(key);
            stringBuilder.append(key + ": " + temp.size() + ": ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("unfiltered number: " + k).append("\n");
        for (String key :unfiltered.keySet()) {
            List<String> temp =  unfiltered.get(key);
            stringBuilder.append(key + ": " + temp.size() + ": ");
            stringBuilder.append(temp).append("\n");
        }
        System.out.println(stringBuilder);
    }

    @Test
    public void testCross() throws Exception {
        String buggyBase = "/home/liu/Desktop/patchsim/";
        String repairBase = buggyBase;
        String baseDir = "/home/liu/Desktop/FLtopN/";
        List<String[]> infos = readinfos(repairBase + "PatchCorrectness.csv");
        String psf = "1, 2, 4, 8, 9, 13, 15, 16, 17, 19, 22, 23, 32, 33, 34, 36, 38, 48, 53, 55, 58, 65, 66, 67, 68, 69, 72, 74, 77, 79, 80, 81, 84, 88, 92, 93, 150, 151, 154, 157, 158, 159, 160, 163, 167, 169, 170, 174, 176, 181, 183, 184, 185, 186, 187, 193, 198, 208, HDRepair5, HDRepair6, HDRepair8, HDRepair9";
        List<String> patchids = Arrays.asList(psf.replace(" ", "").split(","));
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0, j = 0;
        HashMap<String, List<String>> filtered = new HashMap<>();
        for (String[] info :infos) {
            String proj = info[1].toLowerCase(Locale.ROOT), version = info[2];
            if (proj.equals("closure"))
                continue;
            List<String> versions = FileTools.getDirNames(baseDir + "vars/" + proj + "/");
            if (!versions.contains(proj+"_"+version) || info[0].equals("Chart14b_Patch188")){
                continue;
            }
            if (!filtered.containsKey(proj)) {
                filtered.put(proj, new ArrayList<>());
            }
            i ++;
            info[4] = info[4].replace("Patch", "");
            if (patchids.contains(info[4])) {
                List<String> fd = filtered.get(proj);
                fd.add("Patch" + info[4]);
                j ++;
            }
        }
        stringBuilder.append("total number: " + i).append("\n");
        stringBuilder.append("patchsim filter patch num: " + j).append("\n");
        for (String key :filtered.keySet()) {
            List<String> temp =  filtered.get(key);
            stringBuilder.append(key + ": " + temp.size() + "--");
            stringBuilder.append(temp).append("\n");
        }
        System.out.println(stringBuilder);
    }
}
