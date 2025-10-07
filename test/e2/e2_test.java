package e2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class e2_test {

    private Accion accion;
    private Mercado mercado;
    private ClienteSencillo clienteSencillo;
    private ClienteDetallado clienteDetallado;

    @BeforeEach
    void setUp() {
        // Inicializar mercado y observadores
        mercado = Mercado.getInstancia();
        clienteSencillo = new ClienteSencillo();
        clienteDetallado = new ClienteDetallado();

        // Registrar observadores en el mercado
        mercado.agregarObservador(clienteSencillo);
        mercado.agregarObservador(clienteDetallado);

        // Crear acción
        accion = new Accion("AAPL");
    }

    @Test
    void testNotificarObservadores() {
        // Actualizar datos de la acción
        accion.actualizarDatos(150.0, 155.0, 145.0, 1000000);

        // Verificar que no ocurre ninguna excepción
        assertDoesNotThrow(() -> mercado.notificarObservadores(accion));
    }

    @Test
    void testAgregarYEliminarObservadores() {
        // Crear otro observador y agregarlo
        Observador otroCliente = new ClienteSencillo();
        mercado.agregarObservador(otroCliente);

        // Verificar que no hay excepciones al notificar
        accion.actualizarDatos(152.0, 158.0, 148.0, 1200000);
        assertDoesNotThrow(() -> mercado.notificarObservadores(accion));

        // Eliminar un observador
        mercado.eliminarObservador(clienteSencillo);

        // Verificar que aún funciona la notificación
        accion.actualizarDatos(155.0, 160.0, 150.0, 1500000);
        assertDoesNotThrow(() -> mercado.notificarObservadores(accion));
    }

    @Test
    void testActualizarDatosValidos() {
        // Actualizar datos de la acción con valores válidos
        accion.actualizarDatos(140.0, 145.0, 135.0, 800000);
        assertEquals(140.0, accion.getDatos().getCierre());
        assertEquals(145.0, accion.getDatos().getMaximo());
        assertEquals(135.0, accion.getDatos().getMinimo());
        assertEquals(800000, accion.getDatos().getVolumen());

        accion.actualizarDatos(160.0, 165.0, 155.0, 1500000);
        assertEquals(160.0, accion.getDatos().getCierre());
        assertEquals(165.0, accion.getDatos().getMaximo());
        assertEquals(155.0, accion.getDatos().getMinimo());
        assertEquals(1500000, accion.getDatos().getVolumen());
    }

    @Test
    void testSinObservadores() {
        // Eliminar todos los observadores
        mercado.eliminarObservador(clienteSencillo);
        mercado.eliminarObservador(clienteDetallado);

        // Actualizar datos sin observadores
        accion.actualizarDatos(700.0, 720.0, 680.0, 500000);

        // Verificar que no ocurre ninguna excepción
        assertDoesNotThrow(() -> mercado.notificarObservadores(accion));
    }

    @Test
    void testDatosNegativosNoPermitidos() {
        // Intentar actualizar datos con valores negativos
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accion.actualizarDatos(-10.0, -20.0, -30.0, -100);
        });

        // Verificar el mensaje de error
        String expectedMessage = "Datos no válidos";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testCalcularMaximosMinimos() {
        // Probar cálculo de máximos y mínimos con una lista de valores
        List<Double> valores = List.of(150.0, 155.0, 145.0, 160.0);
        DatosAccion datosCalculados = Accion.calcularMaximosMinimos(valores);

        // Verificar los resultados
        assertEquals(160.0, datosCalculados.getMaximo());
        assertEquals(145.0, datosCalculados.getMinimo());
        assertEquals(0, datosCalculados.getCierre()); // Por defecto
    }

    @Test
    void testNotificarConMultiplesAcciones() {
        // Crear y actualizar varias acciones
        Accion accionMicrosoft = new Accion("MSFT");
        accion.actualizarDatos(150.0, 155.0, 145.0, 1000000);
        accionMicrosoft.actualizarDatos(300.0, 310.0, 290.0, 2000000);

        // Verificar que ambas notificaciones funcionan sin errores
        assertDoesNotThrow(() -> mercado.notificarObservadores(accion));
        assertDoesNotThrow(() -> mercado.notificarObservadores(accionMicrosoft));
    }

    @Test
    void testNotificacionSinDatos() {
        // Crear una acción sin actualizar sus datos
        Accion nuevaAccion = new Accion("TSLA");

        // Intentar notificar sin datos
        Exception exception = assertThrows(NullPointerException.class, () -> {
            mercado.notificarObservadores(nuevaAccion);
        });

        String expectedMessage = "Cannot invoke";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}
