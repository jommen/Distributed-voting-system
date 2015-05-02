package voting.view.startWindow;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import voting.control.startWindow.AddPollAction;
import voting.model.AllPolls;

public class NewPollPanel extends JPanel {
	private final JLabel lblUniqueID = new JLabel("Unique ID:");
	private final JTextField uniqueIdentifier = new JTextField();
	private final JLabel lblQuestion = new JLabel("Question:");
	private final JTextField question = new JTextField();
	private final JButton btnCreate = new JButton("Create");

	public NewPollPanel(final AllPolls allPolls) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		this.setBorder(BorderFactory.createTitledBorder("Create new poll"));

		this.lblUniqueID.setLabelFor(this.uniqueIdentifier);
		this.lblQuestion.setLabelFor(this.question);
		this.question.setColumns(15);
		this.uniqueIdentifier.setToolTipText("");
		this.uniqueIdentifier.setColumns(10);

		this.add(this.lblUniqueID);
		this.add(this.uniqueIdentifier);
		this.add(this.lblQuestion);
		this.add(this.question);
		this.add(this.btnCreate);

		this.btnCreate.addActionListener(new AddPollAction(allPolls, this.uniqueIdentifier, this.question));
	}

}
