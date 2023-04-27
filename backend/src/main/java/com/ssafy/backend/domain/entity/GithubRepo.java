package com.ssafy.backend.domain.entity;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;
import static javax.persistence.GenerationType.*;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import com.ssafy.backend.global.response.exception.CustomException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "github_repository")
public class GithubRepo extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Lob
	@Column(name = "readme", nullable = false)
	private String readme;

	@Column(name = "repository_link", nullable = false)
	private String repositoryLink;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "github_id", nullable = false)
	private Github github;

	public static GithubRepo create(String name, String readme, String repositoryLink, Github github) {
		return GithubRepo.builder().name(name).readme(readme).repositoryLink(repositoryLink).github(github).build();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		GithubRepo repo = (GithubRepo)object;
		return this.getName().equals(repo.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName());
	}

	public void validateGithubId(long githubId) {
		if (this.getGithub().getId() != githubId) {
			throw new CustomException(INCONSISTENT_GITHUB_ID);
		}
	}

}
