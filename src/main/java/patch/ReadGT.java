package patch;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import patch.GroundTruth;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReadGT {
    public static List<GroundTruth> GTs = null;
    public static Map<String, List<LineGroundTruth>> LGTMap = null;

    public static List<GroundTruth> getGTs(String filePath, int version) {
        String line = getLine(filePath, version);
        return GTs = splitExps(line);
    }

    private static String getLine(String filePath, int version){
        File file = new File(filePath);
        BufferedReader reader = null;
        String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int idx = 0;
            while ((tempString = reader.readLine()) != null) {
                idx ++;
                if (!tempString.startsWith(String.valueOf(version))) {
                    continue;
                }
                tempString.replace('\r', ' ');
                break;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempString;
    }

    private static List<GroundTruth> splitExps(String line) {
        List<GroundTruth> exps = new ArrayList<>();
        String[] oneFile = line.split(":/");
        assert oneFile.length >= 1;
        for(int i = 1; i < oneFile.length; i ++) {
            String temp = oneFile[i];
            String[] gtsWithFile = temp.split("#");
            if (gtsWithFile.length > 1) {
                String path = gtsWithFile[0].replace("/", ".");
//                String path = gtsWithFile[0];
                String[] gts = gtsWithFile[1].split(";");//<var-[start,end]>
                String regex1 = "-\\[[0-9]+,[0-9]+\\]";
                String regex2 = "-\\[[0-9]+\\]";
                for (String gt :gts) {
                    GroundTruth groundTruth = new GroundTruth(path);
                    String name = "";
                    Pattern pattern1 = Pattern.compile(regex1);
                    Matcher matcher1 = pattern1.matcher(gt);
                    while (matcher1.find()) {
                        name = gt.substring(0, matcher1.start());
                        String[] lineNumbers = matcher1.group()
                                .replace("-[", "")
                                .replace("]", "").split(",");
                        assert lineNumbers.length == 2;
                        groundTruth.setName(name);
                        groundTruth.setStartLineNumber(Integer.parseInt(lineNumbers[0]));
                        groundTruth.setEndLineNumber(Integer.parseInt(lineNumbers[1]));
                        exps.add(groundTruth);
                        continue;
                    }
                    Pattern pattern2 = Pattern.compile(regex2);
                    Matcher matcher2 = pattern2.matcher(gt);
                    while (matcher2.find()) {
                        name = gt.substring(0, matcher2.start());
                        String lineNumber = matcher2.group()
                                .replace("-[", "")
                                .replace("]", "");
                        groundTruth.setName(name);
                        groundTruth.setLinenumber(Integer.parseInt(lineNumber));
                        exps.add(groundTruth);
                    }
                }
            }
        }
        return exps;
    }

    public static boolean hasExtraRepair(List<File> buggyfiles, String repairFileName) {
        List<String> filenames = new ArrayList<>();
        for (File file :buggyfiles) {
            filenames.add(file.getName());
        }
        return !filenames.contains(repairFileName);
    }

    public static Map<String, List<LineGroundTruth>> getBugLines(String bugpositions) {
        List<String> list = FileTools.readEachLine(bugpositions);
        Map<String, List<LineGroundTruth>> map = new HashMap<>();
        for (String line :list) {
            String[] temp = line.split("@");
            assert temp.length == 3;
            String p_v = temp[0];
            String location = temp[1];
            if (!map.containsKey(p_v)) {
                map.put(p_v.toLowerCase(), new ArrayList<>());
            }
            LineGroundTruth lgt = new LineGroundTruth();
            lgt.setProj(p_v.split("_")[0].toLowerCase());
            lgt.setVersion(p_v.split("_")[1]);
            lgt.setLocation(location.substring(location.lastIndexOf("/") + 1));
            List<String> lines = new ArrayList<>();
            String[] ls = temp[2].split(",");
            for (String l :ls) {
                if (!l.contains("-")) {
                    lines.add(l);
                    continue;
                }
                String[] start2End = l.split("-");
                assert start2End.length == 2;
                for (int i = Integer.parseInt(start2End[0]); i <= Integer.parseInt(start2End[1]); i++) {
                    lines.add(String.valueOf(i));
                }
            }
            lgt.setLinenumbers(lines);
            List<LineGroundTruth> lgts = map.get(p_v.toLowerCase());
            lgts.add(lgt);
        }
        LGTMap = map;
        return map;
    }
}
