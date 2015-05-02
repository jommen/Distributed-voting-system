package voting.control.startWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import voting.RMICaller;
import voting.RMICaller.RMICallWithGUIManipulation;
import voting.model.AllPolls;

public class DeleteActionListener implements ActionListener {

    private final String id;
    private final AllPolls allPolls;

    public DeleteActionListener(final AllPolls allPolls, final String id) {
        this.id = id;
        this.allPolls = allPolls;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        RMICaller.callAsync(new RMICallWithGUIManipulation<Void>() {

            @Override
            public Void doRMICall() throws RemoteException {
                DeleteActionListener.this.allPolls.removePoll(DeleteActionListener.this.id);
                return null;
            }

            @Override
            public void doGUIManipulation(final Void rmiReturnValue) {

            }
        });
    }
}
