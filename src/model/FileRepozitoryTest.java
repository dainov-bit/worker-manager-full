package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class FileRepozitoryTest {
    @Test
    public void fTest() {
        System.out.println("Тестируем класс FileRepozitory");
        System.out.println("Заполняем коллекцию данными из файла test.csv");
        Repozitory repo = new Repozitory();
        FileRepozitory fr = new FileRepozitory("src/test.csv",repo);
        fr.loader();
        System.out.println("Давайте проверим, появились ли данные в коллекции.");
        repo.listWorker("","");
System.out.println("Сортировка, поиск и другие тесты выполняются в классе RepozitoryTest.");
System.out.println("Давайте ради интереса добавим еще два работника и выведем их списком вместе с существующими.");
repo.add("Artem",1500.0,Status.WORKER,0);
repo.add("Anna",550,Status.WORKER,0);
repo.listWorker("sort","id");
System.out.println("Давайте сохраним данные");
fr.save();
System.out.println("Теперь очистим коллекцию");
repo.clear();
System.out.println("Попробуем еще раз заполнить коллекцию");
fr.loader();
System.out.println("Проверяем");
repo.listWorker("sort","id");
System.out.println("Конец");
    }

}