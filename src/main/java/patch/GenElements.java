package patch;

import gumtree.spoon.AstComparator;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GenElements {
    private static final String buggyFileDir = "/home/liu/Desktop/groundtruth/buggyfiles/";
    private static final String gtFileDir = "/home/liu/Desktop/groundtruth/groundtruth/";

    public static List<CtStatement> getStmts(String buggyfilePath, int pos) throws Exception {
        AstComparator comparator = new AstComparator();
        CtType type = comparator.getCtType(new File(buggyfilePath));
        List<Integer> poses = new ArrayList<>();
        poses.add(pos);
        StamentFilter stamentFilter = new StamentFilter();
        stamentFilter.set_positions(poses);
        List<CtStatement> stmts = type.getElements(stamentFilter);
        return stmts;
    }

    public static List<CtStatement> getStmts(String buggyfilePath, List<Integer> poses) throws Exception {
        AstComparator comparator = new AstComparator();
        CtType type = comparator.getCtType(new File(buggyfilePath));
        StamentFilter stamentFilter = new StamentFilter();
        stamentFilter.set_positions(poses);
        List<CtStatement> stmts = type.getElements(stamentFilter);
        return stmts;
    }

    public static List<CtElement> getNodes(List<CtStatement> stmts, String name) {
        ExpressionFilter filter = new ExpressionFilter();
        filter.set_name(name);
        List<CtElement> list = new ArrayList<>();
        for (CtStatement stmt :stmts) {
            list.addAll(stmt.getElements(filter));
        }
        return list;
    }

    public static void setGTElements(String project, int version) throws Exception {
        String lowerP = project.toLowerCase();
        String buggyFileDir = GenElements.buggyFileDir + lowerP + "/" + lowerP + "_" + version + "_buggy";
        List<String> buggyFilePath = FileTools.getFilePaths(buggyFileDir, ".java");
        List<GroundTruth> gts =  ReadGT.getGTs(gtFileDir + lowerP + ".csv", version);
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
                String name = gt.getName();
                List<CtElement> nodes = getNodes(stmts, name);
                if (nodes.size() == 0) {
                    System.err.println(project + " " + version + " [" + name+ "]");
                }
                nodes = removeSame(nodes);
//                assert nodes.size() != 0;
                gt.setNodes(nodes);
            }
        }
    }

    public static List<CtElement> removeSame(List<CtElement> nodes) {
        List<CtElement> newOne = new ArrayList<>();
        for (CtElement exp :nodes) {
            int pos = exp.getPosition().getLine();
            if (!hasSame(newOne, pos)) {
                newOne.add(exp);
            }
        }
        return newOne;
    }

    public static boolean hasSame(List<CtElement> list, int line) {
        for (CtElement exp :list) {
            int pos = exp.getPosition().getLine();
            if (pos == line)
                return true;
        }
        return false;
    }

//    public static PatchInfo getPatchInfo() {
//        //从json文件中获得patch对应的project和version信息
//        String patchInfo = patchDir + "/INFO/Patch1.json/";
//        String jsonFile = FileTools.readFileByLines(patchInfo);
//        JSONObject jsonObject = JSONObject.parseObject(jsonFile);
//        String project = jsonObject.getString("project");//首字母大写的
//        String version = jsonObject.getString("bug_id");
//
//        //从patch中获得修改位置的信息(所在类的文件路径和修改位置)
//        String patchPath = patchDir + "Patch1";
//        PatchInfo patch = FileTools.getPatchInfo(patchPath);//{path, startline, offset,..}
//
//        String subPath = patch.getSrc().substring(patch.getSrc().indexOf("/")+1);
//        String lowerP = project.toLowerCase();
//        String buggyFilePath = fileDir + lowerP + "/" + lowerP + "_" + version + "_buggy/" + subPath;
////        System.out.println(buggyFilePath);
//        //根据修改位置获得patch修改的方法.
//        return getPatchInfo(buggyFilePath, patchPath);
//    }
//
//    public static PatchInfo getPatchInfo(String buggyFilePath, String patchPath) {
//        PatchInfo patch = FileTools.getPatchInfo(patchPath);//{path, startline, offset,..}
//        CompilationUnit buggy = JavaFile.genAST(buggyFilePath);
//        List<Integer> oriPoses = new ArrayList<>();
//        for (Integer i :patch.getOriOffset()) {
//            oriPoses.add(patch.getChangeStartLine() + i);
//        }
//        patch.setOriginal(getStmts(buggy, oriPoses));
//        System.out.println(patch);
//        return patch;
//    }
//
//    public static Set<CtElement> getElements(Set<CtElement> stmts, String name) {
//        VariableVisitor visitor = new VariableVisitor();
//        Set<CtElement> nodes = new HashSet<>();
//        for (CtElement node :stmts) {
//            nodes.addAll(visitor.getElements(node, name));
//        }
//        return nodes;
//    }
//
//    public static Set<CtElement> getStmts(CtType type , List<Integer> poses) {
//        MethodVisitor visitor = new MethodVisitor();
//        List<MethodDeclaration> MDList = visitor.getMethod(type, poses);
//        StamentFilter stamentFilter = new StamentFilter(type, poses);
//        Set<CtElement> stmts = new HashSet<>();
//        for (MethodDeclaration method: MDList) {
//            stmts.addAll(stamentFilter.getStmts(method));
//        }
//        return stmts;
//    }
//
//    public static Set<CtElement> getStmts(CtType type, int pos) {
//        MethodVisitor visitor = new MethodVisitor();
//        List<MethodDeclaration> MDList = visitor.getMethod(type, pos);
//        List<Integer> poses = new ArrayList<>();
//        poses.add(pos);
//        StamentFilter stamentFilter = new StamentFilter(type, poses);
//        Set<CtElement> stmts = new HashSet<>();
//        for (MethodDeclaration method: MDList) {
//            stmts.addAll(stamentFilter.getStmts(method));
//        }
//        return stmts;
//    }
    public static void compare(PatchInfo patchInfo) {
        Set<CtElement> ori = patchInfo.getOriginal();
        Set<CtElement> modi = patchInfo.getModified();
        if (modi == null) {
            assert ori.size() == 1;
        }
    }

}
