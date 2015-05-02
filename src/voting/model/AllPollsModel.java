package voting.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AllPollsModel extends UnicastRemoteObject implements AllPolls {

	private final List<AllPollsChangeListener> changeListener;
	private final Map<String, Poll> polls;

	public AllPollsModel() throws RemoteException {
		this.changeListener = new LinkedList<>();
		this.polls = new HashMap<>();
	}

	@Override
	public synchronized Poll addPoll(final String id, final String question) throws DuplicatePollException, RemoteException {
		return this.addPoll(id, question, new String[0]);
	}

	@Override
	public synchronized Poll addPoll(final String id, final String question, final String[] answers) throws DuplicatePollException, RemoteException {
		if (this.pollExists(id)) {
			throw new DuplicatePollException("This poll already exists!");
		}
		final Poll poll = new PollModel(answers, question);
		this.polls.put(id, poll);
		this.firePollAdded(id, question);
		return poll;
	}

	@Override
	public synchronized void removePoll(final String id) {
		if (!this.pollExists(id)) {
			throw new IllegalArgumentException("This poll doesn't exist!");
		}
		this.polls.remove(id);
		this.firePollRemoved(id);
	}

	@Override
	public synchronized Poll getPoll(final String id) {
		if (!this.pollExists(id)) {
			throw new IllegalArgumentException("This poll doesn't exist!");
		}
		return this.polls.get(id);
	}

	private boolean pollExists(final String id) {
		return this.polls.containsKey(id);
	}

	@Override
	public synchronized String[][] getAllIdsAndQuestions() throws RemoteException {
		final String[][] allIdsAndQuestions = new String[this.polls.size()][];

		int counter = 0;
		final Set<Entry<String, Poll>> pollSet = this.polls.entrySet();
		for (final Entry<String, Poll> entry : pollSet) {
			allIdsAndQuestions[counter] = new String[2];
			allIdsAndQuestions[counter][0] = entry.getKey();
			allIdsAndQuestions[counter][1] = entry.getValue().getQuestion();
			counter++;
		}

		return allIdsAndQuestions;
	}

	private void firePollAdded(final String id, final String question) {
		final Iterator<AllPollsChangeListener> iter = this.changeListener.iterator();
		AllPollsChangeListener listener = null;
		while (iter.hasNext()) {
			listener = iter.next();
			try {
				listener.pollAdded(id, question);
			} catch (final RemoteException e) {
				iter.remove();
			}
		}
	}

	private void firePollRemoved(final String id) {
		final Iterator<AllPollsChangeListener> iter = this.changeListener.iterator();
		AllPollsChangeListener listener = null;
		while (iter.hasNext()) {
			listener = iter.next();
			try {
				listener.pollRemoved(id);
			} catch (final RemoteException e) {
				iter.remove();
			}
		}
	}

	@Override
	public synchronized void addAllPollsChangeListener(final AllPollsChangeListener l) {
		this.changeListener.add(l);
	}

	@Override
	public synchronized void removeAllPollsChangeListener(final AllPollsChangeListener l) {
		this.changeListener.remove(l);
	}

}
