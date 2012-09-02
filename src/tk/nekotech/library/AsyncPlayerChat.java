package tk.nekotech.library;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
    private final Shhhhh shush;

    public AsyncPlayerChat(final Shhhhh shush) {
        this.shush = shush;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void moreAsyncFun(final AsyncPlayerChatEvent event) {
        if (!this.shush.tracker.hasPermission(event.getPlayer())) {
            final Patron patron = this.shush.getPatron(event.getPlayer().getName());
            if (patron.getWarnings() >= 5) {
                event.getPlayer().kickPlayer(ChatColor.RED + "[LibraryEnforce] " + ChatColor.WHITE + "You were caught spamming after 5 warnings! Goodbye...");
                this.shush.getServer().dispatchCommand(this.shush.getServer().getConsoleSender(), "ban " + patron.getName() + " [LibraryEnforce] Spamming after warnings!");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        if (!this.shush.tracker.hasPermission(event.getPlayer())) {
            final Patron patron = this.shush.getPatron(event.getPlayer().getName());
            final String message = event.getMessage();
            patron.addMessage();
            if (message.equals(patron.getLast())) {
                event.setCancelled(true);
                patron.addWarning();
                patron.sendMessage(ChatColor.RED + "Don't repeat yourself, this isn't allowed!");
            } else if (patron.isAtSpawnPoint()) {
                event.setCancelled(true);
                patron.addWarning();
                patron.sendMessage(ChatColor.RED + "Move away from spawn before talking!");
            }
            patron.setLast(message);
        }
    }
}
