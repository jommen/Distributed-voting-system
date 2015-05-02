package voting;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

/**
 * Helper class for the asynchronous execution of a RMI call and further
 * manipulation of a GUI by the event dispatch thread.
 * 
 * @author Patrick Fries
 */
public class RMICaller {
	private static final ExecutorService EXECSERVICE = Executors.newCachedThreadPool();

	/**
	 * Calls the method {@link RMICallWithGUIManipulation#doRMICall()} of the
	 * {@link RMICallWithGUIManipulation} object asynchronously in a new thread.
	 * If there was no exception, the method
	 * {@link RMICallWithGUIManipulation#doGUIManipulation()} will be called by
	 * the event dispatch thread.
	 * 
	 * @param call
	 *            {@link RMICallWithGUIManipulation}-object on which the methods
	 *            should be called
	 * @param <T>
	 *            Return type of the RMI call
	 */
	public static <T> void callAsync(final RMICallWithGUIManipulation<T> call) {
		RMICaller.EXECSERVICE.execute(new Runnable() {
			@Override
			public void run() {
				RMICaller.callDirect(call);
			}
		});
	}

	/**
	 * Calls the method {@link RMICallWithGUIManipulation#doRMICall()} of the
	 * {@link RMICallWithGUIManipulation} object directly in a new thread. If
	 * there was no exception, the method
	 * {@link RMICallWithGUIManipulation#doGUIManipulation()} will be called by
	 * the event dispatch thread.
	 * 
	 * @param call
	 *            {@link RMICallWithGUIManipulation}-object on which the methods
	 *            should be called
	 * @param <T>
	 *            Return type of the RMI call
	 */
	public static <T> void callDirect(final RMICallWithGUIManipulation<T> call) {
		try {
			final T rmiReturnValue = call.doRMICall();
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					call.doGUIManipulation(rmiReturnValue);
				}
			});

		} catch (final RemoteException re) {
			JOptionPane.showMessageDialog(null, "Error: " + re.getMessage());
		}
	}

	/**
	 * Interface describing the asynchronous RMI call with further manipulation
	 * of the GUI.
	 * 
	 * @param <T>
	 *            Return type of the RMI call
	 */
	public interface RMICallWithGUIManipulation<T> {
		T doRMICall() throws RemoteException;

		void doGUIManipulation(T rmiReturnValue);
	}
}
