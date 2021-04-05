import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTestTema {
    //create new xlm file
    //assign to service to avoid collisions
    //service returns 1 if validator founds a problem (possible fix?)
    //service returns 0 if duplicate found

    private Service service;

    @BeforeEach
    void setUp() throws IOException {
        try {
            File testTemaXml = new File("testTema.xml");
            if (testTemaXml.createNewFile()) {
                FileWriter myWriter = new FileWriter(testTemaXml.getName());
                myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                myWriter.write("<Entitati>\n</Entitati>");
                myWriter.close();
            } else {
                testTemaXml.delete();
                testTemaXml = new File("testTema.xml");
                FileWriter myWriter = new FileWriter(testTemaXml.getName());
                myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
                myWriter.write("<Entitati>\n</Entitati>");
                myWriter.close();
            }

        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        service = new Service(
                new StudentXMLRepository(new StudentValidator(), "studenti.xml"),
                new TemaXMLRepository(new TemaValidator(), "testTema.xml"),
                new NotaXMLRepository(new NotaValidator(), "note.xml"));
    }

    @Test
    void test_saveTema_success() {
        String id = "1";
        int result = service.saveTema(id, "descriere", 5, 2);
        assertEquals(result, 1);
    }

    @Test
    void test_saveTema_duplicate() {
        String id = "1";
        service.saveTema(id, "descriere", 5, 2);
        int result = service.saveTema(id, "descriere", 5, 2);
        assertEquals(result, 0);
    }

    @Test
    void test_saveTema_null_id() {
        String id = null;
        try {
            int result = service.saveTema(id, "descriere", 5, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("ID invalid!"));
        }
    }

    @Test
    void test_saveTema_empty_id() {
        String id = "";
        try {
            int result = service.saveTema(id, "descriere", 5, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("ID invalid!"));
        }
    }

    @Test
    void test_saveTema_null_desc() {
        String desc = null;
        try {
            int result = service.saveTema("1", desc, 5, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Descriere invalida!"));
        }
    }

    @Test
    void test_saveTema_empty_desc() {
        String desc = null;
        try {
            int result = service.saveTema("1", desc, 5, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Descriere invalida!"));
        }
    }

    @Test
    void test_saveTema_lower_deadline() {
        int deadline = 0;
        try {
            int result = service.saveTema("1", "desc", deadline, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline invalid!"));
        }
    }

    @Test
    void test_saveTema_lowerEqual_deadline() {
        int deadline = 1;
        try {
            int result = service.saveTema("1", "desc", deadline, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline mai mic sau egal ca startline!"));
        }
    }

    @Test
    void test_saveTema_lowerPass_deadline() {
        int deadline = 2;
        int result = service.saveTema("1", "desc", deadline, 1);
        assertEquals(result, 1);
    }

    @Test
    void test_saveTema_upper_deadline() {
        int deadline = 15;
        try {
            int result = service.saveTema("1", "desc", deadline, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline invalid!"));
        }
    }

    @Test
    void test_saveTema_upperEqual_deadline() {
        int deadline = 14;
        try {
            int result = service.saveTema("1", "desc", deadline, 2);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline invalid!"));
        }
    }

    @Test
    void test_saveTema_upperPass_deadline() {
        int deadline = 13;
        int result = service.saveTema("1", "desc", deadline, 12);
        assertEquals(result, 1);
    }

    @Test
    void test_saveTema_deadline_lower_startline() {
        int deadline = 5;
        int startline = 7;
        try {
            int result = service.saveTema("1", "desc", deadline, startline);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline mai mic sau egal ca startline!"));
        }
    }

    @Test
    void test_saveTema_deadline_equal_startline() {
        int deadline = 5;
        int startline = 5;
        try {
            int result = service.saveTema("1", "desc", deadline, startline);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline mai mic sau egal ca startline!"));
        }
    }

    @Test
    void test_saveTema_deadline_greater_startline() {
        int deadline = 6;
        int startline = 5;
        int result = service.saveTema("1", "desc", deadline, startline);
        assertEquals(result,1);
    }

    @Test
    void test_saveTema_lower_startline() {
        int startline = 0;
        try {
            int result = service.saveTema("1", "desc", 5, startline);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Data de primire invalida!"));
        }
    }

    @Test
    void test_saveTema_lowerEqual_startline() {
        int startline = 1;
        try {
            int result = service.saveTema("1", "desc", 5, startline);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Data de primire invalida!"));
        }
    }

    @Test
    void test_saveTema_lowerPass_startline() {
        int startline = 4;
        int result = service.saveTema("1", "desc", 5, startline);
        assertEquals(result, 1);
    }

    @Test
    void test_saveTema_upper_startline() {
        int startline = 15;
        try {
            int result = service.saveTema("1", "desc", 5, startline);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Data de primire invalida!"));
        }
    }

    @Test
    void test_saveTema_upperEqual_startline() {
        int startline = 14;
        try {
            int result = service.saveTema("1", "desc", 5, startline);
        } catch (ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Deadline mai mic sau egal ca startline!"));
        }
    }

    @Test
    void test_saveTema_upperPass_startline() {
        int startline = 13;
        int result = service.saveTema("1", "desc", 14, startline);
        assertEquals(result, 1);
    }


}