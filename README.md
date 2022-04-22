# Задача
Написать программу, которая работает с шифром Цезаря. За основу криптографического алфавита возьми все буквы русского алфавита и знаки пунктуации (. , ”” : - ! ? ПРОБЕЛ). Если попадаются символы, которые не входят в наш криптографический алфавит, просто пропусти их.

# Требования
- Шифрование / расшифровка. Программа должна зашифровывать и расшифровывать текст, используя заданный криптографический ключ.
- Криптоанализ методом brute force. Если пользователь выбирает brute force (брутфорс, поиск грубой силой), программа должна самостоятельно, путем перебора, подобрать ключ и расшифровать текст.
- Криптоанализ методом статистического анализа. Если пользователь выбирает метод статистического анализа, ему нужно предложить загрузить еще один дополнительный файл с текстом, желательно— того же автора и той же стилистики. На основе загруженного файла программа должна составить статистику вхождения символов и после этого попытаться использовать полученную статистику для криптоанализа зашифрованного текста.
- User Interface. Все диалоговые окна с пользователем делай на свое усмотрение. При желании можно использовать графические фреймворки Swing, JavaFX.

# Запуск программы
Программу запустить можно через файл CryptoAnalizer.exe.

Или же с помощью CryptoAnalizer.jar через команду javaw.exe -jar CryptoAnalizer.jar. 

Внимание! Программа компилировалась на 17 версии Java.

# Интерфейс программы

![alt text](https://github.com/Gluk87/CryptoAnalizer/blob/main/src/test/resources/Screen1.png)

![alt text](https://github.com/Gluk87/CryptoAnalizer/blob/main/src/test/resources/Screen2.png)

![alt text](https://github.com/Gluk87/CryptoAnalizer/blob/main/src/test/resources/Screen3.png)

# Диаграмма классов

![alt text](https://github.com/Gluk87/CryptoAnalizer/blob/main/src/test/resources/UML.png)

# Тесты
Для тестов использовал JUnit

![alt text](https://github.com/Gluk87/CryptoAnalizer/blob/main/src/test/resources/Screen4.png)
