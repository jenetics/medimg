/**
 * Created on 18.10.2002 13:47:09
 *
 */
package org.wewi.medimg.viewer;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PrintCommand implements Command {
    private ImageViewer imageViewer;

	/**
	 * Constructor for PrintCommand.
	 */
	public PrintCommand(ImageViewer imageViewer) {
		super();
        this.imageViewer = imageViewer;
	}

	/**
	 * @see org.wewi.medimg.viewer.Command#execute()
	 */
	public void execute() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        pf = job.pageDialog(pf);
        job.setPrintable(imageViewer, pf);
        if (job.printDialog()) {
            try {
                job.print();
            }
            catch (PrinterException e) {
                JOptionPane.showMessageDialog(Viewer.getInstance(), e);
            }
        }        
	}

}
