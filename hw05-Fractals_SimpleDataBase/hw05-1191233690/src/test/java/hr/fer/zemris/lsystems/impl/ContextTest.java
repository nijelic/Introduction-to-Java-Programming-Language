package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Vector2D;

class ContextTest {

  @Test
  void test() {
    Context ctx = new Context();
    TurtleState turt = new TurtleState(new Vector2D(1, 1), new Vector2D(1, 1), Color.BLACK, 0.5);
    ctx.pushState(turt);
  }

}
