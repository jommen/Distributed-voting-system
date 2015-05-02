package voting.view.detailWindow;

import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import voting.model.Poll;
import voting.model.PollChangeListener;
import voting.model.PollData;

public class PieChartView extends JPanel implements PollChangeListener {

	private final ArrayList<Color> colors;
	private final int numberOfBaseColors;

	private PollData pollData;

	private static final int MARGIN = 60;

	public PieChartView(final Poll pollModel) throws RemoteException {
		UnicastRemoteObject.exportObject(this, 0);
		this.pollData = pollModel.getPollData();
		pollModel.addPollChangeListener(this);

		this.colors = new ArrayList<>();
		this.colors.add(Color.GREEN);
		this.colors.add(Color.BLUE);
		this.colors.add(Color.CYAN);
		this.colors.add(Color.MAGENTA);
		this.colors.add(Color.ORANGE);
		this.colors.add(Color.LIGHT_GRAY);
		this.colors.add(Color.PINK);
		this.colors.add(Color.RED);
		this.colors.add(Color.DARK_GRAY);
		this.colors.add(Color.WHITE);
		this.colors.add(Color.YELLOW);
		this.colors.add(Color.BLACK);
		this.colors.add(Color.GRAY);
		this.numberOfBaseColors = this.colors.size();

		// setPreferredSize(new Dimension(400, 400));
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		if (this.getRootPane().getWidth() < 400) {
			g.drawString("Too small.. Please resize", 10, 10);
			return;
		}

		final int diameter = Math.min(this.getWidth(), this.getHeight()) - (PieChartView.MARGIN * 2);

		g.drawOval(PieChartView.MARGIN, PieChartView.MARGIN, diameter, diameter);

		int startAngle = 0;
		this.generateNewColorsIfNeeded();
		final Iterator<Color> fillColorIterator = this.colors.iterator();
		for (int i = 0; i < this.pollData.getNumberOfAnswers(); i++) {
			if (this.pollData.getAnswerPercentage(i) > 0) {
				final double arcAngle = (this.pollData.getAnswerPercentage(i) * 360) / 100;
				g.setColor(fillColorIterator.next());
				g.fillArc(PieChartView.MARGIN, PieChartView.MARGIN, diameter, diameter, startAngle, (int) Math.round(arcAngle));
				startAngle += arcAngle;
				this.drawLabel(g, diameter / 2, this.pollData.getAnswer(i), startAngle - (arcAngle / 2.0));
			}
		}
	}

	private void drawLabel(final Graphics g, final int radius, final String text, double angle) {
		final int circleCenter = PieChartView.MARGIN + radius;
		final double multiplier = radius * 1.3;

		int x, y;
		if ((angle >= 0) && (angle < 180)) {
			final double angleInDegree = Math.toRadians(angle);
			x = (int) (circleCenter + (Math.cos(angleInDegree) * multiplier));
			y = (int) (circleCenter - (Math.sin(angleInDegree) * multiplier));
		} else {
			angle -= 180;
			final double angleInDegree = Math.toRadians(angle);
			x = (int) (circleCenter - (Math.cos(angleInDegree) * multiplier));
			y = (int) (circleCenter + (Math.sin(angleInDegree) * multiplier));
		}

		final int stringWidth = g.getFontMetrics().stringWidth(text);
		x -= stringWidth / 2;

		final int fontHeight = g.getFontMetrics().getAscent() + g.getFontMetrics().getLeading();
		y += fontHeight / 2;

		g.setColor(Color.BLACK);
		g.drawString(text, x, y);
	}

	private void generateNewColorsIfNeeded() {
		if (this.pollData.getNumberOfAnswers() >= this.colors.size()) {
			this.generateNewColors();
		}
	}

	private void generateNewColors() {
		final int startIndex = this.colors.size() - this.numberOfBaseColors;
		for (int i = startIndex; i < this.numberOfBaseColors; i++) {
			Color color = this.colors.get(i);
			while (this.colors.contains(color) && !color.equals(Color.BLACK)) {
				color = color.darker();
			}

			if (!color.equals(Color.BLACK)) {
				this.colors.add(color);
			}
		}
	}

	@Override
	public void voteChanged(final PollData data) {
		this.pollData = data;
		this.repaint();
	}

	@Override
	public void answerAdded(final PollData data) {
		this.pollData = data;
		this.repaint();
	}
}
