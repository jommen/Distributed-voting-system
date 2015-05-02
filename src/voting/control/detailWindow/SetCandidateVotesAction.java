package voting.control.detailWindow;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.model.Poll;

public class SetCandidateVotesAction implements KeyListener, FocusListener {

	private final int index;
	private final Poll model;
	private final JFormattedTextField source;

	public SetCandidateVotesAction(final Poll model, final int index, final JFormattedTextField source) {
		this.index = index;
		this.model = model;
		this.source = source;
	}

	@Override
	public void focusGained(final FocusEvent e) {
	}

	@Override
	public void focusLost(final FocusEvent e) {
		this.updateModel();
	}

	@Override
	public void keyTyped(final KeyEvent e) {
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (e.getSource() instanceof JTextField)) {
			this.updateModel();
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	}

	private void updateModel() {
		try {
			final int votes = Integer.parseInt(this.source.getText());
			RMICaller.callAsync(new RMICallWithGUIManipulation<Void>() {

				@Override
				public Void doRMICall() throws RemoteException {
					SetCandidateVotesAction.this.model.setVotes(SetCandidateVotesAction.this.index, votes);
					return null;
				}

				@Override
				public void doGUIManipulation(final Void rmiReturnValue) {

				}
			});
		} catch (final NumberFormatException e) {

		}
	}
}
