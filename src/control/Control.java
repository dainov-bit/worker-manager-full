package control;

import model.*;
import view.Context;

/**
 * Этот класс является управляющим.
 * В нем есть методы, которые вызываются во view.
 */
public class Control {
    private String file; // путь к data файлу
    private Repozitory repo; // для репозитория
    private FileRepozitory fr; // для файлового репозитория

    public Control(String f) {
        this.file = f;
        this.repo = new Repozitory();
        this.fr = new FileRepozitory(f, this.repo); // в файловый репозиторий передали путь к файлу и объект репозитория

    }

    /**
     * Метод выполняет запрос на заполнение колекции
     */
    public void loader() {
        this.fr.loader();
    }

    /**
     * Метод, во view, слушает событие на добавление нового работника
     */
    public void listenerNewWorker() {
        if (Context.getPath().equals("/new_worker")) {
            if (Context.data.equals("stop")) {
                this.repo.setNull();
                Context.stopEvent();
            }
            if (Context.worker_name.length() == 0) {
                Context.worker_name = Context.data;
            } else if (Context.worker_status == null) {
                int i = 0;
                try {
                    i = Integer.parseInt(Context.data);
                    try {
                        this.repo.searchStatus(i);
                        Context.worker_status = this.repo.getWorkerStatus();
                    } catch (StatusException x) {
                        Context.worker_error = x.getMessage();
                    }
                } catch (Exception e) {
                    Context.worker_error = "Вводить можно только цифру";
                }
            } else if (Context.worker_salary == 0) {
                try {
                    Context.worker_salary = Double.parseDouble(Context.data);
                } catch (Exception e) {
                    Context.worker_error = "Вводить можно цифры и точку.";
                }
            } else {
                if ("yes".equals(Context.data)) {
                    this.repo.add(Context.worker_name, Context.worker_salary, Context.worker_status, 0);
                    Context.stopEvent();
                    this.repo.setNull();
                    Context.setPath("/show");
                } else if ("no".equals(Context.data)) {
                    Context.stopEvent();
                    this.repo.setNull();
                }
            }

        }
    }

    /**
     * Метод слушает событие для редактирования работника
     */
    public void listenerEditWorker() {
        if (Context.getPath().equals("/edit_worker")) {
            if (Context.data.equals("stop")) {
                Context.stopEvent();
                this.repo.setNull();
            }
            if (Context.worker_obj == null) {
                int n;
                try {
                    n = Integer.parseInt(Context.data);
                    try {
                        this.repo.searchWorker(n);
                        Context.worker_obj = this.repo.getWorkerObj();
                    } catch (SearchWorkerException e) {
                        Context.worker_error = e.getMessage();
                    }
                } catch (Exception e) {
                    Context.worker_error = "Вводить можно только цифры";
                }
            } else if (Context.worker_name.length() == 0) {
                if (Context.data.length() == 0) {
                    Context.worker_name = Context.worker_obj.getName();
                } else {
                    Context.worker_name = Context.data;
                }
            } else if (Context.worker_status == null) {
                if (Context.data.length() == 0) {
                    Context.worker_status = Context.worker_obj.getStatusForEdit();
                } else {
                    int i = 0;
                    try {
                        i = Integer.parseInt(Context.data);
                        try {
                            this.repo.searchStatus(i);
                            Context.worker_status = this.repo.getWorkerStatus();
                        } catch (StatusException x) {
                            Context.worker_error = x.getMessage();
                        }
                    } catch (Exception e) {
                        Context.worker_error = "Вводить можно только цифру";
                    }
                }
            } else if (Context.worker_salary == 0) {
                if (Context.data.length() == 0) {
                    Context.worker_salary = Context.worker_obj.getSalary();
                } else {
                    try {
                        Context.worker_salary = Double.parseDouble(Context.data);
                    } catch (Exception e) {
                        Context.worker_error = "Вводить можно цифры и точку.";
                    }
                }
            } else {
                if ("yes".equals(Context.data)) {
                    Context.worker_obj.set(Context.worker_name, Context.worker_status, Context.worker_salary);
                    Context.stopEvent();
                    this.repo.setNull();
                    Context.setPath("/show");
                } else if ("no".equals(Context.data)) {
                    Context.stopEvent();
                    this.repo.setNull();
                }
            }
        }
    }

    /**
     * Метод который слушает событие на удаление работника
     */
    public void listenerDeleteWorker() {
if ("/delete_worker".equals(Context.getPath())) {
if (Context.data.equals("stop")) { Context.stopEvent(); this.repo.setNull();}
if (Context.worker_obj==null) {
int n;
try {
n = Integer.parseInt(Context.data);
try {
    this.repo.searchWorker(n);
    Context.worker_obj = this.repo.getWorkerObj();
} catch(SearchWorkerException e) {
    Context.worker_error = e.getMessage();
}
} catch(Exception e) {
    Context.worker_error = "Вводить можно только цифры";
}
}
else {
if ("yes".equals(Context.data)) {
this.repo.drop(Context.worker_obj);
Context.stopEvent();
this.repo.setNull();
}
else if ("no".equals(Context.data)) {
    Context.stopEvent();
    this.repo.setNull();
}
}
}
}

    /**
     * Метод слушает событие на очистку колекции
     */
    public void listenerClear() {
        if ("/clear".equals(Context.getPath())) {
            if (Context.data.equals("stop")) { Context.stopEvent(); this.repo.setNull();}
            if ("yes".equals(Context.data)) {
this.repo.clear();
Context.stopEvent(); this.repo.setNull();
            }
            else if ("no".equals(Context.data)) {
             Context.stopEvent(); this.repo.setNull();
            }
        }
}

    /**
     * Метод, который слушает событие на сохранение данных в файл
     */
    public void listenerSave() {
        if ("/save".equals(Context.getPath())) {
if (Context.data.equals("stop")) { Context.stopEvent(); this.repo.setNull();}
            if ("yes".equals(Context.data)) {
                this.fr.save();
                Context.stopEvent(); this.repo.setNull();
            }
else if ("no".equals(Context.data)) {
    Context.stopEvent(); this.repo.setNull();
            }
        }
}

    /**
     * Метод, который запрашивает статистику
     */
    public void statistic() {
        System.out.println(this.repo.statistic());
}

    /**
     * Метод выполняет запрос для показа должностей
     */
    public void listStatus() {
        this.repo.listStatus();
    }

    /**
     * Метод запрашивает список работников и отправляет их во view
     */
    public void list() {
        this.repo.listWorker(Context.getSortType(), Context.getSortQuery());
    }

}
