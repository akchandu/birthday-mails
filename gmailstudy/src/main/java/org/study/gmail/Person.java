package org.study.gmail;

public class Person {
	
	private String name;
	private String emailAddress;
	private String occasion;
	private boolean isPersonElder;
	
	public String getOccasion() {
		return occasion;
	}
	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public boolean isPersonElder() {
		return isPersonElder;
	}
	public void setPersonElder(boolean isPersonElder) {
		this.isPersonElder = isPersonElder;
	}
	
}
