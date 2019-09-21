package hr.fer.zemris.java.hw06.shell.commands.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser used for parsing expression.
 * Returns {@link NameBuilder} used for building name.
 * */
public class NameBuilderParser {

	/**
	 * List of NameBuilders which will be merged into one NameBuilder
	 * */
	private List<NameBuilder> nbList;
	/**
	 * index of character of expression (izraz)
	 * */
	private int index;
	/**
	 * length of expression (izraz)
	 * */
	private int length;
	
	/**
	 * Constructor that parses expression (izraz) and saves it into nbList.
	 * @param izraz the expression that needs to be parsed
	 * @throws IllegalStateException If expression is unparsable
	 * @throws NumberFormatException If wrong format of number in expression
	 * */
	public NameBuilderParser(String izraz) {
		nbList = new ArrayList<>();
		index = 0;
		length = izraz.length();
		while (index < length) {
			StringBuilder sb = new StringBuilder();
			
			// normal text
			while (index < length) {
				if(index + 1 < length && izraz.charAt(index) == '$' && izraz.charAt(index+1) == '{') {
					break;
				}
				sb.append(izraz.charAt(index++));
			}
			nbList.add(text(sb.toString()));
			if(index == length) {
				break;
			}
			nbList.add(parseExpression(izraz));
		}
	}

	/**
	 * Returns main NameBuilder that is executable.
	 * @return NameBuilder that is executable
	 * */
	public NameBuilder getNameBuilder() {
		return list(nbList);
	}
	
	/**
	 * Construct main NameBuilder which is constructed form list of NameBuilders.
	 * @param list of NameBuilders that will be merged into one NameBuilder
	 * @return constructed {@link NameBuilder}
	 * */
	private static NameBuilder list(List<NameBuilder> list) {
		return (FilterResult result, StringBuilder sb) -> {
			for(NameBuilder nb:list) {
				nb.execute(result, sb);
			}
		};
	}

	/**
	 * Construct NameBuilder from text.
	 * @param text
	 * @return constructed NameBuilder
	 * */
	private static NameBuilder text(String t) {
		return (FilterResult result, StringBuilder sb) -> {
			sb.append(t);
		};
	}

	/**
	 * Construct NameBuilder from group of index.
	 * @param index of group
	 * @return constructed NameBuilder
	 * @throws IllegalStateException if number of groups is less than index
	 * */
	private static NameBuilder group(int index) {
		return (FilterResult result, StringBuilder sb) -> {
			if(index > result.numberOfGroups()) {
				throw new IllegalStateException("Number of groups is less than index.");
			}
			sb.append(result.group(index));
		};
	}

	/**
	 * Construct NameBuilder from group of index with padding and minimum width.
	 * @param index of group
	 * @param padding which will be added if needed
	 * @param minWidth which is used to calculate padding
	 * @return constructed NameBuilder
	 * @throws IllegalStateException if number of groups is less than index
	 * */
	private static NameBuilder group(int index, char padding, int minWidth) {
		return (FilterResult result, StringBuilder sb) -> {
			if(index > result.numberOfGroups()) {
				throw new IllegalStateException("Number of groups is less than index.");
			}
			String group = result.group(index);
			if (minWidth > group.length()) {
				sb.append(Character.toString(padding).repeat(minWidth - group.length()));
			}
			sb.append(group);
		};
	}
	
	/**
	 * This method parse expression inside braces: ${}
	 * @param izraz expression given in constructor.
	 * @return NameBuilder that is constructed
	 * @throws IllegalStateException If expression is unparsable
	 * @throws NumberFormatException If wrong format of number in expression
	 * */
	private NameBuilder parseExpression(String izraz) {
		// ${
		index += 2;
		trim(izraz);
		StringBuilder sb = new StringBuilder();
		
		if(index < length && !Character.isDigit(izraz.charAt(index))) {
			throw new IllegalStateException("Unparsable expression. Wrong character: " + izraz.charAt(index) + ". Should be digit." );
		}
		while(index<length && Character.isDigit(izraz.charAt(index))) {
			sb.append(izraz.charAt(index++));
		}
		
		trim(izraz);
		
		if(index < length && izraz.charAt(index) == '}') {
			
			int number = Integer.parseInt(sb.toString());
			if(number<0) {
				throw new NumberFormatException(sb.toString() + " is negative. Should be non-negative.");
			}
			
			index++;
			return group(number);
		
		} else if (index < length && izraz.charAt(index) == ',') {
			
			int number = Integer.parseInt(sb.toString());
			if(number<0) {
				throw new NumberFormatException(sb.toString() + " is negative. Should be non-negative.");
			}
			
			++index;
			trim(izraz);
			
			char padding;
			if(index+1<length && izraz.charAt(index) == '0' && Character.isDigit(izraz.charAt(index+1)) ) {
				padding='0';
				++index;
			} else {
				padding = ' ';
			}
			
			sb = new StringBuilder();
			while(index < length && Character.isDigit(izraz.charAt(index))) {
				sb.append(izraz.charAt(index++));
			}
			
			int number2 = Integer.parseInt(sb.toString());
			if(number2<0) {
				throw new NumberFormatException(sb.toString() + " is negative.");
			}
			
			trim(izraz);
			
			if(index < length && izraz.charAt(index) != '}') {
				throw new IllegalStateException("Unparsable expression. Expected '}");
			}
			
			// }
			++index;
			return group(number, padding, number2);
		} else {
			throw new IllegalStateException("Unparsable expression.");
		}
		
	}
	
	/**
	 * Trims all whitespaces from izraz.
	 * @param izraz
	 * @throws IllegalStateException If expression is unparsable
	 * */
	private void trim(String izraz) {
		while(index<length && (izraz.charAt(index) == ' ' || izraz.charAt(index) == '\t')) {
			++index;
		}
		if(index == length) {
			throw new IllegalStateException("Unparsable expression. Expected '}");
		}
	}

}
