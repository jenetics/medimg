/**
 * Created on 07.10.2002 19:26:41
 *
 */
package org.wewi.medimg.util.param;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ParameterWriter {
    public void write(Parameter parameter, StringBuffer buffer);
    
    public void write(Parameter parameter, String file) throws IOException;
    
    public void write(Parameter parameter, OutputStream out) throws IOException;
}
