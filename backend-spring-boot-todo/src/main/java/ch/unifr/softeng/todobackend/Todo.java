package ch.unifr.softeng.todobackend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// This entity is used by the Java Persistence API.
// We use objects from this Entity to interact with the DB,
// instead making SQL queries...

@Entity
public class Todo {

    @Id // PrimaryKey indicator
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generated
    private Long id;

    // Other attributes of the entity...
    // "order" is a reserved keyword in H2,
    // we use orderNumber instead.
    private String title;
    private boolean completed;
    private Integer orderNumber;

    // Set of Tag for the Many-to-Many relationships between tags and todos.
    // @JsonIgnoreProperties("todos") avoids serialization infinite loop
    // Serialize a todos, then its tag, but not the tags todos back again...
    @ManyToMany
    @JsonIgnoreProperties("todos")
    private Set<Tag> tags;

    // Constructor for the Entity stored in DB as a JSON format
    @JsonCreator
    public Todo(@JsonProperty("title") String title,
                @JsonProperty("completed") boolean completed,
                @JsonProperty("order") Integer orderNumber) {
        this.title = title;
        this.completed = completed;
        this.orderNumber = orderNumber;
    }

    // Default constructor required by JPA for entity instantiation
    protected Todo() {
    }

    // Getter for the title
    public String getTitle() {
        return this.title;
    }

    // Getter for completed
    public boolean isCompleted() {
        return this.completed;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Getter with reformatted orderNumber as order
    // when the property is asked
    @JsonProperty("order")
    public Integer getOrderNumber() {
        return orderNumber;
    }

    // Returns the URL of the todos
    public String getUrl() {
        return linkTo(TodoController.class).slash(this.getId()).withSelfRel().getHref();
    }

    // Returns a collection of all tags
    public Collection<Tag> getTags() {
        return tags;
    }

    // Merge the data from the updatedTodo args
    Todo merge(Todo updatedTodo) {
        title = Optional.ofNullable(updatedTodo.title).orElse(title);
        completed = updatedTodo.completed;
        orderNumber = Optional.ofNullable(updatedTodo.orderNumber).orElse(orderNumber);
        return this;
    }

}
