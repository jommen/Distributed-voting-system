package voting.control.startWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.model.AllPolls;
import voting.model.DuplicatePollException;

public class AddPollAction implements ActionListener {

    private final AllPolls allPolls;
    private final JTextField uidField;
    private final JTextField questionField;

    public AddPollAction(final AllPolls allPolls, final JTextField uidField, final JTextField questionField) {
        this.allPolls = allPolls;
        this.uidField = uidField;
        this.questionField = questionField;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final String uid = this.uidField.getText();
        final String question = this.questionField.getText();

        if (uid.equals("") || question.equals("")) {
            JOptionPane.showMessageDialog(null, "Formular muss vollständig ausgefüllt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RMICaller.callAsync(new RMICallWithGUIManipulation<String>() {

            @Override
            public String doRMICall() throws RemoteException {
                String returnValue = "";
                try {
                    AddPollAction.this.allPolls.addPoll(uid, question);
                }
                catch (final DuplicatePollException dpe) {
                    returnValue = "Kennung bereits vorhanden!";
                }
                return returnValue;
            }

            @Override
            public void doGUIManipulation(final String rmiReturnValue) {
                if (!rmiReturnValue.equals("")) {
                    JOptionPane.showMessageDialog(null, rmiReturnValue, "Fehler", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    AddPollAction.this.uidField.setText("");
                    AddPollAction.this.questionField.setText("");
                }
            }
        });
    }
}
