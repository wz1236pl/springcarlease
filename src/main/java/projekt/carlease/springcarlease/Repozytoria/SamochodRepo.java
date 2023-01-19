package projekt.carlease.springcarlease.Repozytoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.Samochod;

public interface SamochodRepo extends JpaRepository<Samochod, Long> {
    Samochod findByIdIs(Long id);
    List<Samochod> findAllByMiasto(String miasto);
    List<Samochod> findAllByZarezerwowanyIs(int a);
    List<Samochod> findAllByZarezerwowanyIsAndMiastoIs(int a, String s);
}
