package ch.unifr.softeng.todobackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


// @RestController handles web requests to ressources
@RestController
@RequestMapping(value = "/todos") // Specify the resource URL
public class TodoController {

    // The repository to make CRUD operations on a DB
    private final TodoRepository repository;

    // Constructor
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    // POST /todos/
    // Creates an Entity inside the DB and
    // return it as http response
    @RequestMapping(method = POST)
    public @ResponseBody
    Todo create(@RequestBody Todo newTodo) {
        return repository.save(newTodo);
    }

    // GET /todos/
    // Returns a collection as http response
    @RequestMapping(method = GET)
    public @ResponseBody
    Collection<Todo> getAll() {
        return repository.findAll();
    }

    // DELETE /todos/
    // Delete all and returns HTTP status code 204 (NO_CONTENT)
    // to indicate a successful deletion
    @RequestMapping(method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        repository.deleteAll();
    }

    // GET /todos/{id}
    // Find by id, and if there is no math, throw an exception
    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody
    Todo getOne(@PathVariable("id") Long id) throws ResponseStatusException {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
    }

    // PATCH /todos/{id}
    // Find the value, merge with changes, save it, or throw an exception
    @RequestMapping(method = PATCH, value = "/{id}")
    public @ResponseBody
    Todo update(@PathVariable("id") Long id,
                @RequestBody Todo updatedTodo) throws ResponseStatusException {
        return repository.findById(id)
                // map apply a fonction to the value
                .map(todo -> todo.merge(updatedTodo)) // Update the value
                .map(todo -> repository.save(todo)) // Save the value
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
    }

    // DELETE /todos/{id}
    @RequestMapping(value = "/{id}", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

    // GET /todos/{id}/tags
    // Returns a Collection of tags from the todos-id
    @RequestMapping(value = "/{todo-id}/tags/", method = GET)
    public @ResponseBody
    Collection<Tag> getAllTags(@PathVariable("todo-id") Long id) throws ResponseStatusException {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        return todo.getTags();
    }

    // DELETE /todos/{id}/tags
    @RequestMapping(value = "/{todo-id}/tags/", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTags(@PathVariable("todo-id") Long id) throws ResponseStatusException {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        todo.getTags().clear();
        repository.save(todo);
    }

    // POST /todos/{id}/tags
    // Create a tag, add it to a todo_obj and save it in DB
    @RequestMapping(value = "/{todo-id}/tags/", method = POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    Tag addTag(@PathVariable("todo-id") Long id,
               @RequestBody Tag tag) throws ResponseStatusException {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        todo.getTags().add(tag);
        repository.save(todo);
        return tag;
    }

    // DELETE /todos/{id}/tags/{tag-id}
    // Remove the tag from the todo_obj
    @RequestMapping(value = "/{todo-id}/tags/{tag-id}", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeTag(@PathVariable("todo-id") Long todoId, @PathVariable("tag-id") Long tagId) throws ResponseStatusException {
        Todo todo = repository.findById(todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo does not exist!"));
        if (!todo.getTags().removeIf(tag -> tag.getId().equals(tagId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!");
        }
        repository.save(todo);
    }

}
