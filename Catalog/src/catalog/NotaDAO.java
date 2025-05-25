package catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public static void adaugaNota(Nota nota) {
        String sql = "INSERT INTO note (student_id, materie, tip, valoare) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nota.getStudent().getId());
            pstmt.setString(2, nota.getMaterie().getNume());
            pstmt.setString(3, nota.getTip().name());
            pstmt.setDouble(4, nota.getValoare());
            pstmt.executeUpdate();

            System.out.println("✅ Nota a fost adăugată cu succes.");

        } catch (SQLException e) {
            System.out.println("❌ Eroare la adăugarea notei: " + e.getMessage());
        }
    }

    public static List<Nota> getNotePentruStudent(Student student) {
        List<Nota> note = new ArrayList<>();
        String sql = "SELECT materie, tip, valoare FROM note WHERE student_id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String materie = rs.getString("materie");
                String tipStr = rs.getString("tip");
                double valoare = rs.getDouble("valoare");

                Materie m = new Materie(materie);
                TipNota tip = TipNota.valueOf(tipStr);

                note.add(new Nota(student, m, valoare, tip));
            }

        } catch (SQLException e) {
            System.out.println("❌ Eroare la citirea notelor: " + e.getMessage());
        }

        return note;
    }
}

