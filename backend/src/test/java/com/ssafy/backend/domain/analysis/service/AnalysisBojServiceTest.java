package com.ssafy.backend.domain.analysis.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.algorithm.repository.BojLanguageQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojLanguageRepository;
import com.ssafy.backend.domain.algorithm.repository.BojQueryRepository;
import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.analysis.dto.BojRankAllComparisonResponse;
import com.ssafy.backend.domain.analysis.dto.BojRankComparisonResponse;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.BaekjoonLanguage;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;

@SpringBootTest
public class AnalysisBojServiceTest {
	@Autowired
	private AnalysisBojService analysisBojService;
	@Autowired
	private BojRepository bojRepository;
	@Autowired
	private BojLanguageRepository bojLanguageRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private BojQueryRepository bojQueryRepository;
	@Autowired
	private BojLanguageQueryRepository bojLanguageQueryRepository;
	@Autowired
	private JobPostingRepository jobPostingRepository;
	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		bojLanguageRepository.deleteAllInBatch();
		bojRepository.deleteAllInBatch();
		jobHistoryRepository.deleteAllInBatch();
		jobPostingRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("백준 1대1 비교 테스트")
	public void compareWithOpponentTest() {
		//given
		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");

		userRepository.saveAll(Arrays.asList(user1, user2));

		Baekjoon boj1 = createBaekjoon(user1, "https://d2gd6pc034wcta.cloudfront.net/tier/14.svg", 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, "https://d2gd6pc034wcta.cloudfront.net/tier/15.svg", 278, 5, 700,
			193, 200);
		bojRepository.saveAll(Arrays.asList(boj1, boj2));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));

		BaekjoonLanguage baekjoonLanguage1 = createBaekjoonLanguage(language1.getId(), "30.00", 20, boj1);
		BaekjoonLanguage baekjoonLanguage2 = createBaekjoonLanguage(language2.getId(), "40.00", 20, boj1);
		BaekjoonLanguage baekjoonLanguage3 = createBaekjoonLanguage(language3.getId(), "50.00", 20, boj1);
		BaekjoonLanguage baekjoonLanguage4 = createBaekjoonLanguage(language3.getId(), "30.00", 20, boj2);
		BaekjoonLanguage baekjoonLanguage5 = createBaekjoonLanguage(language2.getId(), "40.00", 20, boj2);
		BaekjoonLanguage baekjoonLanguage6 = createBaekjoonLanguage(language1.getId(), "50.00", 20, boj2);

		bojLanguageRepository.saveAll(
			Arrays.asList(baekjoonLanguage1, baekjoonLanguage2, baekjoonLanguage3, baekjoonLanguage4, baekjoonLanguage5,
				baekjoonLanguage6));

		//when
		BojRankComparisonResponse response = analysisBojService.compareWithOpponent(user1.getId(), user2.getId());
		//then
		Assertions.assertNotNull(response);
		assertThat(response.getMy().getBojId()).isEqualTo(user1.getBojId());
		assertThat(response.getOpponent().getBojId()).isEqualTo(user2.getBojId());
	}

	@Test
	@DisplayName("백준 공고별 1대 전체 비교 테스트")
	public void compareWithOtherTest() {
		//given
		//given

		User user1 = createUser("user1", "user1");
		User user2 = createUser("user2", "user2");
		User user3 = createUser("user3", "user3");
		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Baekjoon boj1 = createBaekjoon(user1, "https://d2gd6pc034wcta.cloudfront.net/tier/14.svg", 275, 9, 723, 73,
			100);
		Baekjoon boj2 = createBaekjoon(user2, "https://d2gd6pc034wcta.cloudfront.net/tier/15.svg", 278, 5, 700,
			193, 200);
		Baekjoon boj3 = createBaekjoon(user3, "https://d2gd6pc034wcta.cloudfront.net/tier/13.svg", 280, 12, 623,
			173, 300);
		bojRepository.saveAll(Arrays.asList(boj1, boj2, boj3));

		Language language1 = createLanguage("Java 11");
		Language language2 = createLanguage("C++17");
		Language language3 = createLanguage("Python3");
		languageRepository.saveAll(Arrays.asList(language1, language2, language3));

		BaekjoonLanguage baekjoonLanguage1 = createBaekjoonLanguage(language1.getId(), "30.00", 100, boj1);
		BaekjoonLanguage baekjoonLanguage2 = createBaekjoonLanguage(language2.getId(), "40.00", 30, boj1);
		BaekjoonLanguage baekjoonLanguage3 = createBaekjoonLanguage(language3.getId(), "50.00", 17, boj1);
		BaekjoonLanguage baekjoonLanguage4 = createBaekjoonLanguage(language3.getId(), "30.00", 21, boj2);
		BaekjoonLanguage baekjoonLanguage5 = createBaekjoonLanguage(language2.getId(), "40.00", 64, boj2);
		BaekjoonLanguage baekjoonLanguage6 = createBaekjoonLanguage(language1.getId(), "50.00", 23, boj2);
		BaekjoonLanguage baekjoonLanguage7 = createBaekjoonLanguage(language3.getId(), "20.00", 35, boj3);
		BaekjoonLanguage baekjoonLanguage8 = createBaekjoonLanguage(language2.getId(), "20.00", 43, boj3);
		BaekjoonLanguage baekjoonLanguage9 = createBaekjoonLanguage(language1.getId(), "30.00", 78, boj3);

		bojLanguageRepository.saveAll(
			Arrays.asList(baekjoonLanguage1, baekjoonLanguage2, baekjoonLanguage3, baekjoonLanguage4, baekjoonLanguage5,
				baekjoonLanguage6, baekjoonLanguage7, baekjoonLanguage8, baekjoonLanguage9));

		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		JobHistory jobHistory3 = createJobHistory(user3, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2, jobHistory3));
		//when
		BojRankAllComparisonResponse response = analysisBojService.compareWithOther(user1.getId(), jobPosting1.getId());
		//then
		assertThat(response.getMy().getBojId()).isEqualTo(user1.getBojId());
	}

	private BaekjoonLanguage createBaekjoonLanguage(long languageId, String passPercentage, int passCount,
		Baekjoon boj) {
		return BaekjoonLanguage.builder()
			.languageId(languageId)
			.passPercentage(passPercentage)
			.passCount(passCount)
			.baekjoon(boj)
			.build();
	}

	private JobHistory createJobHistory(User user, JobPosting jobPosting) {
		return JobHistory.builder()
			.dDay(LocalDate.now())
			.dDayName("test")
			.statusId(1)
			.jobObjective("백엔드 개발")
			.isDeleted(false)
			.user(user)
			.jobPosting(jobPosting)
			.build();
	}

	private JobPosting createJobPosting(String companyName, String name) {
		return JobPosting.builder()
			.companyName(companyName)
			.name(name)
			.startTime(LocalDate.now())
			.endTime(LocalDate.now())
			.isClose(false)
			.build();

	}

	private Language createLanguage(String name) {
		return Language.builder()
			.name(name)
			.type(LanguageType.BAEKJOON)
			.build();
	}

	private User createUser(String nickname, String bojId) {
		return User.builder().nickname(nickname).bojId(bojId).image("1").isDeleted(false).build();
	}

	private Baekjoon createBaekjoon(User user, String tier, int passCount, int tryFailCount, int submitCount,
		int failCount, int score) {
		return Baekjoon.builder()
			.user(user)
			.tier(tier)
			.passCount(passCount)
			.tryFailCount(tryFailCount)
			.submitCount(submitCount)
			.failCount(failCount)
			.score(score)
			.build();
	}
}
