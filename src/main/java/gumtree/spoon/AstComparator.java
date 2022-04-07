package gumtree.spoon;

import gumtree.spoon.builder.SpoonGumTreeBuilder;
import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.DiffImpl;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.FactoryImpl;
import spoon.reflect.reference.*;
import spoon.reflect.visitor.CtVisitor;
import spoon.support.DefaultCoreFactory;
import spoon.support.StandardEnvironment;
import spoon.support.compiler.VirtualFile;
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler;

import java.io.File;
import java.lang.annotation.Annotation;

/**
 * Computes the differences between two CtElements.
 *
 * @author Matias Martinez, matias.martinez@inria.fr
 */
public class AstComparator {
    private final Factory factory;

    static {
        // default 0.3
        // it seems that default value is really bad
        // 0.1 one failing much more changes
        // 0.2 one failing much more changes
        // 0.3 one failing test_t_224542
        // 0.4 fails for issue31
        // 0.5 fails for issue31
        // 0.6 OK
        // 0.7 1 failing
        // 0.8 2 failing
        // 0.9 two failing tests with more changes
        // see GreedyBottomUpMatcher.java in Gumtree
        System.setProperty("gumtree.match.bu.sim", "0.6");

        // default 2
        // 0 is really bad for 211903 t_224542 225391 226622
        // 1 is required for t_225262 and t_213712 to pass
        System.setProperty("gumtree.match.gt.minh", "1");

        // default 1000
        // 0 fails
        // 1 fails
        // 10 fails
        // 100 OK
        // 1000 OK
        // see AbstractBottomUpMatcher#SIZE_THRESHOD in Gumtree
        //System.setProperty("gumtree.match.bu.size","10");
        //System.setProperty("gt.bum.szt", "1000");
    }

    public AstComparator() {
        this(new FactoryImpl(new DefaultCoreFactory(), new StandardEnvironment()));
    }

    public AstComparator(Factory factory) {
        this.factory = factory;
        factory.getEnvironment().setNoClasspath(true);
        factory.getEnvironment().setCommentEnabled(false);
    }

    /**
     * compares two java files
     */
    public Diff compare(File f1, File f2) throws Exception {
        return this.compare(getCtType(f1), getCtType(f2));
    }

    /**
     * compares two snippet
     */
    public Diff compare(String left, String right) {
        return compare(getCtType(left), getCtType(right));
    }

    /**
     * compares two AST nodes
     */
    public Diff compare(CtElement left, CtElement right) {
        final SpoonGumTreeBuilder scanner = new SpoonGumTreeBuilder();
        return new DiffImpl(scanner.getTreeContext(), scanner.getTree(left), scanner.getTree(right));
    }

    public CtType getCtType(File file) throws Exception {
        // TODO: we should instead reset the model
        factory.getModel().setBuildModelIsFinished(false);
        SpoonModelBuilder compiler = new JDTBasedSpoonCompiler(factory);
        compiler.getFactory().getEnvironment().setLevel("OFF");
        compiler.addInputSource(SpoonResourceHelper.createResource(file));
        compiler.build();

        if (factory.Type().getAll().size() == 0) {
            return null;
        }

        return factory.Type().getAll().get(0);
    }

    private CtType<?> getCtType(String content) {
        // TODO: we should instead reset the model
        factory.getModel().setBuildModelIsFinished(false);
        SpoonModelBuilder compiler = new JDTBasedSpoonCompiler(factory);
        compiler.addInputSource(new VirtualFile(content, "/test"));
        compiler.build();
        return factory.Type().getAll().get(0);
    }

    public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Usage: DiffSpoon <file_1>  <file_2>");
			return;
		}

//        AstComparator diff = new AstComparator();
//        File fl = new File("src/test/resources/Path.java");
//        File fr = new File("src/test/resources/Path2.java");
//
//        CtType ctType = diff.getCtType(fl);
//
////        CtBinaryOperator<T> operator
//        CtBinaryOperator operator;
//
//        ctType.accept(new CtVisitor() {
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

		final Diff result = new AstComparator().compare(new File(args[0]), new File(args[1]));
		System.out.println(result.toString());
    }
}
