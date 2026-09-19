package Pekan2_2511533002;

public class Transaksi {
	String idTransaksi;
	String jenis;
	double nominal;
	
	//Constructor
	public Transaksi(String id, String jenis, Double nominal) {
		this.idTransaksi = id;
		this.jenis = jenis;
		this.nominal = nominal;
	}
	
	public void cetakDetail() {
		System.out.println("ID : " + idTransaksi + " | Jenis : " + jenis + " | Nominal : Rp" + nominal);
	}
}
