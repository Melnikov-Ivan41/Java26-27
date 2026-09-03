package Lab5;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class DecipherReader extends FilterReader {
    private final int key;

    public DecipherReader(Reader in, char key) {
        super(in);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c != -1) {
            return c - key;
        }
        return c;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int readChars = super.read(cbuf, off, len);
        if (readChars != -1) {
            for (int i = off; i < off + readChars; i++) {
                cbuf[i] = (char) (cbuf[i] - key);
            }
        }
        return readChars;
    }
}
