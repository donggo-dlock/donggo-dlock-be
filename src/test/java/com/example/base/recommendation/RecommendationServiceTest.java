package com.example.base.recommendation;

import com.example.base.commentable.controller.port.RecommendationService;
import com.example.base.commentable.domain.dto.RecommendationCreate;
import com.example.base.commentable.domain.exception.DuplicateRecommendationException;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.food.domain.Food;
import com.example.base.food.service.port.FoodRepository;
import com.example.base.mock.TestClockHolder;
import com.example.base.mock.TestContainer;
import com.example.base.reportable.domain.ActiveStatus;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecommendationServiceTest {

    private RecommendationService recommendationService;
    private FoodRepository foodRepository;
    private static final String IP = "127.0.0.1";
    @BeforeEach
    void initAll() {
        ClockHolder clockHolder = new TestClockHolder(10000L);
        TestContainer testContainer = new TestContainer(clockHolder);

        recommendationService = testContainer.recommendationService;
        foodRepository = testContainer.foodRepository;

        Food food1 = getFood();
        foodRepository.save(food1);
    }

    private static Food getFood() {
        Food food1 = new Food();
        food1.setId(1L);
        food1.setName("피자");
        food1.setPassword("1234");
        food1.setContent("피자는 맛있다");
        food1.setDaysBeforeTest(3);
        food1.setMainIngredient("#밀가루 #치즈 #토마토소스");
        food1.setViews(0);
        food1.setLikes(0);
        food1.setDislikes(0);
        food1.setCreatedAt(10000L);
        food1.setStatus(ActiveStatus.ACTIVE);
        food1.setUserInformation("127.0.0.1:2023-12-12");
        return food1;
    }

    @Test
    void 같은_게시글에_추천과_비추천을_동시에_할_수_있다() {
        //given
        RecommendationCreate recommendation = RecommendationCreate.builder()
                .RecommendationFlag(true)
                .Category("FOOD")
                .id(1L)
                .build();
        RecommendationCreate notRecommendation = RecommendationCreate.builder()
                .RecommendationFlag(false)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";

        //when
        recommendationService.create(userInformation, recommendation);
        recommendationService.create(userInformation, notRecommendation);

        //then
        Food food = foodRepository.get(1L);
        assertThat(food.getLikes()).isEqualTo(1);
        assertThat(food.getDislikes()).isEqualTo(1);
    }

    @Test
    void 게시글에_추천을_아직_하지_않았다면_추천을_할_수_있다(){
        //given
        RecommendationCreate recommendation = RecommendationCreate.builder()
                .RecommendationFlag(true)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";

        //when
        recommendationService.create(userInformation, recommendation);

        //then
        Food food = foodRepository.get(1L);
        assertThat(food.getLikes()).isEqualTo(1);
    }

    @Test
    void 게시글에_비추천을_아직_하지_않았다면_비추천을_할_수_있다(){
        //given
        RecommendationCreate notRecommendation = RecommendationCreate.builder()
                .RecommendationFlag(false)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";

        //when
        recommendationService.create(userInformation, notRecommendation);

        //then
        Food food = foodRepository.get(1L);
        assertThat(food.getDislikes()).isEqualTo(1);
    }

    @Test
    void 게시글에_추천을_이미_했다면_추천을_할_수_없다(){
        //given
        RecommendationCreate recommendation = RecommendationCreate.builder()
                .RecommendationFlag(true)
                .Category("FOOD")
                .id(1L)
                .build();
        RecommendationCreate recommendationRetry = RecommendationCreate.builder()
                .RecommendationFlag(true)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";
        recommendationService.create(userInformation, recommendation);

        //when
        //then
        assertThatThrownBy( () -> recommendationService.create(userInformation, recommendationRetry))
                .isInstanceOf(DuplicateRecommendationException.class);

    }

    @Test
    void 게시글에_비추천을_이미_했다면_비추천을_할_수_없다(){
        //given
        RecommendationCreate recommendation = RecommendationCreate.builder()
                .RecommendationFlag(false)
                .Category("FOOD")
                .id(1L)
                .build();
        RecommendationCreate recommendationRetry = RecommendationCreate.builder()
                .RecommendationFlag(false)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";
        recommendationService.create(userInformation, recommendation);

        //when
        //then
        assertThatThrownBy( () -> recommendationService.create(userInformation, recommendationRetry))
                .isInstanceOf(DuplicateRecommendationException.class);

    }
    
    @Test
    void 게시글에_추천을_했어도_하루가_지나면_할_수_있다(){
        //given
        RecommendationCreate recommendation = RecommendationCreate.builder()
                .RecommendationFlag(true)
                .Category("FOOD")
                .id(1L)
                .build();
        RecommendationCreate recommendationRetry = RecommendationCreate.builder()
                .RecommendationFlag(true)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";
        recommendationService.create(userInformation, recommendation);
        String userInformationAfterOneDay = IP + ":2023-12-13";

        //when
        recommendationService.create(userInformationAfterOneDay, recommendationRetry);
        //then
        Food food = foodRepository.get(1L);
        assertThat(food.getLikes()).isEqualTo(2);
    }

    @Test
    void 게시글에_비추천을_했어도_하루가_지나면_할_수_있다(){
        //given
        RecommendationCreate recommendation = RecommendationCreate.builder()
                .RecommendationFlag(false)
                .Category("FOOD")
                .id(1L)
                .build();
        RecommendationCreate recommendationRetry = RecommendationCreate.builder()
                .RecommendationFlag(false)
                .Category("FOOD")
                .id(1L)
                .build();
        String userInformation = IP + ":2023-12-12";
        recommendationService.create(userInformation, recommendation);
        String userInformationAfterOneDay = IP + ":2023-12-13";

        //when
        recommendationService.create(userInformationAfterOneDay, recommendationRetry);
        //then
        Food food = foodRepository.get(1L);
        assertThat(food.getDislikes()).isEqualTo(2);

    }
}
