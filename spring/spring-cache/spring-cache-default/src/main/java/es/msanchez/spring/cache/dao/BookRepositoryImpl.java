package es.msanchez.spring.cache.dao;

import es.msanchez.spring.cache.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * Implementation to simulate a Database request.
 */
@Slf4j
@Repository
public class BookRepositoryImpl implements BookRepository {

  @Override
  @Cacheable(value = "books", key = "#isbn")
  public Book getByIsbn(final String isbn) {
    this.simulateDatabaseAccess();
    return new Book(isbn, "this is a book title");
  }

  private void simulateDatabaseAccess() {
    try {
      Thread.sleep(3000L);
    }catch(final InterruptedException ex) {
      log.error("There was an error on waiting for a Thread", ex);
    }
  }

  @CacheEvict(value = "books", allEntries = true)
  public void refreshBooks() {
    log.info("Evicted books cache.");
  }

}
