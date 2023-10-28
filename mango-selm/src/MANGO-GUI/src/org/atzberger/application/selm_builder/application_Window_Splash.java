package org.atzberger.application.selm_builder;

/**
 * Splash screen frame.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 *
 */
public class application_Window_Splash extends javax.swing.JFrame {

    /** Creates new form application_Window_Splash */
    public application_Window_Splash() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    menuBar = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    openMenuItem = new javax.swing.JMenuItem();
    saveMenuItem = new javax.swing.JMenuItem();
    saveAsMenuItem = new javax.swing.JMenuItem();
    exitMenuItem = new javax.swing.JMenuItem();
    editMenu = new javax.swing.JMenu();
    cutMenuItem = new javax.swing.JMenuItem();
    copyMenuItem = new javax.swing.JMenuItem();
    pasteMenuItem = new javax.swing.JMenuItem();
    deleteMenuItem = new javax.swing.JMenuItem();
    helpMenu = new javax.swing.JMenu();
    contentsMenuItem = new javax.swing.JMenuItem();
    aboutMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setName("Form"); // NOI18N

    menuBar.setName("menuBar"); // NOI18N

    fileMenu.setName("fileMenu"); // NOI18N

    openMenuItem.setName("openMenuItem"); // NOI18N
    fileMenu.add(openMenuItem);

    saveMenuItem.setName("saveMenuItem"); // NOI18N
    fileMenu.add(saveMenuItem);

    saveAsMenuItem.setName("saveAsMenuItem"); // NOI18N
    fileMenu.add(saveAsMenuItem);

    exitMenuItem.setName("exitMenuItem"); // NOI18N
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    editMenu.setName("editMenu"); // NOI18N

    cutMenuItem.setName("cutMenuItem"); // NOI18N
    editMenu.add(cutMenuItem);

    copyMenuItem.setName("copyMenuItem"); // NOI18N
    editMenu.add(copyMenuItem);

    pasteMenuItem.setName("pasteMenuItem"); // NOI18N
    editMenu.add(pasteMenuItem);

    deleteMenuItem.setName("deleteMenuItem"); // NOI18N
    editMenu.add(deleteMenuItem);

    menuBar.add(editMenu);

    helpMenu.setName("helpMenu"); // NOI18N

    contentsMenuItem.setName("contentsMenuItem"); // NOI18N
    helpMenu.add(contentsMenuItem);

    aboutMenuItem.setName("aboutMenuItem"); // NOI18N
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 279, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new application_Window_Splash().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem aboutMenuItem;
  private javax.swing.JMenuItem contentsMenuItem;
  private javax.swing.JMenuItem copyMenuItem;
  private javax.swing.JMenuItem cutMenuItem;
  private javax.swing.JMenuItem deleteMenuItem;
  private javax.swing.JMenu editMenu;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenu helpMenu;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem openMenuItem;
  private javax.swing.JMenuItem pasteMenuItem;
  private javax.swing.JMenuItem saveAsMenuItem;
  private javax.swing.JMenuItem saveMenuItem;
  // End of variables declaration//GEN-END:variables

}
