package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.dto.PaymentData;
import com.gospeltube.gospeltubebackend.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    Optional<Transactions> findByRef(String ref);

    @Query(value = """
     select date_trunc('month', time) as month,
    to_char(date_trunc('month', time), 'Month') as name,
    sum(transactions.gtube_fees) as gtubeAmount,
    sum(transactions.amount_after_fees) as churchAmount
    from transactions where receiver = :userId
    and extract(year from time) = :year
    group by month
    order by month;
""", nativeQuery = true)
    Optional<Set<PaymentData>> getTransactionsByMonth(@Param("userId") Long userId, @Param("year") int year);

    @Query(value = """
     select
         date_trunc('week', time) as month,
         to_char(date_trunc('week', time), 'Week') as name,
         sum(transactions.gtube_fees) as gtubeAmount,
         sum(transactions.amount_after_fees) as churchAmount
     from
         transactions
     where
     receiver = :userId
       and extract(year from time) = :year
       and extract(month from time) = :month
     group by
         month
     order by
         month;
""", nativeQuery = true)
    Optional<Set<PaymentData>> getTransactionByWeeks(@Param("userId") Long userId,
                                                     @Param("year") int year,
                                                     @Param("month") int month);


    @Query(value = """
     SELECT
         date_trunc('day', time) AS day,
         to_char(date_trunc('day', time), 'Day') AS name,
         count(*) AS count,
         sum(transactions.gtube_fees) AS gtubeAmount,
         sum(transactions.amount_after_fees) AS churchAmount
     FROM
         transactions
     WHERE
         receiver = :userId
         AND time >= current_date - interval '7 days'
     GROUP BY
         day
     ORDER BY
         day;
""", nativeQuery = true)
    Optional<Set<PaymentData>> getTransactionsPerDay(@Param("userId") Long userId);
}
