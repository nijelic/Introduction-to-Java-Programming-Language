package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class SmartScriptLexerTest {

  
  @Test
  public void testMiddleBackslash() {
    SmartScriptLexer lexer = new SmartScriptLexer("{$Some \\ test X$}");

    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$",lexer.nextToken().getValue());
    assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
    assertEquals("Some",lexer.nextToken().getValue());
    assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
    // '\\' must not accept
    assertThrows(LexerException.class, ()->lexer.nextToken());
  }
  
  
  @Test
  public void testEscapingCurlyBracket() {
    SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");
    
    assertEquals("Example { bla } blu {$=1$}. Nothing interesting {=here}.",lexer.nextToken().getValue());
    assertEquals(SmartTokenType.TEXT, lexer.getToken().getType());
  }
  
  
  @Test
  public void testEqualSign() {
    SmartScriptLexer lexer = new SmartScriptLexer("{$=$}");
    
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$", lexer.nextToken().getValue());
    assertEquals("=", lexer.nextToken().getValue().toString());
    assertEquals("$}", lexer.nextToken().getValue());
    lexer.setState(SmartLexerState.TEXT);
    
  }
  
  
  @Test
  public void testString() {
    SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
    
    assertEquals("A tag follows ", lexer.nextToken().getValue());
    
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$", lexer.nextToken().getValue());
    assertEquals("=", lexer.nextToken().getValue().toString());
    assertEquals("Joe \"Long\" Smith", lexer.nextToken().getValue());
    assertEquals("$}", lexer.nextToken().getValue());
    lexer.setState(SmartLexerState.TEXT);
    assertEquals(".", lexer.nextToken().getValue());
    
  }
  
  
  @Test
  public void testOpenCloseTag() {
    SmartScriptLexer lexer = new SmartScriptLexer("{$$}");
  
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$",lexer.nextToken().getValue());
    assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
    assertEquals("$}",lexer.nextToken().getValue());
    assertEquals(SmartTokenType.CLOSE_TAG, lexer.getToken().getType());
    lexer.setState(SmartLexerState.TEXT);
    assertEquals(null, lexer.nextToken().getValue());
    assertEquals(SmartTokenType.EOF, lexer.getToken().getType());
  }

  
  @Test
  public void testCloseTag() {
    SmartScriptLexer lexer = new SmartScriptLexer("$}");
    
    assertEquals("$}",lexer.nextToken().getValue());
    assertEquals(SmartTokenType.TEXT, lexer.getToken().getType());
  }
  
  
  @Test
  public void testNextToken() {
    SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n" + 
        "{$ FOR i 1 10 1 $}\n" + 
        " This is {$= i $}-th time this message is generated.\t" + 
        "{$END$}\n" + 
        "{$FOR i 0 10 2 $}\n" + 
        " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + 
        "{$END$}");
    
    assertEquals("This is sample text.\r\n", lexer.nextToken().getValue());
    
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$", lexer.nextToken().getValue());
    assertEquals("FOR", lexer.nextToken().getValue());
    assertEquals("i", lexer.nextToken().getValue());
    assertEquals(1, lexer.nextToken().getValue());
    assertEquals(10, lexer.nextToken().getValue());
    assertEquals(1, lexer.nextToken().getValue());
    assertEquals("$}", lexer.nextToken().getValue());
    
    lexer.setState(SmartLexerState.TEXT);
    assertEquals("\n This is ", lexer.nextToken().getValue());
    
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$", lexer.nextToken().getValue());
    assertEquals("=", lexer.nextToken().getValue().toString());
    assertEquals("i", lexer.nextToken().getValue());
    assertEquals("$}", lexer.nextToken().getValue());
    
    lexer.setState(SmartLexerState.TEXT);
    assertEquals("-th time this message is generated.\t", lexer.nextToken().getValue());
    
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$", lexer.nextToken().getValue());
    assertEquals("END", lexer.nextToken().getValue());
    assertEquals("$}", lexer.nextToken().getValue());
    lexer.setState(SmartLexerState.TEXT);
  }

  
  @Test
  public void testGetState() {
    SmartScriptLexer lexer = new SmartScriptLexer("{$$}");
    
    lexer.setState(SmartLexerState.TAG);
    assertEquals(SmartLexerState.TAG, lexer.getState());
    
    lexer = new SmartScriptLexer("$}");
    lexer.setState(SmartLexerState.TEXT);
    assertEquals(SmartLexerState.TEXT, lexer.getState());
    
    lexer = new SmartScriptLexer("");
    
    assertNotNull(lexer.nextToken());
  }
 
  
  @Test
  public void testTrivialInput() {
 
      assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
      
      SmartScriptLexer lexer = new SmartScriptLexer("");
      
      assertEquals(SmartTokenType.EOF, lexer.nextToken().getType());
  }

 

  
  @Test
  public void testRadAfterEOF() {
      SmartScriptLexer lexer = new SmartScriptLexer("");
      
      // will obtain EOF
      lexer.nextToken();
      // will throw!
      assertThrows(LexerException.class, () -> lexer.nextToken());
  }
  
  
  @Test
  public void testBookExample() {
    SmartScriptLexer lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
    assertEquals("A tag follows ", lexer.nextToken().getValue());
    assertEquals("A tag follows ", lexer.getToken().getValue());
    assertEquals(SmartTokenType.TEXT, lexer.getToken().getType());
    lexer.setState(SmartLexerState.TAG);
    assertEquals("{$", lexer.nextToken().getValue().toString());
    assertEquals("{$", lexer.getToken().getValue());
    assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
    assertEquals("=", lexer.nextToken().getValue().toString());
    assertEquals("=", lexer.getToken().getValue());
    assertEquals(SmartTokenType.SYMBOL, lexer.getToken().getType());
    assertEquals("Joe \"Long\" Smith", lexer.nextToken().getValue().toString());
    assertEquals("Joe \"Long\" Smith", lexer.getToken().getValue());
    assertEquals(SmartTokenType.STRING, lexer.getToken().getType());
    assertEquals("$}", lexer.nextToken().getValue().toString());
    assertEquals("$}", lexer.getToken().getValue());
    assertEquals(SmartTokenType.CLOSE_TAG, lexer.getToken().getType());
    
    lexer.setState(SmartLexerState.TEXT);
    assertEquals(".", lexer.nextToken().getValue().toString());
    assertEquals(".", lexer.getToken().getValue());
    assertEquals(SmartTokenType.TEXT, lexer.getToken().getType());
    assertEquals(null, lexer.nextToken().getValue());
    assertEquals(SmartTokenType.EOF, lexer.getToken().getType());
  
    
  }
  
  @Test
  public void testForLoop() {
	  SmartScriptLexer lexer =  new SmartScriptLexer("{$ FOR i-1.35bbb$}");
	  
	  lexer.setState(SmartLexerState.TAG);
	  assertEquals("{$", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
	  assertEquals("FOR", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("i", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals(-1.35, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.DOUBLE, lexer.getToken().getType());
	  assertEquals("bbb", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("$}", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.CLOSE_TAG, lexer.getToken().getType());
	  
	  lexer.setState(SmartLexerState.TEXT);
	  assertEquals(null, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.EOF, lexer.getToken().getType());
	  
	  
	  lexer = new SmartScriptLexer("{$ FOR i-1.35bbb\"1\" $}");
	  
	  lexer.setState(SmartLexerState.TAG);
	  assertEquals("{$", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
	  assertEquals("FOR", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("i", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals(-1.35, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.DOUBLE, lexer.getToken().getType());
	  assertEquals("bbb", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("1", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.STRING, lexer.getToken().getType());
	  assertEquals("$}", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.CLOSE_TAG, lexer.getToken().getType());
	  
	  lexer.setState(SmartLexerState.TEXT);
	  assertEquals(null, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.EOF, lexer.getToken().getType());
	  
  }
  
  @Test
  public void testStringAndFor() {
	  SmartScriptLexer lexer = new SmartScriptLexer("{$For i 7  \"abra\tK\na \r4dab\\\\ra\\\"KO-4NJ\\\" \"  -12.5  $}{$END$}");
	  
	  lexer.setState(SmartLexerState.TAG);
	  assertEquals("{$", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
	  assertEquals("For", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("i", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals(7, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.INTEGER, lexer.getToken().getType());
	  assertEquals("abra\tK\na \r4dab\\ra\"KO-4NJ\" ", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.STRING, lexer.getToken().getType());
	  assertEquals(-12.5, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.DOUBLE, lexer.getToken().getType());
	  assertEquals("$}", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.CLOSE_TAG, lexer.getToken().getType());
	  
	  lexer.setState(SmartLexerState.TEXT);
	  assertEquals("", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.TEXT, lexer.getToken().getType());
	  lexer.setState(SmartLexerState.TAG);
	  assertEquals("{$", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
	  assertEquals("END", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("$}", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.CLOSE_TAG, lexer.getToken().getType());
	  
	  lexer.setState(SmartLexerState.TEXT);
	  assertEquals(null, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.EOF, lexer.getToken().getType());
  }
  
  
  @Test
  public void testStringAndForException() {
	  SmartScriptLexer lexer = new SmartScriptLexer("{$For i 7  \"abra\\tK\\na \\r4dab\\\\ra\\\"KO-4NJ\\\" \"  -12.5  $}");
	  
	  lexer.setState(SmartLexerState.TAG);
	  assertEquals("{$", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.OPEN_TAG, lexer.getToken().getType());
	  assertEquals("For", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals("i", lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.NAME, lexer.getToken().getType());
	  assertEquals(7, lexer.nextToken().getValue());
	  assertEquals(SmartTokenType.INTEGER, lexer.getToken().getType());
	  assertThrows(LexerException.class, ()->lexer.nextToken());
  }
  
  
}
