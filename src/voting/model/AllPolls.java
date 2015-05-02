package voting.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface to represent the functionality of a collection of {@link Poll}s.
 */
public interface AllPolls extends Remote {

	final String DEFAULT_RMI_NAME = "AllPolls";

	/**
	 * Add a new, empty poll to this collection
	 * 
	 * @param id
	 *            Poll id
	 * @param question
	 *            Question for this poll
	 * @return Model for this {@link Poll}
	 * @throws DuplicatePollException
	 */
	Poll addPoll(String id, String question) throws RemoteException, DuplicatePollException;

	/**
	 * Add a new {@link Poll} to this collection with answer options
	 * 
	 * @param id
	 *            Poll id
	 * @param question
	 *            Question for this poll
	 * @param answers
	 *            Initial answer options
	 * @return Model for this {@link Poll}
	 * @throws DuplicatePollException
	 */
	Poll addPoll(String id, String question, String[] answers) throws RemoteException, DuplicatePollException;

	/**
	 * Deletes the {@link Poll} with this
	 * 
	 * @param id
	 *            ID of the poll to delete
	 */
	void removePoll(String id) throws RemoteException;

	/**
	 * Returns the {@link Poll} with this id
	 * 
	 * @param id
	 *            ID of the poll to return
	 * @return {@link Poll}.
	 */
	Poll getPoll(String id) throws RemoteException;

	/**
	 * Returns a list of all poll ids and their questions. The second dimension
	 * of the returned array shows the position of the id and the question in
	 * the poll.
	 * 
	 * @return See method description.
	 */
	String[][] getAllIdsAndQuestions() throws RemoteException;

	void addAllPollsChangeListener(AllPollsChangeListener l) throws RemoteException;

	void removeAllPollsChangeListener(AllPollsChangeListener l) throws RemoteException;
}
