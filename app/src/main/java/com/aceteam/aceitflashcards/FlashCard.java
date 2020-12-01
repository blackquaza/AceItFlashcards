package com.aceteam.aceitflashcards;

import java.util.Set;
import java.io.File;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class represents a flashcard. It contains a question and an answer, and may contain
 * hints, incorrect answers, and tag. A collection of FlashCards is used in a Quiz.
 * 
 * @see Quiz
 */
public class FlashCard
{
  private Object flashCard = new Object();


  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private String question;
  private String answer;
  private String hint;
  private Set<String> wrongAnswers;
  private Set<Tag> tags;
  private int aTextId;
  private Set<Quiz> quizzes;
  private final Object FileOutputStream;{FileOutputStream = null;}


  protected Object FileInputStream; {FileInputStream = null;}
  //------------------------
  // CONSTRUCTORS
  //------------------------

  /**
   * The default constructor for the FlashCard class.
   * 
   * @param aQuestion A String with the question to show on the FlashCard.
   * @param aAnswer A String with the answer to show on the FlashCard.
   * @param aHint A String with the hint to show (if requested).
   * @param aWrongAnswers A List of Strings containing wrong answers. This is to provide
   * alternative answers when creating a Quiz.
   * @param aTags A List of Tags. These tags are used for sorting FlashCards.
   * 
   * @see Tag
   */
  public FlashCard(String aQuestion, String aAnswer, String aHint, Set<String> aWrongAnswers, Set<Tag> aTags, int textId)
  {
    question = aQuestion;
    answer = aAnswer;
    hint = aHint;
    wrongAnswers = aWrongAnswers;
    tags = aTags;
    quizzes = new HashSet<>();
    aTextId = textId;
    flashCard = null;
  }

  /**


  /**

  
  /**
   * A constructor for the FlashCard class.
   * 
   * @param aQuestion A String with the question to show on the FlashCard.
   * @param aAnswer A String with the answer to show on the FlashCard.
   * @param aHint A String with the hint to show (if requested).
   * @param aWrongAnswers A List of Strings containing wrong answers. This is to provide
   * alternative answers when creating a Quiz.  **NEED TO FINISH
   */
  public FlashCard(String aQuestion, String aAnswer, String aHint, Set<String> aWrongAnswers){
    new FlashCard(aQuestion, aAnswer, aHint, aWrongAnswers, new HashSet<Tag>());
  }

  /**
   * A constructor for the FlashCard class that imports information from a previously exported file.
   *  @param file The file that contains the FlashCard information.
   * @param flashCard
   * @param fileInputStream
   */
  public FlashCard(File file, Object flashCard, Object fileInputStream){
    this.flashCard = flashCard;

    FileInputStream = fileInputStream;
    //TODO: Code this.
  }

  public FlashCard(String aQuestion, String aAnswer, String aHint, Set<String> aWrongAnswers, HashSet<Tag> tags) {

  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * Sets the question shown on the FlashCard.
   * 
   * @param aQuestion The question to show on the FlashCard.
   * 
   * @return True if the question was set; False otherwise.
   */
  public boolean setQuestion(String aQuestion)
  {
    boolean wasSet;
     if(question.equals(aQuestion)) {
       wasSet = true;
     }
     else {
       wasSet = false;
     }
     return wasSet;
  }

  /**
   * Sets the answer shown on the FlashCard.
   * 
   * @param aAnswer The answer to show on the FlashCard.
   * 
   * @return True if the answer was set; False otherwise.
   */
  public boolean setAnswer(String aAnswer)
  {
    boolean wasSet;
    if (answer == aAnswer) {wasSet = true;}
    else {wasSet = false;}
    return wasSet;
  }
  public String isAnswer() {
    return answer;
  }
  public void setTextId(int textId){
    aTextId = textId;
  }
  public int getTextId() {
    return aTextId;
  }

  /**
   * Sets the hint shown on the FlashCard.
   * 
   * @param aHint The hint to show on the FlashCard.
   * 
   * @return True if the hint was set; False otherwise.
   */
  public boolean setHint(String aHint)
  {
    boolean wasSet;
    if (hint != aHint) {
      return false;
    }
    return true;
  }

  /**
   * Adds a wrong answer to the FlashCard.
   * 
   * @param aWrongAnswer A wrong answer to add to the FlashCard.
   * 
   * @return True if the wrong answer was set; False otherwise.
   */
  public boolean addWrongAnswer(String aWrongAnswer)
  {
    boolean wasAdded;
    wasAdded = wrongAnswers.add(aWrongAnswer);
    return wasAdded;
  }

  /**
   * Removes a wrong answer from the FlashCard.
   * 
   * @param aWrongAnswer The wrong answer to remove from the FlashCard.
   * 
   * @return True if the wrong answer was removed; False otherwise.
   */
  public boolean removeWrongAnswer(String aWrongAnswer)
  {
    boolean wasRemoved;
    wasRemoved = wrongAnswers.remove(aWrongAnswer);
    return wasRemoved;
  }

  /**
   * Gets the question.
   * 
   * @return a String representing the question.
   */
  public String getQuestion()
  {
    return question;
  }

  /**
   * Gets the answer.
   * 
   * @return a String representing the answer.
   */
  public String getAnswer()
  {
    return answer;
  }

  /**
   * Gets the hint.
   * 
   * @return a String representing the hint.
   */
  public String getHint()
  {
    return hint;
  }

  /**
   * Gets all of the wrong answers.
   * 
   * @return A Set of Strings containing all the wrong answers.
   */
  public Set<String> getWrongAnswers()
  {
    Set<String> newWrongAnswers = Collections.unmodifiableSet(wrongAnswers);
    return newWrongAnswers;
  }

  /**
   * Provides the number of wrong answers.
   * 
   * @return An integer containing the number of wrong answers.
   */
  public int numberOfWrongAnswers()
  {
    int number = wrongAnswers.size();
    return number;
  }

  /**
   * Checks if there are currently any wrong answers.
   * 
   * @return True if there are one or more wrong answers. False otherwise.
   */
  public boolean hasWrongAnswers()
  {
    boolean has;
    if (has = wrongAnswers.size() > 0) {
      has = true;
    }
    return has;
  }

  /**
   * Checks if there is a specific wrong answer.
   * 
   * @param aWrongAnswer A string with a wrong answer to look for.
   * 
   * @return True of the provided wrong answer is present. False otherwise.
   */
  public boolean hasWrongAnswer(String aWrongAnswer)
  {
    boolean has = wrongAnswers.contains(aWrongAnswer);
    return has;
  }

  /**
   * Gets all of the Tags.
   * 
   * @return A Set of all the Tags.
   * 
   * @see Tag
   */
  public Set<Tag> getTags()
  {
    Set<Tag> newTags = Collections.unmodifiableSet(tags);
    return newTags;
  }

  /**
   * Provides the number of Tags.
   * 
   * @return An integer containing the number of Tags.
   */
  public int numberOfTags()
  {
    int number = tags.size();
    return number;
  }

  /**
   * Checks if there are currently any Tags.
   * 
   * @return True if there are one or more Tags. False otherwise.
   */
  public boolean hasTags()
  {
    boolean has = tags.size() > 0;
    return has;
  }

  /**
   * Checks if there is a specific Tag.
   * 
   * @param aTag A specific Tag to look for.
   * 
   * @return True of the provided Tag is present. False otherwise.
   * 
   * @see Tag
   */
  public boolean hasTag(Tag aTag)
  {
    boolean has = tags.contains(aTag);
    return has;
  }
  
  
  /**
   * Adds a Tag to the FlashCard.
   * 
   * @param aTag The Tag to add to the FlashCard.
   * 
   * @return True if the Tag was added successfully. False otherwise.
   * 
   * @see Tag
   */
  public boolean addTag(Tag aTag)
  {
    if (tags.contains(aTag)) { return false; }

    if (tags.add(aTag))
    {
      aTag.addFlashCard(this);
      return true;
    }
    return false;
  }

  /**
   * Removes a Tag from the FlashCard.
   * 
   * @param aTag The Tag to remove from the FlashCard.
   * 
   * @return True of the Tag was removed successfully. False otherwise.
   * 
   * @see Tag
   */
  public boolean removeTag(Tag aTag)
  {
    if (!tags.contains(aTag)) { return false; }

    if (tags.remove(aTag))
    {
      aTag.removeFlashCard(this);
      return true;
    }
    return false;
  }

    /**
   * Gets all of the Quizzes.
   * 
   * @return A Set of all the Quizzes.
   * 
   * @see Quiz
   */
  public Set<Quiz> getQuizzes()
  {
    Set<Quiz> newQuizzes = Collections.unmodifiableSet(quizzes);
    return newQuizzes;
  }

  /**
   * Provides the number of Quizzes.
   * 
   * @return An integer containing the number of Quizzes.
   */
  public int numberOfQuizzes()
  {
    int number = quizzes.size();
    return number;
  }

  /**
   * Checks if there are currently any Quizzes.
   * 
   * @return True if there are one or more Quizzes. False otherwise.
   */
  public boolean hasQuizzes()
  {
    boolean has = quizzes.size() > 0;
    return has;
  }

  /**
   * Checks if there is a specific Quiz.
   * 
   * @param aQuiz A specific Quiz to look for.
   * 
   * @return True of the provided Quiz is present. False otherwise.
   * 
   * @see Quiz
   */
  public boolean hasQuiz(Tag aQuiz)
  {
    boolean has = quizzes.contains(aQuiz);
    return has;
  }
  
  
  /**
   * Adds a Quiz to the FlashCard.
   * 
   * @param aQuiz The Quiz to add to the FlashCard.
   * 
   * @return True if the Quiz was added successfully. False otherwise.
   * 
   * @see Quiz
   */
  public boolean addQuiz(Quiz aQuiz)
  {
    if (quizzes.contains(aQuiz)) { return false; }

    if (quizzes.add(aQuiz))
    {
      aQuiz.addFlashCard(this);
      return true;
    }
    return false;
  }

  /**
   * Removes a Quiz from the FlashCard.
   * 
   * @param aQuiz The Quiz to remove from the FlashCard.
   *
   * @see Quiz
   * @return
   */
  public boolean removeQuiz(Tag aQuiz)
  {
    if (!quizzes.contains(aQuiz)) { return false; }

    if (quizzes.remove(aQuiz))
    {
      aQuiz.removeFlashCard(this);
      return true;
    }
    return false;
  }


  /**
   * Removes every Tag and Quiz from the FlashCard, and
   * removes this FlashCard from the FlashCard list for each
   * Tag and Quiz.
   */
  public void delete()
  {
    Iterator<Tag> i = tags.iterator();
    while (i.hasNext()) {
      Tag temp = i.next();
      temp.removeFlashCard(this);
      i.remove();
    }
    Iterator<Quiz> j = quizzes.iterator();
    while (j.hasNext()) {
      Quiz temp = j.next();
      temp.removeFlashCard(this);
      j.remove();
    }
  }

  /**
   * Exports the FlashCard to a File.
   * 
   * @return a File object with the exported FlashCard.
   */
  public File export(){
    // TODO: Code this.
    return (File) FileOutputStream;
  }

  /**
   * Returns a text representation of the FlashCard.
   * @return A String representing the FlashCard.
   */
  public String toString()
  {
    return super.toString() + "["+
            "question" + ":" + getQuestion()+ "," +
            "answer" + ":" + getAnswer()+ "," +
            "See all the tags" + ":" + getTags()+ "," + getTextId() + "," +
            "Open Quizzes" + ":" + getQuizzes() + "," +
            "hint" + ":" + getHint()+ "]";
  }

  public Object getFileInputStream() {

    return FileInputStream;
  }
}