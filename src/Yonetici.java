public class Yonetici extends Kullanici {

    // Boş yapıcı metot (Constructor)
    public Yonetici() {
    }
    // Dolu yapıcı metot (Sadece üst sınıf olan Kullanicidan miras alınan özellikler var)
    public Yonetici(String ad, String soyad, String email, String sifre) {
        super(ad, soyad, email, sifre);
    }
}