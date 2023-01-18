package projekt.carlease.springcarlease.Repozytoria;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.Wypozyczenie;

public interface WypozyczenieRepo extends JpaRepository<Wypozyczenie, Long> {
    
}
