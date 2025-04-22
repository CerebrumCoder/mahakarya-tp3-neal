import java.util.Date;

public class TransactionStatus {
    private Date timestamp; // Tanggal status transaksi
    private String status; // Status transaksi

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
}
