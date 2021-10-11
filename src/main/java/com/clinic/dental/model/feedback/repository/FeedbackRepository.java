package com.clinic.dental.model.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clinic.dental.model.feedback.FeedbackEntity;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>{

}
