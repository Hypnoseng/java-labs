# JDBC Lab

โปรเจกต์ฝึก JDBC ต่อเนื่อง (กำลังพัฒนา) ต่อยอดทีละขั้น:

1. CRUD
2. GUI (Swing)
3. ตาราง (JTable)
4. รูปภาพ
5. Transaction
6. รายงาน

## Requirements
- JDK 25+
- MySQL + [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- ตั้งค่าการเชื่อมต่อผ่าน environment variable (`DB_URL`, `DB_USER`, `DB_PASS`) ห้ามใส่รหัสผ่านในโค้ด

## Structure
```
jdbc_lab/
├── Makefile
├── README.md
└── src/
    ├── model/      # Student (record)
    ├── dao/        # SQL / JDBC ทั้งหมด: Db, StudentDao, PhotoDao
    ├── service/    # Transaction, รายงาน (ยังไม่มี)
    └── gui/        # StudentForm (Swing)
```

## Run
```
export DB_PASS=<รหัสผ่าน MySQL>   # ตั้ง DB_URL, DB_USER เพิ่มได้ถ้าจำเป็น
make run
```
