package com.exemple.security.playload.dto;




public class UserWithRoleDTO {
    private Integer userId;
    private String email;
    private String username;
    private String password;
    private Integer roleId;
    private String roleName;
    private String roleStatus;
    
    public UserWithRoleDTO(Integer idUser, String username, String email, String roleName, String roleStatus) {
        this.userId=idUser;
        this.username=username;
        this.email=email;
        this.roleName=roleName;
        this.roleName=roleStatus;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	
	public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	
	
}