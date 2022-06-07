import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.toggle.BareboneToggle;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class Bot extends AbilityBot {

    //Turn off ALL the default abilities supplied by the library.
    private static final BareboneToggle toggle = new BareboneToggle();

    private final ResponseHandler responseHandler;

    public Bot(DefaultBotOptions botOptions) {
        super(Constants.TOKEN, Constants.USERNAME, toggle, botOptions);
        responseHandler = new ResponseHandler(sender);
    }

    @Override
    public long creatorId() {
        return Constants.CREATOR_ID;
    }

    //Respond to start command
    public Ability startCommand() {
        return Ability
            .builder()
            .name("start")
            .info(Constants.DESCRIPTION)
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> responseHandler.replyToStartCommand(ctx.chatId()))
            .build();
    }

    //Respond to random command
    public Ability randomCommand() {
        return Ability
            .builder()
            .name("random")
            .info(Constants.RANDOM_CHAPTER)
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> responseHandler.replyToRandomButton(ctx.chatId()))
            .build();
    }

    //Respond to order command
    public Ability orderCommand() {
        return Ability
            .builder()
            .name("order")
            .info(Constants.ORDER)
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> responseHandler.replyToOrderButton(ctx.chatId()))
            .build();
    }

    //Respond to custom command
    public Ability customCommand() {
        return Ability
            .builder()
            .name("custom")
            .info(Constants.CUSTOM_MSG)
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {
                new SilentSender(sender).forceReply(Constants.CUSTOM_MSG, ctx.chatId());
                replyToChapter();
            })
            .build();
    }

    //Respond to button click
    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> responseHandler.replyToButtons(
            upd.getCallbackQuery().getMessage().getChatId(),
            upd.getCallbackQuery().getData(),
            upd.getCallbackQuery().getMessage().getMessageId());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }


    //Answer to inLineQuery reply
    public Reply replyToInlineQuery() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, update) ->
            responseHandler.answerToInline(update.getInlineQuery().getId(), update.getInlineQuery().getQuery());
        return Reply.of(action, Flag.INLINE_QUERY);
    }


    //Send input chapter to given reply
    public Reply replyToChapter() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, update) ->
            responseHandler.sendCustomChapter(getChatId(update), update.getMessage().getText());
        return Reply.of(action, Flag.REPLY);
    }

}



