package voting.control.startWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.model.AllPolls;
import voting.model.Poll;
import voting.view.detailWindow.VotingFrame;

public class DisplayPollAction implements ActionListener {

    private final AllPolls polls;
    private final String id;

    public DisplayPollAction(final AllPolls polls, final String id) {
        this.id = id;
        this.polls = polls;
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
        RMICaller.callAsync(new RMICallWithGUIManipulation<Poll>() {

            @Override
            public Poll doRMICall() throws RemoteException {
                return DisplayPollAction.this.polls.getPoll(DisplayPollAction.this.id);
            }

            @Override
            public void doGUIManipulation(final Poll rmiReturnValue) {
                try {
                    new VotingFrame(rmiReturnValue);
                }
                catch (final RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
