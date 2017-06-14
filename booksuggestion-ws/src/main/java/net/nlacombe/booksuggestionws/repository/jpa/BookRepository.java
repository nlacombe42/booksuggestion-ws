package net.nlacombe.booksuggestionws.repository.jpa;

import net.nlacombe.booksuggestionws.data.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer>
{
}
