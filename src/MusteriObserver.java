import java.util.List;

public class MusteriObserver implements IObserver {
    private int kullaniciId;
    private String ad;
    private List<String> favoriTurler;

    public MusteriObserver(int kullaniciId, String ad, List<String> favoriTurler) {
        this.kullaniciId = kullaniciId;
        this.ad = ad;
        this.favoriTurler = favoriTurler;
    }

    // Veritabanına kayıt yaparken bize lazım olan ID'yi veren Getter metodu
    public int getKullaniciId() {
        return kullaniciId;
    }

    public boolean turuSeviyorMu(String tur) {
        if (favoriTurler == null) return false;
        return favoriTurler.contains(tur);
    }

    @Override
    public void bildirimAl(String mesaj) {
        System.out.println("🔔 BİLDİRİM [" + ad + "] -> " + mesaj);
    }
}