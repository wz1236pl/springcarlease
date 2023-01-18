package projekt.carlease.springcarlease.Repozytoria;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.Uzytkownik;

public interface UzytkownikRepo extends JpaRepository<Uzytkownik, Long>{
    Uzytkownik findByEmail(String email);                             //przeszukiwanie bazy za podnym mailem w celu wyciągnięcia usera
}
