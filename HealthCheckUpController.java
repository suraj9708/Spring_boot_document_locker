package com.demo.project.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealthCheckUpController  {
          @GetMapping("/health")
          public String healthCheck() { 


			return "Application is healthy";
        	  
        	  
          }
}
