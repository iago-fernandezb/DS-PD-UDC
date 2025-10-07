package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class e1_test {

    private Flota flota;
    private Barco ultraligero;
    private Barco ligero;
    private Barco pesado;
    private Barco ultrapesado;

    @BeforeEach
    void setUp() {
        // Inicializar flota y barcos
        flota = new Flota(50000); // Presupuesto inicial reducido
        ultraligero = new DE("USS Freedom");
        ligero = new CL("USS Cruiser");
        pesado = new CA("USS Heavy Cruiser");
        ultrapesado = new BB("USS Battleship");

        // Agregar barcos a la flota
        flota.agregarBarco(ultraligero);
        flota.agregarBarco(ligero);
        flota.agregarBarco(pesado);
        flota.agregarBarco(ultrapesado);
    }

    @Test
    void testRealizarEjercicio() {
        // Realizar ejercicios con diferentes barcos
        ultraligero.iniciarEjercicio();
        ligero.iniciarEjercicio();
        pesado.iniciarEjercicio();
        ultrapesado.iniciarEjercicio();

        // Validar transiciones de estado
        assertTrue(ultraligero.getEstado() instanceof EnEjercicio);
        assertTrue(ligero.getEstado() instanceof EnEjercicio);
        assertTrue(pesado.getEstado() instanceof EnEjercicio);
        assertTrue(ultrapesado.getEstado() instanceof EnEjercicio);
    }

    @Test
    void testRepararBarco() {
        // Daño y reparación
        ultraligero.iniciarEjercicio();
        ultraligero.danar();
        assertTrue(ultraligero.getEstado() instanceof Danado);

        ultraligero.solicitarReparacion(flota);
        assertTrue(ultraligero.getEstado() instanceof Operativo);

        ligero.iniciarEjercicio();
        ligero.danar();
        assertTrue(ligero.getEstado() instanceof Danado);

        ligero.solicitarReparacion(flota);
        assertTrue(ligero.getEstado() instanceof EnReparacion);
    }

    @Test
    void testEstadoDanadoTransiciones() {
        // Validar transición Danado -> Reparación
        pesado.iniciarEjercicio();
        pesado.danar();
        assertTrue(pesado.getEstado() instanceof Danado);

        pesado.solicitarReparacion(flota);
        assertTrue(pesado.getEstado() instanceof EnReparacion);

        // Volver a operativo desde reparación
        pesado.volverBase(flota);
        assertTrue(pesado.getEstado() instanceof Operativo);
    }

    @Test
    void testFondosInsuficientesParaReparacion() {
        // Reducir fondos para probar insuficiencia
        flota.fondos.quitarPresupuesto(49700); // Quedan solo 1000
        assertEquals(300, flota.getFondos());

        pesado.iniciarEjercicio();
        pesado.danar();
        assertTrue(pesado.getEstado() instanceof Danado);

        pesado.solicitarReparacion(flota); // No debería poder reparar por fondos insuficientes
        assertTrue(pesado.getEstado() instanceof Danado); // Estado no cambia
        assertEquals(300, flota.getFondos()); // Fondos no cambian
    }


    @Test
    void testDesmantelarBarco() {
        // Desmantelar barcos en diferentes estados
        ultraligero.desmantelar();
        assertTrue(ultraligero.getEstado() instanceof Inactivo);
        assertEquals("Desmantelado", ((Inactivo) ultraligero.getEstado()).getRazon(ultraligero));

        pesado.desmantelar();
        assertTrue(pesado.getEstado() instanceof Inactivo);
        assertEquals("Desmantelado", ((Inactivo) pesado.getEstado()).getRazon(pesado));
    }

    @Test
    void testHundirBarco() {
        // Hundir barcos en diferentes estados
        ultrapesado.iniciarEjercicio();
        ultrapesado.hundir();
        assertTrue(ultrapesado.getEstado() instanceof Inactivo);
        assertEquals("Hundido", ((Inactivo) ultrapesado.getEstado()).getRazon(ultrapesado));
    }

    @Test
    void testReparacionExpressUltraligero() {
        // Reparación instantánea para ultraligero
        ultraligero.iniciarEjercicio();
        ultraligero.danar();
        ultraligero.danar();
        assertTrue(ultraligero.getEstado() instanceof Danado);

        ultraligero.solicitarReparacion(flota); // Reparación sin costo adicional
        assertTrue(ultraligero.getEstado() instanceof Operativo);
        assertEquals(50000, flota.getFondos()); // Fondos no cambian para ultraligeros
    }

    @Test
    void testPresupuestoFlota() {
        // Validar actualizaciones de presupuesto
        double presupuestoInicial = flota.getFondos();

        ultraligero.iniciarEjercicio();
        ultraligero.volverBase(flota);
        assertEquals(presupuestoInicial + ultraligero.sumaDinero(), flota.getFondos());

        ultraligero.solicitarReparacion(flota);
        assertEquals(flota.getFondos() - ultraligero.costeExpress(flota), flota.getFondos());

        presupuestoInicial = flota.getFondos();
        ultrapesado.solicitarReparacion(flota);
        assertEquals(presupuestoInicial - ultrapesado.costeReparacion(), flota.getFondos());
    }

    @Test
    void testListadosFlota() {
        // Modificar estados
        ultraligero.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);

        // Verificar listados de reparación e inactivos
        flota.listarEnReparacion();
        flota.listarInactivos();

        // Hundir y verificar recuentos
        ligero.iniciarEjercicio();
        ligero.hundir();
        int enReparacion = flota.listarEnReparacion();
        int inactivos = flota.listarInactivos();

        assertEquals(1, enReparacion); // Solo ultrapesado en reparación
        assertEquals(1, inactivos); // Solo ligero hundido
    }

    @Test
    void testMultipleDamageBeforeRepair() {
        // Probar múltiples daños antes de la reparación
        ultraligero.iniciarEjercicio();
        ultraligero.danar();
        ultraligero.danar();
        ultraligero.danar();
        assertTrue(ultraligero.getEstado() instanceof Danado);

        ultraligero.solicitarReparacion(flota);
        assertTrue(ultraligero.getEstado() instanceof Operativo);
    }

    @Test
    void testInvalidTransitions() {
        // Intentar realizar un ejercicio en estado Danado
        ligero.iniciarEjercicio();
        ligero.danar();
        assertTrue(ligero.getEstado() instanceof Danado);

        ligero.iniciarEjercicio(); // No debe cambiar el estado
        assertTrue(ligero.getEstado() instanceof Danado);

        ligero.desmantelar(); // Transición inválida desde Danado
        assertTrue(ligero.getEstado() instanceof Inactivo);
    }

    @Test
    void testCostCalculationForRepairs() {
        // Validar costos de reparación para diferentes tipos de barcos
        double initialFunds = flota.getFondos();

        pesado.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);
        assertEquals(initialFunds - pesado.costeReparacion(), flota.getFondos());

        ultrapesado.iniciarEjercicio();
        ultrapesado.danar();
        ultrapesado.solicitarReparacion(flota);
        assertEquals(initialFunds - pesado.costeReparacion() - ultrapesado.costeReparacion(), flota.getFondos());

        assertEquals(flota.getFondos(), initialFunds - pesado.costeReparacion() - ultrapesado.costeReparacion());
    }

    @Test
    void testActionsOnInactiveShips() {
        // Probar acciones en barcos desmantelados
        pesado.desmantelar();
        assertTrue(pesado.getEstado() instanceof Inactivo);

        pesado.iniciarEjercicio(); // No debería tener efecto
        assertTrue(pesado.getEstado() instanceof Inactivo);

        pesado.danar(); // Probar daño en barco desmantelado
        assertTrue(pesado.getEstado() instanceof Inactivo);
    }

    @Test
    void testRepairWithoutSufficientFunds() {
        // Reducir fondos para probar reparación fallida
        flota.fondos.quitarPresupuesto(49500); // Quedan solo 500

        ultrapesado.iniciarEjercicio();
        ultrapesado.danar();
        ultrapesado.solicitarReparacion(flota);

        assertTrue(ultrapesado.getEstado() instanceof Danado); // No se repara
        assertEquals(500, flota.getFondos());

        // Intentar reparar otro barco
        pesado.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);
        assertTrue(pesado.getEstado() instanceof Danado);
        assertEquals(500, flota.getFondos());
    }

    @Test
    void testAddAndListInactiveShips() {
        // Hundir barcos y listarlos
        ultraligero.iniciarEjercicio();
        pesado.iniciarEjercicio();
        ultraligero.hundir();
        pesado.hundir();
        ligero.desmantelar();

        int inactivos = flota.listarInactivos();
        assertEquals(3, inactivos);

        // Validar conteo después de reactivar un barco
        ultraligero.solicitarReparacion(flota);
        assertEquals(2, flota.listarInactivos());
    }

    @Test
    void testListRepairsAndInactiveShips() {
        // Modificar estados y verificar listados
        ultraligero.iniciarEjercicio();
        ultraligero.danar();
        pesado.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);

        int enReparacion = flota.listarEnReparacion();
        int inactivos = flota.listarInactivos();

        assertEquals(1, enReparacion); // ultraligero en reparación
        assertEquals(0, inactivos);

        pesado.desmantelar();
        assertEquals(1, flota.listarInactivos());
    }

    @Test
    void testListActiveShips() {
        // Listar barcos activos
        ultraligero.iniciarEjercicio();
        ligero.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);

        flota.listarActivos(); // Verificar impresión manual

        // No debería incluir barcos en estado Danado o Inactivo
        assertTrue(ultraligero.getEstado() instanceof EnEjercicio);
        assertTrue(ligero.getEstado() instanceof EnEjercicio);
        assertTrue(pesado.getEstado() instanceof EnReparacion);
        assertFalse(pesado.getEstado() instanceof Danado);
        assertFalse(ultrapesado.getEstado() instanceof Inactivo);
    }

    @Test
    void testListInactiveShips() {
        // Hundir y desmantelar barcos
        ultraligero.iniciarEjercicio();
        ligero.iniciarEjercicio();
        ultraligero.hundir();
        pesado.desmantelar();
        ligero.hundir();

        int countInactivos = flota.listarInactivos();

        // Validar conteo correcto de barcos inactivos
        assertEquals(3, countInactivos);

        // Reactivar un barco y verificar actualización
        ultraligero.solicitarReparacion(flota);
        assertEquals(2, flota.listarInactivos());
    }

    @Test
    void testListShipsUnderRepair() {
        // Colocar barcos en reparación
        ultraligero.iniciarEjercicio();
        ultraligero.danar();
        pesado.iniciarEjercicio();
        pesado.danar();
        ultraligero.solicitarReparacion(flota);
        pesado.solicitarReparacion(flota);

        int countReparacion = flota.listarEnReparacion();

        // Validar conteo correcto de barcos en reparación
        assertEquals(1, countReparacion);

        // Cambiar estado de un barco y verificar actualización
        ultraligero.volverBase(flota);
        assertEquals(1, flota.listarEnReparacion());
    }

    @Test
    void testCombinationOfStates() {
        // Modificar estados y listar
        pesado.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);
        ligero.iniciarEjercicio();
        ultrapesado.desmantelar();

        // Verificar combinaciones
        assertEquals(1, flota.listarEnReparacion());
        assertEquals(1, flota.listarInactivos());

        // Confirmar que los activos son correctos
        flota.listarActivos(); // Verificar impresión manual
        assertTrue(ligero.getEstado() instanceof EnEjercicio);
    }

    @Test
    void testInvalidListOperations() {
        // Intentar listar sin barcos
        Flota flotaVacia = new Flota(50000);

        assertEquals(0, flotaVacia.listarEnReparacion());
        assertEquals(0, flotaVacia.listarInactivos());

        flotaVacia.listarActivos(); // No debería imprimir nada
    }

    @Test
    void testEdgeCasesForRepairs() {
        // Caso límite: fondos justo para una reparación
        flota.fondos.quitarPresupuesto(49800); // Quedan 500
        ultraligero.iniciarEjercicio();
        ultraligero.danar();
        ultraligero.solicitarReparacion(flota);

        assertTrue(ultraligero.getEstado() instanceof Operativo);
        assertEquals(200, flota.getFondos()); // Suficientes para reparación exacta

        // Intentar otra reparación con fondos insuficientes
        pesado.iniciarEjercicio();
        pesado.danar();
        pesado.solicitarReparacion(flota);

        assertTrue(pesado.getEstado() instanceof Danado);
        assertEquals(200, flota.getFondos());
    }
}
