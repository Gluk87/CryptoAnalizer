import decryptor.BruteForce;
import decryptor.DeCryptor;
import decryptor.StatAnalyze;
import encryptor.EnCryptor;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;

import static system.Crypto.FILE_CREATED;

public class JUnitTest {
    @Test
    public void testEnCryptWithKey() throws IOException {
        int key = 10;
        Path inPath = Paths.get("src\\test\\resources\\Original.txt").toAbsolutePath();
        Path outPath = Paths.get("src\\test\\resources\\Decrypted.txt").toAbsolutePath();
        EnCryptor enCryptor = new EnCryptor(key, String.valueOf(inPath), String.valueOf(outPath));
        assertEquals(enCryptor.enCrypt(), FILE_CREATED);
    }

    @Test
    public void testDeCryptWithKey() throws IOException {
        int key = 10;
        Path inPath = Paths.get("src\\test\\resources\\Decrypted.txt").toAbsolutePath();
        Path outPath = Paths.get("src\\test\\resources\\Encrypted.txt").toAbsolutePath();
        DeCryptor deCryptor = new DeCryptor(key, String.valueOf(inPath), String.valueOf(outPath));
        assertEquals(deCryptor.deCrypt(), FILE_CREATED);
    }

    @Test
    public void testDeCryptBruteForce() throws IOException {
        Path inPath = Paths.get("src\\test\\resources\\Decrypted.txt").toAbsolutePath();
        Path outPath = Paths.get("src\\test\\resources\\Encrypted.txt").toAbsolutePath();
        BruteForce bruteForce = new BruteForce(String.valueOf(inPath), String.valueOf(outPath));
        int key = bruteForce.findKey();
        bruteForce.setKey(key);
        assertEquals(bruteForce.deCrypt(), FILE_CREATED);
    }

    @Test
    public void testDeCryptStatAnalyze() throws IOException {
        Path inPathStat = Paths.get("src\\test\\resources\\Statanalyze.txt").toAbsolutePath();
        Path inPath = Paths.get("src\\test\\resources\\Decrypted.txt").toAbsolutePath();
        Path outPath = Paths.get("src\\test\\resources\\Encrypted.txt").toAbsolutePath();
        StatAnalyze statAnalyze = new StatAnalyze(String.valueOf(inPathStat), String.valueOf(inPath), String.valueOf(outPath));
        int key = statAnalyze.findKey();
        statAnalyze.setKey(key);
        assertEquals(statAnalyze.deCrypt(), FILE_CREATED);
    }
}
