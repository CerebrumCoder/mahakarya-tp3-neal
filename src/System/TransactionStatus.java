package System;
import java.util.Date;

public class TransactionStatus {
    private Date timestamp; // Tanggal status transaksi
    private String status; // Status transaksi

    public static final String SEDANG_DIKEMAS = "Sedang Dikemas";
    public static final String MENUNGGU_PENGIRIM = "Menunggu Pengirim";
    public static final String SEDANG_DIKIRIM = "Dikirim";
    public static final String PESANAN_SELESAI = "Selesai";
    public static final String DIKEMBALIKAN = "Dikembalikan";

    public TransactionStatus(String status) {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Status tidak valid.");
        }
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
        if (isValidStatus(status)) {
            this.status = status;
            this.timestamp = new Date(); // Update timestamp saat status berubah
        } else {
            throw new IllegalArgumentException("Status tidak valid.");
        }
    }

    public boolean isValidStatus(String status) {
        return status.equals(SEDANG_DIKEMAS) || status.equals(MENUNGGU_PENGIRIM)
                || status.equals(SEDANG_DIKIRIM) || status.equals(PESANAN_SELESAI)
                || status.equals(DIKEMBALIKAN);
    }
}