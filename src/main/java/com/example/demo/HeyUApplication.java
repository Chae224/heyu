package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HeyUApplication /*extends SpringBootServletInitializer*/ {
	
	   
//	    @Autowired
//	    private HeyUserRepository hUserRep;
//	    @Autowired
//	    private HeyUService myService;
//	    
//	    @PostConstruct
//	    public void createListUsers() {
//	    	System.out.println(" @PostConstruct s'exectute dans le main");
//	    	myService.getListUsers().addAll(hUserRep.findAll());  
//	    }


	/*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HeyUApplication.class);
    }*/

	public static void main(String[] args) {
		SpringApplication.run(HeyUApplication.class, args);
		

	

		
	}

}
