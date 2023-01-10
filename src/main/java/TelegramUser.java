import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.HashMap;

public class TelegramUser {
    private String chatId;
    private String step;
    private String msg;
    private String fullName;
    private String selectedLang;
    private Location location;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    private Contact contact;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private HashMap<String, Integer> amountOfProduct;

    public void setAmountOfProduct(HashMap<String, Integer> amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public HashMap<String, Integer> getAmountOfProduct() {
        return amountOfProduct;
    }


    public TelegramUser() {
    }

    public String getSelectedLang() {
        return selectedLang;
    }

    public void setSelectedLang(String selectedLang) {
        this.selectedLang = selectedLang;
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "chatId='" + chatId + '\'' +
                ", step='" + step + '\'' +
                ", msg='" + msg + '\'' +
                ", fullName='" + fullName + '\'' +
                ", selectedLang='" + selectedLang + '\'' +
                ", location=" + location +
                ", contact=" + contact +
                ", amountOfProduct=" + amountOfProduct +
                '}';
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
