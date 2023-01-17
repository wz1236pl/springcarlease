package projekt.carlease.springcarlease.Repozytoria;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.Samochod;

public interface SamochodRepo extends JpaRepository<Samochod, Long> {
    
}
