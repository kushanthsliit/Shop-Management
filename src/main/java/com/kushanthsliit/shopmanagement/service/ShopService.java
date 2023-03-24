package com.kushanthsliit.shopmanagement.service;

import com.kushanthsliit.shopmanagement.model.BusinessRecord;

import java.util.List;

public interface ShopService {

    public BusinessRecord addRecord(BusinessRecord businessRecord);

    public List<BusinessRecord> getAllRecords();

    public BusinessRecord getBusinessrecordById(long id);

    public BusinessRecord updateRecord(BusinessRecord businessRecord, long id);

    public String deleteRecord(long id);
}
