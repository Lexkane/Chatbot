package lexkane.chatbot.service;

import lexkane.chatbot.model.Chatbot;
import lexkane.chatbot.model.User;
import lexkane.chatbot.model.dto.ChatbotDto;
import lexkane.chatbot.repository.ChatbotRepository;
import lexkane.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Service
public class ChatbotService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatbotRepository chatbotRepository;


    public Chatbot CreateChatbot(@RequestBody ChatbotDto chatbotDto,
                                 @PathVariable(value = "id") Long id
    ){
        Chatbot bot =  new Chatbot( chatbotDto.getName(), chatbotDto.getHelloMessage(), chatbotDto.isActive());

        Optional<User> user = userRepository.findById(id);
        user.get().getChatbots().add(bot);
        chatbotRepository.save(bot);
        return bot;
    }

    public Chatbot UpdateChatbot(@RequestBody ChatbotDto chatbotDto,
                                 @PathVariable(value = "idChatbot") Long idChatbot
    ){
        Chatbot bot = chatbotRepository.findOneById(idChatbot);

        bot.setName(chatbotDto.getName());
        bot.setHelloMessage(chatbotDto.getHelloMessage());
        bot.setActive(chatbotDto.isActive());
        chatbotRepository.save(bot);

        return bot;
    }

    public boolean DeleteChatbot(@PathVariable(value = "idChatbot") Long idChatbot
    ){
        Chatbot bot = chatbotRepository.findOneById(idChatbot);
        chatbotRepository.delete(bot);
        chatbotRepository.save(bot);

        return true;
    }

    public Set<Chatbot> getListAllChatbots(@PathVariable(value = "id") Long idUser){

        Optional<User> user = userRepository.findById(idUser);
        Set<Chatbot> bots = user.get().getChatbots();

        return bots;
    }

    public Chatbot getOneChatbot(@PathVariable(value = "idChatbot") Long idChatbot
    ){
        Chatbot bot = chatbotRepository.findOneById(idChatbot);
        return bot;
    }
}
