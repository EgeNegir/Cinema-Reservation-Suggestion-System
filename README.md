# Cinema Reservation & Suggestion System

İnönü Üniversitesi Nesneye Yönelik Yazılım Mühendisliği dersi kapsamında geliştirilmiş, Java ve PostgreSQL tabanlı gelişmiş bir sinema otomasyon ve kişiselleştirilmiş içerik yönetim platformudur.

## 🌟 Projenin Öne Çıkan Özellikleri 

- **Akıllı Film Öneri Modülü:** Kullanıcıların geçmişte izleyip değerlendirdiği filmlerin türleri analiz edilerek,
   veritabanı üzerindeki (PostgreSQL JOIN yapıları) sorgularla kullanıcıya kişiselleştirilmiş yeni film tavsiyeleri sunulur.

- **Çifte Rezervasyon Kalkanı:** Aynı seans ve koltuğa aynı anda iki farklı bilet kesilmesini önlemek amacıyla,
  veritabanı seviyesinde `UNIQUE` kısıtlamaları ve Java katmanında güvenli hata yakalama  mekanizmaları kurularak tam veri bütünlüğü sağlanmıştır.

- **Rol Tabanlı Erişim :** Yönetici ve Müşteri rolleri için tamamen izole edilmiş paneller.
  Yöneticiler film/seans CRUD işlemlerini yönetirken, müşteriler rezervasyon, puanlama ve tavsiye modüllerini kullanır.

## 🛠 Kullanılan Teknolojiler
- **Dil:** Java 17+
- **Veritabanı:** PostgreSQL (JDBC)
- **Test Altyapısı:** JUnit 5
- **Tasarım Kalıpları:** Singleton, Decorator, Observer

## 🧩 Uygulanan Tasarım Kalıpları ve Mimari
Sistem "Gevşek Bağlılık" (Loose Coupling) prensipleriyle modüler olarak tasarlanmıştır:
- **Singleton:** Veritabanı bağlantı havuzunun (`DatabaseConnection`) tekil nesne üzerinden güvenli ve performanslı yönetimi.
- **Decorator:** Bilet fiyatlandırmasında dinamik özelliklerin (VIP Koltuk, 3D Gözlük, Öğrenci İndirimi) çalışma zamanında (runtime) ana fiyata eklenmesi.
- **Observer:** Sisteme yeni bir film eklendiğinde, o filmin türünü favorilerine eklemiş olan müşterilere **anlık bildirim** gönderilmesi ve bu bildirimlerin veritabanında kalıcı olarak arşivlenmesi.

## 🚀 Kurulum ve Çalıştırma
Projeyi kendi yerel ortamınızda test etmek için:
1. PostgreSQL üzerinde yeni bir veritabanı oluşturun.
2. Proje dizininde yer alan `src/database.sql` dosyasındaki script'i çalıştırarak gerekli tabloları ve varsayılan yönetici hesaplarını yükleyin.
3. `DatabaseConnection.java` sınıfı içerisindeki URL, kullanıcı adı ve şifre bilgilerini kendi veritabanı ayarlarınıza göre yapılandırın.
4. IDE üzerinden `Test.java` (Ana sınıf) dosyasını derleyip başlatın.

## 🧪 Birim Testleri (Unit Testing)
Projenin çekirdek iş mantığı, öneri algoritmaları ve tasarım kalıplarının doğruluğu, uç durumlar  göz önüne alınarak **JUnit 5** ile kapsamlı birim testlerinden (White-Box Testing) geçirilmiştir. Test sınıfları `src/test` dizininde incelenebilir.
