package com.example.base.medium;

import com.example.base.post.controller.response.PostResponse;
import com.example.base.post.service.PostServiceImpl;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/sql/post-service-test-data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/sql/delete-all-data.sql")
})
class PostServiceTest {
    @Autowired
    private PostServiceImpl postServiceImpl;

    @Test
    void getAll_메서드를_통해_pagination_처리된_게시물_목록을_받는다() {
        //given
        PageCreate pageCreate = PageCreate.of(0, 10);

        //when
        PageResponse<PostResponse> result = postServiceImpl.getAll(pageCreate);

        //then
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getTotal()).isEqualTo(2);
        assertThat(result.getPageCreate().getPage()).isZero();
        assertThat(result.getPageCreate().getSize()).isEqualTo(10);
        assertThat(result.getPageCreate().getOffset()).isZero();
        assertThat(result.getTotalPages()).isEqualTo(1);
    }
}
