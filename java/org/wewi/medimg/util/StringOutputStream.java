/**
 * Created on 12.09.2002
 *
 */
package org.wewi.medimg.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 * 
 */
public final class StringOutputStream extends OutputStream {
    private byte buf[];
    private int count;

    private String string;

    public StringOutputStream() {
        this(32);
    }

    public StringOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException(
                "Negative initial size: " + size);
        }
        buf = new byte[size];
    }

    public synchronized void write(int b) {
        int newcount = count + 1;
        if (newcount > buf.length) {
            byte newbuf[] = new byte[Math.max(buf.length << 1, newcount)];
            System.arraycopy(buf, 0, newbuf, 0, count);
            buf = newbuf;
        }
        buf[count] = (byte) b;
        count = newcount;
    }

    public synchronized void write(byte b[], int off, int len) {
        if ((off < 0)
            || (off > b.length)
            || (len < 0)
            || ((off + len) > b.length)
            || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        int newcount = count + len;
        if (newcount > buf.length) {
            byte newbuf[] = new byte[Math.max(buf.length << 1, newcount)];
            System.arraycopy(buf, 0, newbuf, 0, count);
            buf = newbuf;
        }
        System.arraycopy(b, off, buf, count, len);
        count = newcount;
    }

    public synchronized void writeTo(OutputStream out) throws IOException {
        out.write(buf, 0, count);
    }

    public synchronized void reset() {
        count = 0;
    }

    private synchronized byte[] toByteArray() { 
        byte newbuf[] = new byte[count];
        System.arraycopy(buf, 0, newbuf, 0, count);
        return newbuf;
    }

    public int size() {
        return count;
    }

    public void close() throws IOException {
        string = Base64.encode(buf, 0, size());
    }

    public String getOutputString() {
        return string;
    }

    public String toString() {
        return getOutputString();
    }

}
