package Pekan3_2511533002;

public class Transaksi {
	
	private String idTransaksi;
	private String jenis;
	private double nominal;
	
	//Constructor
	public Transaksi(String id, String jenis, Double nominal) {
		this.idTransaksi = id;
		this.jenis = jenis;
		this.nominal = nominal;
	}
	
	public String getIdTransaksi() {return idTransaksi; }
	public String getJenis() {return jenis; }
	public double getNominal() {return nominal; }
	
	public void cetakDetail() {
		System.out.println("ID : " + idTransaksi + " | Jenis : " + jenis + " | Nominal : Rp" + nominal);
	}
}
