package Pekan4_2511533002;
public class RekeningGiro extends Rekening{
	private double batasOverdraft;
	
	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		
		//Memanggil inisialisasi dasar dari superclass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	//Getter khusus giro
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	
	//(Catatan : Penarikan hingga limir overdraft akan kita selesaikan di modul 5)
}
