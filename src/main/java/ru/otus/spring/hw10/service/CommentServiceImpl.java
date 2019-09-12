package ru.otus.spring.hw10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.dao.CommentRepository;
import ru.otus.spring.hw10.models.Comment;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean update(long id, String text) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        optionalComment.ifPresent(comment ->
                commentRepository.save(optionalComment.get().withText(text))
        );

        return optionalComment.isPresent();
    }
}
