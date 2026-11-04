# Basic Connect (JDBC + MySQL)

ตัวอย่างเชื่อมต่อ MySQL ด้วย JDBC (`PreparedStatement`)

## Requirements
- JDK 25+
- MySQL ที่มีฐานข้อมูล `java_training` และตาราง `resume`
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (ไฟล์ `.jar`)
- `make` (Linux / macOS / WSL)

## Files
- `ResumeSafe.java` – ค้นหาจากตาราง `resume` ด้วย `PreparedStatement` (กัน SQL injection)
- `ResumeTest.java` – ทดสอบการเชื่อมต่อแบบพื้นฐาน

## Configuration
ตั้งค่าผ่าน environment variable (ห้ามใส่รหัสผ่านในโค้ด):

| ตัวแปร | ค่าเริ่มต้น |
|---|---|
| `DB_PASS` | (ต้องกำหนดเอง) |
| `DB_USER` | `root` |
| `DB_URL` | `jdbc:mysql://localhost:3306/java_training` |

## Run
```bash
export DB_PASS='your_password'
make run JDBC=/path/to/mysql-connector-j.jar              # ResumeSafe (ค้นหา "Ray")
make run JDBC=/path/to/mysql-connector-j.jar MAIN=java_database.basic_connect.ResumeTest
make clean
```
