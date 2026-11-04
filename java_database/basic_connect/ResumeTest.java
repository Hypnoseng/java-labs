package java_database.basic_connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

class ResumeTest {
    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
            System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/java_training"),
            System.getenv().getOrDefault("DB_USER", "root"),
            System.getenv("DB_PASS")
        );

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("Select * from Resume ");

        while (resultSet.next()) {
            System.out.println(
                "ID : " + resultSet.getString("Id") + 
                "   Name: " + resultSet.getString("Name") +
                "   Email: " + resultSet.getString("Email") +
                "   Age: " + resultSet.getString("Age")
            );
        }

        statement.close();
        resultSet.close();
    }
}