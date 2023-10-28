package org.atzberger.application.selm_builder;

import org.atzberger.xml.Atz_XML_SAX_DataHandlerInterface;
import org.atzberger.xml.Atz_XML_Helper;
import org.atzberger.xml.Atz_XML_Helper_Handler_Color;
import org.atzberger.xml.Atz_XML_Helper_SAX_ListDataHandler;
import org.atzberger.xml.Atz_XML_SAX_DataHandler;
import org.atzberger.mango.atz3d.Atz3D_Element_Lines;
import org.atzberger.mango.atz3d.Atz3D_Element;
import org.atzberger.mango.atz3d.Atz3D_Model;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * Stochastic Eulerian Lagrangian Method data type.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class SELM_Interaction_LAMMPS_PAIR_COEFF extends SELM_Interaction
        implements Atz_DataChangeListener, Atz_XML_SAX_DataHandlerInterface, SELM_InteractionInterface_LAMMPS_PAIR_STYLE_TABLE {

  private String          tableFilename   = "";
  private String          energyEntryName = "";

  private int             numPairs        = 0;
  private int             numPairsAlloc   = 0;
  private final int       num_dim = 3;

  private SELM_Lagrangian pairList_lagrangianI1[] = null;
  private int             pairList_typeI1[]       = null;

  private SELM_Lagrangian pairList_lagrangianI2[] = null;
  private int             pairList_typeI2[]       = null;  /* assumes I2 > I1 (keeps unique,
                                                              if same lagrangian) */
  private double          coefficient[]           = null;
  
  private boolean         flagVisible             = true;
  private Color           plotColor               = Color.blue;
  
  /* index of element types within the representation of this type */
  public final int    atz3D_Index_Lines = 0;

  Atz3D_Element_Lines atz3D_Element_Lines = new Atz3D_Element_Lines();

  /* XML */
  String     xmlString     = "";
  Attributes xmlAttributes = null;

  public String[]  xml_pairList_lagrangianNamesI1    = null;
  public String[]  xml_pairList_lagrangianTypesStrI1 = null;

  public String[]  xml_pairList_lagrangianNamesI2    = null;
  public String[]  xml_pairList_lagrangianTypesStrI2 = null;

  public static String tagXML_tableFilename         = "tableFilename";
  public static String tagXML_energyEntryName       = "energyEntryName";

  public static String tagXML_numPairs              = "numPairs";

  public static String tagXML_pairList_lagrangianI1 = "pairList_lagrangianI1";
  public static String tagXML_pairList_lagrangianI2 = "pairList_lagrangianI2";

  public static String tagXML_pairList_typeI1         = "pairList_typeI1";
  public static String tagXML_pairList_typeI2         = "pairList_typeI2";

  public static String tagXML_coefficient            = "coefficient";
  
  public static String tagXML_flagVisible           = "flagVisible";
  public static String tagXML_plotColor             = "plotColor";

  public boolean flag_Gen_LAMMPS_XML_Files = false;
 
  SELM_Interaction_LAMMPS_PAIR_COEFF() {

    String superClassName = this.getClass().getSuperclass().getSimpleName();
    String thisClassName  = this.getClass().getSimpleName();

    tableFilename      = "No Table File Specified Yet";
    energyEntryName    = "(none)";

    InteractionName    = "Your Name Here";
    InteractionTypeStr = thisClassName.replace(superClassName + "_", "");

    numPairsAlloc = 1;
    numPairs      = 0;

    pairList_typeI1 = new int[numPairsAlloc];
    pairList_typeI2 = new int[numPairsAlloc];

    pairList_lagrangianI1 = new SELM_Lagrangian[numPairsAlloc];
    pairList_lagrangianI2 = new SELM_Lagrangian[numPairsAlloc];

    coefficient    = new double[numPairsAlloc];
    
    flagVisible = true;
    plotColor   = Color.blue;

  }

  public void setTableFilename(String filename) {
    tableFilename = filename;
  }

  public String getTableFilename() {
    return tableFilename;
  }

  public void setEnergyEntryName(String energyEntryName_in) {
    energyEntryName = energyEntryName_in;
  }

  public String getEnergyEntryName() {
    return energyEntryName;
  }
  
  public void setPlotColor(Color color_in) {
    plotColor = color_in;
  }

  public Color getPlotColor() {
    return plotColor;
  }

  public void setVisible(boolean flagVisible_in) {
    flagVisible = flagVisible_in;
  }

  public boolean isVisible() {
    return flagVisible;
  }

  public int[] getPairListTypeI1() {
    return getPairList_typeI1();
  }

  public int[] getPairListTypeI2() {
    return getPairList_typeI2();
  }


  void addTypePair(SELM_Lagrangian lagrangianI1_in, int typeI1_in,
               SELM_Lagrangian lagrangianI2_in, int typeI2_in) {

    double coefficient;

    coefficient = 0.0;
    addTypePair(lagrangianI1_in, typeI1_in, lagrangianI2_in, typeI2_in, coefficient);

  }


  /* add a pair to the list */
  void addTypePair(SELM_Lagrangian lagrangianI1_in, int typeI1_in,
               SELM_Lagrangian lagrangianI2_in, int typeI2_in,
               double coefficient_in) {

    boolean flagNewPair = true;
    
    int             i,j,k;
    int             typeI1, typeI2;
    SELM_Lagrangian lagrangianI1, lagrangianI2;

    int    pairList_typeI1_new[];
    int    pairList_typeI2_new[];

    int    pairList_lagrangianI1_new[];
    int    pairList_lagrangianI2_new[];

    double coefficient_new[];
    double stiffnessK_new[];

    /* == make sure typeI1 <= typeI2 */
    if (typeI1_in <= typeI2_in) {
      typeI1         = typeI1_in;
      typeI2         = typeI2_in;
      lagrangianI1 = lagrangianI1_in;
      lagrangianI2 = lagrangianI2_in;
    } else {
      typeI1         = typeI2_in;
      typeI2         = typeI1_in;
      lagrangianI1 = lagrangianI2_in;
      lagrangianI2 = lagrangianI1_in;
    }

    /* == check to make sure does not already exist */
    //System.out.println("numPairs = " + numPairs);
    //System.out.println("pairList_typeI1.length = " + pairList_typeI1.length);

    for (k = 0; k < numPairs; k++) {

      if ( (pairList_typeI1[k] == typeI1) && (pairList_typeI2[k] == typeI2) &&
           (pairList_lagrangianI1[k] == lagrangianI1) && (pairList_lagrangianI2[k] == lagrangianI2)
         ) {
        flagNewPair = false;
      }
    } /* end k loop */

    /* == add this entry to the list */
    if (flagNewPair) {

      if (numPairs < numPairsAlloc) {

        pairList_typeI1[numPairs]       = typeI1;
        pairList_lagrangianI1[numPairs] = lagrangianI1;
        pairList_typeI2[numPairs]       = typeI2;
        pairList_lagrangianI2[numPairs] = lagrangianI2;        
        coefficient[numPairs]            = coefficient_in;
        numPairs++;

      } else { /* == resize arrays is necessary, before add */
        
        int numPairsAlloc_new = 2 * numPairsAlloc;
        if (numPairsAlloc_new <= numPairs) { /* if still smaller */
          numPairsAlloc_new = numPairs + 10;
        }

        /* resize the lists to the specified size */
        resizeLists(numPairsAlloc_new);

        /* try to add again */
        addTypePair(lagrangianI1, typeI1,
                lagrangianI2, typeI2,
                coefficient_in); /* can @optimize checks */

      } /* end else */

      /* add a data change listener for this interaction */
      if (lagrangianI1 != null)
        lagrangianI1.addDataChangeListener(this);

      if (lagrangianI2 != null)
        lagrangianI2.addDataChangeListener(this);

    } /* end flagNewPair */

  } /* add interaction */

  /* remove pair if present in the list */
  void removeTypePair(SELM_Lagrangian lagrangianI1_in, int typeI1_in,
                  SELM_Lagrangian lagrangianI2_in, int typeI2_in) {
    
    int typeI1, typeI2;
    SELM_Lagrangian lagrangianI1, lagrangianI2;
    
    /* == make sure typeI1 <= typeI2 */
    if (typeI1_in <= typeI2_in) {
      typeI1         = typeI1_in;
      typeI2         = typeI2_in;
      lagrangianI1 = lagrangianI1_in;
      lagrangianI2 = lagrangianI2_in;
    } else {
      typeI1 = typeI2_in;
      typeI2 = typeI1_in;
      lagrangianI1 = lagrangianI2_in;
      lagrangianI2 = lagrangianI1_in;
    }

    /* == relabel indices and remove entries for given ptI */
    for (int k = 0; k < numPairs; k++) {
      if ((pairList_typeI1[k] == typeI1) && (pairList_typeI2[k] == typeI2) &&
          (pairList_lagrangianI1[k] == lagrangianI1) && (pairList_lagrangianI2[k] == lagrangianI2)
          ) {
        pairList_typeI1[k]         = -1; /* mark for removal */
        pairList_typeI2[k]         = -1;
        pairList_lagrangianI1[k] = null;
        pairList_lagrangianI2[k] = null;
      }
    } /* end k loop */

    /* removes all entries with typeI1 or typeI2 == -1 */
    removeInteraction();
    
  }


  /* remove and re-label all of the pairs */
  void removePtI(SELM_Lagrangian lagrangianI, int ptI) {
    
    int     k;
    
    /* == relabel indices and remove entries for given ptI */
    for (k = 0; k < numPairs; k++) {

      if ( ((pairList_typeI1[k] == ptI) || (pairList_typeI2[k] == ptI)) &&
           ((pairList_lagrangianI1[k] == lagrangianI) || (pairList_lagrangianI2[k] == lagrangianI)) ) {
        pairList_typeI1[k]         = -1; /* mark for removal */
        pairList_typeI2[k]         = -1;
        pairList_lagrangianI1[k] = null;
        pairList_lagrangianI2[k] = null;
      } else { /* otherwise reduce index by one for all indices larger than ptI */
        if ((pairList_typeI1[k] > ptI) && (pairList_lagrangianI1[k] == lagrangianI)) {
          pairList_typeI1[k]--;
        }
        if ((pairList_typeI2[k] > ptI) && (pairList_lagrangianI2[k] == lagrangianI)) {
          pairList_typeI2[k]--;
        }
      }
      
    } /* end k loop */

    /* removes all entries with typeI1 or typeI2 == -1 */
    removeInteraction();

  }




  /* remove interaction */
  void removeInteraction(SELM_Lagrangian lagrangianI1_in, int typeI1_in,
                         SELM_Lagrangian lagrangianI2_in, int typeI2_in) {

    int k;
    int typeI1, typeI2;
    SELM_Lagrangian lagrangianI1, lagrangianI2;

    boolean flagFound   = false;
    int     removeIndex = -1;

    /* == make sure typeI1 <= typeI2 */
    if (typeI1_in <= typeI2_in) {
      typeI1         = typeI1_in;
      typeI2         = typeI2_in;
      lagrangianI1 = lagrangianI1_in;
      lagrangianI2 = lagrangianI2_in;
    } else {
      typeI1 = typeI2_in;
      typeI2 = typeI1_in;
      lagrangianI1 = lagrangianI2_in;
      lagrangianI2 = lagrangianI1_in;
    }

    /* == find index of entry to remove */
    for (k = 0; k < numPairs; k++) {

      if ( (pairList_typeI1[k] == typeI1) && (pairList_typeI2[k] == typeI2) &&
           (pairList_lagrangianI1[k] == lagrangianI1) && (pairList_lagrangianI2[k] == lagrangianI2)
          ) {
        removeIndex = k;
        flagFound   = true;
      }
      
    } /* end k loop */

    /* == if instance found then remove this entry */
    if (flagFound) {
      removeInteraction(removeIndex);
    } /* end flagFound */
    
  } /* end removeInteraction */


  /* remove interaction */
  void removeInteraction(int removeIndex) {

    int k;
    boolean flagFound = false;
    
    int             pairList_typeI1_new[];
    int             pairList_typeI2_new[];

    SELM_Lagrangian pairList_lagrangianI1_new[];
    SELM_Lagrangian pairList_lagrangianI2_new[];

    double coefficient_new[];
    double stiffnessK_new[];

    /* resize array if a lot of slack */
    if (numPairs < numPairsAlloc / 2) {
      numPairsAlloc = numPairsAlloc / 2;
    }

    /* copy data to smaller array with entry deleted */
    pairList_typeI1_new         = new int[numPairsAlloc];
    pairList_typeI2_new         = new int[numPairsAlloc];

    pairList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc];
    pairList_lagrangianI2_new = new SELM_Lagrangian[numPairsAlloc];

    coefficient_new            = new double[numPairsAlloc];
    stiffnessK_new            = new double[numPairsAlloc];

    /* copy first part of the array */
    if (removeIndex >= 0) {
      System.arraycopy(pairList_typeI1, 0, pairList_typeI1_new, 0, removeIndex);
      System.arraycopy(pairList_typeI2, 0, pairList_typeI2_new, 0, removeIndex);

      System.arraycopy(pairList_lagrangianI1, 0, pairList_lagrangianI1_new, 0, removeIndex);
      System.arraycopy(pairList_lagrangianI2, 0, pairList_lagrangianI2_new, 0, removeIndex);

      System.arraycopy(coefficient, 0, coefficient_new, 0, removeIndex);      
    }

    /* copy second part of the array */
    System.arraycopy(pairList_typeI1, removeIndex + 1, pairList_typeI1_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(pairList_typeI2, removeIndex + 1, pairList_typeI2_new, removeIndex, numPairs - removeIndex - 1);

    System.arraycopy(pairList_lagrangianI1, removeIndex + 1, pairList_lagrangianI1_new, removeIndex, numPairs - removeIndex - 1);
    System.arraycopy(pairList_lagrangianI2, removeIndex + 1, pairList_lagrangianI2_new, removeIndex, numPairs - removeIndex - 1);

    System.arraycopy(coefficient, removeIndex + 1, coefficient_new, removeIndex, numPairs - removeIndex - 1);
    
    pairList_typeI1         = pairList_typeI1_new;
    pairList_typeI2         = pairList_typeI2_new;

    pairList_lagrangianI1 = pairList_lagrangianI1_new;
    pairList_lagrangianI2 = pairList_lagrangianI2_new;

    coefficient            = coefficient_new;
        
    numPairs--; /* decrement the pair, since one removed */

  }


  /* removes all entries with ptI == -1 */
  void removeInteraction() {

    int k;    

    int numPairs_new = 0;

    boolean flagFound = false;

    int             pairList_typeI1_new[];
    int             pairList_typeI2_new[];

    SELM_Lagrangian pairList_lagrangianI1_new[];
    SELM_Lagrangian pairList_lagrangianI2_new[];

    double coefficient_new[];
    double stiffnessK_new[];

    /* copy data to smaller array with entry deleted */
    pairList_typeI1_new         = new int[numPairsAlloc];
    pairList_typeI2_new         = new int[numPairsAlloc];

    pairList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc];
    pairList_lagrangianI2_new = new SELM_Lagrangian[numPairsAlloc];

    coefficient_new            = new double[numPairsAlloc];
    stiffnessK_new            = new double[numPairsAlloc];

    /* copy only the entries not having ptI == -1 and lagrangianI == null*/
    for (k = 0; k < numPairs; k++) {

      if ( (pairList_typeI1[k] != -1) && (pairList_typeI2[k] != -1) &&
           (pairList_lagrangianI1[k] != null) && (pairList_lagrangianI2[k] != null) ) {
        pairList_typeI1_new[numPairs_new]        = pairList_typeI1[k];
        pairList_typeI2_new[numPairs_new]        = pairList_typeI2[k];
        pairList_lagrangianI1_new[numPairs_new]  = pairList_lagrangianI1[k];
        pairList_lagrangianI2_new[numPairs_new]  = pairList_lagrangianI2[k];
        coefficient_new[numPairs_new]            = coefficient[k];        
        numPairs_new++;
      }

    } /* end k loop */

    numPairs               = numPairs_new; /* update the number of pairs */
    pairList_typeI1        = pairList_typeI1_new;
    pairList_typeI2        = pairList_typeI2_new;
    pairList_lagrangianI1  = pairList_lagrangianI1_new;
    pairList_lagrangianI2  = pairList_lagrangianI2_new;
    coefficient            = coefficient_new;

  } /* removeInteraction */

  public int getNumPairs() {
    return numPairs;
  }
  
  public int[] getPairList_typeI1() {
    return makeSubArray(pairList_typeI1,numPairs);
  }

  public int[] getPairList_typeI2() {
    return makeSubArray(pairList_typeI2,numPairs);
  }

  public SELM_Lagrangian[] getPairList_lagrangianI1() {
    return makeSubArray(pairList_lagrangianI1, numPairs);
  }

  public SELM_Lagrangian[] getPairList_lagrangianI2() {
    return makeSubArray(pairList_lagrangianI2, numPairs);
  }

  public double[] getCoefficient() {
    return makeSubArray(coefficient,numPairs);
  }
  
  public void setCoefficient(double[] coefficient_in) {
    int N = coefficient_in.length;
    setSubArray(coefficient_in, coefficient, N);
  }
 
  public Color getRenderColor() {
    return plotColor;
  }

  public boolean getRenderVisible() {
    return flagVisible;
  }

  public void setRenderColor(Color renderColor_in) {
    plotColor = renderColor_in;
  }

  public void setRenderVisible(boolean renderVisible_in) {
    flagVisible = renderVisible_in;
  }

  public void setPairList_lagrangianI1(SELM_Lagrangian[] pairList_lagrangianI1_in) {

    int numPairs_in = pairList_lagrangianI1_in.length;

    if (numPairs_in > numPairsAlloc) {
      resizeLists(numPairs_in);
    }
    
    numPairs = numPairs_in;

    setSubArray(pairList_lagrangianI1_in, pairList_lagrangianI1, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (pairList_lagrangianI1_in[k] != null)
        (pairList_lagrangianI1_in[k]).addDataChangeListener(this);
    }

  }

  public void setPairList_lagrangianI2(SELM_Lagrangian[] pairList_lagrangianI2_in) {

    int numPairs_in = pairList_lagrangianI2_in.length;

    if (numPairs_in > numPairsAlloc) {
      resizeLists(numPairs_in);
    }

    numPairs = numPairs_in;

    setSubArray(pairList_lagrangianI2_in, pairList_lagrangianI2, numPairs_in);

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (pairList_lagrangianI2_in[k] != null)
        (pairList_lagrangianI2_in[k]).addDataChangeListener(this);
    }
    
  }

  public void setPairData(int numPairs_in,
                          SELM_Lagrangian[] pairList_lagrangianI1_in, int[] pairList_typeI1_in,
                          SELM_Lagrangian[] pairList_lagrangianI2_in, int[] pairList_typeI2_in,
                          double[] coefficient_in) {
   
    if (numPairs_in >= numPairsAlloc) {
      resizeLists(2*numPairs_in);
    }
    
    setSubArray(pairList_typeI1_in,         pairList_typeI1,         numPairs_in);
    setSubArray(pairList_typeI2_in,         pairList_typeI2,         numPairs_in);
    setSubArray(pairList_lagrangianI1_in, pairList_lagrangianI1, numPairs_in);
    setSubArray(pairList_lagrangianI2_in, pairList_lagrangianI2, numPairs_in);    
    setSubArray(coefficient_in,            coefficient,            numPairs_in);

    numPairs = numPairs_in;

    /* add a data change listener for this interaction */
    for (int k = 0; k < numPairs_in; k++) {
      if (pairList_lagrangianI1_in[k] != null)
        (pairList_lagrangianI1_in[k]).addDataChangeListener(this);

      if (pairList_lagrangianI2_in[k] != null)
        (pairList_lagrangianI2_in[k]).addDataChangeListener(this);
    }
     
  }

  private int[] makeSubArray(int[] array, int numItems) {

    int[] subArray = new int[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }

  private double[] makeSubArray(double[] array, int numItems) {

    double[] subArray = new double[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }

  private SELM_Lagrangian[] makeSubArray(SELM_Lagrangian[] array, int numItems) {

    SELM_Lagrangian[] subArray = new SELM_Lagrangian[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;

  }

  private void setSubArray(int[] subArray, int[] array, int numItems) {
    System.arraycopy(subArray, 0, array, 0, numItems);
  }

  private void setSubArray(double[] subArray, double[] array, int numItems) {
    System.arraycopy(subArray, 0, array, 0, numItems);
  }

  private void setSubArray(Object[] subArray, Object[] array, int numItems) {
    System.arraycopy(subArray, 0, array, 0, numItems);
  }

  private void resizeLists(int numPairsAlloc_new) {

    int numToCopy = -1;

    numToCopy = java.lang.Math.min(numPairs, numPairsAlloc_new);
    
    if (numPairsAlloc_new <= numPairs) {
      //System.out.println("ERROR: Interaction_PAIRS_HARMONIC.resizeLists()");
      //System.out.println("numPairsAlloc_new <= numPairs");
      /* this is now allowed, since numToCopy is min */
    }
  
    int[]                 pairList_typeI1_new         = new int[numPairsAlloc_new];
    int[]                 pairList_typeI2_new         = new int[numPairsAlloc_new];

    SELM_Lagrangian[]     pairList_lagrangianI1_new = new SELM_Lagrangian[numPairsAlloc_new];
    SELM_Lagrangian[]     pairList_lagrangianI2_new = new SELM_Lagrangian[numPairsAlloc_new];

    double[]  coefficient_new    = new double[numPairsAlloc_new];
    double[]  stiffnessK_new    = new double[numPairsAlloc_new];

    System.arraycopy(pairList_typeI1, 0, pairList_typeI1_new, 0, numToCopy);
    System.arraycopy(pairList_typeI2, 0, pairList_typeI2_new, 0, numToCopy);

    System.arraycopy(pairList_lagrangianI1, 0, pairList_lagrangianI1_new, 0, numToCopy);
    System.arraycopy(pairList_lagrangianI2, 0, pairList_lagrangianI2_new, 0, numToCopy);

    System.arraycopy(coefficient, 0, coefficient_new, 0, numToCopy);
    
    pairList_typeI1         = pairList_typeI1_new;
    pairList_typeI2         = pairList_typeI2_new;

    pairList_lagrangianI1 = pairList_lagrangianI1_new;
    pairList_lagrangianI2 = pairList_lagrangianI2_new;

    coefficient            = coefficient_new;    

    numPairsAlloc         = numPairsAlloc_new;

  }


  @Override
  public SELM_Interaction_LAMMPS_PAIR_COEFF clone() {
    SELM_Interaction_LAMMPS_PAIR_COEFF interaction_copy = new SELM_Interaction_LAMMPS_PAIR_COEFF();

    interaction_copy.setDataChangeListenerList(this.listenerList, this.numListeners);

    interaction_copy.InteractionName                   = this.InteractionName.toString();
    interaction_copy.InteractionTypeStr                = this.InteractionTypeStr.toString();

    interaction_copy.tableFilename                     = this.tableFilename;
    interaction_copy.energyEntryName                   = this.energyEntryName;
       
    interaction_copy.numPairs                          = this.numPairs;
    interaction_copy.numPairsAlloc                     = this.numPairsAlloc;
    interaction_copy.pairList_typeI1                   = this.pairList_typeI1.clone();
    interaction_copy.pairList_typeI2                   = this.pairList_typeI2.clone();
    interaction_copy.pairList_lagrangianI1             = this.pairList_lagrangianI1.clone();
    interaction_copy.pairList_lagrangianI2             = this.pairList_lagrangianI2.clone();
    interaction_copy.coefficient                       = this.coefficient.clone();    
    
    interaction_copy.xml_pairList_lagrangianNamesI1    = this.xml_pairList_lagrangianNamesI1;
    interaction_copy.xml_pairList_lagrangianTypesStrI1 = this.xml_pairList_lagrangianTypesStrI1;

    interaction_copy.xml_pairList_lagrangianNamesI2    = this.xml_pairList_lagrangianNamesI2;
    interaction_copy.xml_pairList_lagrangianTypesStrI2 = this.xml_pairList_lagrangianTypesStrI2;

    interaction_copy.plotColor                         = new Color(this.plotColor.getRGB());
    interaction_copy.flagVisible                       = this.flagVisible;
  
    return interaction_copy;
    
  }

  public String getRenderTag() {
    return atz3D_RENDER_TAG_INTERACTION;
  }

  public void renderToModel3D(Atz3D_Model model3D) {

    /* add points to represent the current lagrangian structure */
    //Color plotColor = Color.green;
    if (isVisible() == true) {  /* only add if this DOF is visible */
      model3D.addElements(getAtz3DElementRepresentation());
    }

  }

  public Atz3D_Element[] getAtz3DElementRepresentation() {
    Atz3D_Element[] list = new Atz3D_Element[1];

    atz3D_Element_Lines.setPlotColor(plotColor);

    /* loop over all of the interactions and construct
     * lines for each one.
     */
    int             N = numPairs;
    int             I1, I2;
    int             I;
    int             totalNumLines;
    int             typeI1, typeI2;    
    double[]        X1, X2;
    double[]        ptsX1, ptsX2;
    SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangianI1, lagrangianI2;

    ArrayList       allTypePairsX1 = new ArrayList();
    ArrayList       allTypePairsX2 = new ArrayList();
    
    I             = 0;
    totalNumLines = 0;
    for (int k = 0; k < N; k++) {
      
      lagrangianI1 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) pairList_lagrangianI1[k];
      typeI1       = pairList_typeI1[k];

      lagrangianI2 = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) pairList_lagrangianI2[k];
      typeI2       = pairList_typeI2[k];

      if ((lagrangianI1 != null) && (lagrangianI2 != null)) {

        double[] list_X1 = lagrangianI1.getPtsXType(typeI1);
        double[] list_X2 = lagrangianI2.getPtsXType(typeI2);

        allTypePairsX1.add(list_X1);
        allTypePairsX2.add(list_X2);

        totalNumLines += list_X1.length*list_X2.length/(num_dim*num_dim);
      }

    } /* end k loop */
    
    ptsX1 = new double[totalNumLines*num_dim];
    ptsX2 = new double[totalNumLines*num_dim];

    N = allTypePairsX1.size();
    I = 0;
    for (int k = 0; k < N; k++) {

      double[] list_X1 = (double[])allTypePairsX1.get(k);
      double[] list_X2 = (double[])allTypePairsX2.get(k);

      int numPtsX1 = list_X1.length/num_dim;
      int numPtsX2 = list_X2.length/num_dim;

      for (int k1 = 0; k1 < numPtsX1; k1++) {
        for (int k2 = 0; k2 < numPtsX2; k2++) {

          for (int d = 0; d < num_dim; d++) {
            ptsX1[I*num_dim + d] = list_X1[k1*num_dim + d];
            ptsX2[I*num_dim + d] = list_X2[k2*num_dim + d];
          }
          I++;

        } /* end k2 */
      } /* end k1 */

    } /* end k loop */
            
    atz3D_Element_Lines.setLines(ptsX1, ptsX2);

    //atz3D_Index_Lines       = 0;
    list[atz3D_Index_Lines] = atz3D_Element_Lines;

    return list;
  }

  public void handleDataChange(Atz_DataChangeEvent e) {
    
    System.out.println("Handle Data Change");
    System.out.println("getDataChangeType() = " + e.getDataChangeType());

    /* check which type of object generated the signal */
    if (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE.class.isInstance(e.getSource())) {

      SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE source = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) e.getSource();

      if (source.DATA_CHANGE_REMOVE_PT.equals(e.getDataChangeType())) {
        HashMap extras = (HashMap) e.getDataChangeExtraInfo();

        int                                ptsToRemoveIndex = (Integer) extras.get("ptsToRemoveIndex");
        SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE lagrangian     = (SELM_Lagrangian_LAMMPS_ATOM_ANGLE_STYLE) extras.get("lagrangian");

        System.out.println("Signal to Remove Points..." + lagrangian.LagrangianName + ":" + ptsToRemoveIndex);
        System.out.println("");

        /* remove all interactions involving this particular point */
        /* also, reindex the pairs to reflect the delection of this point */
        removePtI(lagrangian, ptsToRemoveIndex);
        
      }

    } /* SELM_Lagrangian_LAMMPS_ATOM_angle_style */

  }




    /* ====================================================== */
  /* ==================== XML codes ======================= */
  @Override
  public void exportData(String filename, int flagType) {

    switch (flagType) {

      case FILE_TYPE_XML:

        try {

          // Create file
          FileWriter fstream = new FileWriter(filename);
          BufferedWriter fid = new BufferedWriter(fstream);

          Atz_XML_Helper.writeXMLHeader(fid, "1.0", "UTF-8");

          exportToXML(fid);

          //Close the fidput stream
          fid.close();

        } catch (Exception e) {//Catch exception if any
          e.printStackTrace();
          System.out.println(e);
          //System.err.println("Error: " + e.getMessage());
        }

        break; /* end XML */

    } /* end switch */

  }

  @Override
  public void exportToXML(BufferedWriter fid) {

    try {
      Atz_XML_Helper.writeXMLStartTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

      Atz_XML_Helper.writeXMLData(fid, tagXML_InteractionName,    InteractionName);
      Atz_XML_Helper.writeXMLData(fid, tagXML_InteractionTypeStr, InteractionTypeStr);

      if (!flag_Gen_LAMMPS_XML_Files) { /* normal XML file */
        /* WARNING: ideally we should save all paths relative to the XML file path */
        Atz_XML_Helper.writeXMLData(fid, tagXML_tableFilename, tableFilename);
      } else { /* XML file special to LAMMPS parameter files */
        /* generate local LAMMPS table file name that will be used */
        String modifiedTableFilename 
          = org.atzberger.application.selm_builder.application_Project_Atz_XML_DataHandler_LAMMPS_USER_SELM.generateInteractionTableFilename(this);

        Atz_XML_Helper.writeXMLData(fid, tagXML_tableFilename, modifiedTableFilename);
      }

      Atz_XML_Helper.writeXMLData(fid, tagXML_energyEntryName, energyEntryName);

      Atz_XML_Helper.writeXMLData(fid, tagXML_numPairs, numPairs);
      
      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_pairList_lagrangianI1);

      SELM_Lagrangian[] listLagrangianI1 = this.getPairList_lagrangianI1();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI1[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI1[k].LagrangianName, listLagrangianI1[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }

      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_pairList_lagrangianI1);
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_pairList_typeI1, this.getPairList_typeI1());

      Atz_XML_Helper.writeXMLStartTag(fid, tagXML_pairList_lagrangianI2);      
      SELM_Lagrangian[] listLagrangianI2 = this.getPairList_lagrangianI2();
      for (int k = 0; k < listLagrangianI1.length; k++) {
        if (listLagrangianI2[k] != null) {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            listLagrangianI2[k].LagrangianName, listLagrangianI2[k].LagrangianTypeStr);
        } else {
          Atz_XML_Helper_Handler_LagrangianRef.exportToXML(fid,
            "NULL", "NULL");
        }
      }      
      Atz_XML_Helper.writeXMLEndTag(fid, tagXML_pairList_lagrangianI2);

      Atz_XML_Helper.writeXMLData(fid, tagXML_pairList_typeI2, getPairList_typeI2());

      Atz_XML_Helper.writeXMLData(fid, tagXML_coefficient,  getCoefficient());      
      
      Atz_XML_Helper.writeXMLData(fid, tagXML_plotColor,   new Atz_XML_Helper_Handler_Color(plotColor));

      Atz_XML_Helper.writeXMLData(fid, tagXML_flagVisible, flagVisible);

      Atz_XML_Helper.writeXMLEndTag(fid, SELM_Interaction.tagXML_SELM_Interaction);

      flag_Gen_LAMMPS_XML_Files = false; /* set flag back to false so next run does not trigger LAMMPS export */

    } catch (Exception e) {//Catch exception if any
      System.err.println("Error: " + e.getMessage());
      e.printStackTrace();
      flag_Gen_LAMMPS_XML_Files = false; /* set flag back to false so next run does not trigger LAMMPS export */
    }

  }


  @Override
  public void importData(String filename, int flagType) {

    /* open the XML file */

    /* parse the XML file to setup the data */

    //get a factory
    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {

      //get a new instance of parser
      SAXParser sp = spf.newSAXParser();

      //parse the file and also register this class for call backs
      //sp.parse("test1.SELM_Interaction_LAMMPS_ATOM_angle_style", new Atz_XML_DataHandlerWrapper(this));

      sp.parse(filename, new Atz_XML_SAX_DataHandler(this));

      /* Use the local codes XMLContentHandler */

    } catch (SAXException se) {
      se.printStackTrace();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (IOException ie) {
      ie.printStackTrace();
    }

  }

  @Override
  public void XML_startDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }

  @Override
  public void XML_endDocument(Atz_XML_SAX_DataHandler sourceHandler) {
    /* nothing to do */
  }

  //Event Handlers
  @Override
  public void XML_startElement(String uri, String localName, String qName, Attributes attributes, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    /* setup data for later parsing and processing */
    xmlString     = "";
    xmlAttributes = attributes;

    if (qName.equals(SELM_Interaction.tagXML_SELM_Interaction)) {
      /* nothing special to do */
    } else if (qName.equals(tagXML_pairList_lagrangianI1)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_pairList_lagrangianI2)) {
      sourceHandler.parseCurrentScopeWithDataHandler(new Atz_XML_Helper_SAX_ListDataHandler(new Atz_XML_Helper_Handler_LagrangianRef()));
    } else if (qName.equals(tagXML_plotColor)) {
      sourceHandler.parseNextTagWithDataHandler(new Atz_XML_Helper_Handler_Color());
    }
    
  }

  @Override
  public void XML_characters(char[] ch, int start, int length, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {
    xmlString = xmlString + new String(ch, start, length);
  }

  @Override
  public void XML_endElement(String uri, String localName, String qName, Atz_XML_SAX_DataHandler sourceHandler) throws SAXException {

    if (qName.equals(SELM_Interaction.class.getSimpleName())) {
      /* check all entries set */
    } else if (qName.equals(tagXML_InteractionName)) {

    } else if (qName.equals(tagXML_InteractionTypeStr)) {

    } else if (qName.equals(tagXML_tableFilename)) {
      setTableFilename(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_energyEntryName)) {
      setEnergyEntryName(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_numPairs)) {
      int numPairs_new = Integer.parseInt(xmlAttributes.getValue("value"));
      resizeLists(numPairs_new);
      numPairs = numPairs_new;
    } else if (qName.equals(this.tagXML_pairList_lagrangianI1)) {

      /* only process data from this tag if it is non-empty */
      if (numPairs != 0) {
        Atz_XML_Helper_SAX_ListDataHandler handler
          = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
        HashMap tagDataLists = handler.getTagDataLists();
        Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
        ArrayList lagrangianRefList       = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
        xml_pairList_lagrangianNamesI1    = new String[lagrangianRefList.size()];
        xml_pairList_lagrangianTypesStrI1 = new String[lagrangianRefList.size()];
        for (int k = 0; k < lagrangianRefList.size(); k++) {
          lagrangianRef                        = (Atz_XML_Helper_Handler_LagrangianRef)lagrangianRefList.get(k);
          xml_pairList_lagrangianNamesI1[k]    = lagrangianRef.LagrangianName;
          xml_pairList_lagrangianTypesStrI1[k] = lagrangianRef.LagrangianTypeStr;
        }
      } else {
        xml_pairList_lagrangianNamesI1    = new String[0];
        xml_pairList_lagrangianTypesStrI1 = new String[0];
      }

    } else if (qName.equals(this.tagXML_pairList_lagrangianI2)) {

      /* only process data from this tag if it is non-empty */
      if (numPairs != 0) {
        Atz_XML_Helper_SAX_ListDataHandler handler = (Atz_XML_Helper_SAX_ListDataHandler) sourceHandler.getLastUsedDataHandler();
        HashMap tagDataLists = handler.getTagDataLists();
        Atz_XML_Helper_Handler_LagrangianRef lagrangianRef;
        ArrayList lagrangianRefList = (ArrayList) tagDataLists.get(Atz_XML_Helper_Handler_LagrangianRef.tagXML_LagrangianRef);
        xml_pairList_lagrangianNamesI2    = new String[lagrangianRefList.size()];
        xml_pairList_lagrangianTypesStrI2 = new String[lagrangianRefList.size()];
        for (int k = 0; k < lagrangianRefList.size(); k++) {
          lagrangianRef = (Atz_XML_Helper_Handler_LagrangianRef) lagrangianRefList.get(k);
          xml_pairList_lagrangianNamesI2[k] = lagrangianRef.LagrangianName;
          xml_pairList_lagrangianTypesStrI2[k] = lagrangianRef.LagrangianTypeStr;
        }

      } else {
        xml_pairList_lagrangianNamesI2    = new String[0];
        xml_pairList_lagrangianTypesStrI2 = new String[0];
      }

    } else if (qName.equals(tagXML_pairList_typeI1)) {
      pairList_typeI1 = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_pairList_typeI2)) {
      pairList_typeI2 = Atz_XML_Helper.parseIntArrayFromString(xmlString);
    } else if (qName.equals(tagXML_coefficient)) {
      coefficient    = Atz_XML_Helper.parseDoubleArrayFromString(xmlString);    
    } else if (qName.equals(tagXML_flagVisible)) {
      flagVisible = Boolean.parseBoolean(xmlAttributes.getValue("value"));
    } else if (qName.equals(tagXML_plotColor)) {
      plotColor = ((Atz_XML_Helper_Handler_Color)sourceHandler.getLastUsedDataHandler()).color;
    }

  }

  @Override
  public void setupLagrangianFromList(SELM_Lagrangian[] lagrangianList) {
    /* assumes XML data was recently read and should be used */
    setupLagrangianListsFromNames(xml_pairList_lagrangianNamesI1, 
                                  xml_pairList_lagrangianNamesI2,
                                  lagrangianList);
  }

  public void setupLagrangianListsFromNames(String[] lagrangianNamesI1, String[] lagrangianNamesI2, SELM_Lagrangian[] lagrangianList) {

    int N = lagrangianNamesI1.length;

    SELM_Lagrangian[] listLagrangianI1 = new SELM_Lagrangian[N];
    SELM_Lagrangian[] listLagrangianI2 = new SELM_Lagrangian[N];

    for (int k = 0; k < N; k++) {
      /* find appropriate lagrangian */
      listLagrangianI1[k] = null;
      listLagrangianI2[k] = null;
      for (int j = 0; j < lagrangianList.length; j++) {
        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI1[k])) {
          listLagrangianI1[k] = lagrangianList[j];
        }
        if (lagrangianList[j].LagrangianName.equals(lagrangianNamesI2[k])) {
          listLagrangianI2[k] = lagrangianList[j];
        }
      } /* end of j loop */
    } /* end of k loop */

    /* setup the pairs */
    setPairList_lagrangianI1(listLagrangianI1);
    setPairList_lagrangianI2(listLagrangianI2);

  }

  @Override
  public Object XML_getData() {
    return this.clone(); /* return a copy of this object, important to clone for case of lists */
  }

  public void setFlagGenLAMMPS_XML_Files(boolean val) {
    flag_Gen_LAMMPS_XML_Files = val;   /* this value expires after one XML export (turned to false there) */
  }

  /* ==================== XML codes ======================= */
  /* ====================================================== */


}
