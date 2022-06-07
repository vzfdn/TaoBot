import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    private KeyboardFactory() {
        throw new IllegalStateException("Utility Class");
    }

    public static InlineKeyboardMarkup setChapterButtons() {

        //Inline keyboard that appears right next to the message it belongs to.
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLineTwo = new ArrayList<>();

        //Create Random Button.
        InlineKeyboardButton randomButton = new InlineKeyboardButton();
        randomButton.setText(Constants.RANDOM_CHAPTER);
        randomButton.setCallbackData(Constants.RANDOM_CHAPTER);
        rowInLine.add(randomButton);

        //Create Custom Chapter Button.
        InlineKeyboardButton chapterButton = new InlineKeyboardButton();
        chapterButton.setText(Constants.CUSTOM_CHAPTER);
        chapterButton.setCallbackData(Constants.CUSTOM_CHAPTER);
        rowInLine.add(chapterButton);

        //Create Chapters in order Button.
        InlineKeyboardButton orderButton = new InlineKeyboardButton();
        orderButton.setText(Constants.ORDER);
        orderButton.setCallbackData(Constants.ORDER);
        rowInLineTwo.add(orderButton);

        //Add inline buttons to keyboard buttons.
        rowsInLine.add(rowInLine);
        rowsInLine.add(rowInLineTwo);

        //Add  buttons to keyboard markup.
        inlineKeyboard.setKeyboard(rowsInLine);
        return inlineKeyboard;
    }

    public static InlineKeyboardMarkup setMoveButtons() {

        //Inline keyboard that appears right next to the message it belongs to.
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        String right = "\u27A1";
        String left = "\u2b05";

        //Create Next Button.
        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText(right);
        nextButton.setCallbackData(Constants.NEXT);
        rowInline.add(nextButton);

        //Create Previous Button.
        InlineKeyboardButton previousButton = new InlineKeyboardButton();
        previousButton.setText(left);
        previousButton.setCallbackData(Constants.PREVIOUS);
        rowInline.add(previousButton);

        //Add inline buttons to keyboard buttons.
        rowsInline.add(rowInline);

        //Add  buttons to keyboard markup.
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }


}
