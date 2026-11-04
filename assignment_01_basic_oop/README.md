# Assignment 01 – Basic OOP (Java)

แบบฝึกหัดพื้นฐาน OOP ด้วย Java

## Requirements
- JDK 25+ (ใช้ `java.lang.IO` ใน `Main.java`)
- `make` (Linux / macOS / WSL)

## Structure
```
student_management/            # ระบบจัดการนักเรียน (CRUD, in-memory)
  Main.java                    # เมนู / UI
  src/model/Student.java       # ข้อมูลนักเรียน
  src/service/StudentService.java  # logic CRUD
```

## Run (student_management)
```bash
cd student_management
make run     # build + run
make clean   # ลบ out/
```

## Features
เพิ่ม / แสดงทั้งหมด / ค้นหา / แก้ไข / ลบ นักเรียน (ข้อมูลเก็บในหน่วยความจำ เริ่มต้นมี seed 5 รายการ)

## OOP concepts
Class & Object, Encapsulation (private + getter/setter), Constructor,
Separation of concerns (model / service / UI)
