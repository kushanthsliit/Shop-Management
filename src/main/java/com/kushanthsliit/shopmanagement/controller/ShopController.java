package com.kushanthsliit.shopmanagement.controller;

import com.kushanthsliit.shopmanagement.model.BusinessRecord;
import com.kushanthsliit.shopmanagement.repository.BusinessRecordRepository;
import com.kushanthsliit.shopmanagement.response.ApiResponse;
import com.kushanthsliit.shopmanagement.response.ChartResponse;
import com.kushanthsliit.shopmanagement.response.SummaryResponse;
import com.kushanthsliit.shopmanagement.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin("*")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private BusinessRecordRepository businessRecordRepository;

    @GetMapping("/startDate/{startdate}/endDate/{enddate}")
    public String test(@PathVariable String startdate, @PathVariable String enddate){
        return "Hi";
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
            return "User Logged with ".concat(username);
    }

    @PostMapping("/add-record")
    public @ResponseBody ResponseEntity<ApiResponse> addRecord(@RequestBody BusinessRecord businessRecord){
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Successfully Added." ,shopService.addRecord(businessRecord)));
    }

    @GetMapping("/get-all-records")
    public @ResponseBody ResponseEntity<List<BusinessRecord>> getAllRecords(){
        return ResponseEntity.ok(shopService.getAllRecords());
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<BusinessRecord> getBusinessRecordById(@PathVariable long id){
        return ResponseEntity.ok(shopService.getBusinessrecordById(id));
    }

    @PutMapping("/update-record/id/{id}")
    public @ResponseBody ResponseEntity<BusinessRecord> updateRecord(@RequestBody BusinessRecord businessRecord, @PathVariable long id){
        return ResponseEntity.ok(shopService.updateRecord(businessRecord, id));
    }

    @DeleteMapping("/delete/id/{id}")
    public @ResponseBody ResponseEntity<ApiResponse<String>> deleteRecord(@PathVariable long id){
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "SuccessFully Deleted",
                shopService.deleteRecord(id)));
    }

    @GetMapping("/summary/startDate/{startDate}/endDate/{endDate}")
    public @ResponseBody ResponseEntity<ApiResponse<SummaryResponse>> getSummaryBetweenDateRange(@PathVariable String startDate,
                                                                                                 @PathVariable String endDate){
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Summary Retrieved Successfully.",
                shopService.getSummary(startDate, endDate)));
    }

    @GetMapping("/getRecordsByDateRange/startDate/{startDate}/endDate/{endDate}")
    public @ResponseBody ResponseEntity<List<BusinessRecord>> getAllRecordsByDateRange(@PathVariable String startDate,
                                                                                       @PathVariable String endDate){
        return ResponseEntity.ok(shopService.getAllRecordsByDateRange(startDate, endDate));
    }

    @GetMapping("/chartData")
    public @ResponseBody ChartResponse getChartData(){
        return shopService.getChartData();
    }
}
