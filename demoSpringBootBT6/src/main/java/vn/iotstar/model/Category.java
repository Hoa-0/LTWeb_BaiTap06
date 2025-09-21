package vn.iotstar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Tự tăng ID
	private Long categoryId;

	@Column(nullable = false, length = 100)
	private String categoryName;

	@Column(length = 255)
	private String icon;

	// ===== GETTER & SETTER =====

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
