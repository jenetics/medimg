/**
 * Created on 12.09.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringOutputStream extends OutputStream {
    private ByteBuffer buffer;
    private String string;

	/**
	 * Constructor for StringOutputStream.
	 */
	public StringOutputStream() {
		super();
        buffer = ByteBuffer.allocate(100);
	}


	/**
	 * @see java.io.OutputStream#close()
	 */
	public void close() throws IOException {
	   byte[] raw = buffer.array();
       string = Base64.encode(raw);
    
       buffer = null;	
	}

	/**
	 * @see java.io.OutputStream#flush()
	 */
	public void flush() throws IOException {
		super.flush();
	}

	/**
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte[] b, int offset, int length) throws IOException {
		buffer.put(b, offset, length);
	}

	/**
	 * @see java.io.OutputStream#write(byte[])
	 */
	public void write(byte[] b) throws IOException {
		buffer.put(b);
	}

	/**
	 * @see java.io.OutputStream#write(int)
	 */
	public void write(int b) throws IOException {
        buffer.put((byte)b);
	}
    
    public String toString() {
        return string;    
    }

}
