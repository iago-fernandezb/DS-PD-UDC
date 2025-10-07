package e1;

abstract class Barco {
    public String nombre;
    public String clase;
    public String tipo;
    public double peso;
    public EstadoBarco estadoActual;
    public int Nmisiones;

    public Barco(String nombre,String clase, String tipo, double peso, int Nmisiones) {
        this.nombre = nombre;
        this.clase = clase;
        this.tipo = tipo;
        this.peso = peso;
        this.Nmisiones = 0;
        this.estadoActual = new Operativo();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPeso() {
        return peso;
    }

    public int getNmisiones() {
        return Nmisiones;
    }

    public EstadoBarco getEstado() {
        return estadoActual;
    }

    public void setEstado(EstadoBarco nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void iniciarEjercicio() {
        estadoActual.realizarEjercicio(this);
    }

    public void volverBase(Flota flota) {
        estadoActual.volverBase(flota, this);
    }

    public void danar() {
        estadoActual.danar(this);
    }

    public void solicitarReparacion(Flota flota) {
        estadoActual.reparar(flota, this);
    }

    public void desmantelar() {
        estadoActual.desmantelar(this);
    }

    public void hundir() {
        estadoActual.hundir(this);
    }

    public void incrementarMisiones(){
        this.Nmisiones++;
    }

    public int sumaDinero(){
        return 0;
    }

    public int costeReparacion(){
        return 0;
    }

    public int costeExpress(Flota flota){
        return 0;
    }


}
