package org.aisframework.web.test;
//标准的JavaBean类有私有属性都对应有get/set方法，有无参数的构造方法
public class User {
	private String userName;
	private String userpass;
	private int age;
	public User(){}
	
	public User(String userName, String userpass, int age) {
		super();
		this.userName = userName;
		this.userpass = userpass;
		this.age = age;
	}

	public User(String userName, String userpass) {
		this.userName = userName;
		this.userpass = userpass;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", userpass='" + userpass + '\'' +
				", age=" + age +
				'}';
	}
}
