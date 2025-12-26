import java.io.File;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities to create an online glossary.
 *
 * @author Chris Ma
 */
public final class Glossary {

    /**
     * Private constructor.
     */
    private Glossary() {
    }

    /**
     * Nested Comparator class that implements Comparator interface
     */
    private static class AlphabeticalComparator implements Comparator<String> {

        /**
         * Compares two strings alphabetically.
         *
         * @param o1
         *            first string
         * @param o2
         *            second string
         * @return negative if o1 < o2, positive if o1 > o2, or 0 if o1 == o2
         */
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Reads the glossary input file
     *
     * @param in
     *            SimpleReader for input file
     *
     * @requires in stream exists
     *
     * @return map containing a list of <term, definition> pairs
     */
    public static Map<String, String> readGlossary(SimpleReader in) {
        Map<String, String> glossary = new Map1L<>();

        while (!in.atEOS()) {
            String term = in.nextLine().trim();
            if (term.length() > 0) {
                // Read definition lines until blank line
                // Trim the white spaces
                StringBuilder defBuilder = new StringBuilder();
                String line = in.nextLine().trim();

                while (line.length() > 0) {
                    defBuilder.append(line);
                    defBuilder.append("\n");
                    if (!in.atEOS()) {
                        line = in.nextLine().trim();
                    } else {
                        line = "";
                    }
                }

                String definition = defBuilder.toString();
                glossary.add(term, definition);
            }
        }

        return glossary;
    }

    /**
     * Produces a queue of all glossary terms sorted alphabetically.
     *
     * @param glossary
     *            map containing glossary entries
     * @return queue of alphabetically sorted terms
     */
    public static Queue<String> sortedQueueFromMap(Map<String, String> glossary) {
        Queue<String> terms = new Queue1L<String>();
        for (Map.Pair<String, String> term : glossary) {
            terms.enqueue(term.key());
        }
        // sort the terms in queue
        Comparator<String> comparator = new AlphabeticalComparator();
        terms.sort(comparator);

        return terms;
    }

    /**
     * Generates separate pages with each of the terms with their definitions
     *
     * @param glossary
     *            glossary map
     * @param directory
     *            directory path string
     *
     * @requires map is not empty
     * @requires directory must exist
     *
     */
    public static void writeDefinition(Map<String, String> glossary, String directory) {

        String word = "";
        String definition = "";
        for (Map.Pair<String, String> term : glossary) {
            word = term.key();
            definition = term.value();
            definition = definition.replace("\n", "<br>");
            SimpleWriter out = new SimpleWriter1L(directory + "/" + word + ".html");
            out.println("<html>");
            out.println("<head><title>" + word + "</title></head>");
            out.println("<body>");
            out.println("<h2><b><i><font color=\"red\">" + word + "</font></i></b></h2>");
            out.println("<blockquote>" + definition + "</blockquote>");
            out.print("<hr />");
            out.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
            out.println("</body></html>");

            out.close();
        }
    }

    /**
     * Writes an HTML page for the definition of each term in the glossary, with
     * hyperlink so that any glossary term appearing in a definition links to
     * its page.
     *
     * @param glossary
     *            map of terms and definitions
     * @param directory
     *            output directory path for generated HTML files
     *
     * @requires map is not empty
     * @requires directory must exist
     *
     */
    public static void writeDefinitionWHyperlink(Map<String, String> glossary,
            String directory) {

        String word = "";
        String definition = "";
        for (Map.Pair<String, String> term : glossary) {
            word = term.key();
            definition = term.value();
            definition = definition.replace("\n", " <br>");
            SimpleWriter out = new SimpleWriter1L(directory + "/" + word + ".html");
            out.println("<html>");
            out.println("<head><title>" + word + "</title></head>");
            out.println("<body>");
            out.println("<h2><b><i><font color=\"red\">" + word + "</font></i></b></h2>");
            String[] tokens = definition.split("\\s+");
            out.println("<blockquote>");
            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i];
                String punctuation = "";
                if (token.contains(",")) {
                    token = token.substring(0, token.length() - 1);
                    punctuation = ",";
                } else if (token.contains(";")) {
                    token = token.substring(0, token.length() - 1);
                    punctuation = ";";
                } else if (token.contains(":")) {
                    token = token.substring(0, token.length() - 1);
                    punctuation = ":";
                }

                if (glossary.hasKey(token)) {
                    out.print("<a href=\"" + token + ".html\">" + token + "</a>");
                } else if (glossary.hasKey(token.substring(0, token.length() - 1))) {
                    out.print("<a href=\"" + token.substring(0, token.length() - 1)
                            + ".html\">" + token + "</a>");
                } else {
                    out.print(token);
                }

                if (!punctuation.equals("")) {
                    out.print(punctuation);
                }

                if (token.compareTo("<br>") != 0) {
                    out.print(" ");
                }
            }
            out.println("</blockquote>");
            out.print("<hr />");
            out.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
            out.println("</body></html>");

            out.close();
        }
    }

    /**
     * Writes the top-level index.html page that has a list of terms
     *
     * @param terms
     *            queue of alphabetized glossary terms
     * @param directory
     *            output directory path
     *
     */
    public static void writeIndex(Queue<String> terms, String directory) {
        SimpleWriter out = new SimpleWriter1L(directory + "/index.html");

        out.println("<html lang=\"en\">");
        out.println("<head><title>Glossary Index</title></head>");
        out.println("<body>");
        out.println("<h2>Glossary</h2>");
        out.print("<hr />");
        out.println("<main>");
        out.println("<h3>Index<h3>");
        out.println("<ul>");

        for (String term : terms) {
            out.println("<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }

        out.println("</ul>");
        out.println("</main>");
        out.println("</body></html>");

        out.close();
    }

    /**
     * Main Program.
     *
     * @param args
     *            command line arguments
     * @ensures user is prompted for inputs(text file and output directory) and
     *          glossary html files are created inside chosen directory
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        Queue<String> sortedTerms;
        Map<String, String> glossary;

        out.print("Input file: ");
        String inputFile = in.nextLine();
        File file = new File(inputFile);
        while (!file.exists()) {
            out.println("Error: File does not exist!");
            out.print("Input file: ");
            inputFile = in.nextLine();
            file = new File(inputFile);
        }

        out.print("Output folder path: ");
        String folderPath = in.nextLine();
        File directory = new File(folderPath);
        while (!(directory.exists() && directory.isDirectory())) {
            out.println("Error: directory does not exist!");
            out.print("Output folder path: ");
            folderPath = in.nextLine();
            directory = new File(folderPath);
        }

        // Read input file
        SimpleReader fileIn = new SimpleReader1L(inputFile);
        glossary = readGlossary(fileIn);
        fileIn.close();

        // Sort the terms
        sortedTerms = sortedQueueFromMap(glossary);

        // Write term index into index.html
        writeIndex(sortedTerms, folderPath);

        // Write individual term definitions into a output folder
        writeDefinition(glossary, folderPath);

        out.println("Glossary generation complete.");
        out.close();
        in.close();
    }
}
