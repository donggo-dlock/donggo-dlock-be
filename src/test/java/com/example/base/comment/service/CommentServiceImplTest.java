package com.example.base.comment.service;

import com.example.base.comment.controller.port.CommentService;
import com.example.base.comment.controller.request.CommentCreateRequest;
import com.example.base.comment.controller.response.CommentResponse;
import com.example.base.comment.domain.Comment;
import com.example.base.comment.domain.ReferenceType;
import com.example.base.commentable.domain.Commentable;
import com.example.base.common.exception.PasswordNotMatchException;
import com.example.base.common.exception.ResourceNotFoundException;
import com.example.base.food.domain.Food;
import com.example.base.mock.TestClockHolder;
import com.example.base.mock.TestContainer;
import com.example.base.reportable.domain.ActiveStatus;
import com.example.base.reportable.domain.dto.ReportableDelete;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.SliceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentServiceImplTest {

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        TestContainer testContainer = new TestContainer(new TestClockHolder(10000L));
        commentService = testContainer.commentService;

        Food food1 = generateFood();
        testContainer.foodRepository.save(food1);

    }

    private Food generateFood() {
        Food food1 = new Food();
        food1.setId(1L);
        food1.setName("피자");
        food1.setPassword("1234");
        food1.setContent("피자는 맛있다");
        food1.setDaysBeforeTest(3);
        food1.setMainIngredient("밀가루&치즈&토마토소스");
        food1.setViews(0);
        food1.setLikes(0);
        food1.setDislikes(0);
        food1.setCreatedAt(10000L);
        food1.setStatus(ActiveStatus.ACTIVE);
        food1.setUserInformation("127.0.0.1:2023-12-12");
        return food1;
    }

    @Test
    void 댓글을_정상적으로_생성할_수_있다() {
        // given
        CommentCreateRequest request = CommentCreateRequest.builder()
                .referenceType(ReferenceType.FOOD)
                .name("홍길동")
                .password("1234")
                .content("댓글 내용")
                .referenceId(1L)
                .build();
        String userInformation = "127.0.0.1:2024-01-30";

        // when
        commentService.create(request, userInformation);

        // then
        Comment comment = commentService.get(1L);
        Commentable commentable = comment.getReference();
        assertThat(comment.getName()).isEqualTo("홍길동");
        assertThat(comment.getPassword()).isEqualTo("1234");
        assertThat(comment.getContent()).isEqualTo("댓글 내용");
        assertThat(comment.getReferenceType()).isEqualTo(ReferenceType.FOOD);
        assertThat(comment.getCreatedAt()).isEqualTo(10000L);
        assertThat(comment.getUserInformation()).isEqualTo(userInformation);
        assertThat(comment.getStatus()).isEqualTo(ActiveStatus.ACTIVE);
        assertThat(commentable).isInstanceOf(Food.class);
    }

    @Test
    void 댓글을_스크롤해서_가져올_수_있다() {
        //given
        ReferenceType referenceType = ReferenceType.FOOD;
        Long referenceId = 1L;

        PageCreate pageCreate = PageCreate.builder()
                .page(1)
                .size(10)
                .build();

        //when
        SliceResponse<CommentResponse> sliceResponse = commentService.get(referenceType, referenceId, pageCreate);

        //then
        assertThat(sliceResponse.getContent()).hasSize(5);
        assertThat(sliceResponse.hasPrevious()).isFalse();
        assertThat(sliceResponse.isHasNext()).isFalse();
    }

    @Test
    void 댓글_비밀번호가_일치하면_정상적으로_삭제된다() {
        //given
        ReportableDelete reportableDelete = ReportableDelete.builder()
                .password("1234")
                .build();

        //when
        commentService.delete(1L, reportableDelete);

        //then
        assertThatThrownBy( () -> commentService.get(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 댓글_비밀번호가_일치하지_않으면_에러가_발생한다() {
        //given
        ReportableDelete reportableDelete = ReportableDelete.builder()
                .password("1230")
                .build();

        //when
        //then
        assertThatThrownBy( () -> commentService.delete(1L, reportableDelete))
                .isInstanceOf(PasswordNotMatchException.class);
    }
}