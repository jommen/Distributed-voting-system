package voting.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PollChangeListener extends Remote {
	/**
	 * Will be called when the number of votes changed for this poll.
	 * 
	 * @param data
	 *            {@link PollData} object containing the new values and number
	 *            of votes.
	 */
	void voteChanged(PollData data) throws RemoteException;

	/**
	 * Will be called when a new option was added to the poll.
	 * 
	 * @param data
	 *            {@link PollData} object containing the new answer and number
	 *            of votes.
	 */
	void answerAdded(PollData data) throws RemoteException;
}