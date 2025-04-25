package Components;
import java.util.Date;

public class Promo {
    private String id; // ID unik dari promo
    private int usageLimit;
    private Date berlakuHingga; // Tanggal expired dari promo

    public Promo(String id, Date berlakuHingga) {
        this.id = id;
        this.berlakuHingga = berlakuHingga;
    }

    public Promo(String id, int usageLimit, Date berlakuHingga) {
        this.id = id;
        this.usageLimit = usageLimit;
        this.berlakuHingga = berlakuHingga;
    }

    public String getId() {
        return id;
    }

    public Date getBerlakuHingga() {
        return berlakuHingga;
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
