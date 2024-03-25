package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RepozitoryTest {
    @Test
    public void myTest() {
        Repozitory r = new Repozitory();
        System.out.println("Создаем работника и указываем ему данные.\n: id 1, Name: Artem, salary: 1500.0, Status DIRECTOR");
        int id = 1;
        String name = "Artem";
        double salary = 1500.0;
        Status status = Status.DIRECTOR;
        r.add(name,salary,status,id);
        System.out.println("Давайте проверим, добавился ли он, запросив список работников в сортировке бардак");
        r.listWorker("","");
        System.out.println("У нас есть возможность сравнить данные с последним работником, который был добавлен в колекцию.");
        System.out.println(((id==r.getLast().getId() && name.equals(r.getLast().getName()) && salary==r.getLast().getSalary() && status.get().equals(r.getLast().getStatus())) ? "Да, это точно наш работник" : "Нет, это фальшивый работник"));
System.out.println("\nДобавим еще несколько работников в колекцию.\n2,Igor,1800.0,BOOKER\n3,Otto,1300.0,WORKER");
        r.add("Igor",1800.0,Status.BOOKER,2);
        r.add("Otto",1300,Status.WORKER,3);
        System.out.println("Отлично! Выводим работников в сортировке бардак");
        r.listWorker("","");
        System.out.println("Сортируем по id");
        r.listWorker("sort","id");
        System.out.println("Сортируем по зарплате");
        r.listWorker("sort","salary");
        System.out.println("Попробуем найти кого-то с начальными буквами ot");
        r.listWorker("search","ot");
System.out.println("удалим последнего работника");
r.drop(r.getLast());
System.out.println("Посмотрим, есть ли Otto в списке");
r.listWorker("sort","id");
System.out.println("Добавим работника с автоматическим id");
r.add("Fritz",3600.0,Status.WORKER,0);
System.out.println("Выводим список работников по id");
r.listWorker("sort","id");
System.out.println("Конец");

    }
}