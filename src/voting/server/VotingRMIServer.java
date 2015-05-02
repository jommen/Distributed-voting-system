package voting.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import voting.model.AllPolls;

public class VotingRMIServer {
	public static void startServer(final AllPolls allPolls) throws RemoteException, AlreadyBoundException {
		final Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		registry.bind(AllPolls.DEFAULT_RMI_NAME, allPolls);
		System.out.println("RMI-Server ready...");
	}
}
