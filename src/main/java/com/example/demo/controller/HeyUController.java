package com.example.demo.controller;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.LoginDTOSent;
import com.example.demo.dto.ModifyHeyUserSettingsDTOExpected;
import com.example.demo.dto.UpdatePositionDTO;
import com.example.demo.dto.UpdatePositionResponseDto;
import com.example.demo.dto.AuthenticationDTOExpected;
import com.example.demo.model.HeyUser;
import com.example.demo.service.HeyUService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.txw2.Document;

@RestController
public class HeyUController {

	@Autowired
	HeyUService hUServ;

	@GetMapping("/getTest")
	public LoginDTOSent getTest() {
		System.out.println("getTest !");
		LoginDTOSent thisUserLoginDto = new LoginDTOSent();
		thisUserLoginDto.setConnected(false);
		thisUserLoginDto.setMessageSent("We haven't been able to connect you, please check your informations... ");
		return thisUserLoginDto;
	}

	@PostMapping("/updateLocation")
	public UpdatePositionResponseDto updateLocation(@RequestBody UpdatePositionDTO newLocationDto) throws JsonProcessingException {
		
		System.out.println("/updateLocation is called");
		ObjectMapper mapper = new ObjectMapper();
		// convert user object to json string and return it 
		System.out.println(mapper.writeValueAsString(newLocationDto));
		

		HeyUser searchedUser = hUServ.searchUserInArrayList( newLocationDto.getHeyUserAuthentication().getHeyUserName(),newLocationDto.getHeyUserAuthentication().getHeyUserPassword(), hUServ.getListUsers());
			UpdatePositionResponseDto response = new UpdatePositionResponseDto();
			if(searchedUser != null) {
			hUServ.updateLocation(newLocationDto.getHeyUserLocation().getHeyUserLongitude(), newLocationDto.getHeyUserLocation().getHeyUserLatitude(), searchedUser);
			
			response.setHeyUserNearU(hUServ.findNearUser(searchedUser, newLocationDto.getHeyUserLocation().getHeyUserSearchRadius(), hUServ.getListUsers()));
			}
			return response;
//		}
//		return null;
	}



	@PostMapping("/login")
	public LoginDTOSent login(@RequestBody AuthenticationDTOExpected loginDTO) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// convert user object to json string and return it 
		System.out.println(mapper.writeValueAsString(loginDTO));
		HeyUser searchedUser = hUServ.searchUserInArrayList(loginDTO.getHeyUserAuthentication().getHeyUserName(),loginDTO.getHeyUserAuthentication().getHeyUserPassword(), hUServ.getListUsers());
		LoginDTOSent thisUserLoginDto = new LoginDTOSent();

		if(searchedUser != null) {
			//thisUserLoginDto.getUserconnected().setHeyUserName(searchedUser);
			thisUserLoginDto.setUserconnected(searchedUser);
			thisUserLoginDto.setConnected(true);
			thisUserLoginDto.setMessageSent("You've been successfully logged in");
			return thisUserLoginDto;
		} else {
			thisUserLoginDto.setConnected(false);
			thisUserLoginDto.setMessageSent("We haven't been able to connect you, please check your informations... ");
			return thisUserLoginDto;
		}



	}

	@PostMapping("/registering")
	public LoginDTOSent register(@RequestBody AuthenticationDTOExpected registeringDTO) throws JsonProcessingException {
		System.out.println("work");
		ObjectMapper mapper = new ObjectMapper();
		// convert user object to json string and return it 
		System.out.println(mapper.writeValueAsString(registeringDTO));
		
		HeyUser searchedUser = hUServ.searchUserInArrayListByName(registeringDTO.getHeyUserAuthentication().getHeyUserName(), hUServ.getListUsers());
		LoginDTOSent thisUserLoginDto = new LoginDTOSent();
		if(searchedUser != null) {
			thisUserLoginDto.setConnected(false); // A CONFIRMER (si un utilisateur entre en dur "true" dans le false...
			thisUserLoginDto.setMessageSent("There's already a HeyUser with that name.");
			return thisUserLoginDto;
		} else if(registeringDTO.getHeyUserAuthentication().getHeyUserPassword().equals(registeringDTO.getHeyUserAuthentication().getHeyUserConfirmPassword())){
			thisUserLoginDto.getUserconnected().setHeyUserName(registeringDTO.getHeyUserAuthentication().getHeyUserName());
			thisUserLoginDto.getUserconnected().setHeyUserPassword(registeringDTO.getHeyUserAuthentication().getHeyUserPassword());
			thisUserLoginDto.setConnected(true);
			thisUserLoginDto.setMessageSent("Welcome "+thisUserLoginDto.getUserconnected().getHeyUserName() + " ! Your account has been successfully created !" );
			hUServ.save(thisUserLoginDto.getUserconnected());
			hUServ.getListUsers().add(thisUserLoginDto.getUserconnected());
			return thisUserLoginDto;	
		} else {    		
			thisUserLoginDto.setConnected(false); // A CONFIRMER (si un utilisateur entre en dur "true" dans le false...
			thisUserLoginDto.setMessageSent("Your ConfirmPasword is invalid. Please check your informations.");
			return thisUserLoginDto;


		}


	}



	@PostMapping("/ModifyHeyUserSettings")
	public LoginDTOSent modifySettings(@RequestBody ModifyHeyUserSettingsDTOExpected settingsDTO) {
		HeyUser searchedUser = hUServ.searchUserInArrayList( settingsDTO.getHeyUserAuthentication().getHeyUserName(),settingsDTO.getHeyUserAuthentication().getHeyUserPassword(), hUServ.getListUsers());
		LoginDTOSent thisUserLoginDto = new LoginDTOSent();
		if(searchedUser != null) {
			
			thisUserLoginDto.setUserconnected(searchedUser);
			thisUserLoginDto.getUserconnected().setHeyUserMessage(settingsDTO.getHeyUserProfil().getHeyUserMessage());
			thisUserLoginDto.getUserconnected().setHeyUserPic(settingsDTO.getHeyUserProfil().getHeyUserPic());
			thisUserLoginDto.setConnected(true);
			thisUserLoginDto.setMessageSent("Your modifications has been successfully registered !");
			System.out.println("BUG ICI => "+searchedUser.getHeyUserName());
			hUServ.save(thisUserLoginDto.getUserconnected());
			

			int indexOfUserConnected = hUServ.getListUsers().indexOf(searchedUser);
			hUServ.getListUsers().add(indexOfUserConnected, thisUserLoginDto.getUserconnected());
			return thisUserLoginDto;
		} else {
			thisUserLoginDto.setConnected(false); 
			thisUserLoginDto.setMessageSent("You don't have the permission to modify this content... ");
			return thisUserLoginDto;
		}
	}
	
	
	/**
	 * Update the picture passed in Param from a post request.
	 * @param file
	 * @return
	 */
    @PostMapping("/upload")
	public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
    	//TEST TO BE DELETED
		HeyUser searchedUser = hUServ.searchUserInArrayList( "a", "a", hUServ.getListUsers());
		//END OF TEST
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = Paths.get("users_pictures/"+fileName);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.path(fileName)
				.toUriString();
		
		//TEST TO BE DELETED
		searchedUser.setHeyUserPic(fileDownloadUri);
		hUServ.save(searchedUser);
		//END OF TEST
		return ResponseEntity.ok(fileDownloadUri);
	}
	
	@GetMapping("/files/download/{fileName:.+}")
	public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
		Path path = Paths.get("users_pictures/"+fileName);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}



}