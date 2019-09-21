package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Vector2D;

class TurtleStateTest {

  @Test
  void testTurtleState() {
    TurtleState t = new TurtleState(new Vector2D(0, 0), new Vector2D(1, 1), Color.BLACK, 0.5);
    assertEquals(new Vector2D(0, 0), t.getPosition());
    assertEquals(new Vector2D(Math.sqrt(2)/2, Math.sqrt(2)/2), t.getOrientation());
    assertEquals(Color.BLACK, t.getColor());
    assertEquals(0.5, t.getEffectiveLength());
  }

}
