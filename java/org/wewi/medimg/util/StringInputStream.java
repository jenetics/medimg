/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * Created on 12.09.2002
 *
 */
package org.wewi.medimg.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class StringInputStream extends InputStream {
    private ByteArrayInputStream stream;
    
    public StringInputStream(String data) {
        stream = new ByteArrayInputStream(Base64.decode(data));    
    }
    

    /**
     * @see java.io.InputStream#available()
     */
    public int available() throws IOException {
        return stream.available();
    }

    /**
     * @see java.io.InputStream#close()
     */
    public void close() throws IOException {
        stream.close();
    }

    /**
     * @see java.io.InputStream#mark(int)
     */
    public synchronized void mark(int m) {
        stream.mark(m);
    }

    /**
     * @see java.io.InputStream#markSupported()
     */
    public boolean markSupported() {
        return stream.markSupported();
    }

    /**
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
        return stream.read();
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte[] b, int offset, int length) throws IOException {
        return stream.read(b, offset, length);
    }

    /**
     * @see java.io.InputStream#read(byte[])
     */
    public int read(byte[] b) throws IOException {
        return stream.read(b);
    }

    /**
     * @see java.io.InputStream#reset()
     */
    public synchronized void reset() throws IOException {
        stream.reset();
    }

    /**
     * @see java.io.InputStream#skip(long)
     */
    public long skip(long n) throws IOException {
        return stream.skip(n);
    }

}
