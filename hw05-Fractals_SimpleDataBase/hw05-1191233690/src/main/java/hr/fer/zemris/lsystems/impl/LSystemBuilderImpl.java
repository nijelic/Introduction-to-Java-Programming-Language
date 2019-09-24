package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.math.Vector2D;

import java.awt.Color;
import java.util.Scanner;

/**
 * Main class for L-Systems. 
 * */
public class LSystemBuilderImpl implements LSystemBuilder {

  /**
   * unit length of step
   * */
  private double unitLength;
  /**
   * degree scaler for unit length
   * */
  private double unitLengthDegreeScaler;
  /**
   * origin of {@link TurtleState}
   * */
  private Vector2D origin;
  /**
   * angle of {@link TurtleState}
   * */
  private double angle;
  /**
   * axiom of fractal
   * */
  private String axiom;
  /**
   * commands of fractal
   * */
  private Dictionary<Character, Command> commands;
  /**
   * productions of fractal
   * */
  private Dictionary<Character, String> productions;
  

  /**
   * Default constructor
   * */
  public LSystemBuilderImpl() {
    unitLength = 0.1;
    unitLengthDegreeScaler = 1;
    origin = new Vector2D(0, 0);
    angle = 0;
    axiom = "";
    commands = new Dictionary<Character, Command>();
    productions = new Dictionary<Character, String>();
  }
  
  /**
   * Sets unitLength.
   * @param unitLength
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder setUnitLength(double unitLength) {
    this.unitLength = unitLength;
    return this;
  }
  
  /**
   * Sets origin.
   * @param x origin
   * @param y origin
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder setOrigin(double x, double y) {
    this.origin = new Vector2D(x, y);
    return this;
  }
  
  /**
   * Sets angle.
   * @param angle
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder setAngle(double angle) {
    this.angle = Math.toRadians(angle);
    return this;
  }
  
  /**
   * Sets axiom.
   * @param axiom as String
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder setAxiom(String axiom) {
    this.axiom = axiom;
    return this;
  }

  /**
   * Sets unitLengthDegreeScaler.
   * @param unitLengthDegreeScaler as double
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
    this.unitLengthDegreeScaler = unitLengthDegreeScaler;
    return this;
  }
  
  /**
   * Adds production.
   * @param symbol as char 
   * @param production as String
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder registerProduction(char symbol, String production) {
    productions.put(symbol, production);
    return this;
  }
  
  /**
   * Adds command/action.
   * @param symbol as char 
   * @param action as String
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder registerCommand(char symbol, String action) {
    Scanner scan = new Scanner(action);
    String type = scan.next();
    
    if(type.equals("draw")) {
      commands.put(
          symbol, 
          new DrawCommand(
              Double.parseDouble( scan.next() )
          )
      );
    } else if(type.equals("skip")) {
      commands.put(
          symbol, 
          new SkipCommand(
              Double.parseDouble( scan.next() )
          ) 
      );
    } else if(type.equals("scale")) {
      commands.put(
          symbol, 
          new ScaleCommand(
              Double.parseDouble( scan.next() )
          )
      );
    } else if(type.equals("rotate")) {
      commands.put(
          symbol, 
          new RotateCommand(
              Math.toRadians(  Double.parseDouble( scan.next() )  )
          )
      );
    } else if(type.equals("push")) {
      commands.put(
          symbol, 
          new PushCommand()
      );
    } else if(type.equals("pop")) {
      commands.put(
          symbol,  
          new PopCommand()
      );
    } else if(type.equals("color")) {
      commands.put(
          symbol,  
          new ColorCommand( Color.decode("#"+scan.next()) )
      );
    }
    
    scan.close();
    return this;
  }
  
  
  /**
   * Configurates {@link LSystemBuilder} from text.
   * @param lines that contain configuration
   * @return {@link LSystemBuilder}
   * */
  public LSystemBuilder configureFromText(String[] lines) {
    
    for(int i = 0, length = lines.length; i < length; ++i) {
      
      Scanner scan = new Scanner(lines[i]);
      
      if(scan.hasNext()) {
        
        String updateName = scan.next();
        
        if(updateName.equals("origin")) {
          
          double x = Double.parseDouble(scan.next());
          double y = Double.parseDouble(scan.next());
          
          setOrigin(x, y);
        
        } else if(updateName.equals("angle")) {
          
          setAngle(Double.parseDouble(scan.next()));
        
          
        } else if(updateName.equals("unitLength")) {
          
          setUnitLength(Double.parseDouble(scan.next()));
        
          
        } else if(updateName.equals("unitLengthDegreeScaler")) {
          
          parseUnitLengthDegreeScaler(scan);
          
          
        } else if(updateName.equals("command")) {
          
          char symbol = scan.next().charAt(0);
          StringBuilder action = new StringBuilder();
          
          action.append(scan.next());
          while(scan.hasNext()) {
            action.append(" " + scan.next());
          }
          registerCommand(symbol, action.toString());
          
          
        } else if(updateName.equals("axiom")) {
          
          setAxiom(scan.next());
          
          
        } else if(updateName.equals("production")) {
          
          char symbol = scan.next().charAt(0);
          
          registerProduction(symbol, scan.next());
          
          
        } else {
          
          scan.close();
          throw new IllegalArgumentException(updateName + " is not valid.");
        
        
        }
      }
      scan.close();
    }
    
    return this;
  }
  
  /**
   * Returns LSystem of {@link LSystemBuilderImpl}.
   * @return {@link LSystem} of {@link LSystemBuilderImpl}
   * */
  public LSystem build() {
    return new LSystemImpl(this);
  }

  /**
   * Implementation of LSystem.
   * */
  private static class LSystemImpl implements LSystem {
    
    
    /**
     * reference of {@link LSystemBuilderImpl} object.
     * */
    private LSystemBuilderImpl reference;
    
    /**
     * Constructor gets reference of {@link LSystemBuilderImpl}.
     * @param reference
     * */
    public LSystemImpl(LSystemBuilderImpl reference) {
      this.reference = reference;
    }
    
    /**
     * Generates n-th generation of fractals.
     * @param arg0 number of generation
     * @return generation as String
     * */
    @Override
    public String generate(int arg0) {
      String generation = reference.axiom;
      
      for(int i = 0; i < arg0; ++i) {
        StringBuilder temporary = new StringBuilder();
        
        for(int symbolIndex = 0, length = generation.length(); symbolIndex < length; ++symbolIndex) {
          
          char symbol = generation.charAt(symbolIndex);
          
          if(reference.productions.get( symbol ) != null) {
            
            temporary.append( reference.productions.get( symbol ) );
          
          } else {
            temporary.append( symbol );
          }
          
        }
        generation = temporary.toString();
      }
      return generation;
    }
    
    /**
     * Draws the fractal.
     * @param arg0 degree of generation
     * @param painter used for drawing lines
     * */
    @Override
    public void draw(int arg0, Painter painter) {
      String generation = generate(arg0);
      Context ctx = new Context();
      Vector2D orientation = new Vector2D(1, 0);
      orientation.rotate(reference.angle);
      
      TurtleState turtle = new TurtleState(
          reference.origin.copy(), 
          orientation, 
          Color.BLACK, 
          effectiveLength(arg0)
      );
      
      ctx.pushState(turtle);
      
      for(int symbol = 0, length = generation.length(); symbol < length; ++symbol) {
        Command action = reference.commands.get(generation.charAt(symbol));
        if(action != null){
          action.execute(ctx, painter);
        }
      }
      
    }
    
    /**
     * Used for scaling unitLength. Calculates effectiveLength.
     * @param d degree of fractal
     * @return effectiveLength
     * */
    private double effectiveLength(int d) {
      return reference.unitLength * Math.pow(reference.unitLengthDegreeScaler, d);    
    }
    
  }
  
  /**
   * Method that parses UnitLengthDegreeScaler from input. It can be one double number, or fraction of doubles.
   * */
  private void parseUnitLengthDegreeScaler(Scanner scan) {
    String numeratorString = scan.next();
    int indexOfSlash = numeratorString.indexOf('/');
    
    if(indexOfSlash > 0) {
      
      double numerator = Double.parseDouble( numeratorString.substring(0, indexOfSlash) );
      
      if(numeratorString.substring(indexOfSlash).length() - 1 > 0) {
        setUnitLengthDegreeScaler(
            numerator / Double.parseDouble( numeratorString.substring(indexOfSlash) )
            );
        return;
      }
      
      setUnitLengthDegreeScaler(
          numerator / Double.parseDouble( scan.next() )
          );
      return;
    }
 
    // else doesn't contain '/'
    double numerator = Double.parseDouble( numeratorString );   
    
    if(!scan.hasNext()) {
      setUnitLengthDegreeScaler( numerator );
      return;
    }
    
    String slashWithDenominator = scan.next();
    
    if(slashWithDenominator.length() == 1) {
      setUnitLengthDegreeScaler(
          numerator / Double.parseDouble( scan.next() )
          );
      return;
    }
    
    setUnitLengthDegreeScaler(
        numerator / Double.parseDouble( slashWithDenominator.substring(1) )
        );
    return;
  }
  
}
