package org.atzberger.application.selm_builder;

import javax.swing.JTable;

/**
 *
 * Panel for editing this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Interaction_TARGET1 extends JPanel_Interaction {

  public application_SharedData applSharedData = null;

    public JPanel_Interaction_TARGET1(application_SharedData applSharedData_in) {
      applSharedData = applSharedData_in;
      initComponents();
    }


    /** Creates new form JPanel_Lagrangian_ControlPts_BASIC1 */
    public JPanel_Interaction_TARGET1() {
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

        jScrollPane_Lagrangian_ControlPts_BASIC1 = new javax.swing.JScrollPane();
        jTable_Lagrangian_ControlPts_BASIC1 = new org.atzberger.application.selm_builder.JTable_Lagrangian_ControlPts_BASIC1(new org.atzberger.application.selm_builder.TableModel_CouplingOperator_TABLE1_tmp(applSharedData));
        jLabel1 = new javax.swing.JLabel();

        setName("TARGET1"); // NOI18N

        jScrollPane_Lagrangian_ControlPts_BASIC1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane_Lagrangian_ControlPts_BASIC1.setName("jScrollPane_Lagrangian_ControlPts_BASIC1"); // NOI18N

        jTable_Lagrangian_ControlPts_BASIC1.setBorder(javax.swing.BorderFactory.createLineBorder(null));
        jTable_Lagrangian_ControlPts_BASIC1.setModel(new org.atzberger.application.selm_builder.TableModel_CouplingOperator_TABLE1_tmp(applSharedData));
        jTable_Lagrangian_ControlPts_BASIC1.setName("jTable_Lagrangian_ControlPts_BASIC1"); // NOI18N
        jScrollPane_Lagrangian_ControlPts_BASIC1.setViewportView(jTable_Lagrangian_ControlPts_BASIC1);

        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addContainerGap(484, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane_Lagrangian_ControlPts_BASIC1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(279, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(55, Short.MAX_VALUE)
                    .addComponent(jScrollPane_Lagrangian_ControlPts_BASIC1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane_Lagrangian_ControlPts_BASIC1;
    private javax.swing.JTable jTable_Lagrangian_ControlPts_BASIC1;
    // End of variables declaration//GEN-END:variables

  public JTable getJTable_Lagrangian_ControlPts_BASIC1() {
    return jTable_Lagrangian_ControlPts_BASIC1;
  }

}
