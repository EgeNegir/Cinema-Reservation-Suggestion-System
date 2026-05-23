public class OgrenciIndirimiDecorator extends BiletDecorator {
    public OgrenciIndirimiDecorator(IBilet bilet) { super(bilet); }
    @Override
    public String getAciklama() { return bilet.getAciklama() + " (Öğrenci İndirimi)"; }
    @Override
    public double getUcret() { return bilet.getUcret() * 0.8; } // %20 indirim
}