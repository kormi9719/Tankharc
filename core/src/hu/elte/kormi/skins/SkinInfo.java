package hu.elte.kormi.skins;

public class SkinInfo {

    private final int map;
    private final int season;
    private final String flag;

    public SkinInfo(int season, int map, String flag){
        this.season = season;
        this.map = map;
        this.flag = flag;
    }

    public int getMap(){
        return this.map;
    }

    public int getSeason() {
        return this.season;
    }

    public String getFlag(){
        return this.flag;
    }
}
