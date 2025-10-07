package e1;

public class Ultrapesado extends Barco{
    public Ultrapesado(String nombre, String tipo) {
        super(nombre, "Ultrapesado", tipo , 6000, 0);
    }
    public int sumaDinero(){
        return 800;
    }

    public int costeReparacion(){
        return 900;
    }
}
