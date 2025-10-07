package e1;

public class Ligero extends Barco{
    public Ligero(String nombre, String tipo) {
        super(nombre, "Ligero",tipo, 1500, 0);
    }

    public int sumaDinero(){
        return 400;
    }

    public int costeReparacion(int coste){
        return 500;
    }
}