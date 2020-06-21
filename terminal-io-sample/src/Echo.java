import java.io.PrintStream;
import java.util.Scanner;

class Echo {

  public static void main(String[] args) {
    String arg1 = args.length > 0 ? args[0] : "";

    try (Scanner sc = new Scanner(System.in)) {
      while(sc.hasNext()) {
        PrintStream var10000 = System.out;
        var10000.println(sc.next() + arg1);
      }
    }
  }
}
