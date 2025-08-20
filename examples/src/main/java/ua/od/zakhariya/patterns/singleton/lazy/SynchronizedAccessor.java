package ua.od.zakhariya.patterns.singleton.lazy;

public class SynchronizedAccessor {
    private static SynchronizedAccessor instance;

    public static synchronized SynchronizedAccessor getInstance() {
        if (instance == null) {
            instance = new SynchronizedAccessor();
        }

        return instance;
    }

    /*
    + Ленивая инициализация
    - Низкая производительность (критическая секция) в наиболее типичном доступе
    */
}
