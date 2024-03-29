package lexkane.chatbot.service;

import lexkane.chatbot.model.Answer;
import lexkane.chatbot.model.Chatbot;
import lexkane.chatbot.model.Question;
import lexkane.chatbot.model.dto.AnswerDto;
import lexkane.chatbot.repository.AnswerRepository;
import lexkane.chatbot.repository.ChatbotRepository;
import lexkane.chatbot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class AnswerService {
    @Autowired
    ChatbotRepository chatbotRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;


    @PostMapping
    public Answer createAnswer(@RequestBody AnswerDto answerDto,
                               @PathVariable(name = "idChatbot") Long idChatbot,
                               @PathVariable(name = "idQuestion") Long idQuestion
    ){
        Answer answer = new Answer(answerDto.getText());
        answerRepository.save(answer);

        Question question = questionRepository.findOneById(idQuestion);
        question.getAnswers().add(answer);


        Chatbot bot = chatbotRepository.findOneById(idChatbot);
        int countAnswer = bot.getAnswersCount();
        countAnswer++;
        bot.setAnswersCount(countAnswer);
        chatbotRepository.save(bot);

        return answer;
    }

    @PatchMapping("/{idAnswer}")
    public Answer updateAnswer(@RequestBody AnswerDto answerDto,
                               @PathVariable(name = "idAnswer") Long idAnswer
    ){
        Answer answer = answerRepository.findOneById(idAnswer);

        answer.setText(answerDto.getText());
        answerRepository.save(answer);
        return answer;
    }

    @DeleteMapping("/{idAnswer}")
    public boolean deleteAnswer(@PathVariable(name = "idAnswer") Long idAnswer,
                                @PathVariable(name = "idChatbot") Long idChatbot
    ){
        Answer answer = answerRepository.findOneById(idAnswer);

        answerRepository.delete(answer);
        answerRepository.save(answer);

        Chatbot bot = chatbotRepository.findOneById(idChatbot);
        int countAnswer = bot.getAnswersCount();
        countAnswer--;
        bot.setAnswersCount(countAnswer);
        chatbotRepository.save(bot);

        return true;
    }

}
