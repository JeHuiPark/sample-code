package ms.excel.utf8.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class Example {

  public static void main(String[] args) throws IOException {
    var noBomCsv = new File("ms-excel-no-bom-example.csv");
    var bomCsv = new File("ms-excel-bom-example.csv");

    try (var noBomCsvWriter = new FileOutputStream(noBomCsv);
         var bomCsvWriter = new FileOutputStream(bomCsv)) {

      var csvString = "이름,나이,성별\n"
          + "박제희,29,남\n"
          + "홍길동,30,남";

      noBomCsvWriter.write(csvString.getBytes());

      apply_MS_UTF8_Encoding_BOM(bomCsvWriter);
      bomCsvWriter.write(csvString.getBytes());
    }
  }

  private static void apply_MS_UTF8_Encoding_BOM(FileOutputStream bomCsvWriter) throws IOException {
    bomCsvWriter.write(new byte[]{-17, -69, -65});
  }
}
