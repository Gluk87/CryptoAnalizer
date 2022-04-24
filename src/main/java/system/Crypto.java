package system;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class Crypto {

    public static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
            'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
            'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
            'э', 'ю', 'я', '.', ',', '"', ':', '-', '!','?', ' '};

    public static final int MIN_KEY = 1;
    public static final int MAX_KEY = ALPHABET.length - 1;
    public static final String FILE_CREATED = "Файл создан";

    private int key;
    private final String inPath;
    private final String outPath;
    private String inPathStat;

    public Crypto(String inPath, String outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
    }

    public Crypto(int key, String inPath, String outPath) {
        this.key = key;
        this.inPath = inPath;
        this.outPath = outPath;
    }

    public Crypto(String inPathStat, String inPath, String outPath) {
        this.inPathStat = inPathStat;
        this.inPath = inPath;
        this.outPath = outPath;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getInPath() {
        return inPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public String getInPathStat() {
        return inPathStat;
    }

    protected char[] readInBuffer(String path) throws IOException {
        ByteBuffer byteBuffer;
        String stringBuffer;
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(path, "r");
            FileChannel channel = randomAccessFile.getChannel()) {
            byteBuffer = ByteBuffer.allocate((int) channel.size());
            channel.read(byteBuffer);
            StandardCharsets.UTF_8.decode(byteBuffer);
            byteBuffer.flip();
            stringBuffer = new String(byteBuffer.array(), StandardCharsets.UTF_8);
        }
        return stringBuffer.toCharArray();
    }

    protected String writeIntoFile(char[] buffer) throws IOException {
        CharBuffer charBuffer;
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(getOutPath(), "rw");
            FileChannel channel = randomAccessFile.getChannel()) {
            charBuffer = CharBuffer.allocate(buffer.length);
            charBuffer.put(buffer);
            charBuffer.flip();
            channel.write(ByteBuffer.wrap(new String(buffer).getBytes(StandardCharsets.UTF_8)));
        }
        return FILE_CREATED;
    }

    protected char[] getLeftOffsetBuffer(char[] buffer, int key) {
        char[] modifiedBuffer = Arrays.copyOfRange(buffer, 0, buffer.length);
        for (int i = 0; i < modifiedBuffer.length; i++) {
            for (int j = 0; j < ALPHABET.length; j++) {
                if (Character.toLowerCase(modifiedBuffer[i]) == ALPHABET[j]) {
                    if ((j - key >= 0) && (Character.isUpperCase(modifiedBuffer[i]))) {
                        modifiedBuffer[i] = Character.toUpperCase(ALPHABET[j - key]);
                    } else if (j - key >= 0) {
                        modifiedBuffer[i] = ALPHABET[j - key];
                    } else if ((j - key < 0) && (Character.isUpperCase(modifiedBuffer[i]))) {
                        modifiedBuffer[i] = Character.toUpperCase(ALPHABET[ALPHABET.length + j - key]);
                    } else if (j - key < 0) {
                        modifiedBuffer[i] = ALPHABET[ALPHABET.length + j - key];
                    }
                    break;
                }
            }
        }
        return modifiedBuffer;
    }

    protected char[] getRightOffsetBuffer(char[] buffer, int key) {
        char[] modifiedBuffer = Arrays.copyOfRange(buffer, 0, buffer.length);
        for (int i = 0; i < modifiedBuffer.length; i++) {
            for (int j = 0; j < ALPHABET.length; j++) {
                if (Character.toLowerCase(modifiedBuffer[i]) == ALPHABET[j]) {
                    if ((j + key < ALPHABET.length) && (Character.isUpperCase(modifiedBuffer[i]))) {
                        modifiedBuffer[i] = Character.toUpperCase(ALPHABET[j + key]);
                    } else if (j + key < ALPHABET.length) {
                        modifiedBuffer[i] = ALPHABET[j + key];
                    } else if ((j + key >= ALPHABET.length) && (Character.isUpperCase(modifiedBuffer[i]))) {
                        modifiedBuffer[i] = Character.toUpperCase(ALPHABET[j + key - ALPHABET.length]);
                    } else if (j + key >= ALPHABET.length) {
                        modifiedBuffer[i] = ALPHABET[j + key - ALPHABET.length];
                    }
                    break;
                }
            }
        }
        return modifiedBuffer;
    }
}
