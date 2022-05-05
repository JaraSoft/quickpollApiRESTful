package com.jarasoft.v2.controller;

import com.jarasoft.domain.Option;
import com.jarasoft.domain.Vote;
import com.jarasoft.dto.OptionCount;
import com.jarasoft.dto.VoteResult;
import com.jarasoft.dto.error.ErrorDetails;
import com.jarasoft.repository.VoteRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController("computeResultControllerV2")
@RequestMapping("/v2")
public class ComputeResultController {
    private final VoteRepository voteRepository;

    public ComputeResultController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }
    @ApiOperation(value = "Return the vote count for the given poll", response = VoteResult.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error", response = ErrorDetails.class)})
    @GetMapping("/compute-results")
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        List<OptionCount> optionCounts = new ArrayList<>();
        List<Vote> allVotes = (List<Vote>) voteRepository.findByPoll(pollId);
        Map<Option, Long> votesByOption = allVotes.stream()
                .collect(Collectors.groupingBy(Vote::getOption, Collectors.counting()));
        votesByOption.forEach((option, count) -> {
            OptionCount optionCount = new OptionCount();
            optionCount.setOptionId(option.getId());
            optionCount.setCount(count);
            optionCounts.add(optionCount);
        });
        voteResult.setTotalVotes(allVotes.size());
        voteResult.setResults(optionCounts);
        return new ResponseEntity<>(voteResult, HttpStatus.OK);
    }
}
