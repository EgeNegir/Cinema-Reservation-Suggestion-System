import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TasarimKaliplariTest {

    // 1. OBSERVER KALIBI TESTİ
    // Amaç: Abonenin  sadece kendi favori türünü doğru algıladığını kanıtlamak.
    @Test
    public void testObserverFavoriTurEslestirmesi() {
        //Hazırlık
        List<String> egeninFavorileri = Arrays.asList("Bilim Kurgu", "Aksiyon");
        MusteriObserver abone = new MusteriObserver(1, "Ege", egeninFavorileri);

        // İşlem ve Doğrulama
        assertTrue(abone.turuSeviyorMu("Bilim Kurgu"), "HATA: Abone favori türünü (Bilim Kurgu) algılayamadı!");
        assertTrue(abone.turuSeviyorMu("Aksiyon"), "HATA: Abone favori türünü (Aksiyon) algılayamadı!");

        // Favorisi olmayan bir tür gelirse reddetmeli (False dönmeli)
        assertFalse(abone.turuSeviyorMu("Dram"), "HATA: Abone sevmediği türü (Dram) yanlışlıkla kabul etti!");
    }

    // 2. OBSERVER KALIBI UÇ DURUM  TESTİ
    // Amaç: Hiç film beğenmemiş (favorisi olmayan) bir müşteri sisteme katıldığında sistemin çökmemesini sağlamak.
    @Test
    public void testObserverBosFavoriListesiKontrolu() {
        // Hazırlık - Favori listesi null (boş) olan bir müşteri
        MusteriObserver abone = new MusteriObserver(2, "Ahmet", null);

        //Doğrulama - Sistem çökmemeli ve doğrudan False dönmeli
        assertFalse(abone.turuSeviyorMu("Komedi"), "HATA: Null listeye sahip abone hata fırlatmalı veya false dönmeliydi!");
    }

    // 3. YÖNETİCİ ÖZELLİĞİ (CRUD) TESTİ
    // Amaç: Bu hafta eklediğimiz 5 parametreli Film nesnesinin verileri doğru kutuladığını (Encapsulation) kanıtlamak.
    @Test
    public void testYoneticiYeniFilmNesnesiOlusturma() {
        //  Yönetici arayüzünden gelen veriler
        String ad = "Star Wars";
        int sure = 140;
        String yonetmen = "George Lucas";
        String tur = "Bilim Kurgu";
        boolean vizyondaMi = true;

        // İşlem
        Film yeniFilm = new Film(ad, sure, yonetmen, tur, vizyondaMi);

        // Doğrulama Yönetici verileri doğru mu?
        assertEquals("Star Wars", yeniFilm.getFilmAdi(), "HATA: Film adı yanlış atandı!");
        assertEquals("Bilim Kurgu", yeniFilm.getTur(), "HATA: Film türü yanlış atandı!");
        assertTrue(yeniFilm.isVizyondaMi(), "HATA: Filmin vizyon durumu yanlış (Arşiv) atandı!");
    }
}