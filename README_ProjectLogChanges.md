Disini dikasih tahu mana method yang diganti tipe datanya

1. getBalance() pada User (Abstract Class). Ini diubah jadi long bukan String. Karena dari classDiagram masih dalam bentuk String
2. Bagian cek promo dan voucher perlu ditambah Exception supaya enak ngerjainnya.
3. Ada perubahan di dalam ProductRepository.java yang awalnya getProductList() tipe datanya Product[], diubah menjadi List[Product]. Tujuannya
supaya lebih gampang.
4. Ada perubahan di dalam UserRepository.java yang awalnya getAll() tipe datanya User[], diubah menjadi List[User]. Tujuannya supaya lebih gampang.
5. handleKirimBarang() nanti keknya diintegrasi sama SystemPengirim
6. Property "keranjang" diubah namanya menjadi "keranjangList" dan tipenya diubah menjadi List[CartProduct]
  
86HGCWI2MTc0Mjgy
40MQ0TA5MTc0NjI1
FF5BUSJIMTc0NjI1
QXAJVS08MTc0NjMy
WBOJT2AGMTc0NjMy
G2JLTA82MTc0NjMz