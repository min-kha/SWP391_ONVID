package group5.swp391.onlinelearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import group5.swp391.onlinelearning.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    @Query(value = "SELECT * FROM swp391_onvid.wallet where teacher_id = ?1", nativeQuery = true)
    public Wallet getWalletByTeacherId(int studentId);
}