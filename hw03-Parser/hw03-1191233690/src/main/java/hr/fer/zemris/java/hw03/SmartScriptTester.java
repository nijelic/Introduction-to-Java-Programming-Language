package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.parser.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.elems.*;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * Class that tests SmartScriptParser.
 * */
public class SmartScriptTester {

  /**
   * Main method that tests SmartScriptParser.
   * @param args[0] filepath of the data
   * */
  public static void main(String[] args) {
    
    String filepath = args[0];
    String docBody;
    try {
        docBody = new String(
        Files.readAllBytes(Paths.get(filepath)),
        StandardCharsets.UTF_8
       );
    } catch(Exception e) {
      docBody = new String("Error");
    }
    
    SmartScriptParser parser = null;
    try {
     parser = new SmartScriptParser(docBody);
    } catch(SmartScriptParserException e) {
     System.out.println("Unable to parse document!");
     System.exit(-1);
    } catch(Exception e) {
     System.out.println("If this line ever executes, you have failed this class!");
     System.exit(-1);
    }
    DocumentNode document = parser.getDocumentNode();
    String originalDocumentBody = createOriginalDocumentBody(document);
    System.out.println(originalDocumentBody); // should write something like original
     // content of docBody

  }
  
  /**
   * Creates semantically original document.
   * @param document
   * @return String 
   * */
  private static String createOriginalDocumentBody(DocumentNode document) {
    StringBuilder text = new StringBuilder();

    for(int i = 0, childrenNumber = document.numberOfChildren(); i < childrenNumber; ++i) {
      create(text, document.getChild(i));
    }
    
    return text.toString();
  }
  
  /**
   * Builds document on the Node layer. Connects Node layer and Element layer.
   * @param node
   * @param text reference of StringBuilder. There will be text document
   * */
  private static void create(StringBuilder text, Node node) {
    
    if(node.getClass().equals(TextNode.class)) {
      
      String word = ((TextNode)node).getText();
      
      for(int i = 0, length = word.length(); i < length; ++i) {
      
        if(word.charAt(i) == '\\') {
          text.append('\\');
          text.append('\\');
        
        } else if(word.charAt(i) == '{' && i+1 < length && word.charAt(i+1) == '$') {
          text.append('\\');
          text.append('{');
        
        } else {
          text.append(word.charAt(i));
        }
      }
      
    } else if(node.getClass().equals(ForLoopNode.class)) {
      text.append("{$FOR ");
      
      text.append(((ForLoopNode)node).getVariable().asText());
      text.append(" ");

      text.append( unparseElement(  ((ForLoopNode)node).getStartExpression()  ) );
      text.append(" ");
      
      text.append( unparseElement(  ((ForLoopNode)node).getEndExpression()  ) );
      text.append(" ");
      
      if(((ForLoopNode)node).getStepExpression() != null) {
        text.append( unparseElement(  ((ForLoopNode)node).getStepExpression()  ) );
        text.append(" ");
      }
      
      text.append("$}");
      
      for(int i = 0, length = node.numberOfChildren(); i < length; ++i) {
        create(text, node.getChild(i));
      }
      
      text.append("{$END$}");
    
    } else if(node.getClass().equals(EchoNode.class)) {
      
      text.append("{$= ");
      Element[] elements = ((EchoNode)node).getElements();
      
      for(int i=0, length = elements.length; i < length; ++i) {
        text.append( unparseElement( elements[i] ) );
      }
      text.append("$}");
    }
  }
  
  /**
   * Parse document on the Element layer. Lowest layer in the tree.
   * @param element
   * @return String
   * */
  private static String unparseElement(Element element) {
    
    if(element.getClass().equals(ElementConstantDouble.class)) {
      return element.asText() + " ";
    
    } else if(element.getClass().equals(ElementConstantInteger.class)) {
      return element.asText() + " ";
    
    } else if(element.getClass().equals(ElementFunction.class)) {
      return "@" + element.asText() + " ";
    
    } else if(element.getClass().equals(ElementOperator.class)) {
      return element.asText() + " ";
    
    } else if(element.getClass().equals(ElementVariable.class)) {
      return element.asText() + " ";
    
    } else if(element.getClass().equals(ElementString.class)) {
    
      StringBuilder newText = new StringBuilder();
      String stringText = element.asText();
    
      // start of string
      
      newText.append("\"");
      
      for(int i = 0, length = stringText.length(); i < length; ++i) {
      
        if(stringText.charAt(i) == '\\') {
          newText.append("\\");
          newText.append("\\");
        
        } else if (stringText.charAt(i) == '\"') {
          newText.append("\\");
          newText.append("\"");
        
        }  else {
          newText.append(stringText.charAt(i));
        }
      }
      // end of string
      
      newText.append("\" ");
      
      return newText.toString();
    }
    
    return element.asText();
  }

}
