package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Demo3 {

  public static void main(String[] args) {
    LSystemViewer.showLSystem(LSystemBuilderImpl::new);
  }
}
