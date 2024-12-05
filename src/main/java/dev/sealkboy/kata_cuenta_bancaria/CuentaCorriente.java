package dev.sealkboy.kata_cuenta_bancaria;

public class CuentaCorriente extends Cuenta {
    private float sobregiro = 0;

    public CuentaCorriente(float saldoInicial, float tasaAnual) {
        super(saldoInicial, tasaAnual);
    }

    @Override
    public void retirar(float cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad a retirar debe ser mayor que cero.");
            return;
        }
        if (cantidad <= saldo) {
            saldo -= cantidad;
        } else {
            sobregiro += (cantidad - saldo);
            saldo = 0;
        }
        retiros++;
    }

    @Override
    public void consignarSaldo(float cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad a consignar debe ser mayor que cero.");
            return;
        }
        if (sobregiro > 0) {
            if (cantidad >= sobregiro) {
                cantidad -= sobregiro;
                sobregiro = 0;
            } else {
                sobregiro -= cantidad;
                cantidad = 0;
            }
        }
        saldo += cantidad;
        consignaciones++;
    }

    @Override
    public void imprimir() {
        System.out.println("Saldo: " + saldo);
        System.out.println("Consignaciones: " + consignaciones);
        System.out.println("Retiros: " + retiros);
        System.out.println("Sobregiro: " + sobregiro);
        System.out.println("Comisión mensual: " + comisionMensual);
    }

    public float getSobregiro() {
        return sobregiro;
    }
}
