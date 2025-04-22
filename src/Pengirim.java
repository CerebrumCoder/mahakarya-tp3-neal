public class Pengirim extends User {

    public Pengirim(String username, String password) {
        super(username, password, "Pengirim");
    }

    @Override
    public void getRiwayatTransaksi(Transaksi[] transaksi) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRiwayatTransaksi'");
    }

    // Implementasi tambahan untuk fungsi-fungsi terkait pengiriman dapat ditambahkan di sini
}
