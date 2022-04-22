package system;

import decryptor.BruteForce;
import decryptor.DeCryptor;
import decryptor.StatAnalyze;
import encryptor.EnCryptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UserInterface extends JFrame {

    private static final String CHOOSE_IN_FILE = "Выбрать исходный файл";
    private static final String CHOOSE_OUT_FILE = "Сохранить в новый файл";
    private static final String CHOOSE_FILE_STAT = "Выбрать файл для стат анализа";
    private static final String CHOOSE_DIR = "Выбор директории";
    private static final String SAVE_FILE = "Сохранение файла";
    private static final String ENTER_KEY = "Введите ключ (от " + Crypto.MIN_KEY +" до " + Crypto.MAX_KEY +"):";
    private static final String RADIO_ENCRYPT = "Шифрование";
    private static final String RADIO_DECRYPT_KEY = "Расшифровка с ключем";
    private static final String RADIO_DECRYPT_BRUTEFORCE = "Расшифровка brute force";
    private static final String RADIO_DECRYPT_STAT = "Расшифровка статистическим анализом";
    private static final String START = "Запуск";
    private static final String EXC_KEY_NUMBER = "Введите корректный ключ от " + Crypto.MIN_KEY +" до " + Crypto.MAX_KEY;
    private static final String EXC_FILE_NOT_FOUND = "Файл не найден";
    private static final String EXC_KEY_NOT_FOUND = "Ключ не удалось подобрать";
    private static final String EXC_IN_OUT = "Ошибка ввода вывода";
    private static final String EXC_UNKNOWN = "Неизвестная ошибка";

    private final JFileChooser fileChooser = new JFileChooser();
    private final JTextField inDir = new JTextField ();
    private final JTextField outDir = new JTextField ();
    private final JLabel label = new JLabel(ENTER_KEY);
    private final JTextField textKey = new JTextField ();
    private final JRadioButton radioButton1 = new JRadioButton(RADIO_ENCRYPT);
    private final JRadioButton radioButton2 = new JRadioButton(RADIO_DECRYPT_KEY);
    private final JRadioButton radioButton3 = new JRadioButton(RADIO_DECRYPT_BRUTEFORCE);
    private final JRadioButton radioButton4 = new JRadioButton(RADIO_DECRYPT_STAT);
    private final JButton btnOpenDirStat = new JButton(CHOOSE_FILE_STAT);
    private final JTextField inDirStat = new JTextField ();
    private final JLabel msgLabel = new JLabel();

    public UserInterface(){
        super("Криптоанализатор");
        this.setBounds(200, 200, 600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(10, 2, 10, 10));

        JButton btnOpenDir = new JButton(CHOOSE_IN_FILE);
        btnOpenDir.addActionListener(new FileChooserListener());
        container.add(btnOpenDir);
        container.add(inDir);
        JButton btnSaveFile = new JButton(CHOOSE_OUT_FILE);
        btnSaveFile.addActionListener(new SaveFileListener());
        container.add(btnSaveFile);
        container.add(outDir);
        container.add(label);
        container.add(textKey);
        radioButton1.addActionListener(new RadioListener());
        radioButton2.addActionListener(new RadioListener());
        radioButton3.addActionListener(new RadioListener());
        radioButton4.addActionListener(new RadioListener());

        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
        group.add(radioButton3);
        group.add(radioButton4);

        container.add(radioButton1);
        radioButton1.setSelected(true);
        container.add(radioButton2);
        container.add(radioButton3);
        container.add(radioButton4);

        container.add(btnOpenDirStat);
        container.add(inDirStat);
        btnOpenDirStat.addActionListener(new FileStatChooserListener());
        btnOpenDirStat.setVisible(false);
        inDirStat.setVisible(false);

        JButton button = new JButton(START);
        button.addActionListener(new ButtonEventListener());
        container.add(button);
        container.add(msgLabel);
    }

    class FileChooserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle(CHOOSE_DIR);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fileChooser.showOpenDialog(UserInterface.this);
            if (result == JFileChooser.APPROVE_OPTION ) {
                inDir.setText(String.valueOf(fileChooser.getSelectedFile()));
            }
        }
    }

    class FileStatChooserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle(CHOOSE_DIR);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fileChooser.showOpenDialog(UserInterface.this);
            if (result == JFileChooser.APPROVE_OPTION ) {
                inDirStat.setText(String.valueOf(fileChooser.getSelectedFile()));
            }
        }
    }

    class SaveFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle(SAVE_FILE);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showSaveDialog(UserInterface.this);
            if (result == JFileChooser.APPROVE_OPTION ) {
                outDir.setText(String.valueOf(fileChooser.getSelectedFile()));
            }
        }
    }

    class RadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (radioButton1.isSelected() || radioButton2.isSelected()){
                label.setVisible(true);
                textKey.setVisible(true);
                btnOpenDirStat.setVisible(false);
                inDirStat.setVisible(false);
            } else if (radioButton3.isSelected()) {
                label.setVisible(false);
                textKey.setVisible(false);
                btnOpenDirStat.setVisible(false);
                inDirStat.setVisible(false);
            } else if (radioButton4.isSelected()){
                label.setVisible(false);
                textKey.setVisible(false);
                btnOpenDirStat.setVisible(true);
                inDirStat.setVisible(true);
            }
        }
    }

    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            try {
                if (radioButton1.isSelected()) {
                    int key = Integer.parseInt(textKey.getText());
                    EnCryptor enCryptor = new EnCryptor(checkKey(key), inDir.getText(), outDir.getText());
                    msgLabel.setText(enCryptor.enCrypt());
                } else if (radioButton2.isSelected()) {
                    int key = Integer.parseInt(textKey.getText());
                    DeCryptor deCryptor = new DeCryptor(checkKey(key), inDir.getText(), outDir.getText());
                    msgLabel.setText(deCryptor.deCrypt());
                } else if (radioButton3.isSelected()) {
                    BruteForce bruteForce = new BruteForce(inDir.getText(), outDir.getText());
                    int key = bruteForce.findKey();
                    bruteForce.setKey(checkKey(key));
                    msgLabel.setText(bruteForce.deCrypt());
                } else if (radioButton4.isSelected()) {
                    StatAnalyze statAnalyze = new StatAnalyze(inDirStat.getText(), inDir.getText(), outDir.getText());
                    int key = statAnalyze.findKey();
                    statAnalyze.setKey(checkKey(key));
                    msgLabel.setText(statAnalyze.deCrypt());
                }
            } catch (NumberFormatException exp) {
                msgLabel.setText(EXC_KEY_NUMBER);
                exp.printStackTrace();
            } catch (FileNotFoundException exp) {
                msgLabel.setText(EXC_FILE_NOT_FOUND);
                exp.printStackTrace();
            } catch (KeyNotFoundException exp) {
                msgLabel.setText(EXC_KEY_NOT_FOUND);
                exp.printStackTrace();
            }catch (IOException exp) {
                msgLabel.setText(EXC_IN_OUT);
                exp.printStackTrace();
            } catch (RuntimeException exp) {
                msgLabel.setText(EXC_UNKNOWN);
                exp.printStackTrace();
            }
        }

        private int checkKey (int key) {
            if (key < Crypto.MIN_KEY || key > Crypto.MAX_KEY) {
                throw new NumberFormatException();
            }
            return key;
        }
    }


}

