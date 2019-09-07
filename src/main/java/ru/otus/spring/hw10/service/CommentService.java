package ru.otus.spring.hw10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.dao.CommentRepository;
import ru.otus.spring.hw10.models.Comment;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public boolean update(long id, String text) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            return false;
        }

        commentRepository.save(comment.get().withText(text));
        return true;
    }
}
