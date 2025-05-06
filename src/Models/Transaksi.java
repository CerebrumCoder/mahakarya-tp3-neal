package Models;
import java.util.ArrayList;
import java.util.List;

import System.TransactionProduct;
import System.TransactionStatus;

public class Transaksi {
    private String id; // ID unik dari transaksi
    private String namePembeli; // Nama pembeli dari objek transaksi
    private String namePenjual; // Nama penjual dari objek transaksi
    private String namePengirim; // Nama pengirim dari objek transaksi
    private String idDiskon; // ID diskon yang digunakan pada transaksi
    private String namaToko; // Tambah properti namaToko
    private List<TransactionProduct> produkDibeli; // List produk yang dibeli pada transaksi
    private String jenisTransaksi; // Jenis transaksi: "Instant", "Next Day", atau "Regular"
    private long biayaOngkir; // Biaya ongkir berdasarkan jenis transaksi
    private List<TransactionStatus> historyStatus; // History status pengiriman dari transaksi

    public Transaksi(String id, String namePembeli, String namePenjual, String namePengirim, String idDiskon,
                     List<TransactionProduct> produkDibeli, String jenisTransaksi, String namaToko) {
        this.id = id;
        this.namePembeli = namePembeli;
        this.namePenjual = namePenjual;
        this.namePengirim = namePengirim;
        this.idDiskon = idDiskon;
        this.produkDibeli = produkDibeli;
        this.jenisTransaksi = jenisTransaksi;
        this.namaToko = namaToko;
        this.historyStatus = new ArrayList<>();

        // Menentukan biaya ongkir berdasarkan jenis transaksi
        switch (jenisTransaksi) {
            case "Instant" -> this.biayaOngkir = 20000;
            case "Next Day" -> this.biayaOngkir = 15000;
            case "Regular" -> this.biayaOngkir = 10000;
            default -> throw new IllegalArgumentException("Jenis transaksi tidak valid.");
        }

        // Tambahkan status awal
        this.addStatus(new TransactionStatus(TransactionStatus.MENUNGGU_PENGIRIM));
    }

    public void setNamePengirim(String namePengirim) {
        this.namePengirim = namePengirim;
    }

    public String getId() {
        return id;
    }

    public String getNamePembeli() {
        return namePembeli;
    }

    public String getNamePenjual() {
        return namePenjual;
    }

    public String getNamePengirim() {
        return namePengirim;
    }

    public String getIdDiskon() {
        return idDiskon;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public List<TransactionProduct> getProdukDibeli() {
        return produkDibeli;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public long getBiayaOngkir() {
        return biayaOngkir;
    }

    public List<TransactionStatus> getHistoryStatus() {
        return historyStatus;
    }

    public void addStatus(TransactionStatus status) {
        historyStatus.add(status);
    }

    public String getCurrentStatus() {
        if (historyStatus.isEmpty()) {
            return "Belum ada status.";
        }
        return historyStatus.get(historyStatus.size() - 1).getStatus();
    }

    public double calculateTotal(double totalHargaProduk, double diskon) {
        // Menghitung total harga setelah pajak dan diskon
        double total = totalHargaProduk + biayaOngkir;
        total -= diskon; // Mengurangi diskon
        return Math.max(total, 0); // Total tidak boleh negatif
    }

    public void refund() {
        System.out.println("Refund diproses untuk transaksi ID: " + id);
        // Implementasi refund, misalnya mengembalikan saldo pembeli
    }
}
