public class UcDGozlukDecorator extends BiletDecorator {
    public UcDGozlukDecorator(IBilet bilet) { super(bilet); }
    @Override
    public String getAciklama() { return bilet.getAciklama() + ", 3D Gözlük"; }
    @Override
    public double getUcret() { return bilet.getUcret() + 20.0; }
}