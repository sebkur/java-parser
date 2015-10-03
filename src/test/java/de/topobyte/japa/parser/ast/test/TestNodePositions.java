/*
 * Created on 30/06/2008
 */
package de.topobyte.japa.parser.ast.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import de.topobyte.japa.parser.ast.BlockComment;
import de.topobyte.japa.parser.ast.CompilationUnit;
import de.topobyte.japa.parser.ast.ImportDeclaration;
import de.topobyte.japa.parser.ast.LineComment;
import de.topobyte.japa.parser.ast.Node;
import de.topobyte.japa.parser.ast.PackageDeclaration;
import de.topobyte.japa.parser.ast.TypeParameter;
import de.topobyte.japa.parser.ast.body.AnnotationDeclaration;
import de.topobyte.japa.parser.ast.body.AnnotationMemberDeclaration;
import de.topobyte.japa.parser.ast.body.ClassOrInterfaceDeclaration;
import de.topobyte.japa.parser.ast.body.ConstructorDeclaration;
import de.topobyte.japa.parser.ast.body.EmptyMemberDeclaration;
import de.topobyte.japa.parser.ast.body.EmptyTypeDeclaration;
import de.topobyte.japa.parser.ast.body.EnumConstantDeclaration;
import de.topobyte.japa.parser.ast.body.EnumDeclaration;
import de.topobyte.japa.parser.ast.body.FieldDeclaration;
import de.topobyte.japa.parser.ast.body.InitializerDeclaration;
import de.topobyte.japa.parser.ast.body.JavadocComment;
import de.topobyte.japa.parser.ast.body.MethodDeclaration;
import de.topobyte.japa.parser.ast.body.Parameter;
import de.topobyte.japa.parser.ast.body.VariableDeclarator;
import de.topobyte.japa.parser.ast.body.VariableDeclaratorId;
import de.topobyte.japa.parser.ast.expr.ArrayAccessExpr;
import de.topobyte.japa.parser.ast.expr.ArrayCreationExpr;
import de.topobyte.japa.parser.ast.expr.ArrayInitializerExpr;
import de.topobyte.japa.parser.ast.expr.AssignExpr;
import de.topobyte.japa.parser.ast.expr.BinaryExpr;
import de.topobyte.japa.parser.ast.expr.BooleanLiteralExpr;
import de.topobyte.japa.parser.ast.expr.CastExpr;
import de.topobyte.japa.parser.ast.expr.CharLiteralExpr;
import de.topobyte.japa.parser.ast.expr.ClassExpr;
import de.topobyte.japa.parser.ast.expr.ConditionalExpr;
import de.topobyte.japa.parser.ast.expr.DoubleLiteralExpr;
import de.topobyte.japa.parser.ast.expr.EnclosedExpr;
import de.topobyte.japa.parser.ast.expr.FieldAccessExpr;
import de.topobyte.japa.parser.ast.expr.InstanceOfExpr;
import de.topobyte.japa.parser.ast.expr.IntegerLiteralExpr;
import de.topobyte.japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import de.topobyte.japa.parser.ast.expr.LongLiteralExpr;
import de.topobyte.japa.parser.ast.expr.LongLiteralMinValueExpr;
import de.topobyte.japa.parser.ast.expr.MarkerAnnotationExpr;
import de.topobyte.japa.parser.ast.expr.MemberValuePair;
import de.topobyte.japa.parser.ast.expr.MethodCallExpr;
import de.topobyte.japa.parser.ast.expr.NameExpr;
import de.topobyte.japa.parser.ast.expr.NormalAnnotationExpr;
import de.topobyte.japa.parser.ast.expr.NullLiteralExpr;
import de.topobyte.japa.parser.ast.expr.ObjectCreationExpr;
import de.topobyte.japa.parser.ast.expr.QualifiedNameExpr;
import de.topobyte.japa.parser.ast.expr.SingleMemberAnnotationExpr;
import de.topobyte.japa.parser.ast.expr.StringLiteralExpr;
import de.topobyte.japa.parser.ast.expr.SuperExpr;
import de.topobyte.japa.parser.ast.expr.ThisExpr;
import de.topobyte.japa.parser.ast.expr.UnaryExpr;
import de.topobyte.japa.parser.ast.expr.VariableDeclarationExpr;
import de.topobyte.japa.parser.ast.stmt.AssertStmt;
import de.topobyte.japa.parser.ast.stmt.BlockStmt;
import de.topobyte.japa.parser.ast.stmt.BreakStmt;
import de.topobyte.japa.parser.ast.stmt.CatchClause;
import de.topobyte.japa.parser.ast.stmt.ContinueStmt;
import de.topobyte.japa.parser.ast.stmt.DoStmt;
import de.topobyte.japa.parser.ast.stmt.EmptyStmt;
import de.topobyte.japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import de.topobyte.japa.parser.ast.stmt.ExpressionStmt;
import de.topobyte.japa.parser.ast.stmt.ForStmt;
import de.topobyte.japa.parser.ast.stmt.ForeachStmt;
import de.topobyte.japa.parser.ast.stmt.IfStmt;
import de.topobyte.japa.parser.ast.stmt.LabeledStmt;
import de.topobyte.japa.parser.ast.stmt.ReturnStmt;
import de.topobyte.japa.parser.ast.stmt.SwitchEntryStmt;
import de.topobyte.japa.parser.ast.stmt.SwitchStmt;
import de.topobyte.japa.parser.ast.stmt.SynchronizedStmt;
import de.topobyte.japa.parser.ast.stmt.ThrowStmt;
import de.topobyte.japa.parser.ast.stmt.TryStmt;
import de.topobyte.japa.parser.ast.stmt.TypeDeclarationStmt;
import de.topobyte.japa.parser.ast.stmt.WhileStmt;
import de.topobyte.japa.parser.ast.test.classes.DumperTestClass;
import de.topobyte.japa.parser.ast.type.ClassOrInterfaceType;
import de.topobyte.japa.parser.ast.type.PrimitiveType;
import de.topobyte.japa.parser.ast.type.ReferenceType;
import de.topobyte.japa.parser.ast.type.VoidType;
import de.topobyte.japa.parser.ast.type.WildcardType;
import de.topobyte.japa.parser.ast.visitor.VoidVisitorAdapter;

import org.junit.Test;

/**
 * @author Julio Vilmar Gesser
 */
public class TestNodePositions {

    @Test
    public void testNodePositions() throws Exception {
        String source = Helper.readClass("./test", DumperTestClass.class);
        CompilationUnit cu = Helper.parserString(source);

        cu.accept(new TestVisitor(source), null);
    }

    void doTest(String source, Node node) {
        String parsed = node.toString();

        assertTrue(node.getClass().getName() + ": " + parsed, node.getBeginLine() >= 0);
        assertTrue(node.getClass().getName() + ": " + parsed, node.getBeginColumn() >= 0);
        assertTrue(node.getClass().getName() + ": " + parsed, node.getEndLine() >= 0);
        assertTrue(node.getClass().getName() + ": " + parsed, node.getEndColumn() >= 0);

        if (node.getBeginLine() == node.getEndLine()) {
            assertTrue(node.getClass().getName() + ": " + parsed, node.getBeginColumn() <= node.getEndColumn());
        } else {
            assertTrue(node.getClass().getName() + ": " + parsed, node.getBeginLine() <= node.getEndLine());
        }

        String substr = substring(source, node.getBeginLine(), node.getBeginColumn(), node.getEndLine(), node.getEndColumn());
        assertEquals(node.getClass().getName(), trimLines(parsed), trimLines(substr));
    }

    private String trimLines(String str) {
        String[] split = str.split("\n");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            ret.append(split[i].trim());
            if (i < split.length - 1) {
                ret.append("\n");
            }
        }

        return ret.toString();
    }

    private String substring(String source, int beginLine, int beginColumn, int endLine, int endColumn) {
        int pos = 0;
        while (beginLine > 1) {
            if (source.charAt(pos) == '\n') {
                beginLine--;
                endLine--;
            }
            pos++;
        }
        int start = pos + beginColumn - 1;

        while (endLine > 1) {
            if (source.charAt(pos) == '\n') {
                endLine--;
            }
            pos++;
        }
        int end = pos + endColumn;

        return source.substring(start, end);
    }

    class TestVisitor extends VoidVisitorAdapter<Object> {

        private final String source;

        public TestVisitor(String source) {
            this.source = source;
        }

        @Override
        public void visit(AnnotationDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(AnnotationMemberDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ArrayAccessExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ArrayCreationExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ArrayInitializerExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(AssertStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(AssignExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(BinaryExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(BlockComment n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(BlockStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(BooleanLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(BreakStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(CastExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(CatchClause n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(CharLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceType n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(CompilationUnit n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ConditionalExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ConstructorDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ContinueStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(DoStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(DoubleLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EmptyMemberDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EmptyStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EmptyTypeDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EnclosedExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EnumConstantDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EnumDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ExpressionStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(FieldAccessExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(FieldDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ForeachStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ForStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(IfStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ImportDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(InitializerDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(InstanceOfExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(IntegerLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(IntegerLiteralMinValueExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(JavadocComment n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(LabeledStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(LineComment n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(LongLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(LongLiteralMinValueExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(MarkerAnnotationExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(MemberValuePair n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodCallExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(NameExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(NormalAnnotationExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(NullLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ObjectCreationExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(PackageDeclaration n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(Parameter n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(PrimitiveType n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(QualifiedNameExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ReferenceType n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ReturnStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(SingleMemberAnnotationExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(StringLiteralExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(SuperExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(SwitchEntryStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(SwitchStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(SynchronizedStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ThisExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ThrowStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(TryStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(TypeDeclarationStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(TypeParameter n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(UnaryExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(VariableDeclarationExpr n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(VariableDeclarator n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(VariableDeclaratorId n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(VoidType n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(WhileStmt n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

        @Override
        public void visit(WildcardType n, Object arg) {
            doTest(source, n);
            super.visit(n, arg);
        }

    }

}
