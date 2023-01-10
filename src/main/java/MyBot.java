import Utill.CreateMaps;
import Utill.InlineButtonUtil;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class MyBot extends TelegramLongPollingBot {
    List<TelegramUser> users = new ArrayList<>();


    public String getBotUsername() {
        return "optom_pro_bot";
    }

    public String getBotToken() {

        return "5659916859:AAGiCw_TANEVzX-PaJ0RJ4F2qd2_wnY55mw";
    }

    String title = "\n__________________________<b><i>Big One</i></b>\n";

    public void onUpdateReceived(Update update) {
        System.out.println("--------------------------------------------------------------------------");
        //System.out.println(update + "\n" + update.getMessage().getChatId());
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();

            System.out.println("message : " + message.getText() + "\n" +
                    "messageId : " + message.getMessageId());
            TelegramUser user = saveUser(chatId);
            if (message.hasText()) {
                System.out.println("update has text!");
                String textMessage = update.getMessage().getText();

                switch (textMessage) {

                    case "/list":
                        deleteMessage(chatId, update);
                        sendMessageWithText(chatId, "список пользователей:\n");
                        for (int i = 0; i < users.size(); i++) {
                            sendMessageWithText(user.getChatId(), user.getMsg() + "\n" + user.getClass() + "\n" + user.getFullName());
                        }
                        break;
                    case "/start":
                        deleteMessage(chatId, update);
                        showCatalog(chatId, title, user);

                        SendMessage sendMessage = new SendMessage();
                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> rows = new ArrayList<>();

                        KeyboardRow row = new KeyboardRow();
                        row.add("посмотреть заказ");
                        row.add("отправить заказ");

                        rows.add(row);

                        row = new KeyboardRow();

                        KeyboardButton contactButton = new KeyboardButton();
                        contactButton.setText("отправить контакт");
                        contactButton.setRequestContact(true);

                        KeyboardButton locationButton = new KeyboardButton();
                        locationButton.setText("отправить местоположение");
                        locationButton.setRequestLocation(true);

                        row.add(contactButton);
                        row.add(locationButton);

                        rows.add(row);


                        replyKeyboardMarkup.setKeyboard(rows);
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setInputFieldPlaceholder("начинайте отсюда");
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        sendMessage.setText("Здравствуйте наберите товар \uD83D\uDC46");
                        sendMessage.setChatId(chatId);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "отправить заказ":

                        CreateMaps createMaps = new CreateMaps();
                        Double longitude = user.getLocation().getLongitude();
                        createMaps.setLongitude(longitude);
                        Double latitude = user.getLocation().getLatitude();
                        createMaps.setLatitude(latitude);
                        createMaps.setClientName(user.getContact().getFirstName());
                        createMaps.setContact(user.getContact());
                        try {
                            java.io.File mapFile = createMaps.getMapFile(latitude, longitude);
                            SendDocument sendDocument = new SendDocument(chatId, new InputFile(mapFile));
                            execute(sendDocument);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "посмотреть заказ":
                        deleteMessage(chatId, update);
                        JsonService jsonService = new JsonService();
                        List<Photo> income = jsonService.getPhones();

                        HashMap<String, Integer> order = user.getAmountOfProduct();
                        String myOrder = message.getFrom().getFirstName() + "\n" +
                                message.getFrom().getLastName();
                        int summOfOrder = 0;
                        for (Photo element : Collections.unmodifiableList(income)) {
                            if (order.get(element.getName()) > 0) {
                                myOrder += ("<b>" + element.getName() + "</b> : " + order.get(element.getName()) +
                                        " x " + element.getPrice() + " = " + order.get(element.getName()) * element.getPrice() + "\n");
                                summOfOrder += order.get(element.getName()) * element.getPrice();
                            }
                        }
                        myOrder += "\n\n" + "<i>Общая сумма заказа</i>: <b>" + summOfOrder + "</b>";
                        sendMessageWithText(chatId, myOrder);
                        sendMessageWithText("-363227260", myOrder);
                        break;
                    default:
                        deleteMessage(chatId, update);
                        sendMessageWithText(chatId, "\uD83E\uDEE3");
                        deleteMessage(chatId, update);
                        break;
                }
                if (user.getStep().equals(BotConstant.SELECTING_LANG)) {
                    user.setMsg(textMessage);
                    InlineKeyboardButton buttonCatalog = InlineButtonUtil.button(
                            user.getSelectedLang().equals(BotQuery.UZ_SELECT) ? "Catalog" : "Каталог",
                            BotQuery.CATALOG);
                    InlineKeyboardButton buttonRegizration = InlineButtonUtil.button(
                            user.getSelectedLang().equals(BotQuery.UZ_SELECT) ? "Registration" : "Регистрация",
                            BotQuery.REGISTR);

                    List<InlineKeyboardButton> row = InlineButtonUtil.row(buttonCatalog, buttonRegizration);

                    List<List<InlineKeyboardButton>> rowCollection = InlineButtonUtil.collection(row);

                    InlineKeyboardMarkup keyboardMarkup = InlineButtonUtil.keyboard(rowCollection);

                    sendMessageWithKeyboard(chatId,
                            user.getSelectedLang().equals(BotQuery.UZ_SELECT)
                                    ? "Boshqaruv paneli" : "Панель управления", keyboardMarkup);
                } else if (user.getStep().equals((BotConstant.SELECT_lANG))) {
                    user.setFullName(textMessage);

                    InlineKeyboardButton buttonUz = InlineButtonUtil.button(
                            "\uD83C\uDDFA\uD83C\uDDFF Uz", BotQuery.UZ_SELECT);
                    InlineKeyboardButton buttonRu = InlineButtonUtil.button(
                            "\uD83C\uDDF7\uD83C\uDDFA Ru", BotQuery.RU_SELECT);

                    List<InlineKeyboardButton> row = InlineButtonUtil.row(buttonUz, buttonRu);

                    List<List<InlineKeyboardButton>> rowCollection = InlineButtonUtil.collection(row);

                    InlineKeyboardMarkup keyboard = InlineButtonUtil.keyboard(rowCollection);

                    sendMessageWithKeyboard(chatId,
                            "Илтимос, дастур учун тил танланг" + "\nПожалуйста выберите язык общения",
                            keyboard);
                }
            } else if (message.hasLocation()) {//location
                System.out.println("update has location!");
                Location location = update.getMessage().getLocation();
                user.setLocation(location);
                System.out.println(user);
            } else if (message.hasPassportData()) {//passport data
                System.out.println("update has passport data!");
                System.out.println(update.getMessage().hasPassportData());
                PassportData passportData = update.getMessage().getPassportData();
            } else if (message.hasDocument()) {//file
                System.out.println("update has document! ");
                System.out.println(update.getMessage().getDocument().getFileName());
                sendMessageWithText(chatId, update.getMessage().getDocument().getFileName());
                Document document = message.getDocument();
                try {
                    saveFileToFolder(document.getFileId(), document.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (message.hasPhoto()) {//photo
                System.out.println("update has photo");
                List<PhotoSize> photo = message.getPhoto();
                String fileId = photo.get(1).getFileId();
                sendPhoto(chatId, fileId);
            } else if (message.hasVoice()) {//voice
                System.out.println("update has voice!");
                Message voice = update.getMessage();
                try {
                    saveFileToFolder(voice.getVoice().getFileId(), "file name for Voice");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (message.hasAudio()) {//Audio
                System.out.println("update has audio!");
                Audio audio = message.getAudio();
                try {
                    saveFileToFolder(audio.getFileId(),
                            audio.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (message.hasContact()) {
                user.setContact(message.getContact());
                System.out.println("update has contact!");
                System.out.println(user);
            }
        } else if (update.hasCallbackQuery()) {
            System.out.println("update has call back query!" + "\n" + update.getCallbackQuery().getData());
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            TelegramUser user = saveUser(chatId);
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            String data = update.getCallbackQuery().getData();

            if (data.startsWith("minus")) {

                JsonService jsonService = new JsonService();
                List<Photo> income = jsonService.getPhones();

                HashMap<String, Integer> order = user.getAmountOfProduct();

                String productName = update.getCallbackQuery().getMessage().getText();


                for (Photo elementJsonFile : Collections.unmodifiableList(income)) {
                    String name = elementJsonFile.getName();
                    String Description = elementJsonFile.getDescription();
                    int price = elementJsonFile.getPrice();
                    int box = elementJsonFile.getBox();
                    String unit = elementJsonFile.getUnit();

                    int oldAmount = order.get(name);
                    int newAmount;
                    if (productName.startsWith(name)) {
                        if (oldAmount >= 1) {
                            newAmount = order.get(name) - 1;
                        } else {
                            newAmount = order.get(name);
                        }
                        int sum = newAmount * price;
                        order.put(name, newAmount);
                        user.setAmountOfProduct(order);


                        InlineKeyboardButton minus = InlineButtonUtil.button("-", "minus" + name);
                        InlineKeyboardButton menu = InlineButtonUtil.button(String.valueOf(sum), "price" + name);
                        InlineKeyboardButton plus = InlineButtonUtil.button("+", "plus" + name);
                        InlineKeyboardButton bug = InlineButtonUtil.button(newAmount == 0 ? "корзина пуста" : "Ваш заказ : " + newAmount + " " + unit, "plus" + name);


                        List<InlineKeyboardButton> row = InlineButtonUtil.row(minus, menu, plus);
                        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(bug);

                        List<List<InlineKeyboardButton>> rowCollection = InlineButtonUtil.collection(row, row1);
                        InlineKeyboardMarkup keyboard = InlineButtonUtil.keyboard(rowCollection);
                        EditMessageText et = new EditMessageText();
                        et.setChatId(chatId);
                        et.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                        et.setParseMode(ParseMode.HTML);
                        et.setReplyMarkup(keyboard);
                        et.setText("<b>" + name + "</b>" + "\n<i>" + price + "</i>\n" +
                                "в упаковке " + box + " " + unit + "\n<i>" + Description + "</i>" + title);

                        try {
                            execute(et);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (data.startsWith("plus")) {

                JsonService jsonService = new JsonService();
                List<Photo> income = jsonService.getPhones();

                HashMap<String, Integer> order = user.getAmountOfProduct();

                String productName = update.getCallbackQuery().getMessage().getText();

                for (Photo elementJsonFile : Collections.unmodifiableList(income)) {
                    String name = elementJsonFile.getName();
                    String Description = elementJsonFile.getDescription();
                    int price = elementJsonFile.getPrice();
                    int box = elementJsonFile.getBox();
                    String unit = elementJsonFile.getUnit();

                    if (productName.startsWith(name)) {

                        int oldAmount = order.get(name);
                        //if (oldAmount == 0) oldAmount = 1;
                        int sum = ((oldAmount + 1) * price);

                        order.put(name, oldAmount + 1);
                        user.setAmountOfProduct(order);

                        InlineKeyboardButton minus = InlineButtonUtil.button("-", "minus" + name);
                        InlineKeyboardButton menu = InlineButtonUtil.button(String.valueOf(sum), "price" + name);
                        InlineKeyboardButton plus = InlineButtonUtil.button("+", "plus" + name);
                        InlineKeyboardButton bag = InlineButtonUtil.button("Ваш заказ : " + (oldAmount + 1) + " " + unit, "plus" + name);
                        List<InlineKeyboardButton> row = InlineButtonUtil.row(minus, menu, plus);
                        List<InlineKeyboardButton> row1 = InlineButtonUtil.row(bag);

                        List<List<InlineKeyboardButton>> rowCollection = InlineButtonUtil.collection(row, row1);
                        InlineKeyboardMarkup keyboard = InlineButtonUtil.keyboard(rowCollection);
                        EditMessageText et = new EditMessageText();
                        et.setChatId(chatId);
                        et.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                        et.setReplyMarkup(keyboard);
                        et.setParseMode(ParseMode.HTML);
                        et.setText("<b>" + name + "</b>" + "\n<i>" + price + "</i>\n" +
                                "в упаковке " + box + " " + unit + "\n<i>" + Description + "</i>" + title);
                        try {
                            execute(et);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (user.getStep().equals(BotConstant.SELECT_lANG)) {
                if (data.equals(BotQuery.UZ_SELECT)) {
                    user.setSelectedLang(BotQuery.UZ_SELECT);
                    user.setStep(BotConstant.SELECTING_LANG);
                } else if (data.equals(BotQuery.RU_SELECT)) {
                    user.setSelectedLang(BotQuery.RU_SELECT);
                    user.setStep(BotConstant.SELECTING_LANG);
                }
            }
            if (data.equals(BotQuery.UZ_SELECT) || data.equals((BotQuery.RU_SELECT))) {

                InlineKeyboardButton buttonLeft = InlineButtonUtil.button(
                        user.getSelectedLang().equals(BotQuery.UZ_SELECT) ? "Catalog" : "Каталог", BotQuery.CATALOG);

                InlineKeyboardButton buttonRight = InlineButtonUtil.button(
                        user.getSelectedLang().equals(BotQuery.UZ_SELECT) ? "Registrar" : "Регистрация", BotQuery.REGISTR);


                List<InlineKeyboardButton> row = InlineButtonUtil.row(buttonLeft, buttonRight);

                List<List<InlineKeyboardButton>> rowCollection = InlineButtonUtil.collection(row);
                InlineKeyboardMarkup keyboard = InlineButtonUtil.keyboard(rowCollection);

                sendMessageWithKeyboard(chatId, user.getSelectedLang().equals(BotQuery.UZ_SELECT)
                        ? "Boshqaruv paneli" : "Панель управления", keyboard);
            }
            if (data.equals(BotQuery.CATALOG)) {
                showCatalog(chatId, title, user);
            }

        }
    }

    private TelegramUser saveUser(String chatId) {
        for (TelegramUser user : users) {
            if (user.getChatId().equals(chatId)) {
                return user;
            }
        }
        TelegramUser user = new TelegramUser();
        user.setChatId(chatId);
        users.add(user);
        System.out.println("we have a new client : " + user);
        return user;
    }

    private void sendMessageWithText(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageWithKeyboard(String chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFileToFolder(String fileId, String fileName) throws Exception {
        GetFile getFile = new GetFile(fileId);
        File tgFile = execute(getFile);
        String fileUrl = tgFile.getFileUrl(getBotToken());
        System.out.println(fileId);
        System.out.println(fileUrl);
        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
        FileUtils.copyInputStreamToFile(inputStream, new java.io.File(fileName));
    }

    private void sendPhoto(String chatId, String fileId) {
        SendPhoto sp = new SendPhoto(chatId, new InputFile(fileId));
        try {
            execute(sp);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendAnimation(String chatId, String fileId) {
        SendAnimation sendAnimation = new SendAnimation(chatId, new InputFile(fileId));
        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendVideo(String chatId, String fileId) {
        SendVideo sendVideo = new SendVideo(chatId, new InputFile(fileId));
        sendVideo.setProtectContent(true);
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public File getFilePath(Document document) throws TelegramApiException {
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());
        File file = execute(getFile);
        System.out.println(file);
        return file;
    }

    public void showCatalog(String chatId, String title, TelegramUser user) {

        HashMap<String, Integer> order = new HashMap<>();

        JsonService jsonService = new JsonService();
        List<Photo> income = jsonService.getPhones();

        for (Photo elementJsonFile : Collections.unmodifiableList(income)
        ) {
            //System.out.println("Id :" + elementJsonFile.getId());
            String name = elementJsonFile.getName();
            String Description = elementJsonFile.getDescription();
            String animation = elementJsonFile.getPhoto();
            int price = elementJsonFile.getPrice();
            int box = elementJsonFile.getBox();
            String unit = elementJsonFile.getUnit();

            sendVideo(chatId, animation);

            InlineKeyboardButton minus = InlineButtonUtil.button("-", "minus" + name);
            InlineKeyboardButton menu = InlineButtonUtil.button(String.valueOf(0.0), "price" + name);
            InlineKeyboardButton plus = InlineButtonUtil.button("+", "plus" + name);
            InlineKeyboardButton bag = InlineButtonUtil.button("корзина пуста", "bag" + name);

            List<InlineKeyboardButton> row = InlineButtonUtil.row(minus, menu, plus);
            List<InlineKeyboardButton> row1 = InlineButtonUtil.row(bag);

            List<List<InlineKeyboardButton>> rowCollection = InlineButtonUtil.collection(row, row1);
            InlineKeyboardMarkup keyboard = InlineButtonUtil.keyboard(rowCollection);


            sendMessageWithKeyboard(chatId, "<b>" + name + "</b>" + "\n<i>" + price + "</i>\n" +
                    "в упаковке " + box + " " + unit + "\n<i>" + Description + "</i>" + title, keyboard);
            user.setStep(BotQuery.ORDER);

            order.put(name, 0);
            user.setAmountOfProduct(order);

        }
    }

    public void deleteMessage(String chatId, Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(update.getMessage().getMessageId());
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}



