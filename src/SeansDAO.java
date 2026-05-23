import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeansDAO {
    // SeansDAO.java sınıfının içine eklenecek metot
    public double getSeansFiyat(int seansId) {
        double fiyat = 100.0; // Veritabanında bulamazsa varsayılan 100 TL dönsün


        String sql = "SELECT bilet_fiyati FROM seans WHERE seans_id = ?";

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, seansId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                fiyat = rs.getDouble("bilet_fiyati");
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Fiyat bilgisi çekilirken veritabanı hatası oluştu: " + e.getMessage());
        }

        return fiyat;
    }

    public void seansEkle(Seans seans) {
        String sql = "INSERT INTO Seans (film_id, salon_id, tarih, saat, bilet_fiyati) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seans.getFilmId());
            pstmt.setInt(2, seans.getSalonId());
            pstmt.setDate(3, seans.getTarih());
            pstmt.setTime(4, seans.getSaat());
            pstmt.setDouble(5, seans.getBiletFiyati());
            pstmt.executeUpdate();
            System.out.println("Seans başarıyla eklendi.");
        } catch (SQLException e) {
            System.out.println("Seans eklenirken hata oluştu.");
        }
    }

    public void seanslariListele() {
        String sql = "SELECT s.seans_id, f.film_adi, sa.salon_adi, s.tarih, s.saat, s.bilet_fiyati " +
                "FROM Seans s " +
                "JOIN Film f ON s.film_id = f.film_id " +
                "JOIN Salon sa ON s.salon_id = sa.salon_id";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- VİZYONDAKİ SEANSLAR ---");
            while (rs.next()) {
                int id = rs.getInt("seans_id");
                String film = rs.getString("film_adi");
                String salon = rs.getString("salon_adi");
                String tarih = rs.getDate("tarih").toString();
                String saat = rs.getTime("saat").toString();
                double fiyat = rs.getDouble("bilet_fiyati");

                System.out.println("ID: " + id + " | Film: " + film + " | Salon: " + salon + " | Zaman: " + tarih + " " + saat + " | Fiyat: " + fiyat + " TL");
            }
            System.out.println("---------------------------\n");

        } catch (SQLException e) {
            System.out.println("Seanslar getirilirken hata oluştu.");
        }
    }
}