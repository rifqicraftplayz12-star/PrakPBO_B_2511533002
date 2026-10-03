package Pekan4_2511533002;

public class RekeningTabungan extends Rekening{
	
	//Atribut spesifik yang hanya dimiliki oleh tabungan
	private double sukuBunga;
	
	//Constructor subclass
	public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		//Super( ) memanggil constructor kelas induk (Rekening), WAJIB berada di baris pertama
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
	}
	
	public void tambahBungaAkhirBulan() {
		//Menghitung bunga
		//mengapa bisa mengakses saldo secara langsung dari class rekening?
		double nominalBunga = saldo * (sukuBunga / 100);
		saldo += nominalBunga;
		
		//Mencatat riwayat transaksi
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));
		
		System.out.println("Bunga " + sukuBunga + "% berhasil di tambahkan: Rp" + nominalBunga);
	}
}
