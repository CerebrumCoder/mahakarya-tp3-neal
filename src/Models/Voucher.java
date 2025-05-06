package Models;
import java.util.Date;
import java.util.Random;

public class Voucher {
    private String id;
    private int sisaPemakaian;
    private Date berlakuHingga;

    /**
     * Catatan, jika transaksi dilakukan pada tanggal yang sama dengan
     * batas tanggal berlaku voucher, maka voucher masih bisa digunakan. Id maksudnya VoucherCode dari VoucherRepository.java */
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

    // setSisaPemakaian() kalo ada perubahan kurangnya pemakaian voucher
    public void setSisaPemakaian(int sisaPemakaian) {
        this.sisaPemakaian = sisaPemakaian;
    }

    public void setBerlakuHingga(Date berlakuHingga) {
        this.berlakuHingga = berlakuHingga;
    }

    public boolean isValid(Date currentDate) {
        // Cek apakah voucher masih berlaku berdasarkan tanggal
        return !currentDate.after(berlakuHingga) && sisaPemakaian > 0;
    }

    // Ini yang ada di class diagram
    public int calculateDisc() {
        // Implementasi perhitungan diskon berdasarkan kode voucher
        // Mekanisme perhitungan diskon mengikuti TP sebelumnya

        int finalDiscount = calculateDiscountRecursive(0, this.id.length() - 1);
        return normalizeDiscount(finalDiscount);
    }


    // ---------- Ini tambahan dan sifatnya private karena hanya diakses di Voucher.java ----------
    // Ini supaya antisipasi diskon yang lebih dari 100 berdasarkan perkalian dari id (kodeVoucher)
    // yang ada di VoucherRepository.java
    private int normalizeDiscount(int discount) {
        if (discount > 100) {
            // Ensure the discount ≤ 100%
            String discountString = "" + discount;
            return calculateDiscountMoreThan100(discountString, 0, discountString.length() - 1);
        }
        return discount;
    }

    // Ngitung diskon pake id dan nilai diskonnya kurang dari numericCodenya ganjil bukan genap
    private int calculateDiscountRecursive(int indexStart, int indexEnd) {
        if (indexStart > indexEnd) {
            return 0;
        }

        // If the length of numericCode is odd and only one number remains in the middle
        // (the numericCode length is odd not even)
        // Recursion to add the start and end, then middle
        if (indexStart == indexEnd) {
            return Character.getNumericValue(id.charAt(indexStart));
        }

        // Get the index 0 in a numericCode String
        int num1 = Character.getNumericValue(id.charAt(indexStart));

        // Get the last index in a numericCode String
        int num2 = Character.getNumericValue(id.charAt(indexEnd));

        // Multiplication the first and the last number
        int product = num1 * num2;

        // Recursion to the next index
        return product + calculateDiscountRecursive(indexStart + 1, indexEnd - 1);
    }

    // Ngitung diskon pake terbaru dan nilai diskonnya lebih dari 100%
    private int calculateDiscountMoreThan100(String numericCode, int indexStart, int indexEnd) {
        if (indexStart > indexEnd) {
            return 0;
        }

        if (indexStart == indexEnd) {
            return Character.getNumericValue(numericCode.charAt(indexStart));
        }

        int num1 = Character.getNumericValue(numericCode.charAt(indexStart));
        int num2 = Character.getNumericValue(numericCode.charAt(indexEnd));

        int product = num1 * num2;

        return product + calculateDiscountMoreThan100(numericCode, indexStart + 1, indexEnd - 1);
    }

}

