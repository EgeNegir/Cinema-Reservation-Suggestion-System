
-- SİNEMA REZERVASYON VE İÇERİK YÖNETİM SİSTEMİ
-- PostgreSQL Veritabanı Kurulum Dosyası


-- 1. KULLANICI TABLOSU (Admin ve Müşterilerin Ortak Tablosu)
CREATE TABLE Kullanici (
                           kullanici_id SERIAL PRIMARY KEY,
                           ad VARCHAR(50) NOT NULL,
                           soyad VARCHAR(50) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           sifre VARCHAR(50) NOT NULL,
                           rol VARCHAR(10) DEFAULT 'MUSTERI'
);

-- 2. MÜŞTERİ TABLOSU (Sadece Müşterilere Özel Bilgiler)
CREATE TABLE Musteri (
                         musteri_id INT PRIMARY KEY REFERENCES Kullanici(kullanici_id) ON DELETE CASCADE,
                         telefon_no VARCHAR(11)
);

-- 3. FİLM TABLOSU
CREATE TABLE Film (
                      film_id SERIAL PRIMARY KEY,
                      film_adi VARCHAR(100) NOT NULL,
                      sure INT NOT NULL,
                      yonetmen VARCHAR(100),
                      tur VARCHAR(50),
                      vizyonda_mi BOOLEAN DEFAULT TRUE
);

-- 4. SEANS TABLOSU
CREATE TABLE Seans (
                       seans_id SERIAL PRIMARY KEY,
                       film_id INT REFERENCES Film(film_id) ON DELETE CASCADE,
                       salon_adi VARCHAR(50),
                       fiyat DECIMAL(10, 2) NOT NULL
);

-- 5. BİLET TABLOSU (Çift Rezervasyonu Önleyen UNIQUE Kısıtlamalı)
CREATE TABLE Bilet (
                       bilet_id SERIAL PRIMARY KEY,
                       kullanici_id INT REFERENCES Kullanici(kullanici_id) ON DELETE CASCADE,
                       seans_id INT REFERENCES Seans(seans_id) ON DELETE CASCADE,
                       koltuk_no VARCHAR(5) NOT NULL,
                       UNIQUE (seans_id, koltuk_no)
);

-- 6. FİLM DEĞERLENDİRME TABLOSU (Kullanıcı aynı filmi 2 kez oylayamaz)
CREATE TABLE FilmDegerlendirme (
                                   kullanici_id INT REFERENCES Kullanici(kullanici_id) ON DELETE CASCADE,
                                   film_id INT REFERENCES Film(film_id) ON DELETE CASCADE,
                                   begeni_durumu INT NOT NULL,
                                   UNIQUE (kullanici_id, film_id)
);

-- 7. BİLDİRİMLER TABLOSU (Observer Kalıbı İçin Kullanılan Posta Kutusu)
CREATE TABLE Bildirimler (
                             bildirim_id SERIAL PRIMARY KEY,
                             kullanici_id INT REFERENCES Kullanici(kullanici_id) ON DELETE CASCADE,
                             mesaj TEXT NOT NULL,
                             tarih TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- SİSTEMİ TEST ETMEK İÇİN VARSAYILAN VERİLER


-- Varsayılan Yönetici (Admin) Hesabı Ekleme
INSERT INTO Kullanici (ad, soyad, email, sifre, rol)
VALUES ('Admin', 'Sistem', 'admin@sinema.com', '1234', 'ADMIN');