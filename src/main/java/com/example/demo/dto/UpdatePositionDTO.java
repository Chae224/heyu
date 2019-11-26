package com.example.demo.dto;

import com.example.demo.model.HeyUserAuthentication;
import com.example.demo.model.HeyUserLocation;

public class UpdatePositionDTO {
	
	private HeyUserAuthentication heyUserAuthentication;
	private HeyUserLocation heyUserLocation;

	public HeyUserAuthentication getHeyUserAuthentication() {
		return heyUserAuthentication;
	}
	public void setHeyUserAuthentication(HeyUserAuthentication heyUserAuthentication) {
		this.heyUserAuthentication = heyUserAuthentication;
	}
	public HeyUserLocation getHeyUserLocation() {
		return heyUserLocation;
	}
	public void setHeyUserLocation(HeyUserLocation heyUserLocation) {
		this.heyUserLocation = heyUserLocation;
	}
	

	
}
