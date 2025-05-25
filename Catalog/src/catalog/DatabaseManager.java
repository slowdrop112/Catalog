package catalog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:catalog.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            // Creează tabel Student
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS studenti (
                    id TEXT PRIMARY KEY,
                    nume TEXT NOT NULL,
                    prenume TEXT NOT NULL
                )
            """);

// Creează tabel Profesori
            stmt.execute("""
    CREATE TABLE IF NOT EXISTS profesori (
        id TEXT PRIMARY KEY,
        nume TEXT NOT NULL,
        prenume TEXT NOT NULL,
        materie TEXT NOT NULL
    )
""");


            // Creează tabel Materii
            stmt.execute("""
               CREATE TABLE IF NOT EXISTS materii (
                     nume TEXT PRIMARY KEY
                 )
                 
            """);

            // Creează tabel Note
            stmt.execute("""
    CREATE TABLE IF NOT EXISTS note (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        student_id TEXT,
        materie TEXT,
        tip TEXT,
        valoare REAL,
        FOREIGN KEY(student_id) REFERENCES studenti(id),
        FOREIGN KEY(materie) REFERENCES materii(nume)
    )
""");


        } catch (SQLException e) {
            System.out.println("Eroare la inițializarea bazei de date: " + e.getMessage());
        }
    }
}
