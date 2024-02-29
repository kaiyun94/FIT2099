package game.monologue;

import edu.monash.fit2099.engine.actors.Actor;
import game.capabilities.Ability;
import game.capabilities.Indicator;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that manages different speeches
 */
public class Monologue {

    /**
     * an instance of monologue
     */
    private static Monologue instance;

    /**
     * array list of sentences
     */
    private ArrayList<String> sentences;

    /**
     * Constructor
     */
    private Monologue() {
        sentences = new ArrayList<>();
        addSentence("You might need a wrench to smash Koopa's hard shells.");
        addSentence("You better get back to finding the Power Stars.");
        addSentence("The Princess is depending on you! You are our only hope.");
        addSentence("Being imprisoned in these walls can drive a fungus crazy :(");
    }

    /**
     * Get an instance of a monologue
     * @return an instance of monologue
     */
    public static Monologue getInstance() {
        if (instance == null) {
            instance = new Monologue();
        }
        return instance;
    }

    /**
     * Get a sentence to be spoken out
     * @param target the actor that is spoken to
     * @param character the actor that speaks
     * @return a sentence
     */
    public String run(Actor target, Actor character) {
        Random r = new Random();
        String result = "";
        int selection;
        // repeat until there is a sentence to be spoken out
        while(result.length() == 0) {
            selection = r.nextInt(sentences.size());
            switch(selection) {
                case 0:
                    // if the target is holding a wrench, do not print out the first sentence
                    if(!target.hasCapability(Indicator.WRENCH_POINTER))
                        result = sentences.get(0);
                    break;
                case 1:
                    // if the target is invincible(power star effect is active), do not print out the second sentence
                    if(!target.hasCapability(Ability.INVINCIBLE))
                        result = sentences.get(1);
                    break;
                case 2:
                    result = sentences.get(2);
                    break;
                case 3:
                    result = sentences.get(3);
                    break;
            }
        }
        return character + ": " + result;
    }

    /**
     * To add a sentence into the list
     * @param text a sentence to be added
     */
    public void addSentence(String text) {
        sentences.add(text);
    }
}
