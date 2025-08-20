package ua.od.zakhariya.patterns.singleton.non_lazy;

public enum EnumSingleton {
    INSTANCE;

   /*
    + Остроумно
    + Сериализация из коробки
    + Потокобезопасность из коробки
    + Возможность использования EnumSet, EnumMap и т.д.
    + Поддержка switch
    - Не ленивая инициализация
    */
}
