package dev.sealkboy.kata_cuenta_bancaria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CuentaCorrienteTest {

    private CuentaCorriente cuenta;
    private static final float DELTA = 0.001f;

    @BeforeEach
    void setUp() {
        // Configurar una cuenta corriente con saldo inicial
        cuenta = new CuentaCorriente(1000.0f, 12.0f);
    }

    @Test
    void testConstructor() {
        assertEquals(1000.0f, cuenta.saldo, DELTA);
        assertEquals(12.0f, cuenta.tasaAnual, DELTA);
        assertEquals(0, cuenta.getSobregiro(), DELTA);
        assertEquals(0, cuenta.consignaciones);
        assertEquals(0, cuenta.retiros);
    }

    @Test
    void testRetirarSaldoSuficiente() {
        cuenta.retirar(500.0f);
        assertEquals(500.0f, cuenta.saldo, DELTA);
        assertEquals(0, cuenta.getSobregiro(), DELTA);
        assertEquals(1, cuenta.retiros);
    }

    @Test
    void testRetirarSaldoInsuficiente() {
        cuenta.retirar(1500.0f);
        assertEquals(0, cuenta.saldo, DELTA);
        assertEquals(500.0f, cuenta.getSobregiro(), DELTA);
        assertEquals(1, cuenta.retiros);
    }

    @Test
    void testRetirarCantidadNegativa() {
        float saldoInicial = cuenta.saldo;
        cuenta.retirar(0.0f);
        assertEquals(saldoInicial, cuenta.saldo, DELTA);
        assertEquals(0, cuenta.retiros);
    }

    @Test
    void testConsignarSaldoSinSobregiro() {
        cuenta.consignarSaldo(500.0f);
        assertEquals(1500.0f, cuenta.saldo, DELTA);
        assertEquals(1, cuenta.consignaciones);
    }

    @Test
    void testConsignarSaldoConSobregiro() {
        // Primero crear un sobregiro
        cuenta.retirar(1500.0f);
        
        // Consignar monto para cubrir sobregiro parcialmente
        cuenta.consignarSaldo(800.0f);
        assertEquals(0, cuenta.saldo, DELTA);
        assertEquals(300.0f, cuenta.getSobregiro(), DELTA);
        assertEquals(1, cuenta.consignaciones);
    }

    @Test
    void testConsignarSaldoParaCubridSobregiroCompleto() {
        // Primero crear un sobregiro
        cuenta.retirar(1500.0f);
        
        // Consignar monto para cubrir sobregiro completamente
        cuenta.consignarSaldo(1500.0f);
        assertEquals(0, cuenta.saldo, DELTA);
        assertEquals(0, cuenta.getSobregiro(), DELTA);
        assertEquals(1, cuenta.consignaciones);
    }

    @Test
    void testConsignarSaldoExcedenteDespuesDeSobregiro() {
        // Primero crear un sobregiro
        cuenta.retirar(1500.0f);
        
        // Consignar monto mayor al sobregiro
        cuenta.consignarSaldo(2000.0f);
        assertEquals(500.0f, cuenta.saldo, DELTA);
        assertEquals(0, cuenta.getSobregiro(), DELTA);
        assertEquals(1, cuenta.consignaciones);
    }

    @Test
    void testConsignarSaldoCantidadNegativa() {
        float saldoInicial = cuenta.saldo;
        cuenta.consignarSaldo(0.0f);
        assertEquals(saldoInicial, cuenta.saldo, DELTA);
        assertEquals(0, cuenta.consignaciones);
    }
}