package org.atzberger.application.selm_builder;

import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * Panel for editing the this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JPanel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 extends JPanel_Eulerian implements JPanel_Eulerian_interface_controlActionListener {

  application_SharedData applSharedData;

  TableModelListener localFireTableModelListener = null;
    
    /** Creates new form JPanel_Editor_Test1 */
    public JPanel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3(application_SharedData applSharedData_in) {
      applSharedData = applSharedData_in;
      initComponents();

      /* add listener to the jTable_Eulerian for any changes there */
      localFireTableModelListener = new TableModelListener() {
        public void tableChanged(TableModelEvent e)  {
          jTable_Eulerian_tableChanged(e);
        }
      };
      
      jTable_Eulerian.getModel().addTableModelListener(localFireTableModelListener);
      
    }

    public void jTable_Eulerian_tableChanged(TableModelEvent e)  {
      //System.out.println("jTable_Eulerian changed event" + e);

      fireEulerianChanged();

    }

    public void fireEulerianChanged() {

      JPanel jPanel_parent = (JPanel) this.getParent();
            
      if (jPanel_parent != null) {        
        jPanel_parent.firePropertyChange("CHANGED_EULERIAN_DATA", 0, 1);
      }
      
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3 = new javax.swing.JScrollPane();
        jTable_Eulerian = new org.atzberger.mango.table.JTable_Properties1_Default();

        setMinimumSize(new java.awt.Dimension(400, 200));
        setName("LAMMPS_SHEAR_UNIFORM1_FFTW3"); // NOI18N
        setPreferredSize(new java.awt.Dimension(400, 200));

        jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3.setName("jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(JPanel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3.class);
        jTable_Eulerian.setBorder(javax.swing.BorderFactory.createLineBorder(resourceMap.getColor("jTable_Eulerian.border.lineColor"))); // NOI18N
        jTable_Eulerian.setModel(new org.atzberger.application.selm_builder.TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3(applSharedData.atz_UnitsRef));
        jTable_Eulerian.setName("jTable_Eulerian"); // NOI18N
        jTable_Eulerian.setRowSelectionAllowed(false);
        jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3.setViewportView(jTable_Eulerian);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3;
    private javax.swing.JTable jTable_Eulerian;
    // End of variables declaration//GEN-END:variables

  @Override
  public void setData(SELM_Eulerian eulerian) {

    /* avoid firing event for this type of setting */
    //turnOffActionListenerForEdits();

    /* set the data */
    ((TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3)(jTable_Eulerian.getModel())).setFromEulerianData(eulerian);

    /* restore firing of event for editing of table */
    //turnOnActionListenerForEdits();
    
  }

  @Override
  public SELM_Eulerian getData() {
    return ((TableModel_Eulerian_LAMMPS_SHEAR_UNIFORM1_FFTW3)(jTable_Eulerian.getModel())).getEulerianDataFromModel();
  }

  public void turnOffActionListenerForEdits() {
    /* avoid firing event for this type of setting */
    jTable_Eulerian.getModel().removeTableModelListener(localFireTableModelListener);

  }

  public void turnOnActionListenerForEdits() {
    /* restore firing of event for editing of table */
    jTable_Eulerian.getModel().addTableModelListener(localFireTableModelListener);
  }


}