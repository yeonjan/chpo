package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.algorithm.dto.response.BojInformationResponseDTO;
import com.ssafy.backend.domain.entity.common.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "baekjoon")
public class Baekjoon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "tier", nullable = false)
	private String tier;

	@Column(name = "pass_count", nullable = false)
	private int passCount;

	@Column(name = "try_fail_count", nullable = false)
	private int tryFailCount;

	@Column(name = "submit_count", nullable = false)
	private int submitCount;

	@Column(name = "fail_count", nullable = false)
	private int failCount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "boj_previous_rank", nullable = false)
	private long previousRank;

	@Builder
	public Baekjoon(long id, String tier, int passCount, int tryFailCount, int submitCount, int failCount, User user) {
		this.id = id;
		this.tier = tier;
		this.passCount = passCount;
		this.tryFailCount = tryFailCount;
		this.submitCount = submitCount;
		this.failCount = failCount;
		this.user = user;
	}

	public static Baekjoon createBaekjoon(BojInformationResponseDTO bojInformationResponseDTO, User user) {
		return Baekjoon.builder()
			.tier(bojInformationResponseDTO.getTier())
			.passCount(bojInformationResponseDTO.getPassCount())
			.tryFailCount(bojInformationResponseDTO.getTryFailCount())
			.submitCount(bojInformationResponseDTO.getSubmitCount())
			.failCount(bojInformationResponseDTO.getFailCount())
			.user(user)
			.build();
	}

	public void updateBaekjoon(BojInformationResponseDTO bojInformationResponseDTO) {
		this.tier = bojInformationResponseDTO.getTier();
		this.passCount = bojInformationResponseDTO.getPassCount();
		this.tryFailCount = bojInformationResponseDTO.getTryFailCount();
		this.submitCount = bojInformationResponseDTO.getSubmitCount();
		this.failCount = bojInformationResponseDTO.getFailCount();
	}
}
