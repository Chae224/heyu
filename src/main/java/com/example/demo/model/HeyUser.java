package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class HeyUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long heyUserId;
	private String heyUserName="nobody";
	@JsonIgnore
	private String heyUserPassword="0000";
	@Transient
	@JsonIgnore
    private String heyUPasswordConfirm;
	private String heyUserPic;
	@Transient
	@JsonIgnore
	private Double heyUserLatitude= 0.0;
	@Transient
	@JsonIgnore
	private Double heyUserLongitude= 0.0;
	@Transient
	@JsonIgnore/*Properties("heyUserNearU")*/
	private ArrayList<HeyUser> heyUserNearU;
//	@JsonIgnore
//	private int heyUserSearchRadius;	
	@ManyToMany
	@JsonIgnore
	private Set<Role> roles;
	private String heyUserMessage= "Hello! I'm a new HeyUser";
	
	
	
	
	
	public String getHeyUserMessage() {
		return heyUserMessage;
	}

	public void setHeyUserMessage(String heyUserMessage) {
		this.heyUserMessage = heyUserMessage;
	}

	public void addHeyUserNearU(HeyUser user) {
		this.heyUserNearU.add(user);
	}
	
	public void removeHeyUserNearU(HeyUser user) {
		this.heyUserNearU.remove(user);
	}
	
	public List<HeyUser> getHeyUserNearU() {
		return heyUserNearU;
	}
	public void setHeyUserNearU(ArrayList<HeyUser> heyUserNearU) {
		this.heyUserNearU = heyUserNearU;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getHeyUPasswordConfirm() {
		return heyUPasswordConfirm;
	}
	public void setHeyUPasswordConfirm(String heyUPasswordConfirm) {
		this.heyUPasswordConfirm = heyUPasswordConfirm;
	}
	
	public Long getHeyUserId() {
		return heyUserId;
	}
	public void setHeyUserId(Long heyUserId) {
		this.heyUserId = heyUserId;
	}
	public String getHeyUserName() {
		return heyUserName;
	}
	public void setHeyUserName(String heyUserName) {
		this.heyUserName = heyUserName;
	}
	public String getHeyUserPassword() {
		return heyUserPassword;
	}
	public void setHeyUserPassword(String heyUserPassword) {
		this.heyUserPassword = heyUserPassword;
	}
	public String getHeyUserPic() {
		return heyUserPic;
	}
	public void setHeyUserPic(String heyUserPic) {
		this.heyUserPic = heyUserPic;
	}
	public Double getHeyUserLatitude() {
		return heyUserLatitude;
	}
	public void setHeyUserLatitude(Double heyUserLatitude) {
		this.heyUserLatitude = heyUserLatitude;
	}
	public Double getHeyUserLongitude() {
		return heyUserLongitude;
	}
	public void setHeyUserLongitude(Double heyUserLongitude) {
		this.heyUserLongitude = heyUserLongitude;
	}
//	public int getHeyUserSearchRadius() {
//		return heyUserSearchRadius;
//	}
//	public void setHeyUserSearchRadius(int heyUserSearchRadius) {
//		this.heyUserSearchRadius = heyUserSearchRadius;
//	}
}
