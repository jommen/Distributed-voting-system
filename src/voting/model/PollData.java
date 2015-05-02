package voting.model;

import java.io.Serializable;

/**
 * Value object for the content of a model implementing the {@link Poll}
 * interface
 */
public class PollData implements Serializable {
	private final String question;
	private final String[] answers;
	private final int[] votes;
	private final int totalVotes;

	/**
	 * Constructor.
	 * 
	 * @param question
	 *            Question of this poll
	 * @param answers
	 *            Options for this poll.
	 * @param votes
	 *            Number of votes for all options individually.
	 * @param totalVotes
	 *            Total number of votes for all options together.
	 */
	public PollData(final String question, final String[] answers, final int[] votes, final int totalVotes) {
		this.question = question;
		this.answers = answers.clone();
		this.votes = votes.clone();
		this.totalVotes = totalVotes;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return this.question;
	}

	public String[] getAllAnswers() {
		return this.answers;
	}

	public String getAnswer(final int index) {
		return this.answers[index];
	}

	public int getNumberOfAnswers() {
		return this.answers.length;
	}

	public double getAnswerPercentage(final int index) {
		if (this.totalVotes == 0) {
			return 0;
		}
		final float percentage = (float) (this.votes[index] * 100) / this.totalVotes;
		return (percentage * 100) / 100.0;
	}

	public int[] getAllVotes() {
		return this.votes;
	}

	public int getVotes(final int index) {
		return this.votes[index];
	}

	public int getTotalVotes() {
		return this.totalVotes;
	}

}
