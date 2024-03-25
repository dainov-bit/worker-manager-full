package model;

public enum Status {
    DIRECTOR("Директор"),
    DEPUTY_DIRECTOR("Заместитель директора"),
    SECRETARY("Секретарь"),
    BOOKER("Бухгалтер"),
    DRIVER("Водитель"),
    DOCTOR("Доктор"),
    WORKER("Простой рабочий");
    private String title;

    Status(String title) {
        this.title = title;
    }

    public String get() {
        return title;
    }
}
