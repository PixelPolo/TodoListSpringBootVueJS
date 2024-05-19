package ch.unifr.softeng.todobackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

// @RestController handles web requests to ressources
@RestController
@RequestMapping(value = "/tags") // Specify the resource URL
public class TagController {

    // The repository to make CRUD operations on a DB
    private final TagRepository repository;

    // Constructor
    public TagController(TagRepository repository) {
        this.repository = repository;
    }

    // POST /tags/
    // Creates an Entity inside the DB and
    // return it as http response
    @RequestMapping(method = POST)
    public @ResponseBody
    Tag create(@RequestBody Tag newTag) {
        return repository.save(newTag);
    }

    // GET /tags/
    // Returns a collection as http response
    @RequestMapping(method = GET)
    public @ResponseBody
    Collection<Tag> getAll() {
        return repository.findAll();
    }

    // DELETE /tags/
    // Delete all and returns HTTP status code 204 (NO_CONTENT)
    // to indicate a successful deletion
    @RequestMapping(method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        repository.deleteAll();
    }

    // GET /tags/{id}
    // Find by id, and if there is no math, throw an exception
    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody
    Tag getOne(@PathVariable("id") Long id) throws ResponseStatusException {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!"));
    }

    // PATCH /tags/{id}
    // Find the value, merge with changes, save it, or throw an exception
    @RequestMapping(method = PATCH, value = "/{id}")
    public @ResponseBody
    Tag update(@PathVariable("id") Long id,
                @RequestBody Tag updatedTag) throws ResponseStatusException {
        return repository.findById(id)
                // map apply a fonction to the value
                .map(tag -> tag.merge(updatedTag)) // Update the value
                .map(tag -> repository.save(tag)) // Save the value
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!"));
    }

    // DELETE /tags/{id}
    @RequestMapping(value = "/{id}", method = DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

    // GET /tags/{id}/todos
    // Returns a Collection of todos from the tag-id
    @RequestMapping(value = "/{tag-id}/todos/", method = GET)
    public @ResponseBody
    Collection<Todo> getAllTags(@PathVariable("tag-id") Long id) throws ResponseStatusException {
        Tag tag = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag does not exist!"));
        return tag.getTodos();
    }

}
