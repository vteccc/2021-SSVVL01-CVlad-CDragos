package ssvv.example;

import junit.framework.TestCase;
import org.junit.Test;

import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;


public class ServiceTest extends TestCase {

    private final Service service = new Service(
            new StudentXMLRepository(new StudentValidator(), "studenti.xml"),
            new TemaXMLRepository(new TemaValidator(), "teme.xml"),
            new NotaXMLRepository(new NotaValidator(), "note.xml"));

    public void test_saveStudent_success() {
        String id = "3";
        int result = service.saveStudent(id, "tester", 933);
        assertEquals(1,result);
        service.deleteStudent(id);
    }

    public void test_saveStudent_empty_name() {
        String id = "3";
        int result = service.saveStudent(id, "", 933);
        assertEquals(1,result);
    }

    public void test_saveStudent_invlaid_grupa_lower() {
        String id = "3";
        int result = service.saveStudent(id, "", 1);
        assertEquals(1,result);
    }

    public void test_saveStudent_invlaid_grupa_upper() {
        String id = "3";
        int result = service.saveStudent(id, "", 990);
        assertEquals(1,result);
    }

    public void test_saveStudent_null_name() {
        String id = "3";
        int result = service.saveStudent(id, null, 933);
        assertEquals(1,result);
    }

}