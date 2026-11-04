# Student Management

ระบบจัดการนักเรียน (CRUD) ด้วย Java แบบ OOP เก็บข้อมูลในหน่วยความจำ มีทั้งแบบ Console และ GUI (Swing)

## Requirements
- JDK **25 หรือใหม่กว่า** (ใช้ `java.lang.IO` ซึ่งเป็นฟีเจอร์มาตรฐานตั้งแต่ Java 25 ไม่ต้องใช้ `--enable-preview`)
- `make` (Linux / macOS / WSL)
- GUI ต้องมีจอแสดงผล (บน WSL ใช้ WSLg)

## Structure
```
student_management/
├── Main.java                         # Console UI (เมนู)
├── Makefile
└── src/
    ├── model/Student.java            # ข้อมูลนักเรียน (encapsulation)
    ├── service/StudentService.java   # logic CRUD
    └── gui/
        ├── GuiMain.java              # จุดเริ่มต้นของ GUI
        └── StudentManagementFrame.java   # หน้าต่าง Swing
```

## Run
```bash
make run       # Console
make run-gui   # GUI
make build     # คอมไพล์อย่างเดียว (ผลลัพธ์ที่ out/)
make clean     # ลบ out/
```

## Features
| การทำงาน | Console | GUI |
|---|---|---|
| Create (เพิ่ม) | เมนู 1 | ปุ่ม Add |
| Read All (แสดงทั้งหมด) | เมนู 2 | ปุ่ม Read All |
| Read by ID (ค้นหา) | เมนู 3 | ปุ่ม Search by ID / กด Enter ในช่อง ID |
| Update (แก้ไข) | เมนู 4 | ปุ่ม Update |
| Delete (ลบ) | เมนู 5 | ปุ่ม Delete |

มีข้อมูลตัวอย่างเริ่มต้น 5 รายการ (S001–S005) ข้อมูลหายเมื่อปิดโปรแกรม

## OOP concepts
Class & Object, Encapsulation (private + getter/setter), Constructor,
Separation of concerns (model / service / UI)
