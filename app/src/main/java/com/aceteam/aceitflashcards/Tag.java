package com.aceteam.aceitflashcards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class represents categories of FlashCards. FlashCards can have zero
 * or more Tags, and users can use Tags to easily find related FlashCards.
 * 
 * @see FlashCard
 */
public class Tag implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String name;
  private Set<FlashCard> flashCards;

  //------------------------
  // CONSTRUCTORS
  //------------------------

  /**
   * Constructs a Tag with a name and adds the Tag to the specified FlashCards.
   * 
   * @param aName The name of the Tag.
   * @param aFlashCards The FlashCards to apply the Tag to.
   * 
   * @see FlashCard
   */
  public Tag(String aName, Set<FlashCard> aFlashCards)
  {
    name = aName;
    flashCards = aFlashCards;
    for (FlashCard flashCard : flashCards) {
      flashCard.addTag(this);
    }
  }

  /**
   * Constructs a Tag with a name and adds the Tag to a single FlashCard.
   * 
   * @param aName The name of the Tag.
   * @param flashCard The FlashCard to apply the Tag to.
   * 
   * @see FlashCard
   */
  public Tag(String aName, FlashCard flashCard) {
    this(aName, new HashSet<>(Arrays.asList(flashCard)));
  }

  /**
   * Constructs a Tag with a name, and does not apply the Tag to any FlashCards.
   * 
   * @param aName The name of the Tag.
   */
  public Tag(String aName){
    this(aName, new HashSet<FlashCard>());
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * Sets the name for a Tag.
   * 
   * @param aName The name of the Tag.
   * 
   * @return True if name was set successfully. False otherwise.
   */
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  /**
   * Gets the name of a Tag.
   * 
   * @return The name of the Tag.
   */
  public String getName()
  {
    return name;
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
   * Adds this Tag to a FlashCard.
   * 
   * @param aFlashCard The FlashCard to tag.
   * 
   * @return True if the Tag was added successfully. False otherwise.
   * 
   * @see FlashCard
   */
  public boolean addFlashCard(FlashCard aFlashCard)
  {
    if (flashCards.contains(aFlashCard)) { return false; }

    if (flashCards.add(aFlashCard))
    {
      aFlashCard.addTag(this);
      return true;
    }
    return false;
  }

  /**
   * Removes this Tag from a FlashCard.
   * 
   * @param aFlashCard The FlashCard to edit.
   * 
   * @return True if the tag was removed from the FlashCard. False otherwise.
   * 
   * @see FlashCard
   */
  public boolean removeFlashCard(FlashCard aFlashCard)
  {
    if (!flashCards.contains(aFlashCard)) { return false; }

    if (flashCards.remove(aFlashCard))
    {
      aFlashCard.removeTag(this);
      return true;
    }
    return false;
  }

  /**
   * Removes every FlashCard from the set, and removes the Tag
   * from each FlashCard.
   */
  public void delete()
  {
    Iterator<FlashCard> i = flashCards.iterator();
    while (i.hasNext()) {
      FlashCard temp = i.next();
      temp.removeTag(this);
      i.remove();
    }
  }


  /**
   * Exports the Tag to a file.
   *
   * @param cardFolder the folder in which to place the file.
   */
  public void exportTag(File cardFolder) {

    try {
      File loc = new File(cardFolder, getName() + ".ser");
      FileOutputStream fileOut = new FileOutputStream(loc);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
  /**
   * Imports a new FlashCard from a file.
   * @return a FlashCard.
   */
  public static Tag importTag(File file)
  {
    Tag f = null;
    try {
      java.io.FileInputStream fileIn = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      f = (Tag) in.readObject();
      in.close();
      fileIn.close();
      return f;
    } catch (IOException i) {
      i.printStackTrace();
      return null;
    } catch (ClassNotFoundException c) {
      System.out.println("Tag class not found");
      c.printStackTrace();
      return null;
    }
  }

  /**
   * Returns a text representation of the Tag.
   * @return A String representing the Tag.
   */
  public String toString()
  {
    return "[name" + ":" + getName()+ "]";
  }

  /**
   * Compares this object to another to see if they are equal.
   * 
   * @param obj The object to compare.
   * 
   * @return True if the objects are Tags and have the same name. False otherwise.
   */
  public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    Tag t = (Tag) obj;
    return t.getName().equalsIgnoreCase(name);
  }
}