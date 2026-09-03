package Lab5;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class CipherWriter extends FilterWriter {
    private final int key;

    public CipherWriter(Writer out, char key) {
        super(out);
        this.key = key;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + key);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            cbuf[i] = (char) (cbuf[i] + key);
        }
        super.write(cbuf, off, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        char[] chars = str.substring(off, off + len).toCharArray();
        write(chars, 0, chars.length);
    }
}