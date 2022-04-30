package com.jarasoft.v1.controller;

import com.jarasoft.domain.Poll;
import com.jarasoft.dto.error.ErrorDetails;
import com.jarasoft.exception.ResourceNotFoundException;
import com.jarasoft.repository.PollRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController("pollControllerV1")
@RequestMapping("/v1")
@Api(value = "polls")
public class PollController {
    private PollRepository pollRepository;

    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @ApiOperation(value = "Retrieves all the polls", response = Poll.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error", response = ErrorDetails.class)})
    @GetMapping("/polls")
    public ResponseEntity<Iterable<Poll>> getAllPulls() {
        return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Creates a new Poll", notes = "The newly created poll Id will be sent in the location response header")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Poll Created Successfully"),
            @ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetails.class)})
    @PostMapping("/polls")
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (!poll.isPresent()) {
            throw new ResourceNotFoundException("Poll with id: " + pollId + " not found");
        }
        return poll.get();
    }

    @ApiOperation(value = "Retrieves a Poll associated with the pollId", response = Poll.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Poll not found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Error searching Poll", response = ErrorDetails.class)})
    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        return new ResponseEntity<>(verifyPoll(pollId), HttpStatus.OK);
    }

    @ApiOperation(value = "Updates an existing poll")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Poll not found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Error searching Poll", response = ErrorDetails.class)})
    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes an existing poll")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Poll not found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Error searching Poll", response = ErrorDetails.class)})
    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
