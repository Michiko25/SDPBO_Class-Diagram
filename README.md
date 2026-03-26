# SDPBO_Class-Diagram

## Deskripsi Kasus
Kasus yang saya ambil untuk tugas ini adalah sistem karyawan restauran ibu saya. Berikut penjelasan sistem karyawan restauran tersebut:
- Terdapat tiga tugas utama karyawan di restauran ini, yaitu pemasak, kasir dan pembuat minum, dan pelayan.
- Karyawan dibagi menjadi dua tim dengan tiap tim terdiri dari tiga anggota.
- Jam kerja tiap tim dibagi ke dalam sistem shift:
  1. Shift 1 mulai pukul 11.00 - 17.00, shift 2 mulai pukul 15.00 - 21.00.
  2. Rotasi jam kerja (shift) dilakukan tiap 2 minggu sekali.
  3. Pada saat pukul 15.00 - 17.00 ketika kedua shift hadir di restauran, karyawan pada shift 2 memiliki tugas khusus berupa pengadaan stok dan membersihkan lokasi.
- Gaji dihitung berdasarkan jam kerja dan tambahan bonus tiap jam sebesar 10% dari gaji per jam.
- Evaluasi pekerja dilakukan oleh ibu saya (pemilik / manajer) tiap satu minggu sekali. 
- Bonus tahunan berlaku jika restauran memenuhi target tahunan. 

## Class Diagram
- Membentuk abstract class untuk karyawan. Menambahkan variabel untuk karyawan dengan atribut protected supaya class anak (tugas tiap pekerja) dapat mengakses data tersebut secara langsung tanpa pihak luar (encapsulation). Menambahkan total gaji serta abstract method (```work()*```) sebagai kewajiban kerja bagi staf namun implementasi spesifiknya diatur pada masing-masing tugas pekerjaan. 
- Membentuk Enumeration (data statis) seperti nama tim dan tipe shift untuk menjamin konsistensi dan kepastian data karena nama tim dan tipe shift bersifat statis (tidak berubah).
- Membentuk interface seperti bonus dan pekerjaan khusus untuk shift 2 pada pukul 15.00-17.00.
- Membentuk concrete class, memberikan tugas untuk tiap pekerja (pemasak, kasir dan pembuat minum, dan pelayan).
- Membuat class untuk sistem manajer yang dapat mengelola daftar 6 staff serta mengendalikan rotasi shift.

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

<img width="1019" height="882" alt="image" src="https://github.com/user-attachments/assets/dd9c9297-d1c2-4da0-9ea3-383274ad0e84" />

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



## Screenshot output

## Prinsip OOP yang digunakan

## Keunikan
