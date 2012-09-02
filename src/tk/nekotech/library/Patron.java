package tk.nekotech.library;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A library patron.
 */
public class Patron {
    private final Player player;
    private final Location joinLoc;
    private int warnings;
    private String last;
    public boolean fast;
    private int messages;
    private final int update;

    public Patron(final Player player, final Shhhhh shush) {
        this.player = player;
        this.joinLoc = this.player.getLocation();
        this.fast = false;
        this.update = shush.getServer().getScheduler().scheduleSyncRepeatingTask(shush, new UpdateCount(this), 20, 20);
    }

    public int addMessage() {
        return this.messages++;
    }

    public int addWarning() {
        return this.warnings++;
    }

    protected void explode(final Shhhhh shush) {
        shush.getServer().getScheduler().cancelTask(this.update);
    }

    public String getLast() {
        return this.last;
    }

    public int getMessages() {
        return this.messages;
    }

    public String getName() {
        return this.player.getName();
    }

    public int getWarnings() {
        return this.warnings;
    }

    public boolean isAtSpawnPoint() {
        return this.joinLoc == this.player.getLocation();
    }

    protected void resetMessages() {
        this.messages = 0;
    }

    public void sendMessage(final String message) {
        this.player.sendMessage(message);
    }

    public void setLast(final String last) {
        this.last = last;
    }
}
