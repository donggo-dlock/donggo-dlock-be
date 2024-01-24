package com.example.base.food;

import com.example.base.common.exception.PasswordNotMatchException;
import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.food.controller.request.FoodCreateRequest;
import com.example.base.food.controller.response.FoodInfoResponse;
import com.example.base.food.controller.response.FoodResponse;
import com.example.base.food.domain.Food;
import com.example.base.food.domain.dto.FoodDelete;
import com.example.base.food.domain.dto.FoodSearch;
import com.example.base.food.service.FoodServiceImpl;
import com.example.base.mock.FakeFoodRepository;
import com.example.base.mock.TestClockHolder;
import com.example.base.mock.TestPasswordHolder;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
class FoodServiceImplTest {
    private FoodServiceImpl foodServiceImpl;
    private FakeFoodRepository fakeFoodRepository;

    @BeforeEach
    void init(){
        fakeFoodRepository = new FakeFoodRepository();
        TestClockHolder testClockHolder = new TestClockHolder(10000L);
        TestPasswordHolder testPasswordHolder = new TestPasswordHolder();
        foodServiceImpl = new FoodServiceImpl(fakeFoodRepository, testClockHolder, testPasswordHolder);

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
        fakeFoodRepository.save(food1);

        Food food2 = new Food();
        food2.setId(2L);
        food2.setName("피자2");
        food2.setPassword("0234");
        food2.setContent("피자는 맛있다");
        food2.setDaysBeforeTest(3);
        food2.setMainIngredient("#밀가루 #치즈 #토마토소스");
        food2.setViews(0);
        food2.setLikes(0);
        food2.setDislikes(0);
        food2.setCreatedAt(10000L);
        food2.setStatus(ActiveStatus.INACTIVE);
        food2.setUserInformation("120.0.0.1:2023-12-12");
        fakeFoodRepository.save(food2);
    }

    @Test
    void 음식이_정상적으로_등록된다(){
        //given
        FoodCreateRequest foodCreateRequest = FoodCreateRequest.builder()
                .name("피자3")
                .content("피자는 맛있다")
                .password("1234")
                .daysBeforeTest(3)
                .mainIngredient("#밀가루 #치즈 #토마토소스")
                .build();
        String userInformation = "127.0.0.1:2024-01-23";

        //when
        foodServiceImpl.create(foodCreateRequest, userInformation);

        //then
        FoodInfoResponse foodInfoResponse = foodServiceImpl.get("피자3");
        assertThat(foodInfoResponse.name()).isEqualTo("피자3");
        assertThat(foodInfoResponse.content()).isEqualTo("피자는 맛있다");
        assertThat(foodInfoResponse.daysBeforeTest()).isEqualTo(3);
        assertThat(foodInfoResponse.mainIngredient()).isEqualTo("#밀가루 #치즈 #토마토소스");
        assertThat(foodInfoResponse.status()).isEqualTo(ActiveStatus.ACTIVE);
        assertThat(foodInfoResponse.views()).isZero();
        assertThat(foodInfoResponse.likes()).isZero();
        assertThat(foodInfoResponse.dislikes()).isZero();
        assertThat(foodInfoResponse.createdAt()).isEqualTo(10000L);
        assertThat(foodInfoResponse.id()).isPositive();
    }

    @Test
    void 비밀번호가_일치하면_음식이_정상적으로_삭제된다(){
        //given
        FoodDelete foodDelete = FoodDelete.builder()
                .password("1234")
                .build();
        Long id = 1L;

        //when
        foodServiceImpl.delete(foodDelete, id);

        //then
        assertThatThrownBy( () -> foodServiceImpl.get(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 비밀번호가_일치하지_않으면_에러가_발생한다(){
        //given
        FoodDelete foodDelete = FoodDelete.builder()
                .password("1230")
                .build();
        Long id = 1L;

        //when
        assertThatThrownBy( () -> foodServiceImpl.delete(foodDelete, id))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @Test
    void 음식이_활성화_상태면_id로_상세조회할_수_있다(){
        //given
        Long id = 1L;

        //when
        FoodInfoResponse foodInfoResponse = foodServiceImpl.get(id);

        //then
        assertThat(foodInfoResponse.name()).isEqualTo("피자");
        assertThat(foodInfoResponse.content()).isEqualTo("피자는 맛있다");
        assertThat(foodInfoResponse.daysBeforeTest()).isEqualTo(3);
        assertThat(foodInfoResponse.mainIngredient()).isEqualTo("#밀가루 #치즈 #토마토소스");
        assertThat(foodInfoResponse.status()).isEqualTo(ActiveStatus.ACTIVE);
        assertThat(foodInfoResponse.views()).isZero();
        assertThat(foodInfoResponse.likes()).isZero();
        assertThat(foodInfoResponse.dislikes()).isZero();
        assertThat(foodInfoResponse.createdAt()).isEqualTo(10000L);
        assertThat(foodInfoResponse.id()).isEqualTo(1L);
    }

    @Test
    void 음식이_비활성화_상태면_에러가_발생한다(){
        //given
        Long id = 2L;

        //when
        assertThatThrownBy( () -> foodServiceImpl.get(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void 음식_목록을_요청하면_페이지_처리된_응답이_온다(){
        //given
        PageCreate pageCreate = PageCreate.builder()
                .page(0)
                .size(10)
                .build();
        FoodSearch foodSearch = FoodSearch.builder()
                .keyword("")
                .sortBy("VIEW")
                .daysBeforeTest(1)
                .build();

        //when
        PageResponse<FoodResponse> pageResponse = foodServiceImpl.getPagination(pageCreate, foodSearch);

        //when
        assertThat(pageResponse.getTotal()).isZero();
        assertThat(pageResponse.getTotalPages()).isZero();
        assertThat(pageResponse.hasNext()).isFalse();
        assertThat(pageResponse.hasPrevious()).isFalse();
    }


}
