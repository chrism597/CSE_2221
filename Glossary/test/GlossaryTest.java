import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GlossaryTest {

    @Test
    public void readGlossaryTest() {
        SimpleReader in = new SimpleReader1L("test/tests.txt");
        Map<String, String> m = new Map1L<>();

        m = Glossary.readGlossary(in);
        assertEquals(8, m.size());
        assertEquals(m.value("plastoid"),
                "used for the armour of the GAR and Imperial troopers and Darth Vader suit\n");
    }

    @Test
    public void sortedQueueFromMapTest() {
        SimpleReader in = new SimpleReader1L("test/tests.txt");
        Map<String, String> m = new Map1L<>();
        Queue<String> q = new Queue1L<String>();
        m = Glossary.readGlossary(in);
        q = Glossary.sortedQueueFromMap(m);
        String[] expected = { "datacron", "duracrete", "durasteel", "holocron",
                "plastoid", "trooper", "vibrogenerator", "vibroweapon" };
        String[] result = new String[8];
        int i = 0;
        for (String s : q) {
            result[i] = s;
            i++;
        }
        assertArrayEquals(result, expected);
    }

    @Test
    public void writeDefinitionTest() {
        SimpleReader in = new SimpleReader1L("test/tests.txt");
        Map<String, String> m = new Map1L<>();
        m = Glossary.readGlossary(in);
        String directory = "test";
        Glossary.writeDefinition(m, directory);
        for (Map.Pair<String, String> p : m) {
            File file = new File("test/" + p.key() + ".html");
            assertEquals(file.exists(), true);
        }
    }

    @Test
    public void writeIndexTest() {
        SimpleReader in = new SimpleReader1L("test/tests.txt");
        Map<String, String> m = new Map1L<>();
        Queue<String> q = new Queue1L<String>();
        m = Glossary.readGlossary(in);
        q = Glossary.sortedQueueFromMap(m);
        String directory = "test";
        Glossary.writeIndex(q, directory);
        File file = new File("test/index.html");
        assertEquals(file.exists(), true);

    }

    @Test
    public void writeDefinitionWHyperlinkTest() {
        SimpleReader in = new SimpleReader1L("test/tests.txt");
        Map<String, String> m = new Map1L<>();
        m = Glossary.readGlossary(in);
        String directory = "test";
        Glossary.writeDefinitionWHyperlink(m, directory);
        for (Map.Pair<String, String> p : m) {
            File file = new File("test/" + p.key() + ".html");
            assertEquals(file.exists(), true);
        }
    }
}
