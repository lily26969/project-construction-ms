package tn.esprit.tpfoyer.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s";

    @GetMapping("/reverse-geocode")
    public ResponseEntity<?> reverseGeocode(@RequestParam double lat, @RequestParam double lon) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(NOMINATIM_API_URL, lat, lon);

            Map response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.isEmpty()) {
                return ResponseEntity.status(404).body("Location not found");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching location: " + e.getMessage());
        }
    }
}
