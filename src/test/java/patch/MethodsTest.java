package patch;

import gumtree.spoon.FilterWithGT;
import org.junit.Assert;
import org.junit.Test;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MethodsTest {

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


//    @Test
//    public void testSoucrFrag() throws Exception {
//        String file = "/home/liu/Desktop/groundtruth/buggyfiles/math/math_98_buggy/src/java/org/apache/commons/math/linear/BigMatrixImpl.java";
//        AstComparator comparator = new AstComparator();
//        CtType type = comparator.getCtType(new File(file));
//        try {
//            type.getOriginalSourceFragment();
//        } catch (Exception e) {
//            System.out.println(type.toString());
//        }
//        type.accept(new CtVisitor() {
//            @Override
//            public <A extends Annotation> void visitCtAnnotation(CtAnnotation<A> ctAnnotation) {
//
//            }
//
//            @Override
//            public <T> void visitCtCodeSnippetExpression(CtCodeSnippetExpression<T> ctCodeSnippetExpression) {
//
//            }
//
//            @Override
//            public void visitCtCodeSnippetStatement(CtCodeSnippetStatement ctCodeSnippetStatement) {
//
//            }
//
//            @Override
//            public <A extends Annotation> void visitCtAnnotationType(CtAnnotationType<A> ctAnnotationType) {
//
//            }
//
//            @Override
//            public void visitCtAnonymousExecutable(CtAnonymousExecutable ctAnonymousExecutable) {
//
//            }
//
//            @Override
//            public <T> void visitCtArrayRead(CtArrayRead<T> ctArrayRead) {
//
//            }
//
//            @Override
//            public <T> void visitCtArrayWrite(CtArrayWrite<T> ctArrayWrite) {
//
//            }
//
//            @Override
//            public <T> void visitCtArrayTypeReference(CtArrayTypeReference<T> ctArrayTypeReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtAssert(CtAssert<T> ctAssert) {
//
//            }
//
//            @Override
//            public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> ctAssignment) {
//
//            }
//
//            @Override
//            public <T> void visitCtBinaryOperator(CtBinaryOperator<T> ctBinaryOperator) {
//
//            }
//
//            @Override
//            public <R> void visitCtBlock(CtBlock<R> ctBlock) {
//
//            }
//
//            @Override
//            public void visitCtBreak(CtBreak ctBreak) {
//
//            }
//
//            @Override
//            public <S> void visitCtCase(CtCase<S> ctCase) {
//
//            }
//
//            @Override
//            public void visitCtCatch(CtCatch ctCatch) {
//
//            }
//
//            @Override
//            public <T> void visitCtClass(CtClass<T> ctClass) {
//
//            }
//
//            @Override
//            public void visitCtTypeParameter(CtTypeParameter ctTypeParameter) {
//
//            }
//
//            @Override
//            public <T> void visitCtConditional(CtConditional<T> ctConditional) {
//
//            }
//
//            @Override
//            public <T> void visitCtConstructor(CtConstructor<T> ctConstructor) {
//
//            }
//
//            @Override
//            public void visitCtContinue(CtContinue ctContinue) {
//
//            }
//
//            @Override
//            public void visitCtDo(CtDo ctDo) {
//
//            }
//
//            @Override
//            public <T extends Enum<?>> void visitCtEnum(CtEnum<T> ctEnum) {
//
//            }
//
//            @Override
//            public <T> void visitCtExecutableReference(CtExecutableReference<T> ctExecutableReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtField(CtField<T> ctField) {
//
//            }
//
//            @Override
//            public <T> void visitCtEnumValue(CtEnumValue<T> ctEnumValue) {
//
//            }
//
//            @Override
//            public <T> void visitCtThisAccess(CtThisAccess<T> ctThisAccess) {
//
//            }
//
//            @Override
//            public <T> void visitCtFieldReference(CtFieldReference<T> ctFieldReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtUnboundVariableReference(CtUnboundVariableReference<T> ctUnboundVariableReference) {
//
//            }
//
//            @Override
//            public void visitCtFor(CtFor ctFor) {
//
//            }
//
//            @Override
//            public void visitCtForEach(CtForEach ctForEach) {
//
//            }
//
//            @Override
//            public void visitCtIf(CtIf ctIf) {
//
//            }
//
//            @Override
//            public <T> void visitCtInterface(CtInterface<T> ctInterface) {
//
//            }
//
//            @Override
//            public <T> void visitCtInvocation(CtInvocation<T> ctInvocation) {
//
//            }
//
//            @Override
//            public <T> void visitCtLiteral(CtLiteral<T> ctLiteral) {
//
//            }
//
//            @Override
//            public <T> void visitCtLocalVariable(CtLocalVariable<T> ctLocalVariable) {
//
//            }
//
//            @Override
//            public <T> void visitCtLocalVariableReference(CtLocalVariableReference<T> ctLocalVariableReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtCatchVariable(CtCatchVariable<T> ctCatchVariable) {
//
//            }
//
//            @Override
//            public <T> void visitCtCatchVariableReference(CtCatchVariableReference<T> ctCatchVariableReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtMethod(CtMethod<T> ctMethod) {
//
//            }
//
//            @Override
//            public <T> void visitCtAnnotationMethod(CtAnnotationMethod<T> ctAnnotationMethod) {
//
//            }
//
//            @Override
//            public <T> void visitCtNewArray(CtNewArray<T> ctNewArray) {
//
//            }
//
//            @Override
//            public <T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall) {
//
//            }
//
//            @Override
//            public <T> void visitCtNewClass(CtNewClass<T> ctNewClass) {
//
//            }
//
//            @Override
//            public <T> void visitCtLambda(CtLambda<T> ctLambda) {
//
//            }
//
//            @Override
//            public <T, E extends CtExpression<?>> void visitCtExecutableReferenceExpression(CtExecutableReferenceExpression<T, E> ctExecutableReferenceExpression) {
//
//            }
//
//            @Override
//            public <T, A extends T> void visitCtOperatorAssignment(CtOperatorAssignment<T, A> ctOperatorAssignment) {
//
//            }
//
//            @Override
//            public void visitCtPackage(CtPackage ctPackage) {
//
//            }
//
//            @Override
//            public void visitCtPackageReference(CtPackageReference ctPackageReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtParameter(CtParameter<T> ctParameter) {
//
//            }
//
//            @Override
//            public <T> void visitCtParameterReference(CtParameterReference<T> ctParameterReference) {
//
//            }
//
//            @Override
//            public <R> void visitCtReturn(CtReturn<R> ctReturn) {
//
//            }
//
//            @Override
//            public <R> void visitCtStatementList(CtStatementList ctStatementList) {
//
//            }
//
//            @Override
//            public <S> void visitCtSwitch(CtSwitch<S> ctSwitch) {
//
//            }
//
//            @Override
//            public void visitCtSynchronized(CtSynchronized ctSynchronized) {
//
//            }
//
//            @Override
//            public void visitCtThrow(CtThrow ctThrow) {
//
//            }
//
//            @Override
//            public void visitCtTry(CtTry ctTry) {
//
//            }
//
//            @Override
//            public void visitCtTryWithResource(CtTryWithResource ctTryWithResource) {
//
//            }
//
//            @Override
//            public void visitCtTypeParameterReference(CtTypeParameterReference ctTypeParameterReference) {
//
//            }
//
//            @Override
//            public void visitCtWildcardReference(CtWildcardReference ctWildcardReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtIntersectionTypeReference(CtIntersectionTypeReference<T> ctIntersectionTypeReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtTypeReference(CtTypeReference<T> ctTypeReference) {
//
//            }
//
//            @Override
//            public <T> void visitCtTypeAccess(CtTypeAccess<T> ctTypeAccess) {
//
//            }
//
//            @Override
//            public <T> void visitCtUnaryOperator(CtUnaryOperator<T> ctUnaryOperator) {
//
//            }
//
//            @Override
//            public <T> void visitCtVariableRead(CtVariableRead<T> ctVariableRead) {
//
//            }
//
//            @Override
//            public <T> void visitCtVariableWrite(CtVariableWrite<T> ctVariableWrite) {
//
//            }
//
//            @Override
//            public void visitCtWhile(CtWhile ctWhile) {
//
//            }
//
//            @Override
//            public <T> void visitCtAnnotationFieldAccess(CtAnnotationFieldAccess<T> ctAnnotationFieldAccess) {
//
//            }
//
//            @Override
//            public <T> void visitCtFieldRead(CtFieldRead<T> ctFieldRead) {
//
//            }
//
//            @Override
//            public <T> void visitCtFieldWrite(CtFieldWrite<T> ctFieldWrite) {
//
//            }
//
//            @Override
//            public <T> void visitCtSuperAccess(CtSuperAccess<T> ctSuperAccess) {
//
//            }
//
//            @Override
//            public void visitCtComment(CtComment ctComment) {
//
//            }
//
//            @Override
//            public void visitCtJavaDoc(CtJavaDoc ctJavaDoc) {
//
//            }
//
//            @Override
//            public void visitCtJavaDocTag(CtJavaDocTag ctJavaDocTag) {
//
//            }
//
//            @Override
//            public void visitCtImport(CtImport ctImport) {
//
//            }
//
//            @Override
//            public void visitCtModule(CtModule ctModule) {
//
//            }
//
//            @Override
//            public void visitCtModuleReference(CtModuleReference ctModuleReference) {
//
//            }
//
//            @Override
//            public void visitCtPackageExport(CtPackageExport ctPackageExport) {
//
//            }
//
//            @Override
//            public void visitCtModuleRequirement(CtModuleRequirement ctModuleRequirement) {
//
//            }
//
//            @Override
//            public void visitCtProvidedService(CtProvidedService ctProvidedService) {
//
//            }
//
//            @Override
//            public void visitCtUsedService(CtUsedService ctUsedService) {
//
//            }
//        });
//    }


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
        String repairBase = "src/test/resources/SimFix/result/patch/";
        List<String> projs = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        int i = 0, j = 0, k = 0;
        for (String proj :projs) {
//            stringBuilder.append(proj).append(":");
            if (!filtered.containsKey(proj)) {
                filtered.put(proj, new ArrayList<>());
            }
            if (!unfiltered.containsKey(proj)) {
                unfiltered.put(proj, new ArrayList<>());
            }
            List<String> versions = FileTools.getDirNames(repairBase + proj);
            for (String version :versions) {
                i ++;
//                System.out.println(proj + version);
                String buggyFileDir = buggyBase + proj +
                        "/" + proj + "_" + version + "_buggy";
                String repairFileDir = repairBase + proj + "/" + version;
                GenElements.setGTElements(proj, Integer.parseInt(version));
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
    public void testpatchfile() throws IOException {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/IdeaProjects/gumtree-spoon-ast-diff/gumtree-spoon-ast-diff/src/test/resources/TBar/FixedBugs_P/";
        List<String> proj_versions = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("");
        for (String p_v :proj_versions) {
            String[] temp = p_v.split("_");
            assert temp.length >= 2;
            String proj = temp[0].toLowerCase(Locale.ROOT), version = temp[1];
            System.out.println(proj + version);
            String buggyFileDir = buggyBase + proj +
                    "/" + proj + "_" + version + "_buggy/";
            String repairDir = repairBase + p_v + "/";
            CommandUtil.run("cp -r " + buggyFileDir + " " + repairDir + "a");
            stringBuilder.append(" ").append(repairDir);
        }
        FileTools.writeToFile(stringBuilder.toString(), "patchDirs_P");
    }

    @Test
    public void testTbar() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/TBar/FixedBugs_P/";
        List<String> proj_versions = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
//        Map<String, PriorityQueue<String>> filterd = new HashMap<>();
        int i = 0, j = 0, k = 0, c = 0;
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        HashMap<String, List<String>> cr = new HashMap<>(), icr = new HashMap<>();
        for (String p_v :proj_versions) {
            String[] temp = p_v.split("_");
            assert temp.length >= 2;
            String proj = temp[0].toLowerCase(Locale.ROOT), version = temp[1];
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
            if (!cr.containsKey(proj)) {
                cr.put(proj, new ArrayList<>());
            }
            if (!icr.containsKey(proj)) {
                icr.put(proj, new ArrayList<>());
            }
            if (temp.length == 2) {
                c ++;
                cr.get(proj).add(version);
            } else if (temp.length == 3) {
                icr.get(proj).add(version);
            }
            GenElements.setGTElements(proj, Integer.parseInt(version));
            boolean res = false;
            i ++;
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
        stringBuilder.append("total correct number: " + c).append("\n");
        stringBuilder.append("filter number: " + j).append("\n");
        for (String key :filtered.keySet()) {
            List<String> temp =  filtered.get(key);
            stringBuilder.append(key + "(" + temp.size() + "): ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("unfiltered number: " + k).append("\n");
        for (String key :unfiltered.keySet()) {
            List<String> temp =  unfiltered.get(key);
            stringBuilder.append(key + "(" + temp.size() + "): ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("cr number: " + k).append("\n");
        for (String key :cr.keySet()) {
            List<String> temp =  cr.get(key);
            stringBuilder.append(key + "(" + temp.size() + "): ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("icr number: " + k).append("\n");
        for (String key :icr.keySet()) {
            List<String> temp =  icr.get(key);
            stringBuilder.append(key + "(" + temp.size() + "): ");
            stringBuilder.append(temp).append("\n");
        }
        System.out.println(stringBuilder);
    }

    @Test
    public void testFilterWithLine() throws Exception {
        ReadGT.getBugLines("src/test/resources/BugPositions.txt");
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/SimFix/result/patch/";
        List<String> projs = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        int i = 0, j = 0, k = 0;
        for (String proj :projs) {
            if (!filtered.containsKey(proj)) {
                filtered.put(proj, new ArrayList<>());
            }
            if (!unfiltered.containsKey(proj)) {
                unfiltered.put(proj, new ArrayList<>());
            }
            List<String> versions = FileTools.getDirNames(repairBase + proj);
            for (String version :versions) {
                i ++;
                String buggyFileDir = buggyBase + proj +
                        "/" + proj + "_" + version + "_buggy";
                String repairFileDir = repairBase + proj + "/" + version;
                //
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
                        flag = flag || filter.filterWithLine(filter.getActionsWithFile(lf, rf)
                                , proj + "_" + version, rfn);
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
    public void testgetLineInfos() {
        Map<String, List<LineGroundTruth>> map = ReadGT.getBugLines("src/test/resources/BugPositions.txt");
        System.out.println(map.get("Chart_21").get(0));
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
        String baseDir = "/home/liu/Desktop/groundtruth/";
        List<String[]> infos = readinfos(repairBase + "PatchCorrectness.csv");
        String ps = "1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 36, 37, 38, 44, 45, 46, 47, 48, 49, 51, 53, 54, 55, 58, 59, 62, 63, 64, 65, 66, 67, 68, 69, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 88, 89, 90, 91, 92, 93, 150, 151, 152, 153, 154, 155, 157, 158, 159, 160, 161, 162, 163, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 191, 192, 193, 194, 195, 196, 197, 198, 199, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, HDRepair1, HDRepair3, HDRepair4, HDRepair5, HDRepair6, HDRepair7, HDRepair8, HDRepair9, HDRepair10";
        List<String> patchids = Arrays.asList(ps.replace(" ", "").split(","));
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        int i = 0, j = 0, k = 0, c = 0;
        HashMap<String, List<String>> filtered = new HashMap<>(), unfiltered = new HashMap<>();
        for (String[] info :infos) {
            String proj = info[1].toLowerCase(Locale.ROOT), version = info[2];
            if (!patchids.contains(info[4].replace("Patch", "")))
                continue;
//            if (proj.equals("closure"))
//                continue;
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
            if (info[3].equals("Correct")) {
                info[4] = info[4] + "*";
                c ++;
            }
            boolean succ = GenElements.setGTElements(proj, Integer.parseInt(version));
            if (!succ) {
                System.err.println("no gt: " + info[4]);
                continue;
            }
            i ++;
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
            stringBuilder.append(key + "(" + temp.size() + "): ");
            stringBuilder.append(temp).append("\n");
        }
        stringBuilder.append("\n").append("unfiltered number: " + k).append("\n");
        for (String key :unfiltered.keySet()) {
            List<String> temp =  unfiltered.get(key);
            stringBuilder.append(key + "(" + temp.size() + "): ");
            stringBuilder.append(temp).append("\n");
        }
        System.out.println(stringBuilder);
    }
}