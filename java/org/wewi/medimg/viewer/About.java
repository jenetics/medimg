/* Generated by Together */

package org.wewi.medimg.viewer;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JDialog {
    private String title = "About SRT";
    private String product = "Segmentation and Registration Tool";
    private String version = "1.0";
    private String copyright = "Copyright (c) 2002/2003";
    private String comments = " Authors: \n    -Werner Weiser\n    -Franz Wilhelmstötter";
    private JPanel contentPane = new JPanel();
    private JLabel prodLabel = new JLabel();
    private JLabel verLabel = new JLabel();
    private JLabel copLabel = new JLabel();
    private JTextArea commentField = new JTextArea();
    private JPanel btnPanel = new JPanel();
    private JButton okButton = new JButton();
    private JLabel image = new JLabel();
    private BorderLayout formLayout = new BorderLayout();
    private GridBagLayout contentPaneLayout = new GridBagLayout();
    private FlowLayout btnPaneLayout = new FlowLayout();

    /** Creates new About Dialog */
    public About(Frame parent, boolean modal) {
        super(parent, modal);
        initGUI();
        pack();
    }

    public About() {
        super();
        setModal(true);
        initGUI();
        pack();
    }

    /** This method is called from within the constructor to initialize the dialog. */
    private void initGUI() {
        addWindowListener(
            new java.awt.event.WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    closeDialog(evt);
                }
            });
        getContentPane().setLayout(formLayout);
        contentPane.setLayout(contentPaneLayout);
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        prodLabel.setText(product);
        contentPane.add(prodLabel,
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER, 1,
            0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        verLabel.setText(version);
        contentPane.add(verLabel,
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER, 1,
            0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        copLabel.setText(copyright);
        contentPane.add(copLabel,
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER, 1,
            0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        commentField.setBackground(getBackground());
        commentField.setForeground(copLabel.getForeground());
        commentField.setFont(copLabel.getFont());
        commentField.setText(comments);
        commentField.setEditable(false);
        contentPane.add(commentField,
            new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, GridBagConstraints.REMAINDER, 3,
            0.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/logo_seg.gif")));
        getContentPane().add(image, BorderLayout.WEST);
        getContentPane().add(contentPane, BorderLayout.CENTER);
        btnPanel.setLayout(btnPaneLayout);
        okButton.setText("OK");
        okButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
        btnPanel.add(okButton);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        setTitle(title);
        setResizable(false);
    }

    /** Closes the dialog */
    private void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }
}
