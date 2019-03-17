package com.ninox.focus.view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Java Regular Expression Validations
public class RegexMahaboob {
	
	private Pattern pattern;
	private Matcher matcher;
	
	/**
	 * ( # Start of group (?=.*\d) # must contains one digit from 0-9
	 * (?=.*[a-z]) # must contains one lowercase characters (?=.*[A-Z]) # must
	 * contains one uppercase characters (?=.*[@#$%]) # must contains one
	 * special symbols in the list "@#$%" . # match anything with previous
	 * condition checking {6,20} # length at least 6 characters and maximum of
	 * 20 ) # End of group
	 */
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	/**
	 * Java program to demonstrate use of Regular Expression to check if a
	 * String is a 6 digit number or not.
	 */
	// For allowing numbers 			=======> ".*[0-9].*"
	// For not allowing numbers 		=======> ".*[^0-9].*"
	// For allowing digits only(a-z) 	=======> ".*[a-z].*"
	// For allowing digits only(A-Z) 	=======> ".*[A-Z].*"
	// For not allowing only(a-z) 		=======> ".*[^a-z].*"
	// For not allowing only(A-Z)		=======> ".*[^A-Z].*"
	// For allowing digits only(a-zA-Z)	=======> ".*[a-zA-Z].*"
	// For not allowing only(a-zA-Z)	=======> ".*[^a-zA-Z].*"
	private static final String STRING_PATTERN_AZ = ".*[A-Z].*";
	private static final String STRING_PATTERN_az = ".*[a-z].*";
	private static final String STRING_PATTERN_azAZ = ".*[a-zA-Z].*";
	private static final String STRING_PATTERN_NOT_AZ = ".*[^A-Z].*";
	private static final String STRING_PATTERN_NOT_az = ".*[^a-z].*";
	private static final String STRING_PATTERN_NOT_azAZ = ".*[^a-zA-Z].*";
	private static final String ALLOW_DIGIT_PATTERN = ".*[0-9].*";
	private static final String NOT_ALLOW_DIGIT_PATTERN = ".*[^0-9].*";

	public RegexMahaboob(String patternFor) {
		if (patternFor.equals("passwordPattern")) {
			pattern = Pattern.compile(PASSWORD_PATTERN);
		} 
		else if (patternFor.equals("allowDigitPattern09")) {
			pattern = Pattern.compile(ALLOW_DIGIT_PATTERN);
		}
		else if (patternFor.equals("notAllowDigitPattern09")) {
			pattern = Pattern.compile(NOT_ALLOW_DIGIT_PATTERN);
		}
		else if (patternFor.equals("notAllowStringPatternazAZ")) {
			pattern = Pattern.compile(STRING_PATTERN_NOT_azAZ);
		}
		else if (patternFor.equals("notAllowStringPatternaz")) {
			pattern = Pattern.compile(STRING_PATTERN_NOT_az);
		}
		else if (patternFor.equals("notAllowStringPatternAZ")) {
			pattern = Pattern.compile(STRING_PATTERN_NOT_AZ);
		}
		else if (patternFor.equals("allowStringPatternazAZ")) {
			pattern = Pattern.compile(STRING_PATTERN_azAZ);
		}
		else if (patternFor.equals("allowStringPatternaz")) {
			pattern = Pattern.compile(STRING_PATTERN_az);
		}
		else if (patternFor.equals("allowStringPatternAZ")) {
			pattern = Pattern.compile(STRING_PATTERN_AZ);
		}		
	}

	/**
	 * Validate input string with regular expression
	 * 
	 * @param input
	 *            string for validation
	 * @return true valid input string, false invalid input string
	 */
	public boolean validate(final String inputString) {

		matcher = pattern.matcher(inputString);

		return matcher.matches();
	}


}
