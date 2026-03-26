public class Main {
    public static void main () {
        Manager sistem = new Manager ();

        Cook staff1 = new Cook ("C1", "siA", 50, TeamName.TA, ShiftType.Shift1);
        Waiter staff2 = new Waiter ("W02", "siB", 45, TeamName.TB, ShiftType.Shift2);

        sistem.addStaff(staff1);
        sistem.addStaff(staff2);

        double gajisiA = staff1.salaryTotal(8, 2); 
        System.out.println ("Gaji total " + staff1.name + ": Rp " + gajisiA + " ribu" );

        sistem.shiftRotation(); 
        boolean isRotated = sistem.shiftRotation();

        System.out.println ("Status Rotasi: " + isRotated);
        System.out.println ("Shift " + staff1.name + " saat ini: " + staff1.getShift());
    }
}