package projekt.carlease.springcarlease.Repozytoria;

import org.springframework.data.jpa.repository.JpaRepository;

import projekt.carlease.springcarlease.Klasy.CarImg;

public interface CarImgRepo extends JpaRepository<CarImg, Long> {
    
}
