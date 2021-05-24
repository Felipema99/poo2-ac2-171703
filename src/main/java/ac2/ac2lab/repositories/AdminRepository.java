package ac2.ac2lab.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ac2.ac2lab.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin,Long> {
    
    @Query("SELECT a FROM Admin a " +
           "WHERE " +
           " ( LOWER(a.name)            LIKE   LOWER(CONCAT('%', :name,    '%'))) "
    )

    public Page <Admin> find(Pageable pageRequest, String name);
}
