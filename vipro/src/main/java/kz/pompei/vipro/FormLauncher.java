package kz.pompei.vipro;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.UIManager;

import kz.pompei.vipro.model.MethodDefinition;

public class FormLauncher {
  public static void main(String[] args) throws Exception {
    
    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    //    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    //    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
    
    JFrame.setDefaultLookAndFeelDecorated(true);
    
    JFrame f = new JFrame();
    f.setSize(800, 600);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLocation(1000, 10);
    f.setTitle("Привет всем Щ");
    
    ViproEditorPanel p = new ViproEditorPanel();
    {
      MethodDefinition md = new MethodDefinition();
      md.location = new Point(100, 100);
      md.name = "prepareLocation";
      p.source.methodList.add(md);
    }
    f.setContentPane(p);
    
    f.setVisible(true);
  }
}
