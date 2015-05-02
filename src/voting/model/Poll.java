package voting.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Poll extends Remote {
	/**
	 * Returns the question of this poll
	 * 
	 * @return Question of this poll.
	 */
	String getQuestion() throws RemoteException;

	/**
	 * Returns the current values of this poll encapsulated in a
	 * {@link PollData} object.
	 * 
	 * @return Current values of this poll as {@link PollData}.
	 */
	PollData getPollData() throws RemoteException;

	/**
	 * Adds a new answer option to this poll.
	 * 
	 * @param answer
	 *            New answer option
	 */
	void addAnswer(String answer) throws RemoteException;

	/**
	 * Sets the number of votes for a specific answer option.
	 * 
	 * @param answerIndex
	 *            ID of the answer.
	 * @param votes
	 *            New number of votes for this answer.
	 */
	void setVotes(int answerIndex, int votes) throws RemoteException;

	/**
	 * Sets the number of votes for a specific answer option.
	 * 
	 * @param answer
	 *            Text of the answer option.
	 * @param votes
	 *            New number of votes for this answer.
	 */
	void setVotes(String answer, int votes) throws RemoteException;

	/**
	 * Increments the number of votes for this answer option.
	 * 
	 * @param answerIndex
	 *            ID of the option.
	 */
	void incrementVotes(int answerIndex) throws RemoteException;

	/**
	 * Increments the number of votes for this answer option.
	 * 
	 * @param answer
	 *            Text of the option.
	 */
	void incrementVotes(String answer) throws RemoteException;

	void addPollChangeListener(PollChangeListener pcl) throws RemoteException;

	void removePollChangeListener(PollChangeListener pcl) throws RemoteException;
}
