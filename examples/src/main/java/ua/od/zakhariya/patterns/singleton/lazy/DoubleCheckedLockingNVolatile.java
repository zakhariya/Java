package ua.od.zakhariya.patterns.singleton.lazy;

public class DoubleCheckedLockingNVolatile {

    private static volatile DoubleCheckedLockingNVolatile instance;

    public static DoubleCheckedLockingNVolatile getInstance() {

        DoubleCheckedLockingNVolatile localInstance = instance;

        if (localInstance == null) {
            synchronized (DoubleCheckedLockingNVolatile.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DoubleCheckedLockingNVolatile();
                }
            }
        }
        return localInstance;
    }

/*
    + Ленивая инициализация
    + Высокая производительность
    - Поддерживается только с JDK 1.5 [5]
*/
}
