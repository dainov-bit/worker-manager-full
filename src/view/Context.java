package view;

import model.Worker;
import model.Status;

/**
 * Данный класс будет хранить состояние приложения
 */
public class Context {
    public static String data = ""; // поле, которое будет постоянно принимать команды с клавиатуры.
    private static String path = ""; // Поле, которое будет принимать путь к методу.
    private static String login = "";
    private static String systemLogin = "Admin"; // Логин, который хранит программа и будет делать проверку, верный ли логин из поля login
    private static String sortType = ""; // Тип действия. К примеру sort или mode. Более подробней в методах, где это используется.
    private static String sortQuery = ""; // Указываем тип действия или тип сортировки
    /* Далее поля, которые будут принимать временные данные для создания или редактирования пользователя.
    Мы не можем резковать и записывать данные на прямую в класс Worker.
    Для этого и существуют поля ниже, в которые будут заноситься данные, проверяться, а потом вноситься в класс Worker */

    public static String worker_name = ""; // Временно храним имя сотрудника
    public static double worker_salary = 0; // Времено храним зарплату сотрудника
    public static Status worker_status = null; // Временно храним должность сотрудника из enum
    public static String worker_error = ""; // Временно храним информацию про ошибку.
    public static Worker worker_obj = null; // Храним объект класса worker, в который будет помещен сотрудник.

    public static void getError() {
        if (worker_error.length() > 0) {
            System.out.println(worker_error);
            worker_error = "";
        }
    }

    /**
     * Следующий метод решает задачу с адресацией.
     */
    private static void redirectPath() {
        if (data.length() > 0 & !"/save".equals(path) & !"/clear".equals(path) & !"/new_worker".equals(path) & !"/edit_worker".equals(path) & !"/delete_worker".equals(path)) { // откроется,  когда не пустая строка
            // Возврат пользователя
            if ("index".equals(data)) {
                path = "";
            } else if ("back".equals(data)) {
                path = "";
            } else {
                path = path + "/" + data;
            }
        }

    }

    /**
     * Метод, который формирует команды
     */
    public static void redirect() {
        // Проверяем, если пользователь ввел два слова.
        String[] dSplit = data.split(" ");
        if (dSplit.length > 1 && dSplit.length < 3) {
            // Далее проверяем, есть ли в этих словах специальные команды
            if ("search".equals(dSplit[0]) && dSplit[1].length() > 0) {
                path = "/show";
                sortType = "search";
                sortQuery = dSplit[1];
                data = "";
            } else if ("sort".equals(dSplit[0]) && "id".equals(dSplit[1])) {
                // указываем сортировку по id
                sortType = "sort";
                sortQuery = "id";
                path = "/show";
            } else if ("sort".equals(dSplit[0]) && "salary".equals(dSplit[1])) {
                // указываем сортировку по зарплате
                sortType = "sort";
                sortQuery = "salary";
                path = "/show";
            } else if ("mode".equals(dSplit[0]) && "new".equals(dSplit[1])) {
                // активируем режим нового работника
                sortType = "mode";
                sortQuery = "new";
                path = "/new_worker";
                data = "";
            } else if ("mode".equals(dSplit[0]) && "edit".equals(dSplit[1])) {
                // включаем режим редактирования пользователя
                path = "/edit_worker";
                sortType = "mode";
                sortQuery = "edit";
                data = "";
            } else if ("mode".equals(dSplit[0]) && "delete".equals(dSplit[1])) {
                // активируем activity для удаления пользователя
                sortType = "mode";
                sortQuery = "delete_worker";
                path = "/delete_worker";
                data = "";
            } else if ("mode".equals(dSplit[0]) && "clear".equals(dSplit[1])) {
                sortType = "mode";
                sortQuery = "clear";
                data = "";
                path = "/clear";
            } else {
                // Если слова не подходят под каманды, тогда выполняется метод адресации
                redirectPath();
            }
        } else {
            redirectPath();
        }
    }

    public static String getSystemLogin() {
        return systemLogin;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String d) {
        login = d;
    }

    public static String getPath() {
        return path;
    }

    public static String getSortType() {
        return sortType;
    }

    public static String getSortQuery() {
        return sortQuery;
    }

    public static void stopEvent() {
        path = "";
        data = "";
        sortType = "";
        sortQuery = "";
        worker_status = null;
        worker_obj = null;
        worker_name = "";
        worker_salary = 0;
    }


    public static boolean author() {
        if (getLogin().length() > 0 && getLogin().equals(getSystemLogin())) {
            return true;
        } else {
            return false;
        }
    }

    public static void setPath(String d) {
        path = d;
    }


}