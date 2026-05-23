import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmKatalogu implements ISubject {
    private List<IObserver> aboneler = new ArrayList<>();

    @Override
    public void aboneEkle(IObserver observer) { aboneler.add(observer); }

    @Override
    public void aboneCikar(IObserver observer) { aboneler.remove(observer); }

    @Override
    public void abonelereBildir(Film yeniFilm) {
        String durum = yeniFilm.isVizyondaMi() ? "VİZYONA GİRDİ" : "ARŞİVE EKLENDİ";
        String mesaj = "Sevdiğin '" + yeniFilm.getTur() + "' türünde yeni film eklendi! [ID: " + yeniFilm.getFilmId() + " | Ad: " + yeniFilm.getFilmAdi() + "] - Durum: " + durum;

        // Bildirimi veritabanına kaydedecek olan SQL sorgumuz
        String sql = "INSERT INTO Bildirimler (kullanici_id, mesaj) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            for (IObserver abone : aboneler) {
                if (abone instanceof MusteriObserver) {
                    MusteriObserver musteri = (MusteriObserver) abone;

                    if (musteri.turuSeviyorMu(yeniFilm.getTur())) {
                        // 1. Yönetici ekranında anlık (canlı) olarak göster
                        musteri.bildirimAl(mesaj);

                        // 2. Müşteri daha sonra girdiğinde görsün diye veritabanına kaydet
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setInt(1, musteri.getKullaniciId());
                            pstmt.setString(2, mesaj);
                            pstmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Bildirimler veritabanına kaydedilirken hata oluştu. (Bildirimler tablosunu oluşturduğunuzdan emin olun): " + e.getMessage());
        }
    }
}