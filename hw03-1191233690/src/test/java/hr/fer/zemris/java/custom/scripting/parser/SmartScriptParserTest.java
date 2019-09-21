package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.elems.*;
import org.junit.jupiter.api.Test;

class SmartScriptParserTest {

  @Test
  public void testSmartScriptParserTrivialExceptions() {
    
    // should not throw
    new SmartScriptParser("");
    // should throw
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser(null));
    
    
  }
  
  @Test
  public void testSmartScriptParserSemanticExceptions() {

    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$$}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$+$}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$FOr i i i i$}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$For 7 $}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("\\a"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser( "{$for i 7 \" \"-12.5$}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$ FOR 3 1 10 1 $}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$ FOR year @sin 10 $}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$ FOR year $}"));
    assertThrows(SmartScriptParserException.class, ()-> new SmartScriptParser("{$ FOR year 1 10 1 3 $}"));
  }
  
  @Test
  public void testSmartScriptParserChildClasses() {
    SmartScriptParser pars = new SmartScriptParser( "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
    DocumentNode node = pars.getDocumentNode();
        
    assertEquals(3, node.numberOfChildren());
    assertEquals(TextNode.class, node.getChild(0).getClass());
    assertEquals("A tag follows ", ((TextNode)node.getChild(0)).getText());
    assertEquals("Joe \"Long\" Smith", ( (  (EchoNode) node.getChild(1)  ).getElements()[0] ).asText());
    assertEquals(TextNode.class, node.getChild(2).getClass());
    
  }

  @Test
  public void testForLoop() {
    new SmartScriptParser( "{$for i 7 \" \"-12.5$}{$END$}");
    new SmartScriptParser("{$for i 7 \"abraKa4dabra\\\"KO-4NJ\\\" \"-12.5$}mirko{$= voli -PI -3.14$}!{$END$}");
    String docString = "{$For i 7  \"abraKa4dabra\\\"KO-4NJ\\\" \"  -12.5  $}mirko{$For i 7  \"abra\tK\na \r4dab\\\\ra\\\"KO-4NJ\\\" \"  -12.5  $}mirko{$= voli - PI -3.14 $}!{$END$} {$= voli - PI -3.14 $}!{$END$}";
    SmartScriptParser parser = new SmartScriptParser(docString);
    System.out.println(createOriginalDocumentBody(parser.getDocumentNode()));
    assertEquals(true, docString.equals(createOriginalDocumentBody(parser.getDocumentNode())));
  }
  
  @Test
  public void testSmartScriptParserNotExceptions() {
	  new SmartScriptParser("{$ FOR i-1.35bbb$}{$eNd$}");
	  new SmartScriptParser("{$ FOR i-1.35bbb\"1\" $}{$enD$}");
	  new SmartScriptParser("{$= \"Štefica \\\"Štefa\\\" Štefanija\" $}");
	  SmartScriptParser parser = new SmartScriptParser("{$For i 7  \"abra\tK\na \r4dab\\\\ra\\\"KO-4NJ\\\" \"  -12.5  $}{$END$}");
	  
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
      text.append("{$For ");
      
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
