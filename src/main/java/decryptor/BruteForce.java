package decryptor;

import system.KeyNotFoundException;

import java.io.IOException;

/**
 * Данный класс предназначен для дешифровки методом Brute Force
 * В методе findKey() происходит анализ текста для каждого ключа.
 * Если в расшифрованном тексте стоит знак препинания (, . : ? !) и следующий символ разделитель (пробел, tab, перенос строки),
 * то score увеличивается на 1. Также, если символ в верхнем регистре, а предыдущий разделитель (пробел, tab),
 * то score также увеличивается на 1. А если после знака препинания (, . : ? !) нет разделителя, то score уменьшается.
 * Ключ с максимальным score будет возвращен.
 */
public class BruteForce extends DeCryptor implements Key {

    public BruteForce(String inPath, String outPath) throws IOException {
        super(inPath, outPath);
    }

    @Override
    public int findKey() throws IOException {
        char[] bufferOrig = readInBuffer(getInPath());
        char[] bufferShift;
        int key = -1;
        int maxScore = 0;
        for (int k = MIN_KEY; k <= MAX_KEY ; k++) {
            int score = 0;
            bufferShift = getLeftOffsetBuffer(bufferOrig, k);
            for (int i = 0; i < bufferShift.length-1; i++) {
                if ((bufferShift[i] == ',' || bufferShift[i] == '.' || bufferShift[i] == ':' || bufferShift[i] == '?' || bufferShift[i] == '!')
                        && (Character.isSpaceChar(bufferShift[i+1]) || Character.isWhitespace(bufferShift[i+1]))) {
                    score++;
                } else if (Character.isUpperCase(bufferShift[i+1]) && Character.isWhitespace(bufferShift[i])) {
                    score++;
                } else if ((bufferShift[i] == ',' || bufferShift[i] == '.' || bufferShift[i] == ':' || bufferShift[i] == '?' || bufferShift[i] == '!')
                        && (!Character.isSpaceChar(bufferShift[i+1]) || !Character.isWhitespace(bufferShift[i+1]))) {
                    score--;
                }
            }
            if (maxScore < score){
                maxScore = score;
                key = k;
            }
        }
        if (key == -1) {
            throw new KeyNotFoundException();
        }
        return key;
    }
}

