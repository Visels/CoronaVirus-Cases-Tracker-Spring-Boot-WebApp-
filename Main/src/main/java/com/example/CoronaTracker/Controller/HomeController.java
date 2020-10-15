package com.example.CoronaTracker.Controller;

import com.example.CoronaTracker.Models.LocationStats;
import com.example.CoronaTracker.Services.CoronaDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.stream.Location;
import java.util.List;


@Controller
public class HomeController {

    CoronaDataServices coronaDataServices;

    @Autowired
    public HomeController(CoronaDataServices coronaDataServices){
        this.coronaDataServices = coronaDataServices;
    }

    @GetMapping("/")
    public String home(Model model){

        //GETTING THE TOTAL CASES
        List<LocationStats> allStats = coronaDataServices.getLocationStatsList();
        int totalCases = allStats.stream().mapToInt(stat -> Integer.parseInt(stat.getLatestCases())).sum();

        model.addAttribute("locationStats" , allStats);
        model.addAttribute("totalReportedCases", totalCases);
        return "home";
    }

}
