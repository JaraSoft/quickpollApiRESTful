package com.jarasoft.repository;

import com.jarasoft.domain.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
}
