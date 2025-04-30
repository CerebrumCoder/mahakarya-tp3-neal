package Models;
import java.util.Date;
import java.util.Random;

public class Voucher {
    private String id;
    private int sisaPemakaian;
    private Date berlakuHingga;

    /**
     * Catatan, jika transaksi dilakukan pada tanggal yang sama dengan
     * batas tanggal berlaku voucher, maka voucher masih bisa digunakan*/
    public Voucher(String id, Date berlakuHingga) {
        this.id = id;
        this.sisaPemakaian = randomSisaPemakaian();
        this.berlakuHingga = berlakuHingga;
    }

    public String getId() {
        return this.id;
    }

    public int getSisaPemakaian() {
        return this.sisaPemakaian;
    }

    public int randomSisaPemakaian() {
        // Generate nilai acak antara 0 sampai 9
        Random random = new Random();
        int randomIndex = random.nextInt(10);

        // Ambil karakter pada indeks randomIndex dari ID voucher
        char characterAtIndex = this.id.charAt(randomIndex);

        // Konversi karakter menjadi nilai numerik
        int sisaPemakaian = Character.getNumericValue(characterAtIndex);

        // Jika nilai numerik adalah 0, maka sisa pemakaian diatur menjadi 1
        if (sisaPemakaian == 0) {
            sisaPemakaian = 1;
        }

        return this.sisaPemakaian = sisaPemakaian;
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

