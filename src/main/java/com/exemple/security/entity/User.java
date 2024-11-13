package com.exemple.security.entity;




import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User extends ClassEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Employes employes;

	@Size(max = 100)
	@Column(name = "USERNAME", length = 100)
    private String username;

	@Size(max = 100)
	@Column(name = "PASSWORD", length = 100)
    private String password;

	@Size(max = 100)
	private String first;

    @ElementCollection
    private List<UserRole> roles;

}

