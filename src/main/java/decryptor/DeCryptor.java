package decryptor;

import system.Crypto;

import java.io.FileWriter;
import java.io.IOException;

public class DeCryptor extends Crypto {

    public DeCryptor(int key, String inPath, String outPath) {
        super(key, inPath, outPath);
    }

    public DeCryptor(String inPathStat, String inPath, String outPath) {
        super(inPathStat, inPath, outPath);
    }

    public DeCryptor(String inPath, String outPath) {
        super(inPath, outPath);
    }

    public String deCrypt() throws IOException {
        try(FileWriter writer = new FileWriter(getOutPath())) {
            char[] buffer = readInBuffer(getInPath());
            writer.write(getLeftOffsetBuffer(buffer, getKey()));
        }
        return FILE_CREATED;
    }
}
