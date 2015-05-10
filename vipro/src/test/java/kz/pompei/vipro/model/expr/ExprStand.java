package kz.pompei.vipro.model.expr;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class ExprStand extends JFrame {
  public static void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    
    ExprStand form = new ExprStand();
    form.setLocation(1000, 100);
    form.setSize(800, 600);
    
    form.setVisible(true);
  }
  
  private final ExprStandPanel exprPanel = new ExprStandPanel();
  
  public ExprStand() {
    setTitle("Стэнд отрисовки выражений");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    exprPanel.setExpr(ExprExample.ONE.create());
    
    JPanel wall = new JPanel();
    wall.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    
    {
      JPanel top = new JPanel();
      top.setLayout(new FlowLayout(FlowLayout.LEFT));
      top.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
      
      for (final ExprExample e : ExprExample.values()) {
        JButton b = new JButton(e.name());
        top.add(b);
        b.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent event) {
            exprPanel.setExpr(e.create());
          }
        });
      }
      
      c.gridx = 0;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.WEST;
      c.weightx = 1;
      c.weighty = 0;
      wall.add(top, c);
    }
    
    {
      c.gridx = 0;
      c.gridy = 1;
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1;
      c.weighty = 1;
      wall.add(exprPanel, c);
    }
    
    setContentPane(wall);
  }
}
