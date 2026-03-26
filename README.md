# SDPBO_Class-Diagram

## Deskripsi Kasus
Kasus yang saya ambil untuk tugas ini adalah sistem karyawan restauran ibu saya. Berikut penjelasan sistem karyawan restauran tersebut:
* Terdapat tiga tugas utama karyawan di restauran ini, yaitu pemasak, kasir dan pembuat minum, dan pelayan.
* Karyawan dibagi menjadi dua tim dengan tiap tim terdiri dari tiga anggota.
* Jam kerja tiap tim dibagi ke dalam sistem shift:
  1. Shift 1 mulai pukul 11.00 - 17.00, shift 2 mulai pukul 15.00 - 21.00.
  2. Rotasi jam kerja (shift) dilakukan tiap 2 minggu sekali.
  3. Pada saat pukul 15.00 - 17.00 ketika kedua shift hadir di restauran, karyawan pada shift 2 memiliki tugas khusus berupa pengadaan stok dan membersihkan lokasi.
* Gaji dihitung berdasarkan jam kerja dan tambahan bonus tiap jam sebesar 10% dari gaji per jam.
* Evaluasi pekerja dilakukan oleh ibu saya (pemilik / manajer) tiap satu minggu sekali. 
* Bonus tahunan berlaku jika restauran memenuhi target tahunan. 

## Class Diagram
* Membentuk abstract class untuk karyawan. Menambahkan variabel untuk karyawan dengan atribut protected supaya class anak (tugas tiap pekerja) dapat mengakses data tersebut secara langsung tanpa pihak luar (encapsulation). Menambahkan total gaji serta abstract method (```work()*```) sebagai kewajiban kerja bagi staf namun implementasi spesifiknya diatur pada masing-masing tugas pekerjaan. 
* Membentuk Enumeration (data statis) seperti nama tim dan tipe shift untuk menjamin konsistensi dan kepastian data karena nama tim dan tipe shift bersifat statis (tidak berubah).
* Membentuk interface seperti bonus dan pekerjaan khusus untuk shift 2 pada pukul 15.00-17.00.
* Membentuk concrete class, memberikan tugas untuk tiap pekerja (pemasak, kasir dan pembuat minum, dan pelayan).
* Membuat class untuk sistem manajer yang dapat mengelola daftar 6 staff serta mengendalikan rotasi shift.

Berikut bentuk code Mermaid.ai untuk class diagram tersebut:
``` Mermaid
---
title: Karyawan
---
classDiagram
  class TeamName {
  (enumeration)
    TA
    TB
  }
  class ShiftType {
  (enumeration)
    Shift1
    Shift2
  }

  class Bonus {
  (interface)
    +bonusAnnually(double target) void
  }
  class Shift2Task{
    +restock() void
    +cleaning() void
  }
  
  class Employee {
  (abstract)
    #String idEmployee
    #String name
    #double salary
    #TeamName team
    #ShiftType shiftnow
    +salaryTotal(int hour, int overtime) double
    +work()* void
  }
  
  class Cook {
    +work() void
    +cookOrder() void
  }
  
  class CashierBeverage {
    +work() void
    +transaction() void
    +makeDrink() void
  }
  
  class Waiter {
    +work() void
    +deliverOrder() void
    +cleanTable() void
  }
  
  class ManagerSystem {
    -List~Employee~ ListStaff
    -int week
    +shiftRotation() void
    +weekEvaluation() void
  }
  
  %% Relation
  Employee <|-- Cook : Inheritance
  Employee <|-- CashierBeverage : Inheritance
  Employee <|-- Waiter : Inheritance
  
  Employee ..|> Bonus
  
  Cook ..|> Shift2Task
  CashierBeverage ..|> Shift2Task
  Waiter ..|> Shift2Task
  
  ManagerSystem "1" *-- "6" Employee
```

## Kode program Java
File ```Employee.java``` sebagai Abstract Class

``` java
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
```

abstract class Employee sebagai template atribut staff. Variabel-variabel seperti name dan salary menggunakan protected access modifier (encapsulation). Mengimplementasikan logika perhitungan gaji melalui method salaryTotal. Menambahkan abstract method (public abstract void work()). 
Dalam diagram sebelumnya, Employee mewujudkan interface Bonus. ```implements Bonus``` ditambahkan untuk mewariskan aturan bonus pada tiap karyawan. 

File ```Work.java``` sebagai inheritance dan polymorphism

```java
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
```

```extends Employee``` pada class Cook, CashierBeverage, dan Waiter menandakan bahwa class-class tersebut mewarisi seluruh atribut dan method dari class Employee. ```implements Shift2Task``` menandakan bahwa tiap class pekerja di shift2 harus berganti ke pekerjaan khusus pada pukul 15.00-17.00. Setiap kelas diberi isi method seperti ```work()```, satu perintah ini menghasilkan aksi yang berbeda tergantung tugas pekerja (polymorphism).

File ```TeamnShift```

```java
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
```

Peggunaan enumeration untuk memastikan bahwa tipe data dalam team dan shift hanya berisi nilai valid. ```interface Bonus``` untuk mendeskripsikan sistem bonus tahunan dan semua karyawan berhak mendapatkan bonus. ```interface Shift2Task``` sebagai interface spesifik untuk kelas pekerja, mendeskripsikan sistem tugas shift 2 pada pukul 15.00-17.00. 

File ```Manager.java```

```java
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
```

Menggunakan collection (```List<Employee> listStaff```) yang menampung maksimal 6 staff. Hubungan ini disebut composition. Mengimplementasikan aturan rotasi shift melalui logika ```shiftRotation()```, jika minggu genap maka shift akan bertukar.  

## Screenshot output
Berikut hasil output code class diagram melalui ```Mermaid.ai```

<img width="1019" height="882" alt="image" src="https://github.com/user-attachments/assets/dd9c9297-d1c2-4da0-9ea3-383274ad0e84" />

Berikut hasil output pemrograman Java melalui file ```Main.java```
```java
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
```

<img width="222" height="77" alt="Screenshot 2026-03-26 131725" src="https://github.com/user-attachments/assets/2ffa7dfc-0997-4874-95a1-f806bdd2eaa3" />

## Prinsip OOP yang digunakan
1. Abstraction (abstraksi)
   Penerapan pada ```abstract class Employee``` dan ```interface Shift2Task```.
   class-class tersebut hanya sebagai template, tidak menjelaskan cara tetapi hanya memberi standar atau basis bahwa tiap pekerja wajib punya fungsi-fungsi pada abstract class.
2. Inheritance (pewarisan)
   Penerapan pada class ```Cook```, ```CashierBeverage```, dan ```Waiter```.
   Semua atribut dalam kelas induk diwariskan kepada kelas anak karena tiap karyawan pasti memiliki atribut-atribut yang ada dalam kelas induk.
3. Encapsulation (oembungkusan)
   Penerapan private pada ManagerSystem (```private List<Employee> listStaff```):
   Orang luar tidak bisa langsung mengubah data tersebut. Jika ingin mengubah data harus melewati metode ```addStaff()```. Manager bisa validasi perubahan data.
   Penerapan protected pada Employee:
   Untuk mengamankan data, misal ```protected double salary``` bukan informasi umum yang bisa diketahui orang luar, tetapi diketahui dan diolah oleh kelas turunannya. 
5. Polymorphism (banyak bentuk)
   Penerapan ```@Override``` pada metode ```work()``` di setiap kelas pekerja.
   Saat ManagerSystem memanggil perintah staf.work(), hasilnya akan berubah otomatis jika objeknya adalah Cook maka ia akan memasak. Menandakan satu perintah bisa mewujudkan banyak aksi tergantung pada objek (tugas pekerja).  
   
## Keunikan
Overlap Jam 15.00-17.00: 
Menurut saya pada bagian ini merupakan sistem yang unik. Ketika jam shift 1 masih belum selesai, shift 2 sudah hadir. Hal tersebut membuat saya bingung saat pertama kali mengetahuinya. 
Sebelumnya saya mengira pengadaan stok dan pembersih restauran dibagi tugas ke karyawan khusus. Ternyata ibu saya membuat sistem khusus untuk shift 2 pada pukul 15.00-17.00 selama 2 jam melakukan tugas khusus, kemudian lanjut melakukan pekerjaan normal saat jam pekerja shift 1 selesai.  
