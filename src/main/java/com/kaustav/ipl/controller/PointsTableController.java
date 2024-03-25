package com.kaustav.ipl.controller;

import com.kaustav.ipl.model.PointsTable;
import com.kaustav.ipl.repository.PointsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/v1/table")
public class PointsTableController {
    @Autowired
    PointsTableRepository tableRepository;

    @GetMapping
    public List<PointsTable> getPointsTable(){
        return tableRepository.findAll();
    }
}
