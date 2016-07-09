package kz.pompei.vipro.nuclide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadNuclideTable {
  public static void main(String[] args) throws Exception {
    File f = new File("/home/pompei/discs/linux-data/masses_of_nuclides");

    final List<Nuclide> list = new ArrayList<>();


    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"))) {

      int lineNo = 0;
      while (true) {
        String line = br.readLine();
        if (line == null) break;
        lineNo++;
        if (lineNo < 40) continue;
        String sN = line.substring(6, 9).trim();
        String sZ = line.substring(11, 14).trim();
        String name = line.substring(20, 22).trim();
        String sMass = line.substring(96, min(111, line.length())).trim();
//        System.out.println("sN = " + sN + ", sZ = " + sZ + ", name = " + name + ", sMass = " + sMass);
        Nuclide nuclide = new Nuclide(Integer.parseInt(sN), Integer.parseInt(sZ), name, toDouble(sMass));
        list.add(nuclide);
//        System.out.println(nuclide + ", atomic massAEM = " + nuclide.massAEM);
      }

    }

    final Map<String, Nuclide> map = new HashMap<>();
    list.forEach(value -> map.put(value.nuclideName(), value));

    double massH1 = map.get("H-1").massAEM;
    for (Nuclide nuclide : list) {
      nuclide.massP = nuclide.massAEM / massH1;
      double delta = (nuclide.A() - nuclide.massP) * 10000;
      double deltaU = delta / nuclide.A();
      System.out.println(nuclide + ", atomic massP = " + nuclide.massP + ", delta = " + delta + ", deltaU = " + deltaU);
    }
  }

  private static double toDouble(String str) {
    return Double.parseDouble(str.trim().replaceAll("\\s+", "").replaceAll("#", ""));
  }

  private static int min(int a, int b) {
    return a < b ? a : b;
  }
}
