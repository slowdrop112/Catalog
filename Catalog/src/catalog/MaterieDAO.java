package catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterieDAO {

    public static void adaugaMaterie(Materie materie) {
        String sql = "INSERT OR IGNORE INTO materii (nume) VALUES (?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, materie.getNume());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Eroare la adăugarea materiei: " + e.getMessage());
        }
    }


    public static boolean existaMaterie(String numeMaterie) {
        String sql = "SELECT COUNT(*) FROM materii WHERE nume = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeMaterie);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("Eroare la verificarea materiei: " + e.getMessage());
            return false;
        }
    }

    public static List<Materie> getToateMateriile(Profesor profesorFallback) {
        List<Materie> materii = new ArrayList<>();
        String sql = "SELECT * FROM materii";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nume = rs.getString("nume");
                String profesorId = rs.getString("profesor_id");

                // Atribuim profesorFallback doar ca placeholder
                Materie materie = new Materie(nume);
                materii.add(materie);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea materiilor: " + e.getMessage());
        }

        return materii;
    }

    public static List<String> getNumeMaterii() {
        List<String> numeMaterii = new ArrayList<>();
        String sql = "SELECT nume FROM materii";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                numeMaterii.add(rs.getString("nume"));
            }

        } catch (SQLException e) {
            System.out.println("Eroare la listarea numelor materiilor: " + e.getMessage());
        }

        return numeMaterii;
    }
    public static List<Materie> getToateMateriile() {
        List<Materie> materii = new ArrayList<>();
        String sql = "SELECT * FROM materii";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String nume = rs.getString("nume");
                Materie materie = new Materie(nume);
                materii.add(materie);
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea materiilor: " + e.getMessage());
        }

        return materii;
    }

    public static void actualizeazaProfesorPentruMaterie(String numeMaterie, String profesorId) {
        String sql = "UPDATE materii SET profesor_id = ? WHERE nume = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, profesorId);
            pstmt.setString(2, numeMaterie);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Eroare la actualizarea profesorului pentru materie: " + e.getMessage());
        }
    }

    public static void stergeProfesorDinMaterii(String profesorId) {
        String sql = "UPDATE materii SET profesor_id = NULL WHERE profesor_id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, profesorId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Eroare la curățarea profesorului din materii: " + e.getMessage());
        }
    }



}
