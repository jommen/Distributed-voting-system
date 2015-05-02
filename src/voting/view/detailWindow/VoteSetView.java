package voting.view.detailWindow;

import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.control.detailWindow.SetCandidateVotesAction;
import voting.model.Poll;
import voting.model.PollChangeListener;
import voting.model.PollData;

public class VoteSetView extends JPanel implements PollChangeListener {
    private final Poll model;

    public VoteSetView(final Poll model) throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        this.model = model;
        model.addPollChangeListener(this);
        setLayout(new GridLayout(0, 1, 0, 5));

        RMICaller.callAsync(new RMICallWithGUIManipulation<PollData>() {

            @Override
            public PollData doRMICall() throws RemoteException {
                return model.getPollData();
            }

            @Override
            public void doGUIManipulation(final PollData rmiReturnValue) {
                for (int i = 0; i < rmiReturnValue.getNumberOfAnswers(); i++) {
                    addNewCandidate(rmiReturnValue, i);
                }
            }
        });

    }

    private void addNewCandidate(final PollData pollData, final int index) {
        System.out.println(Thread.currentThread().getName());
        final JFormattedTextField textField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        final LabeledComponent component = new LabeledComponent(pollData.getAnswer(index), textField);
        final SetCandidateVotesAction action = new SetCandidateVotesAction(this.model, index, textField);

        textField.setText(String.valueOf(pollData.getVotes(index)));
        textField.addFocusListener(action);
        textField.addKeyListener(action);

        add(component);
    }

    @Override
    public void voteChanged(final PollData pollData) {
        for (int i = 0; i < pollData.getNumberOfAnswers(); i++) {
            final JFormattedTextField textField = (JFormattedTextField) ((LabeledComponent) getComponent(i)).getComponent();
            textField.setText("" + pollData.getVotes(i));
        }
    }

    @Override
    public void answerAdded(final PollData pollData) {
        addNewCandidate(pollData, pollData.getNumberOfAnswers() - 1);
    }

}
