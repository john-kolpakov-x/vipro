package kz.pompei.vipro;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoadCsv_dso {
  public static void main(String[] args) throws Exception {
    new LoadCsv_dso().execute();
  }

  final int maxBatchSize = 30_000;
  final String dir = "/home/pompei/tmp/HYG-Database";

  private void execute() throws Exception {


    Class.forName("org.postgresql.Driver");

    try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/stars", "stars", "")) {

      loadTableFromCsvFile(con, "dso.csv", "dso_str");
      loadTableFromCsvFile(con, "hygdata_v3.csv", "hygdata_v3_str");
      loadTableFromCsvFile(con, "hygfull.csv", "hygfull_str");
      loadTableFromCsvFile(con, "hygxyz.csv", "hygxyz_str");

    }

    System.out.println("Finish");

  }

  private void loadTableFromCsvFile(Connection con, String fileName, String targetTable) throws IOException, SQLException {
    File inFile = new File(dir + "/" + fileName);
    try (BufferedReader rd = new BufferedReader(new InputStreamReader(
      new FileInputStream(inFile), "UTF-8"))) {

      String[] fields = rd.readLine().split(",");
      StringBuilder sql = new StringBuilder();
      sql.append("create table ").append(targetTable).append('(');
      for (String field : fields) {
        sql.append(field).append(" varchar(50),");
      }
      sql.setLength(sql.length() - 1);
      sql.append(')');

      try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
        ps.executeUpdate();
      }

      sql.setLength(0);
      sql.append("insert into ").append(targetTable).append(" (");
      for (String field : fields) {
        sql.append(field).append(", ");
      }
      sql.setLength(sql.length() - 2);
      sql.append(") values (");
      for (String field : fields) {
        sql.append("?, ");
      }
      sql.setLength(sql.length() - 2);
      sql.append(')');

      try (PreparedStatement ps = con.prepareStatement(sql.toString())) {

        int currentBatchSize = 0;
        while (true) {

          String line = rd.readLine();
          if (line == null) break;

          String[] split = line.split(",");
          int index = 1;
          for (String x : split) {
            ps.setString(index++, emptyToNull(x));
          }
          while (index <= fields.length) {
            ps.setString(index++, null);
          }
          ps.addBatch();
          currentBatchSize++;

          if (currentBatchSize >= maxBatchSize) {
            ps.executeBatch();
            currentBatchSize = 0;
            System.out.println("Executed batch");
          }

        }

        if (currentBatchSize > 0) {
          ps.executeBatch();
        }

      }
    }

    System.out.println("Loaded file " + fileName + " into " + targetTable);

  }

  private static String emptyToNull(String s) {
    if (s == null) return null;
    s = s.trim();
    while (s.startsWith("\"")) {
      s = s.substring(1).trim();
    }
    while (s.endsWith("\"")) {
      s = s.substring(0, s.length() - 1).trim();
    }
    if (s.length() == 0) return null;
    return s;
  }
}
