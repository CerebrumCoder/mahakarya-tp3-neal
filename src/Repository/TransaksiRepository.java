package Repository;
import java.util.ArrayList;
import java.util.List;

import Models.Transaksi;

public class TransaksiRepository {
    private List<Transaksi> transaksiList; // Menyimpan seluruh objek Transaksi

    public TransaksiRepository() {
        this.transaksiList = new ArrayList<>();
    }

    // Method untuk menambahkan transaksi baru
    public void addTransaksi(Transaksi transaksi) {
        transaksiList.add(transaksi);
    }

    // Method untuk mendapatkan seluruh transaksi
    public List<Transaksi> getList() {
        return new ArrayList<>(transaksiList); // Mengembalikan salinan daftar transaksi
    }

    // Method untuk memproses transaksi berdasarkan ID
    public void prosesTransaksi(String id) {
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getId().equals(id)) {
                //transaksi.updateStatus(); // Memperbarui status transaksi sesuai alur
                return;
            }
        }
        System.out.println("Transaksi dengan ID " + id + " tidak ditemukan.");
    }
}
