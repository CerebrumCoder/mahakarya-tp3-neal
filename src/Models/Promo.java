package Models;
import java.util.Date;

public class Promo {
    private String id; // ID unik dari promo
    private int usageLimit;
    private Date berlakuHingga; // Tanggal expired dari promo

    // Jika transaksi dilakukan pada tanggal yang sama dengan
    // batas tanggal berlaku promo, maka promo masih bisa digunakan
    public Promo(String id, Date berlakuHingga) {
        this.id = id;
        this.berlakuHingga = berlakuHingga;
    }

    // Untuk promo tidak memiliki batas jumlah pemakaian
    public Promo(String id, int usageLimit, Date berlakuHingga) {
        this.id = id;
        this.usageLimit = usageLimit;
        this.berlakuHingga = berlakuHingga;
    }

    public String getId() {
        return this.id;
    }

    public Date getBerlakuHingga() {
        return this.berlakuHingga;
    }

    public void setBerlakuHingga(Date berlakuHingga) {
        this.berlakuHingga = berlakuHingga;
    }

    public boolean isValid(Date currentDate) {
        // Promo masih valid jika tanggal transaksi tidak melewati tanggal berlaku
        return !currentDate.after(berlakuHingga);
    }

    public double calculateDisc(String promoCode) {
        // Menghitung diskon berdasarkan kode promo
        int total = 0;
        for (char c : promoCode.toCharArray()) {
            if (Character.isDigit(c)) {
                total += Character.getNumericValue(c);
            }
        }
        double discount = total >= 100 ? 5 : total; // Maksimal diskon 5% jika total >= 100
        return discount;
    }
}