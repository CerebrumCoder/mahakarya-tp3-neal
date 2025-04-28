package Repository;

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
                return voucher;
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
        // Tidak digunakan karena ada parameter tambahan di metode generate(int, Date)
        throw new UnsupportedOperationException("Gunakan generate(int, Date) untuk membuat voucher.");
    }

    public void generate(int usageLimit, Date expiryDate) {
        String id = "VCR-" + (voucherList.size() + 1); // Generate ID unik untuk voucher
        Voucher newVoucher = new Voucher(id, usageLimit, expiryDate);
        voucherList.add(newVoucher); // Tambahkan voucher baru ke daftar
    }
}