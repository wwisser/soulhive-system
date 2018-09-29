package de.skydust.system.util.nms;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Title {

    private Class<?> packetTitle;
    private Class<?> packetActions;
    private Class<?> nmsChatSerializer;
    private Class<?> chatBaseComponent;
    private String title = "";
    private ChatColor titleColor = ChatColor.WHITE;
    private String subtitle = "";
    private ChatColor subtitleColor = ChatColor.WHITE;
    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;
    private boolean ticks = false;
    private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<>();

    public Title(String title) {
        this.title = title;

        this.loadClasses();
    }

    public Title(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;

        this.loadClasses();
    }

    public Title(Title title) {
        this.title = title.title;
        this.subtitle = title.subtitle;
        this.titleColor = title.titleColor;
        this.subtitleColor = title.subtitleColor;
        this.fadeInTime = title.fadeInTime;
        this.fadeOutTime = title.fadeOutTime;
        this.stayTime = title.stayTime;
        this.ticks = title.ticks;

        this.loadClasses();
    }

    public Title(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;

        this.loadClasses();
    }

    private void loadClasses() {
        this.packetTitle = getNMSClass("PacketPlayOutTitle");
        this.packetActions = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
        this.chatBaseComponent = getNMSClass("IChatBaseComponent");
        this.nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setTitleColor(ChatColor color) {
        this.titleColor = color;
    }

    public void setSubtitleColor(ChatColor color) {
        this.subtitleColor = color;
    }

    public void setFadeInTime(int time) {
        this.fadeInTime = time;
    }

    public void setFadeOutTime(int time) {
        this.fadeOutTime = time;
    }

    public void setStayTime(int time) {
        this.stayTime = time;
    }

    public void setTimingsToTicks() {
        this.ticks = true;
    }

    public void setTimingsToSeconds() {
        this.ticks = false;
    }

    @SneakyThrows
    public void send(Player player) {
        if (this.packetTitle != null) {
            resetTitle(player);
            Object handle = getHandle(player);
            Object connection = getField(handle.getClass(),
                    "playerConnection").get(handle);
            Object[] actions = this.packetActions.getEnumConstants();
            Method sendPacket = getMethod(connection.getClass(),
                    "sendPacket");
            Object packet = this.packetTitle.getConstructor(new Class[]{this.packetActions,
                    this.chatBaseComponent, Integer.TYPE, Integer.TYPE,
                    Integer.TYPE}).newInstance(actions[2], null,
                    this.fadeInTime * (this.ticks ? 1 : 20),
                    this.stayTime * (this.ticks ? 1 : 20),
                    this.fadeOutTime * (this.ticks ? 1 : 20));
            if ((this.fadeInTime != -1) && (this.fadeOutTime != -1) && (this.stayTime != -1)) {
                sendPacket.invoke(connection, packet);
            }
            Object serialized = getMethod(this.nmsChatSerializer, "a", new Class[]{
                    String.class}).invoke(
                    null, "{text:\"" +
                            ChatColor.translateAlternateColorCodes('&',
                                    this.title) + "\",color:" +
                            this.titleColor.name().toLowerCase() + "}");
            packet = this.packetTitle.getConstructor(new Class[]{this.packetActions,
                    this.chatBaseComponent}).newInstance(actions[0], serialized);
            sendPacket.invoke(connection, packet);
            if (this.subtitle != "") {
                serialized =
                        getMethod(this.nmsChatSerializer, "a", new Class[]{String.class})
                                .invoke(null, "{text:\"" +

                                        ChatColor.translateAlternateColorCodes(
                                                '&', this.subtitle) +
                                        "\",color:" +
                                        this.subtitleColor.name()
                                                .toLowerCase() + "}");
                packet = this.packetTitle.getConstructor(new Class[]{this.packetActions,
                        this.chatBaseComponent}).newInstance(actions[1],
                        serialized);
                sendPacket.invoke(connection, packet);
            }
        }
    }

    @SneakyThrows
    public void clearTitle(Player player) {
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), "playerConnection")
                .get(handle);
        Object[] actions = this.packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), "sendPacket", new Class[0]);
        Object packet = this.packetTitle.getConstructor(new Class[]{this.packetActions,
                this.chatBaseComponent}).newInstance(actions[3], null);
        sendPacket.invoke(connection, packet);
    }

    @SneakyThrows
    private void resetTitle(Player player) {
        Object handle = getHandle(player);
        Object connection = getField(handle.getClass(), "playerConnection")
                .get(handle);
        Object[] actions = this.packetActions.getEnumConstants();
        Method sendPacket = getMethod(connection.getClass(), "sendPacket", new Class[0]);
        Object packet = this.packetTitle.getConstructor(new Class[]{this.packetActions,
                this.chatBaseComponent}).newInstance(actions[4], null);
        sendPacket.invoke(connection, packet);
    }

    private Class<?> getPrimitiveType(Class<?> clazz) {
        return CORRESPONDING_TYPES.getOrDefault(clazz, clazz);
    }

    private Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
        int a = classes != null ? classes.length : 0;
        Class[] types = new Class[a];
        for (int i = 0; i < a; i++) {
            types[i] = getPrimitiveType(classes[i]);
        }
        return types;
    }

    private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
        if (a.length != o.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if ((!a[i].equals(o[i])) && (!a[i].isAssignableFrom(o[i]))) {
                return false;
            }
        }
        return true;
    }

    @SneakyThrows
    private Object getHandle(Object obj) {
        return getMethod("getHandle", obj.getClass(), new Class[0]).invoke(obj);
    }

    private Method getMethod(String name, Class<?> clazz, Class<?>... paramTypes) {
        Class[] t = toPrimitiveTypeArray(paramTypes);
        for (Method m : clazz.getMethods()) {
            Class[] types = toPrimitiveTypeArray(m.getParameterTypes());
            if ((m.getName().equals(name)) && (equalsTypeArray(types, t))) {
                return m;
            }
        }
        return null;
    }

    private String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }

    @SneakyThrows
    private Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz;
        clazz = Class.forName(fullName);
        return clazz;
    }

    @SneakyThrows
    private Field getField(Class<?> clazz, String name) {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods()) {
            if ((m.getName().equals(name)) && (
                    (args.length == 0) ||
                            (classListEquals(args, m.getParameterTypes())))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    private boolean classListEquals(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }

}