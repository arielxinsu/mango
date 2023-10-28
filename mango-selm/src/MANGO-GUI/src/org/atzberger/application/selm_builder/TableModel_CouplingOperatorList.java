package org.atzberger.application.selm_builder;

import org.atzberger.mango.table.TableModel_Properties1_General;
import java.awt.Color;
import javax.swing.table.AbstractTableModel;

/**
 *
 * Customised table behavior for this data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class TableModel_CouplingOperatorList extends TableModel_Properties1_General {

  private final static boolean DEBUG = true;


  TableModel_CouplingOperatorList() {
    String[] columnNames = new String[2];
    columnNames[0] = "Name";
    columnNames[1] = "Type";
    setColumnNames(columnNames);
    init();
    
    /* put in a few test entries */
    int k = 0;
    setValueAt("Test Operator 1", k, 0, EDITABLE);
    setValueAt("SELM_CPTS_SHEAR_UNIFORM1", k, 1, NOT_EDITABLE);
    k++;

    setValueAt("Test Operator 2", k, 0, EDITABLE);
    setValueAt("SELM_FAXEN1_SHEAR_UNIFORM1", k, 1, NOT_EDITABLE);
    k++;

  }

  TableModel_CouplingOperatorList(String[] columnNames) {
    setColumnNames(columnNames);
    init();
  }
  
}
