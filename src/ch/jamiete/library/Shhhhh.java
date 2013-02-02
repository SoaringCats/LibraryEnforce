package ch.jamiete.library;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import to.joe.j2mc.core.permissions.ThreadSafePermissionTracker;

public class Shhhhh extends JavaPlugin implements Listener {
    private Set<Patron> patrons;
    public ThreadSafePermissionTracker tracker;

    public Patron getPatron(final String name) {
        for (final Patron patron : this.patrons) {
            if (patron.getName().equals(name)) {
                return patron;
            }
        }
        return null;
    }

    @Override
    public void onDisable() {
        final StringBuilder sb = new StringBuilder();
        for (final Patron patron : this.patrons) {
            if (patron.getWarnings() > 0) {
                sb.append(patron.getName() + ": " + patron.getWarnings() + ",");
            }
        }
        if (sb.length() != 0) {
            sb.substring(sb.length() - 1, sb.length());
            this.getLogger().info("Due to reload (or server disable) the following players lost their warning counts!");
            this.getLogger().info(sb.toString());
        }
    }

    @Override
    public void onEnable() {
        this.patrons = Collections.newSetFromMap(new ConcurrentHashMap<Patron, Boolean>());
        this.tracker = new ThreadSafePermissionTracker(this, "libraryenforce.librarian");
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(this), this);
        this.getServer().getPluginManager().registerEvents(this, this);
        for (final Player player : this.getServer().getOnlinePlayers()) {
            this.patrons.add(new Patron(player, this));
        }
    }

    @EventHandler
    public void welcomeToTheLibrary(final PlayerJoinEvent event) {
        this.patrons.add(new Patron(event.getPlayer(), this));
    }

    @EventHandler
    public void thanksForYourPatronage(final PlayerQuitEvent event) {
        final Patron patron = this.getPatron(event.getPlayer().getName());
        patron.explode(this);
        this.patrons.remove(patron);
    }
}
