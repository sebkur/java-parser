/*
 * Copyright (C) 2007 Júlio Vilmar Gesser.
 * 
 * This file is part of Java 1.5 parser and Abstract Syntax Tree.
 *
 * Java 1.5 parser and Abstract Syntax Tree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java 1.5 parser and Abstract Syntax Tree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java 1.5 parser and Abstract Syntax Tree.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 05/10/2006
 */
package de.topobyte.japa.parser.ast.visitor;

import de.topobyte.japa.parser.ast.BlockComment;
import de.topobyte.japa.parser.ast.CompilationUnit;
import de.topobyte.japa.parser.ast.ImportDeclaration;
import de.topobyte.japa.parser.ast.LineComment;
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
import de.topobyte.japa.parser.ast.type.ClassOrInterfaceType;
import de.topobyte.japa.parser.ast.type.PrimitiveType;
import de.topobyte.japa.parser.ast.type.ReferenceType;
import de.topobyte.japa.parser.ast.type.VoidType;
import de.topobyte.japa.parser.ast.type.WildcardType;

/**
 * @author Julio Vilmar Gesser
 */
public interface VoidVisitor<A> {

    //- Compilation Unit ----------------------------------

    public void visit(CompilationUnit n, A arg);

    public void visit(PackageDeclaration n, A arg);

    public void visit(ImportDeclaration n, A arg);

    public void visit(TypeParameter n, A arg);

    public void visit(LineComment n, A arg);

    public void visit(BlockComment n, A arg);

    //- Body ----------------------------------------------

    public void visit(ClassOrInterfaceDeclaration n, A arg);

    public void visit(EnumDeclaration n, A arg);

    public void visit(EmptyTypeDeclaration n, A arg);

    public void visit(EnumConstantDeclaration n, A arg);

    public void visit(AnnotationDeclaration n, A arg);

    public void visit(AnnotationMemberDeclaration n, A arg);

    public void visit(FieldDeclaration n, A arg);

    public void visit(VariableDeclarator n, A arg);

    public void visit(VariableDeclaratorId n, A arg);

    public void visit(ConstructorDeclaration n, A arg);

    public void visit(MethodDeclaration n, A arg);

    public void visit(Parameter n, A arg);

    public void visit(EmptyMemberDeclaration n, A arg);

    public void visit(InitializerDeclaration n, A arg);

    public void visit(JavadocComment n, A arg);

    //- Type ----------------------------------------------

    public void visit(ClassOrInterfaceType n, A arg);

    public void visit(PrimitiveType n, A arg);

    public void visit(ReferenceType n, A arg);

    public void visit(VoidType n, A arg);

    public void visit(WildcardType n, A arg);

    //- Expression ----------------------------------------

    public void visit(ArrayAccessExpr n, A arg);

    public void visit(ArrayCreationExpr n, A arg);

    public void visit(ArrayInitializerExpr n, A arg);

    public void visit(AssignExpr n, A arg);

    public void visit(BinaryExpr n, A arg);

    public void visit(CastExpr n, A arg);

    public void visit(ClassExpr n, A arg);

    public void visit(ConditionalExpr n, A arg);

    public void visit(EnclosedExpr n, A arg);

    public void visit(FieldAccessExpr n, A arg);

    public void visit(InstanceOfExpr n, A arg);

    public void visit(StringLiteralExpr n, A arg);

    public void visit(IntegerLiteralExpr n, A arg);

    public void visit(LongLiteralExpr n, A arg);

    public void visit(IntegerLiteralMinValueExpr n, A arg);

    public void visit(LongLiteralMinValueExpr n, A arg);

    public void visit(CharLiteralExpr n, A arg);

    public void visit(DoubleLiteralExpr n, A arg);

    public void visit(BooleanLiteralExpr n, A arg);

    public void visit(NullLiteralExpr n, A arg);

    public void visit(MethodCallExpr n, A arg);

    public void visit(NameExpr n, A arg);

    public void visit(ObjectCreationExpr n, A arg);

    public void visit(QualifiedNameExpr n, A arg);

    public void visit(ThisExpr n, A arg);

    public void visit(SuperExpr n, A arg);

    public void visit(UnaryExpr n, A arg);

    public void visit(VariableDeclarationExpr n, A arg);

    public void visit(MarkerAnnotationExpr n, A arg);

    public void visit(SingleMemberAnnotationExpr n, A arg);

    public void visit(NormalAnnotationExpr n, A arg);

    public void visit(MemberValuePair n, A arg);

    //- Statements ----------------------------------------

    public void visit(ExplicitConstructorInvocationStmt n, A arg);

    public void visit(TypeDeclarationStmt n, A arg);

    public void visit(AssertStmt n, A arg);

    public void visit(BlockStmt n, A arg);

    public void visit(LabeledStmt n, A arg);

    public void visit(EmptyStmt n, A arg);

    public void visit(ExpressionStmt n, A arg);

    public void visit(SwitchStmt n, A arg);

    public void visit(SwitchEntryStmt n, A arg);

    public void visit(BreakStmt n, A arg);

    public void visit(ReturnStmt n, A arg);

    public void visit(IfStmt n, A arg);

    public void visit(WhileStmt n, A arg);

    public void visit(ContinueStmt n, A arg);

    public void visit(DoStmt n, A arg);

    public void visit(ForeachStmt n, A arg);

    public void visit(ForStmt n, A arg);

    public void visit(ThrowStmt n, A arg);

    public void visit(SynchronizedStmt n, A arg);

    public void visit(TryStmt n, A arg);

    public void visit(CatchClause n, A arg);

}
