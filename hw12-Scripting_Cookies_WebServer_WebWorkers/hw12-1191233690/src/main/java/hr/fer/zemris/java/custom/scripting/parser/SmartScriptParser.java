package hr.fer.zemris.java.custom.scripting.parser;


import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Parser for simple script language.
 * */
public class SmartScriptParser {

  /**
   * Root of parsing tree.
   * */
  private DocumentNode documentNode;
  /**
   * Lexer used for tokenization.
   * */
  private SmartScriptLexer lexer;
  /**
   * Stack used for parsing.
   * */
  private ObjectStack stack;
  
  /**
   * Constructor
   * */
  public SmartScriptParser(String text) {
    super();
    try {
      lexer = new SmartScriptLexer(text);
      if(text.length() >= 2 && (text.charAt(0)=='{' && text.charAt(1)=='$')) {
    	  lexer.setState(SmartLexerState.TAG);
      } else {
    	  lexer.setState(SmartLexerState.TEXT);
      }
      stack = new ObjectStack();
      documentNode = new DocumentNode();
      stack.push(documentNode);
      
      parsing();
       
    } catch (SmartScriptParserException e) {
      throw e;
    } catch (LexerException e) {
      throw new SmartScriptParserException("LexerException: " + e.getMessage());
    } catch (EmptyStackException e) {
      throw new SmartScriptParserException("EmptyStackException");
    } catch (NullPointerException e) {
      throw new SmartScriptParserException("NullPointerException");
    } catch (Exception e) {
      throw new SmartScriptParserException("Unexpoected exception occured.");
    }
    
    if(stack.peek().getClass() != DocumentNode.class) {
      throw new SmartScriptParserException("Unparsable. Expected END-tag.");
    }
  }
  
  
  /**
   * Getter of documentNode
   * @return documentNode
   * */
  public DocumentNode getDocumentNode() {
    return documentNode;
  }

  /**
   * Main parsing method.
   * @throws SmartScriptParserException
   * */
  private void parsing() {
    // first token
    lexer.nextToken();
    
    while(tokenType() != SmartTokenType.EOF) {
      
      
      
      if(tokenType() == SmartTokenType.OPEN_TAG) {
        
        lexer.nextToken();
        
        
        if(tokenType() == SmartTokenType.NAME) {
          
          
          if(tokenValue().toString().toLowerCase().equals("for")) {
           
            addsNodeAndPush(parseFor());
           
          } else if(tokenValue().toString().toLowerCase().equals("end")) {
        
            parseEnd();
                   
          }          
          
        
        } else if(tokenType() == SmartTokenType.SYMBOL)  {

          addsNode(parseOtherTag());
     
        } else {
          throw new SmartScriptParserException("Wrong token type.");
        }
        
        // MUST BE CLOSE_TAG
        if(tokenType() != SmartTokenType.CLOSE_TAG) {
          throw new SmartScriptParserException("Expected CLOSE_TAG token.");
        }
        lexer.setState(SmartLexerState.TEXT);
        lexer.nextToken();
      }
      
      
      if(tokenType() == SmartTokenType.TEXT) {

        addsNode(  new TextNode( tokenValue().toString() )  );
        lexer.setState(SmartLexerState.TAG);
        lexer.nextToken();
      }
      
    }
  }
  
  /**
   * Parses FOR-tag. If not possible throws exception.
   * @return ForLoopNode
   * @throws SmartScriptParserException
   * */
  private ForLoopNode parseFor() {
    
    lexer.nextToken();
    if(tokenType() == SmartTokenType.NAME) {
      
      ElementVariable variable = new ElementVariable(tokenValue().toString());
      
      lexer.nextToken();
      Element startExpression = elementNameOrStringOrIntegerOrDouble();
      
      lexer.nextToken();
      Element endExpression = elementNameOrStringOrIntegerOrDouble();
      
      lexer.nextToken();
      Element stepExpression = null;
      if(tokenType()  != SmartTokenType.CLOSE_TAG) {
          stepExpression = elementNameOrStringOrIntegerOrDouble();
          lexer.nextToken();
      }
      
      
      if(tokenType() != SmartTokenType.CLOSE_TAG) {
          throw new SmartScriptParserException("Expected CLOSE_TAG.");
      }
      
      return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
    }
    else {
      throw new SmartScriptParserException("");
    }
  }
  
  /**
   * Used for making ForLoopNode.
   * @return Element
   * @throws SmartScriptParserException
   * */
  private Element elementNameOrStringOrIntegerOrDouble() {
    
    if(tokenType()  == SmartTokenType.NAME) {
     
        return new ElementVariable(tokenValue().toString());
    
    } else if(tokenType()  == SmartTokenType.STRING) {
      
        return new ElementString(tokenValue().toString());
    
    } else if(tokenType()  == SmartTokenType.INTEGER) {
      
        return new ElementConstantInteger((int)tokenValue());
        
    } else if(tokenType()  == SmartTokenType.DOUBLE) {
        
        return new ElementConstantDouble((double) tokenValue());
    } else {
      throw new SmartScriptParserException();
    }
  }
  
  /**
   * Parse END, and if possible removes ForLoopNode
   * @throws SmartScriptParserException
   * */
  private void parseEnd() {
    lexer.nextToken();
    
    
    if(tokenType() != SmartTokenType.CLOSE_TAG) {
      throw new SmartScriptParserException("Wrong token, should be CLOSE_TAG.");
    }
    
    if(stack.isEmpty()) {
      throw new SmartScriptParserException("Stack is empty, END-tag can't close.");
    }
    
    if(stack.peek().getClass() != ForLoopNode.class) {
      throw new SmartScriptParserException("Class at top is not ForLoopNode, END-tag can't close.");
    }
    stack.pop();
  }
  
  /**
   * Returns parses tag others than FOR-tag. In this case only =-Tag.
   * @return EchoNode
   * @throws SmartScriptParserException
   * */
  private EchoNode parseOtherTag() {

    if(tokenType() != SmartTokenType.SYMBOL) {

      throw new SmartScriptParserException("Wrong tag name type.");
    
    } else if(! (tokenValue().toString()).equals((String)"=")) {
      throw new SmartScriptParserException("Wrong tag name value.");
    
    }
    
    ArrayIndexedCollection elements = new ArrayIndexedCollection();
    // makes Element[] 
    while(true) {
      
      lexer.nextToken();

      if(tokenType() == SmartTokenType.CLOSE_TAG) {
        
        int size = elements.size();
        Element[] elements2 = new Element[size];

        for(int i = 0; i < size; ++i) {
          elements2[i] = (Element) elements.get(i);
        }
        
        return new EchoNode(elements2);
      }
      
      if(tokenType() == SmartTokenType.EOF) {
        throw new SmartScriptParserException("Too early EOF.");
      }
      
      elements.add(echoElement());
      
    }
  }
  
  /**
   * Checks and makes Element for EchoNode.
   * @return Element
   * @throws SmartScriptParserException
   * */
  private Element echoElement() {
    if(tokenType()  == SmartTokenType.NAME) {
        
        return new ElementVariable(tokenValue().toString());
    
    } else if(tokenType()  ==  SmartTokenType.STRING) {
      
        return new ElementString(tokenValue().toString());
    
    } else if(tokenType()  ==  SmartTokenType.INTEGER) {
      
        return new ElementConstantInteger((int)tokenValue());
        
    } else if(tokenType()  ==  SmartTokenType.DOUBLE) {
        
        return new ElementConstantDouble((double) tokenValue());
    
    } else if(tokenType()  ==  SmartTokenType.FUNCTION_NAME) {
      
      return new ElementFunction(tokenValue().toString());
      
    } else if(tokenType()  ==  SmartTokenType.SYMBOL) {
      if(!tokenValue().equals("=")) {
        return new ElementOperator( tokenValue().toString() );
      }
      throw new SmartScriptParserException("Wrong token symbol.");
    } 
    else {
      throw new SmartScriptParserException("Unexpected token.");
    }
  }
  
  /**
   * Returns type of current token .
   * @return SmartTokenType
   * */
  private SmartTokenType tokenType() {
    return lexer.getToken().getType();
  }
  
  /**
   * Returns value of current token.
   * @return Object
   * */
  private Object tokenValue() {
    return lexer.getToken().getValue();
  }
  
  /**
   * Adds node to node at top of stack.
   * @param node
   * @throws SmartScriptParserException if stack is empty
   * */
  private void addsNode(Node node) {
    if(stack.isEmpty()) {
      throw new SmartScriptParserException("Stack is empty. Can't add node.");
    }
    Node handy = (Node)stack.peek();
    handy.addChildNode(node);
  }
  
  /**
   * Adds node to node at top of stack. Adds node to stack.
   * @param node
   * @throws SmartScriptParserException if stack is empty
   * */
  private void addsNodeAndPush(Node node) {
    if(stack.isEmpty()) {
      throw new SmartScriptParserException("Stack is empty. Can't add node.");
    }
    
    Node handy = (Node)stack.peek();
    handy.addChildNode(node);
    stack.push(node);
  }
}
