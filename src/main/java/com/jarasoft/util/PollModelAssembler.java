package com.jarasoft.util;

import com.jarasoft.domain.Poll;
import com.jarasoft.v2.controller.ComputeResultController;
import com.jarasoft.v2.controller.PollController;
import com.jarasoft.v2.controller.VoteController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PollModelAssembler implements RepresentationModelAssembler<Poll, EntityModel<Poll>> {
    @Override
    public EntityModel<Poll> toModel(Poll poll) {
        return EntityModel.of(poll,
                linkTo(methodOn(PollController.class).getPoll(poll.getPollId())).withSelfRel(),
                linkTo(methodOn(PollController.class).getAllPolls(null)).withRel("polls"),
                linkTo(methodOn(VoteController.class).getAllVotes(poll.getPollId())).withRel("votes"),
                linkTo(methodOn(ComputeResultController.class).computeResult(poll.getPollId())).withRel("compute-result"));
    }
}
