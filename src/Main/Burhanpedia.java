package Main;
import Repository.AdminRepository;
import Repository.PromoRepository;
import Repository.UserRepository;
import Repository.VoucherRepository;

public class Burhanpedia {
    private UserRepository userRepo;
    private AdminRepository adminRepo;
    private VoucherRepository voucherRepo;
    private PromoRepository promoRepo;

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public AdminRepository getAdminRepo() {
        return adminRepo;
    }

    public VoucherRepository getVoucherRepo() {
        return voucherRepo;
    }

    public PromoRepository getPromoRepo() {
        return promoRepo;
    }

    public Burhanpedia() {
        this.userRepo = new UserRepository();
        this.adminRepo = new AdminRepository();
        this.voucherRepo = new VoucherRepository();
        this.promoRepo = new PromoRepository();
    }

}