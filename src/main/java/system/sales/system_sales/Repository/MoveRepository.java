package system.sales.system_sales.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import system.sales.system_sales.Entity.Move;
import system.sales.system_sales.Entity.MoveType;

public interface MoveRepository extends JpaRepository<Move, Long>{
    List<Move> findByMoveType(MoveType moveType);

}
