package voting.view.startWindow;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.model.AllPolls;
import voting.model.AllPollsChangeListener;

public class OverviewPanel extends JScrollPane implements AllPollsChangeListener {
	private final JPanel contentPanel;
	private final AllPolls allPolls;
	private final HashMap<String, QuestionRowPanel> questionRows;

	public OverviewPanel(final AllPolls allPolls) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
		this.questionRows = new HashMap<>();
		this.allPolls = allPolls;
		this.allPolls.addAllPollsChangeListener(this);
		this.setBorder(BorderFactory.createTitledBorder("Poll dashboard"));

		RMICaller.callAsync(new RMICallWithGUIManipulation<String[][]>() {

			@Override
			public String[][] doRMICall() throws RemoteException {
				final String[][] idsAndQuestions = allPolls.getAllIdsAndQuestions();
				return idsAndQuestions;
			}

			@Override
			public void doGUIManipulation(final String[][] questions) {
				for (int i = questions.length - 1; i >= 0; i--) {
					OverviewPanel.this.addQuestion(questions[i][0], questions[i][1]);
				}
			}
		});

		this.contentPanel = new JPanel(new GridBagLayout());
		this.setViewportView(this.contentPanel);
	}

	public void addQuestion(final String id, final String question) {
		this.addQuestionRow(id, new QuestionRowPanel(id, question, this.allPolls));
	}

	private void addQuestionRow(final String id, final QuestionRowPanel questionRow) {
		if (this.questionRows.containsKey(id)) {
			this.contentPanel.remove(this.questionRows.get(id));
			this.questionRows.remove(id);
		}
		this.contentPanel.add(questionRow, this.getGridBagConstraints());
		this.questionRows.put(id, questionRow);
		this.contentPanel.validate();
	}

	private GridBagConstraints getGridBagConstraints() {
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = this.contentPanel.getComponentCount();
		constraints.weighty = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		return constraints;
	}

	public void removeQuestion(final String id) {
		this.contentPanel.remove(this.questionRows.get(id));
		this.questionRows.remove(id);
	}

	@Override
	public void pollAdded(final String id, final String question) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				OverviewPanel.this.addQuestion(id, question);
			}
		});
	}

	@Override
	public void pollRemoved(final String id) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				OverviewPanel.this.contentPanel.remove(OverviewPanel.this.questionRows.get(id));
				OverviewPanel.this.contentPanel.repaint();
			}
		});

	}
}
