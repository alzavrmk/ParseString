// Иванов Иван Иванович 22.06.1995 89143646548 m
import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        Scanner iScanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите данные одной строкой в формате: " +
                    "Фамилия Имя Отчество дата рождения(dd.mm.yyyy) номер телефона пол(f или m):");
            String strIn = iScanner.nextLine();
            int indexNum = indexOfNumber(strIn);
            if (indexNum == 1) {
                StringBuilder stringOut= strOut(strIn);
                System.out.println(stringOut);
                String filename = strIn.split(" ")[0];

                try {
                    if (! fileReader(filename)){
                        writerNewFileFamily(filename);
                        writerFileInfo(filename,stringOut);
                        System.out.println("Информация записана в новый файл "+filename+".txt");
                    } else {
                        appendFileInfo(filename, stringOut);
                        System.out.println("Информация добавлена в файл "+filename+".txt");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            } else{
                System.out.printf("код ошибки: %d. ", indexNum);
                decoder(indexNum);
            }
        }
        // iScanner.close();

    }
    public static int indexOfNumber(String strIn) {
        int countWords = 6;
        String [] words= strIn.split (" ");
        System.out.println(words[0]);
        if (words.length < countWords){
            return -1;
        }
        if (words.length > countWords){
            return -2;
        }
        return 1;
    }

    public static void decoder(int code) {
        switch (code) {
            case -1:
                System.out.println("Введены не все данные!");
                break;
            case -2:
                System.out.println("Введенна излишняя информация!");
                break;
        }
    }

    static StringBuilder strOut(String strIn){
        String [] words= strIn. split (" ");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            str.append(words[i]+" ");
        }
        String[] numData = words[3].split("\\.");
        if ((parseInt(numData[0]) > 0) && (parseInt(numData[0]) < 32) &&
                (parseInt(numData[1]) > 0) && (parseInt(numData[1]) < 13) &&
                ((parseInt(numData[2]) > 1900) && (parseInt(numData[0]) <  Year.now().getValue()))){
            str.append(numData[0]);
            str.append(".");
            str.append(numData[1]);
            str.append(".");
            str.append(numData[2]+" ");
        }else {
            throw new IllegalArgumentException("Ошибка в дате рождения");
        }
        if (words[4].matches("[0-9]{11}")){
            str.append(words[4]+" ");}
        else {
            throw new IllegalArgumentException("Неверно введен номер телефона!");
        }
        if (words[5].equals("f") || words[5].equals("m")){
            str.append(words[5]);
        }
        else {
            throw new IllegalArgumentException("Неверно введен пол!");
        }
        return str;
    }

    // Метод создает новый файл с именем filename и записывает в него строку strOut
    static void writerFileInfo(String filename, StringBuilder strOut) {

        try (FileWriter fw = new FileWriter(filename+".txt")){
            fw.append(strOut+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Метод добавляет данные строку strOut в файл с именем filename
    static void appendFileInfo(String filename,StringBuilder strOut) {
        try (FileWriter fw = new FileWriter(filename+".txt", true)) {
            fw.append(strOut+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод добавляет  фамилию  family в файл family.txt
    static void writerNewFileFamily(String family) {

        try (FileWriter fw = new FileWriter("family.txt", true)){
            fw.append(family+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод читает файл family.txt и возвращает true, если в нем есть строка равная строке family
    static boolean fileReader(String family) {
        boolean flag = false;
        try (FileReader fr = new FileReader(new File("family.txt"));
             BufferedReader reader = new BufferedReader(fr);) {
            String line;

            while ((line = reader.readLine()) != null){
                if (family.equals(line)){
                    flag = true;
                    break;
                }else {
                    flag = false;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}