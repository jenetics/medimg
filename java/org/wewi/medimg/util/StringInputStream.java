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
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringInputStream extends InputStream {
    private ByteBuffer buffer;
    private byte[] raw;

	/**
	 * Constructor for StringInputStream.
	 */
	public StringInputStream(String data) {
		super();
        
        raw = Base64.decode(data);
        buffer = ByteBuffer.wrap(raw);
	}

	/**
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException {
		return buffer.get();
	}

	/**
	 * @see java.io.InputStream#available()
	 */
	public int available() throws IOException {
		return Integer.MAX_VALUE;
	}

	/**
	 * @see java.io.InputStream#close()
	 */
	public void close() throws IOException {
		raw = null;
        buffer = null;
	}

	/**
	 * @see java.io.InputStream#mark(int)
	 */
	public synchronized void mark(int m) {
	}

	/**
	 * @see java.io.InputStream#markSupported()
	 */
	public boolean markSupported() {
		return false;
	}

	/**
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	public int read(byte[] b, int offset, int length) throws IOException {
        if (length < buffer.remaining()) {
            buffer.get(b, offset, buffer.remaining());
            return buffer.remaining();    
        } else {
            buffer.get(b, offset, length);
            return length;
        }
	}

	/**
	 * @see java.io.InputStream#read(byte[])
	 */
	public int read(byte[] b) throws IOException {
        if (b.length < buffer.remaining()) {
            buffer.get(b);
            return buffer.remaining();    
        } else {
            buffer.get(b);
            return b.length;
        }
	}

	/**
	 * @see java.io.InputStream#reset()
	 */
	public synchronized void reset() throws IOException {
		buffer.position(0);
	}

	/**
	 * @see java.io.InputStream#skip(long)
	 */
	public long skip(long length) throws IOException {
        if (length < buffer.remaining()) {
            buffer.position(buffer.position()+(int)length);
            return length;
        } else {
            buffer.position(buffer.limit()); 
            return buffer.remaining();   
        }
	}

}
