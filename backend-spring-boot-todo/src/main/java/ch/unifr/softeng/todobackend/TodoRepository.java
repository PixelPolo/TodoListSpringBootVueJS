package ch.unifr.softeng.todobackend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository is an interface to do CRUD operation on a DB.
// CRUD operations is automatically implemented if we extend JpaRepository.
// JPA is for Java Persistence API, we can use Java Object instead SQL queries.
// The Todo_class is the Java Object used by the Java Persistence API.

// @Repository is an object that manipulates entities in a DB.
// The repository object is used inside TodoController.

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Method to find by title
    Todo findByTitle(String title);

}
