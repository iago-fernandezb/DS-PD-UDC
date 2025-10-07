package e1;

public class Ultraligero extends Barco {
    public Ultraligero(String nombre, String tipo) {
        super(nombre, "Ultraligero", tipo, 1000, 0);
    }

    public int sumaDinero(){
        return 200;
    }

    public int costeReparacion(){
        return 300;
    }

    public int express(Flota flota){
        if(flota.listarEnReparacion() == 0) {
            this.setEstado(new Operativo());
            return 0;
        }
        return this.costeReparacion();
    }

    @Override
    public void solicitarReparacion(Flota flota) {
        if(flota.listarEnReparacion() == 0) {
            System.out.println(this.getNombre() + " (Ultraligero) ha sido reparado instantáneamente y sin costo.");
            this.setEstado(new Operativo());
        }
        else{
            System.out.println(this.getNombre() + " (Ultraligero) no se puede reparar de forma express porque hay mas barcos reparandose.\n");
            this.setEstado(new EnReparacion());
            flota.fondos.disminuirFondos(this);
        }
    }
}
