package java_source_code_management_tool.util;

/**
 * This class consists of helper methods that operate on or return Java code.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class JavaFormatter
{
	/**
	 * Returns the specified Java code with the specified type of comment being deleted.
	 * 
	 * @param content the Java code to operate on.
	 * @param commentTypeToDelete the type of comment to delete (0: comments / 1: Javadoc).
	 * @return the Java code with the specified type of comment being deleted.
	 */
	public static String deleteCommentsOrJavadoc(String content, int commentTypeToDelete)
	{
		StringBuilder contentSB;
		String currentState = "OUTSIDE_COMMENT";
		char currentChar, nextChar = 0, prevChar = 0;
		boolean latestReadCharDeleted = false;
		
		// Get content to process
		contentSB = new StringBuilder(content);
		
		// Iterate over content one char at a time
		int i=0;
		while(i < contentSB.length())
		{
			// Get current char
			currentChar = contentSB.charAt(i);
			
			// Get next char
			if(i < contentSB.length()-1)
				nextChar = contentSB.charAt(i+1);
			
			switch(currentState)
			{
				case "OUTSIDE_COMMENT":
					
					// If previous read char is not a backslash
					if(prevChar != '\\' || (prevChar == '\\' && i > 1 && contentSB.charAt(i-2) == '\\'))
					{
						if(currentChar == '/')
						{
							// If start of '//' comment
							if(nextChar == '/' && commentTypeToDelete == 0)
							{
								currentState = "ONE_LINE_COMMENT";
								
								// Delete current char
								contentSB.deleteCharAt(i);
								latestReadCharDeleted = true;
							}
							// If start of '/*' comment
							else if(nextChar == '*' && i < contentSB.length()-2 && ((commentTypeToDelete == 1 && contentSB.charAt(i+2) == '*') || (commentTypeToDelete == 0 && contentSB.charAt(i+2) != '*')))
							{
								currentState = "MULTIPLE_LINES_COMMENT";
								
								// Delete current char
								contentSB.deleteCharAt(i);
								latestReadCharDeleted = true;
							}
						}
						// If start of quotation
						else if(currentChar == '\'')
								currentState = "SINGLE_QUOTE";
						else if(currentChar == '"')
								currentState = "DOUBLE_QUOTES";
					}
					
					break;
					
				case "SINGLE_QUOTE":
					
					// If end of quotation
					if(currentChar == '\'' && (prevChar != '\\' || (prevChar == '\\' && i > 1 && contentSB.charAt(i-2) == '\\')))
						currentState = "OUTSIDE_COMMENT";
					
					break;
					
				case "DOUBLE_QUOTES":
					
					// If end of quotation
					if(currentChar == '"' && (prevChar != '\\' || (prevChar == '\\' && i > 1 && contentSB.charAt(i-2) == '\\')))
						currentState = "OUTSIDE_COMMENT";

					break;
					
				case "ONE_LINE_COMMENT":
					
					// Delete current char
					contentSB.deleteCharAt(i);
					latestReadCharDeleted = true;
					
					// if end of one line comment
					if(nextChar == '\n')
					{
						currentState = "OUTSIDE_COMMENT";
						
						// Delete remaining line containing the comment
						while(i > 0 && Character.isWhitespace(contentSB.charAt(i-1)) && contentSB.charAt(i-1) != '\n')
						{
							contentSB.deleteCharAt(i-1);
							i--;
						}
						if(i > 0 && contentSB.charAt(i-1) == '\n')
						{
							contentSB.deleteCharAt(i-1);
							i--;
						}
						else if(i == 0)
							contentSB.deleteCharAt(i);
					}
					
					break;
					
				case "MULTIPLE_LINES_COMMENT":
					
					// Delete current char
					contentSB.deleteCharAt(i);
					latestReadCharDeleted = true;
					
					// If end of multiple lines comment
					if(currentChar == '/' && prevChar == '*')
					{
						currentState = "OUTSIDE_COMMENT";
						
						// Delete remaining line containing the comment
						while(i > 0 && Character.isWhitespace(contentSB.charAt(i-1)) && contentSB.charAt(i-1) != '\n')
						{
							contentSB.deleteCharAt(i-1);
							i--;
						}
						if(i > 0 && contentSB.charAt(i-1) == '\n')
						{
							contentSB.deleteCharAt(i-1);
							i--;
						}
						else if(i == 0)
							contentSB.deleteCharAt(i);
					}
					
					break;
			}
			
			// Set latest read char as previous char
			prevChar = currentChar;
			
			// Go to next char if current one has been processed
			if(!latestReadCharDeleted)
				i++;
			else
				latestReadCharDeleted = false;
		}
		
		return contentSB.toString();
	}
	
	/**
	 * Returns the specified Java code with the code being indented.
	 * 
	 * @param content the Java code to operate on.
	 * @return the Java code with the code being indented.
	 */
	public static String indent(String content)
	{
		StringBuilder contentSB = new StringBuilder();
		String[] contentSplit;
		String quoteState = "NONE";
		String commentState = "NONE";
		int nbIndent = 0;
		char currentChar, nextChar = 0, prevChar = 0;
		
		// Get content
		contentSplit = content.split("\\r?\\n");
		
		// Trim every line of content
		for(int i=0; i<contentSplit.length; i++)
		{
			// Trim line
			contentSB.append(contentSplit[i].trim());
			contentSB.append('\n');
		}
		
		// Indent code
		int i=0;
		while(i < contentSB.length())
		{
			// Get current char
			currentChar = contentSB.charAt(i);
			
			// Get next char
			if(i < contentSB.length()-1)
				nextChar = contentSB.charAt(i+1);
			
			// Process char being read
			switch(currentChar)
			{
				case '{':
				case '}':
					
					// If not between quotes or not in a one-line-comment
					if(quoteState.equals("NONE") && !commentState.equals("ONE_LINE_COMMENT"))
					{
						// Make '{' and '}' always appear alone in a line
						if(nextChar != '\n')
						{
							contentSB.insert(i+1, '\n');
							nextChar = '\n';
						}
						if(prevChar != '\n')
						{
							contentSB.insert(i, '\n');
							i++;
							prevChar = '\n';
						}
						
						// Update number of indents to apply if detecting closing braces
						if(currentChar == '}' && nbIndent > 0)
							nbIndent--;
						
						// Apply indentation to current line containing only a brace
						for(int j=0; j<nbIndent; j++)
						{
							contentSB.insert(i, '\t');
							i++;
							prevChar = '\t';
						}
						
						// Update number of indents to apply if detecting opening braces
						if(currentChar == '{')
							nbIndent++;
					}
					
					break;
					
				case '\n':
					
					// If next line is not one containing only a brace
					if(nextChar != '{' && nextChar != '}')
					{
						// Indent new line
						for(int j=0; j<nbIndent; j++)
						{
							contentSB.insert(i+1, '\t');
							nextChar = '\t';
						}
					}
					
					// Update comment state if end of a one-line-comment detected
					if(commentState.equals("ONE_LINE_COMMENT"))
						commentState = "NONE";
					
					break;
				
				case '\'':
					
					// If not already in a comment
					if(commentState.equals("NONE"))
					{
						// If previous char is not an active backslash
						if(prevChar != '\\' || (prevChar == '\\' && i > 1 && contentSB.charAt(i-2) == '\\'))
						{
							// Update quote state if start of quote detected
							if(quoteState.equals("NONE"))
								quoteState = "SINGLE_QUOTE";
							
							// Update quote state if end of current quote detected
							else if(quoteState.equals("SINGLE_QUOTE"))
								quoteState = "NONE";
						}
					}
					
					break;
					
				case '"':
					
					// If not already in a comment
					if(commentState.equals("NONE"))
					{
						// If previous char is not an active backslash
						if(prevChar != '\\' || (prevChar == '\\' && i > 1 && contentSB.charAt(i-2) == '\\'))
						{
							// Update quote state if start of quote detected
							if(quoteState.equals("NONE"))
								quoteState = "DOUBLE_QUOTES";
							
							// Update quote state if end of current quote detected
							else if(quoteState.equals("DOUBLE_QUOTES"))
								quoteState = "NONE";
						}
					}
					
					break;
					
				case '/':
					
					// If not already between quotes
					if(quoteState.equals("NONE"))
					{
						// If start of comment detected
						if(commentState.equals("NONE"))
						{
							// Update comment state if start of '//' comment detected
							if(nextChar == '/')
								commentState = "ONE_LINE_COMMENT";
							// Update comment state if start of '/*' comment detected
							else if(nextChar == '*')
								commentState = "MULTIPLE_LINES_COMMENT";
						}
						
						// Update comment state if end of current multiple-lines-comment detected
						else if(commentState.equals("MULTIPLE_LINES_COMMENT") && prevChar == '*')
							commentState = "NONE";
					}
					
					break;
			}
			
			// Process special scenarios
			if(quoteState.equals("NONE") && commentState.equals("NONE"))
			{
				// Switch cases indentation
				if((contentSB.indexOf("case", i) == i))
				{
					// If current case is the last one before the next break
					if(indexOutsideQuotesAndCommentsOf("case", i+1, contentSB.toString()) > indexOutsideQuotesAndCommentsOf("break;", i+1, contentSB.toString()) || indexOutsideQuotesAndCommentsOf("case", i+1, contentSB.toString()) == -1)
						// Update number of indents to apply
						nbIndent++;
				}
				else if((contentSB.indexOf("break;", i) == i) && quoteState.equals("NONE") && commentState.equals("NONE"))
				{
					// Update number of indents to apply
					if(nbIndent > 0)
						nbIndent--;
				}
				
				// Conditional statement having no braces indentation
				if(((contentSB.indexOf("if", i) == i && (i<5 || contentSB.indexOf("else if", i-5) != i-5)) || contentSB.indexOf("else", i) == i || contentSB.indexOf("for", i) == i || contentSB.indexOf("while", i) == i || contentSB.indexOf("do", i) == i) && quoteState.equals("NONE") && commentState.equals("NONE"))
				{
					String currentStatement;
					int currentStatementEndIndex;
					int nbOpenedParentesis = 0, nbClosedParentesis = 0;
					int j = i;
					
					// Get index of end of condition
					if(contentSB.indexOf("do", i) != i && (contentSB.indexOf("else", i) != i || contentSB.indexOf("else if", i) == i))
					{
						// Find index when number of '(' equals number of ')'
						while(!(nbOpenedParentesis > 0 && nbOpenedParentesis == nbClosedParentesis))
						{
							if(contentSB.charAt(j) == '(' && !isBetweenQuotesOrCommentsAt(j, contentSB.toString()))
								nbOpenedParentesis++;
							else if(contentSB.charAt(j) == ')' && !isBetweenQuotesOrCommentsAt(j, contentSB.toString()))
								nbClosedParentesis++;
							
							j++;
						}
					}
					
					// Get the currently being processed statement (action following the condition)
					currentStatement = contentSB.substring(j);
					
					currentStatementEndIndex = indexOutsideQuotesAndCommentsOf(";", 0, currentStatement);
					if(currentStatementEndIndex != -1)
						currentStatement = currentStatement.substring(0, currentStatementEndIndex);
					
					// If the statement has no braces & requires indentation
					if(currentStatementEndIndex != -1 && !currentStatement.contains("{") && currentStatement.contains("\n"))
					{
						// Count number of lines to indent
						int nbLines = currentStatement.length() - currentStatement.replace("\n", "").length();
						
						// Insert indentation on beginning of every statement line
						int nbProcessedLines = 0;
						while(nbProcessedLines != nbLines)
						{
							if(contentSB.charAt(j) == '\n')
							{
								// Insert indentation
								contentSB.insert(j+1, '\t');
								
								nbProcessedLines++;
							}
							
							j++;
						}
					}
				}
			}

			// Set latest read char as previous char
			prevChar = currentChar;
			
			i++;
		}
		
		return contentSB.toString();
	}
	
	/**
	 * Returns if the specified index is between quotes or in a comment in the specified content.
	 * 
	 * @param index the index of the char to look for.
	 * @param content the content from which the index is processed.
	 * @return if the specified index is between quotes or in a comment in the specified content.
	 */
	public static boolean isBetweenQuotesOrCommentsAt(int index, String content)
	{
		boolean isBetweenSingleQuotes = false, isBetweenDoubleQuotes = false;
		boolean isInOneLineComment = false, isInMultipleLinesComment = false;
		boolean activeBackslash;
		
		StringBuilder contentSB = new StringBuilder(content);
		
		// Iterate over chars from beginning to specified index
		int i = 0;
		while(i < index && i < contentSB.length())
		{
			// Look for active backslash
			activeBackslash = false;
			if(i > 1 && contentSB.charAt(i-1) == '\\' && contentSB.charAt(i-2) != '\\')
				activeBackslash = true;
			
			// Look for quotes
			if(!isInOneLineComment && !isInMultipleLinesComment && !activeBackslash)
			{
				if(contentSB.charAt(i) == '\'')
				{
					if(isBetweenSingleQuotes)
						isBetweenSingleQuotes = false;
					else if(!isBetweenDoubleQuotes)
						isBetweenSingleQuotes = true;
				}
				else if(contentSB.charAt(i) == '"')
				{
					if(isBetweenDoubleQuotes)
						isBetweenDoubleQuotes = false;
					else if(!isBetweenSingleQuotes)
						isBetweenDoubleQuotes = true;
				}
			}
			
			// Look for comments
			if(!isBetweenSingleQuotes && !isBetweenDoubleQuotes)
			{
				if(!isInMultipleLinesComment && !isInOneLineComment)
				{
					if(contentSB.charAt(i) == '/' && i+1 < contentSB.length())
					{
						if(contentSB.charAt(i+1) == '/')
							isInOneLineComment = true;
						else if(contentSB.charAt(i+1) == '*')
							isInMultipleLinesComment = true;
					}
				}
				else if(contentSB.charAt(i) == '\n' && isInOneLineComment)
				{
					isInOneLineComment = false;
				}
				else if(contentSB.charAt(i) == '*' && isInMultipleLinesComment)
				{
					if(i+1 < contentSB.length() && contentSB.charAt(i+1) == '/')
						isInMultipleLinesComment = false;
				}
			}
			
			i++;
		}
		
		return isBetweenSingleQuotes || isBetweenDoubleQuotes || isInOneLineComment || isInMultipleLinesComment;
	}
	
	/**
	 * Returns the index within the specified content of the first occurrence of the specified substring being outside quotes and comments, starting at the specified index.
	 * 
	 * @param str the substring to look for that is outside quotes and comments.
	 * @param fromIndex the index from which to start the search.
	 * @param content the content from which to do the search.
	 * @return the index within the specified content of the first occurrence of the specified substring being outside quotes and comments, starting at the specified index.
	 */
	public static int indexOutsideQuotesAndCommentsOf(String str, int fromIndex, String content)
	{
		int index = content.indexOf(str, fromIndex);
		
		while(index != -1 && isBetweenQuotesOrCommentsAt(index, content))
			index = content.indexOf(str, index+1);
		
		return index;
	}
}
