package e1;

public class Pesado extends Barco{
    public Pesado(String nombre, String tipo){
        super(nombre, "Pesado", tipo, 3000, 0);
    }

    public int sumaDinero(){
        return 600;
    }

    public int costeReparacion(){
        return 700;
    }
}
