package ua.od.zakhariya.patterns.singleton.lazy;

public class OnDemandHolderIdiom {

    public static class SingletonHolder {
        public static final OnDemandHolderIdiom HOLDER_INSTANCE = new OnDemandHolderIdiom();
    }

    public static OnDemandHolderIdiom getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
