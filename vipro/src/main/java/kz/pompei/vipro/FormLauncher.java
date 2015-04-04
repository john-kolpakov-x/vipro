package kz.pompei.vipro;

import javax.swing.JFrame;
import javax.swing.UIManager;

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
    
    f.setContentPane(new ViproEditorPanel());
    
    f.setVisible(true);
  }
}
