package model;

public class Employees {
    private String name;
    private int salary;
    private String position;

    public Employees(String name, int salary, String position) {
        this.name = name;
        this.salary = salary;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employees{" +
                '\n'+"name=" + name +
                '\n'+"salary=" + salary + "$"+
                '\n'+"position='" + position + '\n' +
                '}' +'\n';
    }
}
