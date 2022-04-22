package decryptor;

import system.Crypto;
import java.io.IOException;

public class DeCryptor extends Crypto implements Decryptable {

    public DeCryptor(int key, String inPath, String outPath) {
        super(key, inPath, outPath);
    }

    public DeCryptor(String inPathStat, String inPath, String outPath) {
        super(inPathStat, inPath, outPath);
    }

    public DeCryptor(String inPath, String outPath) {
        super(inPath, outPath);
    }

    @Override
    public String deCrypt() throws IOException {
        char[] buffer = readInBuffer(getInPath());
        buffer = getLeftOffsetBuffer(buffer, getKey());
        return writeIntoFile(buffer);
    }
}
