package ac2.ac2lab.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ac2.ac2lab.entities.Place;

@Repository
public interface PlaceRepository extends JpaRepository <Place,Long>{
    
    @Query("SELECT p FROM Place p " +
           "WHERE " +
           " ( LOWER(p.name)            LIKE   LOWER(CONCAT('%', :name,    '%'))) "
    )

    public Page <Place> find(Pageable pageRequest, String name);
}
