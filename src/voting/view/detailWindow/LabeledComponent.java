package voting.view.detailWindow;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LabeledComponent extends JPanel {
    private final JLabel label;
    private final Component component;

    public LabeledComponent(final String label, final JComponent component) {
        this.label = new JLabel(label);
        this.label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.component = component;

        setLayout(new GridLayout(1, 0, 10, 0));
        add(this.label);
        add(this.component);
    }

    public Component getComponent() {
        return this.component;
    }

    public void setLabel(final String newLabel) {
        this.label.setText(newLabel);
    }

}
