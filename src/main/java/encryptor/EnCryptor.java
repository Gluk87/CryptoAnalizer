package encryptor;

import system.Crypto;

import java.io.FileWriter;
import java.io.IOException;

public class EnCryptor extends Crypto {

    public EnCryptor(int key, String inPath, String outPath) {
        super(key, inPath, outPath);
    }

    public String enCrypt() throws IOException {
        try(FileWriter writer = new FileWriter(getOutPath())) {
            char[] buffer = readInBuffer(getInPath());
            writer.write(getRightOffsetBuffer(buffer, getKey()));
        }
        return FILE_CREATED;
    }
}
