package model;

import java.util.List;

public class Root {
    private String name;
    private List<Employees> employees;
    private int TotalPrice = 0;
    private int dev=0;
    private int tester = 0;
    private int qa=0;
    private int projectManager = 0;

    public void projectPrice(){
        for (int i=0;i<employees.size();i++){
            this.TotalPrice += employees.get(i).getSalary();
        }
        System.out.println("Project price: " + TotalPrice + "$\n");
    }

    public void positionAmmount(){
        for(int i=0;i<employees.size();i++){
            if(employees.get(i).getPosition().equals("developer")){
                this.dev ++;
            }
            if(employees.get(i).getPosition().equals("tester")){
                this.tester ++;
            }
            if(employees.get(i).getPosition().equals("QAEngineer")){
                this.qa ++;
            }
            if(employees.get(i).getPosition().equals("ProjectManager")){
                this.projectManager++;
            }
        }
        System.out.println("developers: " + this.dev);
        System.out.println("testes: " + this.tester);
        System.out.println("QAEngineers: " + this.qa);
        System.out.println("ProjectManagers: " + this.projectManager + '\n');
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public List<Employees> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public int getDev() {
        return dev;
    }

    public int getTester() {
        return tester;
    }

    public int getQa() {
        return qa;
    }

    public int getProjectManager() {
        return projectManager;
    }
}
