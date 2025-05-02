package Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Models.Product;
import Models.Voucher;

public class ProductRepository {
    private String namaToko; // Nama toko penjual
    private List<Product> productList; // Daftar produk yang dimiliki penjual

    public ProductRepository(String namaToko) {
        this.namaToko = namaToko;
        this.productList = new ArrayList<>();
    }

    // Method untuk mendapatkan produk berdasarkan UUID
    public Product getProductById(UUID id) {
        for (Product product : productList) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null; // Return null jika produk tidak ditemukan
    }

    // Method untuk mendapatkan nama toko
    public String getNamaToko() {
        return this.namaToko;
    }

    // Method untuk mendapatkan daftar produk
    public List<Product> getProductList() {
        return new ArrayList<>(productList);
        // Awalnya ini:
        // productList.toArray(new Product[0]);
    }

    // Method untuk menambahkan produk ke daftar
    public void addProduct(Product product) {
        productList.add(product);
    }
}