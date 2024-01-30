package com.example.base.commentable.domain;

import org.springframework.transaction.annotation.Transactional;

public interface ViewUtils {
    @Transactional
    public void reflectToDatabase(Long key, Integer count);
}
