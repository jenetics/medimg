/**
 * LogHandlerPanel.java
 *
 * Created on 21. August 2002, 15:29
 */

package org.wewi.medimg.viewer;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import javax.swing.JFileChooser;
import javax.swing.JScrollBar;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class LogHandlerPanel extends javax.swing.JPanel {
    
    private class HandlerWorker extends Handler {
        private LogHandlerPanel panel;
        
        public HandlerWorker(LogHandlerPanel panel) {
            this.panel = panel;
            setFormatter(new SimpleFormatter()); 
        }
        
        /**
         * @see java.util.logging.Handler#close()
         */
        public void close() throws SecurityException {
        }

        /**
         * @see java.util.logging.Handler#flush()
         */
        public void flush() {
        }

        /**
         * @see java.util.logging.Handler#publish(LogRecord)
         */
        public void publish(LogRecord record) {
            String msg;
            try {
                msg = getFormatter().format(record);
            } catch (Exception ex) {
                // We don't want to throw an exception here, but we
                // report the exception to any registered ErrorManager.
                reportError(null, ex, ErrorManager.FORMAT_FAILURE);
                return;
            }
        
            panel.appendText(msg);
        }

    }
    
    
    private HandlerWorker handler;
    
    /** Creates new form LogHandlerPanel */
    public LogHandlerPanel() {
        initComponents();
        init();
    }
    
    private void init() {
        handler = new HandlerWorker(this);    
    }
    
    public Handler getHandler() {
        return handler;    
    }
    
    public void clear() {
        logTextArea.setText("");    
    }
    
    private void appendText(String text) {
        logTextArea.append(text); 
        JScrollBar scrollBar = jScrollPane1.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());           
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        logPopupMenu = new javax.swing.JPopupMenu();
        fontChooserMenuItem = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();

        logPopupMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        logPopupMenu.setInvoker(logTextArea);
        fontChooserMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        fontChooserMenuItem.setText("Schriftart");
        fontChooserMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fontChooserMenuItemMousePressed(evt);
            }
        });

        logPopupMenu.add(fontChooserMenuItem);

        setLayout(new java.awt.BorderLayout());

        setBorder(new javax.swing.border.TitledBorder(null, "Logging", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12)));
        clearButton.setFont(new java.awt.Font("Dialog", 0, 12));
        clearButton.setMnemonic('L');
        clearButton.setText("L\u00f6schen");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jPanel1.add(clearButton);

        saveButton.setFont(new java.awt.Font("Dialog", 0, 12));
        saveButton.setMnemonic('n');
        saveButton.setText("Speichern");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jPanel1.add(saveButton);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        logTextArea.setEditable(false);
        logTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logTextAreaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                logTextAreaMouseReleased(evt);
            }
        });

        jScrollPane1.setViewportView(logTextArea);

        jPanel2.add(jScrollPane1);

        add(jPanel2, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void fontChooserMenuItemMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fontChooserMenuItemMousePressed

    }//GEN-LAST:event_fontChooserMenuItemMousePressed

    private void logTextAreaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logTextAreaMouseReleased
        if (evt.isPopupTrigger()) {
            logPopupMenu.show(evt.getComponent(),
                       evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_logTextAreaMouseReleased

    private void logTextAreaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logTextAreaMousePressed
        if (evt.isPopupTrigger()) {
            logPopupMenu.show(evt.getComponent(),
                       evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_logTextAreaMousePressed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Logging-Meldungen speichern");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        String fileName = chooser.getSelectedFile().getAbsolutePath();
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(fileName));
            logTextArea.write(out);
        } catch (Exception e) {
            return;
        }         
    }//GEN-LAST:event_saveButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenuItem fontChooserMenuItem;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu logPopupMenu;
    // End of variables declaration//GEN-END:variables
    
}
