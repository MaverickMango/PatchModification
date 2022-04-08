package patch;

import gumtree.spoon.AstComparator;
import gumtree.spoon.FilterWithGT;
import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.operations.Operation;
import org.junit.Assert;
import org.junit.Test;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.*;
import spoon.reflect.visitor.CtVisitor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

public class GenElementsTest {

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
//        String repairBase = "/home/liu/Desktop/SimFix-master/final/result/patch/";
        String repairBase = "src/test/resources/SimFix/result/patch/";
        List<String> projs = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        int i = 0, j = 0;
        for (String proj :projs) {
            stringBuilder.append(proj).append(":");
            List<String> versions = FileTools.getDirNames(repairBase + proj);
            for (String version :versions) {
                i ++;
//                System.out.println(proj + version);
                String buggyFileDir = buggyBase + proj +
                        "/" + proj + "_" + version + "_buggy";
                String repair = repairBase + proj + "/" + version;
                List<String> repairFilePath = FileTools.getFilePaths(repair, ".java");
//                for (:
//                     ) {
//
//                }
                GenElements.setGTElements(proj, Integer.parseInt(version));
                FilterWithGT filter = new FilterWithGT();
                boolean flag = filter.filterWithGT(filter.getActionsWithFile(buggyFileDir, repair));
                if (flag) {
                    stringBuilder.append(" ").append(version);
                    j ++;
                }
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
        System.out.println("total bugs number: " + i);
        System.out.println("filter number: " + j);
    }

    @Test
    public void testpatchfile() throws IOException {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "/home/liu/IdeaProjects/gumtree-spoon-ast-diff/gumtree-spoon-ast-diff/src/test/resources/TBar/FixedBugs/";
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
            buggyFileDir = repairDir + proj + "_" + version + "_buggy";
//            String[] commands = new String[3];
//            commands[0] = "rm -rf " + buggyFileDir;
//            commands[1] = "mv " + repairDir + "*.txt " + repairDir + p_v + ".patch";
//            commands[2] = "patch " + repairDir + "*.java " + repairDir + "*.patch";
//            CommandUtil.run(commands[0]);
////            CommandUtil.run(commands[1]);
////            CommandUtil.run(commands[2]);
//            stringBuilder.append(commands[1]).append("\n");
//            stringBuilder.append(commands[2]).append("\n\n");
        }
//        FileTools.writeToFile(stringBuilder.toString(), "/home/liu/Desktop/patchdirs");
    }

    @Test
    public void testCommand() throws IOException {
        String res = CommandUtil.run("ls /home/liu/IdeaProjects/gumtree-spoon-ast-diff/gumtree-spoon-ast-diff/src/test/resources/TBar/FixedBugs/Chart_14/*.txt");
        System.out.println(res);//no result
    }

    @Test
    public void testTbar() throws Exception {
        String buggyBase = "/home/liu/Desktop/groundtruth/buggyfiles/";
        String repairBase = "src/test/resources/TBar/FixedBugs/";
        List<String> proj_versions = FileTools.getDirNames(repairBase);
        StringBuilder stringBuilder = new StringBuilder("filter:\n");
        int i = 0, j = 0;
        for (String p_v :proj_versions) {
            String[] temp = p_v.split("_");
            assert temp.length >= 2;
            String proj = temp[0].toLowerCase(Locale.ROOT), version = temp[1];
            i ++;
            System.out.println(proj + version);
            String buggyFileDir = buggyBase + proj +
                    "/" + proj + "_" + version + "_buggy";
            String repair = repairBase + p_v + "/" + "";//TODO, patch the buggy file
            GenElements.setGTElements(proj, Integer.parseInt(version));
            FilterWithGT filter = new FilterWithGT();
            boolean flag = filter.filterWithGT(filter.getActions(buggyFileDir, repair));
            if (flag) {
                stringBuilder.append(" ").append(p_v);
                j ++;
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
        System.out.println("total bugs number: " + i);
        System.out.println("filter number: " + j);
    }

}