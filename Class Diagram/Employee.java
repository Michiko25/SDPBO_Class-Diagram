abstract class Employee implements Bonus {
    protected String idEmployee;
    protected String name;
    protected double salary; // gaji per jam
    protected TeamName team;
    protected ShiftType shiftnow;

    public Employee (String id, String name, double salary, TeamName team, ShiftType shift) {
        this.idEmployee = id;
        this.name = name;
        this.salary = salary;
        this.team = team;
        this.shiftnow = shift;
    }

    public double salaryTotal (int hour, int overtime) {
        double pay = hour * salary;
        double overtimePay = overtime * (salary * 0.1);
        return pay + overtimePay;
    }

    public abstract void work();

    @Override
    public void bonusAnnually(double target) {
        System.out.println("Bonus Annually: Tunjangan untuk " + name + "jika target " + target + "tercapai. ");
    }

    public ShiftType getShift() {
        return shiftnow; 
    }

    public void setShift (ShiftType newShift) {
        this.shiftnow = newShift;
    }
}

