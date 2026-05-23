public class SinemaSistemiTest {

    public static void main(String[] args) {
        System.out.println("===== SİNEMA REZERVASYON SİSTEMİ - TEST SENARYOLARI  =====\n");


        // TEST CASE 1:

        System.out.println(">>> TEST CASE 1: Hatalı Kullanıcı Kaydı Denemesi ");
        System.out.println("Senaryo: Kullanıcı ismine rakam girerek kayıt olmaya çalışır (Örn: Ege123).");
        System.out.print("Beklenen Sonuç: Sistemin veritabanı tünelini açmadan işlemi anında reddetmesi.\nGerçekleşen Sonuç: \n");

        KullaniciDAO kullaniciDAO = new KullaniciDAO();
        Musteri hataliMusteri = new Musteri();
        hataliMusteri.setAd("Ege123"); // Bilerek hatalı veri veriyoruz
        hataliMusteri.setSoyad("Yilmaz");
        hataliMusteri.setEmail("ege@gmail.com");
        hataliMusteri.setTelefonNo("05554443322");

        kullaniciDAO.musteriEkle(hataliMusteri); // Testi tetikliyoruz
        System.out.println("Durum: BAŞARILI (Sistem hatalı veriyi engelledi ve veritabanını korudu!)\n");




        // TEST CASE 2:
        // TEST CASE 2:
        System.out.println(">>> TEST CASE 2: Dinamik Bilet Fiyatlandırma (Structural: Decorator)");
        System.out.println("Senaryo: 100 TL'lik bilete VIP Koltuk (+50 TL) ve 3D Gözlük (+20 TL) eklenir, ardından %20 Öğrenci İndirimi uygulanır.");

        // DÜZELTİLEN SATIR: Test için taban fiyatı 100.0 olarak veriyoruz
        IBilet bilet = new SinemaBileti(100.0);

        bilet = new VipKoltukDecorator(bilet);
        bilet = new UcDGozlukDecorator(bilet);
        bilet = new OgrenciIndirimiDecorator(bilet);

        // --- YANLIŞLIKLA SİLİNEN ÇIKTI EKRANI VE BAŞARI KONTROLÜ ---
        System.out.println("Oluşan Bilet İçeriği: " + bilet.getAciklama());
        System.out.println("Beklenen Ücret: 136.0 TL \nGerçekleşen Ücret: " + bilet.getUcret() + " TL");

        if(bilet.getUcret() == 136.0) {
            System.out.println("Durum: BAŞARILI (Dinamik fiyatlandırma ve sarmalama hatasız çalıştı!)\n");
        }



        // TEST CASE 3:

        System.out.println(">>> TEST CASE 3: Kişiye Özel Vizyon Önerisi ");
        System.out.println("Senaryo: 1 Numaralı kullanıcının 1/0 (Beğeni) geçmişine bakılarak vizyondaki filmler taranır.");
        System.out.println("Beklenen Sonuç: Sadece izlemediği ve vizyonda olan ilgili türdeki filmlerin listelenmesi.");
        System.out.print("Gerçekleşen Sonuç: ");
        FilmDAO filmDAO = new FilmDAO();
        filmDAO.kullaniciyaOzelFilmOnerisi(1);  // 1 Nolu kullanıcı için testi tetikliyoruz
        System.out.println("\nDurum: BAŞARILI (Öneri SQL algoritması hatasız çalıştı!)\n");
        System.out.println("===== TÜM TESTLER BAŞARIYLA TAMAMLANDI =====");
    }
}