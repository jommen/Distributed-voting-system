package voting.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class PollModel extends UnicastRemoteObject implements Poll {

	private final String question;

	private final ArrayList<PollAnswer> answers;
	private final ArrayList<PollChangeListener> listeners;

	public PollModel(final String question) throws RemoteException {
		this(new String[0], question);
	}

	public PollModel(final String[] answers, final String question) throws RemoteException {
		this.question = question;

		this.listeners = new ArrayList<>();
		this.answers = new ArrayList<>();
		for (final String name : answers) {
			this.answers.add(new PollAnswer(name));
		}
	}

	@Override
	public synchronized PollData getPollData() {
		final String[] pollAnswers = new String[this.answers.size()];
		final int[] votes = new int[this.answers.size()];

		for (int i = 0; i < this.answers.size(); i++) {
			pollAnswers[i] = this.answers.get(i).getText();
			votes[i] = this.answers.get(i).getVotes();
		}

		final PollData pollData = new PollData(this.question, pollAnswers, votes, this.getTotalVotes());
		return pollData;
	}

	private int getTotalVotes() {
		int totalVotes = 0;
		for (final PollAnswer answer : this.answers) {
			totalVotes += answer.getVotes();
		}

		return totalVotes;
	}

	@Override
	public synchronized void addAnswer(final String answer) {
		if (this.getIndexOfAnswer(answer) >= 0) {
			throw new IllegalArgumentException("Option already exists!");
		}
		this.answers.add(new PollAnswer(answer, 0));
		this.fireAnswerAdded();
	}

	@Override
	public synchronized void setVotes(final String answer, final int votes) {
		final int index = this.getIndexOfAnswer(answer);
		if (index >= 0) {
			this.setVotes(index, votes);
		} else {
			throw new IllegalArgumentException("Option does not exist!");
		}
	}

	@Override
	public synchronized void setVotes(final int answerIndex, final int votes) {
		this.checkAnswerExists(answerIndex);
		this.answers.get(answerIndex).setVotes(votes);
		this.fireVotesChanged();
	}

	@Override
	public synchronized void incrementVotes(final int answerIndex) {
		this.checkAnswerExists(answerIndex);
		this.answers.get(answerIndex).incrementVotes();
		this.fireVotesChanged();
	}

	@Override
	public synchronized void incrementVotes(final String answer) {
		final int index = this.getIndexOfAnswer(answer);
		if (index >= 0) {
			this.incrementVotes(index);
		} else {
			throw new IllegalArgumentException("Option does not exist!");
		}
	}

	private void checkAnswerExists(final int index) {
		if (index >= this.answers.size()) {
			throw new IllegalArgumentException("Option with index " + index + " does not exist.");
		}
	}

	private int getIndexOfAnswer(final String answer) {
		for (int i = 0; i < this.answers.size(); i++) {
			final PollAnswer pollAnswer = this.answers.get(i);
			if (pollAnswer.getText().equals(answer)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public synchronized void addPollChangeListener(final PollChangeListener pcl) {
		this.listeners.add(pcl);
	}

	@Override
	public synchronized void removePollChangeListener(final PollChangeListener pcl) {
		this.listeners.remove(pcl);
	}

	private void fireAnswerAdded() {
		final PollData pollData = this.getPollData();
		final Iterator<PollChangeListener> iter = this.listeners.iterator();
		PollChangeListener currentListener = null;
		while (iter.hasNext()) {
			currentListener = iter.next();
			try {
				currentListener.answerAdded(pollData);
			} catch (final RemoteException e) {
				iter.remove();
			}
		}
	}

	private void fireVotesChanged() {
		final PollData pollData = this.getPollData();
		final Iterator<PollChangeListener> iter = this.listeners.iterator();
		PollChangeListener currentListener = null;
		while (iter.hasNext()) {
			currentListener = iter.next();
			try {
				currentListener.voteChanged(pollData);
			} catch (final RemoteException e) {
				iter.remove();
			}
		}
	}

	@Override
	public synchronized String getQuestion() {
		return this.question;
	}
}
