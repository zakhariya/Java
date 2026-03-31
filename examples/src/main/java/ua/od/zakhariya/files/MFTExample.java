package ua.od.zakhariya.files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MFTExample {

    public static void main(String[] args) throws IOException {
        new MFTExample().readFirstRecord();
    }

    /*
    * Права: Запуск только от имени Администратора.
Смещение: MFT находится не в начале диска; нужно читать Boot Sector (первые 512 байт), чтобы найти кластер $MFT.
Структура: Записи MFT имеют сложную структуру с атрибутами (заголовки, размер, смещения), которые нужно парсить вручную.
Для полноценной работы часто используют JNA (Java Native Access) для вызова CreateFile и ReadFile WinAPI.
    * */
    private void readFirstRecord() throws IOException {
        // Требуются права администратора!
        String disk = "\\\\.\\C:";
        try (RandomAccessFile mftFile = new RandomAccessFile(disk, "r")) {
            // ВАЖНО: Требуется предварительно найти смещение $MFT через Boot Sector
            // Для примера используем заглушку смещения
            long mftOffset = 0;

            mftFile.seek(mftOffset);
            byte[] record = new byte[1024]; // Размер записи MFT
            mftFile.readFully(record);

            // Проверка сигнатуры "FILE"
            if (record[0] == 0x46 && record[1] == 0x49 && record[2] == 0x4C && record[3] == 0x45) {
                System.out.println("MFT Record Found");
                // Далее необходимо парсить байты согласно спецификации NTFS
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readBytes() throws IOException {
        // Требуются права администратора!
        String disk = "\\\\.\\C:";
        try (RandomAccessFile mftFile = new RandomAccessFile(disk, "r")) {
            // ВАЖНО: Требуется предварительно найти смещение $MFT через Boot Sector
            // Для примера используем заглушку смещения
            long mftOffset = 0x000000;

            mftFile.seek(mftOffset);
            byte[] record = new byte[1024]; // Размер записи MFT

            for (int i = 0; i < 100; i++) {
                mftFile.readFully(record);

                //Анализ сигнатуры "FILE" (46 49 4C 45)
                if (record[0] == 'F' && record[1] == 'I' && record[2] == 'L' && record[3] == 'E') {
                    System.out.println("Найдена запись MFT №" + i);
                    // Здесь должен быть парсинг атрибутов
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
