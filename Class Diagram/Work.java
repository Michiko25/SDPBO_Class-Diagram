class Cook extends Employee implements Shift2Task {
    public Cook (String id, String name, double salary, TeamName team, ShiftType shift) {
        super (id, name, salary, team, shift);
    }

    @Override
    public void work() {}
    public void cookOrder() {}

    @Override
    public void restock() {}

    @Override
    public void cleaning() {}
}

class CashierBeverage extends Employee implements Shift2Task {
    public CashierBeverage (String id, String name, double salary, TeamName team, ShiftType shift) {
        super (id, name, salary, team, shift);
    }

    @Override
    public void work() {}
    public void transaction() {}
    public void makeDrink() {}

    @Override
    public void restock() {}

    @Override
    public void cleaning() {}
}

class Waiter extends Employee implements Shift2Task {
    public Waiter(String id, String name, double salary, TeamName team, ShiftType shift) {
        super(id, name, salary, team, shift);
    }

    @Override
    public void work() {}
    public void deliverOrder() {}
    public void cleanTable() {}

    @Override
    public void restock() {}

    @Override
    public void cleaning() {}
}