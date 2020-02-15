package com.jp.submo.controller;

import com.jp.submo.SubscriptionException;
import com.jp.submo.dto.JpResponseModel;
import com.jp.submo.dto.SubscriptionDto;
import com.jp.submo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.jp.submo.util.SubscriptionUtility.error;

@RestController
@CrossOrigin()
public class SubscriptionModuleController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping(value = "/health")
    public ResponseEntity<String> getIngressHealth() {
        System.out.println("Hello , welcome to subscription Module.");
        return new ResponseEntity<String>("Hi, I am subscription Module. Up and running", HttpStatus.OK);
    }

    @PostMapping("/create-subscription")
    public ResponseEntity<JpResponseModel> createSubscription(@RequestBody() SubscriptionDto subscriptionDto) {
        try {

            return new ResponseEntity<>(subscriptionService.createSubscription(subscriptionDto),
                    HttpStatus.OK);
        } catch (SubscriptionException subExp) {
            return new ResponseEntity<>(error(subExp.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(error(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
