package org.atzberger.application.selm_builder;

/**
 *
 * Hierarchical hash map associated lists.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_Struct_DataListManager {

  protected int                             numListeners      = 0;
  protected Atz_Struct_DataChangeListener[] listenerList      = new Atz_Struct_DataChangeListener[1];
  
  Atz_Struct_DataListManager() {

  }

  Atz_Struct_DataListManager(Atz_Struct_DataChangeListener[] listenerList_in, int numListeners_in) {
    setDataChangeListenerList(listenerList_in, numListeners_in);
  }

  public void setDataChangeListenerList(Atz_Struct_DataChangeListener[] listenerList_in, int numListeners_in) {
    numListeners = numListeners_in;
    listenerList = listenerList_in.clone();
  }
 
  public void addDataChangeListener(Atz_Struct_DataChangeListener listener) {

    if (numListeners >= listenerList.length) {
      resizeLists(2*listenerList.length);
      addDataChangeListener(listener);
    } else {
      if (isAlreadyDataChangeListener(listener) == false) { /* only add if new */
        listenerList[numListeners++] = listener;
      }
    }

  }

  public boolean isAlreadyDataChangeListener(Atz_Struct_DataChangeListener listener) {
    boolean flag = false;

    for (int k = 0; k < numListeners; k++) {
      if (listenerList[k] == listener) {
        flag = true;
      }
    }

    return flag;
  }

  public void removeDataChangeListener(Atz_Struct_DataChangeListener listener) {

    /* find the index of the entry, if present */
    int index = -1;
    for (int k = 0; k < numListeners; k++) {
     if (listener == listenerList[k]) {
       index = k;
     }
    }

    /* remove the entry with valid index */
    if (index >= 0) {
      Atz_Struct_DataChangeListener[] listenerList_new = new Atz_Struct_DataChangeListener[listenerList.length];
      System.arraycopy(listenerList, 0, listenerList_new, 0, index);
      System.arraycopy(listenerList, index + 1, listenerList_new, index, numListeners - index - 1);
      listenerList = listenerList_new;

      if (listenerList.length - 2 >= 2*numListeners) { /* shrink list if too much slack */
        resizeLists(listenerList.length/2);
      }

    }

  }

  public void removeAllDataChangeListeners() {
    numListeners      = 0;    
    listenerList      = new Atz_Struct_DataChangeListener[1];
  }

  public Atz_Struct_DataChangeListener[] getAllDataChangeListeners() {
    return (Atz_Struct_DataChangeListener[]) makeSubArray(listenerList, numListeners);
  }

  public void fireDataChangeEvent(Object source) {
    this.fireDataChangeEvent(source, "", null);
  }

  public void fireDataChangeEvent(Object source, String dataChangeTypeStr) {
    this.fireDataChangeEvent(source, dataChangeTypeStr, null);
  }

  public void fireDataChangeEvent(Object source, String dataChangeTypeStr, Object dataChangeExtraInfo) {
    Atz_Struct_DataChangeListener listener;
    Atz_Struct_DataChangeEvent    dataChangeEvent = new Atz_Struct_DataChangeEvent(source, dataChangeTypeStr, dataChangeExtraInfo);

    /* loop over all of the listeners and handle the data change event */
    for (int k = 0; k < numListeners; k++) {
      (listenerList[k]).handleDataChange(dataChangeEvent);
    }

  }

  private void resizeLists(int numAllocListeners_new) {

    if (numAllocListeners_new <= numListeners) {
      System.out.println("ERROR: Atz_DataChangeable.resizeLists()");
      System.out.println("numAllocListeners_new <= numListeners");
    }

    Atz_Struct_DataChangeListener[] listenerList_new = new Atz_Struct_DataChangeListener[numAllocListeners_new];

    System.arraycopy(listenerList, 0, listenerList_new, 0, numListeners);

    listenerList        = listenerList_new;    

  }

  private Object[] makeSubArray(Object[] array, int numItems) {
    Object[] subArray = new Object[numItems];

    System.arraycopy(array, 0, subArray, 0, numItems);

    return subArray;
  }

  
  @Override
  public Object clone() {
    return new Atz_Struct_DataListManager(listenerList, numListeners);
  }

}
