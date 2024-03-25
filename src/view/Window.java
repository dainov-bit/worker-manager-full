package view;

import java.util.Iterator;
import java.util.Scanner;

import control.*;

/**
 * Класс, который является view.
 * Он выводит информационные методы, а те вызывают методы контролера.
 */
public class Window {
    private Scanner scanner; // для сканера
    private Info info; // информационный класс
    private Control control; // класс контролер

    public Window(String fileCsv) {
        this.scanner = new Scanner(System.in, "utf-8");
        this.control = new Control(fileCsv); // в контролер передали путь к файлу data.csv
        this.info = new Info(); // Класс от Дарьи.
    }

    /**
     * Информационный диалог для регистрации нового работника
     */
    public void newWorker() {
        Context.getError(); // Метод, который выводит сообщение о ошибках.
        if (Context.worker_name.length() == 0) {
            System.out.println("Пожалуйста введите имя");
        } else if (Context.worker_status == null) {
            System.out.println("Отлично! Нового работника звать " + Context.worker_name + "\n");
            this.control.listStatus(); // Список профессий
        } else if (Context.worker_salary == 0) {
            System.out.println("Имя: " + Context.worker_name + "\nДолжность: " + Context.worker_status.get() + "\n\nВведите сумму для зарплаты");
        } else {
            System.out.println("Получины все данные на нового работника.\n");
            System.out.println("Имя: " + Context.worker_name + "\nДолжность: " + Context.worker_status.get() + "\nЗарплата: " + Context.worker_salary + "\n\nЧтобы сохранить, введите yes.\nЧтобы отменить, введите no");
        }
    }

    /**
     * Информационный диалог для редактирования работников
     */
    public void editWorker() {
        Context.getError();
        if (Context.worker_obj == null) {
            System.out.println("Введите id работника");
        } else if (Context.worker_name.length() == 0) {
            System.out.println("Пожалуйста введите имя\nВ данный момент: " + Context.worker_obj.getName() + "\nЕсли хотите сохранить нынешнее имя, тогда оставьте строку пустой");
        } else if (Context.worker_status == null) {
            System.out.println("Отлично! теперь работника звать " + Context.worker_name + "\nТеперь вы можете изменить должность.\n В данный момент, " + Context.worker_name + ", имеет должность " + Context.worker_obj.getStatus() + "\nЕсли вы не хотите менять должность, тогда отправьте просто пустую строку.\n");
            this.control.listStatus();
        } else if (Context.worker_salary == 0) {
            System.out.println("Имя: " + Context.worker_name + "\nДолжность: " + Context.worker_status.get() + "\n\nВведите сумму для зарплаты.\n Сейчас, " + Context.worker_name + ", получает " + Context.worker_obj.getSalary() + "\nЕсли вы хотите сохранить нынешнею зарплату, тогда отправьте просто пустую строку");
        } else {
            System.out.println("Получины все данные на  работника.\n");
            System.out.println("Имя: " + Context.worker_name + "\nДолжность: " + Context.worker_status.get() + "\nЗарплата: " + Context.worker_salary + "\n\nЧтобы сохранить, введите yes.\nЧтобы отменить, введите no");
        }
    }

    /**
     * Информационнный диалог для удаления работника
     */
    public void deleteWorker() {
        Context.getError();
        if (Context.worker_obj == null) {
            System.out.println("Введите id работника");
        } else {
            System.out.println("Вы точно хотите удалить работника " + Context.worker_obj.getName() + "?\n\nyes - да, удалить\nno - Нет, отмена");
        }
    }

    /**
     * Информационный диалог для очистки колекции работников
     */
    public void clearWorker() {
        System.out.println("Вы точно хотите удалить всех работников?\n\nyes - да, удалить\nno - нет, отмена");
    }

    /**
     * Информационный диалог для сохранения данных в файл.
     */
    public void save() {
        System.out.println("Вы точно хотите сохранить работников в csv файл?\n\nyes - да, сохранить\nno - нет, отмена");
    }

    /**
     * Информационный диалог для статистики
     */
    public void statistic() {
        this.control.statistic();
    }

    /**
     * Метод, который является пусковым в приложении
     * В нем принимается решение, какой метод вызвать, какой адрес запустить
     */
    public void run() {
        this.control.loader();
        /* Далее идет цикл do while, в котором и будем обрабатывать команды. */
        do {
            // Проверим, авторизирован ли пользователь или нет
            if (Context.author()) {
                String module = Tools.module(Context.getPath(), 1); // Находим адрес первого уровня
                Tools.pathBar(Context.getPath());
                switch (module) {
                    case "/save":
                        this.save();
                        break;
                    case "/clear":
                        this.clearWorker();
                        break;
                    case "/statistic":
                        this.statistic();
                        break;
                    case "/delete_worker":
                        this.deleteWorker();
                        break;
                    case "/edit_worker":
                        this.editWorker();
                        break;
                    case "/new_worker":
                        this.newWorker();
                        break;
                    case "/show":
                        this.control.list();
                        break;
                    case "/info":
                        this.info.workerInfo();
                        break;
                    case "/help":
                        this.info.workerHelp();
                        break;
                    default:
                        this.info.workerHello();
                }

                Context.data = scanner.nextLine().toLowerCase();
                Context.redirect(); // Адресация и назначение команд
                // Далее методы, которые будут слушать действие.
                this.control.listenerNewWorker();
                this.control.listenerEditWorker();
                this.control.listenerDeleteWorker();
                this.control.listenerClear();
                this.control.listenerSave();
            } else {
                System.out.println("Чтобы войти в систему учета, нужно ввести пароль.\nПодсказка: Пароль Admin\n");
                Context.data = scanner.nextLine();
                Context.setLogin(Context.data);
            }

        } while (!"exit".equals(Context.data));
        System.exit(0);
    }
}
