package Repository;
import java.util.List;

// <T> bisa diganti apapun. Tapi tipenya bisa berubah2
public interface DiskonRepository<T> {
    T getById(String id); // Method untuk mendapatkan diskon berdasarkan ID

    // Method untuk mendapatkan semua diskon
    List<T> getAll();

    // Method untuk membuat dan menambahkan diskon baru
    void generate();
}
