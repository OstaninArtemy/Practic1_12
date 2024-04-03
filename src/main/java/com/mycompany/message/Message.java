package com.mycompany.message;
import com.mirea.kt.example.MessageGo;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;

public class Message {
    public static void main(String[] args) {
        BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Путь к файлу:");
        String PityKfile;
        try {
            PityKfile = data.readLine();
        } catch (IOException x) {
            System.err.println("Ошибка ввода: " + x.getMessage());
            return;
        }
        File file = new File(PityKfile);
        if (!file.exists() || !file.isFile()) {
            System.err.println("Указанный файл не найден!");
            return;
        }
        new Thread(() -> { // создание нового потока с использование пакета
            //com.mirea.kt.example.MessageGo где есть класс MessageGo
            try (ObjectInputStream f = new ObjectInputStream(new FileInputStream(file))) {
                long contVersion = f.readLong();
                if (contVersion != -3380157693869190848L) {
                    System.err.println("Версия файла не подходит!");
                    return;
                }
                MessageGo message = (MessageGo) f.readObject();
                System.out.println("id:"+message.getId());
                System.out.println("body:"+message.getBody());
                System.out.println("type:"+message.getType());
                System.out.println("hasAttachments:"+message.isHasAttachments());
                System.out.println("timestamp:"+message.getTimestamp());
            } catch (IOException | ClassNotFoundException x) {
                System.err.println("Ошибка десериализации:" + x.getMessage());
            }
        }).start();
    }
}
