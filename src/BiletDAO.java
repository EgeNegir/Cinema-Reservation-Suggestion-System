import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BiletDAO {

    // YÖNETİCİ ÖZELLİĞİ - READ: Satılan Tüm Biletleri Görme
    public void tumBiletleriListele() {
        String sql = "SELECT b.bilet_id, k.ad, k.soyad, f.film_adi, b.koltuk_no " +
                "FROM Bilet b " +
                "JOIN Seans s ON b.seans_id = s.seans_id " +
                "JOIN Film f ON s.film_id = f.film_id " +
                "JOIN Kullanici k ON b.musteri_id = k.kullanici_id " +
                "ORDER BY b.bilet_id ASC";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- SATILAN TÜM BİLETLERİN LİSTESİ ---");
            boolean biletVarMi = false;
            while (rs.next()) {
                biletVarMi = true;
                System.out.println("Bilet ID: " + rs.getInt("bilet_id") +
                        " | Müşteri: " + rs.getString("ad") + " " + rs.getString("soyad") +
                        " | Film: " + rs.getString("film_adi") +
                        " | Koltuk: " + rs.getString("koltuk_no"));
            }
            if (!biletVarMi) System.out.println("Sistemde henüz kesilmiş bilet bulunmamaktadır.");
            System.out.println("---------------------------------------");
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Bilet listesi çekilirken hata oluştu: " + e.getMessage());
        }
    }

    public void doluKoltuklariGoster(int seansId) {
        String sql = "SELECT koltuk_no FROM Bilet WHERE seans_id = ?";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, seansId);
            ResultSet rs = pstmt.executeQuery();

            System.out.print("🚫 Dolu Koltuklar: ");
            boolean doluKoltukVarMi = false;
            while (rs.next()) {
                System.out.print("[" + rs.getString("koltuk_no") + "] ");
                doluKoltukVarMi = true;
            }
            if (!doluKoltukVarMi) {
                System.out.print("Yok (Tüm koltuklar boş!)");
            }
            System.out.println();
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Koltuk durumu çekilirken hata oluştu: " + e.getMessage());
        }
    }

    public void biletSat(Bilet bilet) {
        String kontrolSQL = "SELECT * FROM Bilet WHERE seans_id = ? AND koltuk_no = ?";
        String insertSQL = "INSERT INTO Bilet (musteri_id, seans_id, koltuk_no) VALUES (?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement psKontrol = conn.prepareStatement(kontrolSQL);
            psKontrol.setInt(1, bilet.getSeansId());
            psKontrol.setString(2, bilet.getKoltukNo());
            ResultSet rs = psKontrol.executeQuery();

            if (rs.next()) {
                System.out.println("Hata: Seçtiğiniz koltuk zaten dolu!");
            } else {
                PreparedStatement psInsert = conn.prepareStatement(insertSQL);
                psInsert.setInt(1, bilet.getMusteriId());
                psInsert.setInt(2, bilet.getSeansId());
                psInsert.setString(3, bilet.getKoltukNo());
                psInsert.executeUpdate();
                System.out.println("Biletiniz başarıyla kesildi. Koltuk: " + bilet.getKoltukNo());
            }
        } catch (SQLException e) {
            System.out.println("Hata: Veritabanı işlemi sırasında bir sorun oluştu.");
        }
    }
    // YÖNETİCİ ÖZELLİĞİ - DELETE: Satılan Bileti İptal Etme
    public void biletIptalEt(int biletId) {
        String sql = "DELETE FROM Bilet WHERE bilet_id = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, biletId);

            int etkilenenSatir = pstmt.executeUpdate();
            if (etkilenenSatir > 0) {
                System.out.println("✅ Bilet başarıyla iptal edildi. Koltuk artık başkaları için boş durumda!");
            } else {
                System.out.println("⚠️ Hata: Bu ID'ye (" + biletId + ") sahip bir bilet bulunamadı.");
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Bilet iptal edilirken veritabanı hatası oluştu: " + e.getMessage());
        }
    }
}