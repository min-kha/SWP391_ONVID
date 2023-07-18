package group5.swp391.onlinelearning.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.entity.Wallet;
import group5.swp391.onlinelearning.repository.WalletRepository;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.IWalletService;

@Service
public class WalletService implements IWalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    IUserService userService;

    private HttpSession session;

    public WalletService(HttpSession session) {
        this.session = session;
    }

    @Override
    // lấy số dư hiện tại của người dùng
    public BigDecimal getRevenue() {
        User teacher = (User) session.getAttribute("userSession");
        Wallet wallet = walletRepository.getWalletByTeacherId(teacher.getId());
        return wallet.getRevenue();
    }

    @Override
    // Thay doi so du khi co hoc sinh dang ky khoa hoc
    public void changeRevenue(List<Course> listCourse) {
        for (Course course : listCourse) {
            Wallet wallet = walletRepository.getWalletByTeacherId(course.getTeacher().getId());
            BigDecimal percent = new BigDecimal("0.6");
            BigDecimal revenue = wallet.getRevenue().add(course.getPrice().multiply(percent));
            wallet.setRevenue(revenue);
            walletRepository.save(wallet);
        }
    }

    @Override
    // Thay đổi số dư khi rút tiền
    public void subRevenue(BigDecimal money) {
        User teacher = (User) session.getAttribute("userSession");
        Wallet wallet = walletRepository.getWalletByTeacherId(teacher.getId());
        BigDecimal revenue = wallet.getRevenue().subtract(money);
        wallet.setRevenue(revenue);
        walletRepository.save(wallet);
    }

    @Override
    public void createWallet(int teacherId) {
        // get teacher according to id
        User teacher = userService.getUserById(teacherId);

        // set field for teacher default
        Wallet wallet = new Wallet();
        wallet.setRevenue(BigDecimal.ZERO);
        wallet.setTeacher(teacher);

        // save wallet
        walletRepository.save(wallet);
    }

}
