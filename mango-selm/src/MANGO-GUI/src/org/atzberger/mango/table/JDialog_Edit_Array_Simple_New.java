package org.atzberger.mango.table;

import org.atzberger.mango.table.TableModel_Array_Simple;
import javax.swing.JFrame;
import javax.swing.table.TableCellEditor;

/**
 *
 * Dialog editor for arrays of simple data types found within a table.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class JDialog_Edit_Array_Simple_New extends JDialog_Edit {

    static public JFrame baseFrame = null;

    Boolean flagChangeValue = false; /* indicates if value should be changed */

    public JDialog_Edit_Array_Simple_New(boolean modal) {
      this(baseFrame,modal);
    }

    /** Creates new form JDialog_Edit_Array_Simple_New */
    public JDialog_Edit_Array_Simple_New(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

    jScrollPane1 = new javax.swing.JScrollPane();
    jTable_SimpleArray = new javax.swing.JTable();
    jButton_OK = new javax.swing.JButton();
    jButton_Cancel = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.atzberger.application.selm_builder.application_Main.class).getContext().getResourceMap(JDialog_Edit_Array_Simple_New.class);
    setTitle(resourceMap.getString("Form.title")); // NOI18N
    setName("Form"); // NOI18N

    jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    jScrollPane1.setName("jScrollPane1"); // NOI18N

    jTable_SimpleArray.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    jTable_SimpleArray.setModel(new TableModel_Array_Simple());
    jTable_SimpleArray.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jTable_SimpleArray.setFocusTraversalPolicyProvider(true);
    jTable_SimpleArray.setName("jTable_SimpleArray"); // NOI18N
    jTable_SimpleArray.setRowSelectionAllowed(false);
    jTable_SimpleArray.getTableHeader().setReorderingAllowed(false);
    jScrollPane1.setViewportView(jTable_SimpleArray);

    jButton_OK.setText(resourceMap.getString("jButton_OK.text")); // NOI18N
    jButton_OK.setName("jButton_OK"); // NOI18N
    jButton_OK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton_OKActionPerformed(evt);
      }
    });

    jButton_Cancel.setText(resourceMap.getString("jButton_Cancel.text")); // NOI18N
    jButton_Cancel.setName("jButton_Cancel"); // NOI18N
    jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton_CancelActionPerformed(evt);
      }
    });

    jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
    jLabel1.setName("jLabel1"); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(jButton_OK, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
            .addComponent(jButton_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
          .addComponent(jLabel1))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addGap(18, 18, 18)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        .addGap(29, 29, 29)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButton_OK)
          .addComponent(jButton_Cancel))
        .addGap(25, 25, 25))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
      TableCellEditor editor = jTable_SimpleArray.getCellEditor();
      if (editor != null) {
        editor.stopCellEditing();
      }

      this.setVisible(false);
      flagChangeValue = true;
      setNewValueFromTable();

      //newValue = (Object) jTextField_SimpleArray_Value.getText();

      this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);

    }//GEN-LAST:event_jButton_OKActionPerformed

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed

      this.setVisible(false);

      flagChangeValue = false;
      newValue = null;

      this.firePropertyChange(ACTION_DIALOG_DONE, 1, 0);
      
    }//GEN-LAST:event_jButton_CancelActionPerformed


  public Object getDataValue() {

    //newValue = (Object) jTextField_SimpleArray_Value.getText();

    if (flagChangeValue) {
      return newValue;
    } else {
      return oldValue;
    }

  }

  public void setDataValue(Object dataValue, String dataName, String dataType) {

    super.setDataValue(dataValue,dataName,dataType);

    //jTextField_SimpleArray_Value.setText(oldValue.toString());
    //label_parameter_name.setText(dataName);

    String DoubleArrayStr = double[].class.getName();
    if (DoubleArrayStr.equals(dataType)) {

      double[] doubleArray = (double[]) dataValue;

      /* setup the table values */
      int N = doubleArray.length;
      for (int k = 0; k < N; k++) {

        int valInt;
        valInt = k + 1;
        jTable_SimpleArray.setValueAt(valInt, k, 0);

        double valDouble;
        valDouble = doubleArray[k];
        jTable_SimpleArray.setValueAt(valDouble, k, 1);
      }

    } /* end double[] */

    String IntegerArrayStr = int[].class.getName();
    if (IntegerArrayStr.equals(dataType)) {

      int[] intArray = (int[]) dataValue;

      /* setup the table values */

      int N = intArray.length;
      for (int k = 0; k < N; k++) {

        int valInt;
        valInt = k + 1;
        jTable_SimpleArray.setValueAt(valInt, k, 0);

        valInt = intArray[k];
        jTable_SimpleArray.setValueAt(valInt, k, 1);
      }

    } /* end int[] */

    String StringArrayStr = String[].class.getName();
    if (StringArrayStr.equals(dataType)) {

      String[] StringArray = (String[]) dataValue;

      /* setup the table values */
      int N = StringArray.length;
      for (int k = 0; k < N; k++) {

        int valInt;
        valInt = k + 1;
        jTable_SimpleArray.setValueAt(valInt, k, 0);

        String valString;
        valString = StringArray[k];
        jTable_SimpleArray.setValueAt(valString, k, 1);
      }

    } /* end String[] */

  }



  void setNewValueFromTable() {

    String DoubleArrayStr = double[].class.getName();
    if (DoubleArrayStr.equals(oldValue.getClass().getName())) {

      double[] newArray;
      double[] oldArray = (double[]) oldValue;

      newArray = new double[oldArray.length];

      /* set newValue from the table values */
      int N = newArray.length;
      for (int k = 0; k < N; k++) {
        newArray[k] = (Double)jTable_SimpleArray.getValueAt(k, 1);
      }

      newValue = (Object)newArray;

    } /* end double[] */

    String IntArrayStr    = int[].class.getName();
    if (IntArrayStr.equals(oldValue.getClass().getName())) {

      int[] newArray;
      int[] oldArray = (int[]) oldValue;

      newArray = new int[oldArray.length];

      /* set newValue from the table values */
      int N = newArray.length;
      for (int k = 0; k < N; k++) {
        newArray[k] = (Integer)jTable_SimpleArray.getValueAt(k, 1);
      }

      newValue = (Object)newArray;

    } /* end int[] */

    String StringArrayStr = String[].class.getName();
    if (StringArrayStr.equals(oldValue.getClass().getName())) {

      String[] newArray;
      String[] oldArray = (String[]) oldValue;

      newArray = new String[oldArray.length];

      /* set newValue from the table values */
      int N = newArray.length;
      for (int k = 0; k < N; k++) {
        newArray[k] = (String)jTable_SimpleArray.getValueAt(k, 1);
      }

      newValue = (Object)newArray;

    } /* end String[] */

  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton_Cancel;
  private javax.swing.JButton jButton_OK;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable jTable_SimpleArray;
  // End of variables declaration//GEN-END:variables

}
