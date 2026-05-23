import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SeansDAO seansDao = new SeansDAO();
        BiletDAO biletDao = new BiletDAO();
        KullaniciDAO kullaniciDao = new KullaniciDAO();
        FilmDAO filmDao = new FilmDAO();

        while (true) {
            System.out.println("\n===========================================");
            System.out.println("         SİNEMA OTOMASYON SİSTEMİ        ");
            System.out.println("===========================================");
            System.out.println(" [1] Sisteme Giriş Yap");
            System.out.println(" [2] Yeni Müşteri Kaydı");
            System.out.println(" [0] Güvenli Kapatma");
            System.out.print("\n Seçiminiz: ");

            int secim;
            try {
                secim = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sadece listedeki rakamlardan birini giriniz!");
                continue;
            }

            if (secim == 0) {
                System.out.println("Sistem başarıyla kapatıldı. İyi günler!");
                break;
            }

            switch (secim) {
                case 1:
                    System.out.println("\n--- KULLANICI GİRİŞİ ---");
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Şifre: ");
                    String sifre = scanner.nextLine();

                    String[] loginBilgileri = kullaniciDao.girisYap(email, sifre);

                    if (loginBilgileri != null) {
                        int aktifId = Integer.parseInt(loginBilgileri[0]);
                        String rol = loginBilgileri[1];

                        System.out.println("\n🎉 Giriş Başarılı! Hoş geldiniz.");

                        if (rol.equalsIgnoreCase("ADMIN")) {
                            yoneticiPaneli(scanner, filmDao, biletDao, kullaniciDao);
                        } else {
                            // musteriPaneli metoduna kullaniciDao nesnesi de paslanıyor
                            musteriPaneli(scanner, aktifId, seansDao, biletDao, filmDao, kullaniciDao);
                        }
                    } else {
                        System.out.println("❌ Hatalı Email veya Şifre! Giriş reddedildi.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- YENİ MÜŞTERİ KAYDI ---");
                    System.out.print("Ad: "); String ad = scanner.nextLine();
                    System.out.print("Soyad: "); String soyad = scanner.nextLine();
                    System.out.print("Email: "); String mail = scanner.nextLine();
                    System.out.print("Şifre: "); String pass = scanner.nextLine();
                    System.out.print("Telefon No (11 Haneli): "); String tel = scanner.nextLine();

                    kullaniciDao.musteriEkle(new Musteri(ad, soyad, mail, pass, tel));
                    break;

                default:
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
            }
        }
    }

    //  YÖNETİCİ MODÜLÜ (Observer Kalıbı Entegre Edildi)
    private static void yoneticiPaneli(Scanner scanner, FilmDAO filmDao, BiletDAO biletDao, KullaniciDAO kullaniciDao) {

        // --- OBSERVER (GÖZLEMCİ) HAZIRLIĞI ---
        FilmKatalogu katalog = new FilmKatalogu();
        List<MusteriObserver> sistemdekiAboneler = kullaniciDao.tumAboneleriGetir();
        for (MusteriObserver abone : sistemdekiAboneler) {
            katalog.aboneEkle(abone); // Müşteriler bildirim listesine kaydediliyor
        }
        // ------------------------------------

        while (true) {
            System.out.println("\n===========================================");
            System.out.println("          🛠️ YÖNETİCİ KONTROL PANELİ 🛠️       ");
            System.out.println("===========================================");
            System.out.println(" [1] Yeni Film Ekle (Create + Bildirim Gönder)");
            System.out.println(" [2] Sistemden Film Sil (Delete)");
            System.out.println(" [3] Filmin Vizyon Durumunu Değiştir (Update)");
            System.out.println(" [4] Satılan Tüm Biletleri Denetle (Read)");
            System.out.println(" [5] Satılan Bileti İptal Et (Delete)");
            System.out.println(" [0] Oturumu Kapat");
            System.out.print("\n Seçiminiz: ");

            int secim = Integer.parseInt(scanner.nextLine());
            if (secim == 0) {
                System.out.println("Yönetici oturumu kapatıldı.");
                break;
            }

            switch (secim) {
                case 1: // OBSERVER'IN TETİKLENDİĞİ YER
                    System.out.println("\n--- YENİ FİLM EKLEME ---");
                    System.out.print("Film Adı: "); String ad = scanner.nextLine();
                    System.out.print("Süre (Dakika): "); int sure = Integer.parseInt(scanner.nextLine());
                    System.out.print("Yönetmen: "); String yonetmen = scanner.nextLine();
                    System.out.print("Tür (Örn: Bilim Kurgu): "); String tur = scanner.nextLine();
                    System.out.print("Film şu an vizyonda mı? (E/H): ");
                    boolean vizyon = scanner.nextLine().equalsIgnoreCase("E");

                    Film yeniFilm = new Film(ad, sure, yonetmen, tur, vizyon);
                    int yeniId = filmDao.filmEkle(yeniFilm);

                    if (yeniId != -1) {
                        yeniFilm.setFilmId(yeniId); // Veritabanından gelen gerçek ID'yi nesneye koyuyoruz
                        System.out.println("\n--- ABONELERE BİLDİRİM GÖNDERİLİYOR ---");
                        // Observer Kalıbı tetikleniyor!
                        katalog.abonelereBildir(yeniFilm);
                    }
                    break;
                case 2:
                    filmDao.tumFilmleriListele();
                    System.out.print("\nSilmek istediğiniz filmin ID'sini girin: ");
                    int silId = Integer.parseInt(scanner.nextLine());
                    filmDao.filmSil(silId);
                    break;
                case 3:
                    filmDao.tumFilmleriListele();
                    System.out.print("\nDurumunu güncellemek istediğiniz film ID'sini girin: ");
                    int guncelId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Film Vizyonda mı olsun (E) yoksa Arşive mi kaldırılsın (H)?: ");
                    boolean yeniDurum = scanner.nextLine().equalsIgnoreCase("E");
                    filmDao.filmVizyonDurumuGuncelle(guncelId, yeniDurum);
                    break;
                case 4:
                    biletDao.tumBiletleriListele();
                    break;
                case 5:
                    System.out.println("\n--- BİLET İPTAL İŞLEMİ ---");
                    biletDao.tumBiletleriListele();
                    System.out.print("\nİptal etmek istediğiniz Biletin ID'sini girin: ");
                    int iptalId = Integer.parseInt(scanner.nextLine());
                    biletDao.biletIptalEt(iptalId);
                    break;
                default:
                    System.out.println("Geçersiz yönetici işlemi!");
            }
        }
    }

    // 🍿 MÜŞTERİ MODÜLÜ (GÜNCELLENDİ)
    private static void musteriPaneli(Scanner scanner, int aktifMusteriId, SeansDAO seansDao, BiletDAO biletDao, FilmDAO filmDao, KullaniciDAO kullaniciDao) {

        // --- GÜNCELLEME: Müşteri sisteme girdiğinde veritabanındaki bildirimleri listeliyoruz ---
        kullaniciDao.kullaniciBildirimleriniGoster(aktifMusteriId);
        // ---------------------------------------------------------------------------------------

        while (true) {
            System.out.println("\n===========================================");
            System.out.println("             🍿 MÜŞTERİ PANELİ 🍿           ");
            System.out.println("===========================================");
            System.out.println(" [1] Vizyondaki Seansları Listele");
            System.out.println(" [2] Bilet Satın Al (Dinamik Fiyatlandırma)");
            System.out.println(" [3] İzlediğin Filme Puan Ver");
            System.out.println(" [4] Sana Özel Film Önerilerini Gör");
            System.out.println(" [0] Oturumu Kapat");
            System.out.print("\n Seçiminiz: ");

            int secim = Integer.parseInt(scanner.nextLine());
            if (secim == 0) {
                System.out.println("Müşteri oturumu kapatıldı.");
                break;
            }

            switch (secim) {
                case 1:
                    System.out.println("\n--- VİZYONDAKİ SEANSLAR ---");
                    seansDao.seanslariListele();
                    break;
                case 2:
                    System.out.println("\n--- BİLET SATIŞ SİSTEMİ ---");
                    System.out.print("Seans ID: ");
                    int sId = Integer.parseInt(scanner.nextLine());

                    biletDao.doluKoltuklariGoster(sId);

                    System.out.print("Koltuk No (Örn: A5): ");
                    String koltuk = scanner.nextLine();

                    double gercekSeansFiyati = seansDao.getSeansFiyat(sId);
                    IBilet bilet = new SinemaBileti(gercekSeansFiyati);

                    System.out.print("VIP Koltuk istiyor musunuz? (E/H): ");
                    if(scanner.nextLine().equalsIgnoreCase("E")) bilet = new VipKoltukDecorator(bilet);

                    System.out.print("3D Gözlük istiyor musunuz? (E/H): ");
                    if(scanner.nextLine().equalsIgnoreCase("E")) bilet = new UcDGozlukDecorator(bilet);

                    System.out.print("Öğrenci misiniz? (E/H): ");
                    if(scanner.nextLine().equalsIgnoreCase("E")) bilet = new OgrenciIndirimiDecorator(bilet);

                    System.out.println("\nBilet Detayı: " + bilet.getAciklama());
                    System.out.println("Ödenmesi Gereken Toplam Ücret: " + bilet.getUcret() + " TL");

                    biletDao.biletSat(new Bilet(aktifMusteriId, sId, koltuk));
                    break;
                case 3:
                    System.out.println("\n--- FİLM PUANLAMA SİSTEMİ ---");
                    filmDao.tumFilmleriListele();

                    try {
                        System.out.print("Puanlamak istediğiniz Filmin ID'sini girin: ");
                        int fId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Bu filmi beğendiniz mi? (1: Evet, 0: Hayır, veya E/H): ");
                        String girdi = scanner.nextLine().trim();

                        int durum;
                        // Kullanıcı "E" veya "1" girerse beğendi (1) kabul et
                        if (girdi.equalsIgnoreCase("E") || girdi.equals("1")) {
                            durum = 1;
                        }
                        // Kullanıcı "H" veya "0" girerse beğenmedi (0) kabul et
                        else if (girdi.equalsIgnoreCase("H") || girdi.equals("0")) {
                            durum = 0;
                        }
                        // Yanlış bir tuşa basarsa sistemi çökertme, uyar ve menüye dön
                        else {
                            System.out.println("❌ Geçersiz giriş! Lütfen sadece 1/0 veya E/H tuşlayın.");
                            break;
                        }

                        filmDao.filmBegen(aktifMusteriId, fId, durum);

                    } catch (NumberFormatException ex) {
                        System.out.println("❌ Hata: Film ID'si için harf değil, sadece geçerli bir rakam girmelisiniz!");
                    }
                    break;
                case 4:
                    filmDao.kullaniciyaOzelFilmOnerisi(aktifMusteriId);
                    break;
                default:
                    System.out.println("Geçersiz müşteri işlemi!");
            }
        }
    }
}