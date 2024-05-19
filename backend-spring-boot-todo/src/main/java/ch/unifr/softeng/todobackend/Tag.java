package ch.unifr.softeng.todobackend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// This entity is used by the Java Persistence API.
// We use objects from this Entity to interact with the DB,
// instead making SQL queries...

@Entity
public class Tag {

    @Id // PrimaryKey indicator
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generated
    private Long id;

    // Attribute title of the entity
    private String title;

    // Set of Tag for the Many-to-Many relationships between tags and todos.
    // @JsonIgnoreProperties("todos") avoids serialization infinite loop
    // Serialize a todos, then its tag, but not the tags todos back again...
    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    // @JsonBackReference    // Same as @JsonIgnoreProperties("tags");
    private final Set<Todo> todos = new HashSet<>();

    // Constructor for the Entity stored in DB as a JSON format
    @JsonCreator
    public Tag(@JsonProperty("title") String title) {
        this.title = title;
    }

    // Default constructor required by JPA for entity instantiation
    protected Tag() {
    }

    // Getter for the title
    public String getTitle() {
        return this.title;
    }

    // Getter for the id
    public Long getId() {
        return id;
    }

    // Returns the URL of the tag
    public String getUrl() {
        return linkTo(TagController.class).slash(this.getId()).withSelfRel().getHref();
    }

    // Merge the data from the updatedTag args
    Tag merge(Tag updatedTag) {
        title = Optional.ofNullable(updatedTag.title).orElse(title);
        return this;
    }

    // Returns a collection of all todos
    public Collection<Todo> getTodos() {
        return todos;
    }

}
