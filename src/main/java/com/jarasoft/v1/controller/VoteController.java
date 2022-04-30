package com.jarasoft.v1.controller;

import com.jarasoft.domain.Vote;
import com.jarasoft.dto.error.ErrorDetails;
import com.jarasoft.exception.ResourceNotFoundException;
import com.jarasoft.repository.VoteRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController("voteControllerV1")
@RequestMapping("/v1")
public class VoteController {
    private final VoteRepository voteRepository;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @ApiOperation(value = "Retrieves all available votes for a given poll", response = Vote.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error", response = ErrorDetails.class)})
    @GetMapping("/polls/{pollId}/votes")
    public ResponseEntity<Iterable<Vote>> getAllVotes(@PathVariable Long pollId) {
        return new ResponseEntity<>(voteRepository.findByPoll(pollId), HttpStatus.OK);
    }

    @ApiOperation(value = "Creates a new vote", notes = "The newly created vote Id will be sent in the location response header")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Vote Created Successfully"),
            @ApiResponse(code = 500, message = "Error creating vote", response = ErrorDetails.class)})
    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@RequestBody Vote vote, @PathVariable String pollId) {
        vote = voteRepository.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    protected Vote verifyVote(Long voteId) throws ResourceNotFoundException {
        Optional<Vote> vote = voteRepository.findById(voteId);
        if (!vote.isPresent()) {
            throw new ResourceNotFoundException("vote with id: " + voteId + " not found");
        }
        return vote.get();
    }

    @ApiOperation(value = "Retrieves a Vote associated with the voteId", response = Vote.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Vote not found", response = ErrorDetails.class),
            @ApiResponse(code = 500, message = "Error searching vote", response = ErrorDetails.class)})
    @GetMapping("/polls/{pollId}/votes/{voteId}")
    public ResponseEntity<?> getVote(@PathVariable Long voteId, @PathVariable String pollId) {
        return new ResponseEntity<>(verifyVote(voteId), HttpStatus.OK);
    }
}
