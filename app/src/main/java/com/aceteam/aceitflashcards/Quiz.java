package com.aceteam.aceitflashcards;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.io.File;

/** 
 * The Quiz class is a collection of FlashCards that tests the user on
 * their knowledge of a topic.
 * 
 * @see FlashCard
 */
public class Quiz
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String name;
  private int numQuestionsToShow;
  private Set<FlashCard> flashCards;

  //------------------------
  // CONSTRUCTORS
  //------------------------

  /** 
   * Constructs a Quiz with the specified name, the number of questions to show,
   * and the set containing the FlashCards.
   * 
   * @param aName The name of the Quiz.
   * @param aNumQuestionsToShow The number of questions to show on the quiz. Must be
   * less than or equal to the number of FlashCards.
   * @param aFlashCards A Set containing the FlashCards used in the Quiz.
   */
  public Quiz(String aName, int aNumQuestionsToShow, Set<FlashCard> aFlashCards)
  {
    name = aName;
    numQuestionsToShow = aNumQuestionsToShow;
    flashCards = aFlashCards;

    // Quick sanity check on the max and min number of quiz questions.
    if (numQuestionsToShow < 0) {
      numQuestionsToShow = 0;
    } else if (numQuestionsToShow > flashCards.size()) {
      numQuestionsToShow = flashCards.size();
    }
  }

  /** 
   * Constructs a Quiz with the specified name using the provided set of FlashCards.
   * 
   * @param aName The name of the Quiz.
   * @param aFlashCards A Set containing the FlashCards used in the Quiz.
   */
  public Quiz(String aName, Set<FlashCard> aFlashCards)
  {
    new Quiz(aName, aFlashCards.size(), aFlashCards);
  }

  /** 
   * Constructs a Quiz with the specified name using the set of FlashCards
   * with the specified Tag.
   * 
   * @param aName The name of the Quiz.
   * @param aTag A Tag relating to FlashCards.
   */
  public Quiz(String aName, Tag aTag)
  {
    Set<FlashCard> aFlashCards = new HashSet<FlashCard>(aTag.getFlashCards());
    new Quiz(aName, aFlashCards.size(), aFlashCards);
  }

  /** 
   * Constructs a Quiz with the specified name.
   * 
   * @param aName The name of the Quiz.
   */
  public Quiz(String aName)
  {
    new Quiz (aName, 0, new HashSet<FlashCard>());
  }

  /**
   * A constructor for the Quiz class that imports information from a previously exported file.
   * 
   * @param file The file that contains the Quiz information.
   */
  public Quiz(File file){
    // TODO: Code this.
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * Changes the name of the Quiz.
   * 
   * @param aName The new name of the Quiz.
   * 
   * @return True if the name was changed. False otherwise.
   */
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the number of questions to show. Must be at least 0 and less than
   * or equal to the number of FlashCards.
   * 
   * @param aNumQuestionsToShow The number of questions.
   * 
   * @return True if the value was set. False otherwise.
   */
  public boolean setNumQuestionsToShow(int aNumQuestionsToShow)
  {
    boolean wasSet = false;
    numQuestionsToShow = aNumQuestionsToShow;
    wasSet = true;
    return wasSet;
  }

  /**
   * Returns the name of the Quiz.
   * 
   * @return a String representing the name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns the number of questions to show for the Quiz.
   * 
   * @return An integer containing the number of questions to show.
   */
  public int getNumQuestionsToShow()
  {
    return numQuestionsToShow;
  }

  /**
   * Gets the set of FlashCards.
   * 
   * @return The set of FlashCards.
   * 
   * @see FlashCard
   */
  public Set<FlashCard> getFlashCards()
  {
    Set<FlashCard> newFlashCards = Collections.unmodifiableSet(flashCards);
    return newFlashCards;
  }

  /**
   * Gets the number of FlashCards with this Tag.
   * 
   * @return An integer with the number of FlashCards.
   */
  public int numberOfFlashCards()
  {
    int number = flashCards.size();
    return number;
  }

  /**
   * Checks if the set has FlashCards in it.
   * 
   * @return True if the set has at least one FlashCard in it. False otherwise.
   */
  public boolean hasFlashCards()
  {
    boolean has = flashCards.size() > 0;
    return has;
  }

  /**
   * Checks if there is a specific FlashCard.
   * 
   * @param aFlashCard The FlashCard to check.
   * 
   * @return True if the FlashCard is present. False otherwise.
   * 
   * @see FlashCard
   */
  public boolean hasFlashCard(FlashCard aFlashCard)
  {
    boolean has = flashCards.contains(aFlashCard);
    return has;
  }


  /**
   * Adds a FlashCard to this Quiz.
   * 
   * @param aFlashCard The FlashCard to add.
   * 
   * @return True if the FlashCard was added successfully. False otherwise.
   * 
   * @see FlashCard
   */
  public boolean addFlashCard(FlashCard aFlashCard)
  {
    if (flashCards.contains(aFlashCard)) { return false; }

    if (flashCards.add(aFlashCard))
    {
      aFlashCard.addQuiz(this);
      return true;
    }
    return false;
  }

  /**
   * Adds FlashCards to this Quiz by Tag.
   * 
   * @param aTag The Tag related to the FlashCards to add.
   * 
   * @return True if the FlashCards were added successfully. False otherwise.
   * 
   * @see FlashCard
   */
  public boolean addFlashCardsByTag(Tag aTag)
  {
    boolean added = false;
    for (FlashCard card : aTag.getFlashCards()) {
      boolean test = this.addFlashCard(card);
      if (test) {
        added = true;
      }
    }
    return added;
  }

  /**
   * Removes a FlashCard from this Quiz.
   * 
   * @param aFlashCard The FlashCard to remove.
   * 
   * @return True if the FlashCard was removed. False otherwise.
   * 
   * @see FlashCard
   */
  public boolean removeFlashCard(FlashCard aFlashCard)
  {
    if (!flashCards.contains(aFlashCard)) { return false; }

    if (flashCards.remove(aFlashCard))
    {
      aFlashCard.removeQuiz(this);
      return true;
    }
    return false;
  }

  /**
   * Removes every FlashCard from the Quiz.
   */
  public void delete()
  {
    Iterator<FlashCard> i = flashCards.iterator();
    while (i.hasNext()) {
      FlashCard temp = i.next();
      temp.removeQuiz(this);
      i.remove();
    }
  }

  /**
   * Exports the Quiz to a File.
   *
   */

  /*
  public void export()  {
    // TODO: Code this.

    //Get current directory
    PackageManager m = Context.getPackageManager();
    String s = getPackageName();
    PackageInfo p = m.getPackageInfo(s, 0);
    s = p.applicationInfo.dataDir;


    try {
      FileOutputStream fileOut = new FileOutputStream(s + "/quiz.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this);
      out.close();
      fileOut.close();
   } catch (IOException i) {
      i.printStackTrace();
   }

  }

  public static Quiz import()
  {
    //get current app directory
    PackageManager m = getPackageManager();
    String s = getPackageName();
    PackageInfo p = m.getPackageInfo(s, 0);
    s = p.applicationInfo.dataDir;

    //import file
    Quiz q = null;
    try {
      FileInputStream fileIn = new FileInputStream( s + "/quiz.ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      q = (Quiz) in.readObject();
      in.close();
      fileIn.close();
      return q;
   } catch (IOException i) {
      i.printStackTrace();
      return null;
   } catch (ClassNotFoundException c) {
      System.out.println("Quiz class not found");
      c.printStackTrace();
      return null;
   }
  }


   */

 /*
  public JSONObject getJson() throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", name);
    jsonObject.put("numQuestionsToShow", numQuestionsToShow);
    jsonObject.put("flashcards", flashCards);
    return jsonObject;

  }
*/
    /**
     * Returns a text representation of the Quiz.
     * @return A String representing the Quiz.
     */
  public String toString()
  {
    String s = super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "numQuestionsToShow" + ":" + getNumQuestionsToShow()+ ", {";
    
    Iterator<FlashCard> i = flashCards.iterator();
    while (i.hasNext()) {
      FlashCard temp = i.next();
      s += temp.toString();
      if (i.hasNext()) {
        s += ", ";
      }
    }
    s += "} ]";
    return s;
  }
}