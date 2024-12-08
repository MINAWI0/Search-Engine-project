package org.example.enginsearchv4.Repo;

import org.example.enginsearchv4.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findByTerm(String term);
    boolean existsByTerm(String term);

    @Query("SELECT t FROM Term t WHERE t.term IN :terms")
    List<Term> findByTermIn(Collection<String> terms);

}
