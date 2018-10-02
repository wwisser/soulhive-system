package de.soulhive.system.util.reflect;

import com.google.common.reflect.ClassPath;
import de.soulhive.system.command.CommandService;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class ReflectUtils {

    @SneakyThrows
    public <T> Set<T> getPacketObjects(String pckg, Class<T> superClass) {
        ClassLoader classLoader = CommandService.class.getClassLoader();
        Set<T> objects = new HashSet<>();

        for (ClassPath.ClassInfo classInfo : ClassPath.from(classLoader).getTopLevelClassesRecursive(pckg)) {
            Class clazz = Class.forName(classInfo.getName(), true, classLoader);

            if (Arrays.asList(clazz.getInterfaces()).contains(superClass)
                || clazz.getSuperclass().equals(superClass)) {
                objects.add(superClass.cast(clazz.newInstance()));
            }
        }

        return Collections.unmodifiableSet(objects);
    }

}
