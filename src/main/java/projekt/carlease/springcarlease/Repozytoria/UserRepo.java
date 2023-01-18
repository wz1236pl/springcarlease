package projekt.carlease.springcarlease.Repozytoria;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.User;

public interface UserRepo extends JpaRepository<User, Long>{
    User findByEmail(String email);                             //przeszukiwanie bazy za podnym mailem w celu wyciągnięcia usera
}
