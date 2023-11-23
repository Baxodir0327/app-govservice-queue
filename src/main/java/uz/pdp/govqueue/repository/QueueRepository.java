package uz.pdp.govqueue.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.pdp.govqueue.enums.QueueStatusEnum;
import uz.pdp.govqueue.model.Queue;
import uz.pdp.govqueue.payload.DashboardByStatusDTO;
import uz.pdp.govqueue.payload.DashboardTempDTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface QueueRepository extends CrudRepository<Queue, Integer> {


    int countByCreatedAtBetweenAndStatusInAndNumberStartingWith(Timestamp start, Timestamp end, Collection<QueueStatusEnum> status, String number);

    Optional<Queue> findFirstByCreatedAtBetweenAndNumberStartingWithOrderByNumberDesc(Timestamp createdAt, Timestamp createdAt2, String number);

    @Query(value = """
            SELECT COALESCE(AVG(extract(EPOCH FROM finished_at - started_at)) / 60, 10) AS avg
                                                                     FROM queue
                                                                     WHERE created_at BETWEEN :start AND :end
                                                                       AND number LIKE CONCAT(:firstLetter, '%')
            """)
    Long getAvgYesterdayByFirstLetter(@Param("start") Timestamp start, @Param("end") Timestamp end, @Param("firstLetter") String firstLetter);

    List<Queue> findAllByStatus(QueueStatusEnum queueStatusEnum);

    @Query(value = """
                SELECT *
                FROM queue q
                WHERE created_at >= current_date
                  AND (operator_id = :operatorId OR (
                            status = 'RUNNABLE' AND
                            q.gov_service_id IN
                            (SELECT gov_service_id
                             FROM operator_service
                             WHERE operator_id = :operatorId)))
            """)
    List<Queue> findAllByStatusAndOperatorId(@Param("operatorId") Integer operatorId);

    @Query(value = """
                            WITH temp AS (SELECT *
                                          FROM queue
                                          WHERE status = 'RUNNABLE'
                                            AND created_at >= CURRENT_DATE
                                            AND gov_service_id IN (SELECT gov_service_id
                                                                   FROM operator_service
                                                                   WHERE operator_id = :operatorId))
                            SELECT *
                            FROM temp
                            WHERE created_at = (SELECT MIN(created_at)
                                                FROM temp) FOR UPDATE LIMIT 1
            """)
    Optional<Queue> getTopRunnableByOperatorId(Integer operatorId);

    @Query(value = """
            WITH temp AS (SELECT *
                          FROM queue
                          WHERE status = 'CALLED'
                            AND created_at >= CURRENT_DATE
                            AND operator_id = :operatorId)
            SELECT *
            FROM temp
            WHERE created_at = (SELECT MIN(created_at)
                                FROM temp) FOR UPDATE LIMIT 1
            """)
    Optional<Queue> getTopCalledByOperatorId(Integer operatorId);

    @Query(value = """
            SELECT COUNT(CASE WHEN status = 'CANCELLED' THEN 1 END) AS cancelled_count,
                   COUNT(CASE WHEN status = 'COMPLETED' THEN 1 END) AS completed_count,
                   COUNT(id)                                        AS all_count
            FROM queue
            WHERE status IN ('CANCELLED', 'COMPLETED')
              AND operator_id = :operatorId
              AND created_at >= :startDate
              AND created_at < :endDate
            """)
    Optional<DashboardByStatusDTO> getMyQueueForDashboardByStatus(Integer operatorId, LocalDate startDate, LocalDate endDate);

    @Query(value = """
            WITH temp AS (SELECT CAST(DATE(created_at) AS VARCHAR) AS date,
                                          CAST(q.gov_service_id AS VARCHAR) AS service_id,
                                          COUNT(id)                         AS count
                                   FROM queue q
                                   WHERE q.operator_id = :operatorId
                                     AND status IN ('COMPLETED', 'CANCELLED')
                                     AND created_at >= :startDate
                                     AND created_at < :endDate
                                   GROUP BY date, service_id
                                   ORDER BY date)
                     SELECT 'ALL' AS date, 'ALL' AS service, SUM(temp.count) AS count, 0 AS idx
                     FROM temp
                     UNION ALL
                     SELECT 'ALL', service_id, SUM(count), 1
                     FROM temp
                     GROUP BY service_id
                     UNION ALL
                     SELECT 'ALL', date, SUM(count), 2
                     FROM temp
                     GROUP BY date
                     UNION ALL
                     SELECT *, 3
                     FROM temp
                     ORDER BY service
            """)
    List<DashboardTempDTO> getMyQueueForDashboardByService(Integer operatorId, LocalDate startDate, LocalDate endDate);
}
