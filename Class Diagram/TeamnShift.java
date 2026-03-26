enum TeamName {
    TA, TB
}

enum ShiftType {
    Shift1, Shift2
}

interface Bonus {
    void bonusAnnually(double target);
}

interface Shift2Task {
    void restock();
    void cleaning();
}