import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BiletDecoratorTest {

    @Test
    public void testSadeceVipKoltukHesaplamasi() {
        // Arrange (Hazırlık)
        IBilet bilet = new SinemaBileti(100.0);

        // Act (İşlem)
        bilet = new VipKoltukDecorator(bilet); // 100 + 50 = 150 olmalı

        // Assert (Doğrulama - İşte hocanın görmek istediği kod!)
        assertEquals(150.0, bilet.getUcret(), 0.001, "VIP bilet fiyatı yanlış hesaplandı!");
        assertTrue(bilet.getAciklama().contains("VIP Koltuk"));
    }

    @Test
    public void testZincirlemeDecoratorHesaplamasi() {
        // Arrange
        IBilet bilet = new SinemaBileti(100.0);

        // Act - Karmaşık kombinasyon (White-Box yapısal test)
        bilet = new VipKoltukDecorator(bilet);      // +50 = 150
        bilet = new UcDGozlukDecorator(bilet);      // +20 = 170
        bilet = new OgrenciIndirimiDecorator(bilet);// 170 * 0.8 = 136

        // Assert
        assertEquals(136.0, bilet.getUcret(), 0.001, "Zincirleme indirim/zam matematiği hatalı!");
        assertTrue(bilet.getAciklama().contains("3D Gözlük"));
        assertTrue(bilet.getAciklama().contains("Öğrenci İndirimi"));
    }
}