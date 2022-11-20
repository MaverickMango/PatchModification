package patch;

import gumtree.spoon.AstComparator;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadTopN {
    static String baseDir = "/home/liu/Desktop/FLtopN/";

    public static List<GroundTruth> readTopN(String proj, int version, int topN) {
        proj = proj.toLowerCase();
        String methodFile = baseDir + "method/" + proj + "/" + proj + "_" + version + "/instrumented_method_id.txt";
        Map<String, String> map = getLocations(methodFile);
        if (map == null)
            return null;
//        System.out.println(map);
        String varFile = baseDir + "vars/" + proj + "/" + proj + "_" + version + "/ordered_vars2.txt";
        List<String> varLines = FileTools.readEachLine(varFile);
//        assert varLines.size() <= topN;
//        System.out.println(varLines);
        List<GroundTruth> gts = splitExps(varLines, map, topN);
        return ReadGT.GTs = gts;
    }

    public static boolean setGTElements(String project, int version, int topN) throws Exception {
        String lowerP = project.toLowerCase();
        String buggyFileDir = GenElements.buggyFileDir + lowerP + "/" + lowerP + "_" + version + "_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<GroundTruth> gts = readTopN(project, version, topN);
        List<GroundTruth> haveNodes = new ArrayList<>();
        if (gts == null || gts.size() == 0) {
            return false;
        }
        for (String str :buggyFilePath) {
            AstComparator comparator = new AstComparator();
            CtType type = comparator.getCtType(new File(str));
            for (GroundTruth gt :gts) {
                if (!str.replace(".java", "")
                        .replace("/", ".").endsWith(gt.getLocation())) {
                    continue;
                }
                List<Integer> poses = new ArrayList<>();
                if (gt.isOnlyOneLine()) {
                    poses.add(gt.getLinenumber());
                } else {
                    int start = gt.getStartLineNumber();
                    int end = gt.getEndLineNumber();
                    while (start <= end) {
                        poses.add(start);
                        start ++;
                    }
                }
                StamentFilter stamentFilter = new StamentFilter();
                stamentFilter.set_positions(poses);
                List<CtStatement> stmts = type.getElements(stamentFilter);
                stmts = GenElements.removeSame(stmts, poses);
                String name = gt.getName();
                List<CtElement> nodes = GenElements.getNodes(stmts, name);
//                if (nodes.size() == 0) {
//                nodes = removeSame(nodes);
//                assert nodes.size() != 0;
//                    System.err.println(project + " " + version + " [" + name+ "]");
//                }
                gt.setNodes(nodes);
                haveNodes.add(gt);
            }
        }
        ReadGT.GTs = haveNodes;
        return haveNodes.size() != 0;
    }

    public static boolean setGTElements(List<String> buggyFilePath,String project, int version, int topN) throws Exception {
        List<GroundTruth> gts = readTopN(project, version, topN);
        List<GroundTruth> haveNodes = new ArrayList<>();
        if (gts == null || gts.size() == 0) {
            return false;
        }
        for (String str :buggyFilePath) {
            AstComparator comparator = new AstComparator();
            CtType type = comparator.getCtType(new File(str));
            for (GroundTruth gt :gts) {
                str = str.substring(str.lastIndexOf("/"));
                if (!gt.getLocation().endsWith(str.replace(".java", "").replace("/", "."))) {
                    continue;
                }
                List<Integer> poses = new ArrayList<>();
                if (gt.isOnlyOneLine()) {
                    poses.add(gt.getLinenumber());
                } else {
                    int start = gt.getStartLineNumber();
                    int end = gt.getEndLineNumber();
                    while (start <= end) {
                        poses.add(start);
                        start ++;
                    }
                }
                StamentFilter stamentFilter = new StamentFilter();
                stamentFilter.set_positions(poses);
                List<CtStatement> stmts = type.getElements(stamentFilter);
                stmts = GenElements.removeSame(stmts, poses);
                String name = gt.getName();
                List<CtElement> nodes = GenElements.getNodes(stmts, name);
//                if (nodes.size() == 0) {
//                nodes = removeSame(nodes);
//                assert nodes.size() != 0;
//                    System.err.println(project + " " + version + " [" + name+ "]");
//                }
                gt.setNodes(nodes);
                haveNodes.add(gt);
            }
        }
        ReadGT.GTs = haveNodes;
        return haveNodes.size() != 0;
    }

    private static List<String> getLines(String filePath, int topN){
        File file = new File(filePath);
        BufferedReader reader = null;
        String tempString = null;
        List<String> topList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            int idx = 0;
            while ((tempString = reader.readLine()) != null) {
                idx ++;
                if (idx <= topN) {
                    tempString.replace('\r', ' ');
                    topList.add(tempString);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topList;
    }

    private static Map<String, String> getLocations(String filePath) {
        Map<String, String> locations = new HashMap<>();
        File file = new File(filePath);
        BufferedReader reader = null;
        String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine()) != null) {
                 String[] s = tempString.replace('\r', ' ').split(":");
                 assert s.length == 2;
                 String clz = s[1].split("#")[0];
                 locations.put(s[0], clz);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        return locations;
    }

    private static List<GroundTruth> splitExps(List<String> lines, Map<String, String> map, int topN) {
        List<GroundTruth> exps = new ArrayList<>();
        List<Double> rank = new ArrayList<>();
        String lastscore = "-100000";
        for (int i = 0; i < lines.size() && i < 20; i ++) {
            String line = lines.get(i);
            String[] s = line.split("#");
            assert s.length == 2;
            GroundTruth gt = new GroundTruth(map.get(s[0]));
            String[] varinfos = s[1].split("-");
            if (varinfos.length != 2){
                String regex = "\\([\\w\\W]+\\)-";
                Pattern pattern1 = Pattern.compile(regex);
                Matcher matcher1 = pattern1.matcher(s[1]);
                if (matcher1.find()) {
                    varinfos[0] = matcher1.group().substring(0, matcher1.group().length() - 1);
                    varinfos[1] = s[1].substring(matcher1.end());
                }
            }
            gt.setName(varinfos[0]);
            String[] lineInfos = varinfos[1].split(" ");
            assert lineInfos.length == 2;
            String score = lineInfos[1];
            if (lastscore.equals(score)) {
                rank.add(rank.get(i - 1));
                int updateStart = findBefore(i - 1, rank);
                double newRank = ((i - updateStart)*rank.get(updateStart) + i+1)/(i - updateStart + 1);
                freshRank(rank, updateStart, newRank);
            } else {
                rank.add((double) (i + 1));
            }
            lastscore = score;
            if (!lineInfos[0].contains("{")) {
                String[] ls = lineInfos[0].split("/");
                gt.setLinenumber(Integer.parseInt(ls[0]));
            } else {
                String[] ls = lineInfos[0].substring(1, lineInfos[0].length() - 1).split(";");
                String start = ls[0].split("/")[0];
                String end = ls[ls.length - 1].split("/")[0];
                gt.setStartLineNumber(Integer.parseInt(start));
                gt.setEndLineNumber(Integer.parseInt(end));
            }
            exps.add(gt);
        }
        List<GroundTruth> gts = new ArrayList<>();
        for (int i = 0; i < rank.size(); i++) {
            double r = rank.get(i);
            if (r > (double)topN) {
                break;
            }
            gts.add(exps.get(i));
        }
        return gts;
    }

    public static int findBefore(int idx, List<Double> rank) {
        for (int i = idx; i >= 0; i --) {
            if (!rank.get(i + 1).equals(rank.get(i))) {
                return i + 1;
            }
        }
        return 0;
    }

    public static void freshRank(List<Double> rank, int start, double newRank) {
        for (int i = start; i < rank.size(); i++) {
            rank.set(i, newRank);
        }
    }
}
