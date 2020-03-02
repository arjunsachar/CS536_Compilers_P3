///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  P3.java
// File:             ast.java
// Semester:         Spring 2020
//
// Author:           Vineeth Suresh --> vsuresh5@wisc.edu
// CS Login:         vineeth
// Lecturer's Name:  Loris D'Antoni
// Lab Section:      N/A
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Arjun Sachar
// Email:            sachar2@wisc.edu
// CS Login:         Sachar
// Lecturer's Name:  Loris D'Antoni
// Lab Section:      N/A
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          None
//                   
//
// Online sources:   None
//                   
//                   
//////////////////////////// 80 columns wide //////////////////////////////////


import java.io.*;
import java.util.*;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a Wumbo program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of
// children) or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Children
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        linked list of DeclNode
//     DeclNode:
//     VarDeclNode       TypeNode, IdNode, int
//       
//      DeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//       StructDeclNode    IdNode, DeclListNode
//
//     FormalsListNode     linked list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        linked list of StmtNode
//     ExpListNode         linked list of ExpNode
//
//     TypeNode:
//       IntNode           --- none ---
//       BoolNode          --- none ---
//       VoidNode          --- none ---
//       StructNode        IdNode
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PostIncStmtNode     ExpNode
//       PostDecStmtNode     ExpNode
//       ReadStmtNode        ExpNode
//       WriteStmtNode       ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       RepeatStmtNode      ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          --- none ---
//       StrLitNode          --- none ---
//       TrueNode            --- none ---
//       FalseNode           --- none ---
//       IdNode              --- none ---
//       DotAccessNode       ExpNode, IdNode
//       AssignNode          ExpNode, ExpNode
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        ExpNode
//         UnaryMinusNode
//         NotNode
//       BinaryExpNode       ExpNode ExpNode
//         PlusNode
//         MinusNode
//         TimesNode
//         DivideNode
//         AndNode
//         OrNode
//         EqualsNode
//         NotEqualsNode
//         LessNode
//         GreaterNode
//         LessEqNode
//         GreaterEqNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with linked lists of children, or
// internal nodes with a fixed number of children:
//
// (1) Leaf nodes:
//        IntNode,   BoolNode,  VoidNode,  IntLitNode,  StrLitNode,
//        TrueNode,  FalseNode, IdNode
//
// (2) Internal nodes with (possibly empty) linked lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of children:
//        ProgramNode,     VarDeclNode,     FnDeclNode,     FormalDeclNode,
//        StructDeclNode,  FnBodyNode,      StructNode,     AssignStmtNode,
//        PostIncStmtNode, PostDecStmtNode, ReadStmtNode,   WriteStmtNode
//        IfStmtNode,      IfElseStmtNode,  WhileStmtNode,  RepeatStmtNode,
//        CallStmtNode,    ReturnStmtNode,  DotAccessNode,  CallExpNode,
//        UnaryExpNode,    BinaryExpNode,   UnaryMinusNode, NotNode,
//        PlusNode,        MinusNode,       TimesNode,      DivideNode,
//        AndNode,         OrNode,          EqualsNode,     NotEqualsNode,
//        LessNode,        GreaterNode,     LessEqNode,     GreaterEqNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************

abstract class ASTnode {
    // every subclass must provide an unparse operation
    abstract public void unparse(PrintWriter p, int indent);

    // this method can be used by the unparse methods to add indents
    protected void addIndentation(PrintWriter p, int indent) {
        for (int k=0; k<indent; k++) p.print(" ");
    }
}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************

class ProgramNode extends ASTnode 
{
    public ProgramNode(DeclListNode L) 
    {
        myDeclList = L;
    }

    public void unparse(PrintWriter p, int indent) 
    {
        myDeclList.unparse(p, indent);
    }

    // one child
    private DeclListNode myDeclList;
}

class DeclListNode extends ASTnode 
{
    public DeclListNode(List<DeclNode> S)
    {
        myDecls = S;
    }

    public void unparse(PrintWriter p, int indent) 
    {
        Iterator it = myDecls.iterator();
        try 
        {
            while (it.hasNext()) 
            {
                ((DeclNode)it.next()).unparse(p, indent);
            }
        } catch (NoSuchElementException ex) 
        {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    // list of children (DeclNodes)
    private List<DeclNode> myDecls;
}

class FormalsListNode extends ASTnode 
{
    public FormalsListNode(List<FormalDeclNode> S) 
    {
        myFormals = S;
    }


    //IMPLEMENT #1
    public void unparse(PrintWriter p, int indent) 
    {

        //Creates an iterationr to go through the Formals List
    	Iterator it = myFormals.iterator();
  
        //Try catch to make sure that there is elements in list
    	try 
    	{	
            //While loop to go through iterator
    		while(it.hasNext())	 
    		{
    			//Casting the iterator to type FormalDeclNode and unparsing
    			((FormalDeclNode)it.next()).unparse(p, indent);
				//If there is another item in the list
				if(it.hasNext())
				{
                    //Prints a comma inbetween elements
					p.print(" , ");
				}
		    }
            //Catch that triggers when there are no elements in the Formals List
    	} catch (NoSuchElementException ex) 

            {
                //Prints out the error message and exits
                System.err.println("unexpected NoSuchElementException in FormalsListNode.print");
                System.exit(-1);
            }	
    	}
    
    // list of children (FormalDeclNodes)
    private List<FormalDeclNode> myFormals;
}

class FnBodyNode extends ASTnode {
    public FnBodyNode(DeclListNode declList, StmtListNode stmtList) 
    {
        myDeclList = declList;
        myStmtList = stmtList;
    }


    //IMPLEMENT #2
    public void unparse(PrintWriter p, int indent) 
    {
    	
    	//Prints the left bracket
    	p.println("{");
    	
    
        //Unparses the declaration list with an indent + 4
    	myDeclList.unparse(p, indent + 4);
    	
        //Unparses the statement list with an indent + 4
    	myStmtList.unparse(p, indent + 4);
    
    	
    	//Prints the right bracket
    	p.print("}");
    }

    // two children
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class StmtListNode extends ASTnode
{
    public StmtListNode(List<StmtNode> S) 
    {
        myStmts = S;
    }

    //IMPLEMENT #3
    public void unparse(PrintWriter p, int indent) {
    	
        //Creates iterator on statements list
    	Iterator it = myStmts.iterator();

    	try 
    	{
    		while(it.hasNext())	 
    		{
			addIndentation(p, indent);
    			((StmtNode)it.next()).unparse(p, indent);
		}
    		} catch (NoSuchElementException ex) 
    		{
                System.err.println("unexpected NoSuchElementException in StmtListNode.print");
                System.exit(-1);
            }
    	}
    
    // list of children (StmtNodes)
    private List<StmtNode> myStmts;
}

class ExpListNode extends ASTnode 
{
    public ExpListNode(List<ExpNode> S) 
    {
        myExps = S;
    }

    //IMPLEMENT #4
    public void unparse(PrintWriter p, int indent) 
    {
    	Iterator it = myExps.iterator();
        
    	 //Try catch to make sure that there is elements in list
    	try 
    	{	
            //While loop to go through iterator
    		while(it.hasNext())	 
    		{
    			//Casting the iterator to type FormalDeclNode and unparsing
    			((ExpNode)it.next()).unparse(p, indent);

				//If there is another item, print the comma
				if(it.hasNext())
				{
					p.print(" , ");
				}
		    }
          //Catch if no element is found
        } catch (NoSuchElementException ex) 
            {
                System.err.println("unexpected NoSuchElementException in ExpListNode.print");
                System.exit(-1);
            }
    	}
    	
    

    // list of children (ExpNodes)
    private List<ExpNode> myExps;
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************

abstract class DeclNode extends ASTnode 
{   
}

class VarDeclNode extends DeclNode 
{
    public VarDeclNode(TypeNode type, IdNode id, int size) 
    {
        myType = type;
        myId = id;
        mySize = size;
    }

    public void unparse(PrintWriter p, int indent) 
    {
        addIndentation(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.println(";");
    }

    // three children
    private TypeNode myType;
    private IdNode myId;
    private int mySize;  // use value NOT_STRUCT if this is not a struct type

    public static int NOT_STRUCT = -1;
}

class FnDeclNode extends DeclNode 
{
    public FnDeclNode(TypeNode type,
                      IdNode id,
                      FormalsListNode formalList,
                      FnBodyNode body) 
    {
        myType = type;
        myId = id;
        myFormalsList = formalList;
        myBody = body;
    }


    //IMPLEMENT #5
    public void unparse(PrintWriter p, int indent) 
    {
        //Unparses the type
        myType.unparse(p,indent);
        //Adds a space
        p.print(" ");

        //Unparses the type
        myId.unparse(p, indent);
        //Prints a left paren
        p.print(" (");

        //Unparses the formals 
        myFormalsList.unparse(p, indent);
        //Prints the right paren
        p.print(") ");

        //Unparses the body
        myBody.unparse(p,indent);

        //Prints a newline
        p.println();
    }


    // four children
    private TypeNode myType;
    private IdNode myId;
    private FormalsListNode myFormalsList;
    private FnBodyNode myBody;
}


class FormalDeclNode extends DeclNode 
{
    public FormalDeclNode(TypeNode type, IdNode id) 
    {
        myType = type;
        myId = id;
    }

      //IMPLEMENT #6
    public void unparse(PrintWriter p, int indent) 
    {
    	
        //Unparses the type
    	myType.unparse(p, indent);

        //Prints a space
    	p.print(" " );

        //Unparses the ID
    	myId.unparse(p, indent);
    }

    // two children
    private TypeNode myType;
    private IdNode myId;
}

class StructDeclNode extends DeclNode 
{
    public StructDeclNode(IdNode id, DeclListNode declList) 
    {
        myId = id;
		myDeclList = declList;
    }

    //IMPLEMENT #7
    public void unparse(PrintWriter p, int indent) 
    {

        //Uses addIndentation method to add indent
    	 addIndentation(p, indent);

         //Prints out word struct
         p.print("struct ");

         //Unparses the ID 
         myId.unparse(p, indent);
         //Prints the left bracket
         p.println(" {");

        //Unparses the declaration list
         myDeclList.unparse(p, indent+4);

         //Uses addIndentation method to add indent
         addIndentation(p, indent);

         //Prints the left bracket
         p.println("};");
    }

    // two children
    private IdNode myId;
	  private DeclListNode myDeclList;
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************

abstract class TypeNode extends ASTnode 
{
}

class IntNode extends TypeNode 
{
    public IntNode() 
    {
    }

    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out word int
        p.print("int");
    }
}

class BoolNode extends TypeNode 
{
    public BoolNode() 
    {
    }

    //IMPLEMENT #8
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out word bool
    	p.print("bool");
    }
}

class VoidNode extends TypeNode 
{
    public VoidNode() 
    {
    }

    //IMPLEMENT #9
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out word void
    	p.print("void");
    }
}

class StructNode extends TypeNode 
{
    public StructNode(IdNode id) 
    {
		myId = id;
    }

    //IMPLEMENT #10
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out word struct
    	p.print("struct");

        //Prints out space
    	p.print(" ");

        //Unparses the id
    	myId.unparse(p, indent);
    }

    // one child
    private IdNode myId;
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode 
{
}

class AssignStmtNode extends StmtNode 
{
    public AssignStmtNode(AssignNode assign)
    {
        myAssign = assign;
    }

    //IMPLEMENT #11
    public void unparse(PrintWriter p, int indent) 
    {
        //Unparses the assignment
    	myAssign.unparse(p, indent);

        //Prints out semicolon
    	p.println(";");
    }

    // one child
    private AssignNode myAssign;
}

class PostIncStmtNode extends StmtNode 
{
    public PostIncStmtNode(ExpNode exp) 
    {
        myExp = exp;
    }

    //IMPLEMENT #12
    public void unparse(PrintWriter p, int indent) 
    {
    	//Unparses the expression
    	myExp.unparse(p, indent);

        //Prints out corresponding increment symbol
    	p.println("++;");
    }

    // one child
    private ExpNode myExp;
}

class PostDecStmtNode extends StmtNode 
{
    public PostDecStmtNode(ExpNode exp) 
    {
        myExp = exp;
    }

    //IMPLEMENT #13
    public void unparse(PrintWriter p, int indent) 
    {
    	//Unpareses the expression
    	myExp.unparse(p, indent);

          //Prints out corresponding decrement symbol
    	p.println("--;");
    }

    // one child
    private ExpNode myExp;
}

class ReadStmtNode extends StmtNode 
{
    public ReadStmtNode(ExpNode e) 
    {
        myExp = e;
    }

    //IMPLEMENT #14
    public void unparse(PrintWriter p, int indent) 
    {
    	//Prints out reading statement symbol
        p.print("cin >> ");

        //Unparses the expression
        myExp.unparse(p, indent);

        //Prints a semicolon
        p.println(";");
    }

    // one child (actually can only be an IdNode or an ArrayExpNode)
    private ExpNode myExp;
}

class WriteStmtNode extends StmtNode 
{
    public WriteStmtNode(ExpNode exp) 
    {
        myExp = exp;
    }

    //IMPLEMENT #15
    public void unparse(PrintWriter p, int indent) 
    {
    	//Print out the writing statement symbol
        p.print("cout << ");

        //Unparses the expression
        myExp.unparse(p, indent);

        //Prints a semicolon
        p.println(";");
    }

    // one child
    private ExpNode myExp;
}

class IfStmtNode extends StmtNode 
{
    public IfStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) 
    {
        myDeclList = dlist;
        myExp = exp;
        myStmtList = slist;
    }

    //IMPLEMENT #16
    public void unparse(PrintWriter p, int indent) 
    {

        //Prints out if w/ left paren
        p.print("if (");
        //Unparses the expression
        myExp.unparse(p, indent);
        //Prints the right paren and left bracket
        p.println(") {");

        //Unparses the declaration list
        myDeclList.unparse(p, indent + 4);

        //Unparses the statement list
        myStmtList.unparse(p, indent + 4);

        //Adds an indentation
        addIndentation(p, indent);

        //Prints out right bracket
        p.println("}");
    }

    // three children
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class IfElseStmtNode extends StmtNode 
{
    public IfElseStmtNode(ExpNode exp, DeclListNode dlist1,
                          StmtListNode slist1, DeclListNode dlist2,
                          StmtListNode slist2) 
    {
        myExp = exp;
        myThenDeclList = dlist1;
        myThenStmtList = slist1;
        myElseDeclList = dlist2;
        myElseStmtList = slist2;
    }

    //IMPLEMENT #17
    public void unparse(PrintWriter p, int indent) 
    {
    	
        //Prints out if w/ left paren
        p.print("if (");

        //Unparses the expression
        myExp.unparse(p, indent);

        //Prints the right paren and left bracket
        p.println(") {"); 

        //Unparses the declaration list and adds an indent w/4
        myThenDeclList.unparse(p, indent + 4);

         //Unparses the statement list and adds an indent w/4
        myThenStmtList.unparse(p, indent + 4);

        //Adds an indentation
        addIndentation(p, indent);

        //Prints the left bracket
        p.println("}");
        //Adds an indentation
        addIndentation(p, indent);

        //prints out else with left bracket
        p.println("else {");

        //Unparses the declaration in else statement w/indent + 4
        myElseDeclList.unparse(p, indent + 4);

        //Unparses the statement in else statement w/indent + 4
        myElseStmtList.unparse(p, indent + 4);

        //Adds an indentation 
        addIndentation(p, indent);	

        //Adds a left bracket
        p.println("}");
    	
    }

    // five children
    private ExpNode myExp;
    private DeclListNode myThenDeclList;
    private StmtListNode myThenStmtList;
    private StmtListNode myElseStmtList;
    private DeclListNode myElseDeclList;
}

class WhileStmtNode extends StmtNode 
{
    public WhileStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) 
    {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }

    //IMPLEMENT #18
    public void unparse(PrintWriter p, int indent) 
    {
    
        //Prints out while with left paren
        p.print("while (");

        //Unparses the expression in the if
        myExp.unparse(p, indent);

        //Prints right paren w/left bracket 
        p.println(") {"); 

        //Unparses the declaration list
        myDeclList.unparse(p, indent + 4);

        //Unparses the statement list
        myStmtList.unparse(p, indent + 4);

        //Adds an indentation 
        addIndentation(p, indent);

        //Prints out right brack
        p.println("}");
        
    }

    // three children
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class RepeatStmtNode extends StmtNode 
{
    public RepeatStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) 
    {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }

    //IMPLEMENT #19
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out repeat w/left paren
    	 p.print("repeat (");

         //Unparses the expression
         myExp.unparse(p, 0);

         //Prints right paren w/left bracket 
         p.println(") {"); 

         //Unparses the declaration list w/an indent + 4
         myDeclList.unparse(p, indent + 4);

         //Unparses the statement list w/an indent + 4
         myStmtList.unparse(p, indent + 4);

         //Adds an indentation
         addIndentation(p, indent);

         //Prints a right brack
         p.println("}");
    	
    }

    // three children
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class CallStmtNode extends StmtNode 
{
    public CallStmtNode(CallExpNode call) 
    {
        myCall = call;
    }

    //IMPLEMENT #20
    public void unparse(PrintWriter p, int indent) 
    {
    	//Unparses the call 
    	myCall.unparse(p, indent);

        //Prints a semicolon
    	p.println(";");
    }

    // one child
    private CallExpNode myCall;
}

class ReturnStmtNode extends StmtNode 
{
    public ReturnStmtNode(ExpNode exp) 
    {
        myExp = exp;
    }

    //IMPLEMENT #21
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out return
    	p.print("return");

        //If the expression is not null, unparses the expression
    	if(myExp != null)
    	{
    		p.print(" ");
    		myExp.unparse(p, 0);
    	}
        //Prints the semicolon
    	p.println(";");
    }

    // one child
    private ExpNode myExp; // possibly null
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode 
{
}

class IntLitNode extends ExpNode {
    public IntLitNode(int lineNum, int charNum, int intVal) 
    {
        myLineNum = lineNum;
        myCharNum = charNum;
        myIntVal = intVal;
    }

    //IMPLEMENT #22
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints the integer literal 
    	p.print(myIntVal);
    }

    // three children
    private int myLineNum;
    private int myCharNum;
    private int myIntVal;
}

class StringLitNode extends ExpNode 
{
    public StringLitNode(int lineNum, int charNum, String strVal) 
    {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

     //IMPLEMENT #23
    public void unparse(PrintWriter p, int indent) {

        //Prints the string value 
    	p.print(myStrVal);
    }

    // three children
    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}


class TrueNode extends ExpNode 
{
    public TrueNode(int lineNum, int charNum) 
    {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    //IMPLEMENT #24
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out true
    	p.print("true");
    }

    // two children
    private int myLineNum;
    private int myCharNum;
}

class FalseNode extends ExpNode 
{
    public FalseNode(int lineNum, int charNum) 
    {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

     //IMPLEMENT #25
    public void unparse(PrintWriter p, int indent) 
    {
        //prints out false
    	p.print("false");
    }

    // two children
    private int myLineNum;
    private int myCharNum;
}

class IdNode extends ExpNode 
{
    public IdNode(int lineNum, int charNum, String strVal) 
    {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    //IMPLEMENT #26
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out the string literal
        p.print(myStrVal);
    }

    // three children
    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}

class DotAccessExpNode extends ExpNode 
{
    public DotAccessExpNode(ExpNode loc, IdNode id) 
    {
        myLoc = loc;
        myId = id;
    }

    //IMPLEMENT #27
    public void unparse(PrintWriter p, int indent) 
    {
        //Unparses the loc
    	myLoc.unparse(p, indent);

        //Prints the period
    	p.print(".");

        //Prints the ID
    	myId.unparse(p, indent);
    }

    // two children
    private ExpNode myLoc;
    private IdNode myId;
}

class AssignNode extends ExpNode 
{
    public AssignNode(ExpNode lhs, ExpNode exp) 
    {
        myLhs = lhs;
        myExp = exp;
    }

    //IMPLEMENT #28
    public void unparse(PrintWriter p, int indent) 
    {
        //Unparses the left hand side
    	myLhs.unparse(p, indent );

        //Prints the equals sign
    	p.print(" = ");

        //Unparses the expression 
    	myExp.unparse(p, indent);
    }

    // two children
    private ExpNode myLhs;
    private ExpNode myExp;
}

class CallExpNode extends ExpNode 
{
    public CallExpNode(IdNode name, ExpListNode elist) 
    {
        myId = name;
        myExpList = elist;
    }

     public CallExpNode(IdNode name) 
     {
        myId = name;
        myExpList = new ExpListNode(new LinkedList<ExpNode>());
     }

     //IMPLEMENT #29
    public void unparse(PrintWriter p, int indent) 
    {
        //Unparses the ID
        myId.unparse(p, indent);

        //Prints the left paren
        p.print("(");

        //If the expression list is not empty, it unparses the expression list
        if(myExpList != null) {
            myExpList.unparse(p, indent);
        }

        //Prints the right paren
        p.print(")");
    }

    // two children
    private IdNode myId;
    private ExpListNode myExpList;  // possibly null
}

abstract class UnaryExpNode extends ExpNode 
{
    public UnaryExpNode(ExpNode exp) 
    {
        myExp = exp;
    }

    // one child
    protected ExpNode myExp;
}

abstract class BinaryExpNode extends ExpNode 
{
    public BinaryExpNode(ExpNode exp1, ExpNode exp2) 
    {
        myExp1 = exp1;
        myExp2 = exp2;
    }

    // two children
    protected ExpNode myExp1;
    protected ExpNode myExp2;
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class UnaryMinusNode extends UnaryExpNode 
{
    public UnaryMinusNode(ExpNode exp) 
    {
        super(exp);
    }

     //IMPLEMENT #30
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints the left paren
        p.print("(");

        //Prints the minus sign
		p.print("-");

        //Unparses the expression
		myExp.unparse(p, indent);

        //Prints the right paren
		p.print(")");
    }
}

class NotNode extends UnaryExpNode 
{
    public NotNode(ExpNode exp) 
    {
        super(exp);
    }

	// IMPLEMENT #31
	public void unparse(PrintWriter p, int indent) 
    {
        //Prints the left paren
		p.print("(");

        //Prints the not symbol
		p.print("!");

        //Unparses the expression
		myExp.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class PlusNode extends BinaryExpNode 
{
    public PlusNode(ExpNode exp1, ExpNode exp2) 
    {
        super(exp1, exp2);
    }

     //IMPLEMENT #32
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
        p.print("(");

        //Unparses the first expression 
        myExp1.unparse(p, indent);

        //Adds the plus sign
        p.print(" + ");

        //Unparses the second expression
        myExp2.unparse(p, indent);

        //Prints the right paren
        p.print(")");
    }
}

class MinusNode extends BinaryExpNode 
{
    public MinusNode(ExpNode exp1, ExpNode exp2) 
    {
        super(exp1, exp2);
    }

    //IMPLEMENT #33
    public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
        p.print("(");

        //Unparses the first expression 
        myExp1.unparse(p, indent);

        //Adds the minus sign
        p.print(" - ");

        //Unparses the second expression
        myExp2.unparse(p, indent);

        //Prints the right paren
        p.print(")");
    }
}

class TimesNode extends BinaryExpNode 
{
	public TimesNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #34
	public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

         //Adds the multiplication sign
		p.print(" * ");

        //Unparses the first expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class DivideNode extends BinaryExpNode 
{
	public DivideNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #35
	public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Adds the divide symbol 
		p.print(" / ");

         //Unparses the second expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class AndNode extends BinaryExpNode 
{
	public AndNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #36
	public void unparse(PrintWriter p, int indent) 
    {
         //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints the and symbol
		p.print(" && ");

        //Unparses the second expression
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class OrNode extends BinaryExpNode 
{
	public OrNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #37
	public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints out OR symbol
		p.print(" || ");

        //Unparses the second expression
		myExp2.unparse(p, indent);

         //Prints the right paren
		p.print(")");
	}
}

class EqualsNode extends BinaryExpNode 
{
	public EqualsNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #38
	public void unparse(PrintWriter p, int indent) 
    {
         //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints out equal symbol
		p.print(" == ");

        //Unparses the second expression
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class NotEqualsNode extends BinaryExpNode 
{
	public NotEqualsNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #39
	public void unparse(PrintWriter p, int indent) 
    {

         //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints out not equal symbol
		p.print(" != ");

         //Unparses the second expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class LessNode extends BinaryExpNode 
{
	public LessNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #40
	public void unparse(PrintWriter p, int indent) 
    {

        //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints out less than symbol
		p.print(" < ");

        //Unparses the second expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class GreaterNode extends BinaryExpNode 
{
	public GreaterNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #41
	public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints out greater than symbol
		p.print(" > ");

        //Unparses the second expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class LessEqNode extends BinaryExpNode 
{
	public LessEqNode(ExpNode exp1, ExpNode exp2) 
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #42
	public void unparse(PrintWriter p, int indent) 
    {
         //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

         //Prints out less than or equal to symbol
		p.print(" <= ");

         //Unparses the second expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}

class GreaterEqNode extends BinaryExpNode 
{
	public GreaterEqNode(ExpNode exp1, ExpNode exp2)
    {
		super(exp1, exp2);
	}

	// IMPLEMENT #43
	public void unparse(PrintWriter p, int indent) 
    {
        //Prints out left paren
		p.print("(");

        //Unparses the first expression 
		myExp1.unparse(p, indent);

        //Prints out greater than or equal to symbol
		p.print(" >= ");

        //Unparses the second expression 
		myExp2.unparse(p, indent);

        //Prints the right paren
		p.print(")");
	}
}
