package dev.sealkboy.kata_cuenta_bancaria;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CuentaAhorrosTest {

    private CuentaAhorros cuentaActiva;
    private CuentaAhorros cuentaInactiva;
    private static final float DELTA = 0.001f;

    @BeforeEach
    void setUp() {
        // Cuenta activa con saldo suficiente
        cuentaActiva = new CuentaAhorros(15000.0f, 12.0f);
        
        // Cuenta inactiva con saldo insuficiente
        cuentaInactiva = new CuentaAhorros(5000.0f, 12.0f);
    }

    @Test
    void testConstructor() {
        // Verificar inicialización de cuenta activa
        assertTrue(cuentaActiva.activa);
        assertEquals(15000.0f, cuentaActiva.saldo, DELTA);
        assertEquals(12.0f, cuentaActiva.tasaAnual, DELTA);

        // Verificar inicialización de cuenta inactiva
        assertFalse(cuentaInactiva.activa);
        assertEquals(5000.0f, cuentaInactiva.saldo, DELTA);
    }

    @Test
    void testConsignarSaldoCuentaActiva() {
        cuentaActiva.consignarSaldo(5000.0f);
        assertEquals(20000.0f, cuentaActiva.saldo, DELTA);
        assertEquals(1, cuentaActiva.consignaciones);
    }

    @Test
    void testConsignarSaldoCuentaInactiva() {
        float saldoInicial = cuentaInactiva.saldo;
        cuentaInactiva.consignarSaldo(1000.0f);
        assertEquals(saldoInicial, cuentaInactiva.saldo, DELTA);
        assertEquals(0, cuentaInactiva.consignaciones);
    }

    @Test
    void testRetirarCuentaActiva() {
        cuentaActiva.retirar(5000.0f);
        assertEquals(10000.0f, cuentaActiva.saldo, DELTA);
        assertEquals(1, cuentaActiva.retiros);
    }

    @Test
    void testRetirarCuentaInactiva() {
        float saldoInicial = cuentaInactiva.saldo;
        cuentaInactiva.retirar(1000.0f);
        assertEquals(saldoInicial, cuentaInactiva.saldo, DELTA);
        assertEquals(0, cuentaInactiva.retiros);
    }

    @Test
    void testExtractoMensualSinComisiones() {
        CuentaAhorros cuenta = new CuentaAhorros(15000.0f, 12.0f);
        
        // Realizar 4 retiros sin comisión
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        
        cuenta.extractoMensual();
        
        // Verificar que no hay comisión adicional
        assertEquals(0.0f, cuenta.comisionMensual, DELTA);
        assertTrue(cuenta.activa);
    }

    @Test
    void testExtractoMensualConComisiones() {
        CuentaAhorros cuenta = new CuentaAhorros(15000.0f, 12.0f);
        
        // Realizar 6 retiros (2 con comisión)
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        cuenta.retirar(1000.0f);
        
        cuenta.extractoMensual();
        
        // Verificar comisión por retiros adicionales
        // 2 retiros adicionales * 1000 = 2000
        assertEquals(2000.0f, cuenta.comisionMensual, DELTA);
    }

    @Test
    void testCambioEstadoCuenta() {
        CuentaAhorros cuenta = new CuentaAhorros(15000.0f, 12.0f);
        
        // Retirar hasta que el saldo sea menor a 10000
        cuenta.retirar(6000.0f);
        cuenta.extractoMensual();
        
        // Verificar que la cuenta se vuelve inactiva
        assertFalse(cuenta.activa);
    }
}