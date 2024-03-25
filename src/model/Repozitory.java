package model;

import java.util.*;
import java.util.HashSet;
import java.util.Set;

import control.IdComparator;
import control.StatusException;
import control.SearchWorkerException;

/**
 * Этот класс является хранилищем наших объектов, то есть работников.
 */
public class Repozitory {
    // Храним наших работников
    private Set<Worker> worker = new HashSet<>();
    private Worker last; // Поле хранит последнего работника.
    private int count = 1; // для назначения id новому работнику
    private Worker worker_obj = null; // Для перидачи в контекст работника.
    private Status worker_status = null; // статус для перидачи в контекст.

    /**
     * Счетчик для id
     *
     * @return Выводит int число
     */
    public int counter() {
        return this.count++;
    }

    /**
     * Метод, который выводит сообщение про ошибку для перидачи в Context.worker_error
     * @return выводит строку
     */
    public Status getWorkerStatus() {
        return this.worker_status;
    }
/**
 * Сеттер для worker_obj и worker_status
 */
public void setNull() {
    this.worker_obj = null;
    this.worker_status = null;
}

    /**
     * Метод, который выводит объект работника.
     * Нужно для передачи данных в Context.worker_obj  в Control
     * @return Выводит объект класса Worker
     */
    public Worker getWorkerObj() {
        return this.worker_obj;
    }

    /**
     * Метод выполняет поиск в колекции и находит работника.
     *
     * @param n принимает id работника для поиска
     * @return выводит boolean значение
     * @throws SearchWorkerException выводит сообщение о ошибке, если работник не найден.
     */
    public boolean searchWorker(int n) throws SearchWorkerException {
        Iterator<Worker> i = this.idIterator();
        while (i.hasNext()) {
            Worker w = i.next();
            if (n == w.getId()) { // нашли объект и поместили его в Context.worker_obj
                this.worker_obj = w;
                return true;
            }
        }
        if (this.worker_obj == null) { // Если работник не найден, тогда формируем ошибку
            throw new SearchWorkerException("Не удалось найти работника. Неверный id");
        }

        return false;
    }

    /**
     * Удаляем объект из HashSet
     *
     * @param obj Объект для удаления
     */
    public void drop(Worker w) {
        this.worker.remove(w);
    }

    /**
     * Метод выполняет очистку коллекции
     */
    public void clear() {
        this.worker.clear();
    }

    /**
     * Метод, который должен вернуть массив информации о последнем объекте.
     * Он нужен лишь для того, чтобы сделать проверку в тестах
     */

    public Worker getLast() {
        return this.last;
    }


    /**
     * Метод, который в колекцию добовляет работников.
     *
     * @param name   имя работника
     * @param salary зарплата работника
     * @param status статус работника
     * @param c      этот параметр указывает, нужно автоматически id делать или вручную. Если из него приходит ноль, тогда id создается автоматически, если приходит другое число, тогда другое число будет id
     */
    public void add(String name, double salary, Status status, int c) {
        Worker w;
        if (c != 0) {
            w = new Worker(name, salary, status, c);
            this.worker.add(w);
            this.count = c + 1;
        } else {
            w = new Worker(name, salary, status, this.counter());
            this.worker.add(w);
        }
        this.last = w;
    }


    /**
     * Следующий метод передает итератор HashSet, чтобы там можно было пройти по коллекции
     *
     * @return Возвращает Iterator
     */
    public Iterator idIterator() {
        return this.worker.iterator();
    }


    /**
     * Этот метод выполняет заполнение ArrayList из HashSet
     * Это нужно для правильной сортировки данных
     *
     * @return Выводит ArrayList
     */
    public ArrayList hashSetToArrayList() {
        ArrayList<Worker> alw = new ArrayList<>();
        Iterator<Worker> i = this.idIterator();
        while (i.hasNext()) {
            alw.add(i.next());
        }
        return alw;
    }

    /**
     * Метод, который выводит информационную модель о работнике
     * К примеру имя, возраст, пол, должность, зарплата
     *
     * @param obj - объект работника из класса worker
     * @return - выводит строку
     */
    public String model(Worker obj) {
        return "ID: " + obj.getId() + "\n" +
                "Имя: " + obj.getName() + "\n" +
                "Должность: " + obj.getStatus() + "\n" +
                "Зарплата: " + obj.getSalary() + "\n";
    }

    /**
     * Список работников по умолчанию.
     *
     * @param i принимает iterator от hashset
     */
    private void workerDefaultList(Iterator i) {
        while (i.hasNext()) {
            System.out.println(this.model((Worker) i.next()));
        }
    }

    /**
     * Сортировка работников
     *
     * @param type  тип. sort или search
     * @param query строка запроса для поиска или сортировки
     */
    private void arrayIterator(String type, String query) {
        ArrayList<Worker> w = this.hashSetToArrayList();
        if (query.equals("id")) {
            Collections.sort(w, new IdComparator());
        } else {
            Collections.sort(w);
        }
        if (type.equals("search")) {
            int count = query.length();
            w.stream().filter(s -> query.equals(s.getName().substring(0, count).toLowerCase())).forEach(x -> System.out.println(this.model(x)));
        } else {
            w.stream().forEach(x -> System.out.println(this.model(x)));
        }
    }

    /**
     * Метод выводит список работников
     *
     * @param type  Тип. К примеру sort
     * @param query Указывает, как происходит сортировка. К примеру по id
     */
    public void listWorker(String type, String query) {
        if ("sort".equals(type) || type.equals("search")) {
            this.arrayIterator(type, query);
        } else {
            this.workerDefaultList(this.idIterator());
        }
        //
    }

    /**
     * Метод выводит список профессий из enum
     */
    public void listStatus() {
        int i = -1;
        for (Status s : Status.values()) {
            i++;
            System.out.println(i + " - " + s.get());
        }
    }

    /**
     * Метод выполняет поиск профессии по номеру
     *
     * @param n номер професси представленный в списке
     * @return выводит boolean значение
     * @throws StatusException ошибка, если нет такой должности в enum
     */
    public boolean searchStatus(int n) throws StatusException {
        int i = -1;
        for (Status s : Status.values()) {
            i++;
            if (i == n) { // профессия найдена и назначена в Context.worker_status
                this.worker_status = s;
                return true;
            }
        }
        if (this.worker_status == null) { // должность не найдена, формируем ошибку.
            throw new StatusException("Внимание! Неудалось найти профессию");
        }
        return false;
    }

    /**
     * Метод выводит статистику колекции
     *
     * @return выводит строку
     */
    public String statistic() {
        Iterator<Worker> i = this.idIterator();
        int x = 0;
        double money = 0;
        while (i.hasNext()) {
            Worker w = i.next();
            x++;
            money += w.getSalary();
        }
        String result = "Общее количество работников: " + x + "\nОбщая сумма всех зарплат: " + money;
        return result;
    }
}
