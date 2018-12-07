package com.cg.capbook.beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@SuppressWarnings("serial")
@Entity(name="Users")
public class User implements Serializable {
	@Id
	private String emailId;
	private String firstName,lastName,gender;
	private long phoneNumber;
	private String dateOfBirth;
	private String password,confirmPassword;
	private String fullName;
	private String securityQuestion,securityAnswer;
	@Embedded
	private UpdateUser updateUser;
	/*@OneToMany
	private Map<String, User> friendList;*/
	public User() {
		super();
	}
	public User(String firstName, String lastName, String emailid, String gender, long phoneNumber, String dateOfBirth,
			String password, String confirmPassword) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailid;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	
	public User(String emailId, String firstName, String lastName, long phoneNumber, String dateOfBirth,
			UpdateUser updateUser) {
		super();
		this.emailId = emailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.updateUser = updateUser;
	}
	
	public UpdateUser getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(UpdateUser updateUser) {
		this.updateUser = updateUser;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailid() {
		return emailId;
	}

	public void setEmailid(String emailid) {
		this.emailId = emailid;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/*public ArrayList<String> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<String> friendList) {
		this.friendList = friendList;
	}*/

	public String getFullName() {
		setFullName();
		return fullName;
	}

	public void setFullName() {
		this.fullName = firstName+" "+lastName;;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber != other.phoneNumber)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SignUp [firstName=" + firstName + ", lastName=" + lastName + ", emailid=" + emailId + ", gender=" + gender
				+ ", phoneNumber=" + phoneNumber + ", dateOfBirth=" + dateOfBirth + "]";
	}
}