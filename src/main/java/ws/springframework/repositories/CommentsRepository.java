package ws.springframework.repositories;

import org.springframework.data.repository.CrudRepository;
import ws.springframework.domain.Comment;

public interface CommentsRepository extends CrudRepository<Comment, Integer> {
    public Comment findByComment(String name);


}
