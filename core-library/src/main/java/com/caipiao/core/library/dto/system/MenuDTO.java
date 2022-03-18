package com.caipiao.core.library.dto.system;

public class MenuDTO {

	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 权限
	 */
	private String permission;
	
	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 地址
	 */
	private String url;
	
	/**
	 * 父级ID
	 */
	private int parentId;

	/**
	 * 移动ID
	 */
	private int moveId;
	
	/**
	 * 构造器
	 */
	public MenuDTO() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPermission() {
		return permission;
	}


	public void setPermission(String permission) {
		this.permission = permission;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public int getParentId() {
		return parentId;
	}


	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getMoveId() {
		return moveId;
	}

	public void setMoveId(int moveId) {
		this.moveId = moveId;
	}
}
