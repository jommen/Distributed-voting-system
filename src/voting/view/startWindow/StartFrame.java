package voting.view.startWindow;

import java.awt.BorderLayout;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import voting.model.AllPolls;

public class StartFrame extends JFrame {

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws RemoteException
	 */
	public StartFrame(final AllPolls allPolls) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException,
	RemoteException {
		this.setTitle("Poll management");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);

		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		final NewPollPanel newPollPanel = new NewPollPanel(allPolls);
		final OverviewPanel overviewPanel = new OverviewPanel(allPolls);
		final JPanel contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);

		contentPane.add(newPollPanel, BorderLayout.SOUTH);
		contentPane.add(overviewPanel, BorderLayout.CENTER);

		this.setSize(800, 400);
		// setResizable(false);

	}

}
