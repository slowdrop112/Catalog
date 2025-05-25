package catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    public static void adaugaProfesor(Profesor profesor) {
        String sql = "INSERT OR IGNORE INTO profesori (id, nume, prenume, materie) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, profesor.getId());
            pstmt.setString(2, profesor.getNume());
            pstmt.setString(3, profesor.getPrenume());
            pstmt.setString(4, profesor.getMaterieAlocata());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Eroare la adăugarea profesorului: " + e.getMessage());
        }
    }


    public static List<Profesor> getTotiProfesorii() {
        List<Profesor> profesori = new ArrayList<>();
        String sql = "SELECT * FROM profesori ORDER BY CAST(id AS INTEGER) ASC";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                String materie = rs.getString("materie");

                profesori.add(new Profesor(nume, prenume, id, materie));
            }

        } catch (SQLException e) {
            System.out.println("Eroare la citirea profesorilor: " + e.getMessage());
        }

        return profesori;
    }



    public static boolean existaProfesorCuId(String id) {
        String sql = "SELECT COUNT(*) FROM profesori WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Eroare la verificarea ID-ului profesorului: " + e.getMessage());
            return true;
        }
    }

    public static void stergeProfesor(String id) {
        // PAS 1: Ștergem legătura în materii
        MaterieDAO.stergeProfesorDinMaterii(id);

        // PAS 2: Ștergem profesorul din tabelul lui
        String sql = "DELETE FROM profesori WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Profesor șters cu succes.");
            } else {
                System.out.println("❌ Profesorul cu ID-ul " + id + " nu există.");
            }

        } catch (SQLException e) {
            System.out.println("Eroare la ștergerea profesorului: " + e.getMessage());
        }
    }



}
