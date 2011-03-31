import java.io.*;
public class CmdExec {

  public static void main(String argv[]) {
    try {
      String line;
      Process p = Runtime.getRuntime().exec
        ("perl C:\\geo_perl\\getLoc.pl \"1402 Mechanic Street North, 36703\"");
      BufferedReader input =
        new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
        System.out.println("SYSTEM: " + line);
      }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }
    System.out.println("DONE!!!");
  }
}
