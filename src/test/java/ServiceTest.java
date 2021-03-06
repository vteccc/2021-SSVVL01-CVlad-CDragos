import org.junit.jupiter.api.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private final Service service = new Service(
            new StudentXMLRepository(new StudentValidator(), "studenti.xml"),
            new TemaXMLRepository(new TemaValidator(), "teme.xml"),
            new NotaXMLRepository(new NotaValidator(), "note.xml"));

    @Test
    public void test_saveStudent_success() {
        String id = "3";
        int result = service.saveStudent(id, "tester", 933);
        assertEquals(1, result);
        service.deleteStudent(id);
    }

    @Test
    public void test_saveStudent_id_existent() {
        String id = "3";
        service.saveStudent(id, "tester", 933);
        int result = service.saveStudent(id, "tester", 933);
        assertEquals(0, result);
        service.deleteStudent(id);
    }

    @Test
    public void test_saveStudent_empty_name() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "", 933);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Nume invalid!"));
        }
    }

    @Test
    public void test_saveStudent_invlaid_grupa_lower() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "Name", 110);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Grupa invalida!"));
        }
    }

    @Test
    public void test_saveStudent_valid_grupa_lower() {
        String id = "3";
        int result = service.saveStudent(id, "tester", 111);
        assertEquals(1, result);
        service.deleteStudent(id);
    }

    @Test
    public void test_saveStudent_invlaid_grupa_upper() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "Name", 938);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Grupa invalida!"));
        }
    }

    @Test
    public void test_saveStudent_valid_grupa_upper() {
        String id = "3";
        int result = service.saveStudent(id, "tester", 937);
        assertEquals(1, result);
        service.deleteStudent(id);
    }

    @Test
    public void test_saveStudent_invlaid_grupa_lower_without_name() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "", 110);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Nume invalid!"));
        }
    }

    @Test
    public void test_saveStudent_valid_grupa_lower_without_name() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "", 111);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Nume invalid!"));
        }
    }

    @Test
    public void test_saveStudent_invlaid_grupa_upper_without_name() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "", 938);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Nume invalid!"));
        }
    }

    @Test
    public void test_saveStudent_valid_grupa_upper_without_name() {
        String id = "3";
        try {
            int result = service.saveStudent(id, "", 937);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Nume invalid!"));
        }
    }

    @Test
    public void test_saveStudent_null_name() {
        String id = "3";
        try {
            int result = service.saveStudent(id, null, 933);
        } catch (
                ValidationException exception) {
            String message = exception.getMessage();
            assertTrue(message.contains("Nume invalid!"));
        }
    }


}