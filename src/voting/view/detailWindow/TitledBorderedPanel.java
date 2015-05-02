package voting.view.detailWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TitledBorderedPanel extends JPanel {
    public TitledBorderedPanel(final String title, final Component content) {
        setLayout(new GridBagLayout());
        add(content, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), title));
    }
}
