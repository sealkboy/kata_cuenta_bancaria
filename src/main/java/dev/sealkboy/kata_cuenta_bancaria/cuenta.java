package dev.sealkboy.kata_cuenta_bancaria;

public class cuenta {

	public float saldo;
	protected int consignaciones = 0;
	protected int retiros = 0;
	public float tasaAnual;
	protected float comisionMensual = 0.0f;

	public cuenta(float saldo, float tasaAnual) {
		this.saldo = saldo;
		this.tasaAnual = tasaAnual;
	}

	public void consignarSaldo(float cantidad) {
		if (cantidad <= 0) {
			System.out.println("La cantidad a consignar debe ser mayor que cero.");
			return;
		}
		saldo += cantidad;
		consignaciones++; 
	}

	public void retirar(float cantidad) {
		if(cantidad <= 0) {
			System.out.println("La cantidad a retirar debe ser mayor que cero.");
			return;
		}
		if (cantidad > saldo) {
			System.out.println("No se puede retirar más de lo que hay en la cuenta.");
			return;
		}
		saldo -= cantidad; 
		retiros++;
	}

	protected void calcInteresMensual() {
		float interesMensual = saldo * (tasaAnual / 100) / 12;
		saldo += interesMensual; 
	}

	public void extractoMensual(){
		calcInteresMensual();
		saldo -= comisionMensual; 
	}

	public void imprimir() {
		System.out.println("Saldo: " + saldo);
		System.out.println("Consignaciones: " + consignaciones);
		System.out.println("Retiros: " + retiros);
	}
}
