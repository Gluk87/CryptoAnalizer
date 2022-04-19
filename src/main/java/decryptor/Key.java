package decryptor;

import java.io.IOException;

public interface Key {
    int findKey() throws IOException;
}