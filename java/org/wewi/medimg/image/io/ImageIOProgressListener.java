/**
 * Created on 01.09.2002
 *
 */
package org.wewi.medimg.image.io;

import java.util.EventListener;


/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public interface ImageIOProgressListener extends EventListener {

    public void progressChanged(ImageIOProgressEvent event);
}
