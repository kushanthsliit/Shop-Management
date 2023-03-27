package com.kushanthsliit.shopmanagement.repository;

import com.kushanthsliit.shopmanagement.model.BusinessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusinessRecordRepository extends JpaRepository<BusinessRecord, Long> {

    BusinessRecord findByDate(LocalDate date);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM public.business_record br " +
                    "WHERE br.date BETWEEN :startDate AND :endDate")
    List<BusinessRecord> getRecordsBetweeenDateRange(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

    @Query(nativeQuery = true,
            value = "SELECT SUM(br.oyster_sold) AS sumOfOysterSold, " +
                    "SUM(br.tobacco_sold) AS sumOfTobaccoSold, " +
                    "SUM(br.tobacco_sold_quantity) AS sumOfTobaccoSoldQty, " +
                    "SUM(br.phone_cards_sold) AS sumOfPhoneCardsSold, " +
                    "SUM(br.total) AS sumOfZtotal, " +
                    "SUM(br.shop_sales) AS sumOfShopSales, " +
                    "SUM(br.shop) AS sumOfShop, " +
                    "SUM(br.phone_cards_purchased) AS sumOfPhoneCardsPurchased, " +
                    "SUM(br.tobacco_purchased) AS sumOfTobaccoPurchased, " +
                    "SUM(br.tobacco_purchased_quantity) AS sumOfTobaccoPurchasedQty, " +
                    "SUM(br.wages) AS sumOfWages, " +
                    "SUM(br.expenses) AS sumOfExpenses, " +
                    "SUM(br.commision) AS sumOfCommision " +
                    "FROM public.business_record br " +
                    "WHERE br.date BETWEEN :startDate AND :endDate")
    getAllSums getAllSumBetweenDates(@Param("startDate") LocalDate startDate,
                       @Param("endDate") LocalDate endDate);
    public static interface getAllSums{
        double getSumOfOysterSold();
        double getSumOfTobaccoSold();
        double getSumOfTobaccoSoldQty();
        double getSumOfPhoneCardsSold();
        double getSumOfZtotal();
        double getSumOfShopSales();
        double getSumOfShop();
        double getSumOfPhoneCardsPurchased();
        double getSumOfTobaccoPurchased();
        double getSumOfTobaccoPurchasedQty();
        double getSumOfWages();
        double getSumOfExpenses();
        double getSumOfCommision();
    }

}
