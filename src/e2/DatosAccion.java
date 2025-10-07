package e2;

public class DatosAccion {
    private double cierre;
    private double maximo;
    private double minimo;
    private int volumen;

    public DatosAccion(double cierre, double maximo, double minimo, int volumen) {
        this.cierre = cierre;
        this.maximo = maximo;
        this.minimo = minimo;
        this.volumen = volumen;
    }

    public double getCierre() { return cierre; }
    public double getMaximo() { return maximo; }
    public double getMinimo() { return minimo; }
    public int getVolumen() { return volumen; }
}
