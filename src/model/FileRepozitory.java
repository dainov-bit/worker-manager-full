package model;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Stream;

import model.Repozitory;
import view.Context;
import control.IdComparator;

/**
 * Класс для ошибки при открытии и обработки csv файла
 */
class CSVException extends Exception {
    public CSVException(String message) {
        super(message);
    }
}

/**
 * Класс, который выполняет заполнение колекции, а так же сохранение ее в файл.
 */
public class FileRepozitory {
    private String file; // путь к файлу
    private Repozitory repo; // репозиторий

    public FileRepozitory(String f, Repozitory r) {
        this.file = f;
        this.repo = r;
    }

    /**
     * метод, который разберает строку на столбцы
     *
     * @param nextLine строка     * @throws CSVException
     */
    private void modelObject(String nextLine) throws CSVException {
        String[] column = nextLine.split(",");
        if (column.length != 4)
            throw new CSVException("Ошибка! В строке допущена ошибка.\nВозможно вы не поставили запятую между столбцами.\nПожалуйста проверьте файл csv на ошибки и если они есть, тогда исправьте их.");
        int id = 0;
        try {
            id = Integer.parseInt(column[0]);
        } catch (Exception e) {
            throw new CSVException("Внимание! Не удалось преобразовать первый столбец в число.\nПервый столбец всегда имеет id пользователя.");
        }
        String name = column[1];
        double salary;
        try {
            salary = Double.parseDouble(column[2]);
        } catch (Exception e) {
            throw new CSVException("Неудалось преобразовать третий столбец в десятичное число.\n Третий столбец должен содержать зарплату.");
        }
        Status s = null;
        int si = -1;
        int status_worker;
        try {
            status_worker = Integer.parseInt(column[3]);
        } catch (Exception e) {
            throw new CSVException("Внимание! Четвертый столбец поврежден.\nОн должен содержать число, которое указывает профессию работника");
        }
        for (Status sObj : Status.values()) {
            si++;
            if (si == status_worker) {
                s = sObj;
            }
        }
        this.repo.add(name, salary, s, id);
    }

    /**
     * Метод для загрузки данных в коллекцию
     */
    public void loader() {
        try (BufferedReader fr = new BufferedReader(new FileReader(this.file))) {
            String line = fr.readLine();
            int i = 0;
            while (line != null) {
                if (line.length() != 0) {
                    i++;
                    try {
                        this.modelObject(line);
                    } catch (CSVException e) {
                        System.out.println("Строка №" + i + ". - " + e.getMessage());
                        System.exit(0);
                    }
                }
                line = fr.readLine();
            }

        } catch (Exception e) {
            System.out.println("Отсутствует файл csv.\nПрограмма экстренно остановлена.");
            System.exit(0);
        }
    }

    /**
     * Метод для выгрузки данных в файл
     */
    public void save() {
        ArrayList<Worker> a = this.repo.hashSetToArrayList();
        Collections.sort(a, new IdComparator());
        String str = "";
        for (Worker w : a) {
            str += w.getId() + "," + w.getName() + "," + w.getSalary() + "," + w.getStatusForEdit().ordinal() + "\n";
        }
        try (FileOutputStream foi = new FileOutputStream(this.file)) {
            byte[] bt = str.getBytes();
            foi.write(bt, 0, bt.length);
        } catch (Exception e) {
            Context.worker_error = "Не могу сохранить данные";
        }
    }


}
