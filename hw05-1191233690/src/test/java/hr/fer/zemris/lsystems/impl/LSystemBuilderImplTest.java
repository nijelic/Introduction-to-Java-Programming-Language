package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.lsystems.LSystem;

class LSystemBuilderImplTest {

  LSystemBuilderImpl lSystem = new LSystemBuilderImpl();
  
  @Test
  void testLSystemBuilderImpl() {
    lSystem = new LSystemBuilderImpl();
  }

  @Test
  void testSetUnitLength() {
    lSystem.setUnitLength(0.3);
  }

  @Test
  void testSetOrigin() {
    lSystem.setOrigin(0.1, 0.1);
  }

  @Test
  void testSetAngle() {
    lSystem.setAngle(90);
  }

  @Test
  void testSetAxiom() {
    lSystem.setAxiom("F");
  }

  @Test
  void testSetUnitLengthDegreeScaler() {
    lSystem.setUnitLengthDegreeScaler(0.2);
  }

  @Test
  void testRegisterProduction() {
    lSystem.registerProduction('F', "AF");
    lSystem.registerProduction('A', "F-F").registerProduction('-', "AF-F");
  }

  @Test
  void testRegisterCommand() {
    lSystem.registerCommand('A', "draw 0.1");
    lSystem.registerCommand('-', "rotate -30").registerCommand('F', "skip 0.05");
  }

  @Test
  void testConfigureFromText() {
    String[] data = new String[] {
        "origin 0.05 0.4",
        "angle 0",
        "unitLength 0.9",
        "unitLengthDegreeScaler 1.0 / 3.0",
        "",
        "command F draw 1",
        "command + rotate 60",
        "command - rotate -60",
        "",
        "axiom F",
        "",
        "production F F+F--F+F"
        };
    lSystem.configureFromText(data);
  }

  @Test
  void testBuild() {
    lSystem.build();
  }
  
  @Test
  void testFromBook() {
    LSystemBuilderImpl system = new LSystemBuilderImpl();
   
    String[] data = new String[] {
        "origin 0.05 0.4",
        "angle 0",
        "unitLength 0.9",
        "unitLengthDegreeScaler 1.0 / 3.0",
        "",
        "command F draw 1",
        "command + rotate 60",
        "command - rotate -60",
        "",
        "axiom F",
        "",
        "production F F+F--F+F"
        };
    system.configureFromText(data);

    LSystem lSystemObject = system.build();
    
    assertEquals("F", lSystemObject.generate(0));
    assertEquals("F+F--F+F", lSystemObject.generate(1));
    assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystemObject.generate(2));
    
  }

}
