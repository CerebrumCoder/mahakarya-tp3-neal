package Repository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import Models.Promo;

public class PromoRepository implements DiskonRepository<Promo> {
    private List<Promo> promoList; // Menyimpan seluruh objek Promo

    public PromoRepository() {
        this.promoList = new ArrayList<>();
    }

    @Override
    public Promo getById(String id) {
        for (Promo promo : promoList) {
            if (promo.getId().equals(id)) {
                return promo; // Jika terdapat objek Promo yang sesuai dengan ID, maka akan mengembalikan objek Promo tersebut
            }
        }
        return null; // Jika tidak ditemukan, kembalikan null
    }

    @Override
    public List<Promo> getAll() {
        return new ArrayList<>(promoList); // Mengembalikan salinan daftar promo
    }

    @Override
    public void generate() {
        // Tidak digunakan karena ada parameter tambahan di metode generate(String)
        throw new UnsupportedOperationException("Gunakan generate(String) untuk membuat promo.");
    }

    public void generate(String usageLimit) {
        String id = generatePromoCode(); // Generate kode promo
        Promo newPromo = new Promo(id, Integer.parseInt(usageLimit), new Date());
        promoList.add(newPromo); // Tambahkan promo baru ke daftar
    }

    private String generatePromoCode() {
        // Langkah 1: Random string menggunakan SecureRandom
        SecureRandom random = new SecureRandom();
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(charset.length());
            randomString.append(charset.charAt(index));
        }

        // Langkah 2: Timestamp dalam format Unix
        long timestamp = System.currentTimeMillis() / 1000L;
        String base64Timestamp = Base64.getEncoder().encodeToString(String.valueOf(timestamp).getBytes());
        String shortenedTimestamp = base64Timestamp.substring(0, 8);

        // Langkah 3: Format kode promo
        return randomString + shortenedTimestamp;
    }
}