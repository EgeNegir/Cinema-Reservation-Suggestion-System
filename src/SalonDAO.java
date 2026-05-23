import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalonDAO {
    public void salonEkle(Salon salon) {
        String sql = "INSERT INTO Salon (salon_adi, kapasite) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, salon.getSalonAdi());
            pstmt.setInt(2, salon.getKapasite());
            pstmt.executeUpdate();
            System.out.println("Başarılı! Salon eklendi: " + salon.getSalonAdi());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}