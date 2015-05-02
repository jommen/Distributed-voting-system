package voting.control.detailWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.JTextField;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.model.Poll;

public class AddCandidateAction implements KeyListener {

    private final Poll model;

    public AddCandidateAction(final Poll model) {
        this.model = model;
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource() instanceof JTextField) {
            final JTextField textField = (JTextField) e.getSource();
            final String answer = textField.getText();
            if (!answer.equals("")) {
                RMICaller.callAsync(new RMICallWithGUIManipulation<Void>() {

                    @Override
                    public Void doRMICall() throws RemoteException {
                        AddCandidateAction.this.model.addAnswer(answer);
                        return null;
                    }

                    @Override
                    public void doGUIManipulation(final Void rmiReturnValue) {
                        textField.setText("");
                    }
                });
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }

}
