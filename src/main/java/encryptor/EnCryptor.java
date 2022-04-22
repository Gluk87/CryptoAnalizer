package encryptor;

import system.Crypto;
import java.io.IOException;

public class EnCryptor extends Crypto implements Encryptable {

    public EnCryptor(int key, String inPath, String outPath) {
        super(key, inPath, outPath);
    }

    @Override
    public String enCrypt() throws IOException {
        char[] buffer = readInBuffer(getInPath());
        buffer = getRightOffsetBuffer(buffer, getKey());
        return writeIntoFile(buffer);
    }
}
