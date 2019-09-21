package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.collections.*;

public class ModificationCountDemo {
  
  public static void main(String[] args) {
    Collection col = new ArrayIndexedCollection();
    col.add("Ivo");
    col.add("Ana");
    col.add("Jasna");
    ElementsGetter getter = col.createElementsGetter();
    System.out.println("Jedan element: " + getter.getNextElement());
    System.out.println("Jedan element: " + getter.getNextElement());
    col.clear();
    System.out.println("Jedan element: " + getter.getNextElement());
    }
}

