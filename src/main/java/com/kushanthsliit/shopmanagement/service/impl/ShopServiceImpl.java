package com.kushanthsliit.shopmanagement.service.impl;

import com.kushanthsliit.shopmanagement.dto.ChartData;
import com.kushanthsliit.shopmanagement.dto.DatePair;
import com.kushanthsliit.shopmanagement.dto.GetAllSums;
import com.kushanthsliit.shopmanagement.dto.ProfitsDto;
import com.kushanthsliit.shopmanagement.model.BusinessRecord;
import com.kushanthsliit.shopmanagement.repository.BusinessRecordRepository;
import com.kushanthsliit.shopmanagement.response.*;
import com.kushanthsliit.shopmanagement.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private BusinessRecordRepository businessRecordRepository;

    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public BusinessRecord addRecord(BusinessRecord businessRecord) {
        BusinessRecord br = businessRecordRepository.findByDate(businessRecord.getDate());
        if(br == null){
            return businessRecordRepository.save(businessRecord);
        }
        else {
            return null;
        }
    }

    @Override
    public List<BusinessRecord> getAllRecords() {
        return businessRecordRepository.findAllByOrderByDateDesc();
    }

    @Override
    public BusinessRecord getBusinessrecordById(long id) {
        BusinessRecord br = businessRecordRepository.findById(id).get();
        if(br != null){
            return br;
        }
        else {
            return null;
        }
    }

    @Override
    public BusinessRecord updateRecord(BusinessRecord businessRecord, long id) {
        BusinessRecord br = businessRecordRepository.findById(id).get();
        br.setDate(businessRecord.getDate() != null ? businessRecord.getDate() : br.getDate());
        br.setOysterSold(businessRecord.getOysterSold() != 0 ? businessRecord.getOysterSold() : br.getOysterSold());
        br.setTobaccoSold(businessRecord.getTobaccoSold() != 0 ? businessRecord.getTobaccoSold() : br.getTobaccoSold());
        br.setTobaccoSoldQuantity(businessRecord.getTobaccoSoldQuantity() != 0 ? businessRecord.getTobaccoSoldQuantity() : br.getTobaccoSoldQuantity());
        br.setPhoneCardsSold(businessRecord.getPhoneCardsSold() != 0 ? businessRecord.getPhoneCardsSold() : br.getPhoneCardsSold());
        br.setTotal(businessRecord.getTotal() != 0 ? businessRecord.getTotal() : br.getTotal());
        br.setShopSales(businessRecord.getShopSales() != 0 ? businessRecord.getShopSales() : br.getShopSales());
        br.setShop(businessRecord.getShop() != 0 ? businessRecord.getShop() : br.getShop());
        br.setPhoneCardsPurchased(businessRecord.getPhoneCardsPurchased() != 0 ? businessRecord.getPhoneCardsPurchased() : br.getPhoneCardsPurchased());
        br.setTobaccoPurchased(businessRecord.getTobaccoPurchased() != 0 ? businessRecord.getTobaccoPurchased() : br.getTobaccoPurchased());
        br.setTobaccoPurchasedQuantity(businessRecord.getTobaccoPurchasedQuantity() != 0 ? businessRecord.getTobaccoPurchasedQuantity() : br.getTobaccoPurchasedQuantity());
        br.setWages(businessRecord.getWages() != 0 ? businessRecord.getWages() : br.getWages());
        br.setExpenses(businessRecord.getExpenses() != 0 ? businessRecord.getExpenses() : br.getExpenses());
        br.setCommision(businessRecord.getCommision() != 0 ? businessRecord.getCommision() : br.getCommision());
        return businessRecordRepository.save(br);
    }

    @Override
    public String deleteRecord(long id) {
        if(businessRecordRepository.findById(id) != null){
            businessRecordRepository.deleteById(id);
            return "Successfully Deleted...!";
        }
        else {
            return "Can not find Record...!";
        }
    }

    @Override
    public SummaryResponse getSummary(String startDate, String endDate) {
        return getSummaryData(startDate, endDate);
    }

    @Override
    public List<BusinessRecord> getAllRecordsByDateRange(String startDate, String endDate) {
        return businessRecordRepository.getRecordsBetweenDateRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @Override
    public ChartResponse getChartData() {

        ChartResponse chartResponse = new ChartResponse();
        Calendar calendar = Calendar.getInstance();
        ChartData lineChartData = new ChartData();
        ChartData barChartData = new ChartData();
        List<DatePair> datePairList = getDatePairs();

        SummaryResponse summaryResponse = getSummary(simpleDateFormat.format(getBeginDate(calendar)),
                simpleDateFormat.format(getEndDate(calendar)));
        chartResponse.setSummaryResponse(summaryResponse);

        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] productNames = {"Oyster", "Tobacco", "Quantity", "Phone Cards", "Shop Sales"};
        List<Integer> lineChartValues = getProfitPercentage(datePairList);
        List<Integer> barChartValues = getSalesPercentage(calendar);
        List<Integer> sortedLineChartValues = getSortedInteger(lineChartValues);
        List<Integer> sortedBarChartValues = getSortedInteger(barChartValues);

//Line Chart
        lineChartData.setChartType("lineChart");
        lineChartData.setChartLabels(monthNames);
        lineChartData.setChartValues(sortedLineChartValues);
//Bar Chart
        barChartData.setChartType("barchart");
        barChartData.setChartLabels(productNames);
        barChartData.setChartValues(sortedBarChartValues);

        chartResponse.setLineChartData(lineChartData);
        chartResponse.setBarChartData(barChartData);
        return chartResponse;
    }

    List<Integer> getSortedInteger(List<Integer> chartValues){
        List<Integer> sortedChartValues = new ArrayList<>();
        for(int i=chartValues.size()-1; i>=0; i--){
            sortedChartValues.add(chartValues.get(i));
        }
        return sortedChartValues;
    }

    private List<Integer> getSalesPercentage(Calendar calendar){
        List<Integer> chartValues = new ArrayList<>();
        Date startDate = getBeginDate(calendar);
        Date endDate = getEndDate(calendar);
            SummaryResponse sr = getSummary(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
            try {
                double totalSales = sr.getGetAllSums().getSumOfOysterSold() +
                        sr.getGetAllSums().getSumOfTobaccoSold() +
                        sr.getGetAllSums().getSumOfTobaccoSoldQty() +
                        sr.getGetAllSums().getSumOfPhoneCardsSold() +
                        sr.getGetAllSums().getSumOfShopSales();
                double oysterSalesPercentage = sr.getGetAllSums().getSumOfOysterSold() * 100/totalSales;
                double tobaccoSalesPercentage = sr.getGetAllSums().getSumOfTobaccoSold() * 100/totalSales;
                double tobaccoQtySalesPercentage = sr.getGetAllSums().getSumOfTobaccoSoldQty() * 100/totalSales;
                double phoneCardsSalesPercentage = sr.getGetAllSums().getSumOfPhoneCardsSold() * 100/totalSales;
                double shopSalesPercentage = sr.getGetAllSums().getSumOfShopSales() * 100/totalSales;
                chartValues.add((int)oysterSalesPercentage);
                chartValues.add((int)tobaccoSalesPercentage);
                chartValues.add((int)tobaccoQtySalesPercentage);
                chartValues.add((int)phoneCardsSalesPercentage);
                chartValues.add((int)shopSalesPercentage);
            }
            catch (NullPointerException e){
                System.out.println("Exception : " + e.getMessage());
            }
        return chartValues;
    }

    private List<Integer> getProfitPercentage(List<DatePair> datePairList){
        List<Integer> chartValues = new ArrayList<>();
        datePairList.forEach( dates -> {
            SummaryResponse sr = getSummary(dates.getStartDate(), dates.getEndDate());
            try {
                double profit = sr.getProfits().getTotalProfit();
                double totalSales = sr.getGetAllSums().getSumOfOysterSold() +
                        sr.getGetAllSums().getSumOfTobaccoSold() +
                        sr.getGetAllSums().getSumOfTobaccoSoldQty() +
                        sr.getGetAllSums().getSumOfPhoneCardsSold() +
                        sr.getGetAllSums().getSumOfShopSales();
                double profitPercentage = profit * 100/totalSales;
                chartValues.add((int)profitPercentage);
                System.out.println(profitPercentage);
            }
            catch (NullPointerException e){
                System.out.println("Exception : " + e.getMessage());
            }
        });

        return chartValues;
    }

    private List<DatePair> getDatePairs(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int day = localDate.getDayOfMonth();
        List<DatePair> datePairList = new ArrayList<>();
        for(int i=month-1; i>=0 ; i--){
            calendar.set(year, i, day);
            Date beginDate = getBeginDate(calendar);
            Date endDate = getEndDate(calendar);
            datePairList.add(new DatePair(simpleDateFormat.format(beginDate), simpleDateFormat.format(endDate)));
        }
        return datePairList;
    }

    private Date getBeginDate(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        Date beginDate = calendar.getTime();
        return beginDate;
    }

    private Date getEndDate(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndofDay(calendar);
        Date endDate = calendar.getTime();
        return endDate;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    private SummaryResponse getSummaryData(String startDate, String endDate) {
        SummaryResponse summaryResponse = new SummaryResponse();
        if(getAllRecordsByDateRange(startDate, endDate).size() > 0) {
            GetAllSums getAllSums = businessRecordRepository.getAllSumBetweenDates(LocalDate.parse(startDate), LocalDate.parse(endDate));

            ProfitsDto profitsDto = new ProfitsDto();
            profitsDto.setTobaccoProfit(Double.parseDouble(df.format(getAllSums.getSumOfTobaccoSold() - getAllSums.getSumOfTobaccoPurchased())));
            profitsDto.setTobaccoQtyProfit(Double.parseDouble(df.format(getAllSums.getSumOfTobaccoSoldQty() - getAllSums.getSumOfTobaccoPurchasedQty())));
            profitsDto.setPhoneCardsProfit(Double.parseDouble(df.format(getAllSums.getSumOfPhoneCardsSold() - getAllSums.getSumOfPhoneCardsPurchased())));
            profitsDto.setShopProfit(Double.parseDouble(df.format(getAllSums.getSumOfShopSales() - getAllSums.getSumOfShop())));
            profitsDto.setTotalExpenses(Double.parseDouble(df.format(getAllSums.getSumOfWages() + getAllSums.getSumOfExpenses())));
            profitsDto.setTotalProfit(Double.parseDouble(df.format(
                    getAllSums.getSumOfTobaccoSold() - getAllSums.getSumOfTobaccoPurchased() +
                            getAllSums.getSumOfPhoneCardsSold() - getAllSums.getSumOfPhoneCardsPurchased() +
                            getAllSums.getSumOfShopSales() - getAllSums.getSumOfShop() +
                            getAllSums.getSumOfCommision() -
                            (getAllSums.getSumOfWages() + getAllSums.getSumOfExpenses())
                    ))
            );

            summaryResponse.setGetAllSums(getAllSums);
            summaryResponse.setProfits(profitsDto);
            return summaryResponse;
        }

        return summaryResponse;
    }
}
