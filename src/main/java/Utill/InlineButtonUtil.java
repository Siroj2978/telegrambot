package Utill;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtil {
    public static InlineKeyboardButton button(String buttonText, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setCallbackData(callBackData);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... InlineKeyboardButton) {
        List<InlineKeyboardButton> row = new LinkedList<InlineKeyboardButton>();
        row.addAll(Arrays.asList(InlineKeyboardButton));
        return row;
    }

    public static List<List<InlineKeyboardButton>> collection(List<InlineKeyboardButton>... rows) {
        List<List<InlineKeyboardButton>> collection = new LinkedList<>();
        collection.addAll(Arrays.asList(rows));
        return collection;
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> collection) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(collection);
        return keyboardMarkup;
    }
}
