package com.example.base.review.service.port;


import com.example.base.review.domain.Review;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository {
     @Transactional(propagation = Propagation.MANDATORY)
     void save(Review review);

     @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
     Review get(Long id);
}
