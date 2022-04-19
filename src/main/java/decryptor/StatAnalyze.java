package decryptor;

import system.Crypto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Данный класс предназначен для дешифровки методом статистического анализа
 * В методе findKey() происходит подсчет символов для каждого ключа. Символ и его количество помещаем в HashMap.
 * Сравниваем разницу количества символов с текстом, предоставленным для стат. анализа.
 * Минимальная разница в символах будет соответствовать нужному ключу
 */
public class StatAnalyze extends DeCryptor implements Key {

    public StatAnalyze(String inPathStat, String inPath, String outPath) throws IOException {
        super(inPathStat, inPath, outPath);
    }

    @Override
    public int findKey() throws IOException {
        HashMap<Character, Integer> mapOriginal = new HashMap<>(getMapSymbolCount(getInPathStat(), 0));
        HashMap<Character, Integer> mapDist;
        int minSum = 0;
        int key = -1;
        for (int i = Crypto.MIN_KEY; i <= Crypto.MAX_KEY ; i++) {
            int sum = 0;
            // Создаем вторую мапу для шифрованного файла для каждого ключа
            mapDist = new HashMap<>(getMapSymbolCount(getInPath(), i));
            // Перебираем символы в 2 мапах и записываем разницу в количестве символов
            for(Character symbolOrig : mapOriginal.keySet()){
                for(Character symbolDist : mapDist.keySet()){
                    if (symbolOrig.equals(symbolDist)) {
                        int diff = Math.abs(mapOriginal.get(symbolOrig) - mapDist.get(symbolDist));
                        sum = sum + diff;
                    }
                }
            }
            if (i == Crypto.MIN_KEY){
                minSum = sum;
                key = i;
            }
            if (minSum > sum) {
                minSum = sum;
                key = i;
            }
        }
        return key;
    }

    private HashMap<Character, Integer> getMapSymbolCount(String path, int key) throws IOException {
        char[] buffer = readInBuffer(path);
        buffer = getLeftOffsetBuffer(buffer, key);
        ArrayList<Character> arrayList = new ArrayList<>();
        for (char value : buffer) {
            arrayList.add(value);
        }
        // записываем в мапу символ + количество
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : Crypto.ALPHABET) {
            map.put(c, Collections.frequency(arrayList, c));
        }
        return map;
    }
}
