package lexkane.chatbot.repository;

import lexkane.chatbot.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findOneById(Long idAnswer);
}
