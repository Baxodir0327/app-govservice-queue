package uz.pdp.govqueue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.govqueue.model.GovService;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface GovServiceRepository extends ListPagingAndSortingRepository<GovService, Integer>, CrudRepository<GovService, Integer> {

    boolean existsByName(String name);

    List<GovService> findAllByStatusTrue();


    @Query(value = """
            SELECT gs.*
            FROM gov_service gs
            WHERE gs.id IN (SELECT gov_service_id
                            FROM operator_service
                            WHERE operator_id = :operatorId)
               OR gs.id IN (SELECT gov_service_id
                            FROM queue
                            WHERE operator_id = :operatorId
                              AND status IN ('COMPLETED', 'CANCELLED')
                              AND created_at >= :startDate
                              AND created_at < :endDate)
            """)
    List<GovService> getGovServicesByOperatorId(LocalDate startDate, LocalDate endDate, Integer operatorId);


    Page<GovService> findAllByNameContainingIgnoreCaseOrFirstLetter(String name, Character firstLetter, Pageable pageable);
}
