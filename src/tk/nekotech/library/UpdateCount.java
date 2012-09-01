package tk.nekotech.library;

import org.bukkit.ChatColor;

public class UpdateCount implements Runnable {
    private final Patron patron;

    public UpdateCount(final Patron patron) {
        this.patron = patron;
    }

    @Override
    public void run() {
        if (this.patron.getMessages() >= 3) {
            this.patron.addWarning();
            this.patron.sendMessage(ChatColor.RED + "Stop spamming!");
            this.patron.fast = true;
        } else {
            this.patron.fast = false;
        }
        this.patron.resetMessages();
    }
}
