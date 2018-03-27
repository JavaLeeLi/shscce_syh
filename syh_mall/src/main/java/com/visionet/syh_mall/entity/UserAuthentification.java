package com.visionet.syh_mall.entity;

public class UserAuthentification {
	private String name;//姓名
	private String country;//国家
	private String province;//省份
	private String area;//县市
	private String address;//地址
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "UserAuthentification [name=" + name + ", country=" + country + ", province=" + province + ", area="
				+ area + ", address=" + address + "]";
	}
}
