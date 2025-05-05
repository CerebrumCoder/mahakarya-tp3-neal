package Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Models.Voucher;

public class VoucherRepository implements DiskonRepository<Voucher> {
    private List<Voucher> voucherList; // Menyimpan seluruh objek Voucher

    public VoucherRepository() {
        this.voucherList = new ArrayList<>();
    }

    @Override
    public Voucher getById(String id) {
        for (Voucher voucher : voucherList) {
            if (voucher.getId().equals(id)) {
                return voucher; // Jika ditemukan objek Voucher sesuai ID, maka akan mengembalikan objek Voucher tersebut
            }
        }
        return null; // Jika tidak ditemukan, kembalikan null
    }

    @Override
    public List<Voucher> getAll() {
        return new ArrayList<>(voucherList); // Mengembalikan salinan daftar voucher
    }

    @Override
    public void generate() {
        // Tidak digunakan karena ada parameter tambahan di metode generate(String)
        throw new UnsupportedOperationException("Gunakan generate(String) untuk membuat voucher.");
    }

    // Tanyain ini ke asdos
    public void generate(String voucherCode, Date expiryDate) {
        // Buat objek Voucher baru
        Voucher newVoucher = new Voucher(voucherCode, expiryDate);

        // Tambahkan voucher baru ke daftar
        voucherList.add(newVoucher);

        // Debugging
        // System.out.println("Voucher berhasil ditambahkan");
        // System.out.println("Voucher: " + newVoucher);
    }

    // Method generate dengan parameter String untuk batas tanggal waktu pemakaian. Ini kurang tahu dipakai ato engga
    // ini ada sesuai diagram class di file TP3
//    public void generate(String expiryDateInput) {
//        try {
//            // Parse input tanggal menjadi objek Date
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date expiryDate = dateFormat.parse(expiryDateInput);
//
//            // Generate ID unik untuk voucher
//            String id = "VCR-" + (voucherList.size() + 1);
//
//            // Buat voucher baru dengan batas waktu pemakaian
//            Voucher newVoucher = new Voucher(id, 1, expiryDate); // Default sisa pemakaian = 1
//            voucherList.add(newVoucher); // Tambahkan voucher baru ke daftar
//        } catch (ParseException e) {
//            System.out.println("Format tanggal tidak valid. Gunakan format yyyy-MM-dd.");
//        }
//    }
}