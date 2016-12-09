package ws.springframework.repositories;

import org.springframework.data.jpa.repository.Query;
import ws.springframework.domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

/**
 * Created by farou_000 on 02/11/2016.
 */
public interface CommentsRepository extends CrudRepository<Comment, Integer> {
    public Comment findByComment(String name);




}
