package voting.view.detailWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.control.detailWindow.AddCandidateAction;
import voting.model.Poll;
import voting.model.PollChangeListener;
import voting.model.PollData;

public class VotingFrame extends JFrame implements PollChangeListener {
	private final Poll model;

	public VotingFrame(final Poll model) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
		this.model = model;
		this.model.addPollChangeListener(this);

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());

		this.addQuestionView();
		this.addNewCandidateView();
		this.addVoteIncrementView();
		this.addVoteSetView();
		this.addPieChart();
		this.addBarChart();

		this.setResizable(false);
		this.pack();
		this.setSize(this.getWidth() + 300, this.getHeight() + 50);
		this.setVisible(true);
	}

	private void addQuestionView() {
		final GridBagConstraints constraints = this.createConstraints(0, 0);
		constraints.weighty = 0;
		RMICaller.callAsync(new RMICallWithGUIManipulation<String>() {

			@Override
			public String doRMICall() throws RemoteException {
				return VotingFrame.this.model.getQuestion();
			}

			@Override
			public void doGUIManipulation(final String rmiReturnValue) {
				final TitledBorderedPanel questionPanel = new TitledBorderedPanel("Question", new JLabel(rmiReturnValue));
				VotingFrame.this.add(questionPanel, constraints);
			}
		});
	}

	private void addNewCandidateView() {
		final GridBagConstraints constraints = this.createConstraints(1, 0);
		constraints.weighty = 0;

		final JTextField newCandidateTextField = new JTextField();
		newCandidateTextField.addKeyListener(new AddCandidateAction(this.model));
		final LabeledComponent newCandidateField = new LabeledComponent("New option:", newCandidateTextField);
		final TitledBorderedPanel newCandidatePanel = new TitledBorderedPanel("Add a new option to the poll", newCandidateField);
		this.add(newCandidatePanel, constraints);
	}

	private void addVoteIncrementView() throws RemoteException {
		final GridBagConstraints constraints = this.createConstraints(0, 1);
		constraints.weighty = 0;

		final VoteIncrementView voteIncrementView = new VoteIncrementView(this.model);
		final TitledBorderedPanel voteIncrementPanel = new TitledBorderedPanel("Increment", voteIncrementView);
		this.add(voteIncrementPanel, constraints);
	}

	private void addVoteSetView() throws RemoteException {
		final GridBagConstraints constraints = this.createConstraints(1, 1);
		constraints.weighty = 0;

		final VoteSetView voteSetView = new VoteSetView(this.model);
		final TitledBorderedPanel voteSetPanel = new TitledBorderedPanel("Set", voteSetView);
		this.add(voteSetPanel, constraints);
	}

	private void addPieChart() throws RemoteException {
		final GridBagConstraints constraints = this.createConstraints(0, 2);

		final PieChartView pieChartView = new PieChartView(this.model);
		final TitledBorderedPanel pieChartPanel = new TitledBorderedPanel("Pie chart", pieChartView);
		this.add(pieChartPanel, constraints);
	}

	private void addBarChart() throws RemoteException {
		final GridBagConstraints constraints = this.createConstraints(1, 2);

		final BarChartView barChartView = new BarChartView(this.model);
		final TitledBorderedPanel barChartPanel = new TitledBorderedPanel("Bar chart", barChartView);
		this.add(barChartPanel, constraints);
	}

	private GridBagConstraints createConstraints(final int x, final int y) {
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		return constraints;
	}

	@Override
	public void voteChanged(final PollData data) {

	}

	@Override
	public void answerAdded(final PollData data) {
		this.setSize(this.getWidth(), this.getHeight() + 40);
	}
}
