package com.kushanthsliit.shopmanagement.controller;

import com.kushanthsliit.shopmanagement.model.BusinessRecord;
import com.kushanthsliit.shopmanagement.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
            return "User Logged with ".concat(username);
    }

    @PostMapping("/add-record")
    public @ResponseBody ResponseEntity<BusinessRecord> addRecord(@RequestBody BusinessRecord businessRecord){
        return ResponseEntity.ok(shopService.addRecord(businessRecord));
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
    public @ResponseBody ResponseEntity<String> deleteRecord(@PathVariable long id){
        return ResponseEntity.ok(shopService.deleteRecord(id));
    }
}
