package voting.view.detailWindow;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JPanel;

import voting.model.Poll;
import voting.model.PollChangeListener;
import voting.model.PollData;

public class VoteIncrementView extends JPanel implements PollChangeListener {

	protected final Poll model;
	private PollData pollData;

	public VoteIncrementView(final Poll model) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
		this.model = model;
		model.addPollChangeListener(this);
		this.setLayout(new GridLayout(0, 1, 0, 5));

		this.pollData = model.getPollData();
		for (int i = 0; i < this.pollData.getNumberOfAnswers(); i++) {
			this.addNewAnswer(i);
		}
	}

	private void addNewAnswer(final int index) {
		final JButton incrementButton = new JButton("Increment");
		incrementButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							VoteIncrementView.this.model.incrementVotes(index);
						} catch (final RemoteException re) {
							re.printStackTrace();
						}
					}
				}).start();

			}
		});
		incrementButton.setSize(200, 30);
		final LabeledComponent component = new LabeledComponent(this.getFormattedLabel(index), incrementButton);
		this.add(component);
		this.updateUI();
	}

	private String getFormattedLabel(final int index) {
		final String answer = this.pollData.getAnswer(index);
		final int votes = this.pollData.getVotes(index);
		final int totalVotes = this.pollData.getTotalVotes();
		final String percentage = String.format("%.2f", this.pollData.getAnswerPercentage(index)) + "%";

		return String.format("%s: %d of %d (%s)", answer, votes, totalVotes, percentage);
	}

	@Override
	public void voteChanged(final PollData data) {
		this.pollData = data;
		for (int i = 0; i < this.pollData.getNumberOfAnswers(); i++) {
			((LabeledComponent) this.getComponent(i)).setLabel(this.getFormattedLabel(i));
		}
	}

	@Override
	public void answerAdded(final PollData data) {
		this.pollData = data;
		this.addNewAnswer(this.pollData.getNumberOfAnswers() - 1);
	}
}
