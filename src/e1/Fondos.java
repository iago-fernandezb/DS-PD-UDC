package e1;

public class Fondos {
    public double fondos;

    public Fondos(double fondos) {
        this.fondos = fondos;
    }

    public double getFondos() {
        return fondos;
    }

    public void quitarPresupuesto(double fondos){
        this.fondos = this.fondos - fondos;
    }

    public void anadirPresupuesto(double fondos){
        this.fondos = this.fondos + fondos;
    }

    public void aumentarFondos(Barco barco) {
        this.fondos += barco.sumaDinero();
    }

    public boolean disminuirFondos(Barco barco) {
        double aux = this.fondos - barco.costeReparacion();
        if (aux < 0) {
            return false;
        }
        this.fondos = aux;
        return true;
    }
}
