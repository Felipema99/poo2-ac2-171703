package ac2.ac2lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ac2.ac2lab.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository <Ticket,Long>{

}
