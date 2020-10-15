package com.example.CoronaTracker.Services;


import com.example.CoronaTracker.Models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class CoronaDataServices {

    private static String VIRUS_DATA_URI ="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationStats> getLocationStatsList() {
        return locationStatsList;
    }

    private List<LocationStats> locationStatsList = new ArrayList<LocationStats>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        List<LocationStats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URI)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        StringReader csvBody = new StringReader(response.body());

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBody);

        for (CSVRecord record : records)
        {

            LocationStats location = new LocationStats();
            location.setState(record.get("Province/State"));
            location.setCountry(record.get("Country/Region"));
            location.setLatestCases(record.get(record.size()-1));

            int previousDay = Integer.parseInt(record.get(record.size()-2));
            int currentDay = Integer.parseInt(record.get(record.size()-1));

            location.setDelta(currentDay-previousDay);

            newStats.add(location);

        }

        this.locationStatsList = newStats;
    }
}
