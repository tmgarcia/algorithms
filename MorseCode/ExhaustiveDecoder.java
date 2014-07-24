import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.neumont.nlp.DecodingDictionary;

public class ExhaustiveDecoder 
{
	private class Sentence implements Comparable<Sentence>
	{
		public String decodedSentence;
		public float overallLikelihood;
		
		public Sentence(String sentence, float likelihood)
		{
			decodedSentence = sentence;
			overallLikelihood = likelihood;
		}
		public void print()
		{
			System.out.println(decodedSentence);
		}
		@Override
		public int compareTo(Sentence o)
		{
			int result = -1;
			if(overallLikelihood == o.overallLikelihood)
			{
				result = 0;
			}
			else if(overallLikelihood > o.overallLikelihood)
			{
				result = 1;
			}
			return result;
		}
	}
	float frequencyThreshold;
	DecodingDictionary dictionary;
	public int numSentencesToReturn;
	//Set set;
	
	public ExhaustiveDecoder(DecodingDictionary dict, int threshold)
	{
		dictionary = dict;
		frequencyThreshold = threshold;
		numSentencesToReturn = 20;
	}
	
	/**
	 * Calls a helper method that uses backtracking to build a list sentences that could potentially be the translated form of the input Morse code message.
	 * After this list of sentences is built, each sentence is given a likelihood variable, representing the overall likelihood of the sentence occurring in the English language.
	 * This list is then sorted by this likelihood factor, and the top (most likely) sentences are then returned.
	 * 
	 * @param message A single line of Morse code to be decoded.
	 * @return A list of the top 20 sentences that could potentially be translations of the Morse code message.
	 */
	public List<String> decode(String message)
	{
		String previousWord = "";
		String sentenceSoFar = "";
		String remainingMessage = message;
		ArrayList<String> completedSentences = new ArrayList<String>();
		
		ArrayList<String>Sentences = decodeHelper(previousWord, sentenceSoFar, remainingMessage, completedSentences);
		ArrayList<Sentence>SentencesWithLikelihood = new ArrayList<Sentence>();//Storing each sentence string with its overall likelihood score
		for(int i = 0; i < Sentences.size(); i++)
		{
			float likelihood = 1;
			String currentSentence = Sentences.get(i);
			String[] words = currentSentence.split("\\s+");
			for(int j = 1; j < words.length; j++)
			{
				likelihood *= ((dictionary.frequencyOfFollowingWord(words[j-1], words[j]))/1000);
			}
			SentencesWithLikelihood.add(new Sentence(currentSentence, likelihood));
		}
		Collections.sort(SentencesWithLikelihood);//Sorting the sentences by their likelihood 
		Collections.reverse(SentencesWithLikelihood);
		
		ArrayList<String> topSentences = new ArrayList<String>();
		for(int i = 0; i < numSentencesToReturn && i < SentencesWithLikelihood.size(); i++)//Taking the top sentences
		{
			topSentences.add(SentencesWithLikelihood.get(i).decodedSentence);
		}
		return topSentences;
	}
	
	/**
	 * Uses backtracking to create a list of potential translations for a string of Morse Code.
	 * Each branch investigates a possible sentence, ending when no more words can be translated from the Morse code message, and adding a complete sentence when there is no more Morse code to be translated.
	 * This means that a branch, or potential sentence, will only be fully considered if the entire Morse Code message can be used to build it.
	 * 
	 * @param previousWord The word that came previously in the current sentence being built, an empty string if this is the first time this method is being called.
	 * @param decodedSentenceSoFar Holds the current sentence being built, an empty string if this is the first time this method is being called.
	 * @param remainingMessageToDecode The remaining Morse code that has yet to be decoded. If the string is empty, the current branch (current sentence being built) is complete.
	 * @param completedSentences Holds all of the complete sentences that have been created so far.
	 * @return A list of all sentences that can potentially be translated from the Morse code message being decoded.
	 */
	private ArrayList<String> decodeHelper(String previousWord, String decodedSentenceSoFar, String remainingMessageToDecode, ArrayList<String> completedSentences)
	{
		if(remainingMessageToDecode.isEmpty())
		{
			completedSentences.add(decodedSentenceSoFar);
		}
		else
		{
			for(int i = 0; i < remainingMessageToDecode.length(); i++)
			{
				String chunkOfCode = remainingMessageToDecode.substring(0, i+1);
				Set<String> wordSet = dictionary.getWordsForCode(chunkOfCode);
				if(wordSet != null)//Checking to make sure the chunk of morse code matched with any words
				{
					for(String s : wordSet)
					{
						if(previousWord.equals("") || dictionary.frequencyOfFollowingWord(previousWord,s)>=frequencyThreshold)//If it's the first word or its frequency is above the threshold
						{
							String previous = s;
							String sentenceSoFar = decodedSentenceSoFar;
							sentenceSoFar = sentenceSoFar + s+" ";
							String remainingMessage = remainingMessageToDecode.substring(i+1);
							decodeHelper(previous, sentenceSoFar, remainingMessage, completedSentences);
						}
					}
				}
			}
		}
		return completedSentences;
	}
}