import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        DefaultBotOptions botOptions = new DefaultBotOptions();

        //set SOCKS5 proxy for TOR
        botOptions.setProxyHost("127.0.0.1");
        botOptions.setProxyPort(9050);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        botsApi.registerBot(new Bot(botOptions));
    }
}

