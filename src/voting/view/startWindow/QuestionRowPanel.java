package voting.view.startWindow;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import voting.control.startWindow.DeleteActionListener;
import voting.control.startWindow.DisplayPollAction;
import voting.model.AllPolls;

public class QuestionRowPanel extends JPanel {

	/**
	 * 
	 * @param id
	 * @param allPolls
	 * @wbp.parser.constructor
	 */
	public QuestionRowPanel(final String id, final String question, final AllPolls allPolls) {
		final JLabel questionLabel = new JLabel(question);
		final JButton displayButton = new JButton("Open");
		final JButton deleteButton = new JButton("Delete");
		this.setLayout(new MigLayout("", "[100px:n,grow,left][100px][71px]", "[23px:23px:23px]"));

		this.add(questionLabel, "cell 0 0,alignx left,aligny center");
		this.add(displayButton, "cell 1 0,alignx right,aligny top");
		this.add(deleteButton, "cell 2 0,alignx left,aligny top");

		displayButton.addActionListener(new DisplayPollAction(allPolls, id));
		deleteButton.addActionListener(new DeleteActionListener(allPolls, id));
	}
}
