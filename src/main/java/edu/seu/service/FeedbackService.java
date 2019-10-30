package edu.seu.service;

import edu.seu.dao.FeedbackDao;
import edu.seu.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    public void insertFeedback(Feedback feedback){
        feedbackDao.persist(feedback);
    }

    public List<Feedback> showAll(){
        return feedbackDao.selectAll();
    }
}
