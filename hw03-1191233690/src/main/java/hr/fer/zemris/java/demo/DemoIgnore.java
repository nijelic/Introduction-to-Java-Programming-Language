package hr.fer.zemris.java.demo;

/**
 * Ignore this class. It's totally demo.
 * */
public class DemoIgnore {

  /**
   * main demo
   * */
  public static void main(String[] args) {
    Character test = '\1';
    System.out.println(Character.isLetter(test));
    System.out.println(test.equals('\1'));
    System.out.println(Character.isDigit(test));
    System.out.println(Character.isWhitespace(test));
    System.out.println((int)'\\');
    StringBuilder sb = new StringBuilder();
    sb.append("\\");
    sb.append(Integer.toString((int)'\0'));
    sb.append(Integer.toString((int)'\7'));
    System.out.println(sb.toString());
    char[] data = "  \\1st  \r\n\t   ".toCharArray();
    int dataLength = data.length;
    for(int currentIndex = 0 ; currentIndex < data.length; currentIndex++) {
      System.out.println(data[currentIndex]);
      if(data[currentIndex] == '\\' && currentIndex+1 < dataLength && Character.isDigit( data[currentIndex+1] ) ) {
        currentIndex++;
        System.out.println(data[currentIndex]);
    }
    }
    int currentIndex = 0;
    StringBuilder stringToken = new StringBuilder();
    
    while( currentIndex < dataLength && ( Character.isLetter( data[currentIndex] ) || data[currentIndex] == '\\' ) ) {
      stringToken.append(data[currentIndex]);
      System.out.println("kil" + currentIndex);
      if(data[currentIndex] == '\\' && currentIndex+1 < dataLength && Character.isDigit( data[currentIndex+1] ) ) {
          ++currentIndex;
          stringToken.append(data[currentIndex]);
      }
      
      ++currentIndex;
    }
    String str = "";
    char[] data2 = str.toCharArray();
    System.out.println(stringToken.toString());
    System.out.println(data2 + " " +str.length() );
    System.out.println(".");
    sb = new StringBuilder();
    sb.append("{$");
    sb.append('\\');
    sb.append("-12");
    System.out.println(sb.toString().equals(""));
    System.out.println(".");
    System.out.println(sb.toString());
    System.out.println(sb.toString());
    String[] a = new String[0];
    System.out.print(a==null);
  }
 
}
