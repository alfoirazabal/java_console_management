/* 
This can be used publicly and without any restrictions, including commercially.
The author is not liable for any damage or harm that this product may cause.
Crediting the Author is really appreciated.
Hope this will add some value to your project.
Have fun 😊

Dependencies: jansi-1.18.jar (https://github.com/fusesource/jansi)
    Library that adds Foreground and Background color to Win and UNIX Consoles

*/
package alfoiralev;

/**
 *
 * @author Alfonso Irazabal Levy (github: alfoirazabal)
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import org.fusesource.jansi.AnsiConsole;

/**
 * <b>Version 2.5 - 2020/04/26 16:18</b>
 * <br>
 * Either extend this class or import it to use it 
 * <ul>
 * <li>If imported, use ConsoleManagement...</li>
 * <li>If extended, just use a method inside this class.</li>
 * </ul>
*/
public class ConsoleManagement {

	private static final char OS = getSysOS();
	
	private static final String OPT_INPUT_BACK = "0";
	private static final String OPT_INPUT_LISTEN = ":";
	private static final String INFO_INPUT_INVALID = "The selected " +
                "option is invalid, press enter and try again...";
	
	private static final Scanner SC = new Scanner(System.in);
        
        // The colors for the Menu and Messages
        // Index 0: Foreground Color, Index 1: Background Color
        private static final String[] COL_MENU_TITLE = 
                new String[]{"", ""};
        private static final String[] COL_MENU_DESCRIPTION = 
                new String[]{"", ""};
        private static final String[] COL_MENU_BACKOPTION = 
                new String[]{"", ""}; 
        private static final String[] COL_MENU_INPUTLISTEN =
                new String[]{"", ""};
        private static final String[] COL_INVALID_INPUT = 
                new String[]{
                        ConsoleForeColor.RED.Code,
                        ""
                };
        
        static {
            loadAnsi(); // Load Ansi on startup
            Runtime.getRuntime().addShutdownHook(   // Unload Ansi on exit
                    new Thread(ConsoleManagement::unloadAnsi)
            );
        }
        
	public final static void println() {
            System.out.println();
	}
	public final static void println(Object obj) {
            System.out.println(obj);
	}
	public final static void println(Object obj, ConsoleForeColor color) {
            System.out.println(color.Code + obj);
	}
        public final static void println(
                Object obj,
                ConsoleForeColor colorFore,
                ConsoleBackColor colorBack
        ) {
            System.out.println(colorFore.Code + colorBack.Code + obj);
        }
	public final static void print(Object obj) {
            System.out.print(obj);
	}
        public final static void print(Object obj, ConsoleForeColor color) {
            System.out.print(color.Code + obj + ConsoleForeColor.RESET.Code);
        }
        public final static void print(
                Object obj, 
                ConsoleForeColor fgColor, 
                ConsoleBackColor bgColor
        ) {
            System.out.print(fgColor.Code + bgColor.Code + obj + 
                    ConsoleForeColor.RESET.Code
            );
        }

        /**
         * Simply clears the Console, for Windows and UNIX OSs
         * Solutions obtained from: 
         * https://stackoverflow.com/questions/2979383/java-clear-the-console
         */
	public final static void clearConsole()
	{
            try
            {
                if (OS == 'w')
                {
                    new ProcessBuilder("cmd", "/c", "cls")
                            .inheritIO().start().waitFor();
                }
                else
                {
                    print("\033[H\033[2J");
                    System.out.flush();
                }
            }
            catch (final IOException | InterruptedException e)
            {
                // Handle exceptions.
            }
	}
	
        /**
         * Clears the console and displays a Menu with options and returns 
         * the selected option integer (0 for back or exit)
         * @param title The title of the menu
         * @param description The subtitle or description of the menu
         * @param backText The text displayed next to the go back character (0)
         * @param options The options of the menu (displayed next to their
         * respective index + 1)
         * @return The chosen user input
         */
	public final static int displayMenu(
                String title,
                String description,
                String backText,
                String[] options
	) {
            clearConsole();
            println(COL_MENU_TITLE[0] + COL_MENU_TITLE[1] + 
                    title + 
                    ConsoleForeColor.RESET.Code
            );
            println();
            println(COL_MENU_DESCRIPTION[0] +  COL_MENU_DESCRIPTION[1] +
                    description + 
                    ConsoleForeColor.RESET.Code);
            println();
            println(COL_MENU_BACKOPTION[0] + COL_MENU_BACKOPTION[1] + 
                    OPT_INPUT_BACK + " - " + backText +
                    ConsoleForeColor.RESET.Code);
            println();
            for(int i = 0 ; i < options.length ; i++) {
                println((i + 1) + " - " + options[i]);
            }
            println();

            print(COL_MENU_INPUTLISTEN[0] + COL_MENU_INPUTLISTEN[1] + 
                    OPT_INPUT_LISTEN +
                    ConsoleForeColor.RESET.Code);
            try {
                int response = SC.nextInt();
                SC.nextLine();	// Flush Scanner

                if (response >= 0 && response <= options.length) {
                    return response;
                } else {
                    throw new InputMismatchException();				
                }
            } catch (InputMismatchException ex) {
                SC.nextLine();	// Flush Scanner
                displayInvalidInput();
                return displayMenu(
                        title, description, backText, options
                );
            }
		
	}
        
        /**
         * If you have chosen to set menu colors for titles, descriptions, and
         * such, you can reset them here
         */
        public static void resetMenuColors() {
            for(int i = 0 ; i < 2 ; i++) {
                COL_MENU_BACKOPTION[i] = "";
                COL_MENU_DESCRIPTION[i] = "";
                COL_MENU_INPUTLISTEN[i] = "";
                COL_MENU_TITLE[i] = "";
            }
        }
        
        /**
         * Set the foreground and background color for specific elements of the
         * menu, or input messages, etc.
         * @param elementType Element to set the color to. E.g the Menu Title
         * @param foregroundColor The foreground color of the element
         * @param backgroundColor The background color of the element
         */
        public static void setElementColor(
                ElementColorType elementType,
                ConsoleForeColor foregroundColor,
                ConsoleBackColor backgroundColor
        ) {
            String fgCol = foregroundColor.Code;
            String bgCol = backgroundColor.Code;
            switch(elementType.type) {
                case 'b':
                    COL_MENU_BACKOPTION[0] = fgCol;
                    COL_MENU_BACKOPTION[1] = bgCol;
                    break;
                case 'd':
                    COL_MENU_DESCRIPTION[0] = fgCol;
                    COL_MENU_DESCRIPTION[1] = bgCol;
                    break;
                case 'i':
                    COL_MENU_INPUTLISTEN[0] = fgCol;
                    COL_MENU_INPUTLISTEN[1] = bgCol;
                    break;
                case 't':
                    COL_MENU_TITLE[0] = fgCol;
                    COL_MENU_TITLE[1] = bgCol;
                    break;
                case 'n':
                    COL_INVALID_INPUT[0] = fgCol;
                    COL_INVALID_INPUT[1] = bgCol;
            }
        }
	
	private static void displayInvalidInput() {
            clearConsole();
            print(COL_INVALID_INPUT[0] + COL_INVALID_INPUT[1] + 
                    INFO_INPUT_INVALID + ConsoleForeColor.RESET.Code
            );
            SC.nextLine();
	}

        /**
         * Clears the console and requests a String to the user.
         * @param title The title in the console
         * @param requestText The text indicating what is to be requested from
         * the user input
         * @return The outputted user String
         */
	public static final String requestString(
                String title, String requestText
	) {
            String returnable;
            clearConsole();
            if(!title.equals("")) {
                    println(title);
                    println();
            }
            print(requestText + ": ");
            returnable = SC.nextLine();
            return returnable;
	}

        /**
         * Clears the console and requests an Integer to the user.
         * @param title The title in the console
         * @param requestText The text indicating what is to be requested from
         * the user input
         * @return The outputted user Integer
         */
	public static final int requestInt(String title, String requestText) {
            try {
                int result = 
                        Integer.parseInt(requestString(title, requestText));
                return result;
            } catch (NumberFormatException ex) {
                displayInvalidInput();
                return requestInt(title, requestText);
            }
	}

        /**
         * Clears the console and requests a Float to the user.
         * @param title The title in the console
         * @param requestText The text indicating what is to be requested from
         * the user input
         * @return The outputted user Float Number
         */
	public static final float requestFloat(
                String title, String requestText
        ) {
            try {
                float result = 
                        Float.parseFloat(requestString(title, requestText));
                return result;
            } catch(NumberFormatException ex) {
                displayInvalidInput();
                return requestFloat(title, requestText);
            }
	}

        /**
         * Clears the console and requests a Boolean to the user.
         * @param title The title in the console
         * @param requestText The text indicating what is to be requested from
         * the user input
         * @param ok the character used by the user to choose 'yes'
         * @param noOk the character used by the user to choose 'no'
         * @return <b>true</b> if the inputted user char is ok, <b>false</b> 
         * otherwise (note that any character except ok is to be treated as 
         * false)
         */
	public static final boolean requestBoolean(
                String title, String requestText,
                char ok, char noOk
	) {
            try {
                String strRes = requestString(title, requestText);
                return (strRes.charAt(0) == ok);
            } catch (NumberFormatException ex) {
                return requestBoolean(title, requestText, ok, noOk);
            }
	}

        
        /**
         * Clears the console and requests a char to the user.
         * @param title The title in the console
         * @param requestText The text indicating what is to be requested from
         * the user input
         * @return The outputted user char
         */
	public static final char requestChar(
                String title, String requestText
	) {
            char reqChar = requestString(title, requestText).charAt(0);
            return reqChar;
        }
    
    /**
     * Shows a Temporary Message without clearing the console
     * @param message The message to be displayed
     */
    public static final void showTemporaryMessage(
            String message
    ) {
        clearConsole();
        println(message);
    }

    /**
     * Clears the console, shows a message, and waits for the user to press
     * anything
     * @param message The message to be displayed
     */
    public static final void showMessage(
            String message
    ) {
        clearConsole();
        println(message);
        SC.nextLine();
    }
    
    /**
     * Shows a Message without clearing the console, and waits for the user to
     * press anything
     * @param message The message to be displayed
     */
    public static final void waitForInput(
            String message
    ) {
        // Without clearing the Screen
        print(message);
        SC.nextLine();
    }
    
    /*
        Inner Usable methods
    */
    private static char getSysOS() {
        String osName = System.getProperty("os.name");
        char os;
        if (osName.contains("Windows")) {
            os = 'w';   // Windows OS
        } else {
            os = 'o';   // Other OS, such as Linux (Unix)
        }
        return os;
    }

    private static void loadAnsi() {
        if(System.console() != null) {
            // Non IDE Console (Tested with NetBeans Console)
            AnsiConsole.systemInstall();
        }
    }
    
    private static void unloadAnsi() {
        if(System.console() != null) {
            // Non IDE Console (Tested with NetBeans Console)
            print(ConsoleForeColor.RESET.Code); // Also resets background color
            AnsiConsole.systemUninstall();
        }
    }
	
}