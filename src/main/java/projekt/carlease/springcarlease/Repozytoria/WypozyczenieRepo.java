package projekt.carlease.springcarlease.Repozytoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.Wypozyczenie;

public interface WypozyczenieRepo extends JpaRepository<Wypozyczenie, Long> {
    Wypozyczenie findByIdIs(Long id);
    List<Wypozyczenie> findAllByUzytkownikEmail(String email);
    List<Wypozyczenie> findAllByUzytkownikEmailIsAndDataKoniecIsNull(String email);
    List<Wypozyczenie> findAllByUzytkownikEmailIsAndDataKoniecIsNotNull(String email);
}
