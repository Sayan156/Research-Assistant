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
                prompt.append("""
    Summarize the following text into 4-8 concise and informative bullet points.

    Focus on the most important:
    - Ideas
    - Facts
    - Findings
    - Arguments
    - Technical details
    - Conclusions

    Remove repetition and unnecessary details.
    Preserve important names, numbers, technical terms, and concepts.
    Do not add information that is not present in the text.

    Return ONLY the bullet points.
    Do not include an introduction, conclusion, or phrases such as
    "Here is a summary".
    Start every bullet point with "-".
    Do not use Markdown bold, headings, or other formatting.

    Text:

    """);
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
