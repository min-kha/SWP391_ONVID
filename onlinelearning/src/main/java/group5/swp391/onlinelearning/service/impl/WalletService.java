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
import group5.swp391.onlinelearning.service.IWalletService;


@Service
public class WalletService implements IWalletService  {
    @Autowired
    WalletRepository walletRepository;


    private HttpSession session;

    public WalletService(HttpSession session) {
        this.session = session;
    }
    

    @Override
    public BigDecimal getRevenue() {
        User teacher =(User) session.getAttribute("userSession");
        Wallet wallet = walletRepository.getWalletByTeacherId(teacher.getId());
        return wallet.getRevenue();
    }


    @Override
    public void changeRevenue(List<Course> listCourse) {
        for (Course course : listCourse) {
            Wallet wallet = walletRepository.getWalletByTeacherId(course.getTeacher().getId());
            BigDecimal percent = new BigDecimal("0.6");
            BigDecimal revenue = wallet.getRevenue().add(course.getPrice().multiply(percent));
            wallet.setRevenue(revenue);
            walletRepository.save(wallet);
        }
    }
    
}
