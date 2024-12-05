package dev.sealkboy.kata_cuenta_bancaria;

public class cuentaAhorros extends cuenta {
	
	protected boolean activa; 

	public cuentaAhorros(float saldo, float tasaAnual) {
		super(saldo, tasaAnual); 
		this.activa = saldo >= 10000;
	}

	@Override
    public void consignarSaldo(float cantidad) {
        if (!activa) {
            System.out.println("No se puede consignar en una cuenta inactiva.");
            return;
        }
        super.consignarSaldo(cantidad);
    }

	@Override
    public void retirar(float cantidad) {
        if (!activa) {
            System.out.println("No se puede retirar de una cuenta inactiva.");
            return;
        }
        super.retirar(cantidad);
    }

	@Override
    public void extractoMensual() {
        super.extractoMensual();
        if (retiros > 4) {
            comisionMensual += (retiros - 4) * 1000;
        }
        activa = saldo >= 10000;
    }

	@Override
    public void imprimir() {
        super.imprimir();
        System.out.println("Comisión mensual: " + comisionMensual);
        System.out.println("Cuenta activa: " + activa);
    }	
}









}
