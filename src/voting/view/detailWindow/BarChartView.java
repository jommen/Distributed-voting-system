package voting.view.detailWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JPanel;

import voting.model.Poll;
import voting.model.PollChangeListener;
import voting.model.PollData;

public class BarChartView extends JPanel implements PollChangeListener {

    private PollData pollData;

    public BarChartView(final Poll model) throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        this.pollData = model.getPollData();
        model.addPollChangeListener(this);
        setPreferredSize(new Dimension(250, 250));
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);

        final FontMetrics fontMetrics = g.getFontMetrics();

        final int xLabel = 10;
        final int padding = 8;
        final int xBar = xLabel + getMaxStringWidth(fontMetrics) + padding;
        final int maxBarWidth = getWidth() - xBar - 10;
        final int barHeight = fontMetrics.getAscent() + fontMetrics.getLeading();
        final int maxVotes = getMaxVotes();

        int currentY = 10;
        for (int i = 0; i < this.pollData.getNumberOfAnswers(); i++) {
            g.drawString(this.pollData.getAnswer(i), xLabel, currentY + barHeight);
            g.drawRect(xBar, currentY, maxBarWidth, barHeight);

            if (maxVotes > 0) {
                final int barWidth = this.pollData.getVotes(i) * maxBarWidth / maxVotes;
                g.fillRect(xBar, currentY, barWidth, barHeight);
            }
            currentY += barHeight + padding;
        }
    }

    private int getMaxVotes() {
        int maxVotes = 0;
        for (int i = 0; i < this.pollData.getNumberOfAnswers(); i++) {
            maxVotes = Math.max(maxVotes, this.pollData.getVotes(i));
        }

        return maxVotes;
    }

    private int getMaxStringWidth(final FontMetrics metrics) {
        int maxStringWidth = 0;
        for (int i = 0; i < this.pollData.getNumberOfAnswers(); i++) {
            maxStringWidth = Math.max(maxStringWidth, metrics.stringWidth(this.pollData.getAnswer(i)));
        }

        return maxStringWidth;
    }

    @Override
    public void voteChanged(final PollData data) {
        this.pollData = data;
        repaint();
    }

    @Override
    public void answerAdded(final PollData data) {
        this.pollData = data;
        repaint();
    }
}
