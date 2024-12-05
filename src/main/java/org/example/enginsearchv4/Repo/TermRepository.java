package org.example.enginsearchv4.Repo;

import org.example.enginsearchv4.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findByTerm(String term);
    boolean existsByTerm(String term);

}
