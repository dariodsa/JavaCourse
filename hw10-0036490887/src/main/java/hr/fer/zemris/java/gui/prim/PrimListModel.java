package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class {@link PrimListModel} implements ListModel with Integer as parameter.
 * It also allows user to add new elements to list and also to get new one from
 * the list. The observers, who registered on that object, will receive
 * notifications about adding new numbers.
 * 
 * @author dario
 */
public class PrimListModel implements ListModel<Integer> {

    /**
     * 
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    /**
     * list of elements
     */
    private List<Integer> elements = new ArrayList<>();
    /**
     * list of observers
     */
    private List<ListDataListener> observers = new ArrayList<>();

    @Override
    public void addListDataListener(ListDataListener l) {
	Objects.requireNonNull(l);
	observers.add(l);
    }

    @Override
    public Integer getElementAt(int index) {

	return elements.get(index);
    }

    @Override
    public int getSize() {

	return elements.size();
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
	Objects.requireNonNull(l);
	this.observers = new ArrayList<>(observers);
	observers.remove(l);
    }

    /**
     * Adds new element to the list of all elements and notifies all observers of
     * that changed.
     * 
     * @param element
     *            new element
     */
    private void add(Integer element) {
	elements.add(element);
	ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size() - 1,
		elements.size() - 1);
	notifyThem(event);
    }

    /**
     * Notifies all observers which registered on this object. It will send them
     * event variable so that observers know want was changed.
     * 
     * @param event
     *            event of
     */
    private void notifyThem(ListDataEvent event) {
	for (ListDataListener l : observers) {
	    l.intervalAdded(event);
	}
    }

    /**
     * Adds new number to the model. It adds prime numbers, starting it from 1 and
     * then 2, 3, 5, 7 and etc. It adds next prime number in the list.
     */
    public void next() {
	if (getSize() == 0) {
	    add(1);
	    return;
	} else {
	    int last = getElementAt(getSize() - 1);
	    for (++last;; ++last) {
		if (isPrime(last)) {
		    add(last);
		    return;
		}
	    }
	}
    }

    /**
     * Returns true if the given natural number is prime, otherwise it returns
     * false.
     * 
     * @param val
     *            number which we will check
     * @return true if the val is prime, false otherwise
     */
    private boolean isPrime(int val) {
	if (val < 0)
	    return false;
	if (val == 1 || val == 2)
	    return true;
	if (val % 2 == 0)
	    return false;
	for (int i = 3, M = (int) Math.sqrt(val); i <= M; i += 2) {
	    if (val % i == 0)
		return false;
	}
	return true;
    }

}
