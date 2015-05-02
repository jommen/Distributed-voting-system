package voting;

import javax.swing.SwingUtilities;

import voting.model.AllPolls;
import voting.model.AllPollsModel;
import voting.server.VotingRMIServer;

public class VotingRMIServerMain {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final AllPolls allPolls = new AllPollsModel();
                    VotingRMIServer.startServer(allPolls);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
