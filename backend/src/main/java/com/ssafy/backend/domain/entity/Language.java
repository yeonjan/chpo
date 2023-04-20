package com.ssafy.backend.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import com.ssafy.backend.domain.entity.common.LanguageType;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "language")
public class Language {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private LanguageType type;

}
