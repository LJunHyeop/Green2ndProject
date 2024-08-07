package com.green.fefu.online.repository;

import com.green.fefu.entity.HaesolOnlineMultiple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaesolOnlineMultipleRepository extends JpaRepository<HaesolOnlineMultiple, Long> {
    List<String> findSentencebyQueIdOrderbynum(Long queId);
}
