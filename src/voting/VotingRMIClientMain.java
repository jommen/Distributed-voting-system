package voting;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.SwingUtilities;

import voting.model.AllPolls;
import voting.view.startWindow.StartFrame;

public class VotingRMIClientMain {

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Registry registry = LocateRegistry.getRegistry();
					final AllPolls allPolls = (AllPolls) registry.lookup(AllPolls.DEFAULT_RMI_NAME);
					new StartFrame(allPolls).setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
