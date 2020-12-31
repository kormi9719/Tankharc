package hu.elte.kormi.skins;

import com.badlogic.gdx.graphics.Texture;

public class CharacterHolder {

    public static Texture getCharacter(char c){

        if(Character.isDigit(c)){
            String path = "characters/" + c + ".png";
            return new Texture(path);
        } else {
            if(Character.isUpperCase(c)){
                String path = "characters/" + c + ".png";
                return new Texture(path);
            } else {
                String path = "characters/s" + c + ".png";
                return new Texture(path);
            }
        }
    }
}
