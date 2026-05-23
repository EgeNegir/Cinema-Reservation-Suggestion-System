import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KullaniciDAO {

    // GİRİŞ KONTROLÜ: Giriş başarılıysa [id, rol] döndürür, başarısızsa null döner.
    public String[] girisYap(String email, String sifre) {
        String sql = "SELECT kullanici_id, rol FROM Kullanici WHERE email = ? AND sifre = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, sifre);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String[] veriler = new String[2];
                veriler[0] = String.valueOf(rs.getInt("kullanici_id"));
                veriler[1] = rs.getString("rol");
                return veriler;
            }
        } catch (SQLException e) {
            System.out.println("Giriş yapılırken bir veritabanı hatası oluştu: " + e.getMessage());
        }
        return null;
    }

    public void musteriEkle(Musteri musteri) {
        if (musteri.getAd().isEmpty() || musteri.getSoyad().isEmpty()) {
            System.out.println("Hata: Ad veya soyad boş bırakılamaz.");
            return;
        }

        if (!musteri.getAd().matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ ]+$") || !musteri.getSoyad().matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ ]+$")) {
            System.out.println("Hata: Ad ve soyad sadece harflerden oluşmalıdır (Sayı içeremez).");
            return;
        }

        if (!musteri.getEmail().contains("@")) {
            System.out.println("Hata: Geçersiz bir e-posta adresi girdiniz.");
            return;
        }

        if (musteri.getTelefonNo().length() != 11) {
            System.out.println("Hata: Telefon numarası 11 haneli olmalıdır.");
            return;
        }

        String sqlKullanici = "INSERT INTO Kullanici (ad, soyad, email, sifre) VALUES (?, ?, ?, ?)";
        String sqlMusteri = "INSERT INTO Musteri (musteri_id, telefon_no) VALUES (?, ?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sqlKullanici, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, musteri.getAd());
            ps1.setString(2, musteri.getSoyad());
            ps1.setString(3, musteri.getEmail());
            ps1.setString(4, musteri.getSifre());
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);

                PreparedStatement ps2 = conn.prepareStatement(sqlMusteri);
                ps2.setInt(1, id);
                ps2.setString(2, musteri.getTelefonNo());
                ps2.executeUpdate();

                System.out.println("Kayıt başarılı! Müşteri ID numaranız: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Hata: Kayıt işlemi başarısız oldu. Bu e-posta kullanılıyor olabilir.");
        }
    }

    // --- YENİ EKLENEN OBSERVER (GÖZLEMCİ) METODU ---
    // Sistemdeki tüm müşterileri ve onların 1 (Beğendim) puanı verdiği film türlerini çeker
    public List<MusteriObserver> tumAboneleriGetir() {
        List<MusteriObserver> aboneler = new ArrayList<>();
        String sql = "SELECT k.kullanici_id, k.ad, " +
                "(SELECT string_agg(DISTINCT f.tur, ',') " +
                " FROM FilmDegerlendirme fd " +
                " JOIN Film f ON fd.film_id = f.film_id " +
                " WHERE fd.kullanici_id = k.kullanici_id AND fd.begeni_durumu = 1) AS favoriler " +
                "FROM Kullanici k WHERE k.rol = 'MUSTERI'";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("kullanici_id");
                String ad = rs.getString("ad");
                String favorilerStr = rs.getString("favoriler");

                List<String> favoriListesi = new ArrayList<>();
                if (favorilerStr != null) {
                    favoriListesi = Arrays.asList(favorilerStr.split(","));
                }
                // Her müşteriyi Observer nesnesine çevirip listeye ekliyoruz
                aboneler.add(new MusteriObserver(id, ad, favoriListesi));
            }
        } catch (SQLException e) {
            System.out.println("Aboneler çekilirken hata: " + e.getMessage());
        }
        return aboneler;
    }


    // Giriş yapan kullanıcının geçmiş bildirimlerini veritabanından çekip listeler
    public void kullaniciBildirimleriniGoster(int kullaniciId) {
        String sql = "SELECT mesaj, tarih FROM Bildirimler WHERE kullanici_id = ? ORDER BY tarih DESC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kullaniciId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n🔔 --- BİLDİRİM KUTUNUZ ---");
                boolean bildirimVarMi = false;
                while (rs.next()) {
                    bildirimVarMi = true;
                    String tarih = rs.getTimestamp("tarih").toString().substring(0, 16); // Okunabilir tarih formatı
                    System.out.println("[" + tarih + "] " + rs.getString("mesaj"));
                }
                if (!bildirimVarMi) {
                    System.out.println("Henüz yeni bir bildiriminiz yok.");
                }
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Bildirim kutusu yüklenirken hata: " + e.getMessage());
        }
    }
}