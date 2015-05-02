package voting.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the listener that want to get a notice of changes on a
 * {@link AllPolls} implementation
 */
public interface AllPollsChangeListener extends Remote {
	/**
	 * Will be called if there was a new poll added to the model
	 * 
	 * @param id
	 *            ID of the poll
	 * @param question
	 *            Poll question
	 */
	void pollAdded(String id, String question) throws RemoteException;

	/**
	 * Will be called if the poll is deleted.
	 * 
	 * @param id
	 *            ID of the deleted poll.
	 */
	void pollRemoved(String id) throws RemoteException;

}