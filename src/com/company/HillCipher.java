package com.company;


import java.text.Normalizer;


public class HillCipher {

    char[] Alphabet = new char[26];
    //. Ключ используемый при шифровании
    int[][] Key;
    //. Количество столбцов и колонок
    int N;
    String PlainText;


    /**
     * Получить ключ шифрования
     * @return Key
     */
    public int[][] getKey() {
        return Key;
    }

    /**
     *Установить ключ шифрования
     * @param key
     */
    public void setKey(int[][] key) {
        Key = key;
    }


    /**
     * Возращает размерность матрицы как nxn
     * @return N
     */
    public int getN() {
        return N;
    }

    /**
     * Устанавливает размерность матрицы
     * @param n
     */
    public void setN(int n) {
        N = n;
    }

    /**
     * Returns the plain text value for encryption
     * @return PlainText
     */
    public String getPlainText() {
        return PlainText;
    }

    /**
     * Устанавливает текст, который должен быть зашифрован.
     * И заполняет пустые места в соответсвии с x
     * Нормализует строку и удаляет все специальные символы
     * @param plainText
     */
    public void setPlainText(String plainText) {
        PlainText = Normalizer.normalize(plainText.toLowerCase(),
                Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");;
        FillString();
    }

    /**
     * Конструктор
     * @param key ключ шифрования
     * @param n nxn размерность матрицы.
     * @param plainText все специальные символы будут удалены
     */
    public HillCipher(int[][] key, int n, String plainText) {
        Key = key;
        N = n;
        PlainText = Normalizer.normalize(plainText.toLowerCase(),
                Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");

        InitializeAlphabet();

        FillString();
    }

    /**
     * Конструктор
     * Без установки текста
     * @param key
     * @param n
     */
    public HillCipher(int[][] key, int n) {
        Key = key;
        N = n;
        PlainText = "";
        InitializeAlphabet();
    }

    /**
     * Инициализация алфавита с помощью ASCII кодировки
     */
    private void InitializeAlphabet() {
        for (int i = 0; i < 26; i++) {
            Alphabet[i] = (char) (97 + i);
        }
    }

    /**
     * Заполняет текст строкой с x в соответствии с матрицей
     */
    private void FillString() {

        while (PlainText.length() % N != 0)
            PlainText += 'x';
    }

    //. ----------------Начало шифрования------------------------------.//

    /**
     * Конвертация числа в символ по модулю 26 (a-z)
     * @param y
     * @return ASCII Символ a-z
     */
    public char NumberToCharacter(int y) {
        int x = y;
        int t = 0;

        if (y > 25) {
            x /= 26;
            x *= 26;
            t = y - x;
        } else
            t = y;

        return Alphabet[t];
    }

    /**
     * Конвертация символа в число (0-25)
     * @param c
     * @return A число 0-25
     */
    public int CharToNumber(char c) {
        for (int i = 0; i < Alphabet.length; i++) {
            if (c == Alphabet[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Шифрует текст в соответсвии с ключем
     * @param key
     * @return зашифрованная строка
     */
    public String Encrypt(int[][] key) {
        String encryption = "";

        //. Берет блоки текста в соответствии с N
        for (int sIndex = 0; sIndex < PlainText.length(); sIndex += N) {
            String temp = PlainText.substring(sIndex, sIndex + N);

            //. Для каждой строки в ключе матрицы
            for (int r = 0; r < N; r++) {

                //. Новый зашифрованный блок размера N
                int[] block = new int[N];

                //. Для каждого столбца в ключе матрицы
                for (int c = 0; c < N; c++) {
                    //. Установка значений символов в качестве чисел
                    block[c] = key[r][c] * CharToNumber(temp.charAt(c));
                }

                //. Матан для получения зашифрованный символов из блока
                int sum = 0;
                for (int i : block) {
                    sum += i;
                }

                //. Добавление символа в зашифрованную строку
                encryption += NumberToCharacter(sum);
            }

        }

        return encryption;
    }

    // . ------------------------------------Начало расшифровки-------------------------------------.//
    /**
     * Получает остаток наоборот от определителя
     * @param a
     * @param m
     * @return остаток наоборот
     */
    public int ModInverse(int a, int m) {
        a %= m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }

        return -1;
    }

    /**
     * Определитель матрицы
     * @param matrix
     * @return значение определителя матрицы
     */
    public int Determinant(int[][] matrix)  {
        int sum=0;
        int s;
        if(matrix.length==1){
            return(matrix[0][0]);
        }
        for(int i=0;i<matrix.length;i++){
            int[][]smaller= new int[matrix.length-1][matrix.length-1];
            for(int a=1;a<matrix.length;a++){
                for(int b=0;b<matrix.length;b++){
                    if(b<i){
                        smaller[a-1][b]=matrix[a][b];
                    }
                    else if(b>i){
                        smaller[a-1][b-1]=matrix[a][b];
                    }
                }
            }
            if(i%2==0){
                s=1;
            }
            else{
                s=-1;
            }
            sum+=s*matrix[0][i]*(Determinant(smaller));
        }
        return(sum);
    }

    /**
     * Получение ключа расшифрования из оригинального ключа шифрования
     * @return новый ключ
     */
    public int[][] getDecryptionKey() {
        int[][] newKey = Key;
        if (N == 2) {
            int first = newKey[0][0];
            int last = newKey[1][1];
            newKey[0][0] = last;
            newKey[1][1] = first;
            newKey[0][1] *= -1;
            newKey[1][0] *= -1;
        }

        int[][] decKey = new int[N][N];

        int a = ModInverse(Determinant(Key) , 26);

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int temp = 0;
                int x = (temp = newKey[r][c] * a);
                if (x >= 0) {
                    temp /= 26;
                    temp *= 26;
                    decKey[r][c] = x - temp;
                }
                else {
                    int mod = 26;
                    while (-1 * x > mod) {
                        mod += 26;
                    }

                    decKey[r][c] = x + mod;
                }
            }

        }

        return decKey;
    }

    /**
     * Расшифровка текст в соответствии с приоритетом состояния кодировки
     * @param key
     * @param encryption
     * @return расшифрованная строка
     */
    public String Decrypt(int[][] key , String encryption) {
        String temp = PlainText;
        PlainText = encryption;
        String result = Encrypt(key);
        PlainText = temp;

        return result;
    }
}
