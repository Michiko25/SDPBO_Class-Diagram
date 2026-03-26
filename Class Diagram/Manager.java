import java.util.ArrayList;
import java.util.List;

public class Manager {
    private List<Employee> listStaff = new ArrayList<>();
    private int week = 1;

    public void addStaff (Employee e) {
        listStaff.add(e);
    }

    public boolean shiftRotation() {
        if (week % 2 == 0) {
            for (Employee e : listStaff) {
                if (e.getShift() == ShiftType.Shift1) {
                    e.setShift (ShiftType.Shift2);
                }
                else {
                    e.setShift(ShiftType.Shift1);
                }
            }
            week++;
            return true;
        }
        else { 
            week++;
            return false; // belum saatnya rotasi
        }
    }

    public void weekEvaluation() {}
}
