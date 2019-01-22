package jp.co.sample.form;

public class ItemForm {

	private String name;
	private Integer condition;
	private String parentCategory;
	private String childCategory;
	private String grandchildCategory;
	private String brand;
	private double price;
	private String description;

	//getter,setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public String getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	public String getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}
	public String getGrandchildCategory() {
		return grandchildCategory;
	}
	public void setGrandchildCategory(String grandchildCategory) {
		this.grandchildCategory = grandchildCategory;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "ItemForm [name=" + name + ", condition=" + condition + ", parentCategory=" + parentCategory
				+ ", childCategory=" + childCategory + ", grandchildCategory=" + grandchildCategory + ", brand=" + brand
				+ ", price=" + price + ", description=" + description + "]";
	}
	
}
