package com.example.base.review;

import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.common.exception.PasswordNotMatchException;
import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.mock.TestClockHolder;
import com.example.base.mock.TestContainer;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.review.controller.request.ReviewCreateRequest;
import com.example.base.review.controller.response.ReviewInfoResponse;
import com.example.base.review.controller.response.ReviewResponse;
import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.review.service.port.ReviewRepository;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
class ReviewServiceImplTest {
    private ReviewService reviewService;
    private String password = "qlalfqjsghdlqslek";

    @BeforeEach
    void init(){
        ClockHolder clockHolder = new TestClockHolder(172800000L);
        TestContainer testContainer = new TestContainer(clockHolder);
        reviewService = testContainer.reviewService;
        ReviewRepository reviewRepository = testContainer.reviewRepository;
        for (int i = 0; i < 6; i++) {
            reviewRepository.save(generateTestData(clockHolder, i));
        }
    }

    private Review generateTestData(ClockHolder clockHolder, int idx) {
        ActiveStatus activeStatus = ActiveStatus.ACTIVE;
        if (idx == 2)
            activeStatus = ActiveStatus.INACTIVE;

        Review review = new Review();
        review.setName("브라키오의 대장 내시경 후기"+idx);
        review.setContent("내시경은 무서웠지만, 브라키오의 대장은 더 무서웠다.");
        review.setUserInformation("123.123.123.123:2024-01-30");
        review.setPassword("qlalfqjsghdlqslek");
        review.setStatus(activeStatus);
        review.setGender('M');
        review.setAge(19);
        review.setSleepFlag(true);
        review.setResult("이상없음");
        review.setCreatedAt(clockHolder.millis());
        review.setLikes(0);
        review.setDislikes(0);
        review.setViews(0);
        return review;
    }

    @Test
    void 리뷰가_정상적으로_등록된다(){
        //given
        ReviewCreateRequest reviewCreateRequest = ReviewCreateRequest.builder()
                .name("찬영이의 내시경 후기")
                .content("찬영이는 은근 겁이 많은 편이라, 무서웠다.")
                .password("1234")
                .age(19)
                .gender('M')
                .sleepFlag(false)
                .result("용종&기타")
                .build();
        String userInformation = "127.0.0.1:2024-01-23";

        //when
        reviewService.create(reviewCreateRequest, userInformation);

        //then
        Review review = reviewService.getByName("찬영이의 내시경 후기");
        assertThat(review.getName()).isEqualTo("찬영이의 내시경 후기");
        assertThat(review.getContent()).isEqualTo("찬영이는 은근 겁이 많은 편이라, 무서웠다.");
        assertThat(review.getAge()).isEqualTo(19);
        assertThat(review.getGender()).isEqualTo('M');
        assertThat(review.getSleepFlag()).isFalse();
        assertThat(review.getResult()).isEqualTo("용종&기타");
        assertThat(review.getCreatedAt()).isEqualTo(172800000L);
        assertThat(review.getViews()).isZero();
        assertThat(review.getLikes()).isZero();
        assertThat(review.getDislikes()).isZero();
        assertThat(review.getUserInformation()).isEqualTo("127.0.0.1:2024-01-23");
        assertThat(review.getPassword()).isEqualTo("1234");
    }

    @Test
    void 비밀번호가_일치하면_리뷰가_정상적으로_삭제된다(){
        //given
        ReportableDelete reportableDelete = ReportableDelete.builder()
                .password(password)
                .build();
        Long id = 1L;

        //when
        reviewService.delete(reportableDelete, id);

        //then
        assertThatThrownBy( () -> reviewService.getReviewInfoResponse(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 비밀번호가_일치하지_않으면_에러가_발생한다(){
        //given
        ReportableDelete reportableDelete = ReportableDelete.builder()
                .password("1230")
                .build();
        Long id = 1L;

        //when
        assertThatThrownBy( () -> reviewService.delete(reportableDelete, id))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    void 음식이_활성화_상태면_id로_상세조회할_수_있고_조회수가_1_증가한다(){
        //given
        Long id = 1L;

        //when
        ReviewInfoResponse reviewInfoResponse = reviewService.getReviewInfoResponse(id);

        //then
        String[] resultList = {"이상없음"};
        assertThat(reviewInfoResponse.name()).isEqualTo("브라키오의 대장 내시경 후기0");
        assertThat(reviewInfoResponse.content()).isEqualTo("내시경은 무서웠지만, 브라키오의 대장은 더 무서웠다.");
        assertThat(reviewInfoResponse.age()).isEqualTo(19);
        assertThat(reviewInfoResponse.views()).isEqualTo(1);
        assertThat(reviewInfoResponse.likes()).isZero();
        assertThat(reviewInfoResponse.dislikes()).isZero();
        assertThat(reviewInfoResponse.gender()).isEqualTo('M');
        assertThat(reviewInfoResponse.sleepFlag()).isTrue();
        assertThat(reviewInfoResponse.result()).isEqualTo(resultList);
        assertThat(reviewInfoResponse.createdAt()).isEqualTo("2024-01-23 00:00");
    }

    @Test
    void 음식이_비활성화_상태면_에러가_발생한다(){
        //given
        Long id = 3L;

        //when
        assertThatThrownBy( () -> reviewService.getReviewInfoResponse(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void 음식_목록을_요청하면_페이지_처리된_응답이_온다(){
        //given
        PageCreate pageCreate = PageCreate.builder()
                .page(0)
                .size(4)
                .build();
        ReviewSearch reviewSearch = ReviewSearch.builder()
                .sortBy("views")
                .build();

        //when
        PageResponse<ReviewResponse> pageResponse = reviewService.getPagination(pageCreate, reviewSearch);

        //when
        assertThat(pageResponse.getTotal()).isEqualTo(5);
        assertThat(pageResponse.getTotalPages()).isEqualTo(2);
        assertThat(pageResponse.hasNext()).isTrue();
        assertThat(pageResponse.hasPrevious()).isFalse();
    }

    @Test
    void 추천하면_추천수가_1_증가한다(){
        //given
        Long id = 1L;
        Boolean recommendationFlag = true;

        //when
        reviewService.updateRecommendations(id, recommendationFlag);

        //then
        ReviewInfoResponse reviewInfoResponse = reviewService.getReviewInfoResponse(id);
        assertThat(reviewInfoResponse.likes()).isEqualTo(1);
    }

}
