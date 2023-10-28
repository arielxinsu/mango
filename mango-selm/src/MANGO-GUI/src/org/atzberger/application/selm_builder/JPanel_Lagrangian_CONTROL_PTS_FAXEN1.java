package org.atzberger.application.selm_builder;

import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * Panel for editing this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Lagrangian_CONTROL_PTS_FAXEN1 extends JPanel_Lagrangian {
  
    public JPanel_Lagrangian_CONTROL_PTS_FAXEN1(application_SharedData applSharedData_in) {
      super();
      applSharedData = applSharedData_in;
      initComponents();

      /* add listener to the jTable_Lagrangian for any changes there */
      jTable_Lagrangian.getModel().addTableModelListener(new TableModelListener() {
        public void tableChanged(TableModelEvent e)  {
          jTable_Lagrangian_tableChanged(e);
        }
      });

    }

    public void jTable_Lagrangian_tableChanged(TableModelEvent e)  {
      //System.out.println("jTable_Lagrangian changed event" + e);
      
      JPanel jPanel_parent = (JPanel) this.getParent();

      if (jPanel_parent != null)
        jPanel_parent.firePropertyChange("CHANGED_LAGRANGIAN_DATA", 0, 1);
      
    }


    /** Creates new form JPanel_Lagrangian_CONTROL_PTS_BASIC1 */
    public JPanel_Lagrangian_CONTROL_PTS_FAXEN1() {
      super();
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

    jScrollPane_Lagrangian = new javax.swing.JScrollPane();
    jTable_Lagrangian = new org.atzberger.application.selm_builder.JTable_Lagrangian_ControlPts_BASIC1(new org.atzberger.application.selm_builder.TableModel_CouplingOperator_TABLE1_tmp(applSharedData));
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();

    setName("CONTROL_PTS_FAXEN1"); // NOI18N

    jScrollPane_Lagrangian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    jScrollPane_Lagrangian.setName("jScrollPane_Lagrangian"); // NOI18N

    jTable_Lagrangian.setBorder(javax.swing.BorderFactory.createLineBorder(null));
    jTable_Lagrangian.setModel(new org.atzberger.application.selm_builder.TableModel_Lagrangian_CONTROL_PTS_FAXEN1());
    jTable_Lagrangian.setName("jTable_Lagrangian"); // NOI18N
    jScrollPane_Lagrangian.setViewportView(jTable_Lagrangian);

    jButton1.setName("jButton1"); // NOI18N

    jButton2.setName("jButton2"); // NOI18N

    jButton3.setName("jButton3"); // NOI18N

    jButton4.setName("jButton4"); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane_Lagrangian, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jButton1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
            .addComponent(jButton4)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton3)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButton1)
          .addComponent(jButton2)
          .addComponent(jButton3)
          .addComponent(jButton4))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
        .addComponent(jScrollPane_Lagrangian, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JScrollPane jScrollPane_Lagrangian;
  private javax.swing.JTable jTable_Lagrangian;
  // End of variables declaration//GEN-END:variables

  @Override
  public void setData(SELM_Lagrangian lagrangian) {
    ((TableModel_Lagrangian_CONTROL_PTS_FAXEN1)(jTable_Lagrangian.getModel())).setFromLagrangianData(lagrangian);
  }

  @Override
  public SELM_Lagrangian getData() {
    SELM_Lagrangian lagrangian;

    lagrangian = ((TableModel_Lagrangian_CONTROL_PTS_FAXEN1)(jTable_Lagrangian.getModel())).getLagrangianDataFromModel();

    return lagrangian;
  }

}