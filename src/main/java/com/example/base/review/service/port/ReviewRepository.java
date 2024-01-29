package com.example.base.review.service.port;


import com.example.base.review.domain.Review;
import com.example.base.review.domain.dto.ReviewSearch;
import com.example.base.web.dto.PageCreate;
import com.example.base.web.dto.PageResponse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository {
     @Transactional(propagation = Propagation.MANDATORY)
     void save(Review review);

     @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
     Review get(Long id);

     @Transactional(propagation = Propagation.MANDATORY)
     void delete(Review review);

     @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
     PageResponse<Review> getPage(PageCreate pageCreate, ReviewSearch reviewSearch);
}
