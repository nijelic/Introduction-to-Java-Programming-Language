/**
 * This package contains: <br/>
 * <br/>
 * {@link hr.fer.zemris.java.hw07.observer2.ChangeCounter} Class used as
 * {@link IntegerStorageObserver} for counting changes in
 * {@link IntegerStorage}. When valueChanged is called it prints number of
 * changes.<br/>
 * <br/>
 * <br/>
 * 
 * 
 * {@link hr.fer.zemris.java.hw07.observer2.DoubleValue} Class used as
 * {@link IntegerStorageObserver} for writing double value (i.e. “value * 2”) of
 * the current value which is stored in subject, but only first n times since
 * its registration with the subject.<br/>
 * <br/>
 * <br/>
 * 
 * 
 * {@link hr.fer.zemris.java.hw07.observer2.IntegerStorage} This class is the
 * Subject in Observer pattern. It has one integer value and observers are:
 * {@link SquareValue}, {@link ChangeCounter} and {@link DoubleValue}.<br/>
 * <br/>
 * <br/>
 * 
 * {@link hr.fer.zemris.java.hw07.observer2.IntegerStorageChange} This class is
 * used for sending updated changes from Subject to Observers. It has read-only
 * properties.<br/>
 * <br/>
 * <br/>
 * 
 * {@link hr.fer.zemris.java.hw07.observer2.IntegerStorageObserver} Observer
 * interface for {@link IntegerStorage}.<br/>
 * <br/>
 * <br/>
 * 
 * {@link hr.fer.zemris.java.hw07.observer2.ObserverExample} Example of using
 * {@link IntegerStorage} and its {@link IntegerStorageObserver}s.<br/>
 * <br/>
 * <br/>
 * 
 * {@link hr.fer.zemris.java.hw07.observer2.SquareValue} Instances of
 * SquareValue class prints a square of the updated value in the
 * {@link IntegerStorage} to the standard output.<br/>
 * <br/>
 * <br/>
 * 
 * 
 * @author Jelić, Nikola
 *
 */
package hr.fer.zemris.java.hw07.observer2;