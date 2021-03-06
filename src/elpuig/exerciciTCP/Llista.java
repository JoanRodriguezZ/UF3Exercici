package elpuig.exerciciTCP;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Llista implements Serializable {
    private static final long serialVersionUID = 2L;
    private String nom;
    private List<Integer> numberList;

    public Llista(String nom) {
        this.nom = nom;

        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            intList.add((int) (Math.random() * 20 + 1));
        }
        this.numberList = intList;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Integer> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<Integer> numberList) {
        this.numberList = numberList;
    }
}
