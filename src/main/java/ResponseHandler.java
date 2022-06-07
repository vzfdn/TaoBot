import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ResponseHandler {

    private final MessageSender sender;
    private final Random rnd = new Random();
    private int chapterNumber = 1;

    public ResponseHandler(MessageSender sender) {
        this.sender = sender;
    }

    public void replyToStartCommand(long chatId) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(Constants.START_REPLY);
            sender.execute(sendMessage);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(Constants.WAYS);
            message.setReplyMarkup(KeyboardFactory.setChapterButtons());
            sender.execute(message);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToButtons(long chatId, String buttonId, int messageId) {
        switch (buttonId) {
            case Constants.RANDOM_CHAPTER -> replyToRandomButton(chatId);
            case Constants.CUSTOM_CHAPTER -> replyToCustomButton(chatId);
            case Constants.ORDER -> replyToOrderButton(chatId);
            case Constants.NEXT -> nextChapter(chatId, messageId);
            case Constants.PREVIOUS -> previousChapter(chatId, messageId);
            default -> System.out.println("Invalid Input");
        }
    }

    public void replyToRandomButton(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(FileHandler.customChapter(this.rnd.nextInt(82 - 1) + 1));
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToCustomButton(long chatId) {
        SilentSender silentSender = new SilentSender(sender);
        silentSender.forceReply(Constants.CUSTOM_MSG, chatId);
    }

    public void replyToOrderButton(long chatId) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText(FileHandler.customChapter(chapterNumber));
            message.setReplyMarkup(KeyboardFactory.setMoveButtons());
            sender.execute(message);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCustomChapter(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        int num = Integer.parseInt(message);
        if (num > 81 || num < 1) {
            sendMessage.setText("The Chapter number must be between 1 to 81");
        } else {
            sendMessage.setText(FileHandler.customChapter(Integer.parseInt(message)));
        }
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void previousChapter(long chatId, int messageId) {
        if (chapterNumber > 1) {
            try {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setMessageId(messageId);
                editMessageText.setText(FileHandler.customChapter(--chapterNumber));
                editMessageText.setChatId(String.valueOf(chatId));
                editMessageText.setReplyMarkup(KeyboardFactory.setMoveButtons());
                sender.execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void nextChapter(long chatId, int messageId) {
        if (chapterNumber < 81) {
            try {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setMessageId(messageId);
                editMessageText.setText(FileHandler.customChapter(++chapterNumber));
                editMessageText.setChatId(String.valueOf(chatId));
                editMessageText.setReplyMarkup(KeyboardFactory.setMoveButtons());
                sender.execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    public void answerToInline(String inlineId, String query) {
        //Create a InputTextMessageContent to be sent as the result of an inline query.
        InputTextMessageContent inputMessage = new InputTextMessageContent();
        int chNumber = Integer.parseInt(query);
        if (chNumber > 1 && chNumber < 82) {
            inputMessage.setMessageText(FileHandler.customChapter(chNumber));
        } else {
            inputMessage.setMessageText("Invalid Input. Chapter must be between 1 to 81");
        }

        //A link to the inputMessage chapter.
        InlineQueryResultArticle article = new InlineQueryResultArticle();
        article.setTitle("Tao Te Ching");
        article.setDescription("Get the desired chapter");
        article.setId(inlineId);
        article.setInputMessageContent(inputMessage);

        List<InlineQueryResult> list = new ArrayList<>();
        list.add(article);

        AnswerInlineQuery answer = new AnswerInlineQuery();
        answer.setInlineQueryId(inlineId);
        answer.setResults(list);

        try {
            sender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

