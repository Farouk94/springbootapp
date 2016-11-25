package guru.springframework.repositories;

import guru.springframework.domain.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by farou_000 on 02/11/2016.
 */
public interface CommentsRepository extends CrudRepository<Comment, Integer> {
    public Comment findByComment(String name);
}
