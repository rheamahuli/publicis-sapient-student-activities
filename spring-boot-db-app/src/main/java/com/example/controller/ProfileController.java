package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.ProfileNotFoundException;
import com.example.model.beans.Profile;
import com.example.model.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @PostMapping
    public ResponseEntity<Object> store(@RequestBody Profile profile) { // profileId, name, dob, phone
        Profile storedProfile = profileService.storeProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(storedProfile);
    }
    @GetMapping
    public ResponseEntity<Object> getProfiles() {
        List<Profile> list = profileService.fetchProfiles();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable("id") int id) {
        try {
            Profile profile = profileService.fetchProfile(id);
            return ResponseEntity.status(HttpStatus.OK).body(profile);
        } catch (ProfileNotFoundException e) {
            // Exception has getMessage() that returns the message initialized when exception occurs
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", e.getMessage()); // e.getMessage(): Profile with an id not found
            map.put("status", "404");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }   
    }

    
   
}
