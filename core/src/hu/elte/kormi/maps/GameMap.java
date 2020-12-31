package hu.elte.kormi.maps;

import hu.elte.kormi.database.DatabaseHandler;

import java.util.ArrayList;

public class GameMap {

    private final ArrayList<ArrayList<Integer>> terrain;
    private final ArrayList<ArrayList<Integer>> rocks;
    private final ArrayList<ArrayList<Integer>> targets;

    public GameMap(int season, int map){

        ArrayList<String> terrainData = DatabaseHandler.getTerrain(season, map);
        ArrayList<String> rocksData = DatabaseHandler.getRocks(season, map);
        ArrayList<String> targetsData = DatabaseHandler.getTargets(season, map);

        if (terrainData != null) {
            terrain = convertEnvironmentData(terrainData);
        } else {
            terrain = null;
        }

        if (rocksData != null) {
            rocks = convertEnvironmentData(rocksData);
        } else {
            rocks = null;
        }

        if (targetsData != null) {
            targets = convertEnvironmentData(targetsData);
        } else {
            targets = null;
        }
    }

    public ArrayList<ArrayList<Integer>> convertEnvironmentData(ArrayList<String> data){

        ArrayList<ArrayList<Integer>> convertedData = new ArrayList<>();
        for (String row : data) {

            ArrayList<Integer> temp = new ArrayList<>();

            for (int j = 0; j < row.length(); j++) {
                char c = row.charAt(j);
                temp.add(Character.getNumericValue(c));
            }
            convertedData.add(temp);
        }
        return convertedData;
    }

    public ArrayList<ArrayList<Integer>> getTerrain() {
        return this.terrain;
    }

    public ArrayList<ArrayList<Integer>> getRocks() {
        return this.rocks;
    }

    public ArrayList<ArrayList<Integer>> getTargets(){
        return this.targets;
    }
}
