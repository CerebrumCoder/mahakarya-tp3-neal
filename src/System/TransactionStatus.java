package System;
import java.util.Date;

public class TransactionStatus {
    private Date timestamp; // Tanggal status transaksi
    private String status; // Status transaksi
    private int amount;

    public static final String SEDANG_DIKEMAS = "Sedang Dikemas";
    public static final String MENUNGGU_PENGIRIM = "Menunggu Pengirim";
    public static final String SEDANG_DIKIRIM = "Dikirim";
    public static final String PESANAN_SELESAI = "Pesanan Selesai";
    public static final String DIKEMBALIKAN = "Dikembalikan";

    public TransactionStatus(String status) {
        this.timestamp = new Date(); // Set timestamp ke waktu saat ini
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.timestamp = new Date(); // Update timestamp saat status berubah
    }

    public boolean isValidStatus(String status) {
        return status.equals(SEDANG_DIKEMAS) || status.equals(MENUNGGU_PENGIRIM)
                || status.equals(SEDANG_DIKIRIM) || status.equals(PESANAN_SELESAI)
                || status.equals(DIKEMBALIKAN);
    }
}