package com.example.base.mock;

import com.example.base.comment.controller.port.CommentService;
import com.example.base.comment.service.CommentServiceImpl;
import com.example.base.comment.service.port.CommentRepository;
import com.example.base.commentable.controller.port.RecommendationService;
import com.example.base.commentable.service.RecommendationServiceImpl;
import com.example.base.commentable.service.port.RecommendationHolder;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.food.controller.port.FoodService;
import com.example.base.food.service.FoodServiceImpl;
import com.example.base.food.service.port.FoodRepository;
import com.example.base.review.controller.port.ReviewService;
import com.example.base.review.service.ReviewServiceImpl;
import com.example.base.review.service.port.ReviewRepository;

//Spring IOC 컨테이너 기능 구현
public class TestContainer {
    public final FoodRepository foodRepository;
    public final ReviewRepository reviewRepository;
    public final CommentRepository commentRepository;
    public final FoodService foodService;
    public final RecommendationService recommendationService;
    public final RecommendationHolder recommendationHolder;
    public final ReviewService reviewService;
    public final CommentService commentService;

    public TestContainer(ClockHolder clockHolder) {
        this.foodRepository = new FakeFoodRepository();
        this.reviewRepository = new FakeReviewRepository();
        this.recommendationHolder = new TestRecommendationHolder();
        this.commentRepository = new FakeCommentRepository();
        TestFoodViewHolder testFoodViewHolder = new TestFoodViewHolder(foodRepository);
        TestReviewViewHolder testReviewViewHolder = new TestReviewViewHolder(reviewRepository);
        this.foodService = FoodServiceImpl.builder()
                .foodRepository(foodRepository)
                .foodViewHolder(testFoodViewHolder)
                .clockHolder(clockHolder)
                .passwordHolder(new TestPasswordHolder())
                .build();
        this.reviewService =  ReviewServiceImpl.builder()
                .reviewRepository(reviewRepository)
                .reviewViewHolder(testReviewViewHolder)
                .clockHolder(clockHolder)
                .passwordHolder(new TestPasswordHolder())
                .build();
        this.recommendationService = RecommendationServiceImpl.builder()
                .recommendationHolder(recommendationHolder)
                .foodService(foodService)
                .reviewService(reviewService)
                .build();
        this.commentService = CommentServiceImpl.builder()
                .commentRepository(commentRepository)
                .clockHolder(clockHolder)
                .passwordHolder(new TestPasswordHolder())
                .foodService(foodService)
                .reviewService(reviewService)
                .build();
    }
}
