package Models;
import java.util.Date;

public class Voucher {
    private String id;
    private int sisaPemakaian;
    private Date berlakuHingga;

    /**
     * Catatan, jika transaksi dilakukan pada tanggal yang sama dengan
     * batas tanggal berlaku voucher, maka voucher masih bisa digunakan*/
    public Voucher(String id, int sisaPemakaian, Date berlakuHingga) {
        this.id = id;
        this.sisaPemakaian = sisaPemakaian;
        this.berlakuHingga = berlakuHingga;
    }

    public String getId() {
        return this.id;
    }

    public int getSisaPemakaian() {
        return this.sisaPemakaian;
    }

    public void setSisaPemakaian(int sisaPemakaian) {
        this.sisaPemakaian = sisaPemakaian;
    }

    public Date getBerlakuHingga() {
        return this.berlakuHingga;
    }

    public void setBerlakuHingga(Date berlakuHingga) {
        this.berlakuHingga = berlakuHingga;
    }

    public boolean isValid(Date currentDate) {
        // Cek apakah voucher masih berlaku berdasarkan tanggal
        return !currentDate.after(berlakuHingga) && sisaPemakaian > 0;
    }

    public double calculateDisc(double totalHarga) {
        // Implementasi perhitungan diskon berdasarkan kode voucher
        // Mekanisme perhitungan diskon mengikuti TP sebelumnya
        return totalHarga * 0.1; // Contoh: diskon 10% dari total harga
    }

}

