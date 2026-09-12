package com.sayan.Research.Assistant;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class ResearchService {
    private final ChatModel chatModel;
    ResearchService(ChatModel chatModel){
        this.chatModel = chatModel;
    }

    public  String processContent(ResearchRequest req) {
       String prompt = buildPrompt(req);
       return chatModel.call(prompt);
    }
    private String buildPrompt(ResearchRequest req){
        StringBuilder prompt = new StringBuilder();
        switch (req.getOperation()){
            case "summarize" :
                prompt.append("Provide a clear and concise summary of the following text in a few sentences. \n\n");
                break;
            case "suggest" :
                prompt.append("Based on the following content suggest related topics and further readings , format the response with clear heading and bullet points. \n\n");
                break;
            default:
                throw new  IllegalArgumentException("Unknown Operation : " + req.getOperation() +"\n\n");
        }
        prompt.append(req.getContent());
        return prompt.toString();

    }
}
