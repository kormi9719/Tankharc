package hu.elte.kormi.skins;

import com.badlogic.gdx.graphics.Texture;
import hu.elte.kormi.helpers.GameInfo;

import java.util.ArrayList;

public class SkinHolder {

    private final ArrayList<ArrayList<Texture>> gamblingSkins = new ArrayList<>();
    private final ArrayList<ArrayList<Texture>> collectiblesGamblingSkins = new ArrayList<>();
    private final ArrayList<ArrayList<Texture>> collectiblesMapSkins = new ArrayList<>();

    public SkinHolder(){

        for(int i = 0; i < GameInfo.SEASON; i++){

            ArrayList<Texture> temp = new ArrayList<>();

            String path1 = "gambling/S" + (i + 1) + "G1.png";
            String path2 = "gambling/S" + (i + 1) + "G2.png";
            String path3 = "gambling/S" + (i + 1) + "G3.png";

            temp.add(new Texture(path1));
            temp.add(new Texture(path2));
            temp.add(new Texture(path3));

            gamblingSkins.add(temp);
        }

        for(int i = 0; i < GameInfo.SEASON; i++){

            ArrayList<Texture> temp = new ArrayList<>();

            String path1 = "collectibles/S" + (i + 1) + "G1.png";
            String path2 = "collectibles/S" + (i + 1) + "G2.png";
            String path3 = "collectibles/S" + (i + 1) + "G3.png";

            temp.add(new Texture(path1));
            temp.add(new Texture(path2));
            temp.add(new Texture(path3));

            collectiblesGamblingSkins.add(temp);
        }

        for(int i = 0; i < GameInfo.SEASON; i++){

            ArrayList<Texture> temp = new ArrayList<>();

            String path1 = "collectibles/S" + (i + 1) + "M1.png";
            String path2 = "collectibles/S" + (i + 1) + "M2.png";
            String path3 = "collectibles/S" + (i + 1) + "M3.png";

            temp.add(new Texture(path1));
            temp.add(new Texture(path2));
            temp.add(new Texture(path3));

            collectiblesMapSkins.add(temp);
        }
    }

    public ArrayList<ArrayList<Texture>> getGamblingSkins(){
        return this.gamblingSkins;
    }

    public ArrayList<ArrayList<Texture>> getCollectiblesGamblingSkins(){
        return this.collectiblesGamblingSkins;
    }

    public ArrayList<ArrayList<Texture>> getCollectiblesMapSkins(){
        return this.collectiblesMapSkins;
    }
}
