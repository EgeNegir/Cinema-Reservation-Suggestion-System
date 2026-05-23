import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FilmDAO {

    // Yeni eklenen filmin ID'sini döndürür (Observer bildirimi için gerekli)
    public int filmEkle(Film film) {
        String sql = "INSERT INTO Film (film_adi, sure, yonetmen, tur, vizyonda_mi) VALUES (?, ?, ?, ?, ?)";
        int eklenenFilmId = -1;
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, film.getFilmAdi());
            pstmt.setInt(2, film.getSure());
            pstmt.setString(3, film.getYonetmen());
            pstmt.setString(4, film.getTur());
            pstmt.setBoolean(5, film.isVizyondaMi());
            pstmt.executeUpdate();

            // Veritabanının atadığı otomatik ID'yi yakalıyoruz
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                eklenenFilmId = rs.getInt(1);
                System.out.println("🎬 Yeni film başarıyla kataloğa eklendi.");
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return eklenenFilmId;
    }

    // YÖNETİCİ ÖZELLİĞİ - DELETE: Film Silme
    public void filmSil(int filmId) {
        String sql = "DELETE FROM Film WHERE film_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            int sonuc = pstmt.executeUpdate();
            if (sonuc > 0) {
                System.out.println("🗑️ Film sistemden başarıyla silindi.");
            } else {
                System.out.println("⚠️ Bu ID'ye sahip bir film bulunamadı.");
            }
        } catch (SQLException e) {
            System.out.println("Hata: Film silinemedi. Seans tablosunda bu filme ait aktif kayıtlar olabilir!");
        }
    }

    // YÖNETİCİ ÖZELLİĞİ - UPDATE: Vizyon Durumu Güncelleme
    public void filmVizyonDurumuGuncelle(int filmId, boolean vizyondaMi) {
        String sql = "UPDATE Film SET vizyonda_mi = ? WHERE film_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, vizyondaMi);
            pstmt.setInt(2, filmId);
            pstmt.executeUpdate();
            System.out.println("🔄 Filmin vizyon durumu başarıyla güncellendi.");
        } catch (SQLException e) {
            System.out.println("Hata: Güncelleme işlemi başarısız oldu.");
        }
    }

    public void filmBegen(int kullaniciId, int filmId, int durum) {
        String sql = "INSERT INTO FilmDegerlendirme (kullanici_id, film_id, begeni_durumu) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kullaniciId);
            pstmt.setInt(2, filmId);
            pstmt.setInt(3, durum);
            pstmt.executeUpdate();
            System.out.println("Geri bildiriminiz başarıyla kaydedildi!");
        } catch (SQLException e) {
            System.out.println("Hata: Bu filmi zaten oyladınız.");
        }
    }

    public void kullaniciyaOzelFilmOnerisi(int aktifKullaniciId) {
        String sql = "SELECT DISTINCT f2.film_adi, f2.tur, f2.vizyonda_mi " +
                "FROM FilmDegerlendirme fd " +
                "JOIN Film f1 ON fd.film_id = f1.film_id " +
                "JOIN Film f2 ON f1.tur = f2.tur " +
                "WHERE fd.kullanici_id = ? AND fd.begeni_durumu = 1 " +
                "AND f2.film_id NOT IN (SELECT film_id FROM FilmDegerlendirme WHERE kullanici_id = ?) " +
                "LIMIT 5";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, aktifKullaniciId);
            pstmt.setInt(2, aktifKullaniciId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- SİZE ÖZEL FİLM ÖNERİLERİ ---");
            boolean oneriVarMi = false;
            while (rs.next()) {
                oneriVarMi = true;
                String durum = rs.getBoolean("vizyonda_mi") ? "[🎫 VİZYONDA]" : "[🎬 ARŞİV]";
                System.out.println(durum + " " + rs.getString("film_adi") + " (" + rs.getString("tur") + ")");
            }
            if (!oneriVarMi) System.out.println("Henüz öneri oluşturmak için yeterli veriniz yok.");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void tumFilmleriListele() {
        String sql = "SELECT film_id, film_adi, tur, vizyonda_mi FROM Film ORDER BY vizyonda_mi DESC, film_id ASC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- SİSTEMDEKİ TÜM FİLMLER ---");
            while (rs.next()) {
                int id = rs.getInt("film_id");
                String ad = rs.getString("film_adi");
                String tur = rs.getString("tur");
                boolean vizyonda = rs.getBoolean("vizyonda_mi");

                String durum = vizyonda ? "[🎫 VİZYONDA]" : "[🎬 ARŞİV]";
                System.out.println("ID: " + id + " | " + durum + " " + ad + " (" + tur + ")");
            }
            System.out.println("-------------------------------");
        } catch (SQLException e) {
            System.out.println("Filmler listelenirken bir hata oluştu.");
        }
    }
}