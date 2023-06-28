package group5.swp391.onlinelearning.repository2;

onlinelearning/src/main/java/group5/swp391/onlinelearning/repository2/UserRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import group5.swp391.onlinelearning.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
