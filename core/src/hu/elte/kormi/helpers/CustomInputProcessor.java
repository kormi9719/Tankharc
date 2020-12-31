package hu.elte.kormi.helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

public class CustomInputProcessor implements InputProcessor {

    private final ArrayList<Boolean> keysPressed;

    public CustomInputProcessor(){

        this.keysPressed = new ArrayList<>();

        for(int i = 0; i < 7; i++){
            keysPressed.add(false);
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            keysPressed.set(0, true);
            keysPressed.set(1, false);
            return true;
        }

        if(keycode == Input.Keys.S){
            keysPressed.set(1, true);
            keysPressed.set(0, false);
            return true;
        }

        if(keycode == Input.Keys.A){
            keysPressed.set(2, true);
            keysPressed.set(3, false);
            return true;
        }

        if(keycode == Input.Keys.D){
            keysPressed.set(3, true);
            keysPressed.set(2, false);
            return true;
        }

        if(keycode == Input.Keys.LEFT){
            keysPressed.set(4, true);
            keysPressed.set(5, false);
            return true;
        }

        if(keycode == Input.Keys.RIGHT){
            keysPressed.set(5, true);
            keysPressed.set(4, false);
            return true;
        }

        if(keycode == Input.Keys.SPACE){
            keysPressed.set(6, true);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W){
            keysPressed.set(0, false);
            return true;
        }

        if(keycode == Input.Keys.S){
            keysPressed.set(1, false);
            return true;
        }

        if(keycode == Input.Keys.A){
            keysPressed.set(2, false);
            return true;
        }

        if(keycode == Input.Keys.D){
            keysPressed.set(3, false);
            return true;
        }

        if(keycode == Input.Keys.LEFT){
            keysPressed.set(4, false);
            return true;
        }

        if(keycode == Input.Keys.RIGHT){
            keysPressed.set(5, false);
            return true;
        }

        if(keycode == Input.Keys.SPACE){
            keysPressed.set(6, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public ArrayList<Boolean> getKeysPressed(){
        return this.keysPressed;
    }
}
