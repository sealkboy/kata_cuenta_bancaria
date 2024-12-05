package dev.sealkboy.kata_cuenta_bancaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CuentaTests {
    private Cuenta cuenta;

    @BeforeEach
    public void init() {
        // Inicializamos la cuenta con un saldo de 10,000 y tasa anual de 3%
        cuenta = new Cuenta(10000.0f, 3.0f);
    }

    @Test
    void testConsignarSaldoCantidadMayorQueCero() {
        float cantidad = 500f;

        // Consignamos dinero
        cuenta.consignarSaldo(cantidad);

        assertEquals(10500f, cuenta.saldo);
        assertEquals(1, cuenta.consignaciones); // Verificamos que el número de consignaciones se ha incrementado
    }

    @Test
    void testConsignarSaldoCantidadMenorQueCero() {
        float cantidad = -200f;

        // Intentamos consignar una cantidad negativa
        cuenta.consignarSaldo(cantidad);

        assertEquals(10000f, cuenta.saldo); // El saldo no cambia
        assertEquals(0, cuenta.consignaciones); // El número de consignaciones no cambia
    }

    @Test
    void testRetirarSaldoCantidadMenorQueSaldo() {
        float cantidad = 2000f;

        // Retiramos una cantidad menor que el saldo disponible
        cuenta.retirar(cantidad);

        assertEquals(8000f, cuenta.saldo); // El saldo debe reducirse
        assertEquals(1, cuenta.retiros); // El número de retiros se ha incrementado
    }

    @Test
    void testRetirarSaldoCantidadMayorQueSaldo() {
        float cantidad = 12000f;

        // Intentamos retirar más de lo que hay en la cuenta
        cuenta.retirar(cantidad);

        assertEquals(10000f, cuenta.saldo); // El saldo sigue siendo el mismo
        assertEquals(0, cuenta.retiros); // El número de retiros no cambia
    }

    @Test
    void testRetirarSaldoCantidadNegativa() {
        float cantidad = -500f;

        // Intentamos retirar una cantidad negativa
        cuenta.retirar(cantidad);

        assertEquals(10000f, cuenta.saldo); // El saldo no cambia
        assertEquals(0, cuenta.retiros); // El número de retiros no cambia
    }

    @Test
    void testConsignarInteresMensual() {
        // Calculamos y consignamos el interés mensual
        cuenta.calcInteresMensual();

        // Verificamos que el saldo se haya incrementado por el interés mensual
        assertEquals(10025f, cuenta.saldo); // El interés es 10000 * 3% / 12 = 25
    }

    @Test
    void testExtractoMensual() {
        // Realizamos un extracto mensual que incluye los intereses y comisiones
        cuenta.extractoMensual();

        // Verificamos el saldo después del extracto mensual (con intereses y comisiones)
        assertEquals(10025f, cuenta.saldo); // Aseguramos que el saldo es correcto después del extracto
    }

}
