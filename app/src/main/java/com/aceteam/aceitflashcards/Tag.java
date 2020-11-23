package com.aceteam.aceitflashcards;

import java.util.Set;

/**
 * This class represents categories of FlashCards. FlashCards can have zero
 * or more Tags, and users can use Tags to easily find related FlashCards.
 * 
 * @see FlashCard
 */
public class Tag
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
    Tag(aName, new HashSet<FlashCard>(flashCard));
  }

  /**
   * Constructs a Tag with a name, and does not apply the Tag to any FlashCards.
   * 
   * @param aName The name of the Tag.
   */
  public Tag(String aName){
    Tag(aName, new HashSet<FlashCard>());
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
   * Returns a text representation of the Tag.
   * @return A String representing the Tag.
   */
  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
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
    return t.getName.equalsIgnoreCase(name);
  }
}